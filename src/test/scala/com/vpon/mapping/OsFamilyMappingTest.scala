package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

// os_family_ua_mappings_test.csv used by UserAgentUtilTest
class OsFamilyMappingTest extends FlatSpec with MustMatchers {

  it must "get OsFamily properly from osName and osVersion coming from UA DB by mapping" in {
    OsFamilyMapping.findVponOsFamilyIds("Android", "2.2.1") must be(VponOsFamilyIds(Some(1), Some(11)))

    OsFamilyMapping.findVponOsFamilyIds("iOS", "8_0") must be(VponOsFamilyIds(Some(2), Some(89)))

    // 2.2.0 not found so fall back to 2.2
    OsFamilyMapping.findVponOsFamilyIds("Android", "2.2.0") must be(VponOsFamilyIds(Some(1), Some(11)))

    // 3_2_3 not found so fall back to 3_2
    OsFamilyMapping.findVponOsFamilyIds("iOS", "3_2_3") must be(VponOsFamilyIds(Some(2), Some(40)))

    OsFamilyMapping.findVponOsFamilyIds("iOS", "3.2.2") must be(VponOsFamilyIds(Some(2), None))

    // 19_0_1 not found so as to 19 so fall back to iOS/*
    OsFamilyMapping.findVponOsFamilyIds("iOS", "19_0_1") must be(VponOsFamilyIds(Some(2), None))

    // Not defined 'BlackBerry' osName so fall back to */*
    OsFamilyMapping.findVponOsFamilyIds("BlackBerry", "1.1.0") must be(VponOsFamilyIds(Some(0), None))
  }

  it must "provide warmUp method" in {
    try {
      OsFamilyMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }

}
