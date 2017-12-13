package context

import value._
import scala.io._
/*
 * Notes:
 * console is Jedi's user interface
 * parsers and global environment are created here
 * console.main launches repl or executes a Jedi file
 */
object console {
  val parsers = new Jedi3Parsers // for now
  val globalEnv = new Environment
  var verbose = false

  def execute(cmmd: String): String = {
    val tree = parsers.parseAll(parsers.expression, cmmd)
    tree match {
      case tree: parsers.Failure => throw new SyntaxException(tree)
      case _ => {
        val exp = tree.get  // get the expression from the tree
        //println("translation: " + exp.emit)
        val result = exp.execute(globalEnv)  // execute the expression
        result.toString  // return string representation of result
      }
    }
  }

  private def executeFile(fileName: String) {

    var more = true

    for (line <- Source.fromFile(fileName).getLines if more) {
      try {
        if (!line.matches("""\s*""")) {
          println("-> " + line)
          println(execute(line))
        }
      } catch {

        case e: SyntaxException => {
          println(e)
          println(e.result.msg)
          println("line # = " + e.result.next.pos.line)
          println("column # = " + e.result.next.pos.column)
          println("token = " + e.result.next.first)
        }
        case e: UndefinedException => {
          println(e)
          if (verbose) e.printStackTrace()
        }
        case e: TypeException => {
          println(e)
          if (verbose) e.printStackTrace()
        }
        case e: JediException => {
          println(e)
          if (verbose) e.printStackTrace()
        }

        case e: Exception => {
          println(e)
          more = false
        }
      } // catch
    } // for
    println("bye")
  }



  // read-execute-print loop
  def repl {
    var more = true
    var cmmd = ""
    while(more) {
      try {
        print("-> ")
        cmmd = StdIn.readLine
        if (cmmd == "quit") more = false
        else println(execute(cmmd))
      }
      catch {
        case e: SyntaxException => {
          println(e)
          println(e.result.msg)
          println("line # = " + e.result.next.pos.line)
          println("column # = " + e.result.next.pos.column)
          println("token = " + e.result.next.first)
        }
        case e: UndefinedException => {
          println(e.gripe)
          if (verbose) e.printStackTrace()
        }
        case e: UndefinedException => {
          println(e.gripe)
          if (verbose) e.printStackTrace()
        }
        case e: JediException => {
          println(e.gripe)
          if (verbose) e.printStackTrace()
        }
        case e: Exception => {
          println(e.getMessage)
          more = false
        }
      } finally {
        Console.flush
      }
    }
    println("bye")
  }

  def main(args: Array[String]): Unit = {
    if (args.length == 0)
      repl
    else
      try {
        executeFile(args(0))
      } catch  {
        case e: Exception => {
          println(e)
        }
      }
  }
}

// test output with Jedi3Parsers
/*
-> def count = var(0)
OK
-> count
Variable(0)
-> [count]
Variable(0)
-> def incCount = lambda() count = [count] + 1
OK
-> incCount()
context.UndefinedException: Undefined identifier: count
-> incCount()
context.UndefinedException: Undefined identifier: count
-> count
Variable(0)
-> while ([count] < 10) { write("calling incCount"); incCount() }
context.TypeException: Inputs to < must be numbers or texts
-> count
Variable(0)
-> def tri = lambda (n) { def result = var(0); def count = var(0); while([count] < n + 1) { result = [result] + [count]; count = [count] + 1}; [result]}
OK
-> tri(5)
context.TypeException: Inputs to < must be numbers or texts
-> tri(6)
context.TypeException: Inputs to < must be numbers or texts
-> def scores = store(85, 23, 99, 56)
OK
-> scores
 85 23 99 56{ArrayBuffer((), (), (), ())}
-> get(1, scores)
DONE
-> put(60, 2, scores)
DONE
-> scores
 85 23 60 99 56{ArrayBuffer((), (), (), (), ())}
-> def scores2 = map(lambda(x) x + 1, scores)
OK
-> scores2
DONE
-> def scores3 = filter(lambda(x) 70 < x, scores2)
context.TypeException: expected signature: filter(f: Closure, s: Store)
-> scores3
java.lang.NullPointerException
bye
 */

// test output with Jedi4Parsers
/*
-> def acct1 = object{def bal = var(100.0); def deposit = lambda(amt) bal = [bal] + amt}
context.SyntaxException: Syntax error
string matching regex `\z' expected but `{' found
line # = 1
column # = 19
token = {
-> acct1.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> acct1.deposit(67.0)
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> acct1.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> def acct2 = object {def withdraw = lambda(amt) bal = [bal] - amt} extends acct1
context.SyntaxException: Syntax error
string matching regex `\z' expected but `{' found
line # = 1
column # = 20
token = {
-> acct2.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> acct2.withdraw(19)
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> acct2.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 6
token = .
-> def makeAccount = lambda(initAmount) object{def bal = var(initAmount); def deposit = lambda(amt) bal = [bal] + amt; def withdraw = lambda(amt) bal = [bal] - amt}
context.SyntaxException: Syntax error
string matching regex `\z' expected but `{' found
line # = 1
column # = 44
token = {
-> def savings = makeAccount(50.2)
context.UndefinedException: Undefined identifier: makeAccount
-> def checking = makeAccount(93.2)
context.UndefinedException: Undefined identifier: makeAccount
-> savings.withdraw(25)
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 8
token = .
-> checking.deposit(25)
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 9
token = .
-> checking.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 9
token = .
-> savings.bal
context.SyntaxException: Syntax error
string matching regex `\z' expected but `.' found
line # = 1
column # = 8
token = .
bye
 */