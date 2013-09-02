package com.measuru

import unfiltered.request.{PUT, POST, GET, Params}
import unfiltered.response.{InternalServerError, Redirect, Ok, BadRequest}
import unfiltered.directives._, Directives._

import Secretary._
import java.util.Date

object Director {

  case class Measurement(value: Double, date: Date)
  case class Series(id: String, unit: String, measurements: Seq[Measurement] = Nil)

  val front = for {
    _ <- GET
  } yield {
    Showgirl.front
  }

  val series = for {
    _ <- POST
    u <- param[String]("unit")
  } yield {
    Stevedore.newSeries(u)
      .map(id => Redirect(s"/series/$id"))
      .getOrElse(InternalServerError >> "couldn't create that series for ya, sis")
  }

  def putSeries(id: String) = for {
    _ <- POST
    v <- param[BigDecimal]("value")
    d <- param[Date]("date")
  } yield {
    Stevedore.addMeasurement(id, v, d)
      .map(id => Redirect(s"/series/$id"))
      .getOrElse(InternalServerError >> "couldn't create that series for ya, sis")
  }

  def getSeries(id: String) = for {
    _ <- GET
  } yield {
    Stevedore.fetchSeries(id)
      .map(Showgirl.series)
      .getOrElse(BadRequest >> missing("correct id"))
  }

  def series(id: String) = request[Any].flatMap { _ =>
    getSeries(id) | putSeries(id)
  }

  def param[A : StringParser](name: String): Directive[Any, Any, A] = request[Any].flatMap {
    case Params(p) =>
      val paramOpt = for {
        param <- p.get(name)
        head  <- param.headOption
        parse <- StringParser.getAs[A](head)
      } yield parse
      getOrElse(paramOpt, BadRequest >> missing(name))
  }

}
