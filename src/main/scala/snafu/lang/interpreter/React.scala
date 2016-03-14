package snafu.lang.interpreter

import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.vdom.svg.prefix_<^._
import snafu.lang.Diagrams

object React extends Diagrams.Aux[Unit => ReactElement] {

  def ellipse(rx: Double, ry: Double) = { (ctx: Unit) =>
    <.ellipse(^.rx := rx, ^.ry := ry)
  }

  def rectangle(dx: Double, dy: Double) = { (ctx: Unit) =>
    <.rect(^.dx := dx, ^.dy := dy)
  }
}
