package expression
import context.Environment
import value.{Closure, Value}

/**
  * Created by robingoh on 11/20/17.
  */
case class Lambda(val parameters: List[Identifier], val body: Expression)
  extends SpecialForm {
  def execute(env: Environment): Value = new Closure(parameters, body, env)

}

/*
-> def a = 10
OK
-> def foo = lambda(x) a+x
OK
-> foo(3+4)

 */
