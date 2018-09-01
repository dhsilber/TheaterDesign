package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.{Element, Node, NodeList}

/**
  * Created by DHS on 8/6/16.
  */
class FlatTest {

  var venueElement: Element = null

  val prosceniumX = 175.0
  val prosceniumY = 45.0
  val prosceniumZ = 12.0

  private[plot] var flatElement: Element = null
  private[plot] var flatElement2: Element = null
  private[plot] var flatElement3: Element = null
  private[plot] val x1: Double = 12.0
  private[plot] val y1: Double = 34.0
  private[plot] val x2: Double = 19.0
  private[plot] val y2: Double = 30.0
  private[plot] val x3: Double = 78.0
  private[plot] val y3: Double = 89.0


  @BeforeMethod
  @throws[Exception]
  def setUpMethod {
    Proscenium.Reset()
    TestResets.ElementalListerReset()

    val prosceniumElement = new IIOMetadataNode( Proscenium.Tag )
    prosceniumElement.setAttribute( "width", "48" )
    prosceniumElement.setAttribute( "depth", "14" )
    prosceniumElement.setAttribute( "height", "27" )
    prosceniumElement.setAttribute( "x", prosceniumX.toString )
    prosceniumElement.setAttribute( "y", prosceniumY.toString )
    prosceniumElement.setAttribute( "z", prosceniumZ.toString )

    venueElement = new IIOMetadataNode( Venue.Tag )
    venueElement.setAttribute("room", "Test Name")
    venueElement.setAttribute("width", "350")
    venueElement.setAttribute("depth", "400")
    venueElement.setAttribute("height", "240")
    venueElement.appendChild( prosceniumElement )

    flatElement = new IIOMetadataNode( Flat.Tag )
    flatElement.setAttribute("x1", x1.toString)
    flatElement.setAttribute("y1", y1.toString)
    flatElement.setAttribute("x2", x2.toString)
    flatElement.setAttribute("y2", y2.toString)

    //    flatElement2 = new IIOMetadataNode( Flat.Tag )
    //    flatElement2.setAttribute("x1", x2.toString)
    //    flatElement2.setAttribute("y1", y2.toString)
    //    flatElement2.setAttribute("x2", x3.toString)
    //    flatElement2.setAttribute("y2", y3.toString)
    //
    //    flatElement3 = new IIOMetadataNode( Flat.Tag )
    //    flatElement3.setAttribute("x1", x3.toString)
    //    flatElement3.setAttribute("y1", y3.toString)
    //    flatElement3.setAttribute("x2", x1.toString)
    //    flatElement3.setAttribute("y2", y1.toString)
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }

  @Test
  @throws[ Exception ]
  def constantTag {
    assertEquals( Flat.Tag, "flat" )
  }

  @Test
  @throws[ Exception ]
  def constantColor {
    assertEquals( Flat.Color, "green" )
  }

  @Test
  @throws[Exception]
  def isA {
    val instance: Flat = new Flat( flatElement )
    assert(classOf[Elemental].isInstance(instance))
    assert(classOf[ElementalLister].isInstance(instance))
    assert(classOf[Verifier].isInstance(instance))
    assert(classOf[Layerer].isInstance(instance))
    assert(classOf[MinderDom].isInstance(instance))
    assert(classOf[ProtoWall].isInstance(instance))
    assertFalse(classOf[UniqueId].isInstance(instance))

    assertFalse(classOf[IsClamp].isInstance(instance))
    assertFalse(classOf[Legendable].isInstance(instance))
  }

  @Test
  @throws[ Exception ]
  def storesAttributes {
    val flat = new Flat( flatElement )

    assertEquals( flat.start, new Point( x1, y1, 0.0 ) )
    assertEquals( flat.end, new Point( x2, y2, 0.0 ) )
  }

  @Test
  @throws[ Exception ]
  def storesAttributesProscenium {
    new Venue( venueElement )
    val flat = new Flat( flatElement )

    assertEquals( flat.start, new Point( x1 + prosceniumX, prosceniumY - y1, prosceniumZ ) )
    assertEquals( flat.end, new Point( x2 + prosceniumX, prosceniumY - y2, prosceniumZ ) )
  }

  @Test
  @throws[ Exception ]
  def idUnused {
    val flat: Flat = new Flat( flatElement )

    assertNull( TestHelpers.accessString( flat, "id" ) )
  }

  @Test
  @throws[ Exception ]
  def domPlan {
    val draw: Draw = new Draw
    draw.establishRoot
    val flat = new Flat( flatElement )
    val prelist: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( prelist.getLength, 0 )

    flat.dom( draw, View.PLAN )

    val list: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( list.getLength, 1 )

    val node: Node = list.item( 0 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )

    // Sanity check
    assertEquals( flat.start, new Point( x1, y1, 0.0 ) )
    assertEquals( flat.end, new Point( x2, y2, 0.0 ) )

    val foundElement: Element = node.asInstanceOf[ Element ]
    assertEquals( foundElement.getAttribute( "x1" ), x1.toString )
    assertEquals( foundElement.getAttribute( "y1" ), y1.toString )
    assertEquals( foundElement.getAttribute( "x2" ), x2.toString )
    assertEquals( foundElement.getAttribute( "y2" ), y2.toString )
    assertEquals( foundElement.getAttribute( "stroke" ), Flat.Color )
    assertEquals( foundElement.getAttribute( "stroke-width" ), "3" )
  }

  @Test
  @throws[ Exception ]
  def domPlanProscenium {
    val draw: Draw = new Draw
    draw.establishRoot
    new Venue(venueElement)

    val flat = new Flat( flatElement )
    val prelist: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( prelist.getLength, 0 )

    flat.dom( draw, View.PLAN )

    val list: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( list.getLength, 1 )

    val node: Node = list.item( 0 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )

    // Sanity check
    assertEquals( flat.start, new Point( prosceniumX + x1, prosceniumY - y1, prosceniumZ ) )
    assertEquals( flat.end, new Point( prosceniumX + x2, prosceniumY - y2, prosceniumZ ) )

    val foundElement: Element = node.asInstanceOf[ Element ]
    val startX = x1 + prosceniumX
    val startY = prosceniumY - y1
    val endX = x2 + prosceniumX
    val endY = prosceniumY - y2
    val foundX1 = foundElement.getAttribute( "x1" )
    assertEquals( foundX1, startX.toString )
    assertEquals( foundElement.getAttribute( "y1" ), startY.toString )
    assertEquals( foundElement.getAttribute( "x2" ), endX.toString )
    assertEquals( foundElement.getAttribute( "y2" ), endY.toString )
    assertEquals( foundElement.getAttribute( "stroke" ), Flat.Color )
    assertEquals( foundElement.getAttribute( "stroke-width" ), "3" )
  }

}

object FlatTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[ Exception ]
  def tearDownClass
  {
  }
}