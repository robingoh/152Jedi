package expression

/**
  * Created by robingoh on 11/27/17.
  */
case class Iteration(val condition: Expression,
                     val body: Expression)
  extends Iteration {

}
