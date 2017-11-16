package context

import scala.io._
/*
 * Notes:
 * console is Jedi's user interface
 * parsers and global environment are created here
 * console.main launches repl or executes a Jedi file
 */
object console {
  val parsers = new Jedi1Parsers // for now
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
        println("-> " + line)
        println(execute(line))
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
        case e: UndefinedException => {
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
        case e: TypeException => {
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

// jedi1tests.txt console output
/*
-> def sum = add(mul(3, 4), sub(9, 1))
OK
-> sum
20
-> 3 * 4 + 9
21
-> 3 * (4 + 9)
39
-> add(3, 4.0)
7.0
-> sub(3, 4)
-1
-> sub(3, -4)
7
-> 3 - -4
7
-> def pi = 3.14
OK
-> def e = 2.7
OK
-> pi * e + sum
28.478
-> 3 < 4
true
-> 3 == 4
false
-> 3 != 4
true
-> 3 + (if(sum < 19) 8 else 3)
6
-> if (sum < 19) nada else 3
3
-> 3 < 4 && 1 + 1 == 2 && sum < 19 && nada
false
-> 4 < 3 || 1 + 1 != 2 || sum < 19 ||  true || nada
true
bye

Process finished with exit code 0
 */