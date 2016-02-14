package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class SellerRevenueShareTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon SellerRevenueShareType properly by id" in {
    SellerRevenueShareTypeMapping.findVponSellerRevenueShareTypeById(0) must be(VponSellerRevenueShareType(0, "Percent"))
    SellerRevenueShareTypeMapping.findVponSellerRevenueShareTypeById(1) must be(VponSellerRevenueShareType(1, "Fixed CPM"))
  }

  it must "find all Vpon SellerRevenueShareTypes properly" in {
    SellerRevenueShareTypeMapping.findAllVponSellerRevenueShareTypes.size must be(2)
  }

  it must "provide warmUp method" in {
    try {
      SellerRevenueShareTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}