package context

import expression.{Assignment, Expression, Iteration}
import value.Variable
import scala.util.parsing.combinator._


/**
  * Created by robingoh on 11/27/17.
  */

class Jedi3Parsers extends Jedi2Parsers {

//  def block: Parser[Block] = "{" ~> expression ~ rep(";" ~> expression) <~ "}" ^^ {
//    case anExpression ~ moreExpressions => Block(anExpression :: moreExpressions)
//  }

  // assignment ::= identifier ~ "=" ~ expression
  def assignment: Parser[Assignment] = identifier ~ "=" ~ expression ^^ {
    case anIdentifier ~ "=" ~ someExpression => Assignment(anIdentifier, someExpression)

  }

  // iteration ::= "while" ~ "(" ~ expression ~ ")" ~ expression
  def iteration: Parser[Iteration] = "while" ~ "(" ~ expression ~ ")" ~ expression ^^ {
    case "while" ~ "(" ~ condition ~ ")" ~ body => Iteration(condition, body)
  }

  // dereference ::= "[" ~ expression ~ "]"
  def dereference: Parser[Expression] = "[" ~> expression <~ "]" ^^ {
    case anExpresssion => anExpresssion
  }


  override def expression: Parser[Expression] =
    declaration | conditional | iteration | disjunction | failure("Invalid expression")
  override def term: Parser[Expression] =
    lambda | funCall | block | assignment | dereference | literal | "("~>expression<~")"
}