package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.SellerRevenueShareTypeMappingException

case class VponSellerRevenueShareType(id: Int, description: String)

trait SellerRevenueShareTypeMappingTrait {
  def findVponSellerRevenueShareTypeById(id: Int): VponSellerRevenueShareType
  def findAllVponSellerRevenueShareTypes: Seq[VponSellerRevenueShareType]
}

object SellerRevenueShareTypeMapping extends SellerRevenueShareTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(sellerRevenueShareTypeListPath)

  private case class SellerRevenueShareTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val sellerRevenueShareTypeListReader = RowReader(r => SellerRevenueShareTypeListCsvRow(r(0), r(1)))

  private val sellerRevenueShareTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[SellerRevenueShareTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      sellerRevenueShareTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $sellerRevenueShareTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new SellerRevenueShareTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponSellerRevenueShareTypeById(1)

  override def findVponSellerRevenueShareTypeById(id: Int): VponSellerRevenueShareType = {
    sellerRevenueShareTypeMap.get(id) match {
      case Some(description) => VponSellerRevenueShareType(id, description)
      case None => throw new SellerRevenueShareTypeMappingException(s"Not found VponSellerRevenueShareType for id: $id")
    }
  }

  override def findAllVponSellerRevenueShareTypes: Seq[VponSellerRevenueShareType] = {
    (for (kv <- sellerRevenueShareTypeMap) yield VponSellerRevenueShareType(kv._1, kv._2)).toSeq
  }

}
