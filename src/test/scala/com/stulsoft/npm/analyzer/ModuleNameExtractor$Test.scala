/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul
  */
class ModuleNameExtractor$Test extends FlatSpec with Matchers {

  behavior of "ModuleNameExtractor$Test"

  "getModuleName" should "return None for incorrect input" in {
    ModuleNameExtractor.getModuleName(null) should equal(None)
    ModuleNameExtractor.getModuleName("") should equal(None)
  }

  it should "return module name" in {
    ModuleNameExtractor.getModuleName("require('test')") should equal(Some("test"))
    ModuleNameExtractor.getModuleName("""require("test")""") should equal(Some("test"))
    ModuleNameExtractor.getModuleName(""" require("test").""") should equal(Some("test"))
    ModuleNameExtractor.getModuleName(""" new (require("test")).""") should equal(Some("test"))
    ModuleNameExtractor.getModuleName(""" abc require("test")()""") should equal(Some("test"))
  }
}
