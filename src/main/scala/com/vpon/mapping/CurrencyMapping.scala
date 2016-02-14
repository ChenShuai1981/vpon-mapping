package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.CurrencyMappingException

case class VponCurrency(id: String, description: String)

trait CurrencyMappingTrait {
  def findVponCurrencyById(id: String): VponCurrency
  def findAllVponCurrencies(): Seq[VponCurrency]
}

object CurrencyMapping extends CurrencyMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(currencyListPath)

  private case class CurrencyListCsvRow(ID: String, DESC_1_TIER: String)

  private implicit val currencyListReader = RowReader(r => CurrencyListCsvRow(r(0), r(1)))

  private val currencyMap = new scala.collection.mutable.HashMap[String, String]()
  try {
    csv.rowsR[CurrencyListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID
      val description = row.DESC_1_TIER
      currencyMap.put(id, description)
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $currencyListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new CurrencyMappingException(err)
    }
  }

  def warmUp(): Unit = findVponCurrencyById("CNY")

  override def findVponCurrencyById(id: String): VponCurrency = {
    currencyMap.get(id) match {
      case Some(description) => VponCurrency(id, description)
      case None => throw new CurrencyMappingException(s"NOT found VponCurrency for id: $id")
    }
  }

  override def findAllVponCurrencies: Seq[VponCurrency] = {
    (for (kv <- currencyMap) yield VponCurrency(kv._1, kv._2)).toSeq
  }

}
