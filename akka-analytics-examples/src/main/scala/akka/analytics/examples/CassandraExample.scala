package akka.analytics.examples

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import akka.analytics.cassandra._

object CassandraExample extends App {
  val conf = new SparkConf()
    .setAppName("CassandraExample")
    .setMaster("local[4]")
    .set("spark.cassandra.connection.host", "127.0.0.1")
    .set("spark.cassandra.journal.keyspace", "akka") // optional, defaults to "akka"
    .set("spark.cassandra.journal.table", "messages") // optional, defaults to "messages"
  val sc = new SparkContext(conf)

  val rdd: RDD[(JournalKey, Any)] = sc.eventTable().cache()

  println("Unsorted:")
  rdd.collect().foreach(println)
  println("Sorted:")
  rdd.sortByKey().collect().foreach(println)
}
