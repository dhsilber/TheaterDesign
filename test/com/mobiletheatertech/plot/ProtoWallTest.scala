package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode
import java.util.ArrayList

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.{Element, Node, NodeList}
import java.util.TreeSet

/**
  * Created by DHS on 8/6/16.
  */
class ProtoWallTest {
  class Proto( element: Element ) extends ProtoWall( element: Element ) {
    override def drawLine( draw: Draw, segmentStart: Point, segmentEnd: Point ): Unit = {
      val lineElement = draw.line( draw,
        segmentStart.x, segmentStart.y, segmentEnd.x, segmentEnd.y, "purple" )
      lineElement.attribute( "stroke-width", "2" )
    }
  }
  
  private[plot] var element: Element = null
  private[plot] var wallElement2: Element = null
  private[plot] var wallElement3: Element = null
  private[plot] val x1: Double = 12.0
  private[plot] val y1: Double = 34.0
  private[plot] val x2: Double = 19.0
  private[plot] val y2: Double = 30.0
  private[plot] val x3: Double = 78.0
  private[plot] val y3: Double = 89.0

  private[ plot ] var wallEndWithOpeningElement: Element = null
  private[ plot ] val wwoX1: Double = 10.0
  private[ plot ] val wwoX2: Double = 155.9
  private[ plot ] val wwoY: Double = 57.0

  private[ plot ] var openingElement: Element = null
  private[ plot ] val openHeight: Double = 7.3
  private[ plot ] val openWidth: Double = 17.0
  private[ plot ] val openStart: Double = 15.4

  private[ plot ] var openingElement2: Element = null
  private[ plot ] val open2Height: Double = 7.3
  private[ plot ] val open2Width: Double = 17.0
  private[ plot ] val open2Start: Double = 55.4

  @Test
  @throws[Exception]
  def isA {
    val instance: ProtoWall = new ProtoWall( element )
    assert(classOf[Elemental].isInstance(instance))
    assert(classOf[ElementalLister].isInstance(instance))
    assert(classOf[Verifier].isInstance(instance))
    assert(classOf[Layerer].isInstance(instance))
    assert(classOf[MinderDom].isInstance(instance))
    assertFalse(classOf[UniqueId].isInstance(instance))

    assertTrue( classOf[ Populate ].isInstance( instance ) )
    assertFalse(classOf[IsClamp].isInstance(instance))
    assertFalse(classOf[Legendable].isInstance(instance))
  }

  @Test
  @throws[ Exception ]
  def storesAttributes {
    val proto = new Proto( element )

//    assertEquals( proto.x1, x1 )
//    assertEquals( proto.y1, y1 )
//    assertEquals( proto.x2, x2 )
//    assertEquals( proto.y2, y2 )

    assertEquals( proto.start, new Point( x1, y1, 0.0 ) )
    assertEquals( proto.end, new Point( x2, y2, 0.0 ) )
  }

  @Test( expectedExceptions = Array( classOf[ AttributeMissingException ] ), expectedExceptionsMessageRegExp = "Wall " +
    "instance is missing required 'x1' attribute." )
  @throws[ Exception ]
  def noX1 {
    element.removeAttribute( "x1" )
    new Wall( element )
  }

  @Test( expectedExceptions = Array( classOf[ AttributeMissingException ] ), expectedExceptionsMessageRegExp = "Wall " +
    "instance is missing required 'y1' attribute." )
  @throws[ Exception ]
  def noY1 {
    element.removeAttribute( "y1" )
    new Wall( element )
  }

  @Test( expectedExceptions = Array( classOf[ AttributeMissingException ] ), expectedExceptionsMessageRegExp = "Wall " +
    "instance is missing required 'x2' attribute." )
  @throws[ Exception ]
  def noX2 {
    element.removeAttribute( "x2" )
    new Wall( element )
  }

  @Test( expectedExceptions = Array( classOf[ AttributeMissingException ] ), expectedExceptionsMessageRegExp = "Wall " +
    "instance is missing required 'y2' attribute." )
  @throws[ Exception ]
  def noY2 {
    element.removeAttribute( "y2" )
    new Wall( element )
  }

  @Test
  def openingListHasComparator {
    val wall = new Proto( element )
    val list = wall.openingList
    assertNotNull( list )
    assertNotNull( list.comparator )
    assertSame( list.comparator.getClass, new OpeningComparator().getClass )
  }

  @Test def tagCallbackOpening {
    val wall = new Proto( element )
    assertTrue( wall.tags.contains( Opening.Tag ) )
    assertEquals( wall.tags.size, 1 )
  }

