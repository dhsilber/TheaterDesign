package com.mobiletheatertech.plot

import java.text.DecimalFormat
import java.util

import org.w3c.dom.{Element, Node, NodeList}

/**
  * Created by DHS on 7/26/16.
  */
class Truss ( element: Element, parent: MinderDom ) extends UniqueId( element )
  with LinearSupportsClamp
  with Populate
  with Legendable
{
  var name = id

  def this( element: Element ) {
    this( element, null )
  }

//  println( "Truss has parent of " + parent.getClass.toString )

  val endAttachments: java.util.ArrayList[ Point ] = new util.ArrayList[Point]()

  if ( null != parent ) {
    println( "Truss has parent of " + parent.getClass.toString )
    if ( parent.isInstanceOf[TrussBase] ) {
      val base = parent.asInstanceOf[ TrussBase ]
      val baseAttachments: Array[ Point ] = base.mountPoints()
      for( baseAttach <- baseAttachments ) {
        println( "Attach: " + baseAttach.asInstanceOf[ Point ].toString )
        endAttachments.add( baseAttach )
      }
      println( "Truss has TrussBase parent.")
    }
  }
//  println( "Past that point" )


  // Configure LinearSupportsClamp to work with a Truss
  override val hasVertex: Boolean = true

  if (Proscenium.Active) {
    throw new InvalidXMLException("Truss not yet supported with Proscenium.")
  }

  val size: Double = getDoubleAttribute( "size" )
  if (12.0 != size && 20.5 != size) {
    throw new KindException("Truss", size)
  }
  val halfSize: Double = size / 2.0

  val length: Double = getDoubleAttribute( "length" )
  val x = getOptionalDoubleAttributeOrNull( "x" )
  val y = getOptionalDoubleAttributeOrNull( "y" )
  val z = getOptionalDoubleAttributeOrNull( "z" )

  var suspend1: Suspend = null
  var suspend2: Suspend = null
  var point1: Point = null
  var point2: Point = null
  var rotation: Double = 0.0
  var overHang: Double = 0.0

  // These are set within the *Processing() logic.
//  override val start: Point = null
  // Note that start is only mutable because I haven't yet updated Truss to be a child of
  // its TrussBase. When I do, make it 'val' in LinearSupportsClamp
  var boxOrigin : Point = null

  var span: Double = 0.0
  var trussBase: TrussBase = null

  process()


//  val foo = null
  val begin: Double = /*if ( crossesProsceniumCenterline() ) start.x          else*/ 0.0
  val end: Double   = /*if ( crossesProsceniumCenterline() ) start.x + length else*/ length

  override def minLocation = begin
  override def maxLocation = end


  tagCallback( Luminaire.LAYERTAG, processLuminaire )
  tagCallback( Halfborough.Tag, processHalfborough )
  tagCallback( Pipe.Tag, processPipe )
//  tagCallback( Cheeseborough.TAG, processCheeseborough )
//  println( "Truss about to populate children")

  populate( element )

  def parentParse(): TrussBase = {
//    if ( classOf[ PipeBase ].isInstance( parent ) )
//      parent.asInstanceOf[ PipeBase ]
//    else
      parent.asInstanceOf[ TrussBase ]
  }

  def processLuminaire(element: Element ): Unit = {
//    println( "Truss about to create Luminaire")
    element.setAttribute( "on", id )
    val light: Luminaire = new Luminaire(element)
    try {
      hang(light, light.location() )
    }
    catch {
      case exception: MountingException =>
        throw new MountingException (
          "Truss (" + id + ") unit '" + light.unit() + "' has " + exception.getMessage )
      //      case exception: Exception =>
      //        throw new Exception( exception.getMessage, exception.getCause )
    }
//    println( "Truss completed creating Luminaire")
  }

  def processHalfborough(element: Element ): Unit = {
//    println( "Truss about to create Halfborough")
    element.setAttribute( "on", id )
    val half = new Halfborough( element, this )
//    val distanceFromOrigin = locationDistance( light.locationValue() )
//    try {
//      hang(light, distanceFromOrigin.toDouble )
//    }
//    catch {
//      case exception: MountingException =>
//        throw new MountingException (
//          "Truss (" + id + ") unit '" + light.unit() + "' has " + exception.getMessage )
//      //      case exception: Exception =>
//      //        throw new Exception( exception.getMessage, exception.getCause )
//    }
//    println( "Truss completed creating Halfborough")
  }

  def processPipe(element: Element ): Unit = {
//    println( "Truss about to create Pipe")
    element.setAttribute( "on", id )
    val pipe = new Pipe( element )
//    val distanceFromOrigin = locationDistance( light.locationValue() )
//    try {
//      hang(light, distanceFromOrigin.toDouble )
//    }
//    catch {
//      case exception: MountingException =>
//        throw new MountingException (
//          "Truss (" + id + ") unit '" + light.unit() + "' has " + exception.getMessage )
//      //      case exception: Exception =>
//      //        throw new Exception( exception.getMessage, exception.getCause )
//    }
//    println( "Truss completed creating Pipe")
  }


//  def crossesProsceniumCenterline(): Boolean = {
//    if (Proscenium.Active())
//      if ( (start.x < 0) && (start.x + length > 0) )
//        true
//      else
//        false
//    else
//      false
//  }

  def process(): Unit={//( Boolean, Boolean, Boolean ) = {

    def parentParse(): MinderDom = {
      parent
    }

    def baseProcessing(): TrussBase = {
      val base: TrussBase = parent.asInstanceOf[TrussBase]
      if ( null != base ) {
        start = new Point( base.x, base.y, 0.0 )

        boxOrigin = new Point( start.x - 1, start.y - 1, start.z )

        val space: Space = new Space( boxOrigin, Pipe.Diameter, Pipe.Diameter, length )
        if ( ! Venue.Contains( space ) ) {
          throw new LocationException(
            "Truss (" + id + ") should not extend beyond the boundaries of the venue.")
        }
      }

      base
    }

    @throws[AttributeMissingException]
    @throws[DataException]
    @throws[InvalidXMLException]
    @throws[ReferenceException]
    def suspended(): Boolean =
    {
      val suspendList: NodeList = element.getElementsByTagName("suspend")
      if (0 == suspendList.getLength) {
        false
      }
      else if (2 == suspendList.getLength) {
        suspend1 = instantiateSuspend(suspendList, 0)
        suspend2 = instantiateSuspend(suspendList, 1)
        if (suspend1.locate.x > suspend2.locate.x) {
          val temp: Suspend = suspend1
          suspend1 = suspend2
          suspend2 = temp
        }
        point1 = suspend1.locate
        point2 = suspend2.locate
        val slope: Double = point1.slope(point2)
        rotation = Math.toDegrees(Math.atan(slope))
        span = point1.distance(point2)
        overHang = (length - span) / 2
        suspend1.location(overHang)
        suspend2.location(overHang + span)
        true
      }
      else {
        System.err.println("Found " + suspendList.getLength + " suspend child nodes")
        throw new InvalidXMLException(
          "Truss (" + id + ") must have position, one trussbase, or two suspend children." )
        false
      }
    }

    @throws[AttributeMissingException]
    @throws[DataException]
    @throws[InvalidXMLException]
    @throws[ReferenceException]
    def instantiateSuspend( suspendList: NodeList, index: Int): Suspend =
    {
      val suspendNode: Node = suspendList.item(index)
      if (suspendNode.getNodeType == Node.ELEMENT_NODE) {
        return new Suspend(suspendNode.asInstanceOf[Element])
      }
      null
    }

    def positionProcessing(): Boolean = {
      try {
        start = new Point(x, y, z)
      }
      catch {
        case npe: NullPointerException =>
          throw new InvalidXMLException(
            "Truss (" + id + ") must have position, one trussbase, or two suspend children." )
      }
      true
    }
//    (based, suspended, positioned)  =
      trussBase = baseProcessing()
    if ( null != trussBase ) {
      based = true
//      suspended = false
      positioned = false
      return ( true, false, false )
    }

    if( suspended() ) {
      return ( false, true, false )
    }

    ( false, false, positionProcessing() )
  }

//  override
  def mountableLocation(location: Location): Point = {
    val distance = location.distance
    val vertex = location.vertex

    if( ! distance.valid  ) {
      throw new MountingException( "Location specified does not contain a valid distance." )
    }

    if ( ! vertex.valid )
      throw new InvalidXMLException(
        "Truss (" + id + ") location does not include a valid vertex.")

    if ( based ) {
      if ( ! distance.valid ) {
        val vertexOrdinal = vertex.value.toInt - 97
        val lowerPoint = trussBase.mountPoints()( vertexOrdinal )
        return new Point( lowerPoint.x(), lowerPoint.y(), lowerPoint.z() + length )
      }
      else {
      var northOffset: Double = halfSize * -1
      var westOffset: Double = halfSize * -1
      vertex.value match {
        case 'a' =>
        case 'b' =>
          westOffset *= -1
        case 'c' =>
          northOffset *= -1
        case 'd' =>
          northOffset *= -1
          westOffset *= -1
        case _ =>
          throw new InvalidXMLException(
            "Truss (" + id + ") location does not include a valid vertex." )
      }
      return new Point( x + westOffset, y + northOffset, z + distance.value )
      }
    }
    else {
      var offset: Double = halfSize
      vertex.value match {
        case 'a' | 'c' =>
          offset *= -1
        case 'b' | 'd' =>
        case _ =>
          throw new InvalidXMLException(
            "Truss (" + id + ") location does not include a valid vertex.")
      }
      if (positioned) {
        var verticalOffset: Double = halfSize
        vertex.value match {
          case 'a' | 'b' =>
          case 'c' | 'd' =>
            verticalOffset *= -1
          case _ =>
            throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.")
        }
        return new Point(x - length / 2 + distance.value, y + offset, z + verticalOffset)
      }
      else {
        if (null == suspend1) {
          throw new InvalidXMLException("suspend1 is null")
        }
        val point: Point = suspend1.locate
        var verticalOffset: Double = 0.0
        vertex.value match {
          case 'a' | 'b' =>
          case 'c' | 'd' =>
            verticalOffset += size
          case _ =>
            throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.")
        }
        return new Point(point.x - overHang + distance.value, point.y + offset, point.z - verticalOffset)
      }
    }
  }

//  override
  def suspensionPoints(): String = {
    if (positioned) {
      return "Truss is positioned."
    }
    else if (based) {
      return "Truss is set on end, on a trussbase."
    }
    else if (suspended) {
      return "Truss is suspended at " + overHang + " and at " + (length - overHang)
    }
    else {
      return "Cannot figure out how this Truss is held up"
    }
  }

//  override
  def totalSuspendLoads(): String = {
    val fourPlaces: DecimalFormat = new DecimalFormat("###.####")
    return " " + fourPlaces.format(suspend1.load) + " on " + suspend1.refId + ". " +
      fourPlaces.format(suspend2.load) + " on " + suspend2.refId + "."
  }

  override def rotatedLocation(location: Location): Place = {
    if (positioned) {
      return new Place(mountableLocation(location), start, 0.0)
    }
    else if (based) {
      val transformX: Double = trussBase.x + SvgElement.OffsetX
      val transformY: Double = trussBase.y + SvgElement.OffsetY
      val origin: Point = new Point(transformX, transformY, 0.0)
      return new Place(mountableLocation(location), origin, trussBase.rotation)
    }
    else {
      val transformX: Double = point1.x + SvgElement.OffsetX
      val transformY: Double = point1.y + SvgElement.OffsetY
      val origin: Point = new Point(transformX, transformY, point1.z)
      return new Place(mountableLocation(location), origin, rotation)
    }
  }

//  override
  def schematicLocation(location: String): PagePoint = ???

  def locationDistance( location: String ): Integer = {
    val distanceString: String = location.substring(1)
    var distance: Integer = null
    try {
      distance = new Integer(distanceString.trim)
    }
    catch {
      case exception: NumberFormatException => {
        throw new InvalidXMLException(
          "Truss (" + id + ") location must include vertex and distance.")
      }
    }

    if (0 > distance || distance > length) {
      throw new MountingException(
        "Truss (" + id + ") does not include location " + distance.toString + ".")
    }
    distance
  }

//  override
  def calculateIndividualLoad(luminaire: Luminaire): String = ???

  override def dom(draw: Draw, mode: View) {
    var trussRectangle: SvgElement = null
    var group: SvgElement = null
    mode match {
      case View.PLAN | View.TRUSS =>
        group = MinderDom.svgClassGroup(draw, Truss.LayerTag)
        draw.appendRootChild(group)
        println( "Truss group: " + group.toString() )

      case _ =>
        return
    }
    mode match {
      case View.PLAN =>
        if (positioned) {
          group.rectangle(draw, x - length / 2, y - size / 2, length, size, Truss.Color )
        }
        else if (null != trussBase) {
          val verticalTruss: SvgElement =
            group.rectangle(draw, start.x - size / 2, start.y - size / 2, size, size, Truss.Color)
          println( "TrussBase verticalTruss: " + verticalTruss.toString() )

          val transformX: Double = start.x + SvgElement.OffsetX
          val transformY: Double = start.y + SvgElement.OffsetY
          val transform: String = "rotate(" + trussBase.rotation + "," + transformX + "," + transformY + ")"
          verticalTruss.attribute("transform", transform)
          verticalTruss.attribute( "class", "Vertical Truss on Base")
          println( "Truss size: " + size.toString() )
          Truss.BaseCountIncrement()
        }
        else {
          val x1: Double = point1.x
          val y1: Double = point1.y
          val xPlan: Double = x1 - overHang
          val yPlan: Double = y1 - size / 2
          trussRectangle = group.rectangle(draw, xPlan, yPlan, length, size, Truss.Color )
          val transformX: Double = x1 + SvgElement.OffsetX
          val transformY: Double = y1 + SvgElement.OffsetY
          trussRectangle.attribute("transform", "rotate(" + rotation + "," + transformX + "," + transformY + ")")
        }
      case _ =>
        return
    }
  }

  override def verify(): Unit = {

  }

  @throws[MountingException]
  def relocate(location: Location): Point = {
//    var y: Double = .0
//    val vertex: Character = location.charAt(0)
//    var offset: Double = size / 2
//    vertex match {
//      case 'a' =>
//        offset *= -1
//      case 'b' =>
//        y = trussCounted * 320 + 80 + size / 2
//        break //todo: break is not supported
//      case 'c' =>
//        offset *= -1
//      case 'd' =>
//        y = trussCounted * 320 + 220 + size / 2
//        break //todo: break is not supported
//      case _ =>
//        throw new MountingException("Truss (" + id + ") location does not include a valid vertex.")
//    }
//    val distanceString: String = location.substring(1)
//    var distance: Double = .0
//    try {
//      distance = new Double(distanceString.trim)
//    }
//    catch {
//      case exception: NumberFormatException => {
//        throw new MountingException("Truss (" + id + ") location not correctly formatted.")
//      }
//    }
//    if (0 > distance || distance > length) {
//      throw new MountingException("Truss (" + id + ") does not include location " + distance.toString + ".")
//    }
//    val result: Point = new Point(size + distance, y + offset, 0.0)
//    return result
        new Point( 0.0, 0.0, 0.0)
  }

  override def legendCountReset(): Unit = ???

  override def domLegendItem(draw: Draw, start: PagePoint): PagePoint = {
    if (0 >= Truss.BaseCount ) {
      return start
    }

    val group: SvgElement = MinderDom.svgClassGroup(draw, Truss.LayerName )
    group.attribute("transform", "translate(" + start.x + "," + start.y + ")")
    draw.appendRootChild(group)
    group.rectangleAbsolute(draw, 0.0, 0.0, 12.0, 12.0, Truss.Color )
    group.rectangleAbsolute(draw, 3.0, 3.0, 6.0, 6.0, Truss.Color )
    var x: Double = Legend.TEXTOFFSET
    val y: Double = 8.0
    group.textAbsolute(draw, "Truss on trussbase", x, y, Legend.TEXTCOLOR)
    x = Legend.QUANTITYOFFSET
    group.textAbsolute(draw, Truss.BaseCount.toString, x, y, Legend.TEXTCOLOR)
    val finish: PagePoint = new PagePoint(start.x, start.y + 9)
    return finish
  }

  override def toString(): String = {
    "Truss { " +
                      "id='" + id + "' " +
      "origin=" + start +
      ", length=" + length +
      " }"
  }
}

object Truss {
  final val LayerTag = "truss"
  final val LayerName = "Trusses"
  final val Color = "dark blue"
  final val Tag = LayerTag

  final var BaseCount: Int = 0
  final var LegendRegistered: Boolean = false

  def Reset(): Unit = {
    BaseCount = 0
    LegendRegistered = false
  }

  def BaseCountIncrement(): Unit = {
    BaseCount += 1
  }
}
