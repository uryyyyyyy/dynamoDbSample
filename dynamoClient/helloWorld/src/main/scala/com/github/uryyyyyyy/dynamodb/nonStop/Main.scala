package com.github.uryyyyyyy.dynamodb.nonStop

import com.amazonaws.services.dynamodbv2.document.{Item, Table}
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Main {

	val logger = LoggerFactory.getLogger("logger_name")
	val config = ConfigFactory.load()

	var count = 0

	def main(args: Array[String]): Unit = {
		val sleepMillis = sys.env("sleepMillis").toInt

		logger.info("start")
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)

		val item = new Item().`with`("Id", 3).`with`("memo", "memomemo")
		SampleTable.putItem(table, item)

		val ff = (1 to 10).toStream.map(v => () => {
			val item2 = SampleTable.getItem(table, 3)
			println(item2)
		})

		val sequence = () => {
			val item = SampleTable.getItem(table, 3)
			println(item + count.toString)
		}

		val parallel = {
			val ff = (1 to 10).toStream.map(v => () => {
				val item2 = SampleTable.getItem(table, 3)
				println(item2 + count.toString)
			})

			() => MeasurementUtility.execute(ff, 10)
		}

		while (true){
			count += 1
			parallel()
			Thread.sleep(sleepMillis)
		}
	}
}

