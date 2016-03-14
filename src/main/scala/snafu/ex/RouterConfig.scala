package snafu.ex

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.extra.Reusability
import japgolly.scalajs.react.extra.router._
import japgolly.scalajs.react.vdom.prefix_<^._

object RouterConfig {

  sealed trait Location
  case object Home extends Location

  def apply() =
    RouterConfigDsl[Location].buildConfig { dsl =>
      import dsl._

      val core: Rule =
        ( emptyRule
        | staticRoute(root, Home) ~> render(App())
        )

      core
        .notFound(redirectToPage(Home)(Redirect.Replace))
        .renderWith(layout)
        .verify(Home)
    }

  def layout(c: RouterCtl[Location], r: Resolution[Location]) =
    <.div(
      navMenu(c),
      <.div(^.cls := "container", r.render()))

  val navMenu = ReactComponentB[RouterCtl[Location]]("Menu")
    .render_P { ctl =>
      def nav(name: String, target: Location) =
        <.li(
          ^.cls := "navbar-brand active",
          ctl setOnClick target,
          name)
      <.div(
        ^.cls := "navbar navbar-default",
        <.ul(
          ^.cls := "navbar-header",
          nav("Home", Home)))
    }
    .configure(Reusability.shouldComponentUpdate)
    .build
}
