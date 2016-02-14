package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

import com.vpon.mapping.exception.DealTypeMappingException

class DealTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon DealType properly by id" in {
    DealTypeMapping.findVponDealTypeById(0) must be(VponDealType(0, "No Payment"))
    DealTypeMapping.findVponDealTypeById(1) must be(VponDealType(1, "CPM"))
    DealTypeMapping.findVponDealTypeById(2) must be(VponDealType(2, "CPC"))
    DealTypeMapping.findVponDealTypeById(3) must be(VponDealType(3, "CPA"))

    an [DealTypeMappingException] should be thrownBy DealTypeMapping.findVponDealTypeById(-999)
  }

  it must "find all Vpon DealType properly" in {
    DealTypeMapping.findAllVponDealTypes.size must be(4)
  }

  it must "provide warmUp method" in {
    try {
      DealTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}

