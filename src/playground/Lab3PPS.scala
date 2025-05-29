package playground

import playground.Lab2PPS.Option.None
import playground.Lab2PPS.Option._
import playground.Lab3PPS.List.Cons

import scala.annotation.tailrec

object Lab3PPS extends App {

  // A generic linkedlist
  sealed trait List[+E]

  // a companion object (i.e., module) for List
  object List {
    case class Cons[E](head: E, tail: List[E]) extends List[E]

    case class Nil[E]() extends List[E]

    def sum(l: List[Int]): Int = l match {
      case Cons(h, t) => h + sum(t)
      case _ => 0
    }

    def map[A, B](l: List[A])(mapper: A => B): List[B] = l match {
      case Cons(h, t) => Cons(mapper(h), map(t)(mapper))
      case Nil() => Nil()
    }

    def filter[A](l1: List[A])(pred: A => Boolean): List[A] = l1 match {
      case Cons(h, t) if pred(h) => Cons(h, filter(t)(pred))
      case Cons(_, t) => filter(t)(pred)
      case Nil() => Nil()
    }

    @tailrec
    def drop[A](l: List[A], n: Int): List[A] = l match {
      case Cons(_, _) if n <= 0 => l
      case Cons(_, t) => drop(t, n - 1)
      case Nil() => Nil()
    }

    def flatMap[A, B](l: List[A])(f: A => List[B]): List[B] = l match {
      case Cons(h, t) => append(f(h), flatMap(t)(f))
      case Nil() => Nil()
    }

    def append[A](l1: List[A], l2: List[A]): List[A] = l1 match {
      case Cons(h, t) => Cons(h, append(t, l2))
      case Nil() => l2
    }

    def mapInTermsOfFlatMap[A, B](l: List[A])(mapper: A => B): List[B] =
      flatMap(l)(v => Cons(mapper(v), Nil()))

    def filterInTermsOfFlatMap[A](l1: List[A])(pred: A => Boolean): List[A] =
      flatMap(l1) {
        case h if pred(h) => Cons(h, Nil())
        case _ => Nil()
      }

    def max(l: List[Int]): Lab2PPS.Option[Int] = l match {
      case Nil() => Lab2PPS.Option.None()
      case Cons(h, t) => Some(max(t) match {
        case None() => h
        case Some(v) => if (h > v) h else v
      })
    }

    def contains[A](l: List[A], elem: A): Boolean = l match {
      case Cons(h, t) => if (h == elem) true else contains(t, elem)
      case Nil() => false
    }

  }

  // An ADT: type + module
  sealed trait Person
  object Person {

    case class Student(name: String, year: Int) extends Person

    case class Teacher(name: String, course: String) extends Person

    def name(p: Person): String = p match {
      case Student(n, _) => n
      case Teacher(n, _) => n
    }

    def coursesOfTeachers(l: List[Person]): List[String] =
      List.flatMap(l) {
        case Person.Teacher(_, course) => List.Cons(course, List.Nil())
        case Person.Student(_,_) => List.Nil()
      }

  }

  sealed trait Stream[A]

  object Stream {
    private case class Empty[A]() extends Stream[A]
    private case class Cons[A](head: () => A, tail: () => Stream[A]) extends Stream[A]

    def empty[A](): Stream[A] = Empty()

    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }

    def toList[A](stream: Stream[A]): List[A] = stream match {
      case Cons(h,t) => List.Cons(h(), toList(t()))
      case _ => List.Nil()
    }

    def map[A, B](stream: Stream[A])(f: A => B): Stream[B] = stream match {
      case Cons(head, tail) => cons(f(head()), map(tail())(f))
      case _ => Empty()
    }

    def filter[A](stream: Stream[A])(pred: A => Boolean): Stream[A] = stream match {
      case Cons(head, tail) if (pred(head())) => cons(head(), filter(tail())(pred))
      case Cons(head, tail) => filter(tail())(pred)
      case _ => Empty()
    }

    def take[A](stream: Stream[A])(n: Int): Stream[A] = (stream,n) match {
      case (Cons(head, tail), n) if n>0 => cons(head(), take(tail())(n - 1))
      case _ => Empty()
    }

    def iterate[A](init: => A)(next: A => A): Stream[A] = cons(init, iterate(next(init))(next))

    def drop[A](stream: Stream[A])(n: Int): Stream[A] = (stream, n) match {
      case (Cons(_, tail), n) if n > 0 => drop(tail())(n - 1)
      case _ => stream
    }

    def constant[A](a: A): Stream[A] = iterate(a)(_ => a)

    def fibonacci: Stream[Int] = {
      def go(f0: Int, f1: Int): Stream[Int] =
        {
          // here if I call the first element f0, the valuation of the second element go(...) will not be executed

          cons(f0, go(f1, f0 + f1))
        }
      go(0, 1)
    }
  }

  println(Person.name(Person.Student("mario",2015)))

  import Person._
  println(name(Student("mario",2015)))

  val l = List.Cons(10, List.Cons(20, List.Cons(30, List.Nil())))
  println(List.sum(l)) // 60

  import List._

  println(sum(map(filter(l)(_ >= 20))(_ + 1))) // 21+31 = 52

  val lst = Cons(10, Cons(20, Cons(30, Nil())))
  println(drop(lst, 1)) // Cons (20 , Cons (30 , Nil ()))
  println(drop(lst, 2)) // Cons (30 , Nil ())
  println(drop(lst, 5)) // Nil ()

  println(flatMap(lst)(v => Cons(v + 1, Nil()))) // Cons (11 , Cons (21 , Cons (31 , Nil ())))
  println(flatMap(lst)(v => Cons(v + 1, Cons(v + 2, Nil()))))
  // Cons (11 , Cons (12 , Cons (21 , Cons (22 , Cons (31 , Cons (32 , Nil ()))))))

  println(mapInTermsOfFlatMap(lst)(_ + 1)) // Cons (11 , Cons (21 , Cons (31 , Nil ()))
  println(filterInTermsOfFlatMap(lst)(_ % 2 != 0))

  println(max(lst)) // Some (30)
  println(max(Nil())) // None ()

  val persons = List.Cons(Person.Teacher("mario", "scala"), List.Cons(Person.Student("luca", 2015), List.Nil()))
  println(coursesOfTeachers(persons)) // Cons (scala , Nil ())

  val stream = Stream.cons(10, Stream.cons(20, Stream.cons(30, Stream.empty())))
  println(Stream.toList(stream)) // Cons (10 , Cons (20 , Cons (30 , Nil ())))
  println(Stream.toList(Stream.map(stream)(_ + 1))) // Cons (11 , Cons (21 , Cons (31 , Nil ())))
  println(Stream.toList(Stream.filter(stream)(_ >= 20))) // Cons (20 , Cons (30 , Nil ()))
  println(Stream.toList(Stream.take(stream)(2))) // Cons (10 , Cons (20 , Nil ()))
  println(Stream.toList(Stream.drop(stream)(2))) // Cons (30 , Nil ())
  println(Stream.toList(Stream.take(Stream.constant("x"))(10)))
  println(Stream.toList(Stream.take(Stream.fibonacci)(8)))
}
