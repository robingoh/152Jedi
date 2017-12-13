package expression
import context.{Environment, JediException}
import value.{Notification, Value}

/**
  * Created by robingoh on 11/6/17.
  */
case class Declaration(val id: Identifier, val exp: Expression) extends SpecialForm {
  override def execute(env: Environment) = {
    def value = exp.execute(env)

    // 11/27
    // does not allow redefinition of variable in hash table
    if (env.contains(id))
      throw new JediException("does not allow redefinition of variable")
    env.put(id, value)
    Notification.OK

    /*
    1. value = exp.execute
    2. env.put(id,value)
    3. return ok <- notification
     */
  }
}
