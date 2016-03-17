package snafu.ex.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.extra.router.RouterCtl
import japgolly.scalajs.react.vdom.prefix_<^._
import snafu.ex._
import snafu.ex.eg.Drawing1

object MainMenu {

  case class Props(routing: RouterCtl[Location])
  type State = Unit

  case class Item(name: String, loc: Location)

  val entries = Seq(
    Item("Home", Home),
    Item("E.g. Drawing 1", Eg(Drawing1))
  )

  val component = ReactComponentB[Props]("Main menu")
    .render { scope =>
      val routing = scope.props.routing
      <.nav(
        <.ul(
          entries collect {
            case Item(name, loc) => {
              val link = routing.link(loc)
              <.li(link(name))
            }
          }
        )
      )
    }
    .build

  def apply(c: RouterCtl[Location]) = component.apply(Props(routing = c))

}
