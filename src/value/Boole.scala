package value

import context.Environment
import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Boole(val value: Boolean) extends Literal {

  def unary_! = Boole(!value)
  def &&(that: Boole) = Boole(this.value && that.value)
  def ||(that: Boole) = Boole(this.value || that.value)

  override def toString: String = value.toString

}

