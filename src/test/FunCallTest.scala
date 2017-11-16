package test

/**
  * Created by robingoh on 11/6/17.
  */
package test

import expression._
import context._
import value._


object FunCallTest extends App {
  val globalEnvironment = new Environment
  val operands = List(Integer(6), Integer(7))
  var exp = FunCall(Identifier("add"), operands)
  println(exp.execute(globalEnvironment))
  exp = FunCall(Identifier("less"), operands)
  println(exp.execute(globalEnvironment))
  exp = FunCall(Identifier("mul"), operands)
  println(exp.execute(globalEnvironment))
}

// test output
/*
13
true
42

Process finished with exit code 0
 */