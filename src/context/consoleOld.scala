package context

import scala.io._
/*
 * Notes:
 * console is Jedi's user interface
 * parsers and global environment are created here
 * console.main launches repl or executes a Jedi file
 */
object consoleOld {
  val parsers = new Jedi2Parsers
  val globalEnv = new Environment
  var verbose = false

  def execute(cmmd: String): String = {
    val tree = parsers.parseAll(parsers.expression, cmmd)
    tree match {
      case tree: parsers.Failure => throw new SyntaxException(tree)
      case _ =>
        val exp = tree.get  // get the expression from the tree
        //println("translation: " + exp.emit)
        val result = exp.execute(globalEnv)  // execute the expression
        result.toString  // return string representation of result
    }
  }

  private def executeFile(fileName: String) {

    var more = true

    for (line <- Source.fromFile(fileName).getLines if more) {
      try {
        println("-> " + line)
        println(execute(line))
      } catch {
        case e: SyntaxException =>
          println(e)
          println(e.result.msg)
          println("line # = " + e.result.next.pos.line)
          println("column # = " + e.result.next.pos.column)
          println("token = " + e.result.next.first)
        case e: UndefinedException =>
          println(e)
          if (verbose) e.printStackTrace()
        case e: TypeException =>
          println(e)
          if (verbose) e.printStackTrace()
        case e: JediException =>
          println(e)
          if (verbose) e.printStackTrace()
        case e: Exception =>
          println(e)
          more = false
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
        case e: SyntaxException =>
          println(e)
          println(e.result.msg)
          println("line # = " + e.result.next.pos.line)
          println("column # = " + e.result.next.pos.column)
          println("token = " + e.result.next.first)
        case e: UndefinedException =>
          println(e.gripe)
          if (verbose) e.printStackTrace()
        case e: TypeException =>
          println(e.gripe)
          if (verbose) e.printStackTrace()
        case e: JediException =>
          println(e.gripe)
          if (verbose) e.printStackTrace()
        case e: Exception =>
          println(e.getMessage)
          more = false
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
        case e: Exception =>
          println(e)
      }
  }
}

// my jedi2tests.txt console output
/*
-> def pi = 3.14
OK
-> def pi2 = {def x = 2; 2 * pi}
OK
-> write(pi2)
6.28
DONE
-> def fun1 = lambda(x, y) 3 * x + 2 * y
OK
-> fun1(5, 7)
29
-> def tri = lambda(n) if (n == 0) 0 else n + tri(n - 1)
OK
-> tri(5)
15
-> tri(6)
21
-> def square = lambda(x) x * x
OK
-> def addx = lambda(x) lambda(y) x + y
OK
-> def add5 = addx(5)
OK
-> add5(9)
14
-> def compose = lambda(f, g) lambda(z) f(g(z))
OK
-> def add5square = compose(square, add5)
OK
-> add5square(3)
64
-> def fun2 = {def x = 10; lambda(y) x * y}
OK
-> fun2(5.5)
55.0
-> def fun3 = lambda (y) { def x = 10; x * y}
OK
-> fun3(5.5)
55.0
bye

Process finished with exit code 0
 */