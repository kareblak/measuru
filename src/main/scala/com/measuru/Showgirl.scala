package com.measuru

import unfiltered.response.Html5
import scala.xml.{Unparsed, NodeSeq}

object Showgirl {

  def series(unit: String) = bootstrap {
    <p class="lead">
      Series with unit <b>{ unit }</b>. Register new values here.
    </p>
    <form class="form-inline" action="measurement" method="post">
      <input type="hidden" name="unit" value={ unit }/>
      <div class="input-append">
        <input class="span1" type="text" name="measure" placeholder="n"/>
        <span class="add-on">{ unit }</span>
      </div>
      <div class="input-append" id="dp1" data-date="12-02-2012" data-date-format="dd-mm-yyyy">
        <input class="span1" size="16" type="text" value="12-02-2012"/>
        <span class="add-on"><i class="icon-calendar"></i></span>
      </div>
      <button type="submit" class="btn"><i class="icon-plus-sign"></i></button>
    </form>
  }

  val front = bootstrap {
    <p class="lead">
      This is a small sandbox for measuring and counting almost anything numerically related.
      Just enter a unit and proceed to start measuring.
      We'll try to sort out your readings for you, and present trends, changes through graphs and statistic properties.
    </p>
    <form class="form-inline" action="series" method="post">
      <input type="text" name="unit" class="input-small" placeholder="kg/m/$/.."/>
      <button type="submit" class="btn"><i class="icon-plus-sign"></i></button>
    </form>
  }

  def bootstrap(content: NodeSeq) = Html5 {
    <head>
      <title>measuru</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
      <link href="/css/bootstrap.min.css" rel="stylesheet" media="screen"/>
      <link href="/css/bootstrap-responsive.css" rel="stylesheet"/>
      <link href="/css/datepicker.css" rel="stylesheet"/>
    </head>
    <body>
      <div class="container">
        <h1>Measuru!</h1>
        { content }
      </div>
      <script src="http://code.jquery.com/jquery.js"></script>
      <script src="/js/bootstrap-datepicker.js"></script>
      <script src="/js/bootstrap.min.js"></script>
      <script src="/js/datepicker-setup.js"></script>
    </body>
  }

}
