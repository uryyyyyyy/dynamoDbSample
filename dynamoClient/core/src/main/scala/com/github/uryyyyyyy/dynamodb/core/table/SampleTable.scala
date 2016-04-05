package com.github.uryyyyyyy.dynamodb.core.table

import java.util

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item, PrimaryKey, Table}
import com.typesafe.config.ConfigFactory

object SampleTable {

	val tableName = "sample2"

	val id = ("Id", classOf[Int])

	def getTable(dynamoDB: DynamoDB): Table ={
		dynamoDB.getTable(tableName)
	}

	def getItem(table: Table, _id : Int): Item ={
		val key = new PrimaryKey(id._1, _id)
		table.getItem(key)
	}

	def putItem(table: Table, item: Item): Unit ={
		table.putItem(item)
	}

	def atomicCount(table: Table): Unit ={

		val expressionAttributeNames = new util.HashMap[String,String]()
		expressionAttributeNames.put("#p", "pageCount")

		val expressionAttributeValues = new util.HashMap[String,Object]()
		val num = 1.asInstanceOf[Object]
		expressionAttributeValues.put(":val", num)

		val outcome = table.updateItem(
			id._1, 1,
			"set #p = #p + :val",
			expressionAttributeNames,
			expressionAttributeValues)

		println(outcome)
	}
}