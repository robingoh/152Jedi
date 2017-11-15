package expression
import context.Environment
import value.{Boole, Notification, Value}

/**
  * Created by robingoh on 11/6/17.
  */
// 1 condition
//
case class Conditional(val condition: Expression,
                       val consequent: Expression,
                       val alternative: Expression = null)
  extends SpecialForm {

  override def execute(env: Environment): Value = {
    if (condition.execute(env).asInstanceOf[Boole].value)
      consequent.execute(env)
    else if (alternative != null)
        alternative.execute(env)
    else
      Notification.UNSPECIFIED
  }
}
