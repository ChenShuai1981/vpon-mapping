package com.vpon.mapping

import org.scalatest.{BeforeAndAfterAll, MustMatchers, FlatSpec}


class GeographyMappingTest extends FlatSpec with BeforeAndAfterAll with MustMatchers {

  override def beforeAll() = {
    val cacheSpec = s"maximumSize=100,initialCapacity=100,concurrencyLevel=8"
    GeographyMapping.init(cacheSpec)
  }

  it must "find vpon geo properly by list file" in {
    GeographyMapping.findVponGeoByVponGeoId(120) must be(Some(VponGeo(120, "China, CN / Country", "Fujian, CN / Region", "Fuzhou, CN / City")))
    GeographyMapping.findVponGeoByVponGeoId(2) must be(Some(VponGeo(2, "Cambodia, KH / Country", "", "")))
    GeographyMapping.findVponGeoByVponGeoId(4) must be(Some(VponGeo(4, "China, CN / Country", "Anhui, CN / Region", "")))
    GeographyMapping.findVponGeoByVponGeoId(0) must be(Some(VponGeo(0, "Others", "", "")))
  }

  it must "find vpon geo ids by ip properly" in {
    GeographyMapping.findVponGeoIdsByIP("0.0.0.0") must be(Seq(0))
    GeographyMapping.findVponGeoIdsByIP("202.140.101.38") must be(Seq(35))
    GeographyMapping.findVponGeoIdsByIP("93.231.185.204") must be(Seq(0))
    GeographyMapping.findVponGeoIdsByIP("184.146.159.45") must be(Seq(0))
    GeographyMapping.findVponGeoIdsByIP("58.60.5.164") must be(Seq(3, 9 ,117))
    GeographyMapping.findVponGeoIdsByIP("114.80.166.240") must be(Seq(3, 27))
    GeographyMapping.findVponGeoIdsByIP("218.4.152.110") must be(Seq(3, 19, 131))
    GeographyMapping.findVponGeoIdsByIP("58.210.243.51") must be(Seq(3, 19 ,131))
    GeographyMapping.findVponGeoIdsByIP("218.4.152.11") must be(Seq(3, 19 ,131))
    GeographyMapping.findVponGeoIdsByIP("211.84.208.123.57.161.209") must be(Seq(0))
    GeographyMapping.findVponGeoIdsByIP("abc.def.ghi.jkl") must be(Seq(0))
    GeographyMapping.findVponGeoIdsByIP("abcdefghijkl") must be(Seq(0))
  }

  //  it must "find vpon geo ids by ip properly for those region name with non-utf8 encoding" in {
  //    GeographyMapping.findVponGeoIdsByIP("88.100.229.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("83.151.52.127") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("93.158.60.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("83.145.88.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("94.199.127.31") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("91.217.168.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("159.155.154.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("38.65.136.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("201.157.6.95") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("91.131.5.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("79.189.113.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("82.96.53.255") must be(Seq(0))
  //    GeographyMapping.findVponGeoIdsByIP("46.195.81.255") must be(Seq(51, 38))
  //    GeographyMapping.findVponGeoIdsByIP("213.164.205.255") must be(Seq(58, 38))
  //    GeographyMapping.findVponGeoIdsByIP("183.180.162.255") must be(Seq(60, 38))
  //    GeographyMapping.findVponGeoIdsByIP("61.213.111.255") must be(Seq(71, 38))
  //    GeographyMapping.findVponGeoIdsByIP("61.205.209.255") must be(Seq(79, 38))
  //  }

  it must "provide warmUp method" in {
    try {
      GeographyMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }

}
