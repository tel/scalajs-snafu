package snafu.ex

import japgolly.scalajs.react.extra.router._
import org.scalajs.dom
import org.scalajs.dom.Element

import scala.scalajs.js.JSApp
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss.mutable.GlobalRegistry

object Main extends JSApp {
  def main() {

    val baseUrl =
      ( BaseUrl.fromWindowOrigin
        / "target" / "scala-2.11" / "classes" / "index-dev.html"
        )

    val router = Router(baseUrl, RouterConfig().logToConsole)

    // Injects the stylesheet
    GlobalRegistry.addToDocumentOnRegistration()
    GlobalRegistry.register(Styles)

    val appRoot: Element = dom.document.querySelector("#app")
    router() render appRoot
  }
}
