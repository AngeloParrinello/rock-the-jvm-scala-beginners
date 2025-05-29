package lectures.part1basics

import scala.annotation.tailrec

object Functions extends App{

  // defining a function
  // function header/signature
  def aFunction(a: String, b: Int): String = {
    a + " " + b
  }
  println(aFunction("hello", 3))

  // parameterless function
  def aParameterlessFunction(): Int = 42
  println(aParameterlessFunction())
  println(aParameterlessFunction) // same as above

  // in a normal programming language we would have use loops, here we use recursion
  // the return type is usually inferred by the compiler, but not in the recursive function
  def repeatedFunction(aString: String, n: Int): String = {
    if(n == 1) aString
    else aString + repeatedFunction(aString, n - 1)
  }
  println(repeatedFunction("hello", 3))
  // WHEN YOU NEED LOOPS, USE RECURSION

  def aFunctionWithSideEffects(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b

    aSmallerFunction(n, n - 1)
  }

  /*
  1. A greeting function (name, age) => "Hi, my name is $name and I am $age years old."
   */
  def greetingFunction(name: String, age: Int): String = s"Hi, my name is $name and I am $age years old."

  /*
  2. Factorial function 1 * 2 * 3 * .. * n
   */
  def factorialFunction(n: Int): Int = {
    n match {
      case 0 => 1
      case _ => n * factorialFunction(n - 1)
    }
  }

  /*
  3. A Fibonacci function
   */
  def fibonacciFunction(n: Int): Int = {
    n match {
      case 0 => 0
      case 1 => 1
      case _ => fibonacciFunction(n - 1) + fibonacciFunction(n - 2)
    }
  }

  /*
  4. Tests if a number is prime
   */
  def primeNumberFunction(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int): Boolean = {
      if(t <= 1) true
      else n % t != 0 && isPrimeUntil(t - 1)
    }

    isPrimeUntil(n / 2)
  }

  def anotherPrimeNumberFunction(n: Int): Boolean = {
    n match {
      case 1 => false
      case 2 => true
      case _ => !(2 to n / 2).exists(x => n % x == 0)
    }
  }

  println(primeNumberFunction(20)) // false
  println(primeNumberFunction(37)) // true

  // in Scala we use the return type of the function to define the type of the value returned
}
