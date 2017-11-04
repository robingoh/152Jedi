package test

import value._

object TextTest extends App {

  val s1 = Text("California")
  val s2 = Text("...")
  val s3 = Text("Dreaming")
  val s4 = s1 + s2 + s3

  println(s"s1 = $s1")
  println(s"s2 = $s2")
  println(s"s3 = $s3")
  println("\n// testing +, s4 = s1 + s2 + s3")
  println("s4 = " + s4)

  println("\n// Testing substring")
  println("substring of s4 from position 3 until 8")
  println(s4.substring(Integer(3), Integer(8)))

  println("\n// Testing <")
  println(s1 + " < " + s3 + " = " + (s1 < s3))
  println("\n// Testing >")
  println(s1 + " > " + s3 + " = " + (s1 > s3))

  println("\n// Testing ==")
  println(s"$s1 == $s3 = ${s1 == s3}")
  val s5 = Text("California")
  println(s"\ns5 = $s5")
  println("// testing s1 == s5")
  println(s"$s1 == $s5 = ${s1 == s5}\n")

  println("Test originated from Jedi 0.0 homework prompt")
  println("\"cat\" < \"dog\" = " + (Text("cat") < Text("dog")))
  println("animal = \"cat\" + \"fish\" = " + (Text("cat") + Text("fish")))
}
