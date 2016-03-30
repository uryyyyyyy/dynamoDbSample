package com.github.uryyyyyyy.dynamodb

import java.util

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item, PrimaryKey}

object DynamoUtils {

	def getItem(dynamoDB: DynamoDB): Item ={
		val table = dynamoDB.getTable("sample")
		val key = new PrimaryKey("id", 1)
		table.getItem(key)
	}

	def atomicCount(dynamoDB: DynamoDB): Unit ={
		val table = dynamoDB.getTable("sample")

		val expressionAttributeNames = new util.HashMap[String,String]()
		expressionAttributeNames.put("#p", "pageCount")

		val expressionAttributeValues = new util.HashMap[String,Object]()
		val num = 1.asInstanceOf[Object]
		expressionAttributeValues.put(":val", num)

		val outcome = table.updateItem(
			"id", 1,
			"set #p = #p + :val",
			expressionAttributeNames,
			expressionAttributeValues)

		println(outcome)
	}

	def init(): DynamoDB = {
		val credentials = new EnvironmentVariableCredentialsProvider()
		val client = new AmazonDynamoDBClient(credentials)
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		new DynamoDB(client)
	}
}