package expression

import context.Environment
import value.Value

/**
  * Created by robingoh on 10/25/17.
  */

//  Identifiers are names of values. To execute an
// identifier we simply look it up in the environment.
case class Identifier(val name: String) extends Expression {
  override def toString = name
  override def execute(env: Environment) =
    env.get(this) match {
      case None => throw new Exception("Undefined: " + name)
      case Some(value) => value
    }
}
