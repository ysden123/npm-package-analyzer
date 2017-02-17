/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.File

import org.json4s.native.JsonMethods._

/**
  * Reads NPM package
  *
  * @author Yuriy Stul
  */
object PackageReader {
  /**
    * Gets a collection of the dependencies
    *
    * @param path full path to file (package.json)
    * @return the collection of the dependencies
    */
  def getDependencies(path: String): Iterable[String] = {
    val file = new File(path)
    val json = parse(file)
    try {
      val dependencies = json.values.asInstanceOf[Map[String, AnyVal]]("dependencies")
      dependencies.asInstanceOf[Map[String, String]].keys
    }
    catch {
      case _: Exception => Set[String]()
    }
  }
}
