package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class ProtocolTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon ProtocolType properly by id" in {
    ProtocolTypeMapping.findVponProtocolTypeById(0) must be(VponProtocolType(0, "Unknown"))
    ProtocolTypeMapping.findVponProtocolTypeById(1) must be(VponProtocolType(1, "http"))
    ProtocolTypeMapping.findVponProtocolTypeById(2) must be(VponProtocolType(2, "https"))
  }

  it must "find all Vpon ProtocolTypes properly" in {
    ProtocolTypeMapping.findAllVponProtocolTypes.size must be(3)
  }

  it must "provide warmUp method" in {
    try {
      ProtocolTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
