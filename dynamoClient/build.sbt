name := """dynamoDbClients"""

version := "1.0"

lazy val commonSettings = Seq(
	organization := "com.github.uryyyyyyy",
	scalaVersion := "2.10.6"
	//scalaVersion := "2.11.7"
)


lazy val helloWorld = (project in file("helloWorld")).
	settings(commonSettings: _*).dependsOn(core)

lazy val sparkBatch = (project in file("sparkBatch")).
	settings(commonSettings: _*).dependsOn(core)

lazy val core = (project in file("core")).
	settings(commonSettings: _*)