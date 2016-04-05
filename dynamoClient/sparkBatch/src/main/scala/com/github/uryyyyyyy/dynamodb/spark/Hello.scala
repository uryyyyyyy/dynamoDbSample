package com.github.uryyyyyyy.dynamodb.spark

import com.amazonaws.services.dynamodbv2.document.Item
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import org.apache.spark.{SparkConf, SparkContext}
import org.slf4j.LoggerFactory

object Hello {

	def main(args: Array[String]): Unit = {
		println("----Start----")
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
		val rdd = sc.range(1, 10000, 1, 10)
		val accessKey = sys.env("AWS_ACCESS_KEY")
		val secretKey = sys.env("AWS_SECRET_KEY")
		val file = args(0)
		lazy val dynamo = DynamoUtils.setupDynamoClientConnection(accessKey, secretKey)

		println("----Start----")
		rdd.map(v => {
			val table = SampleTable.getTable(dynamo)

			val item = new Item().`with`("Id", v).`with`("memo", "memomemo")
			SampleTable.putItem(table, item)
			SampleTable.getItem(table, v.toInt)
		}).saveAsTextFile(file)

	}
}