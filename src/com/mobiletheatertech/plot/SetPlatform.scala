package com.mobiletheatertech.plot

import org.w3c.dom.Element
import java.util.ArrayList

import scala.collection.JavaConversions._

/**
  * Created by DHS on 8/11/16.
  */

class SetPlatform (val element: Element, parent: SetPiece ) extends MinderDom(element)
  with Populate
  with Legendable
{
  def this( element: Element ) {
    this( element, null )
  }

  var x: Double = 0.0
  var y: Double = 0.0
  if( null != parent ) {
    x = parent.origin.x
    y = parent.origin.y()
  }

  x += getDoubleAttribute("x")
  y += getDoubleAttribute("y")

  val orientation = getOptionalDoubleAttributeOrZero("orientation")

  val shapes: ArrayList[ Shape ] = new ArrayList[ Shape ]

  tagCallback( Shape.Tag, processShape )
  populate( element )

  if ( shapes.size() < 1 )
    throw new InvalidXMLException(
      "SetPlatform at (" + x + ", " + y + ") has no Shape." )

  println( this.toString )

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

  override def toString: String = {
    var result: String = "SetPlatform at (" + x + ", " + y +
      ") has " + shapes.size() + " Shapes."
    if (null != parent) {
      result += "\n  Parent: " + parent.toString
    }

    result
  }

}

object SetPlatform {
  val Tag = "set-platform"
  val Color = "green"
}
