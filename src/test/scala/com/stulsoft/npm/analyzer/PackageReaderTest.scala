/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import org.scalatest.{FlatSpec, Matchers}

/**
  * Unit tests for PackageReader class
  *
  * @author Yuriy Stul
  */
class PackageReaderTest extends FlatSpec with Matchers {

  behavior of "PackageReader"

  "getDependencies" should "get dependencies from package.json filr" in {
    val dependencies = PackageReader.getDependencies(getClass.getResource("/package.json").toURI.getPath)
    dependencies.size should equal(2)
    dependencies.head should equal("hazelcast-client")
    dependencies.tail.head should equal("async")
  }
}
