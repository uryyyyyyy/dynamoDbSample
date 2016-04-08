package com.github.uryyyyyyy.dynamodb.partitionKey

import java.math.BigDecimal

import com.amazonaws.services.dynamodbv2.document.Item
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable

object Main {

	def main(args: Array[String]): Unit = {
		println("start")
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)

		val item = new Item().`with`("Id", 3).`with`("memo", "memomemo")
		SampleTable.putItem(table, item)

		val item2 = SampleTable.getItem(table, 3)
		println(item2)
		println(item2.get("Id").asInstanceOf[BigDecimal])
		println(item2.get("memo").asInstanceOf[String])
	}
}