/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.File

/**
  * @author Yuriy Stul
  */
object TraversePackage {
  /**
    * Returns an array of the JavaScript files.
    *
    * @param dir start directory
    * @return array of the JavaScript files.
    */
  def getJSFiles(dir: File): Array[File] = {
    require(dir != null, "Parameter dir could not be null.")
    require(dir.exists(), s"Directory ${dir.getName} doesn't exist.")
    val these = dir.listFiles.filter(_.getName.endsWith(".js"))
    these ++ dir.listFiles.filter(f => f.isDirectory && !f.getName.contains("node_modules")).flatMap(getJSFiles)
  }
}
