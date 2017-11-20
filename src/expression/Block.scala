package expression
import context.Environment

/**
  * Created by robingoh on 11/13/17.
  */
case class Block(val expressions: List[Expression]) extends SpecialForm {
  def execute(env: Environment) = {
    // step 1 create tempEnv that extends env
    val tempEnv = new Environment(env)

    // step 2 execute and put into localEnv return last value
    for (expression <- expressions)
      expression.execute(tempEnv)

  }

  /*
  def z = 30
  { def x = 10; def y = 20; x + y }
      |||
  env vvv
  col1  col2
    z     30

  step 1
  create localEnv that extends env by val localEnv = new Environment(env)
  step 2

  */

}
