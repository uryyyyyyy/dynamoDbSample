package com.github.uryyyyyyy.dynamodb.spark

import java.util

import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Table}
import com.amazonaws.services.dynamodbv2.model.{AttributeValue, ProvisionedThroughputExceededException, PutItemRequest}
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.fallback.writer.WriterUtil
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object Hello {

	def getItem(table: Table, i: Int, r: Random): Unit = {
		try {
			SampleTable.getItem(table, i)
		}catch{
			case e:ProvisionedThroughputExceededException =>{
				Thread.sleep(r.nextInt(30) * 100)
				getItem(table, i, r)
			}
		}
	}

	def main(args: Array[String]): Unit = {
		val end = args(0).toInt
		val numSlices = args(1).toInt
		println("----Start----")
		val conf = new SparkConf().setAppName("Simple Application")
		val sc = new SparkContext(conf)
		val rdd = sc.range(1, end, 1, numSlices)
		//		val accessKey = sys.env("AWS_ACCESS_KEY")
		//		val secretKey = sys.env("AWS_SECRET_KEY")
		lazy val dynamo = DynamoUtils.init2()
		lazy val dynamoDB = new DynamoDB(dynamo)
		lazy val r = new Random

		println("----Start----")
		val count = rdd.map(v => {

			val item = new util.HashMap[String, AttributeValue]()
			val attr = new AttributeValue()
			attr.setN(v.toString)
			item.put("Id", attr)
			item.put("val", new AttributeValue("valueValueValue"))
			val req = new PutItemRequest(SampleTable.tableName, item)

			WriterUtil.putWithFallBackToSqs(dynamo, req, v)

			val table = SampleTable.getTable(dynamoDB)
			getItem(table, v.toInt, r)
		}).count()
		println(count)

	}
}