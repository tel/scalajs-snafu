package snafu.ex.eg

import japgolly.scalajs.react.ReactElement

trait Example {
  val name: String
  val description: Option[String] = None
  val code: String
  val el: ReactElement
}
