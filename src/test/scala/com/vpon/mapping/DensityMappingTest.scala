package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class DensityMappingTest extends FlatSpec with MustMatchers {

  it must "find VponDensity properly by user screen density" in {
    DensityMapping.findVponDensityByUserScreenDensity(Some(1.2f)) must be(VponDensity(1, "Standard Density"))
    DensityMapping.findVponDensityByUserScreenDensity(Some(1.5f)) must be(VponDensity(2, "High Density"))
    DensityMapping.findVponDensityByUserScreenDensity(Some(2.6f)) must be(VponDensity(2, "High Density"))
    DensityMapping.findVponDensityByUserScreenDensity(None) must be(VponDensity(0, "Unknown"))
  }

  it must "find VponDensity properly by id" in {
    DensityMapping.findVponDensityById(0) must be(VponDensity(0, "Unknown"))
    DensityMapping.findVponDensityById(1) must be(VponDensity(1, "Standard Density"))
    DensityMapping.findVponDensityById(2) must be(VponDensity(2, "High Density"))
  }

  it must "find all VponDensities properly" in {
    DensityMapping.findAllVponDensities().size must be(3)
  }

  it must "provide warmUp method" in {
    try {
      DensityMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }

}
