package com.mobiletheatertech.plot

import scala.collection.mutable
import scala.collection.immutable

trait KnowsChildren {

  val children: mutable.Set[Pipe] = mutable.Set()

  def registerChild(pipe: Pipe): Unit = {
    children += pipe
  }

  def listChildren(): scala.collection.immutable.Set[Pipe] = {
    val result = immutable.Set[Pipe]().empty ++ children
    result
  }
}
