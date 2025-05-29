package lectures.part2oop

import scala.language.postfixOps

object MethodNotations extends App {

    class Person(val name: String, favoriteMovie: String, val age: Int = 0) {
      def likes(movie: String): Boolean = movie == favoriteMovie
      def +(person: Person): String = s"${this.name} is hanging out with ${person.name}"
      def +(nickname: String): Person = new Person(s"$name ($nickname)", favoriteMovie)
      // the space between the method name and the colon is mandatory!!!
      def unary_! : String = s"$name, what the heck?!"
      def unary_+ : Person = new Person(name, favoriteMovie, age + 1)
      // a function that does not take any inputs
      def isAlive: Boolean = true
      // the method signature is very important
      def apply(): String = s"Hi, my name is $name and I like $favoriteMovie"
      def apply(n: Int): String = s"$name watched $favoriteMovie $n times"
      def learns(what: String): String = s"$name is learning $what"
      def learnsScala: String = learns ("Scala") // or this learns "Scala"
    }

    val mary = new Person("Mary", "Inception")
    println(mary.likes("Inception"))
    println(mary likes "Inception") // equivalent
    // infix notation = operator notation (syntactic sugar)
    // only works with methods with one parameter

    // "operators" in Scala
    val tom = new Person("Tom", "Fight Club")
    println(mary + tom)
    println(mary.+(tom)) // equivalent

    println(1 + 2)
    println(1.+(2)) // equivalent
    // ALL OPERATORS ARE METHODS

    // prefix notation
    val x = -1 // equivalent with 1.unary_-
    val y = 1.unary_-
    // unary_ prefix only works with - + ~ !
    println(!mary)
    println(mary.unary_!) // equivalent

    // postfix notation
    println(mary.isAlive)
    println(mary isAlive) // equivalent, rarely used in practice

    // apply
    println(mary.apply())
    println(mary()) // equivalent, this is because we defined the apply

  /*
  1. Overload the + operator which receives a string and returns a new person with a nickname
     mary + "the Rockstar" => new Person "Mary (the Rockstar)"
  2. Add an age to the Person class with default 0 value
     Add a unary + operator => new person with the age + 1
     +mary => mary with the age incrementer
  3. Add a "learns" method in the Person class => "Mary learns Scala"
     Add a learnsScala method, calls learns method with "Scala"
     Use it in postfix notation
   4. Overload the apply method to receive a number and return a string
     mary.apply(10) => "Mary watched Inception 10 times"
   */

    println((mary + "the Rockstar").name) // Mary (the Rockstar)
    println((mary + "the Rockstar")())

    println(mary.unary_+.age)
    println((+mary).age) // equivalent

    println(mary learns "Python")
    println(mary learnsScala)

    println(mary(10))
}

// infix notation = object method parameter (syntactic sugar) - only works with methods with one parameter
// prefix notation = unary operators (syntactic sugar) - only works with - + ~ ! - rarely used in practice
// postfix notation = object method (syntactic sugar) - rarely used in practice
// apply = object() - syntactic sugar - you can call your object as a function