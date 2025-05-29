package playground

object ScalaPlayground extends App {
  println("Hello world!")
}

object ScalaPlaygroundMain {
  // or extends App avoiding to write the main method
  def main(args: Array[String]): Unit = {
    println("Hello world!")
  }
}