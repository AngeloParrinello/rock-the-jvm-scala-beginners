package lectures.part2oop

import playground.{Cinderella, PrinceCharming, Princess}

// if you have multiple classes with the same name in different packages
// you can import them with aliasing
import java.util.Date // Date is a class in java.util
import java.sql.{Date => SqlDate} // Date is a class in java.sql


object PackagingAndImports extends App {

    // package members are accessible by their simple name
    // i.e. since Writer is in the same package as this class, I can use it without importing it
    val writer = new Writer("Daniel", "RockTheJVM", 2018)

    // import the package
    // val princess = new Princess // this will not compile because the class is not imported
    val princess = new Princess // this will compile, after the import
    // val princess = new playground.Princess // fully qualified name, this will compile, without the import

    // packages are in hierarchy
    // matching folder structure.

    // package object
    sayHello
    println(SPEED_OF_LIGHT)

    // imports
    val cinderella = new Cinderella
    val prince = new playground.Prince

    // 1. use FQ names - fully qualified names (the name includes the package name)
    val date = new java.util.Date
    val sqlDate = new java.sql.Date(2018, 5, 4)

    // 2. use aliasing
    val princeCharming = new PrinceCharming
    val realDate = new Date
    val sqlAliasDate = new SqlDate(2018, 5, 4)

    // default imports
    // java.lang - String, Object, Exception
    // scala - Int, Nothing, Function
    // scala.Predef - println, ???
}
