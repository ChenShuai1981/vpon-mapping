package com.vpon.mapping

import scala.util.control.Breaks._

import com.google.common.cache.{CacheStats, CacheBuilder, Cache}
import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import mobi.mtld.da.device.DeviceApi
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.DeviceTypeMappingException

case class VponDevice(make: Option[String],
                      model: Option[String],
                      osName: Option[String],
                      osVersion: Option[String],
                      primaryHardwareType: Option[String],
                      vponDeviceTypeId: Int,
                      vponDeviceOsIds: Seq[Int])

case class VponDeviceType(id: Int, deviceType: String)

trait DeviceTypeMappingTrait {
  def init(cacheSpec: String): Unit
  def warmUp(): Unit
  def findVponDeviceTypeById(id: Int): Option[VponDeviceType]
  def findVponDeviceTypeByPrimaryHardwareType(uaPrimaryHardwareType: String): Option[VponDeviceType]
  def findVponDeviceByUA(ua: String): VponDevice
  def findAllVponDeviceTypes(): Seq[VponDeviceType]
  def underlyingCache: Cache[_, _]
}

object DeviceTypeMapping extends DeviceTypeMappingTrait with MappingConfig {
  private val deviceApi = new DeviceApi()
  private var cache: Cache[String, VponDevice] = _

  def underlyingCache = cache

  deviceApi.loadDataFromStream(classLoader.getResourceAsStream(uaDbPath))

  private val listFile = classLoader.getResourceAsStream(deviceTypeListPath)
  private val mappingFile = classLoader.getResourceAsStream(deviceTypeMappingPath)

  case class DeviceTypeListCsvRow(ID: String, DESC_1_TIER: String, WEB_DISPLAY_ORDER: String)
  case class DeviceTypeMappingCsvRow(UA_PRIMARY_HARDWARE_TYPE: String, ID: String, IGNORE_DESC_1_TIER: String)

  implicit val deviceTypeListReader = RowReader(r => DeviceTypeListCsvRow(r(0), r(1), r(2)))
  implicit val deviceTypeMappingReader = RowReader(r => DeviceTypeMappingCsvRow(r(0), r(1), r(2)))
  private val deviceTypeMap = new scala.collection.mutable.HashMap[Int, VponDeviceType]()
  try {
    csv.rowsR[DeviceTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val deviceType = row.DESC_1_TIER
      deviceTypeMap.put(id, VponDeviceType(id, deviceType))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $deviceTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new DeviceTypeMappingException(err)
    }
  }

  private val deviceTypeUAMap = new scala.collection.mutable.HashMap[String, VponDeviceType]()
  try{
    csv.rowsR[DeviceTypeMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.UA_PRIMARY_HARDWARE_TYPE.equals("UA_PRIMARY_HARDWARE_TYPE")) break // skip HEADER
      val uaPrimaryHardwareType = row.UA_PRIMARY_HARDWARE_TYPE
      val id = row.ID.toInt
      deviceTypeMap.get(id) match {
        case Some(dt) => deviceTypeUAMap.put(uaPrimaryHardwareType, dt)
        case None => throw new DeviceTypeMappingException(s"Not found VponDeviceType for id: ${id}")
      }
    })
  } catch {
    case e: DeviceTypeMappingException => throw e
    case e: Throwable => {
      val err = s"Caught exception when parsing $deviceTypeMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new DeviceTypeMappingException(err)
    }
  }

  def init(cacheSpec: String) {
    cache = CacheBuilder.from(cacheSpec).recordStats().build()
  }

  def warmUp(): Unit = {
    val vponDeviceType = findVponDeviceTypeByPrimaryHardwareType("Tablet")
    val ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"
    val vponDevice = findVponDeviceByUA(ua)
    (vponDeviceType, vponDevice)
  }

  def cacheStats: CacheStats = cache.stats

  def invalidateAll(keys: Iterable[String]) {
    import scala.collection.JavaConverters._
    cache.invalidateAll(keys.asJava)
  }

  def invalidate(key: String) {
    cache.invalidate(key)
  }

  def flush(): Unit = {
    cache.invalidateAll()
  }

  override def findVponDeviceTypeById(id: Int): Option[VponDeviceType] = deviceTypeMap.get(id)

  override def findVponDeviceTypeByPrimaryHardwareType(uaPrimaryHardwareType: String): Option[VponDeviceType] = {
    deviceTypeUAMap.get(uaPrimaryHardwareType) match {
      case Some(t) => Some(t)
      case None => findVponDeviceTypeByPrimaryHardwareType(WILDCARD) match {
        case Some(x) => Some(x)
        case None => None
      }
    }
  }

  override def findVponDeviceByUA(ua: String): VponDevice = {
    Option(cache.getIfPresent(ua)) match {
      case None => storeToCache(ua, lookup(ua))
      case Some(v) => v
    }
  }

  override def findAllVponDeviceTypes(): Seq[VponDeviceType] = {
    deviceTypeMap.values.toSeq
  }

  @inline
  private[this] def storeToCache(ua: String, value: VponDevice) = {
    cache.put(ua, value)
    value
  }

  @inline
  private[this] def lookup(ua: String) = {
    val properties = deviceApi.getProperties(ua)
    val make = if (properties.containsKey("manufacturer")) Some(properties.get("manufacturer").asString()) else None
    val model = if (properties.containsKey("marketingName")) Some(properties.get("marketingName").asString()) else None
    val osName = if (properties.containsKey("osName")) Some(properties.get("osName").asString()) else None
    val osVersion = if (properties.containsKey("osVersion")) Some(properties.get("osVersion").asString()) else None
    val primaryHardwareType = if (properties.containsKey("primaryHardwareType")) Some(properties.get("primaryHardwareType").asString()) else None
    val vponDeviceTypeId = findVponDeviceTypeIdByPrimaryHardwareType(primaryHardwareType)
    val vponDeviceOsIds = findVponDeviceOsIds(osName, osVersion)
    VponDevice(make, model, osName, osVersion, primaryHardwareType, vponDeviceTypeId, vponDeviceOsIds)
  }

  private def findVponDeviceOsIds(osName: Option[String], osVersion: Option[String]): Seq[Int] = {
    val s = OsFamilyMapping.findVponOsFamilyIds(osName.getOrElse(WILDCARD), osVersion.getOrElse(WILDCARD))
    Seq(s.id1, s.id2).flatten
  }

  private def findVponDeviceTypeIdByPrimaryHardwareType(primaryHardwareType: Option[String]): Int = {
    primaryHardwareType match {
      case Some(p) => DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType(p) match {
        case None => findVponDeviceTypeIdByWildcard
        case Some(t) => t.id
      }
      case None => findVponDeviceTypeIdByWildcard
    }
  }

  private def findVponDeviceTypeIdByWildcard: Int = {
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType(WILDCARD) match {
      case Some(d) => d.id
      case None => throw new DeviceTypeMappingException(s"Missing * mapping for Vpon Device Type")
    }
  }

}
