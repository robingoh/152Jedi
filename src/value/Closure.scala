package value

import context.Environment
import expression.{Expression, Identifier}

/**
  * Created by robingoh on 10/25/17.
  */
class Closure(val parameters: List[Identifier],
              val body: Expression,
              val defEnv: Environment)
  extends Value {
  def apply(args: List[Value],
           // for static scope or dynamic scope
            callEnv: Environment = null): Value = {
    val tempEnv = new Environment(defEnv)
    tempEnv.bulkPut(parameters, args)
    body.execute(tempEnv)
  }
}
// I think this is done
