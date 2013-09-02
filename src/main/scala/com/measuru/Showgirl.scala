package com.measuru

import unfiltered.response.Html5
import scala.xml.{Unparsed, NodeSeq}
import java.util.Date
import java.text.{SimpleDateFormat, DateFormat}
import com.measuru.Director.Series

object Showgirl {

  def series(series: Series) = bootstrap {
    <p class="lead">
      Series with unit <b>{ series.unit }</b>. Register new values here.
    </p>
    <form class="form-inline" action={ s"${series.id}" } method="post">
      { series.measurements.map { measurement =>
        <p>
          <div class="input-append">
            <input id="v1" class="span1" type="text" placeholder={ measurement.value.toString } readonly=""/>
            <span class="add-on">{ series.unit }</span>
          </div>
          <div class="input-append" id="dp1" data-date={ format(measurement.date) } data-date-format={ dateFormat }>
            <input class="span1" size="16" type="text" value={ format(measurement.date) } readonly=""/>
            <span class="add-on"><i class="icon-calendar"></i></span>
          </div>
        </p>
      } }
      <div class="input-append">
        <input class="span1" type="text" name="value"/>
        <span class="add-on">{ series.unit }</span>
      </div>
      <div class="input-append" id="date" data-date="01-01-01" data-date-format="dd-mm-yy">
        <input name="date" class="span1" size="16" type="text" value="01-01-01"/>
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

  private lazy val dateFormat = "dd-MM-yy"
  lazy val sdf = new SimpleDateFormat(dateFormat)

  private def today = format(new Date())
  private def format(d: Date) = sdf.format(d)

}
