//package com.mobiletheatertech.plot
//
//import org.w3c.dom.Element
//
///**
//  * Created by DHS on 8/6/16.
//  */
//class Flat( element: Element ) extends ProtoWall( element: Element ) {
//
//  start = Proscenium.LocateIfActive( start )
//  end = Proscenium.LocateIfActive( end )
//
////  override def dom(draw: Draw, mode: View): Unit = ???
//
//  override def verify(): Unit = {}
//
//  override def drawLine( draw: Draw, segmentStart: Point, segmentEnd: Point ): Unit = {
//    val lineElement = draw.line( draw,
//      segmentStart.x, segmentStart.y, segmentEnd.x, segmentEnd.y, Flat.Color )
//    lineElement.attribute( "stroke-width", "2" )
//  }
//
//}
//
//object Flat {
//  final val Tag = "flat"
//  final val Color = "green"
//}
