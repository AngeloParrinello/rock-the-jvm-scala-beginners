package lectures.part3fp

object AnonymousFunction extends App {
  // so far way to define a function
  // val dubler = new Function1[Int, Int] {
  //   override def apply(v1: Int): Int = v1 * 2
  // }

  // new way to define a function
  // val doubler = (x: Int) => x * 2

  // and we can declare the type of the function..

  // anonymous function (LAMBDA)
  val doubler: Int => Int = (x: Int) => x * 2

  // but we have to specify the type of the input parameter
  // val doubler2 = x=> x * 2 // this will not work

  // multiple parameters in a lambda
  // if I have multiple parameters, I have to put them in parentheses
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b
  //other way without the definition of the type
  val adder2 = (a: Int, b: Int) => a + b

  // no parameters
  val justDoSomething: () => Int = () => 3
  // other way without the definition of the type
  val justDoSomething2 = () => 3

  println(justDoSomething) // instantiate the function itself
  println(justDoSomething()) // the actual call

  // curly braces with lambda
  val stringToInt = { (str: String) =>
    str.toInt
  }

  // MORE syntactic sugar
  // each underscore is a parameter
  // but the input type is important to be defined
  val niceIncrementer: Int => Int = _ + 1 // equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ // equivalent to (a, b) => a + b

  /*
    1. MyList: replace all FunctionX calls with lambdas
    2. Rewrite the "special" adder as an anonymous function
   */

  // 1.

  def myPredicate[T](boolean: Boolean): T => Boolean = (_: T) => boolean

  def myTransformer[A, B](transform: A => B): A => B = (elem: A) => transform(elem)

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

  // 2.
  val specialAdder = (x: Int) => (y: Int) => x + y
  println(specialAdder(3)(4))

  /*
  - Instead of using Function1, Function2, etc, we can use lambdas
   */

}
