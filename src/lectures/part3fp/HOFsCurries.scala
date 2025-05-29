package lectures.part3fp

import lectures.part3fp.HOFsCurries.Empty.add

import scala.annotation.tailrec

object HOFsCurries extends App {

  // Higher Order Functions (HOFs) - functions that either take functions as parameters or return functions as results
  val superFunction: (Int, (String, (Int => Boolean)) => Int) => (Int => Int) = null //???
  // try to read this function signature
  // first let's have a look to the return type of the function (after the => without parenthesis)
  // the return function is an Int => Int so a function that takes an Int and returns an Int
  // map, flatmap, filter are HOFs

  // function that applies a function n times over a value x
  // nTimes(f, n, x) = f(f(f...(x))) = nTimes(f, n - 1, f(x))
  // nTimes(f, 3, x) = f(f(f(x))) = nTimes(f, 2, f(x)) = nTimes(f, 1, f(f(x))) = nTimes(f, 0, f(f(f(x))))
  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = n match {
    case 0 => x
    case _ => nTimes(f, n - 1, f(x))
  }

  val plusOne: Int => Int = _ + 1
  println(nTimes(plusOne, 10, 1))

  // nTimesBetter(f, n) = x => f(f(f...(x)))
  // I return a lambda function that takes an Int and returns an Int
  // increment10 = nTimesBetter(plusOne, 10) = x => plusOne(plusOne...(x))
  // val y = increment10(1)
  def nTimesBetter(f: Int => Int, n: Int): Int => Int = n match {
    // more parenthesis to be more explicit but useless
    case 0 => ((x: Int) => x)
    case _ => ((x: Int) => nTimesBetter(f, n - 1)(f(x)))
  }

  val plus10: Int => Int = nTimesBetter(plusOne, 10)
  // plus10 is a function that takes an Int and returns an Int
  println(plus10(1))

  // curried functions
  val superAdder: Int => (Int => Int) = x => y => x + y
  val add3 = superAdder(3) // y => 3 + y
  println(add3(10))
  println(superAdder(3)(10))

  // functions with multiple parameter lists
  def curriedFormatter(c: String)(x: Double): String = c.format(x)
  def curriedFormatter2: String => (Double => String) = s => x => s.format(x)
  val standardFormat: Double => String = curriedFormatter("%4.2f")
  val preciseFormat: Double => String = curriedFormatter("%10.8f")

  println(standardFormat(Math.PI))
  println(preciseFormat(Math.PI))

  /*
   1. Expand MyList
      - foreach method A => Unit
        [1,2,3].foreach(x => println(x))
      - sort function ((A, A) => Int) => MyList
        [1,2,3].sort((x, y) => y - x) => [3,2,1]
      - zipWith (list, (A, A) => B) => MyList[B]
        [1,2,3].zipWith([4,5,6], x * y) => [1 * 4, 2 * 5, 3 * 6] = [4, 10, 18]
      - fold(start)(function) => a value
        [1,2,3].fold(0)(x + y) = 6

    2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
       fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int

    3. compose(f,g) => x => f(g(x))
       andThen(f,g) => x => g(f(x))

   */
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

    def foreach(f: A => Unit): Unit

    def sort(compare: (A, A) => Int): MyList[A]

    def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]

    def fold[B](start: B)(operator: (B, A) => B): B
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

    override def foreach(f: Nothing => Unit): Unit = ()

    override def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = Empty

    override def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[C] =
      if (!list.isEmpty) throw new RuntimeException("Lists do not have the same length")
      else Empty

    override def fold[B](start: B)(operator: (B, Nothing) => B): B = start
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

    override def foreach(f: A => Unit): Unit = {
      f(h)
      t.foreach(f)
    }

    override def sort(compare: (A, A) => Int): MyList[A] = {
      def insert(x: A, sortedList: MyList[A]): MyList[A] = {
        if (sortedList.isEmpty) new Cons(x, Empty)
        else if (compare(x, sortedList.head) <= 0) new Cons(x, sortedList)
        else new Cons(sortedList.head, insert(x, sortedList.tail))
      }

      val sortedTail = t.sort(compare)
      insert(h, sortedTail)

    }

    override def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C] = {
      val zipped = zip(h, list.head)
      new Cons(zipped, t.zipWith(list.tail, zip))
    }

    override def fold[B](start: B)(operator: (B, A) => B): B = {
      val operation = operator(start, h)
      t.fold(operation)(operator)
    }
  }

  // 2. toCurry(f: (Int, Int) => Int) => (Int => Int => Int)
  //    fromCurry(f: (Int => Int => Int)) => (Int, Int) => Int

  def toCurry(f: (Int, Int) => Int): Int => Int => Int = x => y => f(x, y)
  def fromCurry(f: Int => Int => Int): (Int, Int) => Int = (x, y) => f(x)(y)

  // 3. compose(f,g) => x => f(g(x))
  //    andThen(f,g) => x => g(f(x))

  def compose[A, B, T](f: A => B, g: T => A): T => B = x => f(g(x))
  def andThen[A, B, C](f: A => B, g: B => C): A => C = x => g(f(x))

  // testing

  // testing

  val adder: (Int, Int) => Int = _ + _
  val curriedAdder = toCurry(adder)
  val adder2 = fromCurry(curriedAdder)

  println(curriedAdder(4)(17))
  println(adder2(4, 17))

  // testing new operations on MyList

  val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherListOfIntegers: MyList[Int] = new Cons(4, new Cons(5, Empty))

  listOfIntegers.sort((x, y) => y - x).foreach(println)


  val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))
  val anotherListOfStrings: MyList[String] = new Cons("Java", new Cons("is", new Cons("fun", Empty)))

  // println(listOfIntegers.zipWith[String, String](listOfStrings, _ + "-" + _)) // error because the lists have different lengths
  println(listOfIntegers.zipWith[String, String](anotherListOfStrings, _ + "-" + _))

  println(listOfIntegers.fold(0)(_ + _))

  val testOnForComprehension =
    for {
      n <- listOfIntegers
      c <- anotherListOfIntegers
      color <- anotherListOfStrings
    } yield "" + c + n + "-" + color

  println(testOnForComprehension)



}
