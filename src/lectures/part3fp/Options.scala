package lectures.part3fp

import java.util.Random

object Options extends App {

  // Options: a functional way of dealing with the possible absence of a value
  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  println(myFirstOption)
  println(noOption)

  // unsafe APIs
  def unsafeMethod(): String = null
  // val result = Some(unsafeMethod()) // WRONG, because it will be break the whole concept of Option
  // every time you see a Some, it must has within it a value
  // Some(null) is not the same as None!
  /// val wrongUseOfOption = Some(null) // WRONG!!!
  val result = Option(unsafeMethod()) // Some or None
  // we should never do a null check, we should use Option instead
  println(result)

  // chained methods
  def backupMethod(): String = "A valid result"
  // in the case the first method returns null, we can use the backup method to return a valid result
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))
  println(chainedResult)

  // Design unsafe APIs
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterUnsafeMethod() orElse betterBackupMethod()
  println(betterChainedResult)

  // functions on Options
  println(myFirstOption.isEmpty) // good test to see if an Option has a value or not
  println(myFirstOption.get) // UNSAFE - DO NOT USE THIS, get try to get the value from the Option, but if the Option is None, it will throw an exception
  // again we should never use get, we should use map, flatMap, filter instead
  // because the get method would break the whole concept of Option and the risk of a NullPointerException is high

  // map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(_ > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))

  // for-comprehensions
   /*
      Exercise.
    */
  val config: Map[String, String] = Map(
    // fetched from elsewhere
    // so we don't know if the value is null or not
    "host" -> "176.45.36.1",
    "port" -> "80"
  )

  class Connection {
    def connect = "Connected" // connect to some server
  }
  object Connection {
    val random = new Random(System.nanoTime())
    // the API of the Connection object is a bit unsafe, because it can return a null value
    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }

  // try to establish a connection, if so - print the connect method
  val host: Option[String] = config.get("host") // the get method (on MAP!) returns an Option
  val port: Option[String] = config.get("port")
  /*
    if (h != null)
      if (p != null)
        return Connection.apply(h, p)
    return null
   */
  // the flatMap method is used to chain Options and safely access the value inside the Option
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  /*
    if (c != null)
      return c.connect
    return null
   */
  val connectionStatus = connection.map(c => c.connect)
  // if (connectionStatus == null) println(None) else print(Some(connectionStatus.get))
  println(connectionStatus)

  // let's chain all together
  config.get("host")
    .flatMap(host => config.get("port")
      .flatMap(port => Connection(host, port))
      .map(connection => connection.connect))
    .foreach(println)

  // but this is familiar to something we already know...

  // for-comprehensions
  for {
    host <- config.get("host") // if this returns a value ...
    port <- config.get("port") // if this returns a value ...
    connection <- Connection(host, port) // and if this returns a value ...
  } println(connection.connect) // then print the connection

}
