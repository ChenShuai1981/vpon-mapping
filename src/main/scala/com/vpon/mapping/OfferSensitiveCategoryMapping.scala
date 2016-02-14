package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.OfferSensitiveCategoryMappingException

case class VponOfferSensitiveCategory(id: String, description: String, category: String)

trait OfferSensitiveCategoryMappingTrait {
  def findVponOfferSensitiveCategoryById(id: String): VponOfferSensitiveCategory
  def findVponOfferSensitiveCategoriesByCategory(category: String): Seq[VponOfferSensitiveCategory]
  def findAllVponOfferSensitiveCategories: Seq[VponOfferSensitiveCategory]
}

object OfferSensitiveCategoryMapping extends OfferSensitiveCategoryMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(offerSensitiveCategoryListPath)

  private case class OfferSensitiveCategoryListCsvRow(ID: String, DESC_1_TIER: String, IGNORE_TABLE_TYPE: String)

  private implicit val offerSensitiveCategoryListReader = RowReader(r => OfferSensitiveCategoryListCsvRow(r(0), r(1), r(2)))

  private val idToDescMap = new scala.collection.mutable.HashMap[String/* ID */, String/* DESC */]()
  private val idToCategoryMap = new scala.collection.mutable.HashMap[String/* ID */, String/* Category */]()
  private val categoryToIDMap = new scala.collection.mutable.HashMap[String/* Category */, scala.collection.mutable.ArrayBuffer[String]/* ID */]()
  try {
    csv.rowsR[OfferSensitiveCategoryListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID
      val description = row.DESC_1_TIER
      val category = row.IGNORE_TABLE_TYPE
      idToDescMap.put(id, description)
      idToCategoryMap.put(id, category)
      categoryToIDMap.getOrElseUpdate(category, scala.collection.mutable.ArrayBuffer[String]()) += id
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $offerSensitiveCategoryListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new OfferSensitiveCategoryMappingException(err)
    }
  }

  def warmUp(): Unit = findVponOfferSensitiveCategoryById("001")

  override def findVponOfferSensitiveCategoryById(id: String): VponOfferSensitiveCategory = {
    idToDescMap.get(id) match {
      case Some(description) => idToCategoryMap.get(id) match {
        case Some(category) => VponOfferSensitiveCategory(id, description, category)
        case None => throw new OfferSensitiveCategoryMappingException(s"NOT found VponOfferSensitiveCategory | category for id: $id")
      }
      case None => throw new OfferSensitiveCategoryMappingException(s"NOT found VponOfferSensitiveCategory | description for id: $id")
    }
  }

  override def findVponOfferSensitiveCategoriesByCategory(category: String): Seq[VponOfferSensitiveCategory] = {
    categoryToIDMap.get(category) match {
      case Some(ids) => for(id <- ids) yield {
        idToDescMap.get(id) match {
          case Some(description) => VponOfferSensitiveCategory(id, description, category)
          case None => throw new OfferSensitiveCategoryMappingException(s"NOT found VponOfferSensitiveCategory | description for id: $id")
        }
      }
      case None => throw new OfferSensitiveCategoryMappingException(s"NOT found VponOfferSensitiveCategory category: $category")
    }
  }

  override def findAllVponOfferSensitiveCategories: Seq[VponOfferSensitiveCategory] = {
    (for (kv <- idToDescMap) yield {
      val id = kv._1
      val description = kv._2
      idToCategoryMap.get(id) match {
        case Some(category) => VponOfferSensitiveCategory(id, description, category)
        case None => throw new OfferSensitiveCategoryMappingException(s"NOT found VponOfferSensitiveCategory | category for id: $id")
      }
    }).toSeq
  }

}
