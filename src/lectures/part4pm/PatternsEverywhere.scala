package lectures.part4pm

object PatternsEverywhere extends App {

  // big idea #1
  try {
    // code
  } catch {
    case e: RuntimeException => "runtime"
    case npe: NullPointerException => "npe"
    case _ => "something else"
  }

  // Catches are actually matches
  /*
    try {
      // code
    } catch (e) {
      e match {
        case e: RuntimeException => "runtime"
        case npe: NullPointerException => "npe"
        case _ => "something else"
      }
    }
   */

  // big idea #2
  val list = List(1, 2, 3)
  val evenOnes = for {
    x <- list if x % 2 == 0 // ?!
  } yield 10 * x

  // generators are also based on pattern matching
  // all the generators that we have seen in the for-comprehensions are based on pattern matching

  val tuples = List((1, 2), (3, 4))
  val filterTuples = for {
    (first, second) <- tuples // decomposed as PM...
  } yield first * second
  // case classes, :: operators, ...

  // big idea #3
  val tuple = (1, 2, 3)
  val (a, b, c) = tuple // I can assign the tuple to a pattern, multiple value definition based on pattern matching
  println(b)
  // multiple value definitions based on PM
  // but also lists, and so on support this feature

  val head :: tail = list
  println(head)
  println(tail)

  // big idea #4
  // partial function based on pattern matching
  val mappedList = list.map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "the ONE"
    case _ => "something else"
  } // partial function literal

  // equivalent to the one above, and indeed the IDE highlights the warning
  val mappedList2 = list.map { x =>
    x match {
      case v if v % 2 == 0 => v + " is even"
      case 1 => "the ONE"
      case _ => "something else"
    }
  }
  println(mappedList)
}
