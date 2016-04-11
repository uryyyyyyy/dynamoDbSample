name := """dynamoDBClientFallbackCore"""

version := "1.0"

val json4sVersion = "3.3.0"
val awsSdkVersion = "1.10.67"

libraryDependencies ++= Seq(
	"com.amazonaws" % "aws-java-sdk-sqs" % awsSdkVersion,
	"org.json4s" %% "json4s-native" % json4sVersion,
	"org.json4s" %% "json4s-jackson" % json4sVersion,
	"org.json4s" %% "json4s-ext" % json4sVersion,
	"com.esotericsoftware.kryo" % "kryo" % "2.24.0"
)
