package com.measuru

import unfiltered.response._
import scala.util._
import unfiltered.directives._, Directives._
import javax.servlet.http.HttpServletRequest

object Secretary {

  def tryOrElse[A](a: => A, b: => ResponseFunction[Any]) = Try(a).map(success).getOrElse(b)

  implicit class RichResponse(rf: ResponseFunction[Any]) {
    def >>(msg: String) = rf ~> PlainTextContent ~> ResponseString(msg)
  }

  def missing(s: String) = {
    val msgs = List(
      "Provide %s, fool!",
      "Gief %s, lollersk8z!",
      "Where... is... the %s?"
    )
    Random.shuffle(msgs).head.format(s)
  }

  def host = request[HttpServletRequest].map { req =>
    val schema = req.underlying.getScheme;
    val serverName = req.underlying.getServerName;
    val serverPort = req.underlying.getServerPort match {
      case 80 if schema == "http"   => ""
      case 443 if schema == "https" => ""
      case p                        => ":" + p
    }
    val contextPath = Option(req.underlying.getContextPath).getOrElse("")
    s"$schema://$serverName$serverPort$contextPath"
  }

  implicit class RichDirOfString(val d: Directive[HttpServletRequest, Any, String]) extends AnyVal {
    def /(s: String) = d.map(str => s"$str/$s")
  }

  implicit class RichString(val s: String) extends AnyVal {
    def qp(qp: (String, String)) = if (s.contains("?")) s"$s&${qp._1}=${qp._2}" else s"$s?${qp._1}=${qp._2}"
  }
}
