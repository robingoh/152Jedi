package expression
import context.Environment
import value.{Boole, Value}

/**
  * Created by robingoh on 11/6/17.
  */
case class Disjunction(val operands: List[Expression])
  extends SpecialForm {

  override def execute(env: Environment): Value = {
    var result = false
    for (operand <- operands if !result) {
      if (operand.execute(env) == Boole(true))
        result = true
    }
    Boole(result)
  }
}
