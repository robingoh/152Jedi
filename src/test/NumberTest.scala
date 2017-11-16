
package test

import value.{Integer, Real}

object NumberTest extends App {
  println("Testing Integer")
  var i1 = Integer(7)
  var i2 = Integer(6)
  println(s"i1 = $i1")
  println(s"i2 = $i2")

  println("i1 + i2 = " + (i1 + i2))
  println("i1 - i2 = " + (i1 - i2))
  println("i1 * i2 = " + (i1 * i2))

  println("i1 / i2 = " + (i1 / i2) + "\n")
  try {
    println("i1 / 0 = " + (i1 / Integer(0)) + "\n")
  } catch {
    case e: ArithmeticException => println("Cannot divide by zero.")
  }

  println("i1 == i2 = " + (i1 == i2))
  println("i1 < i2 = " + (i1 < i2))
  println("i1 > i2 = " + (i1 > i2))

  println("i1.## = " + i1.##)

  println("\nTesting Real")
  var r1 = Real(3.14)
  var r2 = Real(2.71)
  println(s"r1 = $r1")
  println(s"r2 = $r2")

  println("r1 + r2 = " + (r1 + r2))
  println("r1 - r2 = " + (r1 - r2))
  println("r1 * r2 = " + (r1 * r2))

  println("r1 / r2 = " + (r1 / r2) + "\n")
  println("r1 / 0.0 = " + (r1 / Real(0.0)) + "\n")

  println("r1 == r2 = " + (r1 == r2))
  println("r1 < r2 = " + (r1 < r2))
  println("r1 > r2 = " + (r1 > r2))

  println("r1.## = " + r1.##)

  println("\nTesting intToReal")
  println(s"r1 = $r1")
  println(s"i1 = $i1")
  val r3 = Real(7.0)
  println(s"r3 = $r3")

  println("r1 + i1 = " + (r1 + i1))
  println("r1 - i1 = " + (r1 - i1))
  println("r1 * i1 = " + (r1 * i1))
  println("r1 / i1 = " + (r1 / i1))

  println("r1 == i1 = " + (r1 == i1))
  println("i1 == r3 = " + (i1 == r3))
  println("r1 < i1 = " + (r1 < i1))
  println("r1 > i1 = " + (r1 > i1))
}

// test output
/*
Testing Integer
i1 = 7
i2 = 6
i1 + i2 = 13
i1 - i2 = 1
i1 * i2 = 42
i1 / i2 = 1

Cannot divide by zero.
i1 / 0 = ()

i1 == i2 = false
i1 < i2 = false
i1 > i2 = true
i1.## = 55

Testing Real
r1 = 3.14
r2 = 2.71
r1 + r2 = 5.85
r1 - r2 = 0.43000000000000016
r1 * r2 = 8.5094
r1 / r2 = 1.1586715867158672

r1 / 0.0 = Infinity

r1 == r2 = false
r1 < r2 = false
r1 > r2 = true
r1.## = 1565118

Testing intToReal
r1 = 3.14
i1 = 7
r3 = 7.0
r1 + i1 = 10.14
r1 - i1 = -3.86
r1 * i1 = 21.98
r1 / i1 = 0.44857142857142857
r1 == i1 = false
i1 == r3 = false
r1 < i1 = true
r1 > i1 = false
 */