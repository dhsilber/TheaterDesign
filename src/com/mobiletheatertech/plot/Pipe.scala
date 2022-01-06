package com.mobiletheatertech.plot

import org.w3c.dom.{Element, Node, NodeList}

import java.lang

/**
  * Created by DHS on 7/18/16.
  */
class Pipe(element: Element, parent: MinderDom) extends UniqueId(element)
  with LinearSupportsClamp
  with KnowsChildren
  with Populate {

  var parentType = Pipe.ParentType.Unknown
  var name: String = element.getAttribute("id")

  //  var mountingStatus = Unknown
  //  println()
  //  println( "Starting to build  " + name )
  //  var standAlone: Boolean = false

  def this(element: Element) {
    this(element, null)
  }

  def parent(): MinderDom = {
    return parent
  }

  var base: PipeBase = _
  var parentPipe: Pipe = _
  positioned = !based
  parentParse()
  //  println("Parent base is " + parent.id)
  //  println("Parent pipe is " + parentPipe)

  val length: Double = lengthParse()
  val attachmentPosition: Double = attachmentPositionParse()
  val horizontalOrientation: Double = horizontalOrientationParse()
  val offsetX: lang.Double = getOptionalDoubleAttributeOrZero("offsetx")
  val offsetY: lang.Double = getOptionalDoubleAttributeOrZero("offsety")

  val startUnadjusted: Point = startPosition()
  //  println( s"startUnadjusted for $id: " + startUnadjusted )
  start = Proscenium.LocateIfActive(startUnadjusted)
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


  val begin: Double = if (crossesProsceniumCenterline()) startUnadjusted.x else 0.0
  val end: Double = if (crossesProsceniumCenterline()) startUnadjusted.x + length else length

  override def minLocation: Double = begin

  override def maxLocation: Double = end

  tagCallback(Pipe.LayerTag, processPipe)
  tagCallback(Luminaire.LAYERTAG, processLuminaire)
  populate(element)

  //  println(this.toString())

  //  def setStandAlone(): Unit = {
  //    standAlone = true
  //  }

  def parentParse(): Unit = {
    if (classOf[PipeBase].isInstance(parent)) {
      base = parent.asInstanceOf[PipeBase]
      based = true
      //      println( "Found that parent is a base and got " + base.idForLocalUse)

    } else if (classOf[Pipe].isInstance(parent)) {
      parentPipe = parent.asInstanceOf[Pipe]
      parentPipe.registerChild(this)
      //      println( " + getOptionalDoubleAttributeOrNull("attach"))
      //    } else {
      //      throw new InvalidXMLException("Pipe (" + id + ") parent is neither PipeBase nor Pipe.")
    }
    //else
    //      null
  }

  def horizontalOrientationParse(): Double = {
    var orientation = getOptionalDoubleAttributeOrZero("orientation")
    if ((0.0 != orientation) && (90.0 != orientation)) {
      ElementalLister.Remove(this)
      throw new InvalidXMLException("Pipe (" + id + ") orientation may only be set to 90.")
    }

    //  TODO:   This is wrong, but it is too late at night for me to dive in to fixing it.
    //    if (attachmentPosition != null) {
    //      orientation = 90.0
    //    }



    orientation
  }

  def attachmentPositionParse(): Double = {
    getOptionalDoubleAttributeOrNull("attach")
  }

  def lengthParse(): Double = {
    val length = getDoubleAttribute("length")
    if (0 >= length) {
      //    Yokeable.Remove( this )
      ElementalLister.Remove(this)
      throw new SizeException(this.getClass.getSimpleName, id, "should have a positive length")
    }

    length
  }

  def startPosition(): Point = {
    if (based) {
      base.mountPoint()
    }
    else if (attachmentPosition != null) {
      // cross-pipe, assuming that mounting point is in the center
      // cross-pipe will have horizontal orientation
      // Initial functionality does not allow for rotation away from the x-axis
      //      println( s"X: ${parentPipe} ${parentPipe.origin()}  length: $length")
      val x = parentPipe.start.x() - length / 2.0
      val y = parentPipe.start.y()
      val z = parentPipe.start.z() + attachmentPosition
      new Point(x, y, z)
    }
    else {
      val x = getOptionalDoubleAttributeOrNull("x")
      val y = getOptionalDoubleAttributeOrNull("y")
      val z = getOptionalDoubleAttributeOrNull("z")
      println("point: ", x, " ", y, " ", z)
      try {
        //val thingy: Point = new Point( x, y, z )
        //println ( "New pipe point: " + thingy + "." )

        new Point(x, y, z)
      }
      catch {
        case npe: NullPointerException => {
          ElementalLister.Remove(this)
          throw new InvalidXMLException(
            "Pipe (" + id + ") must be mounted on a base or another pipe or explicitly positioned.")
        }
      }
    }
  }

  def figureBoxOrigin(): Point = {
    if (90 == horizontalOrientation) {
      new Point(start.x() - 1, start.y(), start.z() - 1)
    }
    else if (based) {
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
    //    println("Venue: " + Venue.toString())
    //    println("Pipe occupies " + positionSpaceOccupied().toString())

    if (!Venue.Contains(positionSpaceOccupied())) {
      //      Yokeable.Remove( this )
      ElementalLister.Remove(this)
      throw new LocationException("Pipe (" + id
        + ") should not extend beyond the boundaries of the venue.")
    }

    //    def tooSmall( dimension: Double ): Unit = {
    //      if (0 >= dimension) {
    //        ElementalLister.Remove(this)
    //        throw new LocationException(
    //          "Pipe (" + id + ") should not extend beyond the boundaries of the venue.")
    //      }
    //    }

    def positionSpaceOccupied(): Space = {
      if (based) {
        new Space(boxOrigin, Pipe.Diameter, Pipe.Diameter, length)
      }
      else if (90 == horizontalOrientation) {
        new Space(boxOrigin, Pipe.Diameter, length, Pipe.Diameter)
      }
      else {
        //println ( "BoxOrigin: " + boxOrigin + ". Length: " + length + ". Diameter: " + Pipe.Diameter + "." )
        new Space(boxOrigin, length, Pipe.Diameter, Pipe.Diameter)
      }
    }
  }


  def crossesProsceniumCenterline(): Boolean = {
    if (90 == horizontalOrientation)
      false
    else if ((startUnadjusted.x < 0) && (startUnadjusted.x + length > 0))
      true
    else
      false
  }

  def processPipe(element: Element): Unit = {
    element.setAttribute("on", id)
    //    println("Making new Pipe attached to pipe " + id)
    // TODO: This should be stored somewhere so that a build drawing of a boom can be made in one generated page:
    new Pipe(element, this)
  }


  def processLuminaire(element: Element): Unit = {
    element.setAttribute("on", id)
    //    println("Making new Luminaire attached to pipe " + id)
    val luminaire = new Luminaire(element)
    //    println("... new luminaire is " + luminaire.`type`() + ", at: " + luminaire.location())

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

  def slopeToPoint(slope: Double, overHang: Double): Point = {
    new Point(1.0, 2.0, 3.0)
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


  def dump(): Unit = {
    for (luminaire <- IsClampList) {
      System.out.println(luminaire.toString)
    }
  }


  // Members declared in com.mobiletheatertech.plot.Yokeable
  def calculateIndividualLoad(x$1: Luminaire): String = {
    ""
  }

  def locationDistance(location: Location): Double = {
    val distance = location.distance

    if (!distance.valid) {
      throw new MountingException("Location specified does not contain a valid distance.")
    }

    if ((begin > distance.value) || (distance.value > end)) {
      ElementalLister.Remove(this)
      throw new MountingException(
        "Pipe (" + id + ") location must be in the range of " + begin.toString() +
          " to " + end.toString() + ".")
    }
    distance.value
  }

  override def rotatedLocation(location: Location): Place = {
    if (positioned) {
      new Place(mountableLocation(location), origin(), horizontalOrientation)
    }
    else if (based) {
      //      val transformX: Double = start.x + SvgElement.OffsetX()
      //      val transformY: Double = start.y + SvgElement.OffsetY()
      //      val origin: Point = new Point(transformX, transformY, Pipe.baseOffsetZ )
      new Place(mountableLocation(location), origin(), 0.0)
    }
    else {
      //      val transformX: Double = support1.locate().x() + SvgElement.OffsetX
      //      val transformY: Double = support1.locate().y() + SvgElement.OffsetY
      //      val origin: Point = new Point(transformX, transformY, support1.locate().z() )
      //      new Place( mountableLocation( location ), origin, 0.0 )
      ElementalLister.Remove(this)
      throw new Exception("Bailing out of rotatedLocation() because no cheeseborough.")
    }
  }

  def mountableLocation(location: Location): Point = {
    val offset = location.distance

    if (!offset.valid) {
      throw new MountingException("Location specified does not contain a valid distance.")
    }

    if ((offset.value < begin) || (end < offset.value)) {
      ElementalLister.Remove(this)
      throw new MountingException("beyond the end of pipe")
    }

    if (based)
      new Point(start.x, start.y + offsetY, start.z + offset.value)
    else if (90 == horizontalOrientation)
      new Point(start.x, start.y + offsetY + offset.value, start.z)
    else //if ((startUnadjusted.x< 0) && (startUnadjusted.x + length > 0))
      new Point(start.x - begin + offset.value, start.y + offsetY, start.z)
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
    new PagePoint(0.0, 0.0)
  }

  def suspensionPoints(): String = {
    ""
  }

  def totalSuspendLoads(): String = {
    ""
  }

  // Members declared in com.mobiletheatertech.plot.Verifier
  def verify(): Unit = {}

  override def toString(): String = {
    "Pipe { " +
      "id='" + id + "' " +
      "origin=" + start +
      ", length=" + length +
      " }"
  }


  // This only works for booms made up of one vertical pipe and some number of crosspipes.
  def draw(draw: Draw, title: String): Unit = {

    val group: SvgElement = MinderDom.svgClassGroup(draw, Pipe.LayerTag)
    draw.appendRootChild(group)

    val ex = 10.0
    var wy = 0.0
    group.headerText(draw, Event.Name, ex, wy)

    wy += 20
    group.headerText(draw, Venue.Building + " -- " + Venue.Name, ex, wy)

    wy += 20.0
    group.text(draw, title, ex, wy, Pipe.Color, "14")

    pipeDraw(draw, group, ex, wy)
  }


  def pipeDraw(draw: Draw, group: SvgElement, ex: Double, wy: Double): Double = {
    println(s"${id}:  length: ${length}   internalDraw( ex: ${ex}, wy: ${wy}")
    numberLuminaires()

    var localWy = wy

    var crosspipeMaxLength = 0.0

    //    localWy += drawPipeText(draw, group, ex, localWy)
    listChildren().foreach(child => {
      crosspipeMaxLength = Math.max(crosspipeMaxLength, child.length)
      //      localWy += child.drawPipeText(draw, group, ex, localWy)
    })

    localWy += 40
    //    val keyText = "* detail lines start with \"<unit number>: [<dimmer or address>] (<channel>)\"."
    //    group.text(draw, keyText, ex, localWy, Luminaire.COLOR)

    val drawBox: Point = new Point(ex + crosspipeMaxLength / 2, localWy, 0.0)
    val verticalPipeBottomY = localWy + length
    if (based) {
      group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color)
    }
    else {
      group.rectangle(draw, drawBox.x(), wy - attachmentPosition, length, Pipe.Diameter, Pipe.Color)
    }

    val normalDrawBox: Point = new Point(boxOrigin.x() + offsetX, boxOrigin.y() + offsetY, boxOrigin.z())
    val offset: Point = new Point(
      normalDrawBox.x() - drawBox.x(),
      normalDrawBox.y() - (wy - attachmentPosition),
      normalDrawBox.z() - normalDrawBox.z())
    for (luminaire <- IsClampList) {
      luminaire.setStandAloneOffset(offset)
      luminaire.dom(draw, View.PLAN)
    }

    listChildren().foreach(child => {
      localWy += child.pipeDraw(draw, group, ex, verticalPipeBottomY)
    })

    localWy
  }

  def textDraw(draw: Draw, group: SvgElement, ex: Double, wy: Double): Double = {
    println(s"internalDraw( ex: ${ex}, wy: ${wy}")
    numberLuminaires()

    var localWy = wy

    var crosspipeMaxLength = 0.0

    //    localWy += drawPipeText(draw, group, ex, localWy)
    listChildren().foreach(child => {
      crosspipeMaxLength = Math.max(crosspipeMaxLength, child.length)
      //      localWy += child.drawPipeText(draw, group, ex, localWy)
    })

    localWy += 20
    //    val keyText = "* detail lines start with \"<unit number>: [<dimmer or address>] (<channel>)\"."
    //    group.text(draw, keyText, ex, localWy, Luminaire.COLOR)

    val drawBox: Point = new Point(ex + crosspipeMaxLength / 2, localWy, 0.0)
    val verticalPipeBottomY = localWy + length
    if (based) {
      group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color)
    }
    else {
      group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color)
    }

    val normalDrawBox: Point = new Point(boxOrigin.x() + offsetX, boxOrigin.y() + offsetY, boxOrigin.z())
    val offset: Point = new Point(
      normalDrawBox.x() - drawBox.x(),
      normalDrawBox.y() - drawBox.y(),
      normalDrawBox.z() - normalDrawBox.z())
    for (luminaire <- IsClampList) {
      luminaire.setStandAloneOffset(offset)
      luminaire.dom(draw, View.PLAN)
    }

    listChildren().foreach(child => {
      localWy += child.pipeDraw(draw, group, ex, verticalPipeBottomY)
    })

    localWy
  }

  private def drawPipeText(draw: Draw, group: SvgElement, ex: Double, wy: Double): Double = {
    var localWy = wy + 20
    group.text(draw, s"Luminaire detail for pipe ${id}:", ex, localWy, Pipe.Color)

    localWy += 20
    var totalWeight = 0.0
    for (luminaire <- sortedClampList) {
      localWy += 15
      val dmx = if (!luminaire.address().isEmpty) {
        luminaire.address()
      } else {
        luminaire.dimmer()
      }
      val kind = luminaire.`type`()

      // TODO: Text should all be drawn at the bottom and have a pipe id prepended to the line
      //      Or possibly a header with a pipe name, followed by all the text that applys to it.
      var text = luminaire.unit().toString +
        ": [" + dmx +
        "] (" + luminaire.channel() + ") " +
        kind +
        "  --  (location: " + luminaire.location.feet() + ")" +
        "  --  " + luminaire.info()
      if (!luminaire.target().isEmpty) {
        text += "  --  target is " + luminaire.target()
      }
      if (!luminaire.color().isEmpty) {
        text += "  --  [color: " + luminaire.color() + "]"
      }

      val luminaireDefinition = LuminaireDefinition.Select(kind)
      if (null == luminaireDefinition) {
        throw new ReferenceException("Unable to find definition for " + kind)
      }
      val weight = luminaireDefinition.weight()
      text += "  -- weight: " + weight.toString
      totalWeight += weight

      group.text(draw, text, ex, localWy, Luminaire.COLOR)
    }

    // TODO: The weight of the pipes should be included in the count:

    localWy += 20
    val weightText = "Total weight on this pipe is " + totalWeight + "."
    group.text(draw, weightText, ex, localWy, Luminaire.COLOR)

    localWy
  }

  // Members declared in com.mobiletheatertech.plot.MinderDom
  def dom(draw: Draw, mode: View): Unit = {
    if (mode == View.TRUSS) return


    //    println(s"Drawing PipeBase for $id")
    numberLuminaires()


    //    val height: Double = Venue.Height() - boxOrigin.z()

    val drawBox: Point = new Point(boxOrigin.x() + offsetX, boxOrigin.y() + offsetY, boxOrigin.z())

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
      case _ => throw new Exception("In Pipe.dom, id = " + id + " -- Invalid View.")
    }
    //        group.appendChild(pipeRectangle);

    def domPlan(): Unit = {
      if (based) {
        group.circle(draw, drawBox.x() + Pipe.Radius, drawBox.y() + Pipe.Radius, Pipe.Radius, Pipe.Color)
        group.text(draw, id, drawBox.x() - 18.0, drawBox.y() + 26.0, Pipe.Color)
      } else if (90.0 == horizontalOrientation) {
        //        group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color)
        // Todo: drawBox is not set corectly
        group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color)
        //        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color)
      } else {
        group.rectangle(draw, drawBox.x(), drawBox.y(), length, Pipe.Diameter, Pipe.Color)
        //        group.rectangle(draw, drawBox.x(), drawBox.y(), Pipe.Diameter, length, Pipe.Color)
        //        group.text(draw, id, 38.0, drawBox.y() + Pipe.Diameter, Pipe.Color)
      }
    }
  }


}

object Pipe {

  object ParentType extends Enumeration {
    type PipeParentType = Value

    val Unknown = Value
  }

  val bar = ParentType.Unknown

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

  def Select(id: String): Pipe = {
    return LinearSupportsClamp.Select(id).asInstanceOf[Pipe]
  }

}