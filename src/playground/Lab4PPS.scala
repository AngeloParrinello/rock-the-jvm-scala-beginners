package playground

import playground.Lab3PPS.Person.Teacher

object Lab4PPS extends App {

  trait ComplexNumber {
    def real: Double
    def imaginary: Double
  }

  object ComplexNumber {
    def apply(real: Double, imaginary: Double): ComplexNumber =
      new ComplexNumberImpl(real, imaginary)

    private class ComplexNumberImpl(override val real: Double, override val imaginary: Double) extends ComplexNumber {
      assert(real != 0 || imaginary != 0, "Both real and imaginary parts cannot be zero")
    }
  }

  val complexNumber = ComplexNumber(1, 2)
  println(complexNumber, complexNumber.real, complexNumber.imaginary)

  val complexNumber2 = ComplexNumber(1, 2)
  println(complexNumber == complexNumber2)

  case class ComplexNumberCase(real: Double, imaginary: Double) extends ComplexNumber

  val complexNumberCase = ComplexNumberCase(1, 2)
  val complexNumberCase2 = ComplexNumberCase(1, 2)
  println(complexNumberCase == complexNumberCase2)

  case class Course(name: String, code: String, credits: Int)
  trait Student {
    def name: String
    def courses: playground.Lab3PPS.List[Course]
    def hasTeacher(teacher: Teacher): Boolean
  }

  object Student {
    def apply(name: String, courses: playground.Lab3PPS.List[Course]): Student =
      new StudentImpl(name, courses)

    private class StudentImpl(override val name: String, override val courses: playground.Lab3PPS.List[Course]) extends Student {
      override def hasTeacher(teacher: Teacher): Boolean = Lab3PPS.List.contains(courses, teacher.course)
    }
  }



}
