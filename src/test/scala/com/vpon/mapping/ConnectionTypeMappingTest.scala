package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

import com.vpon.mapping.exception.ConnectionTypeMappingException

class ConnectionTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find VponConnectionType properly by CXNT and TELT" in {
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("0", "1") must be(VponConnectionType(0, "Unknown"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("0", "3") must be(VponConnectionType(0, "Unknown"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("1", "1") must be(VponConnectionType(1, "Wifi"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("1", "3") must be(VponConnectionType(1, "Wifi"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("2", "1") must be(VponConnectionType(3, "Cellular data - 2G"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("2", "2") must be(VponConnectionType(4, "Cellular data - 3G"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("2", "3") must be(VponConnectionType(5, "Cellular data - 4G"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("2", "5") must be(VponConnectionType(2, "Cellular data - Unknown Generation"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("3", "1") must be(VponConnectionType(0, "Unknown"))
    ConnectionTypeMapping.findVponConnectionTypeByCxntAndTelt("3", "3") must be(VponConnectionType(0, "Unknown"))
  }

  it must "find VponConnectionType properly by id" in {
    ConnectionTypeMapping.findVponConnectionTypeById(0) must be(VponConnectionType(0, "Unknown"))
    ConnectionTypeMapping.findVponConnectionTypeById(1) must be(VponConnectionType(1, "Wifi"))
    ConnectionTypeMapping.findVponConnectionTypeById(2) must be(VponConnectionType(2, "Cellular data - Unknown Generation"))
    ConnectionTypeMapping.findVponConnectionTypeById(3) must be(VponConnectionType(3, "Cellular data - 2G"))
    ConnectionTypeMapping.findVponConnectionTypeById(4) must be(VponConnectionType(4, "Cellular data - 3G"))
    ConnectionTypeMapping.findVponConnectionTypeById(5) must be(VponConnectionType(5, "Cellular data - 4G"))

    an [ConnectionTypeMappingException] should be thrownBy ConnectionTypeMapping.findVponConnectionTypeById(-999)
  }

  it must "find all VponConnectionTypes properly" in {
    ConnectionTypeMapping.findAllVponConnectionTypes().size must be(7)
  }

  it must "provide warmUp method" in {
    try {
      ConnectionTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
