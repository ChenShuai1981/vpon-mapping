package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class GenderTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon GenderType properly by id" in {
    GenderTypeMapping.findVponGenderTypeById(0) must be(VponGenderType(0, "Unknown"))
    GenderTypeMapping.findVponGenderTypeById(1) must be(VponGenderType(1, "Male"))
    GenderTypeMapping.findVponGenderTypeById(2) must be(VponGenderType(2, "Female"))
  }

  it must "find all Vpon GenderType properly" in {
    GenderTypeMapping.findAllVponGenderTypes.size must be(3)
  }

  it must "provide warmUp method" in {
    try {
      GenderTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
