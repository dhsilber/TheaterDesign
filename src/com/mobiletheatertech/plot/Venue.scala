package com.mobiletheatertech.plot

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.Text
import java.awt._

/**
  * Created by DHS on 8/3/16.
  */

class Venue ( element: Element) extends MinderDom(element)
  with Populate
  with Legendable
{
  val room = getStringAttribute("room")
  val width = getDoubleAttribute("width")
  val depth = getDoubleAttribute("depth")
  val height = getDoubleAttribute("height")
  val building = getOptionalStringAttribute("building")
  val circuiting: String = getOptionalStringAttribute("circuiting")

  circuiting match {
    case "" =>
    case "one-to-one" =>
    case "one-to-many" =>
    case _ =>
      throw new InvalidXMLException("'circuiting' attribute invalid.")
  }

  new Point(width, depth, height)
  Venue.StaticVenue = this

  tagCallback( Proscenium.Tag, processProscenium )
  tagCallback( Raceway.Tag, processRaceway )
//  tagCallback( PipeBase.Tag, processPipeBase )
  tagCallback( Pipe.LayerTag, processPipe )
//  tagCallback( TrussBase.Tag, processTrussBase )
//  tagCallback( Truss.Tag, processTruss )

  populate( element )

  def processProscenium( element: Element ): Unit = {
    new Proscenium( element )
  }

  def processRaceway( element: Element ): Unit = {
    new Raceway( element )
  }

//  def processPipeBase( element: Element ): Unit = {
//    new PipeBase( element )
//  }

  def processPipe( element: Element ): Unit = {
    new Pipe( element )
  }

//  def processTrussBase( element: Element ): Unit = {
//    //    println( "Event about to create TrussBase")
//    new TrussBase( element )
//  }
//
//  def processTruss( element: Element ): Unit = {
//    //    println( "Event about to create Truss")
//    new Truss( element )
//    //    println( "Event completed creating Truss")
//  }

  def verify {
  }

  def dom(draw: Draw, mode: View) {
    mode match {
      case View.PLAN =>
        draw.rectangle(draw, 0.0, 0.0, width, depth, Venue.COLOR)
      case View.SECTION =>
//        println( s"z: ${Point.SmallZ}")
        draw.rectangle(draw, 0.0, 0.0, depth, height - Point.SmallZ, Venue.COLOR)
      case View.FRONT =>
        draw.rectangle(draw, 0.0, 0.0, width, height, Venue.COLOR)
      case View.TRUSS =>
    }
  }

  def legendCountReset {
  }

  def domLegendItem(draw: Draw, start: PagePoint): PagePoint = {
    val x: Double = start.x + 10
    val text: SvgElement = draw.text(draw, room, x, start.y, Legend.TEXTCOLOR)
    text.attribute("font-family", "serif")
    text.attribute("font-size", "10")
    val foo: Text = draw.document.createTextNode(room)
    text.appendChild(foo)
    return new PagePoint(start.x, start.y + 12)
  }

  override def toString: String = {
    return building + room + "\nWidth: " + width + ", Depth: " + depth + ", Height: " + height
  }
}


object Venue {
  val Tag = "venue"
  val ONETOMANY: String = "one-to-many"
  val ONETOONE: String = "one-to-one"
  private var StaticVenue: Venue = null
  private val COLOR: String = "black"

  def Venue: Venue = {
    return StaticVenue
  }

  @throws[ReferenceException]
  def Contains(space: Space): Boolean = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    val room: Space = new Space(new Point(0.0, 0.0, 0.0), StaticVenue.width, StaticVenue.depth, StaticVenue.height)
    return room.contains(space)
  }

  @throws[ReferenceException]
  def Contains2D(rectangle: Rectangle): Boolean = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    val area: Rectangle = new Rectangle(0, 0, StaticVenue.width.intValue, StaticVenue.depth.intValue)
    return area.contains(rectangle)
  }

  @throws[ReferenceException]
  def Contains2D(x: Int, y: Int): Int = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    val area: Rectangle = new Rectangle(0, 0, StaticVenue.width.intValue, StaticVenue.depth.intValue)
    return area.outcode(x, y)
  }

  @throws[ReferenceException]
  def Name: String = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.room
  }

  @throws[ReferenceException]
  def Width: Double = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.width
  }

  def Depth: Double = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.depth
  }

  @throws[ReferenceException]
  def Height: Double = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.height
  }

  @throws[ReferenceException]
  def Building: String = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.building
  }

  @throws[ReferenceException]
  def Circuiting: String = {
    if(null == StaticVenue) {
      throw new ReferenceException("Venue is not defined.")
    }
    return StaticVenue.circuiting
  }

  def Reset(): Unit = {
    StaticVenue = null
  }

  def ToString: String = {
    StaticVenue.toString()
//    return "Venue " + Name + ": width " + Width + ", depth " + Depth + ", height " + Height
  }
}
