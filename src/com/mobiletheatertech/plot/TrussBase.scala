package com.mobiletheatertech.plot

/**
  * Created by dhs on 11/29/16.
  */
import java.util

import org.w3c.dom.Element

/**
  * Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:05 PM To change this template use
  * File | Settings | File Templates.
  *
  * This is a square base for truss.
  *
  * @author dhs
  * @since 0.0.5
  */

class TrussBase (val element: Element) extends MinderDom(element)
  with Populate
{
  final val spacing = 3.0

  val size = getDoubleAttribute("size")
  val y = getDoubleAttribute("y")
  val x = getDoubleAttribute("x")
  val z = getOptionalDoubleAttributeOrZero( "z" )
  val rotation = getOptionalDoubleAttributeOrZero("rotation")
  var truss: Truss = null


  // Give the base's element a unique id so that its Plot object can be recognized
  // and used.
  val processedMark = Mark.Generate()
  element.setAttribute( "processedMark", processedMark )

  var drawPlace : Point = null

  tagCallback( Truss.LayerTag, processTruss )
  populate( element )


  def processTruss( element: Element ): Unit = {
    println( "TrussBase creates child Truss")
    truss = new Truss( element, this )
  }

  def verify() {
    drawPlace = Proscenium.LocateIfActive( new Point( x, y, z ) )
  }

  def mountPoints(): Array[Point] = {
    val foo = new Array[Point](4)
    foo( 0 ) = new Point( x - spacing, y - spacing, z + 2.0 )
    foo( 1 ) = new Point( x + spacing, y - spacing, z + 2.0 )
    foo( 2 ) = new Point( x - spacing, y + spacing, z + 2.0 )
    foo( 3 ) = new Point( x + spacing, y + spacing, z + 2.0 )
    foo
  }

  def dom(draw: Draw, mode: View) {
    mode match {
      case View.PLAN =>
        println( "TrussBase.dom." )
        val group = MinderDom.svgClassGroup(draw, Truss.LayerTag)
        draw.appendRootChild(group)

        val base = group.rectangle(draw, x - size / 2, y - size / 2, size, size, TrussBase.Color )

        val transformX: Double = x + SvgElement.OffsetX
        val transformY: Double = y + SvgElement.OffsetY
        val transform: String = "rotate(" + rotation + "," + transformX + "," + transformY + ")"
        base.attribute("transform", transform)

      case default =>
    }
  }
}

object TrussBase {
  final val Tag: String = "trussbase"
  final val Color = "taupe"

  /**
    * Find a {@code TrussBase}
    *
    * @param mark string to match while searching for a { @code TrussBase}
    * @return { @code TrussBase} whose mark matches specified string
    */
  // Copied from Truss - refactor to Minder?
  def Find(mark: String): TrussBase = {
    import scala.collection.JavaConversions._
//    for (thingy <- ElementalLister.List) {
//      if (classOf[TrussBase].isInstance(thingy)) if (thingy.asInstanceOf[TrussBase].processedMark == mark) return thingy.asInstanceOf[TrussBase]
//    }

    val list = ElementalLister.List()

    for( item <- 0 until list.size() ) {
      val thingy = list.get( item )
      if ( thingy.isInstanceOf[ TrussBase ]) {
        if ( thingy.asInstanceOf[ TrussBase ].processedMark == mark ) {
          return thingy.asInstanceOf[TrussBase]
        }
      }
    }

    null
  }

  def Reset(): Unit = {
//    LegendRegistered = false
//    LegendCount = 0
  }

}
