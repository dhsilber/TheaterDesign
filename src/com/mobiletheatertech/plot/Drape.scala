package com.mobiletheatertech.plot

import org.w3c.dom.{NodeList, Element}

import scala.collection.mutable.ListBuffer

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/30/13 Time: 4:01 AM To change this template use
 * File | Settings | File Templates.
 */
class Drape ( element: Element ) extends MinderDom( element ) {

  val drapeBaseList: NodeList = element.getElementsByTagName( "drapebase" )
  if (2 > drapeBaseList.getLength) {
    throw new InvalidXMLException( "Drape must have at least two drapebase children")
  }
  var basesMutable = new ListBuffer[DrapeBase]()
  var index = 0
  for (index <- 0 until drapeBaseList.getLength ) {
    var node = drapeBaseList.item( index )
    var element: Element = node.asInstanceOf[Element]
    basesMutable += new DrapeBase( element )

  }
  val bases = basesMutable.toList

  override def dom(draw: Draw, mode: View): Unit = {
//    import com.mobiletheatertech.plot.MinderDom
//    val group = Drape.svgClassGroup( draw,

    // duplicates code in MinderDom.svgClassGroup, which cannot be called from scala
//    because it is static
    val group: SvgElement = draw.element( "g" )
    group.attribute( "class", Drape.LayerTag )
    draw.appendRootChild( group )

    var index = 0;
    for (index <- 0 until bases.length - 1 ) {
      var base1 = bases( index )
      var base2 = bases( index + 1 )
      wavyline( group, draw, base1.x, base1.y, base2.x, base2.y, "magenta" )
    }
  }

  def wavyline( group: SvgElement, draw: Draw,
                x1: Double, y1: Double, x2: Double, y2: Double,
                color: String ): Unit = {
    // TODO: make a wavyline method in SvgElement
//    group.line( draw, x1, y1 + 10, x2, y2 + 10, color )

    val offsetX = SvgElement.OffsetX()
    val offsetY = SvgElement.OffsetY()

    var path = "M " + (x1 + offsetX).toString +
      " " + (y1 + offsetY).toString +
//    "Q " + (x1 - 10 + offsetX).toString +
//    " " + (y1 + 5 + offsetY).toString +
      "L " + (x2 + offsetX).toString +
      " " + (y2 + offsetY).toString
    group.path( draw, path, "purple" )

  }

  override def verify(): Unit = {}

  def gear(): List[Elemental] = {
    bases
  }
}

object Drape {
  val LayerTag = "drape"
}
