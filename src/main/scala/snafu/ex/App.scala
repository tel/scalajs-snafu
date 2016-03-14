package snafu.ex

import japgolly.scalajs.react.{ReactElement, ReactComponentB}
import japgolly.scalajs.react.vdom.prefix_<^._

trait Lang {
  type Diagram

  def ellipse(rx: Double, ry: Double): Diagram
  def circle(r: Double): Diagram = ellipse(r, r)
}

object Lang {
  abstract class Aux[D] extends Lang { type Diagram = D }
}

object Interpret extends Lang.Aux[Unit => ReactElement] {

  def ellipse(rx: Double, ry: Double) = { (ctx: Unit) =>
    <.svg.ellipse(^.svg.rx := rx, ^.svg.ry := ry)
  }
}


class Drawing[Diagram](val LangImpl: Lang.Aux[Diagram]) {
  import LangImpl._
  val render = circle(10)
}

object Drawing {
  def el: ReactElement = Drawing(Interpret)(())
  def apply[Diagram](LangImpl: Lang.Aux[Diagram]): LangImpl.Diagram = new Drawing(LangImpl).render
}

object App {

  case class Props(width: Int, height: Int)

  val Component = ReactComponentB[Props]("App container")
    .renderP { case (scope, Props(width, height)) =>
      <.svg.svg(
        ^.svg.width := width,
        ^.svg.height := height,
        Drawing.el
      )
    }
    .build

  def apply() = Component(Props(width = 500, height = 500))

}
