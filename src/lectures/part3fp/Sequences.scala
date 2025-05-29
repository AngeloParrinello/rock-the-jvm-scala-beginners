package lectures.part3fp

object Sequences extends App {

  // Seq
  // very general interface for data structures that:
  // 1. have a well-defined order
  // 2. can be indexed
  val aSequence = Seq(1, 3, 2, 4)
  // Seq is an abstract class with two main implementations: List and Array
  // Seq is a trait that has a lot of subclasses: List, Array, Vector, Map, Range, etc
  println(aSequence) // prints List(1, 2, 3, 4)
  println(aSequence.reverse) // prints List(4, 3, 2, 1)
  println(aSequence(2)) // prints 3
  println(aSequence ++ Seq(5, 6, 7)) // prints List(1, 2, 3, 4, 5, 6, 7)
  println(aSequence.sorted) // prints List(1, 2, 3, 4)

  // Ranges
  // aRange is a sequence of numbers with a step of 1
  val aRange: Seq[Int] = 1 to 10
  aRange.foreach(println) // prints 1 2 3 4 5 6 7 8 9 10
  // anotherRange is a sequence of numbers with a step of 1
  // but the last number is not included
  val anotherRange: Seq[Int] = 1 until 10
  anotherRange.foreach(println) // prints 1 2 3 4 5 6 7 8 9

  (1 to 10).foreach(x => println("Hello")) // prints Hello 10 times

  // Lists
  // immutable linked list
  // are fast for: head, tail, isEmpty, prepend (add to the head) with O(1)
  // are slow for: apply, insert, length, reverse with O(n)
  val aList = List(1, 2, 3)
  val anotherPrepend = 42 :: aList
  // prepending and appending elements to lists
  // the : is always on the side of the list
  val prepended = 42 +: aList :+ 89
  println(prepended) // prints List(42, 1, 2, 3, 89)

  // appending elements, creating a list with 5 apples
  val apples5 = List.fill(5)("apple")
  println(apples5) // prints List(apple, apple, apple, apple, apple)

  println(aList.mkString("-")) // prints 1-2-3

  // Arrays
  // equivalent of simple Java arrays
  // can be manually constructed with predefined lengths
  // can be mutated (updated in place)
  // indexing is fast
  val numbers = Array(1, 2, 3, 4)
  // array of a given length with all elements initialized with 0
  // quite ugly syntax
  val threeElements = Array.ofDim[Int](3)
  threeElements.foreach(println) // prints 0 0 0

  // mutation
  // i.e. side effects
  // quite useless in reality but exists
  numbers(2) = 0 // syntax sugar for numbers.update(2, 0)
  println(numbers.mkString(" ")) // prints 1 2 0 4

  // arrays and seq
  // arrays can be converted to sequences
  // implicit conversion --> wrappedArray
  // implicit conversion is a mechanism that allows the compiler to automatically convert values of one type to another
  val numbersSeq: Seq[Int] = numbers // implicit conversion
  println(numbersSeq) // prints WrappedArray(1, 2, 0, 4)

  // Vectors
  // default implementation for immutable sequences
  // effectively constant indexed read and write: O(log32(n))
  // fast element addition: append/prepend
  // implemented as a fixed-branched trie (branch factor 32, it means that each node has 32 children
  // 32 elements in each node)
  // good performance for large sizes
  val vector: Vector[Int] = Vector(1, 2, 3)
  println(vector) // prints Vector(1, 2, 3)

  // vectors vs lists
  // vectors are good for large sizes
  // lists are good for small sizes

  // performance implications
  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTime(collection: Seq[Int]): Double = {
    val r = new scala.util.Random
    val times = for {
      it <- 1 to maxRuns
    } yield {
      val currentTime = System.nanoTime()
      collection.updated(r.nextInt(maxCapacity), r.nextInt())
      System.nanoTime() - currentTime
    }
    times.sum * 1.0 / maxRuns
  }
  // adv: keeps reference to the tail
  // disv: updating an element in the middle takes a long time
  val numbersList = (1 to maxCapacity).toList
  // adv: depth of the tree is small
  // disv: needs to replace an entire 32-element chunk
  val numbersVector = (1 to maxCapacity).toVector
  println(getWriteTime(numbersList))
  println(getWriteTime(numbersVector)) // 3 orders of magnitude faster
  // the time complexity of updating an element in the middle of a list is O(n)
  // the time complexity of updating an element in the middle of a vector is O(log32(n))

}
