name := """dynamoDbClients"""

version := "1.0"

lazy val commonSettings = Seq(
	organization := "com.github.uryyyyyyy",
	scalaVersion := "2.11.7"
)

lazy val core = (project in file("core")).
	settings(commonSettings: _*)

lazy val helloWorld = (project in file("helloWorld")).
	settings(commonSettings: _*).dependsOn(core)

lazy val sparkBatch = (project in file("sparkBatch")).
	settings(commonSettings: _*).dependsOn(core)

lazy val sqsFallBackCore = (project in file("sqsFallBackCore")).
	settings(commonSettings: _*).dependsOn(core)

lazy val sqsFallBackWriter = (project in file("sqsFallBackWriter")).
	settings(commonSettings: _*).dependsOn(sqsFallBackCore)

lazy val sqsFallBackBatch = (project in file("sqsFallBackBatch")).
	settings(commonSettings: _*).dependsOn(sqsFallBackCore)
