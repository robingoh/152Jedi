package expression

import context.Environment
import value.Value

/**
  * Created by robingoh on 10/25/17.
  */
trait Expression {
  def execute(env: Environment): Value
}
