package snafu.ex.eg

import japgolly.scalajs.react.ReactElement
import snafu.lang.Diagrams
import snafu.lang.interpreter.React

class Drawing1[Diagram](val LangImpl: Diagrams.Aux[Diagram]) {
  import LangImpl._
  val render = circle(10)
}

object Drawing1 extends Example {
  val el: ReactElement = Drawing1(React)(())
  def apply[Diagram](LangImpl: Diagrams.Aux[Diagram]): LangImpl.Diagram =
    new Drawing1(LangImpl).render

  val name: String = "Drawing 1"
  val code: String = "circle(10)"
}
