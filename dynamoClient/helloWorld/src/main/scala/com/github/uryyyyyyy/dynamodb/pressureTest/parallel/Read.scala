package com.github.uryyyyyyy.dynamodb.pressureTest.parallel

import com.amazonaws.services.dynamodbv2.document.{Item, Table}
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

object Read {

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

	def atomicCount():Int = synchronized {
		count += 1
		count
	}

	def execute(table:Table): Unit ={
		val parallel = {
			val ff = (1 to 10).toStream.map(v => () => {
				val item2 = SampleTable.getItem(table, 3)
				val c = atomicCount()
				println(item2 + c.toString)
			})
			() => MeasurementUtility.execute(ff, 10)
		}

		while (count < 5000){
			atomicCount()
			parallel()
		}
	}
}

