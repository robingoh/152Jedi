package value

import context.TypeException

import scala.collection.mutable.ArrayBuffer

/**
  * Created by robingoh on 10/25/17.
  */

// backed by mutable buffer


class Store(private var elems:ArrayBuffer[Value] = ArrayBuffer[Value]()) extends Value {
  //private var elems = ArrayBuffer[Value]()
  // adds e to the end of store
  def add(e: Value) {
    elems += e
  }
  // inserts e at position pos in this
  def put(element: Value, position: Integer) {
    elems.insert(position.value, element)
  }
  // removes element at position pos from this
  def rem(position: Integer) {
    elems.remove(position.value)
  }
  // returns element at position pos in this
  def get(position: Integer): Value = elems(position.value)

  // returns true ie this contains e
  def contains(element: Value): Boole = Boole(elems.contains(element))

  // returns the size of this
  def size: Integer = Integer(elems.size)

  // returns "{e0 e1 e2 ...}"
  override def toString = "{" + elems.map((v: Value)=>print(s" $v")) + "}"

  // map and filter return new Store object
  // but not modifying this object
  // returns container containing the elements of this transformed by trans
  def map(transform: Closure): Store =
    new Store(elems.map((v: Value)=> transform(List(v))))

  // returns container containing the elements of this that passed test
  def filter(test: Closure): Store = {
    if (!test(List()).isInstanceOf[Boole])
      throw new TypeException
    new Store(elems.filter((v: Value)=>test.apply(List(v)).asInstanceOf[Boole].value))
  }
}


/* In class study notes
def abc {???} procedure when it's void
def abc = {???} multi-expression
def abc = ??? single
def abc: Unit = {???}
 */