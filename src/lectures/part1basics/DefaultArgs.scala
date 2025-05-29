package lectures.part1basics

import scala.annotation.tailrec

object DefaultArgs extends App {

    // Default arguments
    // the default value is used when the parameter is not provided
    // nice way to avoid method overloading or nested methods and extends the API
    @tailrec
    def trFact(n: Int, acc: Int = 1): Int = {
      if(n <= 1) acc
      else trFact(n - 1, n * acc)
    }
    val fact10 = trFact(10)
    println(fact10)

    def savePicture(format: String = "jpg", width: Int = 1920, height: Int = 1080): Unit = println("saving picture")
    // we cannot do this: savePicture(800, 600) because the compiler will not know which parameter we are passing
    // we have to pass all the parameters in the right order or some of them and name them
    savePicture("jpg", 800, 600)
    savePicture(width = 800)
    savePicture(height = 600, format = "png")
    savePicture()

    // 1. pass in every leading argument
    // 2. name the arguments
    // 3. default arguments

    savePicture(width = 800, format = "bmp", height = 600)

}
