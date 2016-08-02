package com.mobiletheatertech.plot

import org.w3c.dom.{Element, Node, NodeList}

/**
  * Created by DHS on 7/18/16.
  */
class Pipe ( element: Element ) extends UniqueId( element ) 
  with LinearSupportsClamp
  with Populate
{

  val length: Double = getDoubleAttribute( "length" )

  val x = getOptionalDoubleAttributeOrNull( "x" )
  val y = getOptionalDoubleAttributeOrNull( "y" )
  val z = getOptionalDoubleAttributeOrNull( "z" )
  val orientationValue = getOptionalDoubleAttributeOrZero( "orientation" )
  val offsetX = getOptionalDoubleAttributeOrZero( "offsetx" )

  // These are set within the *Processing() logic.
  var start: Point = null
  var boxOrigin : Point = null


  // A pipe can be supported by a base or explicitly positioned within the drawing.
  var base: PipeBase = null
  var support1: Cheeseborough = null
  var support2: Cheeseborough = null
  override val ( based: Boolean, positioned: Boolean ) = process()


  if ( 0 >= length ) {
//    Yokeable.Remove( this )
    throw new SizeException( this.toString(), "length" )
  }

  if( (0.0 != orientationValue) && (90.0 != orientationValue) )
    throw new InvalidXMLException(
      "Pipe (" + id + ") orientation may only be set to 90." )

  val begin: Double = if ( crossesProsceniumCenterline() ) start.x          else 0.0
  val end: Double   = if ( crossesProsceniumCenterline() ) start.x + length else length

  override def minLocation = begin
  override def maxLocation = end

  new Layer( Pipe.LayerTag, Pipe.LayerName, Pipe.Color )

  tagCallback( Luminaire.LAYERTAG, processLuminaire )
  populate( element )

  def processLuminaire(element: Element ): Unit = {
    element.setAttribute( "on", id )
    val light: Luminaire = new Luminaire(element)
    try {
      hang(light, light.locationValue().toDouble )
    }
    catch {
      case exception: MountingException =>
        throw new MountingException (
          "Pipe (" + id + ") unit '" + light.unit() + "' " + exception.getMessage )
//      case exception: Exception =>
//        throw new Exception( exception.getMessage, exception.getCause )
    }
  }

  def process(): ( Boolean, Boolean )= {
    base = baseProcessing()
    if ( null != base ) return ( true, false )

    cheeseboroughProcessing()
    if( null != start ) return ( false, false )

    ( false, positionProcessing() )
  }

  def cheeseboroughProcessing(): Unit = {
    val cheeseboroughList: NodeList = element.getElementsByTagName(Pipe.Cheeseborough)
    cheeseboroughList.getLength() match {
      case 0 => return
      case 2 => {}
      case _ => {
//        Yokeable.Remove(this)
        throw new InvalidXMLException(
          "Pipe (" + id + ") should have zero or two cheeseboroughs.")
      }
    }
    support1 = findCheeseborough( cheeseboroughList, 0 )
    support2 = findCheeseborough( cheeseboroughList, 1 )

    val point1 = support1.locate()
    val point2 = support2.locate()
    val slope: Double = point1.slope( point2 )
    //        rotation = Math.toDegrees(Math.atan(slope));

    val supportSpan: Double = point1.distance(point2)
    val span: Long = Math.round(supportSpan)
    val overHang: Double = (length - span.intValue()) / 2

    start = slopeToPoint( slope, overHang )
  }

  def findCheeseborough( cheeseboroughList: NodeList, index: Int ): Cheeseborough = {
    val node: Node = cheeseboroughList.item(index)
    // Much of this code is copied from HangPoint.ParseXML - refactor
    if ((null != node) & (node.getNodeType == Node.ELEMENT_NODE)) {
      val element: Element = node.asInstanceOf[Element]
      //          val mark: String = element.getAttribute("processedMark")

      val reference: String = element.getAttribute("id")
      val found: Cheeseborough = Cheeseborough.Find(reference)

      return found
    }

    return null
  }

  def crossesProsceniumCenterline(): Boolean = {
    if (Proscenium.Active())
      if (90 == orientationValue)
        false
      else if ( (start.x < 0) && (start.x + length > 0) )
        true
      else
        false
    else
      false
  }

  def slopeToPoint( slope: Double, overHang: Double ): Point = {
    new Point( 1.0, 2.0, 3.0 )
  }

  def positionProcessing(): Boolean = {
    try {
      start = new Point( x, y, z )
    }
    catch {
      case npe: NullPointerException =>
        throw new InvalidXMLException(
          "Pipe (" + id + ") must be on base or explicitly positioned." )
    }

    tooSmall( Proscenium.Origin().x + x )
    tooSmall( Proscenium.Origin().y + y )
    tooSmall( Proscenium.Origin().z + z )

    if (! Venue.Contains( positionSpaceOccupied() ) ) {
//      Yokeable.Remove( this )
      throw new LocationException( "Pipe (" + id
        + ") should not extend beyond the boundaries of the venue." )
    }

    true
  }

  def positionSpaceOccupied(): Space = {

    if ( Proscenium.Active() )
      if ( 90 == orientationValue ) {
        boxOrigin = Proscenium.Locate(new Point(start.x() - 1, start.y(), start.z() - 1))
      }
      else {
        boxOrigin = Proscenium.Locate(new Point(start.x, start.y + 1, start.z - 1))
      }
    else {
      boxOrigin = new Point( start.x, start.y - 1, start.z - 1 )
    }

    if ( Proscenium.Active() && (90 == orientationValue) ) {
      new Space( boxOrigin, Pipe.Diameter, length, Pipe.Diameter )
    }
    else {
      new Space( boxOrigin, length, Pipe.Diameter, Pipe.Diameter )
    }
  }

  def tooSmall( dimension: Double ): Unit = {
    if (0 >= dimension)
      throw new LocationException(
        "Pipe (" + id + ") should not extend beyond the boundaries of the venue.")
  }

  //  def tooLarge( dimension: Double ): Unit = {
  //    if (0 >= dimension)
  //      throw new LocationException(
  //        "Pipe (" + identifier + ") should not extend beyond the boundaries of the venue.")
  //  }

  def tooShort( dimension: Double ): Unit = {
    if (0 >= dimension)
      throw new SizeException( this.toString(), " length" )
  }

  def baseProcessing(): PipeBase = {
    val base = findBase()
    if ( null != base ) {
      start = new Point( base.x, base.y, base.z + 2 )

      if ( Proscenium.Active() ) {
        boxOrigin = new Point( start.x - 1, start.y + 1, start.z )
        boxOrigin = Proscenium.Locate( boxOrigin )
      }
      else {
        boxOrigin = new Point( start.x - 1, start.y - 1, start.z )
      }

      val space: Space = new Space( boxOrigin, Pipe.Diameter, Pipe.Diameter, length )
      if ( ! Venue.Contains( space ) ) {
//        Yokeable.Remove(this)
        throw new LocationException(
          "Pipe (" + id + ") should not extend beyond the boundaries of the venue.")
      }
    }

    base
  }

//  def baseDetermine(): Boolean = {
//    base = findBase()
//
//    start = new Point( base.x, base.y, base.z )
//    false
//  }

  def findBase(): PipeBase = {
    val baseList: NodeList = element.getElementsByTagName( "pipebase" )
    if ( 1 == baseList.getLength() ) {
      val node: Node = baseList.item( 0 )
      // Much of this code is copied from HangPoint.ParseXML - refactor
      if ( (null != node) & (node.getNodeType == Node.ELEMENT_NODE) ) {
          val element: Element = node.asInstanceOf[ Element ]
//          val mark: String = element.getAttribute("processedMark")

          return new PipeBase( element )

      }
      return null
    }
    else if ( 1 < baseList.getLength() ) {
      throw new InvalidXMLException( "Pipe (" + id + ") should not have more than one base." )
    }

    null
  }

  // Members declared in com.mobiletheatertech.plot.MinderDom
  def dom( draw: Draw, mode: View): Unit = {
    if (mode == View.TRUSS) return


    //    val height: Double = Venue.Height() - boxOrigin.z()

    val drawBox: Point = new Point(boxOrigin.x() + offsetX, boxOrigin.y(), boxOrigin.z())

    val group: SvgElement = MinderDom.svgClassGroup(draw, Pipe.LayerTag)
    draw.appendRootChild(group)

    mode match {
      case View.PLAN => domPlan()
      case View.TRUSS => domPlan()
      //            case SCHEMATIC:
      //                if (90.0 == orientation) {
      //                    group.rectangleAbsolute(draw,
      //                            schematicPosition.x() - DIAMETER / 2,
      //                            schematicPosition.y() - length / 2,
      //                            DIAMETER, length, COLOR);
      //                } else {
      //                    group.rectangleAbsolute(draw,
      //                            schematicPosition.x() - length / 2,
      //                            schematicPosition.y() - DIAMETER / 2,
      //                            length, DIAMETER, COLOR );
      //                }
      //                break;
      case View.SECTION => return
      //        group.rectangle( draw, drawBox.y(), height, Pipe.Diameter, Pipe.Diameter, Pipe.Color );
      case View.FRONT => return
      //        group.rectangle( draw, drawBox.x(), height, length, Pipe.Diameter, Pipe.Color );
    }
    //        group.appendChild(pipeRectangle);

    def domPlan(): Unit = {
      if (based) {
        group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, Pipe.Diameter, Pipe.Color);
      } else if (90.0 == orientationValue) {
        group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color);
        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color);
      } else {
        group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color);
        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color);
      }
    }
  }

  // Members declared in com.mobiletheatertech.plot.Yokeable
  def calculateIndividualLoad(x$1: Luminaire): String = { "" }
  def locationDistance( location: String ): Integer = {
    val distance = numberFromLocation( location )

    if ( (begin > distance) || (distance > end) )
      throw new MountingException(
        "Pipe (" + id + ") location must be in the range of " + begin.toString() +
        " to " + end.toString() + ".")

    distance
  }

  def mountableLocation( location: String ): Point = {
    val offset: Double = numberFromLocation(location)

    if ((offset < begin) || (end < offset))
      throw new MountingException("beyond the end of pipe")

    def crossesProsceniumCenterline(): Point = {
      Proscenium.Locate(new Point(offset, start.y - 1, start.z - 1))
    }
    def doesNotCrossProsceniumCenterline(): Point = {
      Proscenium.Locate(new Point(start.x + offset, start.y - 1, start.z - 1))
    }
    def rightAngleToProscenium(): Point = {
      Proscenium.Locate(new Point(start.x - 1, start.y + offset, start.z - 1))
    }
    def noProscenium(): Point = {
      new Point(start.x + offset, start.y, start.z)
    }

    if (Proscenium.Active()) {
      if (based)
        Proscenium.Locate( new Point( base.x, base.y, base.z + offset + Pipe.baseOffsetZ ) )
      else if (90 == orientationValue)
        rightAngleToProscenium()
      else if ((start.x < 0) && (start.x + length > 0))
        crossesProsceniumCenterline()
      else
        doesNotCrossProsceniumCenterline()
    }
    else {
      if (based)
        new Point(base.x, base.y, base.z + offset + Pipe.baseOffsetZ)
      else if (90 == orientationValue)
        new Point(start.x - 1, start.y + offset, start.z - 1)
      else
        noProscenium()
    }
  }

  def numberFromLocation( location: String ): Int = {
    try {
      new Integer( location )
    }
    catch {
      case nfe: NumberFormatException =>
        throw new InvalidXMLException("Pipe (" + id + ") location must be a number.")
    }
  }

  override def rotatedLocation( location: String ): Place = {
    if (positioned) {
      if ( 90.0 == orientationValue ) {
        new Place(mountableLocation(location), start, 90.0 )
      }
      else {
        new Place(mountableLocation(location), start, 0.0)
      }
    }
    else if ( based ) {
      val transformX: Double = base.x + SvgElement.OffsetX
      val transformY: Double = base.y + SvgElement.OffsetY
      val origin: Point = new Point(transformX, transformY, Pipe.baseOffsetZ )
      new Place( mountableLocation( location ), origin, 0.0 )
    }
    else {
      val transformX: Double = support1.locate().x() + SvgElement.OffsetX
      val transformY: Double = support1.locate().y() + SvgElement.OffsetY
      val origin: Point = new Point(transformX, transformY, support1.locate().z() )
      new Place( mountableLocation( location ), origin, 0.0 )
    }
  }

  def schematicLocation(x$1: String): com.mobiletheatertech.plot.PagePoint = {
    new PagePoint( 0.0, 0.0 ) }
  def suspensionPoints(): String = { "" }
  def totalSuspendLoads(): String = { "" }

  // Members declared in com.mobiletheatertech.plot.Verifier
  def verify(): Unit = {}

  override def toString(): String = {
    "Pipe { " +
      //                \"id='\" + id + '\\'' +
      "origin=" + start +
      ", length=" + length +
      " }"
  }
}

object Pipe {
  final val Diameter: Double = 2.0
  final val DiameterString = Diameter.toString
  final val LayerName: String = "Pipes"
  final val LayerTag: String = "pipe"
  final val Cheeseborough: String = "cheeseborough"
  final val Color: String = "black"
  final val baseOffsetZ: Double = 2.0

  def SchematicPositionReset() {}
}