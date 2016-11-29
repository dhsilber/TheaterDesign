package com.mobiletheatertech.plot

/**
  * Created by DHS on 9/25/16.
  */
class InstanceId( identifier: String ) {
  val string = identifier

  def id: String = {
    if ( null == string || "" == string ) "instance"
    else s"($string)"
  }

}
