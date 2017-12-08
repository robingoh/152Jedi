package context

import scala.util.parsing.combinator._
import expression._
import value._

class Jedi4Parsers extends Jedi3Parsers {

  // change the name of the identifier parser to name:
  def name: Parser[String] = """[a-zA-Z][a-zA-Z0-9]*""".r ^^ {
    case someString => someString
  }

  // override the inherited identifier parser with a parser that can handle qualified names:
  override def identifier: Parser[Identifier] = name ~ rep("." ~> name) ^^ {
    case someName ~ moreNames => someName :: moreNames
  }

  // define the object parser:
  // obj ::=  "object" ~ "{" ~ declaration ~ (";" ~ declaration)* ~ "}" ~ opt("extends" ~ identifier)
  // note that "object" is a Scala reserved word, so we must name our parser obj.
  def obj: Parser[Object] = "object" ~> "{" ~> declaration ~ rep(";" ~> declaration) ~ "}" ~>
    opt("extends" ~> identifier) ^^ {
    
  }

  override def term: Parser[Expression]  = obj | lambda | funCall | block | assignment |
    dereference | literal | "("~>expression<~")"
}