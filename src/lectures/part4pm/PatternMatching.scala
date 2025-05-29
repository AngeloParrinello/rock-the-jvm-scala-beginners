package lectures.part4pm

object PatternMatching extends App {

  // switch on steroids
  val random = new scala.util.Random
  val x = random.nextInt(10)

  val description = x match {
    case 1 => "the ONE"
    case 2 => "double or nothing"
    case 3 => "third time is the charm"
    case _ => "something else" // _ is a wildcard
  }

  println(x)
  println(description)
  val bob = Person("Bob", 20)
  val greeting = bob match {
    // the if here is called a guard
    case Person(n, a) if a < 21 => s"Hi, my name is $n and I can't drink in the US"
    case Person(n, a) => s"Hi, my name is $n and I am $a years old"
    case _ => "I don't know who I am"
  }
  // If I try to run this code, I will get a warning that the match may not be exhaustive
  // If I remove the sealed keyword from the Animal class, I will not get the warning
  val animal: Animal = Dog("Terra Nova")

  println(greeting)

  /*
    1. Cases are matched in order
    2. what if no cases match? MatchError --> so important to have a default case
    3. what is the type of the pattern match expression? is the unification of all the types of all the cases, unified type of all the types in all the cases
    4. PM works really well with case classes
   */
  // match everything
  val isEven = x % 2 == 0
  // DO NOT DO THIS - it is not recommended to use this kind of pattern matching
  // PM should not be used in this case
  val isEvenMatch = x match {
    case 0 => true
    case _ if isEven => true
    case _ => false
  }

  def show(expr: Expr): String = expr match {
    case Number(n) => s"$n"
    case Sum(e1, e2) => show(e1) + " + " + show(e2)
    case Prod(e1, e2) =>
      def maybeShowParentheses(expr: Expr) = expr match {
        case Prod(_, _) => show(expr)
        case Number(_) => show(expr)
        case _ => "(" + show(expr) + ")"
      }

      maybeShowParentheses(e1) + " * " + maybeShowParentheses(e2)
  }

  trait Expr
  animal match {
    case Dog(someBreed) => println(s"Matched a dog of the $someBreed breed")
  }

  // PM on sealed hierarchies
  sealed class Animal

  // 1. Decompose values
  // this is due to the .unapply method in the Person object
  case class Person(name: String, age: Int)

  println(isEvenMatch)

  // Exercise
  // simple function uses PM
  // takes an Expr => human readable form
  // Sum(Number(2), Number(3)) => 2 + 3
  // Sum(Number(2), Sum(Number(3), Number(4))) => 2 + 3 + 4
  // Prod(Sum(Number(2), Number(1)), Number(3)) => (2 + 1) * 3
  // Sum(Prod(Number(2), Number(1)), Number(3)) => 2 * 1 + 3

  case class Dog(breed: String) extends Animal

  case class Parrot(greeting: String) extends Animal

  case class Number(n: Int) extends Expr

  case class Sum(e1: Expr, e2: Expr) extends Expr

  case class Prod(e1: Expr, e2: Expr) extends Expr

  println(show(Sum(Number(2), Number(3))))
  println(show(Sum(Number(2), Sum(Number(3), Number(4)))) == "2 + 3 + 4")
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))) == "(2 + 1) * 3")
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))) == "2 * 1 + 3")


}
