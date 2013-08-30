package com.measuru

import dispatch.classic.{Http, Handler}

object Postman {

  def dispatch[A](h: => Handler[A]) = {
    val http = new Http
    try { http(h) }
    finally { http.shutdown() }
  }

}
