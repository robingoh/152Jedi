package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Text(val value: String) extends Literal {
  def <(other: Text) = this.value.length < other.value.length
  def ==(s: String) = value == s
  def substring(begin: Integer, end: Integer) = value.substring(begin.value, end.value)
  def +(other: Text) = Text(this.value + other.value)

}
