package lectures.part1basics

object ValuesVariablesTypes extends App {
  val x: Int = 42
  // val y: Int = "hello, Scala" // This will not compile
  println(x)

  // VALS ARE IMMUTABLE
  // x = 2 // This will not compile

  // COMPILER can infer types
  val y = 42
  println(y)

  val aString: String = "hello"
  val anotherString = "goodbye"

  val aBoolean: Boolean = true // or false
  val aChar: Char = 'a' // single characters with single quotes
  val anInt: Int = x
  val aShort: Short = 4613 // 2 bytes
  val aLong: Long = 5273985273895237L // 8 bytes
  val aFloat: Float = 2.0f // 4 bytes, the f as the L above is needed
  val aDouble: Double = 3.14 // 8 bytes, consistent with Java

  // VARIABLES
  var aVariable: Int = 4
  aVariable = 5 // side effects, software without side effects or with minimal side effects are better
  aVariable += 1 // also works with -= *= /=
  println(aVariable)
  // prefer vals over vars

}
