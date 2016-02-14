package com.vpon.mapping

import java.net.InetAddress

import scala.util.control.Breaks._

import com.google.common.cache.{CacheStats, CacheBuilder, Cache}
import com.maxmind.db.Reader.FileMode
import com.maxmind.geoip2.DatabaseReader
import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.validator.routines.InetAddressValidator
import org.slf4j.LoggerFactory

import com.vpon.mapping.GeographyMapping.GeoMatchType.GeoMatchType
import com.vpon.mapping.exception.GeographyMappingException

case class VponGeo(id: Int, country: String, region: String, city: String)
case class MaxmindGeo(countryId: Option[String], cityId: Option[String], cityName: Option[String])

trait GeographyMappingTrait {
  def init(cacheSpec: String): Unit
  def warmUp(): Unit
  def findVponGeoIdsByIP(ip: String): Seq[Int]
  def findVponGeoByVponGeoId(vponGeoId: Int): Option[VponGeo]
  def findVponGeoIdsByMaxmindGeoFields(optCountryId: Option[String], optCityId: Option[String], cityName: String): Seq[Int]
  def underlyingCache: Cache[_, _]
}

object GeographyMapping extends GeographyMappingTrait with config.MappingConfig {
  private[this] val logger = LoggerFactory.getLogger(GeographyMapping.getClass)

  private[this] val listFile = classLoader.getResourceAsStream(geographyListPath)
  private[this] val mappingFile = classLoader.getResourceAsStream(geographyMappingPath)

  private[this] var reader: DatabaseReader = _
  private[this] var cache: Cache[String, Seq[Int]] = _

  private[this] val geographyMap = new scala.collection.mutable.HashMap[Int, VponGeo]()
  private[this] val cityIdMapping = new scala.collection.mutable.HashMap[String, Seq[Int]]()
  private[this] val cityNameMapping = new scala.collection.mutable.HashMap[String, Seq[Int]]()

  case class GeographyListCsvRow(ID: String, DESC_1_TIER: String, DESC_2_TIER: String, DESC_3_TIER: String)
  case class GeographyMappingCsvRow(GEOIP_COUNTRY_ID: String, GEOIP_REGION_ID: String, REGION: String, ID: String, ID_1: String, ID_2: String)

  def underlyingCache = cache

  // scalastyle:off
  implicit val geographyListReader = RowReader(r => GeographyListCsvRow(r(1), r(2), r(3), r(4)))
  implicit val geographyMappingReader = RowReader(r => GeographyMappingCsvRow(r(0), r(1), r(2), r(3), r(4), r(5)))
  // scalastyle:on

  reader = new DatabaseReader.Builder(classLoader.getResourceAsStream(ipDbPath)).fileMode(FileMode.MEMORY).build()

  csv.rowsR[GeographyListCsvRow](listFile, ';').foreach(row => breakable {
    if (row.ID.equals("ID") || row.ID.isEmpty) break // skip HEADER
    try {
      val id = row.ID.toInt
      val country = row.DESC_1_TIER
      val region = row.DESC_2_TIER
      val city = row.DESC_3_TIER
      geographyMap.put(id, VponGeo(id, country, region, city))
    } catch {
      case e: Throwable => throw new GeographyMappingException(s"Caught exception when parsing geography mapping csv file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}")
    }
  })
  csv.rowsR[GeographyMappingCsvRow](mappingFile, ';').foreach(row => breakable {
    if (row.GEOIP_COUNTRY_ID.equals("GEOIP_COUNTRY_ID")) break // skip HEADER
    val countryId = row.GEOIP_COUNTRY_ID
    val cityId = row.GEOIP_REGION_ID
    val cityName = row.REGION
    val id = convertStringToInt(row.ID)
    val id1 = convertStringToInt(row.ID_1)
    val id2 = convertStringToInt(row.ID_2)
    val ids = Seq(id, id1, id2).flatten.sorted
    cityIdMapping.put(getCityIdMappingKey(countryId, cityId), ids)
    if (cityId.isEmpty) cityNameMapping.put(getCityNameMappingKey(countryId, cityName), ids)
  })

  def init(cacheSpec: String) {
    cache = CacheBuilder.from(cacheSpec).recordStats().build()
  }

  def warmUp(): Unit = findVponGeoIdsByIP("0.0.0.0")

  def invalidateAll(keys: Iterable[String]) {
    import collection.JavaConverters._
    cache.invalidateAll(keys.asJava)
  }

  def invalidate(key: String) {
    cache.invalidate(key)
  }

