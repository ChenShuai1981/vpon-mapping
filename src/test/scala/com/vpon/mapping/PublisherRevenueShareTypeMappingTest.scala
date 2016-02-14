package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class PublisherRevenueShareTypeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon PublisherRevenueShareType properly by id" in {
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(0) must be(VponPublisherRevenueShareType(0, "No Payment"))
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(1) must be(VponPublisherRevenueShareType(1, "CPM"))
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(2) must be(VponPublisherRevenueShareType(2, "CPC"))
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(3) must be(VponPublisherRevenueShareType(3, "CPA"))
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(4) must be(VponPublisherRevenueShareType(4, "Owner CPM"))
    PublisherRevenueShareTypeMapping.findVponPublisherRevenueShareTypeById(5) must be(VponPublisherRevenueShareType(5, "Owner Revenue Share"))
  }

  it must "find all Vpon PublisherRevenueShareTypes properly" in {
    PublisherRevenueShareTypeMapping.findAllVponPublisherRevenueShareTypes.size must be(6)
  }

  it must "provide warmUp method" in {
    try {
      PublisherRevenueShareTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
