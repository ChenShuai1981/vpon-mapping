package com.vpon.mapping.exception

class MappingException(error: String) extends RuntimeException

class CarrierMappingException(error: String) extends MappingException(error)

class ConnectionTypeMappingException(error: String) extends MappingException(error)

class DealTypeMappingException(error: String) extends MappingException(error)

class DensityMappingException(error: String) extends MappingException(error)

class DeviceTypeMappingException(error: String) extends MappingException(error)

class GeographyMappingException(error: String) extends MappingException(error)

class LanguageMappingException(error: String) extends MappingException(error)

class OsFamilyMappingException(error: String) extends MappingException(error)

class SupplyTypeMappingException(error: String) extends MappingException(error)

class DeviceOSMappingException(error: String) extends MappingException(error)

class AdPositionMappingException(error: String) extends MappingException(error)

class CurrencyMappingException(error: String) extends MappingException(error)

class GenderTypeMappingException(error: String) extends MappingException(error)

class ImpressionTypeMappingException(error: String) extends MappingException(error)

class MediaTypeMappingException(error: String) extends MappingException(error)

class OfferSensitiveCategoryMappingException(error: String) extends MappingException(error)

class PublisherRevenueShareTypeMappingException(error: String) extends MappingException(error)

class SellerRevenueShareTypeMappingException(error: String) extends MappingException(error)

class PlatformTypeMappingException(error: String) extends MappingException(error)

class ProtocolTypeMappingException(error: String) extends MappingException(error)

class TechnicalAttributeMappingException(error: String) extends MappingException(error)

class TimezoneMappingException(error: String) extends MappingException(error)

class UniversalCategoryMappingException(error: String) extends MappingException(error)
