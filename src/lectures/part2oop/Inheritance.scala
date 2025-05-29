package lectures.part2oop

object Inheritance extends App {

  // single class inheritance, you can only extend one class at time like in Java
  sealed class Animal { // superclass of Cat
    val creatureType = "wild" // this is public by default, we can override it
    def eat = println("nomnom")
    private def sleep = println("zzzzzz")
    protected def walk = println("walking")
  }

  class Cat extends Animal { // subclass of Animal
    def crunch = {
      eat // this is allowed, inherited from Animal
      println("crunch crunch")
      // sleep // this is not allowed, because sleep is private in Animal
      walk // this is allowed, inherited from Animal
    }
  }

  val cat = new Cat
  cat.eat // inherited from Animal
  // cat.walk // this is not allowed, because walk is protected in Animal
  cat.crunch

  // constructors
  class Person(name: String, age: Int)
  // this is not allowed because we need to call the constructor of the superclass, we have
  // unspecified value for the superclass constructor
  // class Adult(name: String, age: Int, idCard: String) extends Person // this is not allowed

  // correct way
  class Adult(name: String, age: Int, idCard: String) extends Person(name, age)

  // overriding
  class Dog(override val creatureType: String) extends Animal {
    // override val creatureType = "domestic", same thing as above in the signature
    override def eat = {
      super.eat // call the eat method from the superclass
      println("crunch, crunch")
    }
  }

  val dog = new Dog("K9")
  dog.eat
  println(dog.creatureType)

  // type substitution (broad: polymorphism)
  val unknownAnimal: Animal = new Dog("K9")
  // I'm going to see crunch, crunch because the method eat is overridden in Dog
  // and the method eat is called on the Dog instance
  // a method call will always go to the most overridden version of the method
  // the compiler will look at the type of the reference, not the instance
  unknownAnimal.eat

  // overRIDING vs overLOADING

  // overriding => supply a different implementation in derived classes
  // overloading => supply multiple methods with different signatures in the same class

  // super
  // when you want to call a method from the superclass

  // preventing overrides
  // 1 - use final on member
  // i.e. (in the class Animal) final def eat = println("nomnom")
  // 2 - use final on the entire class
  // i.e. (in the class Animal) final class Animal
  // 3 - seal the class = you can extend classes in THIS FILE, prevent extension in other files
  // i.e. sealed class Animal
  // often used when you have a hierarchy of classes and you want to make sure that
  // the hierarchy is not extended
  /*
  A sealed trait can be extended only in the same file as its declaration.

  They are often used to provide an alternative to enums. Since they can be only extended in a single file, the compiler knows every possible subtypes and can reason about it.

  For instance with the declaration:

  sealed trait Answer
  case object Yes extends Answer
  case object No extends Answer
   */


}
