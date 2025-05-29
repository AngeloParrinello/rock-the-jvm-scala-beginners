package lectures.part1basics

object CBNvsCBV extends App {

  // Call by value
  // the expression is evaluated before the function is called (the value is computed before the function is called)
  // classic way of passing parameters in most programming languages
    def calledByValue(x: Long): Unit = {
      println("by value: " + x) // same value here and below
      println("by value: " + x)
    }

  // Call by name
  // the arrow => tells the compiler to evaluate the expression every time it is called
  // the expression is passed literally (the expression is passed as is)
  // the expression is evaluated every time it is used within
    def calledByName(x: => Long): Unit = {
      println("by name: " + x) // two different values, it is like I'd call System.nanoTime() twice (here and below)
      println("by name: " + x)
    }

    calledByValue(System.nanoTime()) // returns the same value
    calledByName(System.nanoTime()) // returns different values, because the expression is evaluated every time it is called

    def infinite(): Int = 1 + infinite()
    def printFirst(x: Int, y: => Int): Unit = println(x)

    // printFirst(infinite(), 34) // This will crash
    printFirst(34, infinite()) // This will not crash, this because the lazy evaluation of the second parameter
  // the second parameter is never evaluated, because it is not used in the function
  // this is the difference between call by value and call by name
  // call by value evaluates the expression before the function is called
  // call by name evaluates the expression every time it is used within the function (so it is evaluated lazily)

}
