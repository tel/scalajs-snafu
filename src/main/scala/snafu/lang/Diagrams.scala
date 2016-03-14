package snafu.lang

trait Diagrams {
  type Diagram

  def ellipse(rx: Double, ry: Double): Diagram
  def rectangle(dx: Double, dy: Double): Diagram

  def circle(r: Double): Diagram = ellipse(r, r)
}

object Diagrams {

  /**
    * The Aux pattern allows us to talk about type members as type parameters.
    * It turns out that this makes it easier to define interpreters of Diagrams.
    * It's possible to do so without using an Aux pattern, BUT for some reason
    * this seems to trigger a Scala.js bug.
    */
  abstract class Aux[D] extends Diagrams { type Diagram = D }
}
