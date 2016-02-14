package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.TimezoneMappingException

case class VponTimezone(id: Int, description: String)

trait TimezoneMappingTrait {
  def findVponTimezoneById(id: Int): VponTimezone
  def findAllVponTimezones: Seq[VponTimezone]
}

object TimezoneMapping extends TimezoneMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(timezoneListPath)

  private case class TimezoneListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val timezoneListReader = RowReader(r => TimezoneListCsvRow(r(0), r(1)))

  private val timezoneMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[TimezoneListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      timezoneMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $timezoneListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new TimezoneMappingException(err)
    }
  }

  def warmUp(): Unit = findVponTimezoneById(1)

  override def findVponTimezoneById(id: Int): VponTimezone = {
    timezoneMap.get(id) match {
      case Some(description) => VponTimezone(id, description)
      case None => throw new TimezoneMappingException(s"Not found VponTimezone for id: $id")
    }
  }

  override def findAllVponTimezones: Seq[VponTimezone] = {
    (for (kv <- timezoneMap) yield VponTimezone(kv._1, kv._2)).toSeq
  }

}
