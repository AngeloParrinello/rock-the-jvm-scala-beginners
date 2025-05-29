package lectures.part2oop

object Generics extends App {

    // invariant list in this case (because we do not define +A or -A)
    // generic classes, parameterized types in this case A
    class MyList[A] {
      // use the type A
    }

  // multiple type parameters are also possible
  class MyMap[Key, Value]

  // generic traits
  trait MyListTrait[A] {

  }

    // once you instantiate the class, you need to pass the type
    val listOfIntegers = new MyList[Int]
    val listOfStrings = new MyList[String]

    // generic methods
    // companion object, the object cannot be parameterized
    object MyList {
      // generic method
      def empty[A]: MyList[A] = ???
    }

    // here we defined the type of the method
    val emptyListOfIntegers = MyList.empty[Int]

    // variance problem
    class Animal
    class Cat extends Animal
    class Dog extends Animal

    // question: does List[Cat] extends List[Animal]?
    // three possible answers:

    // 1. yes, List[Cat] extends List[Animal] = COVARIANCE
    // +A means that the list is covariant
    // so we use the list of animals as a list of cats
    // the same way that a list of cats is a list of animals
    // the same way a cat is an animal
    class CovariantList[+A]
    val animal: Animal = new Cat
    val animalList: CovariantList[Animal] = new CovariantList[Cat]
    // animalList.add(new Dog) ??? HARD QUESTION => TLDR : we return a list of more
    // generic type, in this case a list of animals
    // we will see the solution to this problem later

    // 2. no = INVARIANCE
    // list of cats and list of animals are two separate things
    class InvariantList[A]
    // val invariantAnimalList: InvariantList[Animal] = new InvariantList[Cat] // this will not compile

    // 3. Hell, no! = CONTRAVARIANCE
    // the list of cats is a list of animals
    // basically the opposite of covariance
    // -A means that the list is contravariant
    // look at the type relationship
   // if you take a look to the covariance example, the first one
  // we can replace the list of animals with a list of cats because
  // a list of cats is a list of animals and a cat is an animal
  // in the controvariant example, we can replace the list of cats with a list of animals
    class ContravariantList[-A]
    val contravariantList: ContravariantList[Cat] = new ContravariantList[Animal]
    // look at this second example
    // the semantic of it will change this time
    // because the right side is a trainer of animal
    // because I want a trainer of cat but I have a trainer of animal
    // which is better because I want a trainer of cat but I have
    // a trainer of animal wchich can be also a trainer of dog, dinausaur etc.
    // so it is better, and in particular it is a trainer of cat
    class Trainer[-A]
    val trainer: Trainer[Cat] = new Trainer[Animal]

    // bounded types
    // this is a class that accepts only subtypes of animal
    // in other words this means that the class Cage can only accepts
    // type that are animals or subtypes of animals
    // this a upper bounded type
    class Cage[A <: Animal](animal: A)
    val cage = new Cage(new Dog)

  // we also have the lower bounded type
  // this means that the class Cage can only accepts
  // type that are animals or supertypes of animals
  // this is a lower bounded type
  class CageLowerBound[A >: Animal](animal: A)
  val cageLowerBound = new CageLowerBound(new Animal)

  // the bounded types solve the variance problem
  // because the variance problem is a problem of subtyping
  // let's see an example
  // in this case we have a class MyList2 that is covariant
  // and we have a method add that accepts a type A
  // class MyList[+A] {
    // use the type A
    // this function signature, this method, does not work
    // even if the list is covariant and generic because
    // we are trying to add an element of type A
    // def add(element: A): MyList[A] = ??? // this is a placeholder
    // the error that the compiler will give us is:
    // covariant type A occurs in contravariant position in type A of value element
    // this means that the type A is in a contravariant position
    // and this is not allowed
    // and this is the technical explanation to the question:
    // can we do // animalList.add(new Dog) ???
    // the answer is no, because the adding a dog would transform the list in something
  // more generic, in this case a list of animals
  //}

  // to overcome this problem we can use the bounded types! See the correct examples:
  class MyList2[A] {
    // B is a super type of A and the element is of type B
    def add[B >: A](element: B): MyList2[B] = ??? // this is a placeholder
    /*
    A = Cat
    B = Dog = Animal
    If I add a dog to a list of cats, I will turn the list of cats into a list of animals
     */
  }
  }




    class Car
    // val newCage = new Cage(new Car) // this will not compile

    // expand MyList to be generic
    // use the type A inside the class definition
    // covariant list
    abstract class MyList[+A] {
      def head: A
      def tail: MyList[A]
      // bounded types: A is a super type of B
      def add[B >: A](element: B): MyList[B]
      def printElements: String
      def isEmpty: Boolean
      override def toString: String = "[" + printElements + "]"
    }

    object Empty extends MyList[Nothing] {
      def head: Nothing = throw new NoSuchElementException
      def tail: MyList[Nothing] = throw new NoSuchElementException
      def isEmpty: Boolean = true
      def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)
      def printElements: String = ""
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

    object testList extends App {
      val listOfIntegers: MyList[Int] = new Cons(1, new Cons(2, new Cons(3, Empty)))
      println(listOfIntegers.head)
      println(listOfIntegers.tail.head)
      println(listOfIntegers.add(4).head)
      println(listOfIntegers.isEmpty)
      println(listOfIntegers.toString)
      println(listOfIntegers.printElements)

      val listOfStrings: MyList[String] = new Cons("Hello", new Cons("Scala", Empty))
      println(listOfStrings.head)
      println(listOfStrings.tail.head)
      println(listOfStrings.add("Java").head)
      println(listOfStrings.isEmpty)
      println(listOfStrings.toString)
      println(listOfStrings.printElements)
    }

    }
