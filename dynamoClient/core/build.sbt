name := """dynamoDBClientCore"""

version := "1.0"

val awsSdkVersion = "1.10.67"

libraryDependencies ++= Seq(
	"com.amazonaws" % "aws-java-sdk-dynamodb" % awsSdkVersion,
	"com.typesafe" % "config" % "1.3.0",
	"org.scalatest" %% "scalatest" % "3.0.0-M15" % "test"
)
