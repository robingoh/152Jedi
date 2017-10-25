package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Boole(val value: Boolean) extends Literal {
  override def execute(env: Any) = ???
}