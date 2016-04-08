package com.github.uryyyyyyy.dynamodb.sortkey

import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SortKeyTable

object Main {

	def main(args: Array[String]): Unit = {
		println("start")
		val dynamoDB = DynamoUtils.init()

		val items = SortKeyTable.getItems(dynamoDB)
		items.foreach(println)
	}
}