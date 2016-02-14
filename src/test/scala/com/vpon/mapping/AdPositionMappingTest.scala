package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

import com.vpon.mapping.exception.AdPositionMappingException

class AdPositionMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon AdPosition properly by id" in {
    AdPositionMapping.findVponAdPositionById(0) must be(VponAdPosition(0, "Unknown"))
    AdPositionMapping.findVponAdPositionById(6) must be(VponAdPosition(6, "Above the fold"))
    AdPositionMapping.findVponAdPositionById(2) must be(VponAdPosition(2, "DEPRECATED - May or may not be initially visible depending on screen size/resolution."))
    AdPositionMapping.findVponAdPositionById(5) must be(VponAdPosition(5, "Below the Fold"))
    AdPositionMapping.findVponAdPositionById(4) must be(VponAdPosition(4, "Header"))
    AdPositionMapping.findVponAdPositionById(1) must be(VponAdPosition(1, "Footer"))
    AdPositionMapping.findVponAdPositionById(3) must be(VponAdPosition(3, "Sidebar"))
    AdPositionMapping.findVponAdPositionById(7) must be(VponAdPosition(7, "Fullscreen"))

    an [AdPositionMappingException] should be thrownBy AdPositionMapping.findVponAdPositionById(-999)
  }

  it must "find all Vpon AdPosition properly" in {
    AdPositionMapping.findAllVponAdPositions.size must be(8)
  }

  it must "provide warmUp method" in {
    try {
      AdPositionMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
