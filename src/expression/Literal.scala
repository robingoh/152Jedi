package expression

import context.Environment
import value.Value

/**
  * Created by robingoh on 10/25/17.
  */
/*
  From lecture Nov 1

 */
trait Literal extends Expression with Value {
  def execute(env: Environment) = this
}