  def flush(): Unit = {
    cache.invalidateAll()
  }

  def cacheStats: CacheStats = cache.stats

  override def findVponGeoIdsByIP(ip: String): Seq[Int] = {
    Option(cache.getIfPresent(ip)) match {
      case None => storeToCache(ip, lookup(ip))
      case Some(v) => v
    }
  }

  override def findVponGeoByVponGeoId(vponGeoId: Int): Option[VponGeo] = {
    geographyMap.get(vponGeoId)
  }

  @inline
  private[this] def storeToCache(ip: String, value: Seq[Int]) = {
    cache.put(ip, value)
    value
  }

  @inline
  private[this] def lookup(ip: String) = {
    val inetAddressValidator = InetAddressValidator.getInstance()
    inetAddressValidator.isValid(ip) match {
      case false => {
        logger.warn(s"Invalid IP: ${ip}")
        findVponGeoIdsByMaxmindGeoFields(None, None, List.empty[String].toString())
      }
      case true => {
        try {
          val ipAddress = InetAddress.getByName(ip)
          val loc = reader.city(ipAddress)
          val countryId = Option(loc.getCountry) match {
            case None => None
            case Some(c) => Some(c.getGeoNameId.toString)
          }
          val cityId = Option(loc.getCity) match {
            case None => None
            case Some(c) => Some(c.getGeoNameId.toString)
          }
          val cityName = loc.getSubdivisions.toString
          val ids = findVponGeoIdsByMaxmindGeoFields(countryId, cityId, cityName)
          logger.debug(s"countryId = $countryId, cityId = $cityId, cityName = $cityName, ids = $ids")
          ids
        } catch {
          case e : Exception => {
            logger.warn(s"Address not found by ip: ${ip}")
            findVponGeoIdsByMaxmindGeoFields(None, None, List.empty[String].toString())
          }
        }
      }
    }
  }

  private def getCityIdMappingKey(countryId: String, cityId: String) = s"${countryId}#${cityId}"
  private def getCityNameMappingKey(countryId: String, cityName: String) = s"${countryId}#${cityName}"

  object GeoMatchType extends Enumeration {
    type GeoMatchType = Value

    val COUNTRY_ID_AND_CITY_ID = Value("COUNTRY_ID_AND_CITY_ID")
    val COUNTRY_ID_AND_CITY_NAME = Value("COUNTRY_ID_AND_CITY_NAME")
  }

  /**
   * 1. if given both countryId and cityId, then should query out one and exactly one mapping
   * 2. else if given both countryId and cityName, then should query out one and exactly one mapping
   * 3. else if given countryId, then should query out one and exactly one mapping
   * 4. else should query out one and exactly one wildcard mapping
   * @param optCountryId
   * @param optCityId
   * @param cityName
   * @return
   */
  override def findVponGeoIdsByMaxmindGeoFields(optCountryId: Option[String], optCityId: Option[String], cityName: String): Seq[Int] = {

    def matchAllWildcard: Seq[Int] = {
      cityIdMapping.get(getCityIdMappingKey(WILDCARD, WILDCARD)) match {
        case Some(d) => d
        case None => {
          throw new GeographyMappingException("Missing (*, *, *) for Vpon Geography Mapping")
        }
      }
    }

    def matchCountryIdWildcard(countryId: String): Seq[Int] = {
      cityIdMapping.get(getCityIdMappingKey(countryId, EMPTY)) match {
        case Some(d) => d
        case None => matchAllWildcard
      }
    }

    def getIds(matchType: GeoMatchType, countryId: String, cityField: String): Seq[Int] = {
      matchType match {
        case GeoMatchType.COUNTRY_ID_AND_CITY_ID => {
          cityIdMapping.get(getCityIdMappingKey(countryId, cityField)) match {
            case Some(d) => d
            case None => matchCountryIdWildcard(countryId)
          }
        }
        case GeoMatchType.COUNTRY_ID_AND_CITY_NAME => {
          cityNameMapping.get(getCityNameMappingKey(countryId, cityField)) match {
            case Some(d) => d
            case None => matchCountryIdWildcard(countryId)
          }
        }
      }
    }

    optCountryId match {
      case None => matchAllWildcard
      case Some(countryId) => optCityId match {
        case None => getIds(GeoMatchType.COUNTRY_ID_AND_CITY_NAME, countryId, cityName)
        case Some(cityId) => getIds(GeoMatchType.COUNTRY_ID_AND_CITY_ID, countryId, cityId)
      }
    }
  }
}
