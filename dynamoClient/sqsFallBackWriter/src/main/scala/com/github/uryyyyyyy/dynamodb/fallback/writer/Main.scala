package com.github.uryyyyyyy.dynamodb.fallback.writer

import com.amazonaws.services.sqs.model.SendMessageRequest
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.fallback.core.{MessageCodec, Organization, Person, SqsUtils}
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

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

		val organization = Organization(
			"some organization",
			List(Person("Taro", 22), Person("Hanako", 18), Person("Saburo", 25))
		)

		implicit val formats = Serialization.formats(NoTypeHints)
		val str = MessageCodec.encode(organization)

		val cred = DynamoUtils.credential()
		val client = SqsUtils.init(cred)
		val request = new SendMessageRequest()
			.withQueueUrl(SqsUtils.queueURL)
			.withMessageBody(str)
		val result = client.sendMessage(request)
		println(result)
	}

}