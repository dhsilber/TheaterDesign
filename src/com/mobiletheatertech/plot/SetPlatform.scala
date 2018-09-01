package com.mobiletheatertech.plot

import org.w3c.dom.Element
import java.util.ArrayList

import scala.collection.JavaConversions._

/**
  * Created by DHS on 8/11/16.
  */

class SetPlatform (val element: Element) extends MinderDom(element)
  with Populate
  with Legendable
{
  val x = getDoubleAttribute("x")
  val y = getDoubleAttribute("y")
  val orientation = getOptionalDoubleAttributeOrZero("orientation")

  val shapes: ArrayList[ Shape ] = new ArrayList[ Shape ]

  tagCallback( Shape.Tag, processShape )
  populate( element )

  if ( shapes.size() < 1 )
    throw new InvalidXMLException( "SetPlatform has no Shape." )

  def processShape( element: Element ): Unit = {
    shapes.add( new Shape( element ) )
  }

  def verify()
  {
  }

  def dom(draw: Draw, mode: View)
  {
    if( mode == View.SECTION ) return

    val group: SvgElement = draw.group(draw, SetPlatform.Tag )
    group.element.setAttribute( "stroke", SetPlatform.Color )

    for ( thing <- shapes) {
      val element = thing.toSvg( group, draw, x, y )
      element.attribute( "transform", "rotate(" + orientation + "," + x + "," + y + ")" )
      element.attribute( "stroke-width", "1" )
      element.attribute("fill", SetPlatform.Color )
      element.attribute("fill-opacity", "0.1")
    }
  }

  def legendCountReset()
  {
  }

  def domLegendItem(draw: Draw, start: PagePoint): PagePoint =
    null

}

object SetPlatform {
  val Tag = "set-platform"
  val Color = "green"
}
