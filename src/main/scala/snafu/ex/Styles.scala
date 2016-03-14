package snafu.ex

import org.scalajs.dom
import org.scalajs.dom.{DOMList, Element}

import scala.language.postfixOps
import scalacss.Defaults._

object Styles extends StyleSheet.Inline {
  import dsl._

  // TODO: Should this go here? I highly doubt it, tbh.
  def applyToSelectorAll(sel: String, style: StyleA) {
    val els = dom.document.querySelectorAll(sel).asInstanceOf[DOMList[Element]]
    for ( ix <- 1 to els.length ) {
      val el = els(ix-1)
      println(el)
      el.classList add style.htmlClass
    }
  }

  val body = style(
    backgroundColor(c"#eee")
  )
}
