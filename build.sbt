import sbt.Keys.libraryDependencies

lazy val scalaTestVersion = "3.0.1"
lazy val scalaLoggingVersion = "3.5.0"
lazy val logbackVersion = "1.1.2"
lazy val json4sVersion = "3.5.0"

lazy val commonSettings = Seq(
  organization := "com.stulsoft",
  version := "0.0.3",
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq(
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps"),
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.json4s" %% "json4s-native" % json4sVersion,
    "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
  )
)

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "npm-package-analyzer"
  )

mainClass in assembly := Some("com.stulsoft.npm.analyzer.Analyzer")

coverageEnabled := true

// To get coverage run command: sbt clean coverage test coverageReport
