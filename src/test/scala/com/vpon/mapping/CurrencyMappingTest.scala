package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

import com.vpon.mapping.exception.CurrencyMappingException

class CurrencyMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon Currency properly by id" in {
    CurrencyMapping.findVponCurrencyById("CNY") must be(VponCurrency("CNY", "CNY - Chinese Yuan"))
    CurrencyMapping.findVponCurrencyById("HKD") must be(VponCurrency("HKD", "HKD - Hong Kong Dollar"))
    CurrencyMapping.findVponCurrencyById("JPY") must be(VponCurrency("JPY", "JPY - Japanese Yen"))
    CurrencyMapping.findVponCurrencyById("TWD") must be(VponCurrency("TWD", "TWD - Taiwan Dollar"))
    CurrencyMapping.findVponCurrencyById("USD") must be(VponCurrency("USD", "USD - United States Dollar"))

    an [CurrencyMappingException] should be thrownBy CurrencyMapping.findVponCurrencyById("AAA")
  }

  it must "find all Vpon Currencies properly" in {
    CurrencyMapping.findAllVponCurrencies.size must be(5)
  }

  it must "provide warmUp method" in {
    try {
      CurrencyMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
