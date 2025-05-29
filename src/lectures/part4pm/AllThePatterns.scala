package lectures.part4pm

object AllThePatterns extends App {

  // 1 - constants
  // Switch on steroids
  val x: Any = "Scala"
  val constants = x match {
    case 1 => "a number"
    case "Scala" => "THE Scala"
    case true => "The truth"
    case AllThePatterns => "A singleton object"
  }

  // 2 - match anything
  // 2.1 wildcards
  val matchAnything = x match {
    case _ =>
  }

  // 2.2 variable
  val matchAVariable = x match {
    case something => s"I've found $something"
  }

  // 3 - tuples
  val aTuple = (1, 2)
  val matchATuple = aTuple match {
    case (1, 1) =>
    case (something, 2) => s"I've found $something"
  }

  val nestedTuple = (1, (2, 3))
  lazy val matchANestedTuple = nestedTuple match {
    case (_, (2, v)) => ???
  }
  // PMs can be NESTED!

  // 4 - case classes - constructor pattern
  // PMs can be nested with case classes as well
  val aList: List[Int] = List(1, 2, 3)
  val matchAList = aList match {
    case List(1, _, _) => // extractor - advanced
    case List(1, _*) => // list of arbitrary length - advanced
    case 1 :: List(_) => // infix pattern
    case List(1, 2, 3) :+ 42 => // infix pattern
  }

  // 5 - list patterns
  val aStandardList = List(1, 2, 3, 42)
  val standardListMatching = aStandardList match {
    case List(1, _, _, _) => // extractor - advanced
    case List(1, _*) => // list of arbitrary length - advanced
    case 1 :: List(_) => // infix pattern
    case List(1, 2, 3) :+ 42 => // infix pattern
  }

  // 6 - type specifiers
  val unknown: Any = 2
  val unknownMatch = unknown match {
    case int: Int => // explicit type specifier
    case list: List[Int] => // explicit type specifier
    case map: Map[Int, Int] => // explicit type specifier
  }

  // 7 - name binding
  lazy val nameBindingMatch = aList match {
    case nonEmptyList @ List(1, _*) => {
      ???
      nonEmptyList
    }// name binding => use the name later(here)
    case List(1, rest @ _*) => {
      ???
      rest
    }// name binding inside nested patterns
  }

  // 8 - multi-patterns
  val multipattern = aList match {
    case List(1, _*) | List(_, 2, _, _) => // compound pattern (multi-pattern)
  }

  // 9 - if guards
  lazy val secondElementSpecial = aList match {
    case List(_, second, _) if second % 2 == 0 => ???
  }

  /*
    Question.
   */
  val numbers = List(1, 2, 3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => "a list of strings"
    case listOfNumbers: List[Int] => "a list of numbers"
    case _ => ""
  }
  println(numbersMatch)
  /*
  This will print "a list of strings" because of type erasure.
  This is not Scala's fault. The JVM was designed with backwards compatibility in mind,
   so it was not possible to add generics to the JVM. And to make generics work in Scala,
   Scala had to do some tricks. One of these tricks is type erasure.
   So Scala sees the match-case like this:
     val numbersMatch = numbers match {
    case listOfStrings: List=> "a list of strings"
    case listOfNumbers: List => "a list of numbers"
    case _ => ""
  }
  And the IDE is warning us about this. It is telling us that the pattern type is unchecked since it is eliminated by erasure.
  Saying: fruitless type test: a value of type List[Int] cannot also be a List[String](but still might match its erasure)
   */

}
