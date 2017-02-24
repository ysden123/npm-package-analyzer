/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for Analyzer class
  *
  * @author Yuriy Stul
  */
class AnalyzerTest extends FlatSpec with Matchers {
  private def pathToTestProject:String={
    getClass.getResource("/test-project").toURI.getPath
  }
  behavior of "AnalyzerTest"

  "getModuleName" should "return empty collection for incorrect input" in {
    new Analyzer(pathToTestProject).getModuleNames(null).size should equal(0)
    new Analyzer(pathToTestProject).getModuleNames("").size should equal(0)
  }

  it should "return module name" in {
    new Analyzer(pathToTestProject).getModuleNames("require('test')") should equal(Set("test"))
    new Analyzer(pathToTestProject).getModuleNames("""require("test")""") should equal(Set("test"))
    new Analyzer(pathToTestProject).getModuleNames(""" require("test").""") should equal(Set("test"))
    new Analyzer(pathToTestProject).getModuleNames(""" new (require("test")).""") should equal(Set("test"))
    new Analyzer(pathToTestProject).getModuleNames(""" abc require("test")()""") should equal(Set("test"))
    new Analyzer(pathToTestProject).getModuleNames("""require("test1")require('test2')""") should equal(Set("test1","test2"))
    new Analyzer(pathToTestProject).getModuleNames("""require("te-st1")require('te_st2')""") should equal(Set("te-st1","te_st2"))
  }

  "getDependencies" should "get dependencies from package.json file" in {
    val dependencies = new Analyzer(pathToTestProject).getDependencies(getClass.getResource("/package.json").toURI.getPath)
    dependencies.size should equal(2)
    dependencies.head should equal("hazelcast-client")
    dependencies.tail.head should equal("async")
  }


  "getJSFiles" should "return list of JavaScript files" in {
    val testProjectPath = getClass.getResource("/test-project").toURI.getPath
    val files = new Analyzer(pathToTestProject).getJSFiles(new File(testProjectPath))
    files.length > 0 should equal(true)
    files.exists(_.getName.endsWith("m1.js")) should equal(true)
    files.exists(_.getName.endsWith("m2.js")) should equal(true)
    files.exists(_.getName.endsWith("ms1.js")) should equal(true)
  }

  it should "handle error" in {
    assertThrows[IllegalArgumentException] {
      new Analyzer(pathToTestProject).getJSFiles(null)
    }

    assertThrows[IllegalArgumentException] {
      new Analyzer(pathToTestProject).getJSFiles(new File("ERROR22ERROR"))
    }
  }

  "getUnusedModuleNames" should "get list of used module names" in {
    val unusedModuleNames = new Analyzer(pathToTestProject).getUnusedModuleNames(getClass.getResource("/test-project").toURI.getPath)
    unusedModuleNames should not be null
    unusedModuleNames.size should equal(1)
  }

  "main" should "prevent incorrect program parameters" in {
    assertThrows[IllegalArgumentException] {
      new Analyzer(null)
    }
    assertThrows[IllegalArgumentException] {
      new Analyzer("error")
    }
    assertThrows[IllegalArgumentException] {
      new Analyzer("package.json")
    }
  }
}
