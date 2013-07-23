package com.measuru

import unfiltered.filter.Plan
import unfiltered.response.ResponseString

class Routes extends Plan {

  def intent: Plan.Intent = {
    case _ => ResponseString("hello world")
  }

}

