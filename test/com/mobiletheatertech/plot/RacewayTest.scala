package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.{Element, Node, NodeList}

/**
  * Created by dhs on 2/16/17.
  */
class RacewayTest {

  var element: Element = null
  var startx: Double = 13.5
  var starty: Double = 87.1
  var startz: Double = 61.3
  var endx: Double = 14.1
  var endy: Double = 92.9
  var endz: Double = 72.6
  var tailLength = 12.5

  private var prosceniumElement: Element = null
  private val prosceniumX = 200
  private val prosceniumY = 144
  private val prosceniumZ = 12

  @BeforeMethod
  def setUpMethod(): Unit = {
    Proscenium.Reset()
//    TestResets.MountableReset()
    TestResets.LayerReset()
    TestResets.ElementalListerReset()
//    TestResets.LuminaireReset()
    element = null
//    UniqueId.Reset()

    val venueElement = new IIOMetadataNode("venue")
    venueElement.setAttribute("room", "Test Name")
    venueElement.setAttribute("width", "350")
    venueElement.setAttribute("depth", "400")
    venueElement.setAttribute("height", "240")
    new Venue(venueElement)

    prosceniumElement = new IIOMetadataNode("proscenium")
    prosceniumElement.setAttribute("width", "260")
    prosceniumElement.setAttribute("height", "200")
    prosceniumElement.setAttribute("depth", "22")
    prosceniumElement.setAttribute("x", prosceniumX.toString)
    prosceniumElement.setAttribute("y", prosceniumY.toString)
    prosceniumElement.setAttribute("z", prosceniumZ.toString)

    element = new IIOMetadataNode( Raceway.Tag )
    element.setAttribute( "startx", startx.toString )
    element.setAttribute( "starty", starty.toString )
    element.setAttribute( "startz", startz.toString )
    element.setAttribute( "endx", endx.toString )
    element.setAttribute( "endy", endy.toString )
    element.setAttribute( "endz", endz.toString )
  }

  @Test
  def isA(): Unit = {
    val instance = new Raceway( element )

    assert ( classOf[Elemental].isInstance(instance) )
    assert ( classOf[ElementalLister].isInstance(instance) )
    assert ( classOf[Verifier].isInstance(instance) )
    assert ( classOf[Layerer].isInstance(instance) )
    assert ( classOf[MinderDom].isInstance(instance) )
    assertFalse ( classOf[Yokeable].isInstance(instance) )
//    assertTrue ( classOf[Populate].isInstance(instance) )
//    assertTrue ( classOf[Legendable].isInstance(instance) )
  }

  @Test
  def constantTag(): Unit = {
    assertEquals( Raceway.Tag, "raceway" )
  }

  @Test
  def constantColor(): Unit = {
    assertEquals( Raceway.Color, "black" )
  }

  @Test
  def storesAttributes(): Unit = {
    val instance = new Raceway( element )

    assertEquals( instance.startx, startx )
    assertEquals( instance.starty, starty )
    assertEquals( instance.startz, startz )

    assertEquals( instance.endx, endx )
    assertEquals( instance.endy, endy )
    assertEquals( instance.endz, endz )

    assertEquals( instance.tailLength, 0.0 )
  }

  @Test
  def storesOptionalAttributes(): Unit = {
    element.setAttribute( "taillength", tailLength.toString )
    val instance = new Raceway( element )

    assertEquals( instance.startx, startx )
    assertEquals( instance.starty, starty )
    assertEquals( instance.startz, startz )
                          
    assertEquals( instance.endx, endx )
    assertEquals( instance.endy, endy )
    assertEquals( instance.endz, endz )

    assertEquals( instance.tailLength, tailLength )
  }

//  @Test
//  def
//  width & height should not be negative.
//
//    Need to take proscenium into account when drawing.

