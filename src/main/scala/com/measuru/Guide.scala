package com.measuru

import unfiltered.filter.Plan
import javax.servlet.http.HttpServletRequest
import unfiltered.filter.request.ContextPath
import unfiltered.directives._
import unfiltered.request.Seg

object Guide extends Plan {

  val Intent = Directive.Intent[HttpServletRequest, String] {
    case ContextPath(_, path) => path
  }

  def intent = Intent {
    case Seg(Nil)             => Director.front
    case Seg(List("series"))  => Director.series
  }


}

