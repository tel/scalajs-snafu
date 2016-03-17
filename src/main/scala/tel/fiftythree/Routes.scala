package tel.fiftythree

import scala.language.higherKinds
import scala.util.matching.Regex

trait Routes[Router[_]] {

}

object Routes {

  /**
    * A Representation is essentially `(String => Either[Error, A], A => String)`
    * such that parse is a retract of print, e.g. `parse(print(x)) = x`.
    */
  trait Representation[A] {
    def parse(repr: String): Either[String, A]
    def print(value: A): String
  }

  object Representation {
    import scala.util.control.Exception._

    implicit val stringHasLiteralRepresentation = new Representation[String] {
      def parse(repr: String) = Right(repr)
      def print(value: String) = value
    }

    implicit val intHasRepresentation = new Representation[Int] {
      def parse(repr: String): Either[String, Int] = {
        val catcher: Catch[Int] = catching(classOf[NumberFormatException])
        val almost: Either[Throwable, Int] = catcher either Integer.valueOf(repr)
        almost.left.map(_.toString())
      }
      def print(value: Int): String = value.toString()
    }
  }

  trait DSL[Router[_]] {
    /** Matches an exact string */
    def literal(repr: String): Router[Unit] =
      core.map[String, Unit](_ => (), _ => repr)(regex(repr.r))

    /** Matches any string that passes a regex filter */
    def regex(pattern: Regex): Router[String]

    /**
      * Provided a `Representation` of some type as a String, we check to see
      * if the next segment of the path matches.
      */
    def repr[A: Representation]: Router[A]

    /** Matches any non-empty string */
    def string: Router[String] = repr

    /** Core semantic combinators */
    val core: Core[Router]
  }

  /**
    * Core exterior properties of a Router.
    *
    * The primary namespace to use is `DSL`. These are separated out (though
    * still available) since they form the basis of operation but don't help
    * with the presentation of the DSL.
    */
  trait Core[Router[_]] {

    /**
      * Given a transform and its inverse (really, retraction) we can transform
      * a Router producing one type to a different router.
      */
    def map[A, B](f: A => B, g: B => A)(r: Router[A]): Router[B]
  }
}
