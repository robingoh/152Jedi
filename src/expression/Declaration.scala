package expression
import context.Environment
import value.{Notification, Value}

/**
  * Created by robingoh on 11/6/17.
  */
case class Declaration(val id: Identifier, val exp: Expression) extends SpecialForm {
  override def execute(env: Environment) = {
    env.put(id, exp.execute(env))
    Notification.OK // ?
    /*
    1. value = exp.execute
    2. env.put(id,value)
    3. return ok <- notification
     */
  }
}
