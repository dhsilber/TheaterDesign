package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by dhs on 11/29/16.
  */
class Halfborough( element: Element ) extends MinderDom( element )
  with Gripper
{

  id = getStringAttribute( "id" )
  val on = getStringAttribute( "on" )
  val location = getStringAttribute( "location" )

  Attachment.Add( this )

  // Members declared in com.mobiletheatertech.plot.MinderDom
  def dom(x$1: com.mobiletheatertech.plot.Draw,x$2: com.mobiletheatertech.plot.View): Unit = {}

  // Members declared in com.mobiletheatertech.plot.Verifier
  def verify(): Unit = {}

}

object Halfborough {
  final val Tag = "halfborough"

  def Reset(): Unit = {

  }
}


