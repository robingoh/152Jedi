package context

import scala.util.parsing.combinator._
import expression._
import value._

/*
 * Notes:
 * disjunction reduces to conjunction reduces to equality ... reduces to term
 * if A reduces to B, then B will have higher precedence than A
 * Example: sum reduces to product, so a + b * c = a + (b * c)
 * Had to make some big corrections to numeral regex
 * This could probably have been a singleton
 */

class JediParsers extends RegexParsers {

  def expression: Parser[Expression] = declaration | conditional | disjunction | failure("Invalid expression")

  def declaration: Parser[Declaration] = "def" ~ identifier ~ "=" ~ expression ^^ {
    case "def"~id~"="~exp => Declaration(id, exp)
  }

  def conditional: Parser[Conditional] = "if" ~ "(" ~ expression ~ ")" ~ expression ~ opt("else" ~ expression) ^^ {
    case "if"~"("~cond~")"~cons~None => Conditional(cond, cons)
    case "if"~"("~cond~")"~cons~Some("else"~alt) => Conditional(cond, cons, alt)
  }


  def  disjunction: Parser[Expression] = conjunction ~ rep("||" ~> conjunction) ^^ {
    case con ~ Nil => con
    case con ~ more => Disjunction(con::more)
  }

  // conjunction ::= equality ~ ("&&" ~ equality)*

  // equality ::= inequality ~ ("==" ~ inequality)*

  // inequality ::= sum ~ (("<" | ">" | "!=") ~ sum)?

  //


  // negate(exp) = 0 - exp
  private def negate(exp: Expression): Expression = {
    val sub = Identifier("sub")
    val zero = Integer(0)
    FunCall(sub, List(zero, exp))
  }

  // sum ::= product ~ ("+" | "-") ~ product)*
  def sum: Parser[Expression] = product ~ rep(("+"|"-") ~ product ^^ {
    case "+"~s=>s
    case "-"~s=> negate(s)
  })^^{
    case p~Nil=> p
    case p~rest=>FunCall(Identifier("add"), p::rest)
  }

  // product ::= term ~ (("*" | "/") ~ term)*

  def term: Parser[Expression]  = funCall | literal | "("~>expression<~")"

  def literal = boole | real | integer | text | identifier


  // text ::= any chars bracketed by quotes
  def text: Parser[Text] = """\"[^"]+\"""".r ^^ {
    case chars => Text(chars.substring(1, chars.length - 1))
  }

  // integer ::= 0|(\+|-)?[1-9][0-9]*

  // real ::= (\+|-)?[0-9]+\.[0-9]+

  // boole ::= true | false

  // identifier ::= [a-zA-Z][a-zA-Z0-9]*

  // funCall ::= identifier ~ operands

  // operands ::= "(" ~ (expression ~ ("," ~ expression)*)? ~ ")"

}
