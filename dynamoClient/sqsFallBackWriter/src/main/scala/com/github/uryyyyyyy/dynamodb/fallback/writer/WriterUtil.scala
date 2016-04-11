package com.github.uryyyyyyy.dynamodb.fallback.writer

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.{ProvisionedThroughputExceededException, PutItemRequest}
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.fallback.core.{Serializer, SqsUtils}

object WriterUtil {

	lazy val cred = DynamoUtils.credential()
	lazy val client = SqsUtils.init(cred)
	lazy val SERIALIZER = new Serializer[PutItemRequest]

	private def enqueue(putItemRequest: PutItemRequest): Unit = {
		val str = SERIALIZER.serialize(putItemRequest)
		val request = new SendMessageRequest()
			.withQueueUrl(SqsUtils.queueURL)
			.withMessageBody(str)
		val result = client.sendMessage(request)
		println(result)
	}

	def putWithFallBackToSqs(dynamo:AmazonDynamoDBClient, req:PutItemRequest, logIndex : Any): Unit ={
		try {
			dynamo.putItem(req)
			println(s"PutItemResult ${logIndex}")
		}catch{
			case e:ProvisionedThroughputExceededException =>{
				println(s"ProvisionedThroughputExceededException: ${logIndex}")
				enqueue(req)
			}
		}
	}

}