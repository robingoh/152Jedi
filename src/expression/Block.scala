package expression
import context.Environment
import value.Value

/**
  * Created by robingoh on 11/13/17.
  */
case class Block(val expressions: List[Expression]) extends SpecialForm {
  def execute(env: Environment) = {
    // step 1 create tempEnv that extends env
    val tempEnv = new Environment(env)

    // step 2 execute and put into localEnv return last value
    var lastValue: Value = null
    for (expression <- expressions)
      lastValue = expression.execute(tempEnv)
    lastValue
  }
}
