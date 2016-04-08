package com.github.uryyyyyyy.dynamodb.fallback.core

import org.json4s._
import org.json4s.jackson.{JsonMethods, Serialization}

object MessageCodec {

	implicit val formats = Serialization.formats(NoTypeHints)

	def encode[A <: AnyRef : Manifest](a: A): String = Serialization.write(a)
	def decode[A <: AnyRef : Manifest](a: String): A = JsonMethods.parse(a).extract[A]
}

case class Organization(name: String, persons: List[Person])

case class Person(name: String, age: Int)