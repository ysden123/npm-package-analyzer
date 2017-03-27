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
class Analyzer(val directory: String) {
  require(directory != null && !directory.isEmpty, s"Wrong path to npm project: $directory")
  val projectPath = new File(directory)
  require(projectPath.exists() && projectPath.isDirectory, s"Wrong path to npm project: $directory")

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
    if (text.contains("jpt-google-adwords"))
      println(text)
    if (text != null && !text.isEmpty) {
//      val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)[',\"]\\)".r
      val pattern = "require\\([',\"]([a-zA-Z0-9_\\-\\ ]*)[/|'|\"]\\)".r
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

  def getUsedModuleNames(files: Array[File]): Set[String] = {
    files.flatMap(file => {
      Source.fromInputStream(new FileInputStream(file), "UTF8")
        .getLines()
        .flatMap(getModuleNames)
    }).toSet
  }

  def getUnusedModuleNames(path: String): Set[String] = {
    val usedModuleNames = getUsedModuleNames(getJSFiles(new File(path)))

//    usedModuleNames.foreach(println)

    val declaredModuleNames = getDependencies(new File(path, "package.json").getPath)
    declaredModuleNames.filter(dm => !usedModuleNames.contains(dm)).toSet
  }

  def analyze: String = {
    val buffer = new StringBuilder("NPM package analyzer, V 0.1.0\n")

    buffer.append(s"Analyzing project ${projectPath.getName}\n")

    try {
      val unusedModuleNames = getUnusedModuleNames(directory)
      buffer.append(s"Number of unused modules is ${unusedModuleNames.size}\n")

      if (unusedModuleNames.nonEmpty) {
        buffer.append("List of unused modules:\n")
        unusedModuleNames.foreach(n => buffer.append(s"$n\n"))
      }
    } catch {
      case e: Exception => buffer.append(s"Exception: ${e.getMessage}\n")
    }

    buffer.toString
  }
}
