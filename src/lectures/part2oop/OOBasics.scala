package lectures.part2oop

object OOBasics extends App {

    val person = new Person("John", 26)
    println(person.age)
    person.greet("Daniel")
    person.greet()

    val author = new Writer("Charles", "Dickens", 1812)
    val novel = new Novel("Great Expectations", 1861, author)
    println(novel.authorAge)
    println(novel.isWrittenBy(author))

    val counter = new Counter
    counter.inc.print // 1
    counter.inc.inc.inc.print // 3
    counter.inc(10).print // 10


}


// constructor

class PersonWithParametersNotAccessible(name: String, age: Int) // class parameters are not fields

// we cannot do val person = new PersonWithParametersNotAccessible("John", 26)
// and then person.age because age is not a field but is a class parameter

// constructor
// to convert a class parameter to a field we need to add the val or var keyword
// default parameters work as multiple constructors
// WITH VAL WE MAKE IT A FIELD
class Person(name: String, val age: Int = 0) {
    // body
    // the val or var definition here is a field
    // and i can use it outside the class
    // outside : person.x
    val x = 2

    // every instance of the class will execute this code ... so put it into
    // a method otherwise it will be run always (but if you want to run it always, put it here)
    println(1 + 3)

    // method
    // remember to use this. to access the fields of the class or the methods
    def greet(name: String): Unit = println(s"${this.name} says: Hi, $name")
    // person = new Person("John", 26)
    // person.greet("Daniel") --> John says: Hi, Daniel

    // overloading
    // multiple methods with the same name but different signatures/parameters
    def greet(): Unit = println(s"Hi, I am $name")

    // multiple constructors
    // or overloading constructors
  // quite useless because we can use default parameters!
    def this(name: String) = this(name, 0)
    def this() = this("John Doe")
}

/*
Novel and Writer
Writer: first name, surname, year
  - method: fullName (concatenation of first name and surname)
Novel: name, year of release, author
  - authorAge
  - isWrittenBy(author)
  - copy (new year of release) = new instance of Novel

 */

/*
Counter class
  - receives an int value
  - method current count
  - method to increment/decrement => new Counter
  - overload inc/dec to receive an amount

 */

class Writer(firstName: String, surname: String, val year: Int) {
    def fullName(): String = s"$firstName $surname"
}

class Novel(name: String, year: Int, author: Writer) {
    def authorAge = year - author.year
    def isWrittenBy(author: Writer) = author == this.author
    def copy(newYear: Int): Novel = new Novel(name, newYear, author)
}

// with val we ensure immutability and we obtain also the getter
class Counter(val count: Int = 0) {
    def inc = {
        println("incrementing")
        new Counter(count + 1) // immutability
    }
    def dec = {
        println("decrementing")
        new Counter(count - 1)
    }
    def inc(n: Int): Counter = {
        if(n <= 0) this
        else inc.inc(n - 1)
    }
    def dec(n: Int): Counter = {
        if(n <= 0) this
        else dec.dec(n - 1)
    }
    def print = println(count)
}

// classes: how to defined them, how to instantiate them, how to use them
// class parameters vs class fields
// how to define methods and fields
// how to use the fields and methods
