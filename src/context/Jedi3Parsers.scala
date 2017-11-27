package context

import expression.Expression

/**
  * Created by robingoh on 11/27/17.
  */

class Jedi3Parsers extends Jedi2Parsers {

  // assignment ::= identifier ~ "=" ~ expression
  def assignment: Parser[] = identifier ~ "=" ~> expression ^^ {

  }

  // iteration ::= "while" ~ "(" ~ expression ~ ")" ~ expression

  // dereference ::= "[" ~ expression ~ "]"


  override def expression: Parser[Expression] =
    declaration | conditional | iteration | disjunction | failure("Invalid expression")
  override def term: Parser[Expression]  =
    lambda | funCall | block | assignment | dereference | literal | "("~>expression<~")"
}