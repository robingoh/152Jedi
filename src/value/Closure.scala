package value

import context.Environment
import expression.{Expression, Identifier}

/**
  * Created by robingoh on 10/25/17.
  */
class Closure(val parameters: List[Identifier],
              val body: Expression,
              val definingEnv: Environment)
  extends Value {
  // callEnv is for implementing static scope or dynamic scope (extra credit)
  def apply(args: List[Value], callEnv: Environment = null): Value = {
    var tempEnv: Environment = null
    // if want dynamic scope
    if (callEnv != null) tempEnv = new Environment(callEnv)
    // else static scope
    else tempEnv = new Environment(definingEnv)

    tempEnv.bulkPut(parameters, args)
    body.execute(tempEnv)
  }
}
