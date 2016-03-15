package com.github.uryyyyyyy.aerospike

import java.util
import java.util.ArrayList

import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.{DynamoDB, Table}
import com.amazonaws.services.dynamodbv2.model._

object Main {
	def main(args: Array[String]) {
		val dynamoDB = new DynamoDB(new AmazonDynamoDBClient(new ProfileCredentialsProvider))
		val attributeDefinitions: ArrayList[AttributeDefinition] = new ArrayList[AttributeDefinition]
		attributeDefinitions.add(new AttributeDefinition().withAttributeName("Id").withAttributeType("N"))
		val keySchema: util.ArrayList[KeySchemaElement] = new ArrayList[KeySchemaElement]
		keySchema.add(new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH))
		val request: CreateTableRequest = new CreateTableRequest().withTableName(tableName)
			.withKeySchema(keySchema)
			.withAttributeDefinitions(attributeDefinitions)
			.withProvisionedThroughput(new ProvisionedThroughput().withReadCapacityUnits(5L)
				.withWriteCapacityUnits(6L))
		val table: Table = dynamoDB.createTable(request)
		table.waitForActive
	}
}

