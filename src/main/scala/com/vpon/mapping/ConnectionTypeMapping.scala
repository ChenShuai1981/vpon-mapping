package com.vpon.mapping


import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.apache.commons.lang3.exception.ExceptionUtils

import com.vpon.mapping.config.MappingConfig
import com.vpon.mapping.exception.ConnectionTypeMappingException

case class VponConnectionType(id: Int, connectionType: String)

trait ConnectionTypeMappingTrait {
  def warmUp(): Unit
  def findVponConnectionTypeByCxntAndTelt(cxnt: String, telt: String): VponConnectionType
  def findVponConnectionTypeById(id: Int): VponConnectionType
  def findAllVponConnectionTypes(): Seq[VponConnectionType]
}

object ConnectionTypeMapping extends ConnectionTypeMappingTrait with MappingConfig {

  private val listFile = classLoader.getResourceAsStream(connectionTypeListPath)
  private val mappingFile = classLoader.getResourceAsStream(connectionTypeMappingPath)

  case class ConnectionTypeListCsvRow(ID: String, DESC_1_TIER: String)
  case class ConnectionTypeMappingCsvRow(CXNT: String, TELT: String, ID: String)

  implicit val connectionTypeListReader = RowReader(r => ConnectionTypeListCsvRow(r(0), r(1)))
  implicit val connectionTypeMappingReader = RowReader(r => ConnectionTypeMappingCsvRow(r(0), r(1), r(2)))
  private val connectionTypeMap = new scala.collection.mutable.HashMap[Int, VponConnectionType]()
  try {
    csv.rowsR[ConnectionTypeListCsvRow](listFile, ';').foreach(row => breakable {
      if (row.ID.equals("ID")) break // skip HEADER
      val id = row.ID.toInt
      val connectionType = row.DESC_1_TIER
      connectionTypeMap.put(id, VponConnectionType(id, connectionType))
    })
  } catch {
    case e: Throwable => {
      val err = s"Caught exception when parsing $connectionTypeListPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new ConnectionTypeMappingException(err)
    }
  }

  private val mapping = new scala.collection.mutable.HashMap[String, VponConnectionType]()
  try {
    csv.rowsR[ConnectionTypeMappingCsvRow](mappingFile, ';').foreach(row => breakable {
      if (row.CXNT.equals("CXNT")) break // skip HEADER
      val id = row.ID.toInt
      val cxnt = row.CXNT
      val telt = row.TELT
      val key = getKey(cxnt, telt)
      val value = connectionTypeMap.get(id)
      value match {
        case Some(c) => mapping.put(key, c)
        case None => throw new ConnectionTypeMappingException(s"Not found VponConnectionType for id: ${id}")
      }
    })
  } catch {
    case e: ConnectionTypeMappingException => throw e
    case e: Throwable => {
      val err = s"Caught exception when parsing $connectionTypeMappingPath file. ${e.getMessage}\n${ExceptionUtils.getStackTrace(e)}"
      throw new ConnectionTypeMappingException(err)
    }
  }

  private def getKey(cxnt: String, telt: String) = s"${cxnt}#${telt}"

  def warmUp(): Unit = findVponConnectionTypeByCxntAndTelt("1", "1")

  override def findAllVponConnectionTypes(): Seq[VponConnectionType] = {
    connectionTypeMap.values.toSeq
  }

  override def findVponConnectionTypeById(id: Int): VponConnectionType = {
    connectionTypeMap.get(id) match {
      case Some(ct) => ct
      case None => throw new ConnectionTypeMappingException(s"NOT found VponConnectionType for id: $id")
    }
  }

  /**
   *
   * @param cxnt
   * @param telt
   * @return VponConnectionType
   */
  override def findVponConnectionTypeByCxntAndTelt(cxnt: String, telt: String): VponConnectionType = {
    mapping.get(getKey(cxnt, telt)) match {
      case Some(c) => c
      case None => mapping.get(getKey(cxnt, WILDCARD)) match {
        case Some(r) => r
        case None => mapping.get(getKey(WILDCARD, telt)) match {
          case Some(t) => t
          case None => mapping.get(getKey(WILDCARD, WILDCARD)) match {
            case Some(v) => v
            case None => throw new ConnectionTypeMappingException("Missing (*, *) pair mapping in Vpon ConnectionType Mapping")
          }
        }
      }
    }
  }

}
