package expression
import context.Environment
import value.Value

/**
  * Created by robingoh on 11/6/17.
  */
case class Declaration extends SpecialForm {
  override def execute(env: Environment): Value = {
    /*
    1. value = exp.execute
    2. env.put(id,value)
    3. return ok <- notification
     */
  }
}
