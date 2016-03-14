package snafu.ex

import org.scalajs.dom
import org.scalajs.dom.{DOMList, Element}

import scala.language.postfixOps
import scalacss.Defaults._
import scalacss.Percentage

object Styles extends StyleSheet.Inline {
  import dsl._

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

  val common = mixin(
    backgroundColor.rgb(Percentage(95), Percentage(95), Percentage(95))
  )

  val outer = style(
    common,
    margin(12 px, auto),
    textAlign.left,
    cursor.pointer,

    &.hover(
      cursor.zoomIn
    )
  )

}
