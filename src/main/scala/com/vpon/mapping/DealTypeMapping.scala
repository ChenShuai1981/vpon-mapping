package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.DealTypeMappingException

case class VponDealType(id: Int, description: String)

trait DealTypeMappingTrait {
  def findVponDealTypeById(id: Int): VponDealType
  def findAllVponDealTypes: Seq[VponDealType]
}

object DealTypeMapping extends DealTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(dealTypeListPath)

  private case class DealTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val dealTypeListReader = RowReader(r => DealTypeListCsvRow(r(0), r(1)))

  private val dealTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[DealTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      dealTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $dealTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new DealTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponDealTypeById(0)

  override def findVponDealTypeById(id: Int): VponDealType = {
    dealTypeMap.get(id) match {
      case Some(description) => VponDealType(id, description)
      case None => throw new DealTypeMappingException(s"NOT found VponDealType for id: $id")
    }
  }

  override def findAllVponDealTypes: Seq[VponDealType] = {
    (for (kv <- dealTypeMap) yield VponDealType(kv._1, kv._2)).toSeq
  }

}
