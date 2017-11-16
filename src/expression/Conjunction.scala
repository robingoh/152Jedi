package expression
import context.Environment
import value.{Boole, Value}

/**
  * Created by robingoh on 11/6/17.
  */
case class Conjunction(val operands: List[Expression])
  extends SpecialForm {

  override def execute(env: Environment): Value = {
    // execute operands from left to right until the answer is known
    var result = true
    for (operand <- operands if result) {
      if (operand.execute(env) == Boole(false))
        result = false
    }
    Boole(result)
  }
}
