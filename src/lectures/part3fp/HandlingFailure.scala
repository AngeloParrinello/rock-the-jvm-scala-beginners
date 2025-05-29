package lectures.part3fp

import java.util.Random
import scala.util.{Failure, Success, Try}

object HandlingFailure extends App {

  // create success and failure
  val aSuccess = Success(3)
  val aFailure = Failure(new RuntimeException("Super Failure"))

  println(aSuccess)
  println(aFailure)

  // Most of the time you do not need to create a Success or Failure yourself as did above
  // because the Try.apply method will do that for you
  val potentialFailure = Try(unsafeMethod())
  val anotherPotentialFailure = Try {
    // code that might throw
  }
  // but the program did not crash, because the Try object caught the exception!
  println(potentialFailure) // Failure(java.lang.RuntimeException: No string for you)

  // this is another syntax sugar
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))

  // utilities
  println(potentialFailure.isSuccess) // false
  println(potentialFailure.isFailure) // true
  val betterFallback = betterUnsafeMethod() orElse betterBackupMethod()
  // Exercise
  val host = "localhost"

  println(fallbackTry) // Success(A valid result)
  val port = "8080"
  // if you get the connection, get the page, and render the page
  val possibleConnection = HttpService.getSafeConnection(host, port)
  val possibleHTML = possibleConnection.flatMap(connection => connection.getSafe("/home"))

  // if you think that your code can return a null value, you should return an Option object
  // if you think that your code can throw an exception, you should return a Try object

  // map, flatMap, filter
  println(aSuccess.map(_ * 2)) // Success(6)
  println(aSuccess.flatMap(x => Success(x * 10))) // Success(30)
  println(aSuccess.filter(_ > 10)) // Failure(java.util.NoSuchElementException: Predicate does not hold for 3)
  // and this means that Try is a monad and support for-comprehensions!

  // Try objects via apply method
  def unsafeMethod(): String = throw new RuntimeException("No string for you")

  // orElse
  // just like Option, Try has orElse method
  def backupMethod(): String = "A valid result"

  // If you design the API, you should return a Try object instead of throwing an exception
  // because the Try object will catch the exception and return a Failure object
  // and the client code can handle the Failure object in a more functional way
  // and the client code can decide what to do in case of a failure
  def betterUnsafeMethod(): Try[String] = Failure(new RuntimeException("No string for you"))

  def betterBackupMethod(): Try[String] = Success("A valid result")

  def renderHTML(page: String) = println(page)

  class Connection {
    // in the real world, we would not have the access to the source code of the Connection class
    // so we would not be able to modify the get method to return a Try object
    // so we would need to wrap the get method in a Try object
    def getSafe(url: String): Try[String] = Try(get(url))

    def get(url: String): String = {
      val random = new Random(System.nanoTime())
      if (random.nextBoolean()) "<html>...</html>"
      else throw new RuntimeException("Connection interrupted")
    }
  }

  object HttpService {
    val random = new Random(System.nanoTime())

    def getSafeConnection(host: String, port: String): Try[Connection] = Try(getConnection(host, port))

    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException("Someone else took the port")
    }
  }
  possibleHTML.foreach(renderHTML)

  // shorthand version
  HttpService.getSafeConnection(host, port)
    .flatMap(connection => connection.getSafe("/home"))
    .foreach(renderHTML)

  // for comprehension version, the same as the shorthand version
  for {
    connection <- HttpService.getSafeConnection(host, port)
    html <- connection.getSafe("/home")
  } renderHTML(html)

  // in an imperative way: a nightmare
  /*
   try {
     val connection = HttpService.getConnection(host, port)
     try {
       connection.get("/home")
       renderHTML(page)
     } catch (e) {
       e.printStackTrace()
     }
   } catch (e) {
     e.printStackTrace()
   }
   */

}
