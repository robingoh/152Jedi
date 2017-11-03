package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */

case class Real (val value: Double) extends Literal with Ordered[Real] with Equals {
  def +(other: Real) = Real(this.value + other.value)
  def -(other: Real) = Real(this.value - other.value)
  def *(other: Real) = Real(this.value * other.value)
  def /(other: Real) = {
    try {
      Real(this.value / other.value)
    } catch {
      case e: ArithmeticException => println("Cannot divide by zero.")
    }
  }

  override def toString = value.toString

  def compare(other: Real): Int = if (this.value < other.value) -1 else if (other.value < this.value) 1 else 0

  override def canEqual(other: Any) =  other.isInstanceOf[Real]

  override def equals(other: Any): Boolean =
    other match {
      case other: Real => this.canEqual(other) && (other.value == this.value)
      case _ => false
    }

  override def hashCode = this.toString.##
}