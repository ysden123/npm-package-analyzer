package com.stulsoft.npm.analyzer

import java.io.File

import org.json4s.{JObject, JString}
import org.json4s.native.JsonMethods._


/**
  * Reads NPM package
  *
  * @author Yuriy Stul
  */
object PackageReader {
  def readPackage(path: String): Iterable[String] = {
    val file = new File(path)
    val json = parse(file)
    val dependencies =  json.values.asInstanceOf[Map[String, AnyVal]].get("dependencies").get
//    println(dependencies.asInstanceOf[Map[String,String]].keys)
    dependencies.asInstanceOf[Map[String,String]].keys
  }
}

object Test extends App {

  val dependencies=PackageReader.readPackage(new File(Test.getClass.getResource("/package.json").getPath)
    .getAbsolutePath.replaceAll("%20", " "))
  println(dependencies.mkString(", "))
}
