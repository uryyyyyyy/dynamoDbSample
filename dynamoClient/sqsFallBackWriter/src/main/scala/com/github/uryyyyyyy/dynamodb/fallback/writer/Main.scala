package com.github.uryyyyyyy.dynamodb.fallback.writer

import java.util

import com.amazonaws.services.dynamodbv2.model.{AttributeValue, PutItemRequest}
import com.github.uryyyyyyy.dynamodb.core.DynamoUtils
import com.github.uryyyyyyy.dynamodb.core.table.SampleTable

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

//		val organization = Organization(
//			"some organization",
//			List(Person("Taro", 22), Person("Hanako", 18), Person("Saburo", 25))
//		)
//
//		implicit val formats = Serialization.formats(NoTypeHints)
//		val str = MessageCodec.encode(organization)
//
//		val cred = DynamoUtils.credential()
//		val client = SqsUtils.init(cred)
//		val request = new SendMessageRequest()
//			.withQueueUrl(SqsUtils.queueURL)
//			.withMessageBody(str)
//		val result = client.sendMessage(request)
//		println(result)

		def put(i:Int): Unit ={
			val dynamoDB = DynamoUtils.init2()
			val item = new util.HashMap[String, AttributeValue]()
			val attr = new AttributeValue()
			attr.setN(Integer.toString(i))
			item.put("Id", attr)
			item.put("memo", new AttributeValue("memomemo"))
			val req = new PutItemRequest(SampleTable.tableName, item)

			WriterUtil.putWithFallBackToSqs(dynamoDB, req, i)
		}

		def execute(): Unit ={

			(1 to 1000).foreach(v =>{
				put(v)
			})
		}

		execute()
	}

}