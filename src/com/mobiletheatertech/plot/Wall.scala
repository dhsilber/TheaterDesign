package com.mobiletheatertech.plot

import java.util

import org.w3c.dom.Element

/**
  * Created by DHS on 8/7/16.
  */
class Wall( element: Element ) extends ProtoWall( element: Element )
  with Populate
{
//  val openingList = new util.TreeSet[ Opening ]( new OpeningComparator )

  Wall.WallList.add( this )


//  override def dom( draw: Draw, mode: View ): Unit = {
//    val iterator = openingList.iterator()
//    val wallStart = new Point( x1, y1, 0.0 )
//    val wallEnd = new Point( x2, y2, 0.0 )
//    val wallSegment = new Line( wallStart, wallEnd )
//
//    var segmentStart = wallStart
//    while ( iterator.hasNext ) {
//      val opening = iterator.next()
//
//      val segmentEnd = wallSegment.pointAtDistanceFromP1( opening.start() )
//      drawLine( draw, segmentStart, segmentEnd )
//
//      segmentStart = wallSegment.pointAtDistanceFromP1( opening.start() + opening.width() )
//    }
//
//    drawLine( draw, segmentStart, wallEnd )
//  }

  override def drawLine( draw: Draw, segmentStart: Point, segmentEnd: Point ): Unit = {
    val lineElement = draw.line( draw,
      segmentStart.x, segmentStart.y, segmentEnd.x, segmentEnd.y, Wall.Color )
    lineElement.attribute( "stroke-width", "2" )
  }

  override def verify( ): Unit = {}

  def nearestPointNearWall( point: Point ): Point = {
    null
  }

  def next: Wall = {
    null
  }

  def previous: Wall = {
    null
  }

  def nextCorner( startingPoint: Point, destinationPoint: Point, destinationWall: Wall ): Point = {
null
  }

  def nextNear( startingPoint: Point, destinationPoint: Point, destinationWall: Wall ): Wall= {
    null
  }
}

object Wall
  {
    final val Tag = "wall"
//    final val OpeningTag = "opening"
    final val Color = "black"

    val WallList = new util.ArrayList[ Wall ]()

    def WallNearestPoint( point: Point ): Wall = {
      null
    }

    def Reset()={
      WallList.clear()
    }

}