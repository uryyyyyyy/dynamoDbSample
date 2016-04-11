package com.github.uryyyyyyy.dynamodb.fallback.batch

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient
import com.amazonaws.services.dynamodbv2.model.{ProvisionedThroughputExceededException, PutItemRequest}
import com.amazonaws.services.sqs.model.{ChangeMessageVisibilityRequest, DeleteMessageRequest, ReceiveMessageRequest}
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.fallback.core.{Serializer, SqsUtils}

import scala.annotation.tailrec
import scala.collection.JavaConversions._
import scala.util.Random

object ReaderUtil {

	lazy val cred = DynamoUtils.credential()
	lazy val client = SqsUtils.init(cred)
	lazy val SERIALIZER = new Serializer[PutItemRequest]
	val r = new Random
	val receiveMessageRequest = new ReceiveMessageRequest()
		.withMaxNumberOfMessages(10)
		.withWaitTimeSeconds(10)
		.withQueueUrl(SqsUtils.queueURL)

	def retry(dynamo:AmazonDynamoDBClient): Unit = {
		while(true){
			val result = client.receiveMessage(receiveMessageRequest)
			val messages = result.getMessages

			messages.foreach(m => {
				client.changeMessageVisibility(new ChangeMessageVisibilityRequest()
					.withQueueUrl(SqsUtils.queueURL)
					.withReceiptHandle(m.getReceiptHandle)
					.withVisibilityTimeout(20))

				val req = SERIALIZER.deserialize(m.getBody)
				retry(dynamo, req)

				val deleteMessageRequest = new DeleteMessageRequest()
					.withQueueUrl(SqsUtils.queueURL)
					.withReceiptHandle(m.getReceiptHandle)
				client.deleteMessage(deleteMessageRequest)
			})
		}
	}

	@tailrec
	private def retry(dynamo:AmazonDynamoDBClient, req: PutItemRequest){
		try {
			println(s"try: ${req.getItem.get("Id").getN}")
			dynamo.putItem(req)
			println(s"PutItemResult: ${req}")
		}catch{
			case e:ProvisionedThroughputExceededException =>{
				Thread.sleep(r.nextInt(30) * 100)
				retry(dynamo, req)
			}
		}
	}
}
