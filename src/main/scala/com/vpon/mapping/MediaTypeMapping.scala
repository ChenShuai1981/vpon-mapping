package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.MediaTypeMappingException

case class VponMediaType(mainMediaId: Int, subMediaId: Int, mainMediaDescription: String, subMediaDescription: String)

trait MediaTypeMappingTrait {
  def findVponMediaTypeByIds(mainMediaId: Int, subMediaId: Int): VponMediaType
  def findAllVponMediaTypes: Seq[VponMediaType]
}

object MediaTypeMapping extends MediaTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(mediaTypeListPath)

  private case class MediaTypeListCsvRow(ID1: String, ID2: String, DESC_1_TIER: String, DESC_2_TIER: String)

  private implicit val mediaTypeListReader = RowReader(r => MediaTypeListCsvRow(r(0), r(1), r(2), r(3)))

  private val mediaTypeMap = new scala.collection.mutable.HashMap[(Int, Int), (String, String)]()
  try {
    csv.rowsR[MediaTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID1.equals("ID1")) break // skip HEADER
      val mainMediaId = row.ID1.toInt
      val subMediaId = row.ID2.toInt
      val mainMediaDescription = row.DESC_1_TIER
      val subMediaDescription = row.DESC_2_TIER
      mediaTypeMap.put((mainMediaId, subMediaId), (mainMediaDescription, subMediaDescription))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $mediaTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new MediaTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponMediaTypeByIds(1, 1)

  override def findVponMediaTypeByIds(mainMediaId: Int, subMediaId: Int): VponMediaType = {
    mediaTypeMap.get((mainMediaId, subMediaId)) match {
      case Some((mainMediaDescription, subMediaDescription)) => VponMediaType(mainMediaId, subMediaId, mainMediaDescription, subMediaDescription)
      case None => {
        val err = s"NOT found VponMediaType for mainMediaId -> $mainMediaId and subMediaId -> $subMediaId"
        throw new MediaTypeMappingException(err)
      }
    }
  }

  override def findAllVponMediaTypes: Seq[VponMediaType] = {
    (for (kv <- mediaTypeMap) yield VponMediaType(kv._1._1, kv._1._2, kv._2._1, kv._2._2)).toSeq
  }

}
