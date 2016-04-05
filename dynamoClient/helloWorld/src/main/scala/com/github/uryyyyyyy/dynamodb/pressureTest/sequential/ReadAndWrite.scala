package com.github.uryyyyyyy.dynamodb.pressureTest.sequential

import com.amazonaws.services.dynamodbv2.document.{Item, Table}
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object ReadAndWrite {

	val logger = LoggerFactory.getLogger("logger_name")
	val config = ConfigFactory.load()

	var count = 0

	def main(args: Array[String]): Unit = {

		logger.info("start")
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)

		val item = new Item().`with`("Id", 3).`with`("memo", "memomemo")
		SampleTable.putItem(table, item)

		MeasurementUtility.timeCounter(() => execute(table))
	}

	def execute(table:Table): Unit ={
		(1 to 3000).foreach(v => {
			val item = new Item().`with`("Id", v).`with`("memo", "memomemo")
			SampleTable.putItem(table, item)

			val item2 = SampleTable.getItem(table, v)
			println(item2)
		})
	}
}

