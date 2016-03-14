package snafu.ex

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import snafu.ex.eg.Drawing1

object App {

  case class Props(width: Int, height: Int)

  val Component = ReactComponentB[Props]("App container")
    .renderP { case (scope, Props(width, height)) =>
      <.svg.svg(
        ^.svg.width := width,
        ^.svg.height := height,
        Drawing1.el
      )
    }
    .build

  def apply() = Component(Props(width = 500, height = 500))

}
