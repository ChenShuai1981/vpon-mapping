package com.vpon

package object mapping {

  val WILDCARD = "*"

  val EMPTY = ""

  val NOTAVAILABLE = "n/a"

  implicit val codec = scala.io.Codec.ISO8859

  val classLoader = getClass().getClassLoader()

  def convertStringToInt(str: String): Option[Int] = {
    try{
      Some(str.toInt)
    } catch {
      case e: java.lang.NumberFormatException => None
    }
  }

}
