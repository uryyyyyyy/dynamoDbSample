package com.github.uryyyyyyy.dynamodb.sortkey

import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SortKeyTable
import org.slf4j.LoggerFactory

object Main {

	val logger = LoggerFactory.getLogger("logger_name")

	def main(args: Array[String]): Unit = {
		logger.info("start")
		val dynamoDB = DynamoUtils.init()

		val items = SortKeyTable.getItems(dynamoDB)
		items.foreach(println)
	}
}