  @Test def populateChildrenOpeningNone {
    val wall = new Proto( element )

    val list = ElementalLister.List

//    val venueInstance: ElementalLister = list.get( 0 )
//    assert( classOf[ MinderDom ].isInstance( venueInstance ) )
//    assert( classOf[ Venue ].isInstance( venueInstance ) )

    val wallInstance: ElementalLister = list.get( 0 )
    assert( classOf[ MinderDom ].isInstance( wallInstance ) )
    assert( classOf[ ProtoWall ].isInstance( wallInstance ) )

    assertEquals( list.size, 1 )
    assertSame( wallInstance, wall )
    assertEquals( wall.openingList.size, 0 )
  }

  @Test def populateChildrenOpeningOne {
    wallEndWithOpeningElement.appendChild( openingElement )
    val wall = new Proto( wallEndWithOpeningElement )

    val list: ArrayList[ ElementalLister ] = ElementalLister.List

//    val venueInstance: ElementalLister = list.get( 0 )
//    assert( classOf[ MinderDom ].isInstance( venueInstance ) )
//    assert( classOf[ Venue ].isInstance( venueInstance ) )

    val wallInstance: ElementalLister = list.get( 0 )
    assert( classOf[ MinderDom ].isInstance( wallInstance ) )
    assert( classOf[ ProtoWall ].isInstance( wallInstance ) )

    assertEquals( list.size, 1 )
    assertSame( wallInstance, wall )

    assertEquals( wall.openingList.size, 1 )
  }

  @Test def populateChildrenOpeningTwo {
    wallEndWithOpeningElement.appendChild( openingElement )
    wallEndWithOpeningElement.appendChild( openingElement2 )

    checkTwoChildOpenings
  }

  @Test def populateChildrenOpeningTwoReversed {
    wallEndWithOpeningElement.appendChild( openingElement2 )
    wallEndWithOpeningElement.appendChild( openingElement )

    checkTwoChildOpenings
  }

  private[ plot ] def checkTwoChildOpenings {
    val wall = new Proto( wallEndWithOpeningElement )

    val list: ArrayList[ ElementalLister ] = ElementalLister.List

//    val venueInstance: ElementalLister = list.get( 0 )
//    assert( classOf[ MinderDom ].isInstance( venueInstance ) )
//    assert( classOf[ Venue ].isInstance( venueInstance ) )

    val wallInstance: ElementalLister = list.get( 0 )
    assert( classOf[ MinderDom ].isInstance( wallInstance ) )
    assert( classOf[ ProtoWall ].isInstance( wallInstance ) )

    assertEquals( list.size, 1 )
    assertSame( wallInstance, wall )

    assertEquals( wall.openingList.size, 2 )
    val iterator = wall.openingList.iterator
    val one: Opening = iterator.next
    assertNotNull( one )
    val two: Opening = iterator.next
    assertNotNull( two )
    assertNotSame( one, two )
    assert( one.start < two.start )
  }

  @Test
  @throws[ Exception ]
  def domPlan {
    val draw: Draw = new Draw
    draw.establishRoot
    val flat = new Proto( element )
    val prelist: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( prelist.getLength, 0 )

    flat.dom( draw, View.PLAN )

    val list: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( list.getLength, 1 )

    val node: Node = list.item( 0 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )

    val foundElement: Element = node.asInstanceOf[ Element ]
    val foundX1 = foundElement.getAttribute( "x1" )
    assertEquals( foundX1, x1.toString )
    assertEquals( foundElement.getAttribute( "y1" ), y1.toString )
    assertEquals( foundElement.getAttribute( "x2" ), x2.toString )
    assertEquals( foundElement.getAttribute( "y2" ), y2.toString )
    assertEquals( foundElement.getAttribute( "stroke" ), "purple" )
    assertEquals( foundElement.getAttribute( "stroke-width" ), "2" )
  }

  @Test
  @throws[ Exception ]
  def domPlanOpening {
    element = new IIOMetadataNode( Wall.Tag )
    element.setAttribute( "x1", "10" )
    element.setAttribute( "y1", "20" )
    element.setAttribute( "x2", "310" )
    element.setAttribute( "y2", "420" )

    openingElement = new IIOMetadataNode( Opening.Tag )
    openingElement.setAttribute( "start", "5.0" )
    openingElement.setAttribute( "width", "5.0" )
    openingElement.setAttribute( "height", "72.0" )

    element.appendChild( openingElement )

    val draw: Draw = new Draw
    draw.establishRoot
    val wall = new Proto( element )
    wall.verify

    val prelist: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( prelist.getLength, 0 )

    wall.dom( draw, View.PLAN )

    val list: NodeList = draw.root.getElementsByTagName( "line" )

    var node: Node = list.item( 0 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )
    var wallElement: Element = node.asInstanceOf[ Element ]
    assertEquals( wallElement.getAttribute( "x1" ), "10.0" )
    assertEquals( wallElement.getAttribute( "y1" ), "20.0" )
    assertEquals( wallElement.getAttribute( "x2" ), "13.0" )
    assertEquals( wallElement.getAttribute( "y2" ), "24.0" )

    node = list.item( 1 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )
    wallElement = node.asInstanceOf[ Element ]
    assertEquals( wallElement.getAttribute( "x1" ), "16.0" )
    assertEquals( wallElement.getAttribute( "y1" ), "28.0" )
    assertEquals( wallElement.getAttribute( "x2" ), "310.0" )
    assertEquals( wallElement.getAttribute( "y2" ), "420.0" )

    assertEquals( list.getLength, 2 )
  }

