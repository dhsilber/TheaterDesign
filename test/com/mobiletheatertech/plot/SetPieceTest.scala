package com.mobiletheatertech.plot

package com.mobiletheatertech.plot

import java.util

import javax.imageio.metadata.IIOMetadataNode
//import org.testng.Assert._
//import org.testng.annotations._
import org.testng.Assert._
import org.testng.annotations.{Test, _}
import org.w3c.dom.{Element, Node, NodeList}

class SetPieceTest {

  var setPieceElement: Element = null;
  val x = 1.2
  val y = 1.3

  var flatElement: Element = null
  private[plot] val x1 = 1.1
  private[plot] val y1 = 2.2
  private[plot] val x2 = 3.3
  private[plot] val y2 = 4.4

  var setPlatformElement: Element = null
  val platformX = 5.5
  val platformY = 6.6
  
  var neutralSetPlatformElement: Element = null
  var neutralFlatElement: Element = null

  var shapeElement: Element = null
  val r = 7.7

  @BeforeMethod
  @throws[Exception]
  def setUpMethod: Unit = {
    TestResets.ElementalListerReset()

    setPieceElement = new IIOMetadataNode( SetPiece.Tag )
    setPieceElement.setAttribute( "x", x.toString )
    setPieceElement.setAttribute( "y", y.toString )

    flatElement = new IIOMetadataNode( Flat.Tag )
    flatElement.setAttribute("x1", x1.toString)
    flatElement.setAttribute("y1", y1.toString)
    flatElement.setAttribute("x2", x2.toString)
    flatElement.setAttribute("y2", y2.toString)

    setPlatformElement = new IIOMetadataNode( SetPlatform.Tag )
    setPlatformElement.setAttribute( "x", platformX.toString )
    setPlatformElement.setAttribute( "y", platformY.toString )

    neutralFlatElement = new IIOMetadataNode( Flat.Tag )
    neutralFlatElement.setAttribute("x1", "0" )
    neutralFlatElement.setAttribute("y1", "0" )
    neutralFlatElement.setAttribute("x2", "0" )
    neutralFlatElement.setAttribute("y2", "0" )

    neutralSetPlatformElement = new IIOMetadataNode( SetPlatform.Tag )
    neutralSetPlatformElement.setAttribute( "x", "0" )
    neutralSetPlatformElement.setAttribute( "y", "0" )

    shapeElement = new IIOMetadataNode( Shape.Tag )
    shapeElement.setAttribute( "circle", r.toString )
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }

  @Test
  @throws[ Exception ]
  def constantTag {
    assertEquals( SetPiece.Tag, "setpiece" )
  }

  @Test
  @throws[ Exception ]
  def constantColor {
//    assertEquals( Flat.Color, "green" )
  }

  @Test
  @throws[Exception]
  def isA {
    setPieceElement.appendChild( flatElement )
    val instance: SetPiece = new SetPiece( setPieceElement )
    assert(classOf[Elemental].isInstance(instance))
    assert(classOf[ElementalLister].isInstance(instance))
//    assert(classOf[Verifier].isInstance(instance))
//    assert(classOf[Layerer].isInstance(instance))
//    assert(classOf[MinderDom].isInstance(instance))
//    assert(classOf[ProtoWall].isInstance(instance))
//    assertFalse(classOf[UniqueId].isInstance(instance))

    assert(classOf[Populate].isInstance(instance))
//    assertFalse(classOf[IsClamp].isInstance(instance))
//    assertFalse(classOf[Legendable].isInstance(instance))
  }

  @Test
  @throws[ Exception ]
  def storesAttributes {
    setPieceElement.appendChild( flatElement )
    val setPiece = new SetPiece( setPieceElement )

    assertEquals( setPiece.origin, new Point( x, y, 0.0 ) )
//    val flat = new Flat( flatElement )
//
//    assertEquals( flat.start, new Point( x1, y1, 0.0 ) )
//    assertEquals( flat.end, new Point( x2, y2, 0.0 ) )
  }

  @Test
  @throws[ Exception ]
  def idUnused {
//    val flat: Flat = new Flat( flatElement )
//
//    assertNull( TestHelpers.accessString( flat, "id" ) )
  }

  @Test
  def countPopulateTags(): Unit = {
    setPieceElement.appendChild( flatElement )
    val setPiece = new SetPiece( setPieceElement )
    assertEquals( setPiece.populateTags.size, 2 )
  }

  @Test
  def tagCallbackRegisteredFlat(): Unit = {
    setPieceElement.appendChild( flatElement )
    val setPiece = new SetPiece( setPieceElement )
    assertTrue( setPiece.populateTags.contains( Flat.Tag ))
  }

  @Test
  def tagCallbackRegisteredSetPlatform(): Unit = {
    setPieceElement.appendChild( flatElement )
    val setPiece = new SetPiece( setPieceElement )
    assertTrue( setPiece.populateTags.contains( SetPlatform.Tag ))
  }

  @Test
  def populateChildrenFlat(): Unit = {
    setPieceElement.appendChild( neutralFlatElement )

    val list: util.ArrayList[ElementalLister] = ElementalLister.List
    assertEquals( list.size, 0 )

    new SetPiece( setPieceElement )

    assertEquals(list.size, 2)

    val setPiece: ElementalLister = list.get(0)
    assertNotNull( setPiece )
    //    assert(classOf[MinderDom].isInstance( setPiece ))
    assert(classOf[SetPiece].isInstance( setPiece ))

    assert( classOf[Flat].isInstance( list.get(1) ))
    val flat: Flat = list.get(1).asInstanceOf[Flat]
    assertEquals( flat.start, new Point( x, y, 0.0 ) )
  }

  @Test
  def populateChildrenSetPlatform(): Unit = {
    setPieceElement.appendChild( neutralSetPlatformElement )
    neutralSetPlatformElement.appendChild( shapeElement )

    val list: util.ArrayList[ElementalLister] = ElementalLister.List
    assertEquals( list.size, 0 )

    new SetPiece( setPieceElement )

    assertEquals(list.size, 2)

    val setPiece: ElementalLister = list.get(0)
    assertNotNull( setPiece )
    //    assert(classOf[MinderDom].isInstance( setPiece ))
    assert(classOf[SetPiece].isInstance( setPiece ))

    assert( classOf[SetPlatform].isInstance( list.get(1) ))
    val setPlatform: SetPlatform = list.get(1).asInstanceOf[SetPlatform]
    assertEquals( setPlatform.x, x )
    assertEquals( setPlatform.y, y )
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "SetPiece at \\(1.2, 1.3\\) must have content.")
  def noChildren(): Unit = {
    new SetPiece( setPieceElement )
  }

  @Test
  @throws[ Exception ]
  def domPlan: Unit = {
  }

}

