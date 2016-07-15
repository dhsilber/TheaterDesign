package com.mobiletheatertech.plot

/**
 * Created by dhs on 2/3/16.
 */
class Distance ( inchInternal: Double ) {

  def inch(): Double = {
    inchInternal
  }

  def foot(): Double = {
    inchInternal / 12
  }

  override def toString(): String = {
    val feet: Integer = inchInternal.toInt / 12
    var inch: Integer = inchInternal.toInt % 12

    inch = Math.abs( inch )

    feet.toString() + "\'" + inch.toString() + "\""
  }
}
