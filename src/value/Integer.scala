package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Integer(val value: Int) extends Literal {
  override def execute(env: Any) = ???
}
