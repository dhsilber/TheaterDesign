package com.mobiletheatertech.plot

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

import scala.Function1
import scala.runtime.BoxedUnit
import java.util.ArrayList

import scala.collection.JavaConversions._

/**
  * Created by DHS on 8/11/16.
  */

class SetPlatform (val element: Element) extends MinderDom(element)
  with Populate
  with Legendable
{
  val x = getDoubleAttribute("x")
  val y = getDoubleAttribute("y")
  val orientation = getOptionalDoubleAttributeOrZero("orientation")

  val polygonList: ArrayList[ Element ] = subElements(element, "shape")

  val Polygons: ArrayList[ Shape ] = new ArrayList[ Shape ]

  for (polygonElement <- polygonList) {
    val polygonString: String = polygonElement.getAttribute("polygon")
    if(null != polygonString && "" != polygonString) {
//      val polygon: Shape = new Shape( element );//polygonString)
//      Polygons.add(polygon)
    }
  }

  def subElements(element: Element, tag: String): ArrayList[ Element ] =
  {
    val resultList: ArrayList[ Element ] = new ArrayList[ Element ]
    val displays: NodeList = element.getElementsByTagName(tag)
    val length: Int = displays.getLength
    var index: Int = 0
    while (index < length) {
      {
        val node: Node = displays.item(index)
        if(null != node) if(node.getNodeType == Node.ELEMENT_NODE) {
          val subElement: Element = node.asInstanceOf[ Element ]
          resultList.add(subElement)
        }
      }
      {
        index += 1; index - 1
      }
    }
    resultList
  }

  def verify()
  {
  }

  def dom(draw: Draw, mode: View)
  {
    if(!Proscenium.Active) return
    val group: SvgElement = draw.group(draw, "")
//    for (var thing: Shape <- Polygons) {
//      thing.toSvg( group, draw, x, y )
//    }
  }

  def legendCountReset()
  {
  }

  def domLegendItem(draw: Draw, start: PagePoint): PagePoint =
    null

}

object SetPlatform {
  val Tag = "set-platform"
}