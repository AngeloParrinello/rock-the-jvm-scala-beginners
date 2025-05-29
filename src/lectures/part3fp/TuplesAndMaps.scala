package lectures.part3fp

object TuplesAndMaps extends App {

  type Person = String
  type SocialNetwork = Map[Person, Set[Person]]

  // Tuples = finite ordered "lists"
  // tuples group things together
  val aTuple = (2, "hello, Scala") // Tuple2[Int, String] = (Int, String)
  val anotherTuple = new Tuple2(2, "hello, Scala") // same as above
  // a -> b is sugar for (a, b)

  println(aTuple._1) // 2
  println(aTuple.copy(_2 = "goodbye Java"))
  println(aTuple.swap) // ("hello, Scala", 2)

  // Maps - keys -> values
  // collections used to associate keys with values
  val aMap: Map[String, Int] = Map()
  // key -> value, you can use both (key, value) or key -> value to create a record in the map
  val phonebook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)
  val newPairing = "Mary" -> 678
  val newPhonebook = phonebook + newPairing // it returns a new map, maps are immutable
  println(newPhonebook)

  println(phonebook)

  // Map operations
  println(phonebook.contains("Jim"))
  println(phonebook("Jim"))
  println(phonebook("Mary")) // -1


  // functional on maps
  // map, flatMap, filter
  // they works as expected, but they operate on the pair (key, value)
  println(phonebook.map(pair => pair._1.toLowerCase -> pair._2))

  // filterKeys
  // we can filter the keys of the map
  println(phonebook.filterKeys(x => x.startsWith("J")))

  // mapValues
  // we can transform the values of the map
  println(phonebook.mapValues(number => "0245-" + number))

  // conversions to other collections
  println(phonebook.toList)
  println(List(("Daniel", 555)).toMap)
  // println(List(("Daniel", 555), "Jim").toMap) // it will throw an exception
  // add a pairing

  val names = List("Bob", "James", "Angela", "Mary", "Daniel", "Jim")
  // like groupBy in SQL
  println(names.groupBy(name => name.charAt(0))) // Map(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))

  /*
    1. What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900?
    2. Overly simplified social network based on maps
      Person = String
      - add a person to the network
      - remove
      - friend (mutual)
      - unfriend

      - number of friends of a person
      - person with most friends
      - how many people have NO friends
      - if there is a social connection between two people (direct or not)
   */


  // Why the value of "Jim" is 900? Because the last entry is the one that is kept
  // So CAREFUL when mapping keys! Because you can lose data

  // 2. Overly simplified social network based on maps

  // 1. What would happen if I had two original entries "Jim" -> 555 and "JIM" -> 900?
  val newMap = Map(("Jim", 555), "JIM" -> 900)
  println(newMap) // Map(Jim -> 555, JIM -> 900)
  println(newMap.map(pair => pair._1.toLowerCase -> pair._2)) // Map(jim -> 900)


  val empty: SocialNetwork = Map()
  val network = add(add(add(empty, "Bob"), "Mary"), "Jim")
  val network2 = friend(network, "Bob", "Mary")
  val network3 = friend(network2, "Bob", "Jim")

  def add(network: SocialNetwork, person: Person): SocialNetwork = {
    network + (person -> Set())
  }

  def remove(network: SocialNetwork, person: Person): SocialNetwork = {
    // we also should remove the person from the friends of the other people but let's  keep it simple
    network - person
  }

  def friend(network: SocialNetwork, a: Person, b: Person): SocialNetwork = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA + b)) + (b -> (friendsB + a))
  }

  def unfriend(network: SocialNetwork, a: Person, b: Person): SocialNetwork = {
    val friendsA = network(a)
    val friendsB = network(b)

    network + (a -> (friendsA - b)) + (b -> (friendsB - a))
  }

  def numberOfFriends(network: SocialNetwork, person: Person): Int = {
    network(person).size
  }

  def personWithMostFriends(network: SocialNetwork): Person = network.maxBy(_._2.size)._1


  def peopleWithNoFriends(network: SocialNetwork): Int = network.count(_._2.isEmpty)

  def socialConnection(network: SocialNetwork, a: Person, b: Person): Boolean = {
    // breadth-first search
    @scala.annotation.tailrec
    def bfs(target: Person, consideredPeople: Set[Person], discoveredPeople: Set[Person]): Boolean = {
      // if we have no more discovered people, then we have not found the target
      if (discoveredPeople.isEmpty) false
      else {
        // let's get the first person in the discovered people
        val person = discoveredPeople.head
        // if the person is the target, then we have found the target
        if (person == target) true
        // if the person is already considered, then we should not consider it again
        else if (consideredPeople.contains(person)) bfs(target, consideredPeople, discoveredPeople.tail)
        // otherwise, we should consider the person and add its friends to the discovered people
        else bfs(target, consideredPeople + person, discoveredPeople.tail ++ network(person))
      }
    }

    bfs(b, Set(), network(a) + a)
  }

  println(network3)
  println(unfriend(network3, "Bob", "Mary"))
  println(numberOfFriends(network3, "Bob"))
  println(personWithMostFriends(network3))
  println(peopleWithNoFriends(network3))
  println(socialConnection(network3, "Mary", "Jim"))


}
