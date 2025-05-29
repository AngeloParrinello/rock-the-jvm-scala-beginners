package playground

object Lab2PPS {

  def parity(n: Int): String = {
    if (n % 2 == 0) "even" else "odd"
  }

  def parityMatch(n: Int): String = n match {
    case n if n % 2 == 0 => "even"
    case _ => "odd"
  }

  val parity: (Int => String) = (n: Int) => if (n % 2 == 0) "even" else "odd"

  def negDef(f: String => Boolean): String => Boolean = {
    (s: String) => !f(s)
  }
  val negVal: (String => Boolean) => String => Boolean = f => s => !f(s)

  val empty = (s: String) => s.isEmpty
  val nonEmpty = negVal(empty)
  println(nonEmpty("Scala"))
  println(nonEmpty(""))

  def genericNegDef[A](f: A => Boolean): A => Boolean = {
    (a: A) => !f(a)
  }

  val relationBetweenThreeNumberCheckCurriedFunType: Int => Int => Int => Boolean =
    (x: Int) => (y: Int) => (z: Int) => x < y && y < z

  println(relationBetweenThreeNumberCheckCurriedFunType(1)(2)(3))

  val relationBetweenThreeNumberCheckFunType: (Int, Int, Int) => Boolean =
    (x: Int, y: Int, z: Int) => x < y && y < z

  def relationBetweenThreeNumberCheckFunType(x: Int, y: Int, z: Int): Boolean = x < y && y < z

  def defRelationBetweenThreeNumberCheckCurriedFunType(x: Int)(y: Int)(z: Int): Boolean = x < y && y < z

  def functionalCompositions(f: Int => Int, g: Int => Int): Int => Int = x => f(g(x))

  def genericFunctionalComposition[A, B, C](f: A => B, g: B => C): A => C = x => g(f(x))

  println(genericFunctionalComposition[Int, Double, String](_.toDouble, _.toString)(1))

  def fib(n: Int): Int = n match {
    case 0 => 0
    case 1 => 1
    case _ => fib(n - 1) + fib(n - 2)
  }

  def fibTail(n: Int) = {
    @scala.annotation.tailrec
    def _fib(n: Int, prev: Int, cur: Int): Int = n match {
      case 0 => prev
      case _ => _fib(n - 1, cur, prev + cur)
    }
    _fib(n, 0, 1)
  }

  println(fib(8)) // 21

  sealed trait Shape
  case class Circle(radius: Double) extends Shape
  case class Rectangle(width: Double, height: Double) extends Shape
  case class Square(side: Double) extends Shape

  object Shape {
    def area(shape: Shape): Double = shape match {
      case Circle(radius) => math.Pi * math.pow(radius, 2)
      case Rectangle(width, height) => width * height
      case Square(side) => math.pow(side, 2)
    }

    def perimeter(shape: Shape): Double = shape match {
      case Circle(radius) => 2 * math.Pi * radius
      case Rectangle(width, height) => 2 * (width + height)
      case Square(side) => 4 * side
    }
  }

  sealed trait Option[A]
  object Option {
    case class Some[A](value: A) extends Option[A]
    case class None[A]() extends Option[A]

    def isEmpty[A](opt: Option [A]): Boolean = opt match {
      case None () => true
      case _ => false
    }

    def getOrElse[A](opt : Option [A], orElse: A ): A = opt match {
      case Some (value) => value
      case _ => orElse
    }

    def flatMap[A, B](opt: Option[A])(f: A => Option[B]): Option[B] = opt match {
      case Some(value) => f(value)
      case _ => None()
    }

    def filter[A](opt: Option[A])(predicate: A => Boolean): Option[A] = opt match {
      case Some(value) if predicate(value) => opt
      case _ => None()
    }

    def map[A](opt: Option[A])(f: A => A): Option[A] = opt match {
      case Some(value) => Some(f(value))
      case _ => None()
    }

    def map2[A, B](opt1: Option[A], opt2: Option[A])(f: (A, A) => B): Option[B] = (opt1, opt2) match {
      case (_, None()) => None()
      case (None(), _) => None()
      case (Some(value1), Some(value2)) => Some(f(value1, value2))
      case _ => None()
    }

    println(Option.map2(Some(1), Some(2))(_ + _)) // Some(3)

  }

}
