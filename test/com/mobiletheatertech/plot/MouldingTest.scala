package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.Element

/**
  * Created by DHS on 9/24/16.
  */
class MouldingTest {

  var element: Element = null
  val depth = 2.8
  val width = 4.7
  val sideUnsupported = "unsupported side"
  val sideBoth = "both"

  @BeforeMethod
  @throws[Exception]
  def setUpMethod(): Unit = {
    element = new IIOMetadataNode( Moulding.Tag )
    element.setAttribute( "depth", depth.toString )
    element.setAttribute( "width", width.toString )
    element.setAttribute( "side", sideBoth )
  }

  @Test
  @throws[ Exception ]
  def constantTag {
    assertEquals( Moulding.Tag, "moulding" )
  }

  @Test
  @throws[Exception]
  def isA {
    val instance: Moulding = new Moulding( element )
    assertTrue( classOf[ Elemental ].isInstance( instance ) )
    assertFalse( classOf[ ElementalLister ].isInstance( instance ) )
//    assert( classOf[ Verifier ].isInstance(instance) )
//    assert( classOf[ Layerer ].isInstance(instance) )
//    assert( classOf[ MinderDom ].isInstance(instance) )
//    assert( classOf[ ProtoWall ].isInstance(instance) )
//    assertFalse( classOf[ UniqueId ].isInstance(instance) )

//    assertFalse( classOf[ IsClamp ].isInstance(instance) )
//    assertFalse( classOf[ Legendable ].isInstance(instance) )
  }

  @Test
  @throws[ Exception ]
  def storesAttributes {
    val moulding: Moulding = new Moulding( element )

    assertEquals( moulding.depth, depth )
    assertEquals( moulding.width, width )
    assertEquals( moulding.side, sideBoth )
  }

  @Test( expectedExceptions = Array( classOf[ AttributeInvalidException ] ),
    expectedExceptionsMessageRegExp = "Moulding instance has invalid 'side' attribute." )
  @throws[ Exception ]
  def unsupportedSideAttributeValue {
    element.setAttribute( "side", sideUnsupported )
    val moulding: Moulding = new Moulding( element )
  }
}
