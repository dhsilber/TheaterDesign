package com.mobiletheatertech.plot

import org.w3c.dom.{Element, Node, NodeList}

import scala.util.Sorting

/**
  * Created by DHS on 7/18/16.
  */
class Pipe ( element: Element, parent: MinderDom ) extends UniqueId( element )
  with LinearSupportsClamp
  with Populate
{
  var name = id
  var standAlone: Boolean = false

  def this( element: Element ) {
    this( element, null )
  }

  var base: PipeBase = parentParse()
  override val based: Boolean = (null != base)
  override val positioned = !based


  val length: Double = lengthParse()
  val orientationValue = orientationParse()
  val offsetX = getOptionalDoubleAttributeOrZero("offsetx")

  val startUnadjusted: Point = startPosition()
//println( "startUnadjusted: " + startUnadjusted )
  start = Proscenium.LocateIfActive( startUnadjusted )
//println( "start          : " + start )


  // These are set within the *Processing() logic.
  //  var start: Point = null
  var boxOrigin: Point = figureBoxOrigin()
//println( "boxOrigin      : " + boxOrigin )


  //  // A pipe can be supported by a base or explicitly positioned within the drawing.
  //  var support1: Cheeseborough = null
  //  var support2: Cheeseborough = null

  //  override val ( suspended: Boolean, positioned: Boolean ) = process()
  positionProcessing()

  new Layer(Pipe.LayerTag, Pipe.LayerName, Pipe.Color)


  val begin: Double = if(crossesProsceniumCenterline()) startUnadjusted.x else 0.0
  val end: Double = if(crossesProsceniumCenterline()) startUnadjusted.x + length else length

  override def minLocation = begin
  override def maxLocation = end

  tagCallback( Luminaire.LAYERTAG, processLuminaire )
  populate( element )

  def setStandAlone(): Unit = {
    standAlone = true
  }

  def parentParse(): PipeBase = {
    if ( classOf[ PipeBase ].isInstance( parent ) )
      parent.asInstanceOf[ PipeBase ]
    else
      null
  }

  def orientationParse(): Double = {
    val orientation = getOptionalDoubleAttributeOrZero( "orientation" )
    if( (0.0 != orientation) && (90.0 != orientation) ) {
      ElementalLister.Remove(this)
      throw new InvalidXMLException(
        "Pipe (" + id + ") orientation may only be set to 90.")
    }
    orientation
  }

  def lengthParse(): Double = {
    val length = getDoubleAttribute( "length" )
    if ( 0 >= length ) {
      //    Yokeable.Remove( this )
      ElementalLister.Remove(this)
      throw new SizeException( this.getClass.getSimpleName, id, "should have a positive length" )
    }

    length
  }

  def startPosition(): Point = {
    if(based) {
      base.mountPoint()
    }
    else {
      val x = getOptionalDoubleAttributeOrNull("x")
      val y = getOptionalDoubleAttributeOrNull("y")
      val z = getOptionalDoubleAttributeOrNull("z")
      try {
//val thingy: Point = new Point( x, y, z )
//println ( "New pipe point: " + thingy + "." )

        new Point(x, y, z)
      }
      catch {
        case npe: NullPointerException => {
          ElementalLister.Remove(this)
          throw new InvalidXMLException (
            "Pipe (" + id + ") must be on a base or explicitly positioned.")
        }
      }
    }
  }

  def figureBoxOrigin(): Point = {
    if ( 90 == orientationValue ) {
      new Point(start.x() - 1, start.y(), start.z() - 1)
    }
    else if (based ) {
      new Point(start.x - 1, start.y - 1, start.z)
    }
    else {
      new Point(start.x, start.y + 1, start.z - 1)
    }
  }


  def positionProcessing(): Unit = {
//    tooSmall( start.x )
//    tooSmall( start.y )
//    tooSmall( start.z )

    if(!Venue.Contains(positionSpaceOccupied())) {
      //      Yokeable.Remove( this )
      ElementalLister.Remove(this)
      throw new LocationException( "Pipe (" + id
        + ") should not extend beyond the boundaries of the venue." )
    }

//    def tooSmall( dimension: Double ): Unit = {
//      if (0 >= dimension) {
//        ElementalLister.Remove(this)
//        throw new LocationException(
//          "Pipe (" + id + ") should not extend beyond the boundaries of the venue.")
//      }
//    }

    def positionSpaceOccupied(): Space = {
      if ( based ) {
        new Space( boxOrigin, Pipe.Diameter, Pipe.Diameter, length )
      }
      else if ( 90 == orientationValue ) {
        new Space( boxOrigin, Pipe.Diameter, length, Pipe.Diameter )
      }
      else {
//println ( "BoxOrigin: " + boxOrigin + ". Length: " + length + ". Diameter: " + Pipe.Diameter + "." )
        new Space( boxOrigin, length, Pipe.Diameter, Pipe.Diameter )
      }
    }
  }


  def crossesProsceniumCenterline(): Boolean = {
    if (90 == orientationValue)
      false
    else if ( (startUnadjusted.x < 0) && (startUnadjusted.x + length > 0) )
      true
    else
      false
  }

  def processLuminaire(element: Element ): Unit = {
    element.setAttribute( "on", id )
    new Luminaire(element)

    // Commented this out 2018-01-16 because Luminaire does this for itself.
    // leaving the code here because arguably hanging the light should be directed from above.
    //
    // OTOH, Luminaires are created either with/by the pipe or by Event, which argues for Luminaire
    // handling this in one location in the code.
    //
//    try {
//      hang(light, light.location )
//    }
//    catch {
//      case exception: MountingException => {
//        ElementalLister.Remove(this)
//        throw new MountingException(
//          "Pipe (" + id + ") unit '" + light.unit() + "' " + exception.getMessage)
//        //      case exception: Exception =>
//        //        throw new Exception( exception.getMessage, exception.getCause )
//      }
//    }
  }

//  def process(): ( Boolean, Boolean ) = {
//
////    cheeseboroughProcessing()
//    if( null != start ) return ( true, false )
//
//    ( false, positionProcessing() )
//  }

//  def cheeseboroughProcessing(): Unit = {
//    val cheeseboroughList: NodeList = element.getElementsByTagName(Pipe.Cheeseborough)
//    cheeseboroughList.getLength() match {
//      case 0 => return
//      case 2 => {}
//      case _ => {
////        Yokeable.Remove(this)
//        throw new InvalidXMLException(
//          "Pipe (" + id + ") should have zero or two cheeseboroughs.")
//      }
//    }
//    support1 = findCheeseborough( cheeseboroughList, 0 )
//    support2 = findCheeseborough( cheeseboroughList, 1 )
//
//    val point1 = support1.locate()
//    val point2 = support2.locate()
//    val slope: Double = point1.slope( point2 )
//    //        rotation = Math.toDegrees(Math.atan(slope));
//
//    val supportSpan: Double = point1.distance(point2)
//    val span: Long = Math.round(supportSpan)
//    val overHang: Double = (length - span.intValue()) / 2
//
////    start = slopeToPoint( slope, overHang )
//  }
//
//  def findCheeseborough( cheeseboroughList: NodeList, index: Int ): Cheeseborough = {
//    val node: Node = cheeseboroughList.item(index)
//    // Much of this code is copied from HangPoint.ParseXML - refactor
//    if ((null != node) & (node.getNodeType == Node.ELEMENT_NODE)) {
//      val element: Element = node.asInstanceOf[Element]
//      //          val mark: String = element.getAttribute("processedMark")
//
//      val reference: String = element.getAttribute("id")
//      val found: Cheeseborough = Cheeseborough.Find(reference)
//
//      return found
//    }
//
//    return null
//  }

  def slopeToPoint( slope: Double, overHang: Double ): Point = {
    new Point( 1.0, 2.0, 3.0 )
  }

  //  def tooLarge( dimension: Double ): Unit = {
  //    if (0 >= dimension)
  //      throw new LocationException(
  //        "Pipe (" + identifier + ") should not extend beyond the boundaries of the venue.")
  //  }

//  def tooShort( dimension: Double ): Unit = {
//    if (0 >= dimension) {
//      ElementalLister.Remove( this )
//      throw new SizeException( this.toString(), " length" )
//    }
//  }



  def draw( draw: Draw, title: String ): Unit = {

    numberLuminaires()

    val group: SvgElement = MinderDom.svgClassGroup(draw, Pipe.LayerTag)
    draw.appendRootChild(group)

    val ex = 10.0
    var wy = 0.0
    group.headerText( draw, Event.Name, ex, wy )
    wy += 20
    group.headerText( draw, Venue.Building + " -- " + Venue.Name, ex, wy )

    wy += 20.0
    group.text(draw, title, ex, wy, Pipe.Color, "14" )

    wy += 20.0
    val drawBox: Point = new Point( ex, wy, 0.0 )
    group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color)

    val normalDrawBox: Point = new Point(boxOrigin.x() + offsetX, boxOrigin.y(), boxOrigin.z())
    val offset: Point = new Point(
      normalDrawBox.x() - drawBox.x(),
      normalDrawBox.y() - drawBox.y(),
      normalDrawBox.z() - normalDrawBox.z() )
    for ( luminaire <- IsClampList ) {
      luminaire.setStandAloneOffset( offset )
      luminaire.dom(draw, View.PLAN)
    }

    wy += 10
    var totalWeight = 0.0
    for ( luminaire <- sortedClampList ) {
      wy += 15
      val dmx = if ( ! luminaire.address().isEmpty) { luminaire.address() } else { luminaire.dimmer() }
      val kind = luminaire.`type`()
      var text = luminaire.unit().toString +
        ": [" + dmx +
        "] (" + luminaire.channel() + ") " +
        kind +
        "  --  (location: " + luminaire.location.feet() + ")" +
        "  --  " + luminaire.info()
      if ( ! luminaire.target().isEmpty ) {
        text += "  --  target is " + luminaire.target()
      }
      if ( ! luminaire.color().isEmpty ) {
        text += "  --  [color: " + luminaire.color() + "]"
      }

      val luminaireDefinition = LuminaireDefinition.Select( kind )
      if (null == luminaireDefinition) {
        throw new ReferenceException("Unable to find definition for " + kind )
      }
      val weight = luminaireDefinition.weight()
      text += "  -- weight: " + weight.toString
      totalWeight += weight

      group.text( draw, text, ex, wy, Luminaire.COLOR )
    }

    wy += 20
    val weightText = "Total weight on this pipe is " + totalWeight + "."
    group.text( draw, weightText, ex, wy, Luminaire.COLOR )

    wy += 20
    val keyText = "* detail lines start with \"<unit number>: [<dimmer or address>] (<channel>)\"."
    group.text( draw, keyText, ex, wy, Luminaire.COLOR )
  }

  def dump(): Unit = {
    for ( luminaire <- IsClampList ) {
      System.out.println( luminaire.toString )
    }
  }

  // Members declared in com.mobiletheatertech.plot.MinderDom
  def dom( draw: Draw, mode: View): Unit = {
    if (mode == View.TRUSS) return

    numberLuminaires()


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
      case _ => throw new Exception( "In Pipe.dom, id = " + id + " -- Invalid View.")
    }
    //        group.appendChild(pipeRectangle);

    def domPlan(): Unit = {
      if (based) {
        group.circle( draw, drawBox.x() + Pipe.Radius, drawBox.y() + Pipe.Radius, Pipe.Radius, Pipe.Color)
      } else if (90.0 == orientationValue) {
        group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color)
        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color)
      } else {
        group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color)
        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color)
      }
    }
  }

  // Members declared in com.mobiletheatertech.plot.Yokeable
  def calculateIndividualLoad(x$1: Luminaire): String = { "" }

  def locationDistance( location: Location ): Double = {
    val distance = location.distance

    if( ! distance.valid  ) {
      throw new MountingException( "Location specified does not contain a valid distance." )
    }

    if ( (begin > distance.value) || (distance.value > end) ) {
      ElementalLister.Remove(this)
      throw new MountingException(
        "Pipe (" + id + ") location must be in the range of " + begin.toString() +
          " to " + end.toString() + ".")
    }
    distance.value
  }

  override def rotatedLocation( location: Location ): Place = {
    if (positioned) {
      if ( 90.0 == orientationValue ) {
        new Place(mountableLocation(location), origin(), 90.0 )
      }
      else {
        new Place(mountableLocation(location), origin(), 0.0)
      }
    }
    else if ( based ) {
//      val transformX: Double = start.x + SvgElement.OffsetX()
//      val transformY: Double = start.y + SvgElement.OffsetY()
//      val origin: Point = new Point(transformX, transformY, Pipe.baseOffsetZ )
      new Place( mountableLocation( location ), origin(), 0.0 )
    }
    else {
      //      val transformX: Double = support1.locate().x() + SvgElement.OffsetX
      //      val transformY: Double = support1.locate().y() + SvgElement.OffsetY
      //      val origin: Point = new Point(transformX, transformY, support1.locate().z() )
      //      new Place( mountableLocation( location ), origin, 0.0 )
      ElementalLister.Remove(this)
      throw new Exception( "Bailing out of rotatedLocation() because no cheeseborough.")
    }
  }

  def mountableLocation( location: Location ): Point = {
    val offset = location.distance

    if( ! offset.valid  ) {
      throw new MountingException( "Location specified does not contain a valid distance." )
    }

    if ((offset.value < begin) || (end < offset.value)) {
      ElementalLister.Remove(this)
      throw new MountingException("beyond the end of pipe")
    }

    if (based)
      new Point( start.x, start.y, start.z + offset.value )
    else if (90 == orientationValue)
      new Point(start.x, start.y + offset.value, start.z )
    else //if ((startUnadjusted.x< 0) && (startUnadjusted.x + length > 0))
      new Point(start.x - begin + offset.value, start.y, start.z)
//    else
//      new Point(start.x - begin + offset, start.y, start.z)
  }

