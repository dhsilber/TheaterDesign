package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
 * Created by dhs on 12/8/15.
 */
class DrapeBase ( element: Element ) extends MinderDom( element ) {

  val WidthHalf = 9.0
  val DepthHalf = 9.0

  val x = getDoubleAttribute( "x" )
  val y = getDoubleAttribute( "y" )
  val z = getOptionalDoubleAttributeOrZero( "z" )

  var drawPlace : Point = null


  def verify(): Unit = {
    drawPlace = Proscenium.LocateIfActive( new Point( x, y, z ) )
  }

  def dom( draw: Draw, mode: View ): Unit = {
    mode match {
      case View.TRUSS =>
        return

      case View.PLAN =>
        val group = MinderDom.svgClassGroup( draw, "" )
        draw.appendRootChild( group )

//        val circle = group.circle( draw, drawPlace.x(), drawPlace.y(), 18.0, "blue" )
//        circle.attribute( "stroke-opacity", "0.5" )
        group.line( draw,
          drawPlace.x() - WidthHalf, drawPlace.y() - DepthHalf,
          drawPlace.x() + WidthHalf, drawPlace.y() - DepthHalf, "blue" )
        group.line( draw,
          drawPlace.x() - WidthHalf, drawPlace.y() - DepthHalf,
          drawPlace.x() - WidthHalf, drawPlace.y() + DepthHalf, "blue" )
        group.line( draw,
          drawPlace.x() + WidthHalf, drawPlace.y() - DepthHalf,
          drawPlace.x() + WidthHalf, drawPlace.y() + DepthHalf, "blue" )
        group.line( draw,
          drawPlace.x() - WidthHalf, drawPlace.y() + DepthHalf,
          drawPlace.x() + WidthHalf, drawPlace.y() + DepthHalf, "blue" )

      case default =>
        return
    }
  }

}

object DrapeBase {

  def Find(mark: String): DrapeBase = {
    //
    //    val list = ElementalLister.List()
    //
    //    for( item <- 0 to list.size() - 1 ) {
    //      val thingy = list.get( item )
    //      if ( thingy.isInstanceOf[ PipeBase ]) {
    //        if ( thingy.asInstanceOf[ PipeBase ].processedMark == mark ) {
    //          return thingy.asInstanceOf[PipeBase]
    //        }
    //      }
    //    }
    //
    //
    //    // This didn't work at all:
    //    //    for( thingy <- ElementalLister.List() ) {
    //    // ... It seems like Scala needs one of its own collection types for this syntax to work.
    //

    return null
  }
}