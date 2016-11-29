package com.mobiletheatertech.plot

import org.w3c.dom.{Element, Node, NodeList}

/**
  * Created by DHS on 7/29/16.
  */
trait Populate {

  val tags = collection.mutable.Map[ String, Element => Unit ]()

  def populate( element: Element ): Unit = {
    val nodes: NodeList = element.getChildNodes
    val count = nodes.getLength()

    for ( index <- 0 until count ) {
      val childNode = nodes.item( index )
      if( (null != childNode) && (childNode.getNodeType == Node.ELEMENT_NODE) ) {
        val childElement: Element = nodes.item( index ).asInstanceOf[Element]
        val tag = childElement.getTagName()
        if(tags.contains(tag)) {
          tags(tag)(childElement)
        }
      }
    }
  }

  def tagCallback( tag: String, callback: Element => Unit ): Unit = {
    tags( tag ) = callback
  }
}
