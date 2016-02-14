package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.PlatformTypeMappingException

case class VponPlatformType(id: Int, description: String)

trait PlatformTypeMappingTrait {
  def findVponPlatformTypeById(id: Int): VponPlatformType
  def findAllVponPlatformTypes: Seq[VponPlatformType]
}

object PlatformTypeMapping extends PlatformTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(platformTypeListPath)

  private case class PlatformTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val platformTypeListReader = RowReader(r => PlatformTypeListCsvRow(r(0), r(1)))

  private val platformTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[PlatformTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      platformTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $platformTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new PlatformTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponPlatformTypeById(1)

  override def findVponPlatformTypeById(id: Int): VponPlatformType = {
    platformTypeMap.get(id) match {
      case Some(description) => VponPlatformType(id, description)
      case None => throw new PlatformTypeMappingException(s"Not found VponPlatformType for id: $id")
    }
  }

  override def findAllVponPlatformTypes: Seq[VponPlatformType] = {
    (for (kv <- platformTypeMap) yield VponPlatformType(kv._1, kv._2)).toSeq
  }

}
