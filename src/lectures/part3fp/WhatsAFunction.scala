package lectures.part3fp

object WhatsAFunction extends App {
  // DREAM: use functions as first class elements
  // problem: oop

  // in Java we would do something like..
  class Action {
    def execute(element: Int): String = s"executing $element"
  }

  // and to make it more generic we would do something like..
  trait ActionTrait[A, B] {
    def execute(element: A): B
  }

  // a better designed, more functional approach would be..
  trait MyFunction[A, B] {
    def apply(element: A): B
  }

  // doubler works as a function!
  val doubler = new MyFunction[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }
  // call it like a function but it is an object
  println(doubler(2)) // doubler.apply(2)

  // Scala has OOTB (out of the box) function types
  // function types = Function1[A, B]
  // by default Scala supports until Function22
  // so like Function22[A, B, C, D, E, F, G, ...]
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }
  println(stringToIntConverter("3") + 4)

  // Scala has a syntactic sugar for FunctionX
  // in this below case is Function2 --> (Int, Int) => Int
  val adder: ((Int, Int) => Int) = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }
  // Function types Function2[A, B, R] === (A, B) => R

  // ALL SCALA FUNCTIONS ARE OBJECTS!! OR INSTANCES OF CLASSES DERIVING FROM
  // FUNCTION1, FUNCTION2, ETC. BECAUSE JVM WAS NOT INITIALLY DESIGNED TO SUPPORT
  // FUNCTIONAL PROGRAMMING

  /*
    1. a function which takes 2 strings and concatenates them
    2. transform the MyPredicate and MyTransformer into function types
    3. define a function which takes an int and returns another function which takes an int and returns an int
       - what's the type of this function
       - how to do it
   */

  // 1.
  val concatenator: (String, String) => String = new Function2[String, String, String] {
    override def apply(a: String, b: String): String = a + b
  }
  println(concatenator("Hello, ", "Scala"))

  // 2.

  def myPredicate[T](boolean: Boolean): T => Boolean = new Function1[T, Boolean] {
    override def apply(elem: T): Boolean = boolean
  }

  def myTransformer[A, B](transform: A => B): A => B = new Function1[A, B] {
    override def apply(elem: A): B = transform(elem)
  }

  abstract class MyList[+A] {
    def head: A

    def tail: MyList[A]

    // bounded types: A is a super type of B
    def add[B >: A](element: B): MyList[B]

    def printElements: String

    def isEmpty: Boolean

    override def toString: String = "[" + printElements + "]"
    // these below are now called higher order functions!
    // functions that either receive functions as parameters or return functions as results
    def map[B](transformer: A => B): MyList[B]

    def filter(predicate: A => Boolean): MyList[A]

    def flatMap[B](transformer: A => MyList[B]): MyList[B]

    def append[B >: A](list: MyList[B]): MyList[B]
  }

  object Empty extends MyList[Nothing] {
    def head: Nothing = throw new NoSuchElementException

    def tail: MyList[Nothing] = throw new NoSuchElementException

    def isEmpty: Boolean = true

    def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

    def printElements: String = ""

    override def map[B](transformer: Nothing => B): MyList[B] = Empty

    override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

    override def flatMap[B](transformer: Nothing => MyList[B]): MyList[B] = Empty

    override def append[B >: Nothing](list: MyList[B]): MyList[B] = list
  }

  class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
    def head: A = h

    def tail: MyList[A] = t

    def isEmpty: Boolean = false

    def add[B >: A](element: B): MyList[B] = new Cons(element, this)

    override def printElements: String = {
      if (t.isEmpty) "" + h
      else h + " " + t.printElements
    }

    /*
    [1,2,3].map(n * 2)
    = new Cons(2, [2,3].map(n * 2))
    = new Cons(2, new Cons(4, [3].map(n * 2)))
    = new Cons(2, new Cons(4, new Cons(6, Empty.map(n * 2))))
    = new Cons(2, new Cons(4, new Cons(6, Empty)))
     */
    override def map[B](transformer: A => B): MyList[B] =
      new Cons(transformer(h), t.map(transformer))

    /*
    [1,2,3].filter(n % 2 == 0)
    = [2,3].filter(n % 2 == 0)
    = new Cons(2, [3].filter(n % 2 == 0))
    = new Cons(2, Empty.filter(n % 2 == 0))
    = new Cons(2, Empty)
     */
    override def filter(predicate: A => Boolean): MyList[A] =
      if (predicate(h)) new Cons(h, t.filter(predicate))
      else t.filter(predicate)

    override def append[B >: A](list: MyList[B]): MyList[B] =
      new Cons(h, t.append(list))

    override def flatMap[B](transformer: A => MyList[B]): MyList[B] =
      transformer(h) append t.flatMap(transformer)
  }

  // MORE FP LIST!


  // 3.
  val superAdder: Int => (Int => Int) = new Function1[Int, Function1[Int,Int]] {
    override def apply(x: Int): Int => Int = new Function1[Int, Int] {
      override def apply(y: Int): Int = x + y
    }
  }

  val add3 = superAdder(3)
  println(add3(4))
  println(superAdder(3)(4)) // curried function, can be called like this because
  // the function is returning another function and then we are calling that function

  // we want to work with functions as first class elements
  // we want to pass functions as parameters
  // we want to return functions as results
  // we want to work with functions as values

  // but we come from an OOP world
  // how do we do this in Scala?
  // solution: All Scala functions are objects
}
