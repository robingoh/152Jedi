package value

/**
  * Created by robingoh on 10/25/17.
  */
class Notification(val message: String) extends Value {
  override def toString = message
}

object Notification {
  def apply(message: String) = new Notification(message)
  val OK = Notification("OK")
  val DONE = Notification("DONE")
  val UNSPECIFIED = Notification("UNSPECIFIED")

}
