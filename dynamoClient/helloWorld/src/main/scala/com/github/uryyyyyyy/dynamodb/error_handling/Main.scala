package com.github.uryyyyyyy.dynamodb.error_handling

import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable

object Main {
	def main(args: Array[String]): Unit = {
		val dynamo = DynamoUtils.init()

		val table = SampleTable.getTable(dynamo)
		val item = SampleTable.getItem(table, 1)
		println(item)
	}
}