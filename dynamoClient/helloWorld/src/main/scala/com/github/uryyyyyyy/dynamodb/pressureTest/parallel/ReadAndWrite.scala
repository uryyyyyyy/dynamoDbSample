package com.github.uryyyyyyy.dynamodb.pressureTest.parallel

import com.amazonaws.services.dynamodbv2.document.{Item, Table}
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}
import com.typesafe.config.ConfigFactory

object ReadAndWrite {

	val config = ConfigFactory.load()

	var count = 0

	def main(args: Array[String]): Unit = {

		println("start")
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)

		val item = new Item().`with`("Id", 3).`with`("memo", "memomemo")
		SampleTable.putItem(table, item)

		MeasurementUtility.timeCounter(() => execute(table))
	}

	def execute(table:Table): Unit ={
		val ff = (1 to 3000).toStream.map(v => () => {
			val item = new Item().`with`("Id", v).`with`("memo", "memomemo")
			SampleTable.putItem(table, item)

			val item2 = SampleTable.getItem(table, v)
			println(item2)
		})
		MeasurementUtility.execute(ff, 1000)
	}
}

