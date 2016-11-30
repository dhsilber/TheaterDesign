package com.mobiletheatertech.plot

import scala.collection.immutable._

/**
  * Created by dhs on 11/30/16.
  */
object Attachment {

  final val Tag = "attachment"

  var GripperMap: Map[ String, Gripper ] = _
  Reset()

  def Add( item: Gripper ): Unit = {
    GripperMap += (item.id -> item)
  }

  def Find( ident: String ): Gripper = {
    GripperMap( ident )
  }

  def Reset(): Unit = {
    GripperMap = new TreeMap[ String, Gripper ]()
  }

}
