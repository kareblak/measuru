package com.measuru

import unfiltered.filter.Plan
import unfiltered.directives._, Directives._
import unfiltered.request.{Seg, Path}

object Guide extends Plan {

  def intent: Plan.Intent = Path.Intent {
    case Seg(Nil) | Seg("" ::Nil)   => Director.front
    case Seg("series" :: Nil)       => Director.series
    case Seg("series" :: id :: Nil) => Director.series(id)
  }

}

