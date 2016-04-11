package com.github.uryyyyyyy.dynamodb.pressureTest.sequential

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

	def execute(table:Table): Unit ={

		(1 to 1000).foreach(v =>{
			val item = new Item().`with`("Id", count).`with`("memo", "memomemo")
			val res = SampleTable.putItem(table, item)
			println(s"PutItemResult ${v} = ${res}")
		})
	}
}

