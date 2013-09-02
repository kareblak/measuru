package com.measuru

import scala.util.Properties
import java.io.File

object Runner extends App {

  val port = Properties.envOrElse("PORT", "8888").toInt

  unfiltered.jetty.Http(port)
    .plan(Guide)
    .resources(new File("src/main/resources/").toURI.toURL)
    .run(_ => println("server started"))

}