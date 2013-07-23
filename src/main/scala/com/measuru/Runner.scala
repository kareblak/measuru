package com.measuru

import scala.util.Properties

object Runner extends App {

  val port = Properties.envOrElse("PORT", "8889").toInt

  unfiltered.jetty.Http(port).plan(new Routes).run(_ => println("server started"))

}