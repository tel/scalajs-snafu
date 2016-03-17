package snafu.ex

import scalacss.Defaults._
import scalacss.ext.CssReset

object Styles extends StyleSheet.Inline {
  import dsl._
  val cssReset = style(CssReset.normaliseCss)
  val warn = style(backgroundColor.red)
}
