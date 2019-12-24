package com.mobiletheatertech.plot

import java.awt.Rectangle
import java.util

import org.w3c.dom.Element
import java.util.ArrayList

/**
  * Created by DHS on 9/24/16.
  */
class Proscenium( element: Element ) extends MinderDom( element )
  with Populate
{

  val width = getDoubleAttribute( "width" )
  val depth = getDoubleAttribute( "depth" )
  val height = getDoubleAttribute( "height" )
  val x = getDoubleAttribute( "x" )
  val y = getDoubleAttribute( "y" )
  val z = getDoubleAttribute( "z" )

  if (0 >= width) throw new SizeException("Proscenium", "width")
  if (0 >= depth) throw new SizeException("Proscenium", "depth")
  if (0 >= height) throw new SizeException("Proscenium", "height")


  if (!Venue.Contains2D(new Rectangle(
    x.intValue() - width.intValue() / 2, y.intValue(),
    width.intValue(), depth.intValue()))) {
    throw new LocationException(
      "Proscenium should not extend beyond the boundaries of the venue.")
  }
  if (0 > z) {
    throw new LocationException(
      "Proscenium should not extend beyond the boundaries of the venue.")
  }
  if (z + height > Venue.Height ) {
    throw new LocationException(
      "Proscenium should not extend beyond the boundaries of the venue.")
  }

  Proscenium.origin = new Point( x, y, z )
  println( "Proscenium origin... " + Proscenium.origin.toString )

  Proscenium.active = true

  new Point( x, y, - z )

  /*
  Thinking out loud here...

  Proscenium arches often have moldings around them. Most commonly, they are
  decorations on the DS side. but I know of at least one stage (Corey Auditorium)
  where this molding extends US as well. (Actually, in that case it is a slab of
  wood applied to the inside of the arch which extends both US & DS of the
  proscenium, but I'm not planning on yet another way to implement that.)

  This molding could be anything from a modern squared-off thing to a more ornate
  sloped and patterned affair.

  I think the best way to represent this would be with an element that is a child
  of the proscenium that can describe the cross-section of the molding and where
  it goes.

   */

  val mouldings: ArrayList[ Moulding ] = new ArrayList[ Moulding ]

  tagCallback( Moulding.Tag, processMoulding )
  populate( element )

  def processMoulding( element: Element ): Unit = {
    mouldings.add( new Moulding( element ) )
  }

  def dom( draw: Draw, mode: View ): Unit = {
    mode match {
      case View.PLAN =>
        domPlan (draw)
      case View.SECTION =>
        domSection (draw)
      case default =>
    }
  }

  private def domPlan(draw: Draw)
  {
    val startX: Double = x - width / 2
    val startY: Double = y
    val endX: Double = x + width / 2
    val endY: Double = y + depth
    // SR end of proscenium arch
    draw.line(draw, startX, startY, startX, endY, Proscenium.Color)
    // SL end of proscenium arch
    draw.line(draw, endX, startY, endX, endY, Proscenium.Color)
    // US side of proscenium arch
    var line: SvgElement = draw.line(draw, startX, startY, endX, startY, Proscenium.FadedColor)
    line.attribute("stroke-opacity", "0.3")
    // DS side of proscenium arch
    line = draw.line(draw, startX, endY, endX, endY, Proscenium.FadedColor)
    line.attribute("stroke-opacity", "0.1")
  }

  @throws[ ReferenceException ]
  private[ plot ] def domSection(draw: Draw)
  {
    val zee: Double = Venue.Height
    val prosceniumWall: SvgElement = draw.line(draw, y, zee, y, zee - Venue.Height, Proscenium.FadedColor)
    val front: Double = y + depth
    val frontWall: SvgElement = draw.line(draw, front, zee, front, zee - Venue.Height, Proscenium.FadedColor)
    val top: Double = zee - height
    val archTop: SvgElement = draw.line(draw, y, top, front, top, Proscenium.FadedColor)
    val stage: SvgElement = draw.line(draw, 0.0, zee, y, zee, Proscenium.StageColor)
    stage.attribute("stroke-width", "2")
  }

  override def verify(): Unit = {}
}

object Proscenium {
  final val Tag = "proscenium"
  final val Color = "black"
  final val FadedColor = "gray"
  final val StageColor = "black"
  var active = false
  var origin = new Point( 0.0, 0.0, 0.0 )

  def Origin: Point = {
    origin
  }

  def Active: Boolean = {
    active
  }

  def LocateIfActive( unfixed: Point ): Point = {
    if ( active ) new Point(
      origin.x + unfixed.x,
      origin.y - unfixed.y,
      origin.z + unfixed.z
    )
    else {
      unfixed
    }
  }

  def LocateIfActivePathString( ex: Double, wy: Double ): String = {
    var localX = ex
    var localY = wy

    if ( Active ) {
      localX = origin.x() + ex
      localY = origin.y() - wy
    }

    s"$localX $localY "
  }

  def Reset(): Unit = {
    active = false
    origin = new Point( 0.0, 0.0, 0.0 )
  }
}
