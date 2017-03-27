package com.stulsoft.npm.analyzer

/**
  * @author Yuriy Stul.
  */
object Test extends App {
//  val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)[',\"]\\)".r
//  val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)('|\"|<)\\)".r
  val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)[<]\\)".r
//  val text1 = "const Config = require('jpt-google-adwords/conf/config');"
  val text1 = "const Config = require('jpt-google-adwords<conf<config');"
  val text2 = "const Config = require('jpt-google-adwords');"

  var moduleNames0 = for (m <- pattern findAllMatchIn text1) yield m.group(0)
  println("text1 0")
  moduleNames0.foreach(println)
  var moduleNames1 = for (m <- pattern findAllMatchIn text1) yield m.group(1)
  println("text1 1")
  moduleNames1.foreach(println)

  moduleNames0 = for (m <- pattern findAllMatchIn text2) yield m.group(0)
  println("text2 0")
  moduleNames0.foreach(println)
  moduleNames1 = for (m <- pattern findAllMatchIn text2) yield m.group(1)
  println("text2 1")
  moduleNames1.foreach(println)
}
