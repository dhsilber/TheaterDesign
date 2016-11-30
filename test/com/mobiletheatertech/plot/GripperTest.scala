package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.Element

/**
  * Created by dhs on 11/30/16.
  */
class GripperTest {

  var element: Element = null
  val id = "foo"
  val on = "bar"
  val location = "d"

  @BeforeMethod
  @throws[Exception]
  def setUpMethod(): Unit = {
  }

//  @Test
//  @throws[ Exception ]
//  def constantTag(): Unit = {
//    assertEquals( Halfborough.Tag, "halfborough" )
//  }

  @Test
  @throws[Exception]
  def isA() {
    class foo extends Gripper {

    }
    val instance: foo = new foo( )
    assertTrue( classOf[ HasID ].isInstance( instance ) )
    assertTrue( classOf[ Gripper ].isInstance( instance ) )
  }

//  @Test
//  @throws[ Exception ]
//  def storesInitialAttributes() {
//    val instance: Halfborough = new Halfborough( element )
//
//    assertEquals( instance.id, id )
//    assertEquals( instance.on, on )
//    assertEquals( instance.location, location )
//  }
//
//  @Test
//  @throws[ Exception ]
//  def storesAttachment() {
//    val instance: Halfborough = new Halfborough( element )
//
//    //    assertSame( instance, Attachment.Find( id ) )
//  }
  //
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
