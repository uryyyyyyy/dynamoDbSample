package com.github.uryyyyyyy.dynamodb.parallel

import com.amazonaws.services.dynamodbv2.document.PrimaryKey
import com.github.uryyyyyyy.dynamodb.{DynamoUtils, MeasurementUtility}

object Main {
	def main(args: Array[String]): Unit = {
		val dynamoDB = DynamoUtils.init()
		val table = dynamoDB.getTable("sampleDB")
		val key = new PrimaryKey("Id", 1)
		val item = table.getItem(key)
		println("start " + item)
		Thread.sleep(1000)
		val ss = (1 to 10).toStream.map(v => () => {
			val key = new PrimaryKey("Id", 1)
			val item = table.getItem(key)
			println(item)
		})

		val f = () => MeasurementUtility.execute(ss, 10)

		MeasurementUtility.timeCounter(f)
		dynamoDB.shutdown()
	}
}