package com.vpon.mapping

import org.scalatest.{MustMatchers, FlatSpec}

class OfferSensitiveCategoryMappingTest extends FlatSpec with MustMatchers {

  it must "find Vpon OfferSensitiveCategory properly by id" in {
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("001") must be(VponOfferSensitiveCategory("001", "Airline", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("002") must be(VponOfferSensitiveCategory("002", "Apparel and Accessory", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("003") must be(VponOfferSensitiveCategory("003", "Appliances", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("004") must be(VponOfferSensitiveCategory("004", "Automotive and Vehicles", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("005") must be(VponOfferSensitiveCategory("005", "Computers and Electronics", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("006") must be(VponOfferSensitiveCategory("006", "Cosmetic Procedures", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("007") must be(VponOfferSensitiveCategory("007", "Cosmetics and Hygiene", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("008") must be(VponOfferSensitiveCategory("008", "Education", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("009") must be(VponOfferSensitiveCategory("009", "Employment", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("010") must be(VponOfferSensitiveCategory("010", "Family and Parenting", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("011") must be(VponOfferSensitiveCategory("011", "Financial", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("012") must be(VponOfferSensitiveCategory("012", "Fitness and Health", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("013") must be(VponOfferSensitiveCategory("013", "Flowers and Gifts", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("014") must be(VponOfferSensitiveCategory("014", "Food and Drink", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("015") must be(VponOfferSensitiveCategory("015", "Government", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("016") must be(VponOfferSensitiveCategory("016", "Health-care", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("017") must be(VponOfferSensitiveCategory("017", "Home and Decor", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("018") must be(VponOfferSensitiveCategory("018", "Hotels", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("019") must be(VponOfferSensitiveCategory("019", "Insurance", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("020") must be(VponOfferSensitiveCategory("020", "Legal", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("021") must be(VponOfferSensitiveCategory("021", "Movies", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("022") must be(VponOfferSensitiveCategory("022", "Music", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("023") must be(VponOfferSensitiveCategory("023", "Non Profit", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("024") must be(VponOfferSensitiveCategory("024", "Office Supplies", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("000") must be(VponOfferSensitiveCategory("000", "Others", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("025") must be(VponOfferSensitiveCategory("025", "Pets", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("026") must be(VponOfferSensitiveCategory("026", "Print and Publications", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("027") must be(VponOfferSensitiveCategory("027", "Real Estate", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("028") must be(VponOfferSensitiveCategory("028", "Retail", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("029") must be(VponOfferSensitiveCategory("029", "Software and Web Apps", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("030") must be(VponOfferSensitiveCategory("030", "Sporting Goods", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("031") must be(VponOfferSensitiveCategory("031", "Sports and Events", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("032") must be(VponOfferSensitiveCategory("032", "Telecommunications", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("033") must be(VponOfferSensitiveCategory("033", "Television", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("034") must be(VponOfferSensitiveCategory("034", "Toys / Games", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("035") must be(VponOfferSensitiveCategory("035", "Travel Destinations", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("036") must be(VponOfferSensitiveCategory("036", "Travel Services", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("037") must be(VponOfferSensitiveCategory("037", "Video Games", "Offer Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("101") must be(VponOfferSensitiveCategory("101", "Alcoholic Beverages and References", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("102") must be(VponOfferSensitiveCategory("102", "Consumer Loans", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("103") must be(VponOfferSensitiveCategory("103", "Dating", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("104") must be(VponOfferSensitiveCategory("104", "Gambling - Government-run Lottery", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("105") must be(VponOfferSensitiveCategory("105", "Horoscope or Astrology", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("106") must be(VponOfferSensitiveCategory("106", "Mobile App Downloads", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("107") must be(VponOfferSensitiveCategory("107", "Online Games", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("108") must be(VponOfferSensitiveCategory("108", "Pharmaceuticals and Supplements", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("109") must be(VponOfferSensitiveCategory("109", "Politics", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("110") must be(VponOfferSensitiveCategory("110", "Religion", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("111") must be(VponOfferSensitiveCategory("111", "Sexual Health", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("112") must be(VponOfferSensitiveCategory("112", "Sexually Suggestive", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("114") must be(VponOfferSensitiveCategory("114", "Shaky, Flashing, Flickering, Extreme Animation, Smileys", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("115") must be(VponOfferSensitiveCategory("115", "Surveys and Quizzes", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("116") must be(VponOfferSensitiveCategory("116", "User Interactive (e.g. Embedded Games)", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("113") must be(VponOfferSensitiveCategory("113", "Weight Loss", "Sensitive Category"))
    OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoryById("117") must be(VponOfferSensitiveCategory("117", "Windows Dialog or Alert Style", "Sensitive Category"))
  }

  it must "find Vpon OfferSensitiveCategory properly by category" in {
    val offerCategories = OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoriesByCategory("Offer Category")
    offerCategories.size must be(38)
    val sensitiveCategories = OfferSensitiveCategoryMapping.findVponOfferSensitiveCategoriesByCategory("Sensitive Category")
    sensitiveCategories.size must be(17)
  }

  it must "find all Vpon OfferSensitiveCategories properly" in {
    OfferSensitiveCategoryMapping.findAllVponOfferSensitiveCategories.size must be(55)
  }

  it must "provide warmUp method" in {
    try {
      OfferSensitiveCategoryMapping.warmUp()
    } catch {
      case e: Throwable => fail("should provide warmUp method")
    }
  }
}
