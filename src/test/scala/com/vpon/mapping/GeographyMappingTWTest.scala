package com.vpon.mapping

import org.scalatest.{MustMatchers, BeforeAndAfterAll, FlatSpec}

class GeographyMappingTWTest extends FlatSpec with BeforeAndAfterAll with MustMatchers {

  override def beforeAll() = {
    val cacheSpec = s"maximumSize=100,initialCapacity=100,concurrencyLevel=8"
    GeographyMapping.init(cacheSpec)
  }

  it must "find vpon geo ids by maxmind geo fields properly (TW)" in {
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), None, "[]") must be(List(93))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), None, "[Taiwan]") must be(List(93))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672082"), "[Fukien]") must be(List(93))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670436"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669506"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676914"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676821"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665443"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667669"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676538"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672557"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1679136"), "[Taiwan, Changhua]") must be(List(93, 94))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668399"), "[Taiwan, Taichung]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1679136"), "[Taiwan, Changhua]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666907"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676163"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6587023"), "[Taiwan]") must be(List(93, 94))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1678836"), "[Taiwan, Chiayi]") must be(List(93, 95))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1678835"), "[Taiwan, Chiayi]") must be(List(93, 95))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670295"), "[Taiwan]") must be(List(93, 96))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668957"), "[Taiwan]") must be(List(93, 96))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6656332"), "[Taiwan]") must be(List(93, 96))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670367"), "[Taiwan]") must be(List(93, 96))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671913"), "[Taiwan]") must be(List(93, 96))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671468"), "[Taiwan]") must be(List(93, 97))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1675151"), "[Taiwan, Hsinchu]") must be(List(93, 97))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1675151"), "[Taiwan, Hsinchu]") must be(List(93, 98))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677112"), "[Taiwan, Hsinchu]") must be(List(93, 98))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677112"), "[Taiwan, Hsinchu]") must be(List(93, 98))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1675151"), "[Taiwan, Hsinchu]") must be(List(93, 98))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1675151"), "[Taiwan, Hsinchu]") must be(List(93, 98))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665297"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672233"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6747354"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669121"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669124"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674504"), "[Taiwan, Hualien]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674504"), "[Taiwan, Hualien]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6254449"), "[Taiwan]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674504"), "[Taiwan, Hualien]") must be(List(93, 99))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673820"), "[Kaohsiung]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673777"), "[Taiwan]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673816"), "[Taiwan]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671749"), "[Taiwan]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673951"), "[Kaohsiung, Kaohsiung]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672582"), "[Kaohsiung, Kaohsiung]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668444"), "[Taiwan]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673825"), "[Kaohsiung, Kaohsiung]") must be(List(93, 100))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1678228"), "[Taiwan, Keelung]") must be(List(93, 101))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1678008"), "[Fukien]") must be(List(93, 102))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665450"), "[Taiwan]") must be(List(93, 103))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666628"), "[Taiwan]") must be(List(93, 103))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671971"), "[Taiwan, Miaoli]") must be(List(93, 103))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677513"), "[Taiwan]") must be(List(93, 103))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667027"), "[Taiwan]") must be(List(93, 103))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677099"), "[Taiwan]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670310"), "[Taiwan, Nantou]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671930"), "[Taiwan]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671566"), "[Taiwan, Nantou]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671566"), "[Taiwan, Nantou]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670310"), "[Taiwan, Nantou]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666855"), "[Taiwan]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671078"), "[Taiwan]") must be(List(93, 104))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670106"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673311"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665491"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666768"), "[Taiwan]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676847"), "[Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676847"), "[Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670538"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668876"), "[Taiwan]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670029"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670029"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6621190"), "[Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668311"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670141"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665540"), "[Taiwan, T'ai-pei Shih]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670141"), "[Taipei, Taipei]") must be(List(93, 105))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668341"), "[Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672882"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672584"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667982"), "[Taipei, Taipei]") must be(List(93, 105))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666220"), "[Taiwan]") must be(List(93, 106))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672228"), "[Taiwan, Penghu]") must be(List(93, 106))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670652"), "[Taiwan]") must be(List(93, 106))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676242"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668336"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1679004"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668986"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6647640"), "[Taiwan, Pingtung]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666140"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1672033"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670481"), "[Taiwan, Pingtung]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665707"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673625"), "[Taiwan]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677628"), "[Kaohsiung, Kaohsiung]") must be(List(93, 107))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669038"), "[Taiwan]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668399"), "[Taiwan, Taichung]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670682"), "[Taiwan]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674643"), "[Taiwan, Taichung]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669903"), "[Taiwan]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668396"), "[Taiwan]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676034"), "[Taiwan]") must be(List(93, 108))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668355"), "[Taiwan, Tainan]") must be(List(93, 109))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669723"), "[Taiwan]") must be(List(93, 109))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666154"), "[Taipei]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668341"), "[Taipei]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668681"), "[Taiwan, T'ai-pei Shih]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6647327"), "[Taipei, Taipei]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1669369"), "[Taiwan, T'ai-pei Shih]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667385"), "[Taiwan, T'ai-pei Shih]") must be(List(93, 110))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6602591"), "[Taiwan]") must be(List(93, 111))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668360"), "[Taiwan]") must be(List(93, 111))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6949678"), "[Taiwan]") must be(List(93, 111))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668295"), "[Taiwan, Taitung]") must be(List(93, 111))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668467"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665615"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1670909"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671954"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("6574039"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1677308"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667042"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667905"), "[Taiwan, Taoyuan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667905"), "[Taiwan, Taoyuan]") must be(List(93, 112))
//    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668341"), "[Taipei]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1671728"), "[Taiwan]") must be(List(93, 112))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674199"), "[Taiwan, Yilan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668725"), "[Taiwan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674199"), "[Taiwan, Yilan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674199"), "[Taiwan, Yilan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665239"), "[Taiwan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674199"), "[Taiwan, Yilan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1678716"), "[Taiwan, Yilan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1666175"), "[Taiwan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1667061"), "[Taiwan]") must be(List(93, 113))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668988"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1668721"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676397"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1673388"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1674247"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665194"), "[Taiwan, Yunlin County]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1665196"), "[Taiwan, Yunlin County]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("1676810"), "[Taiwan]") must be(List(93, 114))
    GeographyMapping.findVponGeoIdsByMaxmindGeoFields(Some("1668284"), Some("*"), "*") must be(List(93))
  }
}
