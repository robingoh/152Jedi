package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Real(val value: Double) extends Literal {
  override def execute(env: Any) = ???
}