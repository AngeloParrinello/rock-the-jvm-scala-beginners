package lectures.part3fp

object MapFlatMapFilter extends App {

  val list = List(1, 2, 3)
  println(list)
  println(list.head)
  println(list.tail)

  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number"))

  // filter
  println(list.filter(_ % 2 == 0))

  // flatMap
  val toPair = (x: Int) => List(x, x + 1)
  println(list.flatMap(toPair))

  // print all combinations between two lists
  val numbers = List(1, 2, 3, 4)
  val chars = List('a', 'b', 'c', 'd')
  val colors = List("black", "white")

  // "iterating"
  // In normal programming languages, we would use nested loops to iterate over all combinations of the elements of the lists.
  // In functional programming, we use flatMap to achieve the same result.
  val combinations = numbers.flatMap(n => chars.flatMap(c => colors.map(color => "" + c + n + "-" + color)))
  println(combinations)

  // foreach
  list.foreach(println)

  // for-comprehensions
  // this for comprehension is equivalent to the flatMap above
  // this is another way to write the flatMap above
  // this is a syntactic sugar for flatMap, map, and filter
  val forCombinations2 = for {
    n <- numbers
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color

  val forCombinations = for {
    n <- numbers if n % 2 == 0
    c <- chars
    color <- colors
  } yield "" + c + n + "-" + color
  println(forCombinations)

  // identical to numbers.foreach(println)
  for {
    n <- numbers
  } println(n)

  // syntax overload
  list.map { x =>
    x * 2
  }

  /*
   1. MyList supports for comprehensions?
   2. A small collection of at most ONE element - Maybe[+T]
     - map, flatMap, filter
   */

  // 1. MyList supports for comprehensions?
  // Yes, MyList supports for comprehensions.
  // The for comprehension is syntactic sugar for flatMap, map, and filter.
  // The for comprehension is a way to write flatMap, map, and filter in a more readable way.
  // Let's see how we can implement the for comprehension in MyList.
  // see in src/lectures/part3fp/HOFsCurries.scala


  // 2. A small collection of at most ONE element - Maybe[+T]
  //   - map, flatMap, filter
  // The Maybe collection is a collection that can have at most one element.
  // or is a collection that can have zero or one element.
  // Maybe is similar to Option

  abstract class Maybe[+T] {
    def map[B](f: T => B): Maybe[B]
    def flatMap[B](f: T => Maybe[B]): Maybe[B]
    def filter(p: T => Boolean): Maybe[T]
  }

  case object MaybeNot extends Maybe[Nothing] {
    def map[B](f: Nothing => B): Maybe[B] = MaybeNot
    def flatMap[B](f: Nothing => Maybe[B]): Maybe[B] = MaybeNot
    def filter(p: Nothing => Boolean): Maybe[Nothing] = MaybeNot
  }

  case class Just[+T](value: T) extends Maybe[T] {
    def map[B](f: T => B): Maybe[B] = Just(f(value))
    def flatMap[B](f: T => Maybe[B]): Maybe[B] = f(value)
    def filter(p: T => Boolean): Maybe[T] =
      if (p(value)) this
      else MaybeNot
  }

  val just3 = Just(3)
  println(just3)
  println(just3.map(_ * 2))
  println(just3.flatMap(x => Just(x % 2 == 0)))
  println(just3.filter(_ % 2 == 0))

  /*
  This Maybe collection is similar to the Option collection in Scala.
  Is extremely important to denote the possible absence of a value.
   */
}
