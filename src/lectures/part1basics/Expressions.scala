package lectures.part1basics

object Expressions extends App{

  val x = 1 + 2 // EXPRESSION, evaluated to a value with a type and the compiler can infer the type
  println(x)

  println(2 + 3 * 4) // + - * / & | ^ << >> >>> (right shift with zero extension)

  println(1 == x) // == != > >= < <=

  println(!(1 == x)) // ! && ||

  var aVariable = 2
  aVariable += 3 // also works with -= *= /= ... side effects so pay attention
  println(aVariable)

  // Instructions (DO, change a variable, print something etc. something IMPERATIVE)
  // vs Expressions (VALUE, we think in terms of values and expressions, like in functional programming)
  // in Scala we think in terms of expressions

  // IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3 // IF EXPRESSION, not an IF instruction
  println(aConditionedValue)
  println(if(aCondition) 5 else 3)

  // LOOPS are discouraged in Scala, they are imperative, they only execute instructions and side effects
  var i = 0
  while(i < 10){
    println(i)
    i += 1 // side effects that returns Unit
  }
  // NEVER WRITE THIS AGAIN

  // EVERYTHING in Scala is an Expression!

  val aWeirdValue = (aVariable = 3) // Unit === void
  println(aWeirdValue) // prints (), this is the only value of type Unit
  val aWeirdLoop = while(i < 10){
    println(i)
    i += 1
  }
  println(aWeirdLoop) // prints (), this is the only value of type Unit

  // SIDE EFFECTS: println(), whiles, reassigning

  // Code blocks, we can define values inside a code block and the last value is the value of the block, STILL AN EXPRESSION
  val aCodeBlock = {
     // only visible inside the block
    val y = 2
    val z = y + 1

    if(z > 2) "hello" else "goodbye"
  }

  // 1. What is the difference between "hello world" vs println("hello world")?
  // "hello world" is a String, println("hello world") is an expression that returns Unit

  // 2. What is the value of:
  val someValue = {
    2 < 3
  }
  // Boolean true

  // 3. What is the value of:
  val someOtherValue = {
    if(someValue) 239 else 986
    42
  }
  // Int 42

}
