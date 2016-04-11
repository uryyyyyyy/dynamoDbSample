package com.github.uryyyyyyy.dynamodb.core

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.{AWSCredentialsProvider, BasicAWSCredentials, EnvironmentVariableCredentialsProvider}
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.typesafe.config.ConfigFactory

object DynamoUtils {

	val conf = ConfigFactory.load()
	lazy val s3BucketName = conf.getString("s3.bucketName")

	def credential(): AWSCredentialsProvider = {
		new EnvironmentVariableCredentialsProvider()
	}

	def init(): DynamoDB = {
		val credentials = new EnvironmentVariableCredentialsProvider()
		//set withMaxErrorRetry for causing ProvisionedThroughputExceededException
		val client = new AmazonDynamoDBClient(credentials, new ClientConfiguration().withMaxErrorRetry(0))
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		new DynamoDB(client)
	}

	def init2(): AmazonDynamoDBClient = {
		val credentials = new EnvironmentVariableCredentialsProvider()
		//set withMaxErrorRetry for causing ProvisionedThroughputExceededException
		val client = new AmazonDynamoDBClient(credentials, new ClientConfiguration().withMaxErrorRetry(0))
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		client
	}

	def init(accessKey:String, secretKey:String): DynamoDB = {
		val credentials = new BasicAWSCredentials(accessKey, secretKey)
		val client = new AmazonDynamoDBClient(credentials)
		client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		new DynamoDB(client)
	}
}