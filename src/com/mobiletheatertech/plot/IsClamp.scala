package com.mobiletheatertech.plot

/**
  * Created by DHS on 7/28/16.
  */
trait IsClamp {
  def weight: Double = ???
  def position: Place = ???
  def position( location: Point ): Unit = ???
}
