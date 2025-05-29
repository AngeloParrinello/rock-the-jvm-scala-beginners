package lectures.part2oop

object AnonymousClasses extends App {
  trait Animal {
    def eat: Unit
  }

  // anonymous class
  // what does it mean? It means that we are instantiating a class and creating an instance of a type
  // and under the hood, the compiler will create a new class that extends the Animal trait
  val funnyAnimal: Animal = new Animal {
    override def eat: Unit = println("ahahahahah")
  }


  // class AnonymousClasses$$anon$1 extends Animal {
  //   override def eat: Unit = println("ahahahahah")
  // }
  // val funnyAnimal: Animal = lectures.part2oop.AnonymousClasses$$anon$1


  println(funnyAnimal.getClass) // class lectures.part2oop.AnonymousClasses$$anon$1

  class Person(name: String) {
    def sayHi: Unit = println(s"Hi, my name is $name, how can I help?")
  }

  // you have to pass the constructor arguments, even if it is an anonymous class
  val jim = new Person("Jim") {
    override def sayHi: Unit = println(s"Hi, my name is Jim, how can I be of service?")
  }

  println(jim.getClass) // class lectures.part2oop.AnonymousClasses$$anon$2

  // you will need to implement all the abstract fields and methods
  // anonymous classes work for abstract classes and traits

  /*
  * 1. Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  * 2. Generic trait MyTransformer[-A, B] with a method transform(A) => B
  * 3. MyList:
  *  - map(transformer) => MyList
  * - filter(predicate) => MyList
  * - flatMap(transformer from A to MyList[B]) => MyList[B]
  * class EvenPredicate extends MyPredicate[Int]
  * class StringToIntTransformer extends MyTransformer[String, Int]
  * [1,2,3].map(n * 2) = [2,4,6]
  * [1,2,3,4].filter(n % 2) = [2,4]
  * [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
   */

  // 1. Generic trait MyPredicate[-T] with a little method test(T) => Boolean
  trait MyPredicate[-T] {
    def test(elem: T): Boolean
  }

  // 2. Generic trait MyTransformer[-A, B] with a method transform(A) => B
  trait MyTransformer[-A, B] {
    def transform(elem: A): B
  }

  // 3. MyList:
  //  - map(transformer) => MyList
  // - filter(predicate) => MyList
  // - flatMap(transformer from A to MyList[B]) => MyList[B]

  abstract class MyList[+A] {
    def head: A

    def tail: MyList[A]

    // bounded types: A is a super type of B
    def add[B >: A](element: B): MyList[B]

    def printElements: String

    def isEmpty: Boolean

    override def toString: String = "[" + printElements + "]"

    def map[B](transformer: MyTransformer[A, B]): MyList[B]

    def filter(predicate: MyPredicate[A]): MyList[A]

    def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]

    def append[B >: A](list: MyList[B]): MyList[B]
  }

  object Empty extends MyList[Nothing] {
    def head: Nothing = throw new NoSuchElementException

    def tail: MyList[Nothing] = throw new NoSuchElementException

    def isEmpty: Boolean = true

    def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)

    def printElements: String = ""

    override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty

    override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

    override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B] = Empty

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
    override def map[B](transformer: MyTransformer[A, B]): MyList[B] =
      new Cons[B](transformer.transform(h), t.map(transformer))

    /*
    [1,2,3].filter(n % 2 == 0)
    = [2,3].filter(n % 2 == 0)
    = new Cons(2, [3].filter(n % 2 == 0))
    = new Cons(2, Empty.filter(n % 2 == 0))
    = new Cons(2, Empty)
     */
    override def filter(predicate: MyPredicate[A]): MyList[A] =
      if (predicate.test(h)) new Cons(h, t.filter(predicate))
      else t.filter(predicate)

    override def append[B >: A](list: MyList[B]): MyList[B] =
      new Cons(h, t.append(list))

    override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] =
      transformer.transform(h) append t.flatMap(transformer)
  }

  // example of flatMap
  // [1,2,3].flatMap(n => [n, n+1]) => [1,2,2,3,3,4]
  val list = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val transformer = new MyTransformer[Int, MyList[Int]] {
    override def transform(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem + 1, Empty))
  }
  println(list.flatMap(transformer).toString) // [1 2 2 3 3 4]
}
