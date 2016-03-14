package snafu.ex

import japgolly.scalajs.react.extra.router._
import org.scalajs.dom
import snafu.ex.RouterConfig.Location
import scalacss.ScalaCssReact._
import scalacss.Defaults._

import scala.scalajs.js.JSApp

object Main extends JSApp {
  def main() {

    val baseUrl =
      ( BaseUrl.fromWindowOrigin
        / "target" / "scala-2.11" / "classes" / "index-dev.html"
        )

    val router: Router[Location] = Router(baseUrl, RouterConfig().logToConsole)

    // Injects the stylesheet
    Styles.addToDocument()
    Styles.applyToSelectorAll("body", Styles.body)

    router() render dom.document.querySelector("#app")
  }
}
