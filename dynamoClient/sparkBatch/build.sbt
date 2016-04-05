name := """dynamoDBClient_spark2.11"""

version := "1.0"

lazy val sparkVersion = "1.6.1"

libraryDependencies ++= Seq(
	"org.apache.spark" %% "spark-core" % sparkVersion % "provided"
)

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)