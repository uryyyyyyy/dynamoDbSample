package com.github.uryyyyyyy.dynamodb.fallback.batch

import com.github.uryyyyyyy.dynamodb.core.DynamoUtils

object Main {

	def main(args: Array[String]) {

		val dynamoDB = DynamoUtils.init2()
		ReaderUtil.retry(dynamoDB)
	}

}