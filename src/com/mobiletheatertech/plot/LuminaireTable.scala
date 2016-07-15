package com.mobiletheatertech.plot

import org.w3c.dom.Text

/**
 * Created by dhs on 2/5/16.
 */
object LuminaireTable {
  var entries: List[String] = List()

  def add ( entry: String ): Unit = {
    entries = entries.::( entry )
  }

  def clear : Unit = {
    entries = List()
  }

  def dom( draw: Draw, mode: View ): Unit = {
    val list = entries.reverse

    var y = 200

    for( line <- list ) {
      val textNode: Text = draw.document.createTextNode( line )
      val element: SvgElement = draw.element("text")
      element.attribute("x", "12" )
      element.attribute("y", y.toString)
      element.appendChild(textNode)
      draw.appendChild(element.element)

      y += 17
    }
  }
}


