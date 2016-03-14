package snafu.ex

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom

import scala.scalajs.js.JSApp

object Main extends JSApp {
  def main(): Unit = {
    println("hi")
    ReactDOM.render(App(), dom.document.querySelector("#app"))
  }
}
