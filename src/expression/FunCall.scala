package expression
import context.{Environment, alu}
import value.{Closure, Value}

/**
  * Created by robingoh on 10/25/17.
  */
// (i.e., function calls or applications) use eager execution.
// The execute all of their operands (i.e., inputs) even if
// they don't need to.
case class FunCall(val operator: Identifier, val operands: List[Expression])
  extends Expression {

  // eagerly convert operands to arguments (values)
  // then ask alu to execute operator with these arguments
  // alu.execute(operator,args)
  def execute(env: Environment): Value = {
    val arguments = operands.map(_.execute(env))
    operator.execute(env) match {
      case closure: Closure => closure(arguments)
      case _ => alu.execute(operator, arguments)
    }
  }
}
