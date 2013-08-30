package com.measuru

import unfiltered.request.{POST, GET, Params}
import unfiltered.response.BadRequest
import unfiltered.directives._, Directives._

import Secretary._

object Director {

  val front = for {
    _ <- GET
  } yield {
    Showgirl.front
  }

  val series = for {
    _ <- POST
    p <- param("unit")
  } yield {
    Showgirl.series(p)
  }

  def param(name: String) = request[Any].flatMap {
    case Params(p) =>
      getOrElse(p.get(name).flatMap(_.headOption), BadRequest >> missing(name))
  }

}
