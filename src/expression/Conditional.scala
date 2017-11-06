package expression
import context.Environment
import value.Value

/**
  * Created by robingoh on 11/6/17.
  */
case class Conditional(val operands: List[Expression])
  extends SpecialForm {

  override def execute(env: Environment): Value = ???
}
