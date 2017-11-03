package test

import expression._
import value._

import scala.collection.mutable

/**
  * Created by robingoh on 10/25/17.
  */
class Environment extends mutable.HashMap[Identifier, Value]
