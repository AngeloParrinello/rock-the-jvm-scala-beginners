package lectures.part2oop

object CaseClasses extends App {

  // case classes are typically used to model immutable data
  // they are lightweight data structures with some boilerplate code

  /*
    equals, hashCode, toString, apply (because has already implemented a companion object), copy
    in case classes already implemented
   */
  case class Person(name: String, age: Int)

  // 1. class parameters are fields
  // meaning that I can access them directly
  val jim = new Person("Jim", 34)
  println(jim.name)

  // 2. sensible toString
  // println(instance) = println(instance.toString) // syntactic sugar
  // without the "case" keyword, the output would be something like "Person@1b6d3586"
  // instead of "Person(Jim,34)"
  println(jim.toString)
  println(jim) // same as println(jim.toString), already implemented from Java

  // 3. equals and hashCode implemented OOTB (Out Of The Box)
  val jim2 = new Person("Jim", 34)
  println(jim == jim2) // true, but without the "case" keyword, it would be false

  // 4. Case Classes have handy copy method
  val jim3 = jim.copy(age = 45) // copy method is used to create a new instance of the case class
  println(jim3)

  // 5. Case Classes have companion objects, but I can also declare my own
  // without re-define the apply method
  object Person {
    def uselessMethod(): Unit = println("Useless method")
  }
  val thePerson = Person
  val mary = Person("Mary", 23) // apply method from companion object
  println(mary)
  println(thePerson)

  // 6. Case Classes are serializable, which makes them useful in distributed systems
  // Akka

  // 7. Case Classes have extractor patterns = CCs can be used in PATTERN MATCHING
  // because under the hood implement the unapply method

  // Case Objects - similar to case classes, but without the companion object
  // because they are their own companion object
  case object UnitedKingdom {
    def name: String = "The UK of GB and NI"
  }

  /*
    Expand MyList - use case classes and case objects
   */
  abstract class MyList[+A] {
    def head: A

    def tail: MyList[A]

    def isEmpty: Boolean

    def add[B >: A](element: B): MyList[B]

    def printElements: String

    override def toString: String = "[" + printElements + "]"
  }

  case object Empty extends MyList[Nothing] {
    def head: Nothing = throw new NoSuchElementException

    def tail: MyList[Nothing] = throw new NoSuchElementException

    def isEmpty: Boolean = true

    def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

    def printElements: String = ""
  }

  case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
    def head: A = h

    def tail: MyList[A] = t

    def isEmpty: Boolean = false

    def add[B >: A](element: B): MyList[B] = new Cons(element, this)

    def printElements: String = {
      if (t.isEmpty) "" + h
      else h + " " + t.printElements
    }
  }


}
