package expression
import context.{Environment, TypeException}
import value.{Boole, Notification, Value}

/**
  * Created by robingoh on 11/27/17.
  */
case class Iteration(val condition: Expression,
                     val body: Expression)
  extends SpecialForm {
  def execute(env: Environment): Value = {
    if (!condition.execute(env).isInstanceOf[Boole])
      throw new TypeException
    while (condition.execute(env).asInstanceOf[Boole].value) {
      body.execute(env)
    }
    Notification.DONE
  }
}