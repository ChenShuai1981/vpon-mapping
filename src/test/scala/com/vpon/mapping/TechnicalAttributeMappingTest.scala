package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class TechnicalAttributeMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon TechnicalAttribute properly by id" in {
    TechnicalAttributeMapping.findVponTechnicalAttributeById(1) must be(VponTechnicalAttribute(1, "Animated: 0-15 seconds"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(2) must be(VponTechnicalAttribute(2, "Animated: 15-30 seconds"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(3) must be(VponTechnicalAttribute(3, "Animated: more than 30 seconds"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(4) must be(VponTechnicalAttribute(4, "Audio: Auto-Initiated"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(5) must be(VponTechnicalAttribute(5, "Audio: User-Initiated"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(6) must be(VponTechnicalAttribute(6, "Expandable: Auto-Expand"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(7) must be(VponTechnicalAttribute(7, "Expandable: Click to Expand"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(8) must be(VponTechnicalAttribute(8, "DHTML"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(9) must be(VponTechnicalAttribute(9, "Image"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(10) must be(VponTechnicalAttribute(10, "MRAID-2"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(11) must be(VponTechnicalAttribute(11, "Text"))
    TechnicalAttributeMapping.findVponTechnicalAttributeById(12) must be(VponTechnicalAttribute(12, "Video"))
  }

  it must "find all Vpon TechnicalAttributes properly" in {
    TechnicalAttributeMapping.findAllVponTechnicalAttributes.size must be(12)
  }

  it must "provide warmUp method" in {
    try {
      TechnicalAttributeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}