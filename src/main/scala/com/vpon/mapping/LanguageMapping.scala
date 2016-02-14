package com.vpon.mapping


import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.LanguageMappingException

case class VponLanguage(id: Int, description: String)

trait LanguageMappingTrait {
  def warmUp(): Unit
  def findVponLanguageByLanguageCodeAndLocale(languageCode: String, locale: String): VponLanguage
  def findVponLanguageById(id: Int): VponLanguage
  def findAllVponLanguages(): Seq[VponLanguage]
}

object LanguageMapping extends LanguageMappingTrait with MappingConfig {

  private val listFile = classLoader.getResourceAsStream(languageListPath)
  private val mappingFile = classLoader.getResourceAsStream(languageMappingPath)

  private case class LanguageListCsvRow(ID: String, DESC_1_TIER: String)
  private case class LanguageMappingCsvRow(LANGUAGE_CODE: String, LOCALE: String, ID: String)

  private implicit val languageListReader = RowReader(r => LanguageListCsvRow(r(0), r(1)))
  private implicit val languageMappingReader = RowReader(r => LanguageMappingCsvRow(r(0), r(1), r(2)))

  private val languageMap = new scala.collection.mutable.HashMap[Int, VponLanguage]()
  try{
    csv.rowsR[LanguageListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      languageMap.put(id, VponLanguage(id, description))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $languageListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new LanguageMappingException(err)
    }
  }

  private val mapping = new scala.collection.mutable.HashMap[String, VponLanguage]()
  try{
    csv.rowsR[LanguageMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.LANGUAGE_CODE.equals("LANGUAGE_CODE")) break // skip HEADER
      val languageCode = row.LANGUAGE_CODE
      val locale = row.LOCALE
      val id = row.ID.toInt
      val key = getKey(languageCode, locale)
      val value = languageMap.get(id)
      value match {
        case Some(c) => mapping.put(key, c)
        case None => throw new LanguageMappingException(s"Not found VponLanguage for id ${id}")
      }
    })
  } catch {
    case e: LanguageMappingException => throw e
    case e: Throwable => {
      val err = s"Caught exception when parsing $languageMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new LanguageMappingException(err)
    }
  }

  private def getKey(languageCode: String, locale: String) = s"${languageCode.toLowerCase}-${locale.toLowerCase}"

  def warmUp(): Unit = findVponLanguageByLanguageCodeAndLocale("zh", "tw")

  override def findVponLanguageById(id: Int): VponLanguage = {
    languageMap.get(id) match {
      case Some(vl) => vl
      case None => throw new LanguageMappingException(s"Not found VponLanguage for id: ${id}")
    }
  }

  override def findVponLanguageByLanguageCodeAndLocale(languageCode: String, locale: String): VponLanguage = {
    mapping.get(getKey(languageCode.toLowerCase, locale.toLowerCase)) match {
      case Some(l) => l
      case None => mapping.get(getKey(languageCode.toLowerCase, WILDCARD)) match {
        case Some(g) => g
        case None => mapping.get(getKey(WILDCARD, WILDCARD)) match {
          case Some(k) => k
          case None => throw new LanguageMappingException("Missing (*, *) for VponLanguage Mapping")
        }
      }
    }
  }

  override def findAllVponLanguages(): Seq[VponLanguage] = languageMap.values.toSeq
}
