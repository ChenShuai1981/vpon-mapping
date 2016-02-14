package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class MediaTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon MediaType properly by ids" in {
    MediaTypeMapping.findVponMediaTypeByIds(1, 1) must be(VponMediaType(1, 1, "Banner", "Standard Banner"))
    MediaTypeMapping.findVponMediaTypeByIds(2, 1) must be(VponMediaType(2, 1, "Interstitial", "Standard Interstitial"))
  }

  it must "find all Vpon MediaType properly" in {
    MediaTypeMapping.findAllVponMediaTypes.size must be(2)
  }

  it must "provide warmUp method" in {
    try {
      MediaTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}