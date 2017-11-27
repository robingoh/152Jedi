package expression
import context.Environment
import value.Value

/**
  * Created by robingoh on 11/27/17.
  */
case class Assignment(val variable: Identifier,
                      val update: Expression)
  extends SpecialForm {
  def execute(env: Environment): Value = ???
}
