package value

import context.UndefinedException
import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Integer(val value: Int) extends Literal with Ordered[Integer] with Equals {
  def +(other: Integer) = Integer(this.value + other.value)
  def -(other: Integer) = Integer(this.value - other.value)
  def *(other: Integer) = Integer(this.value * other.value)
  def /(other: Integer) = Integer(this.value / other.value)

  override def toString = value.toString

  def compare(other: Integer): Int =
    if (this.value < other.value) -1 else if (other.value < this.value) 1 else 0

  override def canEqual(other: Any) =  other.isInstanceOf[Integer]

  override def equals(other: Any): Boolean =
    other match {
      case other: Integer => this.canEqual(other) && (other.value == this.value)
      case _ => false
    }

  override def hashCode = this.toString.##
}

object Integer {
  implicit def intToReal(n: Integer): Real = Real(n.value.toDouble)
}