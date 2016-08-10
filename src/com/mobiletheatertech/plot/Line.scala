package com.mobiletheatertech.plot

import java.awt.geom.Line2D

/**
  * Created by DHS on 8/7/16.
  */
class Line( val p1: Point, p2: Point )
{
//  val x1 = p1.x
//  val y1 = p1.y
//  val z1 = p1.z
//  val x2 = p2.x
//  val y2 = p2.y
//  val z2 = p2.z

  //  Whoops. This is redundant with Point's distance( Point ) method.
  def length(): Double = {

//    val width = x2 - x1
//    val depth = y2 - y1
//    val height = z2 - z1
//
//    Math.sqrt( width * width + depth * depth + height * height )

    p1.distance( p2 )
}

  // ... and this should be giving me the same results as Point.OnLine(Point,Point,Double),
  // (which was already implemented) but this is cleaner and easier to understand.Ljkjkjkjkjkj
  def pointAtDistanceFromP1( distance: Double ): Point = {

//    val u = distance / length()
//
//    val x = (1-u) * x1 + u * x2
//    val y = (1-u) * y1 + u * y2
//    val z = (1-u) * z1 + u * z2
//
//    new Point( x, y, z )

    Point.OnALine( p1, p2, distance )
  }
}
