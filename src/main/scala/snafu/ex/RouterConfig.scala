package snafu.ex

import japgolly.scalajs.react.extra.router._
import snafu.ex.eg.Drawing1

object RouterConfig {

  def apply() =
    RouterConfigDsl[Location].buildConfig { dsl =>
      import Layout.layout
      import dsl._

      val core: Rule =
        ( emptyRule
        | staticRoute(root, Home) ~> render(App())
        )

      core
        .notFound(redirectToPage(Home)(Redirect.Replace))
        .renderWith(layout)
        .verify(Home, Eg(Drawing1))
    }

}
