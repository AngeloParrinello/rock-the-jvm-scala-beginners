package lectures.part2oop

object Objects extends App{

    // SCALA DOES NOT HAVE CLASS-LEVEL FUNCTIONALITY ("static")
    // Scala does not have static values or methods
    // Scala has objects
    // Objects can have values, variables, methods, types, expressions, etc.
    // Objects are singletons by definition
    // Objects do not receive parameters

    // FOR THE CLASS LEVEL DEFINITION WE USE OBJECT IN SCALA
    // In Scala we use object as singleton instances
    object Person { // type + its only instance
      // "static"/"class" - level functionality
      val N_EYES = 2
      def canFly: Boolean = false

      // Often used this pattern
      // Factory method
      // build some Person instance from a call
      def apply(mother: Person, father: Person): Person = new Person("Bobbie")
    }

    class Person(val name: String) {
      // instance-level functionality


    }

    // The Object Person and the Class Person are in the same scope so they are called
    // COMPANION OBJECT

    // COMPANIONS
    // Scala is more object-oriented than Java
    // A class and an object can have the same name
    // A class and an object in the same scope are called companions
    // Companions can access each other's private members

    println(Person.N_EYES)
    println(Person.canFly)

    // Scala object = SINGLETON INSTANCE
    val p1 = Person
    val p2 = Person
    println(p1 == p2) // true because they are the same instance through the Object

    val mary = new Person("Mary")
    val john = new Person("John")
    println(mary == john)

    val bobbie = Person(mary, john)
    val bobbie2 = Person.apply(mary, john)

    // Scala Applications = Scala object with
    // def main(args: Array[String]): Unit
    // The main method is the entry point of a Scala application
    // The main method is a static method in a singleton object
    // def main(args: Array[String]): Unit is equivalent to public static void main(String[] args) in Java

    // Scala does not have static values/methods
    // Scala objects
      // - are in their own class
      // - are the only instance
      // - singleton pattern in one line
    // Scala companions
     // - can access each other's private members
     // - Scala is more object-oriented than Java
    // Scala applications

}
