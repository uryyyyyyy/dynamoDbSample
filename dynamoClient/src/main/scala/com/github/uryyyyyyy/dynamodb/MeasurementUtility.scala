package com.github.uryyyyyyy.dynamodb

import java.util.concurrent.Executors

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}


object MeasurementUtility {

	def execute(fs:Stream[()=> Any], threadNum:Int ): Unit ={
		val executors = Executors.newFixedThreadPool(threadNum)
		implicit val s = ExecutionContext.fromExecutorService(executors)

		val fs_ = fs.map(f => Future(f()))
		Await.result(Future.sequence(fs_), Duration.Inf)
		s.shutdown()
	}

	def timeCounter(f: ()=> Any):Unit = {
		val now = System.currentTimeMillis()
		f()
		val millis = System.currentTimeMillis() - now
		println("%d milliseconds".format(millis))
	}
}
