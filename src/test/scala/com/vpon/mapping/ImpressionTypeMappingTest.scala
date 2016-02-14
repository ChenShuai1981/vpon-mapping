package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class ImpressionTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon ImpressionType properly by id" in {
    ImpressionTypeMapping.findVponImpressionTypeById(1) must be(VponImpressionType(1, "Blank"))
    ImpressionTypeMapping.findVponImpressionTypeById(2) must be(VponImpressionType(2, "PSA"))
    ImpressionTypeMapping.findVponImpressionTypeById(3) must be(VponImpressionType(3, "Default"))
    ImpressionTypeMapping.findVponImpressionTypeById(4) must be(VponImpressionType(4, "Kept"))
    ImpressionTypeMapping.findVponImpressionTypeById(5) must be(VponImpressionType(5, "Resold"))
    ImpressionTypeMapping.findVponImpressionTypeById(6) must be(VponImpressionType(6, "RTB"))
    ImpressionTypeMapping.findVponImpressionTypeById(7) must be(VponImpressionType(7, "External Impression"))
    ImpressionTypeMapping.findVponImpressionTypeById(8) must be(VponImpressionType(8, "External Click"))
  }

  it must "find all Vpon ImpressionTypes properly" in {
    ImpressionTypeMapping.findAllVponImpressionTypes.size must be(8)
  }

  it must "provide warmUp method" in {
    try {
      ImpressionTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
