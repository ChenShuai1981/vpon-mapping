package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.PublisherRevenueShareTypeMappingException

case class VponPublisherRevenueShareType(id: Int, description: String)

trait PublisherRevenueShareTypeMappingTrait {
  def findVponPublisherRevenueShareTypeById(id: Int): VponPublisherRevenueShareType
  def findAllVponPublisherRevenueShareTypes: Seq[VponPublisherRevenueShareType]
}

object PublisherRevenueShareTypeMapping extends PublisherRevenueShareTypeMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(publisherRevenueShareTypeListPath)

  private case class PublisherRevenueShareTypeListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val publisherRevenueShareTypeListReader = RowReader(r => PublisherRevenueShareTypeListCsvRow(r(0), r(1)))

  private val publisherRevenueShareTypeMap = new scala.collection.mutable.HashMap[Int, String]()
  try {
    csv.rowsR[PublisherRevenueShareTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      publisherRevenueShareTypeMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $publisherRevenueShareTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new PublisherRevenueShareTypeMappingException(err)
    }
  }

  def warmUp(): Unit = findVponPublisherRevenueShareTypeById(1)

  override def findVponPublisherRevenueShareTypeById(id: Int): VponPublisherRevenueShareType = {
    publisherRevenueShareTypeMap.get(id) match {
      case Some(description) => VponPublisherRevenueShareType(id, description)
      case None => throw new PublisherRevenueShareTypeMappingException(s"Not found VponPublisherRevenueShareType for id: $id")
    }
  }

  override def findAllVponPublisherRevenueShareTypes: Seq[VponPublisherRevenueShareType] = {
    (for (kv <- publisherRevenueShareTypeMap) yield VponPublisherRevenueShareType(kv._1, kv._2)).toSeq
  }

}
