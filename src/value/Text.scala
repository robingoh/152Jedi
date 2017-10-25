package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Text(val value: String) extends Literal {
  override def execute(env: Any) = ???
}
