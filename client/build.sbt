name := """dynamoDbSampleClient"""

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
	"com.amazonaws" % "aws-java-sdk-dynamodb" % "1.10.60",
	"ch.qos.logback" % "logback-classic" % "1.1.6",
	"com.typesafe" % "config" % "1.3.0"
)
