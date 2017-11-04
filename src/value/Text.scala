package value

import expression.Literal

/**
  * Created by robingoh on 10/25/17.
  */
case class Text(val value: String) extends Literal with Ordered[Text] with Equals {
  def substring(begin: Integer, end: Integer) = value.substring(begin.value, end.value)
  def +(other: Text) = Text(this.value + other.value)

  override def toString: String = this.value

  override def canEqual(other: Any) =  other.isInstanceOf[Text]

  override def equals(other: Any): Boolean =
    other match {
      case other: Text => this.canEqual(other) && (other.value == this.value)
      case _ => false
    }

  override def compare(other: Text): Int = this.value.compare(other.value)
}