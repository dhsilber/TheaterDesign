package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.Element

/**
  * Created by dhs on 11/29/16.
  */
class HalfboroughTest {

  var element: Element = null
  val id = "foo"
  val on = "bar"
  val location = "d"

  @BeforeMethod
  @throws[Exception]
  def setUpMethod(): Unit = {
    Halfborough.Reset()

    element = new IIOMetadataNode( Halfborough.Tag )
    element.setAttribute( "id", id )
    element.setAttribute( "on", on )
    element.setAttribute( "location", location )
  }

  @Test
  @throws[ Exception ]
  def constantTag(): Unit = {
    assertEquals( Halfborough.Tag, "halfborough" )
  }

  @Test
  @throws[Exception]
  def isA() {
    val instance: Halfborough = new Halfborough( element )
    assertTrue( classOf[ Elemental ].isInstance( instance ) )
    assertTrue( classOf[ ElementalLister ].isInstance( instance ) )
    assert( classOf[ Verifier ].isInstance(instance) )
    assert( classOf[ Layerer ].isInstance(instance) )
    assert( classOf[ MinderDom ].isInstance(instance) )
    assert( classOf[ Halfborough ].isInstance(instance) )
    //    assertFalse( classOf[ UniqueId ].isInstance(instance) )

    assert( classOf[ Gripper ].isInstance(instance) )

    //    assertFalse( classOf[ IsClamp ].isInstance(instance) )
    //    assertFalse( classOf[ Legendable ].isInstance(instance) )
  }

  @Test
  @throws[ Exception ]
  def storesInitialAttributes() {
    val instance: Halfborough = new Halfborough( element )

    assertEquals( instance.id, id )
    assertEquals( instance.on, on )
    assertEquals( instance.location, location )
  }

  @Test
  @throws[ Exception ]
  def storesAttachment() {
    val instance: Halfborough = new Halfborough( element )

    assertSame( instance, Attachment.Find( id ) )
  }

//  @Test
//  @throws[ Exception ]
//  def findsReferencedHalfborough() {
//    val instance: Halfborough = new Halfborough( element )
//    val reference: Halfborough = new Halfborough( referringElement )
//
//    assertSame( instance, reference )
//  }

//  @Test( expectedExceptions = Array( classOf[ AttributeInvalidException ] ),
//    expectedExceptionsMessageRegExp = "Moulding instance has invalid 'side' attribute." )
//  @throws[ Exception ]
//  def unsupportedSideAttributeValue {
//    element.setAttribute( "side", sideUnsupported )
//    val moulding: Moulding = new Moulding( element )
//  }
}
