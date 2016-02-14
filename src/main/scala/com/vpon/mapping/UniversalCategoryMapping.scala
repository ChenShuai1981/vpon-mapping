package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.UniversalCategoryMappingException

case class VponUniversalCategory(id: Int, category: String, description: String, openRTBID: String)

trait UniversalCategoryMappingTrait {
  def findVponUniversalCategoryById(id: Int): VponUniversalCategory
  def findAllVponUniversalCategories: Seq[VponUniversalCategory]
}

object UniversalCategoryMapping extends UniversalCategoryMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(universalCategoryListPath)

  private case class UniversalCategoryListCsvRow(ID: String, DESC_1_TIER: String, DESC_2_TIER: String, IGNORE_OPENRTB_ID: String)

  private implicit val universalCategoryListReader = RowReader(r => UniversalCategoryListCsvRow(r(0), r(1), r(2), r(3)))

  private val universalCategoryMap = new scala.collection.mutable.HashMap[Int/* id */, String/* openRTBID */]()
  private val openRTBMap = new scala.collection.mutable.HashMap[String/* openRTBID */, (String/* category */, String/* description */)]()
  try {
    csv.rowsR[UniversalCategoryListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val category = row.DESC_1_TIER
      val description = row.DESC_2_TIER
      val openRTBID = row.IGNORE_OPENRTB_ID
      universalCategoryMap.put(id, openRTBID)
      openRTBMap.put(openRTBID, (category, description))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $universalCategoryListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new UniversalCategoryMappingException(err)
    }
  }

  def warmUp(): Unit = findVponUniversalCategoryById(1)

  override def findVponUniversalCategoryById(id: Int): VponUniversalCategory = {
    universalCategoryMap.get(id) match {
      case Some(openRTBID) => openRTBMap.get(openRTBID) match {
        case Some(t) => VponUniversalCategory(id, t._1, t._2, openRTBID)
        case None => {
          val err = s"Not found VponUniversalCategory (openRTBID -> (category, description)) for openRTBID: $openRTBID"
          throw new UniversalCategoryMappingException(err)
        }
      }
      case None => throw new UniversalCategoryMappingException(s"Not found VponUniversalCategory for id: $id")
    }
  }

  override def findAllVponUniversalCategories: Seq[VponUniversalCategory] = {
    (for (kv <- universalCategoryMap) yield {
      val id = kv._1
      val openRTBID = kv._2
      openRTBMap.get(openRTBID) match {
        case Some(t) => VponUniversalCategory(id, t._1, t._2, openRTBID)
        case None => {
          val err = s"Not found VponUniversalCategory (openRTBID -> (category, description)) for openRTBID: $openRTBID"
          throw new UniversalCategoryMappingException(err)
        }
      }
    }).toSeq
  }

}
