package expression
import context.Environment

/**
  * Created by robingoh on 11/13/17.
  */
case class Block(val expressions: List[Expression]) extends SpecialForm {
  def execute(env: Environment) = {
    // step 1 create localEnv that extends env
    val localEnv = new Environment(env)

    // step 2 execute and put into localEnv
    localEnv()
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
