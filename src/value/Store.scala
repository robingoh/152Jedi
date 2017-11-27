package value

import scala.collection.mutable.ArrayBuffer

/**
  * Created by robingoh on 10/25/17.
  */

// backed by mutable buffer


class Store() extends Value {
  private var elems = ArrayBuffer[Value]()
  // adds e to the end of store
  def add(e: Value) {
    elems += e
  }
  // inserts e at position pos in this
  def put(element: Value, position: Integer) {???}
  // removes element at position pos from this
  def rem(position: Integer) {???}
  // returns element at position pos in this
  def get(position: Integer): Value = ???
  // returns true ie this contains e
  def contains(element: Value): Boole = ???
  // returns the size of this
  def size: Integer = ???
  // returns "{e0 e1 e2 ...}"
  override def toString = {???}

  // map and filter return new Store object
  // but not modifying this object
  // returns container containing the elements of this transformed by trans
  def map(transform: Closure): Store = {???}
  // returns container containing the elements of this that passed test
  def filter(test: Closure): Store = {???}
}