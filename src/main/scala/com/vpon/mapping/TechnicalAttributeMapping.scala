package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.TechnicalAttributeMappingException

case class VponTechnicalAttribute(id: Int, description: String)

trait TechnicalAttributeMappingTrait {
  def findVponTechnicalAttributeById(id: Int): VponTechnicalAttribute
  def findAllVponTechnicalAttributes: Seq[VponTechnicalAttribute]
}

object TechnicalAttributeMapping extends TechnicalAttributeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(technicalAttributeListPath)

  private case class TechnicalAttributeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val technicalAttributeListReader = RowReader(r => TechnicalAttributeListCsvRow(r(0), r(1)))

  private val technicalAttributeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[TechnicalAttributeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      technicalAttributeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $technicalAttributeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new TechnicalAttributeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponTechnicalAttributeById(1)

  override def findVponTechnicalAttributeById(id: Int): VponTechnicalAttribute = {
    technicalAttributeMap.get(id) match {
      case Some(description) => VponTechnicalAttribute(id, description)
      case None => throw new TechnicalAttributeMappingException(s"Not found VponTechnicalAttribute for id: $id")
    }
  }

  override def findAllVponTechnicalAttributes: Seq[VponTechnicalAttribute] = {
    (for (kv <- technicalAttributeMap) yield VponTechnicalAttribute(kv._1, kv._2)).toSeq
  }

}
