package com.github.uryyyyyyy.dynamodb.fallback.batch

import com.amazonaws.services.sqs.model.{ChangeMessageVisibilityRequest, DeleteMessageRequest, ReceiveMessageRequest}
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.fallback.core.{MessageCodec, Organization, Person, SqsUtils}
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

import scala.collection.JavaConversions._

object Main {

	def main(args: Array[String]) {

//		val organization = Organization(
//			"some organization",
//			List(Person("Taro", 22), Person("Hanako", 18), Person("Saburo", 25))
//		)
//
//		implicit val formats = Serialization.formats(NoTypeHints)
//		val str = MessageCodec.encode(organization)
//		println(str)
//
//		val cls = MessageCodec.decode[Organization](str)
//		println(cls)

		val cred = DynamoUtils.credential()
		val client = SqsUtils.init(cred)
		val receiveMessageRequest = new ReceiveMessageRequest(SqsUtils.queueURL).withMaxNumberOfMessages(10)
		val result = client.receiveMessage(receiveMessageRequest)
		val messages = result.getMessages

		messages.foreach(m => {
			client.changeMessageVisibility(new ChangeMessageVisibilityRequest()
				.withQueueUrl(SqsUtils.queueURL)
				.withReceiptHandle(m.getReceiptHandle)
				.withVisibilityTimeout(20))

			println(",essage in-flight start")
			Thread.sleep(10000)

			val json = m.getBody
			println(json)
			val obj = MessageCodec.decode[Organization](json)
			println(obj)



			val deleteMessageRequest = new DeleteMessageRequest()
				.withQueueUrl(SqsUtils.queueURL)
				.withReceiptHandle(m.getReceiptHandle)
			client.deleteMessage(deleteMessageRequest)
		})
		println(",essage deleted")

		client.shutdown()
	}

}