package expression

import context.Environment


/**
  * Created by robingoh on 10/25/17.
  */

//  Identifiers are names of values. To execute an
// identifier we simply look it up in the environment.
// Jedi 1
//case class Identifier(val name: String) extends Expression {
//  override def toString = name
//  override def execute(env: Environment) =
//    env.get(this) match {
//      case None => throw new UndefinedException(Identifier(name))
//      case Some(value) => value
//    }
//}

// Jedi 2
case class Identifier(val name: String) extends Expression {
  override def toString = name
  def execute(env: Environment) = env(this)
}
