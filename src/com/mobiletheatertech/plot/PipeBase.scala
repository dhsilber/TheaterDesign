package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
 * Created by dhs on 7/15/15.
 */
class PipeBase ( element: Element ) extends MinderDom( element ) {

  val x = getDoubleAttribute( "x" )
  val y = getDoubleAttribute( "y" )
  val z = getOptionalDoubleAttributeOrZero( "z" )

  val processedMark = Mark.Generate()
  element.setAttribute( "processedMark", processedMark )

  var drawPlace : Point = null


  def verify() : Unit = {
    if (Proscenium.Active()) {
      drawPlace = Proscenium.Locate( new Point( x, y, z ) )
    }
    else {
      drawPlace = new Point( x, y, z )
    }
  }

  def dom( draw: Draw, mode: View ): Unit = {
    mode match {
      case View.TRUSS =>
        return

      case View.PLAN =>
        val group = MinderDom.svgClassGroup( draw, "" )
        draw.appendRootChild( group )

        val circle = group.circle( draw, drawPlace.x(), drawPlace.y(), 18.0, "blue" )
        circle.attribute( "stroke-opacity", "0.5" )

      case default =>
        return
    }
  }

}

object PipeBase {

  def Find( mark : String ): PipeBase = {

    val list = ElementalLister.List()

    for( item <- 0 to list.size() - 1 ) {
      val thingy = list.get( item )
      if ( thingy.isInstanceOf[ PipeBase ]) {
        if ( thingy.asInstanceOf[ PipeBase ].processedMark == mark ) {
          return thingy.asInstanceOf[PipeBase]
        }
      }
    }


    // This didn't work at all:
//    for( thingy <- ElementalLister.List() ) {
    // ... It seems like Scala needs one of its own collection types for this syntax to work.


    return null
  }
}
