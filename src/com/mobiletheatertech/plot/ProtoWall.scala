package com.mobiletheatertech.plot

import java.util

import org.w3c.dom.Element

/**
  * Created by DHS on 8/6/16.
  */
class ProtoWall( element: Element ) extends MinderDom( element: Element )
  with Populate
{
  val openingList = new util.TreeSet[ Opening ]( new OpeningComparator )

  val x1: Double = getDoubleAttribute( "x1" )
  val y1: Double = getDoubleAttribute( "y1" )
  val x2: Double = getDoubleAttribute( "x2" )
  val y2: Double = getDoubleAttribute( "y2" )

  var start = new Point( x1, y1, 0.0 )
  var end = new Point( x2, y2, 0.0 )

  tagCallback( Opening.Tag, processOpening )
  populate( element )

  def processOpening( element: Element ): Unit = {
    openingList.add( new Opening( element ) )
  }

  override def dom(draw: Draw, mode: View): Unit = {
    if( mode == View.SECTION ) return

    val wallStart = start
    val wallEnd = end
    val wallSegment = new Line( wallStart, wallEnd )

    var segmentStart = wallStart
    val iterator = openingList.iterator()
    while ( iterator.hasNext ) {
      val opening = iterator.next()

      val segmentEnd = wallSegment.pointAtDistanceFromP1( opening.start( ) )

      drawLine( draw, segmentStart, segmentEnd )

      segmentStart = wallSegment.pointAtDistanceFromP1( opening.start() + opening.width() )
    }
    drawLine( draw, segmentStart, wallEnd )
  }

  @throws[FeatureException]
  @throws[ReferenceException]
  override def verify(): Unit = {}

  def drawLine( draw: Draw, segmentStart: Point, segmentEnd: Point ): Unit = ???
}

object ProtoWall {}
