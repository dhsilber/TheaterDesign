package com.mobiletheatertech.plot

import scala.runtime.Nothing$

/**
  * Created by dhs on 12/2/16.
  */
class Location( location: String ) {
  private var isValid = true
  private var vertexProvided = false
  private var distanceProvided = false
  private val internalVertex: Char = letterAtStart
//  val distance: Option[Double] = locationDistancefoo(location)
//  val distance: Option[Double] = locationDistancefoo(location)
  private var internalDistance: Double = locationDistance( location )

  private def locationDistance( location: String ): Double = {
    val distanceString = if ( vertexProvided ) location.substring(1) else location

    val workingString = distanceString.trim
    if ( ! workingString.isEmpty ) {
      distanceProvided = true
      internalDistance = workingString.toDouble
    }
    internalDistance
  }

  def distance: ValidatedDouble = {
    new ValidatedDouble( distanceProvided, internalDistance )
  }

  private def letterAtStart: Char = {
    val letter = location.charAt(0)
//    var letter: Char = ' '
//    letter = location.charAt(0)
    if (letter.isLetter) vertexProvided = true
    letter
  }

  def vertex: ValidatedChar = {
    new ValidatedChar( vertexProvided, internalVertex )
  }

  def compare( that: Location ): Int = {
    val here: Int = (this.internalDistance * 100).toInt
    val there: Int = (that.internalDistance * 100).toInt
    here - there
  }

  def feet(): String = {
    val feet = internalDistance / 12
    val inches = internalDistance % 12

    return feet.toInt.toString + "'" + inches.toInt.abs.toString + "\""
  }

  override def toString: String = {
    var result = ""
    if ( vertexProvided ) {
      result += internalVertex.toString
    }
    if ( vertexProvided && distanceProvided) {
      result += " "
    }
    if ( distanceProvided ) {
      result += internalDistance.toString
    }
    result
  }

}
