package com.github.uryyyyyyy.dynamodb.sortkey

import com.github.uryyyyyyy.dynamodb.DynamoUtils

object Main {
	def main(args: Array[String]): Unit = {
		val dynamo = DynamoUtils.init()

		val ss = DynamoUtils.getItem(dynamo)
		println(ss)
	}
}