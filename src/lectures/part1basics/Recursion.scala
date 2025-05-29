package lectures.part1basics

object Recursion extends App {
  def factorial(n: Int): Int = {
    if (n <= 1) 1
    else {
      println(s"Computing factorial of $n - I first need factorial of ${n - 1}")
      val result = n * factorial(n - 1)
      println(s"Computed factorial of $n")
      result
    }
  }
  // we have all these print statements that show us the path that the JVM follows in order to
  // call the function. The JVM helds all these temp data in the stack, so it is quite easy to overflow it!
  println(factorial(10))

  // println(factorial(5000)) // StackOverflow error!! the recursive depth is too deep for the JVM to handle
  // so we can use only recursion for at most 400-500 calls, after that we will get a StackOverflow error?
  // yes but there is a way to avoid it, we can use the tail recursion
  // the tail recursion is a special case of recursion where the recursive call is the last expression
  // that the function performs, so the JVM can replace the current stack frame with the next one
  // without keeping the current one in memory, so we can avoid the StackOverflow error

  def anotherFactorial(n: Int): BigInt = {
    @scala.annotation.tailrec
    def factorialHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factorialHelper(x - 1, x * accumulator) // TAIL RECURSION = use recursive call as the LAST expression
    }
    factorialHelper(n, 1)
  }

  /*
  anotherFactorial(10) = factorialHelper(10, 1)
  = factorialHelper(9, 10 * 1)
  = factorialHelper(8, 9 * 10 * 1)
  = factorialHelper(7, 8 * 9 * 10 * 1)
  = ...
  = factorialHelper(2, 3 * 4 * ... * 10 * 1)
  = factorialHelper(1, 2 * 3 * 4 * ... * 10 * 1)
  = 1 * 2 * 3 * 4 * ... * 10 --> so only here we start to multiply the numbers and makes the calculations
  The trick here is that the whole calculation is done in the accumulator, so we don't need to keep all the
  intermediate results in the stack, so we can avoid the StackOverflow error
  and calculate the whole number at the end, at the last recursive call
   */

  // IN GENERAL WHEN NEEDING LOOPS, USE TAIL RECURSION

  /*
  1. Concatenate a string n times
   */
  def concatenateString(aString: String, n: Int): String = {
    @scala.annotation.tailrec
    def concatenateHelper(aString: String, n: Int, accumulator: String): String = {
      if (n <= 0) accumulator
      else concatenateHelper(aString, n - 1, aString + accumulator)
    }

    concatenateHelper(aString, n, "")
  }

  /*
  2. IsPrime function tail recursive
   */
  def isPrime(n:Int): Boolean = {
    @scala.annotation.tailrec
    def isPrimeUntil(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeUntil(t - 1, n % t != 0 && isStillPrime)
    }

    isPrimeUntil(n / 2, isStillPrime = true)
  }

  /*
  3. Fibonacci function, tail recursive
   */
  def fibonacci(n: Int): Int = {
    @scala.annotation.tailrec
    def fibonacciHelper(i: Int, last: Int, nextToLast: Int): Int = {
      if (i >= n) last
      else fibonacciHelper(i + 1, last + nextToLast, last)
    }

    if (n <= 2) 1
    else fibonacciHelper(2, 1, 1)
  }

  def anotherFibonacci(n: Int): Int = {
    if (n <= 2) 1
    else anotherFibonacci(n - 1) + anotherFibonacci(n - 2)
  }

  def anotherTailRecFactorialWithMatchCases(n: Int): BigInt = {
    @scala.annotation.tailrec
    def factorialHelper(x: Int, accumulator: BigInt): BigInt = {
      x match {
        case 0 | 1 => accumulator
        case _ => factorialHelper(x - 1, x * accumulator)
      }
    }
    factorialHelper(n, 1)
  }





}
