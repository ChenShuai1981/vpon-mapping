package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.GenderTypeMappingException

case class VponGenderType(id: Int, description: String)

trait GenderTypeMappingTrait {
  def findVponGenderTypeById(id: Int): VponGenderType
  def findAllVponGenderTypes: Seq[VponGenderType]
}

object GenderTypeMapping extends GenderTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(genderTypeListPath)

  private case class GenderTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val genderTypeListReader = RowReader(r => GenderTypeListCsvRow(r(0), r(1)))

  private val genderTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[GenderTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      genderTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $genderTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new GenderTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponGenderTypeById(0)

  override def findVponGenderTypeById(id: Int): VponGenderType = {
    genderTypeMap.get(id) match {
      case Some(description) => VponGenderType(id, description)
      case None => throw new GenderTypeMappingException(s"NOT found VponGenderType for id: $id")
    }
  }

  override def findAllVponGenderTypes: Seq[VponGenderType] = {
    (for (kv <- genderTypeMap) yield VponGenderType(kv._1, kv._2)).toSeq
  }

}
