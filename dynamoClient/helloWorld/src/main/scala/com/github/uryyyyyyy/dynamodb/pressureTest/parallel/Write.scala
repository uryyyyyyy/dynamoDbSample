package com.github.uryyyyyyy.dynamodb.pressureTest.parallel

import com.amazonaws.services.dynamodbv2.document.{Item, Table}
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}
import com.typesafe.config.ConfigFactory

object Write {

	val config = ConfigFactory.load()

	var count = 0

	def main(args: Array[String]): Unit = {

		println("start")
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)

		MeasurementUtility.timeCounter(() => execute(table))
	}

	def atomicCount():Int = synchronized {
		count += 1
		count
	}

	def execute(table:Table): Unit ={
		val parallel = {
			val ff = (1 to 10).toStream.map(v => () => {
				val item = new Item().`with`("Id", count).`with`("memo", "memomemo")
				val item2 = SampleTable.putItem(table, item)
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

