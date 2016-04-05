package com.github.uryyyyyyy.dynamodb.core.table

import java.util

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Item}

import scala.collection.mutable.ListBuffer

object SortKeyTable {

	val tableName = "sample"

	val id = ("Id", classOf[Int])
	val sortKey = ("UId", classOf[String])

	def getItems(dynamoDB: DynamoDB): Seq[Item] ={
		val table = dynamoDB.getTable(tableName)

		val spec = new QuerySpec()
			.withKeyConditionExpression("Id = :v_id")
			.withValueMap(new ValueMap()
				.withInt(":v_id", 1))

		val items = table.query(spec)

		val iterator = items.iterator()
		val list = ListBuffer[Item]()
		while (iterator.hasNext()) {
			val item = iterator.next()
			list += item
		}
		list
	}

	def atomicCount(dynamoDB: DynamoDB): Unit ={
		val table = dynamoDB.getTable(tableName)

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