package com.mobiletheatertech.plot

import org.w3c.dom.Element

import scala.collection.mutable.ArrayBuffer

/**
  * Created by DHS on 7/27/16.
  */
abstract class UniqueId( element: Element ) extends MinderDom( element: Element ) {
  id = getStringAttribute( "id" )

  if( UniqueId.Check(id) ) {
    println("For " + this.getClass.getSimpleName )
    UniqueId.Display()
    throw new InvalidXMLException(
      this.getClass.getSimpleName + " id '" + id + "' is not unique.")
  }

  UniqueId.UniqueList += id
}

object UniqueId {
  val UniqueList = new ArrayBuffer[ String ]

  def Display(): Unit ={
    println(UniqueList)
  }

  def Check (id: String): Boolean = {
    UniqueList.contains( id )
  }

  def Reset(): Unit = {
    UniqueList.clear()
  }
}
