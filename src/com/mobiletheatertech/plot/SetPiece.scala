package com.mobiletheatertech.plot

import org.w3c.dom.Element

class SetPiece( element: Element )
  extends MinderDom( element: Element )
  with Populate
{
//  println( "SetPiece construction.")

//  case class PointElements( point: Point, list: Tuple2( x: Double, y: Double ) )

  var children: Boolean = false

  var origin: Point =  {
    val x = getDoubleAttribute( "x" )
    val y = getDoubleAttribute( "y" )
    new Point( x, y, 0.0 )
  }
//  var location =
  val rotation = getOptionalDoubleAttributeOrZero( "rotation" )

  tagCallback( Flat.Tag, processFlat )
  tagCallback( SetPlatform.Tag, processSetPlatform )

  populate( element )

  if (! children) throw new InvalidXMLException(
    "SetPiece at (" + origin.x + ", " + origin.y + ") must have content.")

  def processFlat( element: Element ): Unit = {
//    println( "Set")
    new Flat( element, this )
    children = true
  }

  def processSetPlatform( element: Element ): Unit = {
    new SetPlatform( element, this )
    children = true
  }

  /**
    * Hook to allow each {@code MinderDom}-derived instance to update the DOM for the generated SVG.
    * <p/>
    * Items that make use of this functionality will replace this comment with specifics.
    *
    * @param draw canvas/DOM manager
    * @param mode drawing mode
    * @throws MountingException if mounting location cannot be established
    */
  override def dom(draw: Draw, mode: View): Unit = {

//    val group: SvgElement = draw.group(draw, SetPiece.Tag )
//    group.element.setAttribute( "stroke", "red" )

  }

  /**
    * Hook to allow each {@code Verifier}-derived instance to perform sanity checks after all XML has
    * been parsed.
    * <p/>
    * Items that make use of this functionality will replace this comment with specifics.
    *
    * @throws InvalidXMLException if an invalid combination of XML specifications is found
    * @throws LocationException   if certain plot items don't fit in available physical space
    */
  override def verify(): Unit = {}
}

object
SetPiece {
  val Tag = "setpiece"
}
