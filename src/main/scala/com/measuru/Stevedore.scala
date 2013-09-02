package com.measuru

import scala.util.Properties
import com.measuru.Director._
import java.util.Date

object Stevedore {

  import com.mongodb.casbah.Imports._

  type where = MongoDBObject

  val MongoSetting(db) = Properties.envOrNone("MONGOLAB_URI")

  def newSeries(unit: String): Option[String] = saveSeries(Series("", unit, Nil))

  def saveSeries(series: Series) = {
    val o = MongoDBObject(
      "unit" -> series.unit,
      "measurements" -> series.measurements.map { measurement =>
        MongoDBObject(
          "value" -> measurement.value,
          "date"  -> measurement.date
        )}
    )
    db("series") += o
    o.getAs[ObjectId]("_id").map(_.toString)
  }

  def addMeasurement(seriesId: String, value: BigDecimal, date: Date) = {
    fetchSeries(seriesId).map { series =>
      val q = MongoDBObject("_id" -> new ObjectId(seriesId))
      val o = $addToSet("measurements" -> MongoDBObject(
        "value" -> value.toDouble,
        "date"  -> date
      ))
      db("series").update(q, o)
      seriesId
    }
  }

  def fetchSeries(id: String) = {
    for {
      obj   <- db("series").findOneByID(new ObjectId(id))
      id    <- obj.getAs[ObjectId]("_id").map(_.toString)
      unit  <- obj.getAs[String]("unit")
      msmnt <- obj.getAs[Seq[BasicDBObject]]("measurements")
    } yield {
      val measurements = msmnt.map { m =>
        for {
          v <- m.getAs[Double]("value")
          d <- m.getAs[Date]("date")
        } yield Measurement(v, d)
      }.flatten
      Series(id, unit, measurements)
    }
  }

  object MongoSetting {
    def unapply(url: Option[String]): Option[MongoDB] = {
      val regex = """mongodb://(\w+):(\w+)@([\w|\.]+):(\d+)/(\w+)""".r
      url match {
        case Some(regex(u, p, host, port, dbName)) =>
          val db = MongoConnection(host, port.toInt)(dbName)
          db.authenticate(u, p)
          Some(db)
        case _ => Some(MongoConnection("127.0.0.1", 27017)("test"))
      }
    }
  }

}
