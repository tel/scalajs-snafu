package snafu.ex

import snafu.ex.eg.Example

sealed trait Location
case object Home extends Location
case class Eg(it: Example) extends Location
