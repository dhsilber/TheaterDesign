package com.mobiletheatertech.plot

//import scala.util.Sorting

object LocationOrdering extends Ordering[Luminaire] {

  def compare(a: Luminaire, b: Luminaire) = b.location() compare a.location()
}