  @Test
  @throws[ Exception ]
  def domPlanTwoOpenings {
    element = new IIOMetadataNode( Wall.Tag )
    element.setAttribute( "x1", "10" )
    element.setAttribute( "y1", "20" )
    element.setAttribute( "x2", "310" )
    element.setAttribute( "y2", "420" )
    
    openingElement = new IIOMetadataNode( Opening.Tag )
    openingElement.setAttribute( "start", "5.0" )
    openingElement.setAttribute( "width", "5.0" )
    openingElement.setAttribute( "height", "72.0" )
    
    openingElement2 = new IIOMetadataNode( Opening.Tag )
    openingElement2.setAttribute( "start", "50.0" )
    openingElement2.setAttribute( "width", "5.0" )
    openingElement2.setAttribute( "height", "72.0" )
    
    element.appendChild( openingElement )
    element.appendChild( openingElement2 )
    
    val draw: Draw = new Draw
    draw.establishRoot
    val wall = new Proto( element )
    wall.verify

    val prelist: NodeList = draw.root.getElementsByTagName( "line" )
    assertEquals( prelist.getLength, 0 )

    wall.dom( draw, View.PLAN )

    val list: NodeList = draw.root.getElementsByTagName( "line" )

    var node: Node = list.item( 0 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )
    var wallElement: Element = node.asInstanceOf[ Element ]
    assertEquals( wallElement.getAttribute( "x1" ), "10.0" )
    assertEquals( wallElement.getAttribute( "y1" ), "20.0" )
    assertEquals( wallElement.getAttribute( "x2" ), "13.0" )
    assertEquals( wallElement.getAttribute( "y2" ), "24.0" )

    node = list.item( 1 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )
    wallElement = node.asInstanceOf[ Element ]
    assertEquals( wallElement.getAttribute( "x1" ), "16.0" )
    assertEquals( wallElement.getAttribute( "y1" ), "28.0" )
    assertEquals( wallElement.getAttribute( "x2" ), "40.0" )
    assertEquals( wallElement.getAttribute( "y2" ), "60.0" )

    node = list.item( 2 )
    assertEquals( node.getNodeType, Node.ELEMENT_NODE )
    wallElement = node.asInstanceOf[ Element ]
    assertEquals( wallElement.getAttribute( "x1" ), "43.0" )
    assertEquals( wallElement.getAttribute( "y1" ), "64.0" )
    assertEquals( wallElement.getAttribute( "x2" ), "310.0" )
    assertEquals( wallElement.getAttribute( "y2" ), "420.0" )

    assertEquals( list.getLength, 3 )
  }

  @BeforeMethod
  @throws[Exception]
  def setUpMethod {
    TestResets.ElementalListerReset()
    TestResets.ProsceniumReset()
    Venue.Reset()
//    Wall.Reset()
    TestResets.WallReset()
    TestResets.PointReset()

    wallEndWithOpeningElement = new IIOMetadataNode( Opening.Tag )
    wallEndWithOpeningElement.setAttribute( "x1", wwoX1.toString )
    wallEndWithOpeningElement.setAttribute( "y1", wwoY.toString )
    wallEndWithOpeningElement.setAttribute( "x2", wwoX2.toString )
    wallEndWithOpeningElement.setAttribute( "y2", wwoY.toString )

    openingElement = new IIOMetadataNode( Opening.Tag )
    openingElement.setAttribute( "width", openWidth.toString )
    openingElement.setAttribute( "height", openHeight.toString )
    openingElement.setAttribute( "start", openStart.toString )

    openingElement2 = new IIOMetadataNode( Opening.Tag )
    openingElement2.setAttribute( "width", open2Width.toString )
    openingElement2.setAttribute( "height", open2Height.toString )
    openingElement2.setAttribute( "start", open2Start.toString )

    element = new IIOMetadataNode( Opening.Tag )
    element.setAttribute("x1", x1.toString)
    element.setAttribute("y1", y1.toString)
    element.setAttribute("x2", x2.toString)
    element.setAttribute("y2", y2.toString)
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }

}

object ProtoWallTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}