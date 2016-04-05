package com.github.uryyyyyyy.dynamodb.core

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.typesafe.config.ConfigFactory

object DynamoUtils {

	val conf = ConfigFactory.load()
	lazy val s3BucketName = conf.getString("s3.bucketName")

	def init(): DynamoDB = {
		val credentials = new EnvironmentVariableCredentialsProvider()
		val client = new AmazonDynamoDBClient(credentials)
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		new DynamoDB(client)
	}
}