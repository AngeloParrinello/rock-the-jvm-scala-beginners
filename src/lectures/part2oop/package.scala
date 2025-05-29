package lectures

// Rarely used in practice
package object part2oop {
  // we can define constants and methods here
  // and use them in other classes of the same package
  // ONE PACKAGE OBJECT PER PACKAGE
  def sayHello: Unit = println("Hello, Scala")
  val SPEED_OF_LIGHT = 299792458
}
