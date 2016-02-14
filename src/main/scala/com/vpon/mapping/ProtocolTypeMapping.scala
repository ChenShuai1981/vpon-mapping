package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.ProtocolTypeMappingException

case class VponProtocolType(id: Int, description: String)

trait ProtocolTypeMappingTrait {
  def findVponProtocolTypeById(id: Int): VponProtocolType
  def findAllVponProtocolTypes: Seq[VponProtocolType]
}

object ProtocolTypeMapping extends ProtocolTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(protocolTypeListPath)

  private case class ProtocolTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val protocolTypeListReader = RowReader(r => ProtocolTypeListCsvRow(r(0), r(1)))

  private val protocolTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[ProtocolTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      protocolTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $protocolTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new ProtocolTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponProtocolTypeById(1)

  override def findVponProtocolTypeById(id: Int): VponProtocolType = {
    protocolTypeMap.get(id) match {
      case Some(description) => VponProtocolType(id, description)
      case None => throw new ProtocolTypeMappingException(s"Not found VponProtocolType for id: $id")
    }
  }

  override def findAllVponProtocolTypes: Seq[VponProtocolType] = {
    (for (kv <- protocolTypeMap) yield VponProtocolType(kv._1, kv._2)).toSeq
  }

}
