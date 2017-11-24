package context

import expression.{Block, Expression, Identifier, Lambda}

/**
  * Created by robingoh on 11/20/17.
  */
class Jedi2Parsers extends Jedi1Parsers {
  // params parser
  // a parameter list is zero or more comma-separated identifiers bracketed by parentheses:
  // params ::= "(" ~ (identifier ~ ("," ~ identifier)*)? ~ ")"
  def params: Parser[List[Identifier]] = "(" ~> opt(identifier ~ rep("," ~> identifier)) <~ ")" ^^ {
    case None => Nil
    case Some(identifier ~ identifiers) => identifier :: identifiers
  }

  // lambda parser
  // lambda ::= "lambda" ~ params ~ expression
  def lambda: Parser[Lambda] = "lambda" ~> params ~ expression ^^ {
    case someParams ~ someExpression => Lambda(someParams, someExpression)
  }

  // block parser
  // a block is one or more semi-colon separated expressions bracketed by curly braces:
  // block ::= "{" ~ expression ~ (";" ~ expression)* ~ "}"
  def block: Parser[Block] = "{" ~> expression ~ rep(";" ~> expression) <~ "}" ^^ {
    case anExpression ~ moreExpressions => Block(anExpression :: moreExpressions)
  }

  // override of term parser
  override def term: Parser[Expression] = lambda | funCall | block | literal | "("~>expression<~")"
}
