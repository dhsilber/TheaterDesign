package com.mobiletheatertech.plot

import org.w3c.dom.Element

/**
  * Created by DHS on 8/6/16.
  */
class Flat( element: Element, parent: SetPiece ) extends ProtoWall( element: Element )
{
  def this( element: Element ) {
    this( element, null )
  }

  if( null != parent ) {
    start = new Point(
      start.x + parent.origin.x(),
      start.y + parent.origin.y(),
      start.z
    )
    end = new Point(
      end.x + parent.origin.x(),
      end.y + parent.origin.y(),
      end.z
    )
  }
  start = Proscenium.LocateIfActive( start )
  end = Proscenium.LocateIfActive( end )

//  override def dom(draw: Draw, mode: View): Unit = ???

  override def verify(): Unit = {}

  override def drawLine( draw: Draw, segmentStart: Point, segmentEnd: Point ): Unit = {
    val lineElement = draw.line( draw,
      segmentStart.x, segmentStart.y, segmentEnd.x, segmentEnd.y, Flat.Color )
    lineElement.attribute( "stroke-width", "3" )
    if( null != parent ) {
      if (0 != parent.rotation) {
        lineElement.rotate(parent.rotation,
          Proscenium.Origin.x + parent.origin.x,
          Proscenium.Origin.y - parent.origin.y)
      }
    }
  }

}

//Next step is to clean up tests & code from this work and push to github.

object Flat {
  final val Tag = "flat"
  final val Color = "green"
}
