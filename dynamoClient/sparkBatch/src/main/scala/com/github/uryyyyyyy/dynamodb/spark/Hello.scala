package com.github.uryyyyyyy.dynamodb.spark

import com.amazonaws.services.dynamodbv2.document.Item
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import org.apache.spark.{SparkConf, SparkContext}

object Hello {

	def main(args: Array[String]): Unit = {
		val end = args(0).toInt
		val numSlices = args(1).toInt
		println("----Start----")
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
		val rdd = sc.range(1, end, 1, numSlices)
		val accessKey = sys.env("AWS_ACCESS_KEY")
		val secretKey = sys.env("AWS_SECRET_KEY")
		val file = args(2)
		lazy val dynamo = DynamoUtils.init(accessKey, secretKey)

		println("----Start----")
		val count = rdd.map(v => {
			val table = SampleTable.getTable(dynamo)

			val item = new Item().`with`("Id", v).`with`("val", "valueValueValue")
			SampleTable.putItem(table, item)
			SampleTable.getItem(table, v.toInt)
		}).count()
		println(count)

	}
}