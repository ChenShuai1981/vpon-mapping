package com.vpon.mapping

import scala.util.control.Breaks._
import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.OsFamilyMappingException

case class VponOsFamilyIds(id1: Option[Int], id2: Option[Int])
case class VponOsFamily(id: Int, osName: String, osVersion: String)

object OsFamilyMapping extends MappingConfig {

  private val listFile = classLoader.getResourceAsStream(osfamilyListPath)
  private val mappingFile = classLoader.getResourceAsStream(osfamilyMappingPath)

  private case class OsFamilyListCsvRow(ID: String, DESC_1_TIER: String, DESC_2_TIER: String)
  private case class OsFamilyMappingCsvRow(OS_NAME: String, OS_VERSION_MAJOR: String, OS_VERSION_MIDDLE: String, ID1: String, ID2: String)

  // scalastyle:off
  private implicit val osFamilyListReader = RowReader(r => OsFamilyListCsvRow(r(0), r(1), r(2)))
  private implicit val osFamilyMappingReader = RowReader(r => OsFamilyMappingCsvRow(r(0), r(1), r(2), r(3), r(4)))
  // scalastyle:on

  private val osFamilyMap = new scala.collection.mutable.HashMap[Int, VponOsFamily]()
  try {
    csv.rowsR[OsFamilyListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val osName = row.DESC_1_TIER
      val osVersion = row.DESC_2_TIER
      osFamilyMap.put(id, VponOsFamily(id, osName, osVersion))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $osfamilyListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new OsFamilyMappingException(err)
    }
  }

  var mapping = new scala.collection.mutable.HashMap[String, VponOsFamilyIds]()
  try{
    csv.rowsR[OsFamilyMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.OS_NAME.equals("OS_NAME")) break // skip HEADER
      val osName = row.OS_NAME
      val osVersionMajor = row.OS_VERSION_MAJOR
      val osVersionMiddle = row.OS_VERSION_MIDDLE
      val id1 = convertStringToInt(row.ID1)
      val id2 = convertStringToInt(row.ID2)
      mapping.put(getKey(osName, osVersionMajor, osVersionMiddle), VponOsFamilyIds(id1, id2))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $osfamilyMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new OsFamilyMappingException(err)
    }
  }

  def warmUp(): Unit = findVponOsFamilyIds("Android", "2.2.0")

  def findVponOsFamilyIds(osName: String, osVersion: String): VponOsFamilyIds = {
    val splitter = osName match {
      case "Android" => "\\."
      case "iOS" => "_"
      case _ => " "
    }
    val versionList = osVersion.split(splitter).toList ::: List(WILDCARD, WILDCARD)
    val firstTwoVersionItems = versionList.take(2)

    mapping.get(getKey(osName, firstTwoVersionItems.head, firstTwoVersionItems.last)) match {
      case Some(r) => r
      case None => mapping.get(getKey(osName, WILDCARD, WILDCARD)) match {
        case Some(s) => s
        case None => mapping.get(getKey(WILDCARD, WILDCARD, WILDCARD)) match {
          case Some(t) => t
          case None => {
            throw new OsFamilyMappingException("Missing (*, *, *) for Vpon Os Family Mapping")
          }
        }
      }
    }
  }

  private def getKey(osName: String, osVersionMajor: String, osVersionMiddle: String) = s"${osName}#${osVersionMajor}#${osVersionMiddle}"

}
