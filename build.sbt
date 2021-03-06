import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.0.1"
lazy val scalaLoggingVersion = "3.5.0"
lazy val logbackVersion = "1.1.2"
lazy val json4sVersion = "3.5.0"
lazy val scalaFxVersion = "8.0.102-R11"
lazy val scalaFxmlVersion = "0.3"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.1.4",
  scalaVersion := "2.12.1",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.scalafx" %% "scalafx" % scalaFxVersion,
    "org.scalafx" %% "scalafxml-core-sfx8" % scalaFxmlVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "npm-package-analyzer"
  )

mainClass in assembly := Some("com.stulsoft.npm.analyzer.MainView")

coverageEnabled := true

// To get coverage run command: sbt clean coverage test coverageReport