//  def numberFromLocation( location: String ): Int = {
//    try {
//      new Integer( location )
//    }
//    catch {
//      case nfe: NumberFormatException => {
//        ElementalLister.Remove(this)
//        throw new InvalidXMLException(
//          this.getClass.getSimpleName, id, "location must be a number")
//      }
//    }
//  }

  def schematicLocation(x$1: String): com.mobiletheatertech.plot.PagePoint = {
    new PagePoint( 0.0, 0.0 ) }
  def suspensionPoints(): String = { "" }
  def totalSuspendLoads(): String = { "" }

  // Members declared in com.mobiletheatertech.plot.Verifier
  def verify(): Unit = {}

  override def toString(): String = {
    "Pipe { " +
      "id='" + id + "' " +
      "origin=" + start +
      ", length=" + length +
      " }"
  }




}

object Pipe {
  final val LayerTag: String = "pipe"
  final val Tag = LayerTag

  final val Diameter: Double = 2.0
  final val DiameterString = Diameter.toString
  final val Radius = Diameter / 2
  final val LayerName: String = "Pipes"
//  final val Cheeseborough: String = "cheeseborough"
  final val Color: String = "black"
  final val baseOffsetZ: Double = 2.0

  def SchematicPositionReset() {}

  def Select( id: String ): Pipe = {
    return LinearSupportsClamp.Select( id ).asInstanceOf[ Pipe ]
  }
}