package expression

/**
  * Created by robingoh on 10/25/17.
  */
case trait Expression {
  def execute(env: Environment): Value
}
