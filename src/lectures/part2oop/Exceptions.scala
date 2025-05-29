package lectures.part2oop

object Exceptions extends App {

    // you should never use null in Scala
    val x: String = null
    // println(x.length)
    // this ^^ will crash with a NPE (NullPointerException)

    // throwing and catching exceptions

    // 1. throwing exceptions
    // I can use also String, because it is a subtype of Nothing
    // val aWeirdValue: Nothing = throw new RuntimeException
    // val aWeirdValue: String = throw new NullPointerException

    // throwable classes extend the Throwable class
    // Exception and Error are the major Throwable subtypes

    // 2. how to catch exceptions
    def getInt(withExceptions: Boolean): Int =
      if (withExceptions) throw new RuntimeException("No int for you!")
      else 42

     try {
      // code that might throw
      getInt(true)
    } catch {
      case e: RuntimeException => println("caught a Runtime exception", e)
      // case e: NullPointerException => println("caught a Runtime exception") --> this
      // will actually crash the program throwing a RuntimeException
      // because the catch block is not exhaustive and the exception is not caught
    } finally {
      // code that will get executed NO MATTER WHAT
      // optional
      println("finally")
    }

    // it is AnyVal since try to match the return type of the try block
  // which is a Int and the return type of the catch block, which is a Unit
    val potentialFail: AnyVal = try {
      // code that might throw
      getInt(true)
    } catch {
      case e: RuntimeException => 43
    } finally {
      // code that will get executed NO MATTER WHAT
      // optional
      // does not influence the return type of this expression
      // use finally only for side effects
      println("finally")
    }

    // 3. how to define your own exceptions
    class MyException extends Exception
    val exception = new MyException

    // uncomment the line below to see the exception
    // throw exception

  /*
  1. Crash your program with an OutOfMemoryError
    2. Crash with SOError
    3. PocketCalculator
      - add(x, y)
      - subtract(x, y)
      - multiply(x, y)
      - divide(x, y)

      Throw
        - OverflowException if add(x, y) exceeds Int.MAX_VALUE
        - UnderflowException if subtract(x, y) exceeds Int.MIN_VALUE
        - MathCalculationException for division by 0
   */

  // OOM - OutOfMemoryError
  // val array = Array.ofDim(Int.MaxValue)

  // SO - StackOverflowError
  // def infinite: Int = 1 + infinite
  // val noLimit = infinite

  case class OverflowException() extends RuntimeException
  case class UnderflowException() extends RuntimeException
  case class MathCalculationException() extends RuntimeException("Division by 0")

  object PocketCalculator {
    def add(x: Int, y: Int): Int = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      else result
    }

    def subtract(x: Int, y: Int): Int = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def multiply(x: Int, y: Int): Int = {
      val result = x * y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      else if (x < 0 && y < 0 && result < 0) throw new OverflowException
      else if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      else if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      else result
    }

    def divide(x: Int, y: Int): Int = {
      if (y == 0) throw new MathCalculationException
      else x / y
    }
  }


}
