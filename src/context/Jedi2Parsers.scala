package context

import scala.util.parsing.combinator._
import expression.{Expression, _}
import value._

/**
  * Created by robingoh on 11/20/17.
  */
class Jedi2Parsers extends Jedi1Parsers {
  // params parser
  // a parameter list is zero or more comma-separated identifiers bracketed by parentheses:
  // params ::= "(" ~ (identifier ~ ("," ~ identifier)*)? ~ ")"

  // lambda parser
  // lambda ::= "lambda" ~ params ~ expression

  // block parser
  // a block is one or more semi-colon separated expressions bracketed by curly braces:
  // block ::= "{" ~ expression ~ (";" ~ expression)* ~ "}"


  // override of term parser
  override def term: Parser[Expression]  = lambda | funCall | block | literal | "("~>expression<~")"

}
