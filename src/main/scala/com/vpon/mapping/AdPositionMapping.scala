package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.AdPositionMappingException

case class VponAdPosition(id: Int, description: String)

trait AdPositionMappingTrait {
  def findVponAdPositionById(id: Int): VponAdPosition
  def findAllVponAdPositions: Seq[VponAdPosition]
}

object AdPositionMapping extends AdPositionMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(adPositionListPath)

  private case class AdPositionListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val adPositionListReader = RowReader(r => AdPositionListCsvRow(r(0), r(1)))

  private val adPositionMap = new scala.collection.mutable.HashMap[Int, String]()

  try {
    csv.rowsR[AdPositionListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      adPositionMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $adPositionListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new AdPositionMappingException(err)
    }
  }

  def warmUp(): Unit = findVponAdPositionById(0)

  override def findVponAdPositionById(id: Int): VponAdPosition = {
    adPositionMap.get(id) match {
      case Some(description) => VponAdPosition(id, description)
      case None => throw new AdPositionMappingException(s"NOT found VponAdPosition for id: $id")
    }
  }

  override def findAllVponAdPositions: Seq[VponAdPosition] = {
    (for (kv <- adPositionMap) yield VponAdPosition(kv._1, kv._2)).toSeq
  }

}
