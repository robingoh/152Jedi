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

class Jedi1Parsers extends RegexParsers {

  def identifier: Parser[Identifier]

  def expression: Parser[Expression] = declaration | conditional | disjunction | failure("Invalid expression")

  def declaration: Parser[Declaration] = "def" ~ identifier ~ "=" ~ expression ^^ {
    case "def"~id~"="~exp => Declaration(id, exp)
  }

  def conditional: Parser[Conditional] = "if" ~ "(" ~ expression ~ ")" ~ expression ~ opt("else" ~ expression) ^^ {
    case "if"~"("~cond~")"~cons~None => Conditional(cond, cons)
    case "if"~"("~cond~")"~cons~Some("else"~alt) => Conditional(cond, cons, alt)
  }

  //                                           // repeat        > drops || from the tree
  def  disjunction: Parser[Expression] = conjunction ~ rep("||" ~> conjunction) ^^ {
    case con ~ Nil => con
    case con ~ more => Disjunction(con::more)
  }
  //                                                                   // transform
  def conjunction: Parser[Expression] = equality ~ rep("&&" ~> equality) ^^ {
    case expression ~ Nil => expression
    case expression ~ moreStuffs => Conjunction(expression :: moreStuffs)// should be a list of expression

   }

  def equality: Parser[Expression] = inequality ~ rep("==" ~> inequality) ^^ {
    case expression ~ Nil => expression
    case expression ~ moreStuffs => Equality(expression :: moreStuffs)
  }

  def inequality: Parser[Expression] = sum ~ opt(("<" | ">" | "!=") ~> sum)) ^^ {
    case sum ~ None => sum // ?
    case sum ~ Some(anOperator ~ anotherSum) => anOperator match {
      case "<" => FunCall(Identifier("less"), List(sum, anotherSum))
      case ">" => FunCall(Identifier("more"), List(sum, anotherSum))
      case "!=" => FunCall(Identifier("notEqual"), List(sum, anotherSum))
    }
  }

  // p1 - p2 must be parsed into p1 + (-p2)
  // same with product, t1 / t2 must be parsed into t1 * 1/t2


  // negate(exp) = 0 - exp
  private def negate(exp: Expression): Expression = {
    val sub = Identifier("sub")
    val zero = Integer(0)
    FunCall(sub, List(zero, exp))
  }

  // sum ::= product ~ ("+" | "-") ~ product)*
  def sum: Parser[Expression] = product ~ rep(("+"|"-") ~ product ^^ {
    // pre-transformation
    case "+" ~ s => s
    case "-" ~ s => negate(s)
  })^^{
    case p~Nil=> p
    case p~rest=>FunCall(Identifier("add"), p::rest)
  }

  private def inverse(exp: Expression): Expression = {
    val divide = Identifier("div")
    val one = Integer(1)
    FunCall(divide, List(one, exp))
  }
  // product ::= term ~ (("*" | "/") ~ term)*
  // a * b / c transformed into a * b * (1 / c)
  def product: Parser[Expression] = term ~ rep(("*"|"/") ~ term ^^ {
    // pre-transformation
    case "*" ~ s => s
    case "/" ~ s => inverse(s)
  }) ^^ {
    case p~Nil=> p
    case p~rest=>FunCall(Identifier("mul"), p::rest)
  }


  def term: Parser[Expression]  = funCall | literal | "("~>expression<~")"

  def literal = boole | real | integer | text | identifier


  // text ::= any chars bracketed by quotes
  def text: Parser[Text] = """\"[^"]+\"""".r ^^ {
    case chars => Text(chars.substring(1, chars.length - 1))
  }

  // integer ::= 0|(\+|-)?[1-9][0-9]*
  def integer: Parser[Real] = """0|(\+|-)?[1-9][0-9]*""".r ^^ {
    case someInteger => Integer(someInteger.toInt)
  }

  // real ::= (\+|-)?[0-9]+\.[0-9]+
  def real: Parser[Real] = """(\+|-)?[0-9]+\.[0-9]+""".r ^^ {
    case someReal => Real(someReal.toDouble)
  }

  // boole ::= true | false
  def boole: Parser[Boole] = """true|false""".r ^^ {
    case someBoole => Boole(someBoole.toBoolean)
  }

  // identifier ::= [a-zA-Z][a-zA-Z0-9]*
  def identifier: Parser[Text] = """[a-zA-Z][a-zA-Z0-9]*""".r ^^ {
    case someIdentifier => Text(someIdentifier)
  }

  // funCall ::= identifier ~ operands
  def funCall = identifier ~ operands

  // operands ::= "(" ~ (expression ~ ("," ~ expression)*)? ~ ")"
  def operands = "(" ~ opt(expression ~ rep("," ~ expression)) ~ ")"

}
