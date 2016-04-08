package com.github.uryyyyyyy.dynamodb.fallback.core

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.sqs.AmazonSQSAsyncClient

object SqsUtils {

	val queueURL = sys.env("SQS_URL")

	def init(credentials: AWSCredentialsProvider): AmazonSQSAsyncClient = {
		val c = new AmazonSQSAsyncClient(credentials)
		c.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1))
		c
	}
}