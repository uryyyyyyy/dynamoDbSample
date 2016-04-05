package com.github.uryyyyyyy.dynamodb.parallel

import com.github.uryyyyyyy.dynamodb.core.table.SampleTable
import com.github.uryyyyyyy.dynamodb.core.{DynamoUtils, MeasurementUtility}

object Main {
	def main(args: Array[String]): Unit = {
		val dynamoDB = DynamoUtils.init()
		val table = SampleTable.getTable(dynamoDB)
		val _item = SampleTable.getItem(table, 1)
		println("start " + _item)
		Thread.sleep(1000)
		val ss = (1 to 10).toStream.map(v => () => {
			val item = SampleTable.getItem(table, 1)
			println(item)
		})

		val f = () => MeasurementUtility.execute(ss, 10)

		MeasurementUtility.timeCounter(f)
		dynamoDB.shutdown()
	}
}