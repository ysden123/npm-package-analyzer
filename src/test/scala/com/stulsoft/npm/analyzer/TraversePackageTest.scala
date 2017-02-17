/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.File

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Yuriy Stul
  */
class TraversePackageTest extends FlatSpec with Matchers {

  behavior of "TraversePackageTest"

  "getJSFiles" should "return list of JavaScript files" in {
    val testProjectPath = getClass.getResource("/test-project").toURI.getPath
    val files = TraversePackage.getJSFiles(new File(testProjectPath))
    files.length > 0 should equal(true)
    files.exists(_.getName.endsWith("m1.js")) should equal(true)
    files.exists(_.getName.endsWith("m2.js")) should equal(true)
    files.exists(_.getName.endsWith("ms1.js")) should equal(true)
  }

  it should "handle error" in {
    assertThrows[IllegalArgumentException] {
      TraversePackage.getJSFiles(null)
    }

    assertThrows[IllegalArgumentException] {
      TraversePackage.getJSFiles(new File("ERROR22ERROR"))
    }
  }
}
