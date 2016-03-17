package tel.fiftythree

import tel.fiftythree.Routes.{Representation, Core}

import scala.util.matching.Regex

final case class
  RoutingPrism[A](val parses: Location => Either[RoutingError, (A, Location)],
                  val prints: A => (Location => Location))

object RoutingPrism {

  object Core extends Routes.Core[RoutingPrism] {

    def map[A, B](f: (A) => B, g: (B) => A)(r: RoutingPrism[A]): RoutingPrism[B] = {
      r.copy(
        parses = (loc: Location) => r.parses(loc) match {
          case Left(err) => Left(err)
          case Right((value, newLoc)) => Right((f(value), newLoc))
        },
        prints = (b: B) => r.prints(g(b))
      )
    }
  }

  object DSL extends Routes.DSL[RoutingPrism] {

    override def literal(repr: String): RoutingPrism[Unit] = {

      def parsesLiteral(loc: Location): Either[RoutingError, (Unit, Location)] =
        loc.uncons match {
          case None => Left(RoutingError.UnexpectedEOL)
          case Some((seg, newLoc)) =>
            if (repr == seg)
              Right(((), newLoc))
            else
              Left(RoutingError.ExpectedSegment(repr))
        }

      def printsLiteral(a: Unit)(loc: Location): Location =
        loc.cons(repr)

      RoutingPrism(
        parses = parsesLiteral,
        prints = printsLiteral
      )

    }

    def regex(pattern: Regex): RoutingPrism[String] = {

      def parsesRegex(loc: Location): Either[RoutingError, (String, Location)] =
        loc.uncons match {
          case None => Left(RoutingError.UnexpectedEOL)
          case Some((str, newLoc)) => str match {
            case pattern(_*) => Right((str, newLoc))
            case _ => {
              val reason = """does not match /%s/""".format(pattern.toString())
              Left(RoutingError.NoParse(
                found = str,
                reason = Some(reason)
              ))
            }
          }
        }

      /**
        * We ASSUME that the string that passes back this way would have
        * matched the regex. This invariant is out of our hands.
        */
      def printsRegex(a: String)(loc: Location): Location =
        loc.cons(a)

      RoutingPrism(
        parses = parsesRegex,
        prints = printsRegex
      )
    }

    val core: Core[RoutingPrism] = Core

    def repr[A: Representation]: RoutingPrism[A] = {

      val rep = implicitly[Representation[A]]

      def parsesRepr(loc: Location): Either[RoutingError, (A, Location)] =
        loc.uncons match {
          case None => Left(RoutingError.UnexpectedEOL)
          case Some((seg, newLoc)) => rep.parse(seg) match {
            case Left(error) =>
              Left(RoutingError.NoParse(found = seg, reason = Some(error)))
            case Right(value) =>
              Right((value, newLoc))
          }
        }
      def printsRepr(a: A)(loc: Location): Location =
        loc.cons(rep.print(a))

      RoutingPrism(
        parses = parsesRepr,
        prints = printsRepr
      )
    }
  }
}

