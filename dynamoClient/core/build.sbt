name := """dynamoDBClientCore"""

version := "1.0"

libraryDependencies ++= Seq(
	"com.amazonaws" % "aws-java-sdk-dynamodb" % "1.10.62",
	"ch.qos.logback" % "logback-classic" % "1.1.6",
	"com.typesafe" % "config" % "1.3.0",
	"org.scalatest" %% "scalatest" % "3.0.0-M15" % "test"
)
