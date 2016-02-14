package com.vpon.mapping.config

trait MappingConfig {
  lazy val ipDbPath = "GeoIP2-City.mmdb"
  lazy val uaDbPath = "deviceatlas.json"

  val adPositionListPath = "ad_position/ad_position.csv"

  val carrierListPath = "carrier/carrier.csv"
  val carrierMappingPath = "carrier/carrier_mccmnc.csv"

  val connectionTypeListPath = "connection_type/connection_type.csv"
  val connectionTypeMappingPath = "connection_type/connection_type_mapping.csv"

  val currencyListPath = "currency/currency.csv"

  val dealTypeListPath = "deal_type/deal_type.csv"

  val densityListPath = "density/density.csv"
  val densityMappingPath = "density/density_mapping.csv"

  val deviceTypeListPath = "device_type/device_type.csv"
  val deviceTypeMappingPath = "device_type/device_type_ua_mappings.csv"

  val genderTypeListPath = "gender_type/gender_type.csv"

  val geographyListPath = "geography/geography.csv"
  val geographyMappingPath = "geography/geography_geoip.csv"

  val impressionTypeListPath = "impression_type/impression_type.csv"

  val languageListPath = "language/language.csv"
  val languageMappingPath = "language/language_iso_mapping.csv"

  val mediaTypeListPath = "media_type/media_type.csv"

  val offerSensitiveCategoryListPath = "offer_sensitive_category/offer_sensitive_category.csv"

  val osfamilyListPath = "os_family/os_family.csv"
  val osfamilyMappingPath = "os_family/os_family_ua_mappings.csv"

  val publisherRevenueShareTypeListPath = "publisher_revenue_share_type/publisher_revenue_share_type.csv"

  val sellerRevenueShareTypeListPath = "seller_revenue_share_type/seller_revenue_share_type.csv"

  val platformTypeListPath = "platform_type/platform_type.csv"

  val protocolTypeListPath = "protocol_type/protocol_type.csv"

  val supplyTypeListPath = "supply_type/supply_type.csv"
  val supplyTypeMappingPath = "supply_type/supply_type_ua_mappings.csv"

  val technicalAttributeListPath = "technical_attribute/technical_attribute.csv"

  val timezoneListPath = "timezone/timezone.csv"

  val universalCategoryListPath = "universal_category/universal_category.csv"
}
