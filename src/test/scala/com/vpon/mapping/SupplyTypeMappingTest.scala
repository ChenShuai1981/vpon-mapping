package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class SupplyTypeMappingTest extends FlatSpec with MustMatchers {

  it must "provide warmUp method" in {
    try {
      SupplyTypeMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }

  it must "find Vpon Supply Type properly by ua primary hardware type and sdk_st" in {
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Desktop", "1") must be(VponSupplyType(1, "Web Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Desktop", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Desktop", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Mobile Phone", "1") must be(VponSupplyType(2, "Mobile Web Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Mobile Phone", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Mobile Phone", "3") must be(VponSupplyType(3, "Apps Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Tablet", "1") must be(VponSupplyType(2, "Mobile Web Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Tablet", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Tablet", "3") must be(VponSupplyType(3, "Apps Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("eReader", "1") must be(VponSupplyType(2, "Mobile Web Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("eReader", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("eReader", "3") must be(VponSupplyType(3, "Apps Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("TV", "1") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("TV", "2") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("TV", "3") must be(VponSupplyType(4, "TV Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Games Console", "1") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Games Console", "2") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Games Console", "3") must be(VponSupplyType(4, "TV Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Set Top Box", "1") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Set Top Box", "2") must be(VponSupplyType(4, "TV Placements"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Set Top Box", "3") must be(VponSupplyType(4, "TV Placements"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Media Player", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Media Player", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Media Player", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Plug-in Modem", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Plug-in Modem", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Plug-in Modem", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Embedded Network Module", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Embedded Network Module", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Embedded Network Module", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Camera", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Camera", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Camera", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wireless Hotspot", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wireless Hotspot", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wireless Hotspot", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wristwatch", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wristwatch", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Wristwatch", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Glasses", "1") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Glasses", "2") must be(VponSupplyType(0, "Others / Unknown"))
    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("Glasses", "3") must be(VponSupplyType(0, "Others / Unknown"))

    SupplyTypeMapping.findVponSupplyTypeByPrimaryHardwareTypeAndSDKSupplyType("N/A", "N/A") must be(VponSupplyType(0, "Others / Unknown"))
  }

  it must "find all VponSupplyTypes properly" in {
    SupplyTypeMapping.findAllVponSupplyTypes().size must be(5)
  }
}
