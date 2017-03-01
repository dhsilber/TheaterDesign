package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by dhs on 2/16/17.
  */
class Raceway( element: Element ) extends MinderDom( element ) {
  val startx = getDoubleAttribute( "startx" )
  val starty = getDoubleAttribute( "starty" )
  val startz = getDoubleAttribute( "startz" )
  val endx = getDoubleAttribute( "endx" )
  val endy = getDoubleAttribute( "endy" )
  val endz = getDoubleAttribute( "endz" )
  val tailLength = getOptionalDoubleAttributeOrZero( "taillength" )

  println( "Creating raceway" )

  val startUnadjusted: Point = new Point( startx, starty, startz )
  val start = Proscenium.LocateIfActive( startUnadjusted )

  override def dom(draw: Draw, mode: View): Unit = {

    println( "Drawing Raceway")
    mode match {
      case View.PLAN =>
        val group = MinderDom.svgClassGroup( draw, Raceway.Tag )
        draw.appendRootChild(group)

        group.rectangle( draw,
          start.x, start.y, (endx - startx).abs, (endy - starty).abs,
          Raceway.Color )
      case _ =>
    }
  }

  override def verify(): Unit = {}
}

object Raceway {
  final val Tag = "raceway"

  final val Color = "black"
}