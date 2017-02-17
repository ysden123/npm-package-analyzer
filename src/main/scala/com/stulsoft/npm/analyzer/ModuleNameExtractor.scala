/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

/**
  * @author Yuriy Stul
  */
object ModuleNameExtractor {
  /**
    * Returns name of module, if it was defined
    *
    * @param text the test text
    * @return name of module, if it was defined; None - otherwise
    */
  def getModuleName(text: String): Option[String] = {
    text match {
      case null => None
      case s: String if s.isEmpty => None
      case s: String =>
        val pattern = "require\\([',\"](.*)[',\"]\\)".r
        for (m <- pattern findFirstMatchIn s) yield m.group(1)
    }
  }
}
