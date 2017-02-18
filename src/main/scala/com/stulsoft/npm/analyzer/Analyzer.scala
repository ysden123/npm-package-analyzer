/*
 * Copyright (c) 2017. Yuriy Stul
 */

package com.stulsoft.npm.analyzer

import java.io.{File, FileInputStream}

import org.json4s.native.JsonMethods.parse

import scala.io.Source

/**
  * @author Yuriy Stul
  */
object Analyzer extends App {
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

  /**
    * Returns collection of module names
    *
    * @param text the test text
    * @return collection of module names; the collection may be empty
    */
  def getModuleNames(text: String): Set[String] = {
    if (text != null && !text.isEmpty) {
      val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)[',\"]\\)".r
      val moduleNames = for (m <- pattern findAllMatchIn text) yield m.group(1)
      moduleNames.toSet
    } else {
      Set()
    }
  }

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

  private def getUsedModuleNames(files: Array[File]): Set[String] = {
    files.flatMap(file => {
      Source.fromInputStream(new FileInputStream(file), "UTF8")
        .getLines()
        .flatMap(getModuleNames)
    }).toSet
  }

  def getUnusedModuleNames(path: String): Set[String] = {
    val usedModuleNames = getUsedModuleNames(getJSFiles(new File(path)))

    val declaredModuleNames = getDependencies(new File(path, "package.json").getPath)
    declaredModuleNames.filter(dm => !usedModuleNames.contains(dm)).toSet
  }

  println("NPM package analyzer, V 0.0.3")
  require(args != null && args.length == 1, "Path to npm project is not specified.")
  val projectPath = new File(args(0))
  require(projectPath.exists() && projectPath.isDirectory, s"Wrong path to npm project: ${args(0)}")

  println(s"Analyzing project ${projectPath.getName}")

  val unusedModuleNames = getUnusedModuleNames(args(0))
  println(s"Number of unused modules is ${unusedModuleNames.size}")

  if (unusedModuleNames.nonEmpty) {
    println("List of unused modules:")
    unusedModuleNames.foreach(println)
  }
}