  @Test
  def domPlan(): Unit = {
    val draw = new Draw()
    draw.establishRoot()
    val raceway = new Raceway( element )

    val existingRectangles = draw.root().getElementsByTagName( "g" )
    assertEquals( existingRectangles.getLength, 1 )

    raceway.dom( draw, View.PLAN )

    val group: NodeList = draw.root().getElementsByTagName( "g" )
    val groupNode: Node = group.item( 1 )
    assertEquals( groupNode.getNodeType, Node.ELEMENT_NODE )
    val groupElement: Element = groupNode.asInstanceOf[ Element ]
    assertEquals(groupElement.getAttribute("class"), Raceway.Tag)
    val list: NodeList = groupElement.getElementsByTagName("rect")
    val node: Node = list.item(0)
    assertEquals(node.getNodeType, Node.ELEMENT_NODE)
    val racewayDrawingElement: Element = node.asInstanceOf[Element]
    assertEquals(racewayDrawingElement.getAttribute("x"), startx.toString)
    assertEquals(racewayDrawingElement.getAttribute("y"), starty.toString)
//    val width: Double = endx - startx
//    val height: Double = endy - starty
    val width: Double = ( endx - startx ).abs
    val height: Double = ( endy - starty ).abs
    assertEquals(racewayDrawingElement.getAttribute("width"), width.toString)
    assertEquals(racewayDrawingElement.getAttribute("height"), height.toString)
    assertEquals(racewayDrawingElement.getAttribute("fill"), "none")
    assertEquals(racewayDrawingElement.getAttribute("stroke"), Raceway.Color )

    assertEquals(list.getLength, 1)

    assertEquals( group.getLength, 2 )
  }

  @Test
  def domPlanNegatives(): Unit = {
    starty = -286
    endy = -294
    startx = 185
    endx = -185

    element.setAttribute( "startx", startx.toString )
    element.setAttribute( "starty", starty.toString )
    element.setAttribute( "endx", endx.toString )
    element.setAttribute( "endy", endy.toString )

    domPlan()
  }

  @Test
  def domProscenium(): Unit = {

    new Proscenium( prosceniumElement )

    starty = -286
    endy = -294
    startx = 185
    endx = -185

    element.setAttribute( "startx", startx.toString )
    element.setAttribute( "starty", starty.toString )
    element.setAttribute( "endx", endx.toString )
    element.setAttribute( "endy", endy.toString )
    val start: Point = Proscenium.LocateIfActive( new Point( startx, starty, startz ) )

    val draw = new Draw()
    draw.establishRoot()
    val raceway = new Raceway( element )

    val existingRectangles = draw.root().getElementsByTagName( "g" )
    assertEquals( existingRectangles.getLength, 1 )

    raceway.dom( draw, View.PLAN )

    val group: NodeList = draw.root().getElementsByTagName( "g" )
    val groupNode: Node = group.item( 1 )
    assertEquals( groupNode.getNodeType, Node.ELEMENT_NODE )
    val groupElement: Element = groupNode.asInstanceOf[ Element ]
    assertEquals(groupElement.getAttribute("class"), Raceway.Tag)
    val list: NodeList = groupElement.getElementsByTagName("rect")
    val node: Node = list.item(0)
    assertEquals(node.getNodeType, Node.ELEMENT_NODE)
    val racewayDrawingElement: Element = node.asInstanceOf[Element]
    assertEquals(racewayDrawingElement.getAttribute("x"), start.x.toString)
    assertEquals(racewayDrawingElement.getAttribute("y"), start.y.toString)
    //    val width: Double = endx - startx
    //    val height: Double = endy - starty
    val width: Double = ( endx - startx ).abs
    val height: Double = ( endy - starty ).abs
    assertEquals(racewayDrawingElement.getAttribute("width"), width.toString)
    assertEquals(racewayDrawingElement.getAttribute("height"), height.toString)
    assertEquals(racewayDrawingElement.getAttribute("fill"), "none")
    assertEquals(racewayDrawingElement.getAttribute("stroke"), Raceway.Color )

    assertEquals(list.getLength, 1)

    assertEquals( group.getLength, 2 )
  }
}
