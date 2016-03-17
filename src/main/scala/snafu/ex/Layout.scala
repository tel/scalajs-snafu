package snafu.ex

import snafu.ex.{components => c}
import japgolly.scalajs.react.ReactElement
import japgolly.scalajs.react.extra.router.{Resolution, RouterCtl}
import japgolly.scalajs.react.vdom.prefix_<^._

import scala.language.postfixOps
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry

object Layout {

  object Styles extends StyleSheet.Inline {
    import dsl._

    val cls = style(
      width(800 px),
      height(200 px),
      display.block,
      backgroundColor.blue
    )
  }

  GlobalRegistry.register(Styles)

  def layout(routing: RouterCtl[Location],
             r: Resolution[Location]):
  ReactElement = {
    <.div(
      <.div(^.id := "nav", c.MainMenu(routing)),
      <.div(Styles.cls, r.render()))
  }

}

