package expression
import context.Environment
import value.Value

/**
  * Created by robingoh on 11/29/17.
  */
case class Object(val members: List[Declaration],
                  val delegate: Identifier = null)
extends SpecialForm {
  def execute(env: Environment): Value = {
    val tempEnv = new Environment(env)
    for (member <- members)
      member.execute(tempEnv)
    tempEnv
  }
}

/*
1. create tempEnv extending env or delegate
2. execute members in tempEnv
3/ return tempEnv
 */