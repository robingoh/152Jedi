package expression
import context.{Environment, UndefinedException}
import value.{Notification, Value}

/**
  * Created by robingoh on 11/27/17.
  */
case class Assignment(val variable: Identifier,
                      val update: Expression)
  extends SpecialForm {
  def execute(env: Environment): Value = {
    if (!env.contains(variable))
      throw new UndefinedException(variable)
    env.put(variable, update.execute(env))
    Notification.OK
  }
}
