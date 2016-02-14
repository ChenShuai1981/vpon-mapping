package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class TimezoneMappingTest extends FlatSpec with MustMatchers {

  it must "provide warmUp method" in {
    try {
      TimezoneMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }

  it must "find Vpon Timezone properly by id" in {
    TimezoneMapping.findVponTimezoneById(1) must be(VponTimezone(1, "(UTC+00)  Europe / London"))
    TimezoneMapping.findVponTimezoneById(2) must be(VponTimezone(2, "(UTC+00)  GMT"))
    TimezoneMapping.findVponTimezoneById(3) must be(VponTimezone(3, "(UTC+01)  Europe / Berlin"))
    TimezoneMapping.findVponTimezoneById(4) must be(VponTimezone(4, "(UTC+02)  Asia / Jerusalem"))
    TimezoneMapping.findVponTimezoneById(5) must be(VponTimezone(5, "(UTC+03)  Asia / Tehran"))
    TimezoneMapping.findVponTimezoneById(6) must be(VponTimezone(6, "(UTC+03)  Europe / Moscow"))
    TimezoneMapping.findVponTimezoneById(7) must be(VponTimezone(7, "(UTC+04)  Asia / Dubai"))
    TimezoneMapping.findVponTimezoneById(8) must be(VponTimezone(8, "(UTC+04)  Asia / Kabul"))
    TimezoneMapping.findVponTimezoneById(9) must be(VponTimezone(9, "(UTC+05)  Asia / Kathmandu"))
    TimezoneMapping.findVponTimezoneById(10) must be(VponTimezone(10, "(UTC+05)  Asia / Kolkata"))
    TimezoneMapping.findVponTimezoneById(11) must be(VponTimezone(11, "(UTC+05)  Indian / Maldives"))
    TimezoneMapping.findVponTimezoneById(12) must be(VponTimezone(12, "(UTC+06)  Asia / Dhaka"))
    TimezoneMapping.findVponTimezoneById(13) must be(VponTimezone(13, "(UTC+06)  Asia / Rangoon"))
    TimezoneMapping.findVponTimezoneById(14) must be(VponTimezone(14, "(UTC+06)  Indian / Cocos"))
    TimezoneMapping.findVponTimezoneById(15) must be(VponTimezone(15, "(UTC+07)  Asia / Bangkok"))
    TimezoneMapping.findVponTimezoneById(16) must be(VponTimezone(16, "(UTC+07)  Asia / Ho Chi Minh"))
    TimezoneMapping.findVponTimezoneById(17) must be(VponTimezone(17, "(UTC+07)  Asia / Jakarta"))
    TimezoneMapping.findVponTimezoneById(18) must be(VponTimezone(18, "(UTC+08)  Asia / Hong Kong"))
    TimezoneMapping.findVponTimezoneById(19) must be(VponTimezone(19, "(UTC+08)  Asia / Macau"))
    TimezoneMapping.findVponTimezoneById(20) must be(VponTimezone(20, "(UTC+08)  Asia / Beijing"))
    TimezoneMapping.findVponTimezoneById(21) must be(VponTimezone(21, "(UTC+08)  Asia / Singapore"))
    TimezoneMapping.findVponTimezoneById(22) must be(VponTimezone(22, "(UTC+08)  Asia / Taipei"))
    TimezoneMapping.findVponTimezoneById(23) must be(VponTimezone(23, "(UTC+09)  Asia / Pyongyang"))
    TimezoneMapping.findVponTimezoneById(24) must be(VponTimezone(24, "(UTC+09)  Asia / Seoul"))
    TimezoneMapping.findVponTimezoneById(25) must be(VponTimezone(25, "(UTC+09)  Asia / Tokyo"))
    TimezoneMapping.findVponTimezoneById(26) must be(VponTimezone(26, "(UTC+09)  Australia / Adelaide"))
    TimezoneMapping.findVponTimezoneById(27) must be(VponTimezone(27, "(UTC+10)  Asia / Magadan"))
    TimezoneMapping.findVponTimezoneById(28) must be(VponTimezone(28, "(UTC+10)  Australia / Sydney"))
    TimezoneMapping.findVponTimezoneById(29) must be(VponTimezone(29, "(UTC+11)  Asia / Sakhalin"))
    TimezoneMapping.findVponTimezoneById(30) must be(VponTimezone(30, "(UTC-01)  America / Scoresbysund"))
    TimezoneMapping.findVponTimezoneById(31) must be(VponTimezone(31, "(UTC-02)  Atlantic / South Georgia"))
    TimezoneMapping.findVponTimezoneById(32) must be(VponTimezone(32, "(UTC-03)  America / Argentina / Buenos Aires"))
    TimezoneMapping.findVponTimezoneById(33) must be(VponTimezone(33, "(UTC-03)  Canada / Newfoundland"))
    TimezoneMapping.findVponTimezoneById(34) must be(VponTimezone(34, "(UTC-04)  America / Caracas"))
    TimezoneMapping.findVponTimezoneById(35) must be(VponTimezone(35, "(UTC-04)  America / Halifax"))
    TimezoneMapping.findVponTimezoneById(36) must be(VponTimezone(36, "(UTC-05)  America / New York"))
    TimezoneMapping.findVponTimezoneById(37) must be(VponTimezone(37, "(UTC-05)  US / Eastern"))
    TimezoneMapping.findVponTimezoneById(38) must be(VponTimezone(38, "(UTC-06)  America / Chicago"))
    TimezoneMapping.findVponTimezoneById(39) must be(VponTimezone(39, "(UTC-06)  US / Central"))
    TimezoneMapping.findVponTimezoneById(40) must be(VponTimezone(40, "(UTC-07)  America / Denver"))
    TimezoneMapping.findVponTimezoneById(41) must be(VponTimezone(41, "(UTC-07)  US / Mountain"))
    TimezoneMapping.findVponTimezoneById(42) must be(VponTimezone(42, "(UTC-08)  America / Los Angeles"))
    TimezoneMapping.findVponTimezoneById(43) must be(VponTimezone(43, "(UTC-08)  US / Pacific"))
    TimezoneMapping.findVponTimezoneById(44) must be(VponTimezone(44, "(UTC-09)  America / Juneau"))
    TimezoneMapping.findVponTimezoneById(45) must be(VponTimezone(45, "(UTC-10)  US / Hawaii"))
  }

  it must "find all VponTimezones properly" in {
    TimezoneMapping.findAllVponTimezones.size must be(45)
  }
}
