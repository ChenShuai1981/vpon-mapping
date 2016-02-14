package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.CarrierMappingException

trait CarrierMappingTrait {
  def warmUp(): Unit
  def findVponCarrierByMccAndMnc(mcc: String, mnc: String): VponCarrier
  def findVponCarrierById(id: Int): VponCarrier
  def findAllVponCarriers(): Seq[VponCarrier]
}

case class VponCarrier(id: Int, country: String, name: String)

object CarrierMapping extends CarrierMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(carrierListPath)
  private val mappingFile = classLoader.getResourceAsStream(carrierMappingPath)

  private case class CarrierListCsvRow(ID: String, DESC_1_TIER: String, DESC_2_TIER: String, DISPLAY_ORDER: String)
  private case class CarrierMappingCsvRow(MCC: String, MNC: String, ID: String)

  private implicit val carrierListReader = RowReader(r => CarrierListCsvRow(r(0), r(1), r(2), r(3)))
  private implicit val carrierMappingReader = RowReader(r => CarrierMappingCsvRow(r(0), r(1), r(2)))

  private val carrierMap = new scala.collection.mutable.HashMap[Int, VponCarrier]()
  try {
    csv.rowsR[CarrierListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val country = row.DESC_1_TIER
      val name = row.DESC_2_TIER
      carrierMap.put(id, VponCarrier(id, country, name))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $carrierListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new CarrierMappingException(err)
    }
  }

  private val mapping = new scala.collection.mutable.HashMap[String, VponCarrier]()
  try {
    csv.rowsR[CarrierMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.MCC.equals("MCC")) break // skip HEADER
      val id = row.ID.toInt
      val mcc = row.MCC
      val mnc = row.MNC
      val key = getKey(mcc, mnc)
      val value = carrierMap.get(id)
      value match {
        case Some(c) => mapping.put(key, c)
        case None => throw new CarrierMappingException(s"Not found Vpon Carrier for id ${id}")
      }
    })
  } catch {
    case e: CarrierMappingException => throw e
    case e: Throwable => {
      val err = s"Caught exception when parsing $carrierMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new CarrierMappingException(err)
    }
  }

  private def getKey(mcc: String, mnc: String) = s"${mcc}#${mnc}"

  def warmUp(): Unit = findVponCarrierByMccAndMnc("466", "01")

  override def findAllVponCarriers(): Seq[VponCarrier] = {
    carrierMap.values.toSeq
  }

  override def findVponCarrierById(id: Int): VponCarrier = {
    carrierMap.get(id) match {
      case Some(vc) => vc
      case None => throw new CarrierMappingException(s"NOT found VponCarrier for id: $id")
    }
  }

  /**
   * Mapping to VponCarrier by given mcc and mnc
   * @param mcc
   * @param mnc
   * @return VponCarrier
   */
  override def findVponCarrierByMccAndMnc(mcc: String, mnc: String): VponCarrier = {
    mapping.get(getKey(mcc, mnc)) match {
      case Some(c) => c
      case None => mapping.get(getKey(mcc, WILDCARD)) match {
        case Some(r) => r
        case None => mapping.get(getKey(WILDCARD, WILDCARD)) match {
          case Some(t) => t
          case None => throw new CarrierMappingException("Missing (*, *) pair mapping in Vpon Carrier Mapping")
        }
      }
    }
  }
}
