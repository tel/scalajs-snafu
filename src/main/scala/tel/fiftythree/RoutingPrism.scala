package tel.fiftythree

import scala.util.matching.Regex
import tel.fiftythree.Tuples.Composition

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

    def unit[A](a: A): RoutingPrism[A] =
      RoutingPrism(
        parses = (loc: Location) => Right((a, loc)),
        prints = (_: A) => identity[Location]
      )

    override def pairFlat[A, B]
      (ra: RoutingPrism[A], rb: RoutingPrism[B])
      (implicit c: Composition[A, B]): RoutingPrism[c.C] = {

      RoutingPrism(

        // We can't write this as an locally bound function because of
        // something about not being able to pass a function in with a
        // "dependent" (path-dependent) type. We also can't use
        // for-comprehension syntax because the inferencer breaks hard. :(
        //
        // Also this is a really nice monad transformer situation. So maybe
        // things would work better if we just implemented a new type with
        // its own `flatMap` implementation.
        parses = (loc: Location) =>
          ra.parses(loc) match {
            case Left(err) => Left(err)
            case Right((a, newLoc)) =>
              rb.parses(newLoc) match {
                case Left(err) => Left(err)
                case Right((b, newNewLoc)) =>
                  Right((c.smash(a, b), newNewLoc))
              }
          },

        // We define things locally here as well just for parallelism with
        // `parses`.
        prints = (x: c.C) => rb.prints(c._2(x)) andThen ra.prints(c._1(x))
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

    val core: Routes.Core[RoutingPrism] = Core

    def represented[A](rep: Representation[A]): RoutingPrism[A] = {

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

