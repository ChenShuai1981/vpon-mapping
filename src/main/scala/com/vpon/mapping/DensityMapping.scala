package com.vpon.mapping

import scala.annotation.tailrec
import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.jexl2.{MapContext, JexlEngine}
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.DensityMappingException

case class VponDensity(id: Int, description: String)

trait DensityMappingTrait {
  def warmUp(): Unit
  def findVponDensityByUserScreenDensity(u_sd: Option[Double]): VponDensity
  def findVponDensityById(id: Int): VponDensity
  def findAllVponDensities(): Seq[VponDensity]
}

object DensityMapping extends DensityMappingTrait with MappingConfig {
  private val listFile = classLoader.getResourceAsStream(densityListPath)
  private val mappingFile = classLoader.getResourceAsStream(densityMappingPath)

  private case class DensityListCsvRow(ID: String, DESC_1_TIER: String)
  private case class DensityMappingCsvRow(u_sd: String, ID: String)

  private implicit val densityListReader = RowReader(r => DensityListCsvRow(r(0), r(1)))
  private implicit val densityMappingReader = RowReader(r => DensityMappingCsvRow(r(0), r(1)))

  private var notAvailableDensity: Option[VponDensity] = None

  private val jexl = new JexlEngine
  jexl.setSilent(true)
  jexl.setLenient(true)

  private val densityMap = new scala.collection.mutable.HashMap[Int, VponDensity]()
  try {
    csv.rowsR[DensityListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val description = row.DESC_1_TIER
      densityMap.put(id, VponDensity(id, description))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $densityListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new DensityMappingException(err)
    }
  }

  private val mapping = new scala.collection.mutable.HashMap[String, VponDensity]()
  try{
    csv.rowsR[DensityMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.u_sd.equals("u_sd")) break // skip HEADER
      if (row.u_sd.equals(NOTAVAILABLE)) {
        notAvailableDensity = densityMap.get(row.ID.toInt)
        break
      }
      val id = row.ID.toInt
      val u_sd_exp = row.u_sd
      val value = densityMap.get(id)
      value match {
        case Some(c) => mapping.put(u_sd_exp, c)
        case None => throw new DensityMappingException(s"Not found VponDensity for id ${id}")
      }
    })
  } catch {
    case e: DensityMappingException => throw e
    case e: Throwable => {
      val err = s"Caught exception when parsing $densityMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new DensityMappingException(err)
    }
  }

  def warmUp(): Unit = findVponDensityByUserScreenDensity(Some(1.2f))

  override def findAllVponDensities(): Seq[VponDensity] = {
    densityMap.values.toSeq
  }

  override def findVponDensityById(id: Int): VponDensity = {
    densityMap.get(id) match {
      case Some(d) => d
      case None => throw new DensityMappingException(s"NOT found VponDensity for id: $id")
    }
  }

  /**
   *
   * @param u_sd
   * @return VponDensity
   */
  override def findVponDensityByUserScreenDensity(u_sd: Option[Double]): VponDensity = {
    @tailrec
    def func(maps: List[String], u_sd: Double): VponDensity = {
      maps match {
        case Nil => throw new DensityMappingException("Not found VponDensity")
        case exp :: s => {
          val expression = jexl.createExpression(s"i ${exp}")
          val jexlContext = new MapContext
          jexlContext.set("i", u_sd)
          if (expression.evaluate(jexlContext).asInstanceOf[Boolean]) {
            mapping.get(exp) match {
              case Some(m) => m
              case None => throw new DensityMappingException("Found a VponDensity in mapping but missing data in list")
            }
          } else {
            (func(s, u_sd))
          }
        }
      }
    }

    u_sd match {
      case Some(usd) => func(mapping.keys.toList, usd)
      case None => notAvailableDensity match {
        case Some(d) => d
        case None => throw new DensityMappingException("Missing n/a mapping for Vpon Density")
      }
    }
  }
}
