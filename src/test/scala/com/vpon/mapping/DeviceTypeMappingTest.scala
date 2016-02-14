package com.vpon.mapping

import scala.util.control.Breaks._

import com.nrinaudo.csv
import com.nrinaudo.csv.RowReader
import org.scalatest.{BeforeAndAfterAll, MustMatchers, FlatSpec}

import com.vpon.mapping.config.{TestMappingConfig, MappingConfig}

class DeviceTypeMappingTest extends FlatSpec with MustMatchers with BeforeAndAfterAll with TestMappingConfig {

  override def beforeAll() = {
    val cacheSpec = s"maximumSize=100,initialCapacity=100,concurrencyLevel=8"
    DeviceTypeMapping.init(cacheSpec)
  }

  it must "find Vpon Device Type properly by primary hardware type" in {
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Desktop") must be(Some(VponDeviceType(3, "Desktops & Laptops")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Tablet") must be(Some(VponDeviceType(2, "Tablets")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("eReader") must be(Some(VponDeviceType(2, "Tablets")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Mobile Phone") must be(Some(VponDeviceType(1, "Phones")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("TV") must be(Some(VponDeviceType(4, "Smart TV")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Games Console") must be(Some(VponDeviceType(4, "Smart TV")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Set Top Box") must be(Some(VponDeviceType(4, "Smart TV")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Media Player") must be(Some(VponDeviceType(0, "Others / Unknown")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Plug-in Modem") must be(Some(VponDeviceType(0, "Others / Unknown")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Embedded Network Module") must be(Some(VponDeviceType(0, "Others / Unknown")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Camera") must be(Some(VponDeviceType(0, "Others / Unknown")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Wireless Hotspot") must be(Some(VponDeviceType(0, "Others / Unknown")))
    // 'Watch' is NOT existed!
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Watch") must be(Some(VponDeviceType(0, "Others / Unknown")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Wristwatch") must be(Some(VponDeviceType(5, "Wearables")))
    DeviceTypeMapping.findVponDeviceTypeByPrimaryHardwareType("Glasses") must be(Some(VponDeviceType(5, "Wearables")))
  }

  it must "find Vpon Device Type properly by user agent string" in {
    val testFile = classLoader.getResourceAsStream(osfamilyMappingTestPath)
    case class osFamilyTestCsvRow(UA: String, ID_1: String, ID_2: String)
    implicit val osFamilyTestReader = RowReader(r => osFamilyTestCsvRow(r(0), r(1), r(2)))
    csv.rowsR[osFamilyTestCsvRow](testFile, ';').foreach(row => breakable {
      if (row.UA.equals("UA")) break // skip HEADER
      val ua = row.UA
      val id1 = convertStringToInt(row.ID_1)
      val id2 = convertStringToInt(row.ID_2)
      val deviceObject = DeviceTypeMapping.findVponDeviceByUA(ua)
      deviceObject.vponDeviceOsIds must be(Seq(id1, id2).flatten)
    })
  }

  it must "given user agent string, get DeviceObject properly" in {
    val ua = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36"
    val vponDevice = DeviceTypeMapping.findVponDeviceByUA(ua)
    vponDevice.make must be(Some("Apple"))
    vponDevice.model must be(None)
    vponDevice.osName must be(Some("OS X"))
    vponDevice.osVersion must be(Some("10_9_5"))
    vponDevice.primaryHardwareType must be(Some("Desktop"))
    vponDevice.vponDeviceTypeId must be(3)
    vponDevice.vponDeviceOsIds must be(Seq(0))
  }

  it must "find all VponDeviceTypes properly" in {
    DeviceTypeMapping.findAllVponDeviceTypes().size must be(6)
  }

  it must "provide warmUp method" in {
    try {
      DeviceTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
