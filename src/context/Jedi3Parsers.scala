package context

import expression.{Assignment, Expression, Iteration}
import value.Variable

/**
  * Created by robingoh on 11/27/17.
  */

class Jedi3Parsers extends Jedi2Parsers {

  // assignment ::= identifier ~ "=" ~ expression
  def assignment: Parser[Assignment] = identifier ~ "=" ~> expression ^^ {
    case someIdentifier ~ someExpression => Assignment(someIdentifier, someExpression)

  }

  // iteration ::= "while" ~ "(" ~ expression ~ ")" ~ expression
  def iteration: Parser[Iteration] = "while" ~ "(" ~> expression ~ ")" ~> expression ^^ {
    case condition ~ body => Iteration(condition, body)
  }

  // dereference ::= "[" ~ expression ~ "]"
  def dereference: Parser[Variable] = "[" ~> expression <~ "]" ^^ {
    case variable => variable
  }


  override def expression: Parser[Expression] =
    declaration | conditional | iteration | disjunction | failure("Invalid expression")
  override def term: Parser[Expression]  =
    lambda | funCall | block | assignment | dereference | literal | "("~>expression<~")"
}