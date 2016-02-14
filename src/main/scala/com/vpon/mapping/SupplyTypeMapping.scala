package com.vpon.mapping


import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.SupplyTypeMappingException

case class VponSupplyType(id: Int, description: String)

trait SupplyTypeMappingTrait {
  def warmUp(): Unit
  def findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType(uaPrimaryHardwareType: String, sdk_st: String): VponSupplyType
  def findVponSupplyTypeById(id: Int): VponSupplyType
  def findAllVponSupplyTypes(): Seq[VponSupplyType]
}

object SupplyTypeMapping extends SupplyTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(supplyTypeListPath)
  private val mappingFile = classLoader.getResourceAsStream(supplyTypeMappingPath)

  private case class SupplyTypeListCsvRow(ID: String, DESC_1_TIER: String, WEB_DISPLAY_ORDER: String)
  private case class SupplyTypeMappingCsvRow(UA_PRIMARY_HARDWARE_TYPE: String, SDK: String, ID: String, IGNORE_DESC_1_TIER: String)

  private implicit val supplyTypeListReader = RowReader(r => SupplyTypeListCsvRow(r(0), r(1), r(2)))
  private implicit val supplyTypeMappingReader = RowReader(r => SupplyTypeMappingCsvRow(r(0), r(1), r(2), r(3)))

  private val supplyTypeMap = new scala.collection.mutable.HashMap[Int, VponSupplyType]()
  try {
    csv.rowsR[SupplyTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      supplyTypeMap.put(id, VponSupplyType(id, description))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $supplyTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new SupplyTypeMappingException(err)
    }
  }

  private val mapping = new scala.collection.mutable.HashMap[String, VponSupplyType]()
  try {
    csv.rowsR[SupplyTypeMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.UA_PRIMARY_HARDWARE_TYPE.equals("UA_PRIMARY_HARDWARE_TYPE")) break // skip HEADER
      val uaPrimaryHardwareType = row.UA_PRIMARY_HARDWARE_TYPE
      val id = row.ID.toInt
      val sdk_st = row.SDK
      val key = getKey(uaPrimaryHardwareType, sdk_st)
      val value = supplyTypeMap.get(id)
      value match {
        case Some(c) => mapping.put(key, c)
        case None => throw new SupplyTypeMappingException(s"Not found Vpon Supply Type for id ${id}")
      }
    })
  } catch {
    case e: Throwable => throw new SupplyTypeMappingException(s"Caught exception when parsing $supplyTypeMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}")
  }

  private def getKey(uaPrimaryHardwareType: String, sdk_st: String) = s"${uaPrimaryHardwareType}#${sdk_st}"

  def warmUp(): Unit = findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Desktop", "1")

  override def findVponSupplyTypeById(id: Int): VponSupplyType = {
    supplyTypeMap.get(id) match {
      case Some(st) => st
      case None => throw new SupplyTypeMappingException(s"Not found VponSupplyType for id: ${id}")
    }
  }

  override def findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType(uaPrimaryHardwareType: String, sdk_st: String): VponSupplyType = {
    mapping.get(getKey(uaPrimaryHardwareType, sdk_st)) match {
      case Some(st) => st
      case None => {
        mapping.get(getKey(uaPrimaryHardwareType, WILDCARD)) match {
          case Some(st2) => st2
          case None => mapping.get(getKey(WILDCARD, WILDCARD)) match {
            case Some(st3) => st3
            case None => throw new SupplyTypeMappingException("Missing (*, *) pair mapping in Vpon Supply Type Mapping")
          }
        }
      }
    }
  }

  override def findAllVponSupplyTypes(): Seq[VponSupplyType] = supplyTypeMap.values.toSeq
}
