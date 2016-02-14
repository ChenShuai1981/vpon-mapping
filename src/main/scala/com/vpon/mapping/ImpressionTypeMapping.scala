package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.ImpressionTypeMappingException

case class VponImpressionType(id: Int, description: String)

trait ImpressionTypeMappingTrait {
  def findVponImpressionTypeById(id: Int): VponImpressionType
  def findAllVponImpressionTypes: Seq[VponImpressionType]
}

object ImpressionTypeMapping extends ImpressionTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(impressionTypeListPath)

  private case class ImpressionTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val impressionTypeListReader = RowReader(r => ImpressionTypeListCsvRow(r(0), r(1)))

  private val impressionTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[ImpressionTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      impressionTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $impressionTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new ImpressionTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponImpressionTypeById(1)

  override def findVponImpressionTypeById(id: Int): VponImpressionType = {
    impressionTypeMap.get(id) match {
      case Some(description) => VponImpressionType(id, description)
      case None => throw new ImpressionTypeMappingException(s"NOT found VponImpressionType for id: $id")
    }
  }

  override def findAllVponImpressionTypes: Seq[VponImpressionType] = {
    (for (kv <- impressionTypeMap) yield VponImpressionType(kv._1, kv._2)).toSeq
  }

}
