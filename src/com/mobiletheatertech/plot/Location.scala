package com.mobiletheatertech.plot

/**
  * Created by dhs on 12/2/16.
  */
class Location( location: String ) {
  var isValid = true
  var vertexProvided = false
  var distanceProvided = false
  val vertex: Char = letterAtStart
  val distance: Double = locationDistance(location)

  def letterAtStart: Char = {
    var letter: Char = ' '
    letter = location.charAt(0)
    if (! letter.isDigit) vertexProvided = true
    letter
  }

  def locationDistance( location: String ): Double = {
    val distanceString = if ( vertexProvided ) location.substring(1) else location

    var result: Double = 0.0
    val workingString = distanceString.trim
    if ( ! workingString.isEmpty ) {
      distanceProvided = true
      result = workingString.toDouble
    }
    result
  }

  override def toString: String = {
    var result = ""
    if ( vertexProvided ) {
      result += vertex.toString
    }
    if ( vertexProvided && distanceProvided) {
      result += " "
    }
    if ( distanceProvided ) {
      result += distance.toString
    }
    result
  }

}
