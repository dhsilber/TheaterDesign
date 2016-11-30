package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.Element

/**
  * Created by dhs on 11/30/16.
  */
class AttachmentTest {

  var halfboroughElement: Element = null
  val id = "foo"
  val on = "bar"
  val location = "d"

  @BeforeMethod
  def setUpMethod(): Unit = {
    Attachment.Reset()

    halfboroughElement = new IIOMetadataNode( Halfborough.Tag )
    halfboroughElement.setAttribute( "id", id )
    halfboroughElement.setAttribute( "on", on )
    halfboroughElement.setAttribute( "location", location )
  }

  @Test
  def constantTag(): Unit = {
    assertEquals( Attachment.Tag, "attachment" )
  }

  @Test( expectedExceptions = Array( classOf[ NoSuchElementException ] ) )
  def findMissing(): Unit = {
    Attachment.Find( "not here" )
  }

  @Test
  def add(): Unit = {
    val halfborough = new Halfborough( halfboroughElement )
    Attachment.Add( halfborough )

    assertSame( Attachment.Find( id ), halfborough )
  }

//  @Test
//  @throws[Exception]
//  def isA() {
//    val instance: Attachment = new Attachment( element )
//    assertTrue( classOf[ Elemental ].isInstance( instance ) )
//    assertTrue( classOf[ ElementalLister ].isInstance( instance ) )
//    assert( classOf[ Verifier ].isInstance(instance) )
//    assert( classOf[ Layerer ].isInstance(instance) )
//    assert( classOf[ MinderDom ].isInstance(instance) )
//    assert( classOf[ Attachment ].isInstance(instance) )
//    //        assert( classOf[ ProtoWall ].isInstance(instance) )
//    //    assertFalse( classOf[ UniqueId ].isInstance(instance) )
//
//    //    assertFalse( classOf[ IsClamp ].isInstance(instance) )
//    //    assertFalse( classOf[ Legendable ].isInstance(instance) )
//  }
//
//  @Test
//  @throws[ Exception ]
//  def storesInitialAttributes() {
//    val instance: Attachment = new Attachment( element )
//
//    assertEquals( instance.id, id )
//    assertEquals( instance.on, on )
//    assertEquals( instance.location, location )
//  }

//  @Test
//  @throws[ Exception ]
//  def storesAttachment() {
//    val instance: Attachment = new Attachment( element )
//
//    //    assertSame( instance, Attachment.Find( id ) )
//  }
  //
  //  @Test
  //  @throws[ Exception ]
  //  def findsReferencedAttachment() {
  //    val instance: Attachment = new Attachment( element )
  //    val reference: Attachment = new Attachment( referringElement )
  //
  //    assertSame( instance, reference )
  //  }
}
