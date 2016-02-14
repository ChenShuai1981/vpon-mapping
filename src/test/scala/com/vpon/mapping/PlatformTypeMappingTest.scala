package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class PlatformTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon PlatformType properly by id" in {
    PlatformTypeMapping.findVponPlatformTypeById(0) must be(VponPlatformType(0, "Direct"))
    PlatformTypeMapping.findVponPlatformTypeById(1) must be(VponPlatformType(1, "Real Time"))
  }

  it must "find all Vpon PlatformTypes properly" in {
    PlatformTypeMapping.findAllVponPlatformTypes.size must be(2)
  }

  it must "provide warmUp method" in {
    try {
      PlatformTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
