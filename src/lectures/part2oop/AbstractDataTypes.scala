package lectures.part2oop

object AbstractDataTypes extends App {

    // abstract
    // some situation where you need to leave some methods unimplemented
    // abstract classes cannot be instantiated
    // abstract classes are meant to be extended
    abstract class Animal {
      val creatureType: String // we are not supplying it any value
      def eat: Unit
    }

    class Dog extends Animal {
      override val creatureType: String = "Canine"
      // the override keyword is not mandatory but it is a good practice
      // to make it clear that we are overriding a method
      // but the compiler will tell you if you are not overriding anything
      def eat: Unit = println("crunch crunch")
    }

    // traits
    // a trait is a behavior that a class has
    // traits are like abstract classes but more similar to interfaces
    trait Carnivore {
      def eat(animal: Animal): Unit
    }

    trait ColdBlooded

    class Crocodile extends Animal with Carnivore {
      val creatureType: String = "croc" // this is the implementation of the abstract field
      def eat: Unit = println("nomnomnom") // this is the implementation of the abstract method
      def eat(animal: Animal): Unit =
        println(s"I'm a croc and I'm eating ${animal.creatureType}") // this is the implementation of the trait method

    }

    class Rabbit extends Animal with Carnivore with ColdBlooded {
      val creatureType: String = "rabbit"
      def eat: Unit = println("rabbit rabbit")
      def eat(animal: Animal): Unit =
        println(s"I'm a rabbit and I'm eating ${animal.creatureType}")
    }

    val dog = new Dog
    val croc = new Crocodile
    croc.eat(dog)

    val rabbit = new Rabbit
    rabbit.eat(croc)

    // abstract classes can have abstract and non-abstract members
    // as the traits. So, what are the differences between
    // abs class and traits?

    // traits vs abstract classes
    // 1 - traits do not have constructor parameters
    // i.e. we cannot pass any parameters to a trait, but you can do with a asb class
    // 2 - multiple traits may be inherited by the same class
    // i.e. multiple inheritance
    // 3 - traits = behavior, abstract class = "thing"
    // i.e. traits are meant to describe what something does
    // while abstract classes are meant to describe what something is

}
