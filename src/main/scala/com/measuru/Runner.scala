package com.measuru

object Runner extends App {

  unfiltered.jetty.Http(8889).plan(new Routes).run(_ => println("server started"))

}