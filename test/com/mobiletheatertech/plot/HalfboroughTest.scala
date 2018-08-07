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

  var trussBaseElement: Element = null
  val trussBaseSize = 36
  val trussBaseY = 12
  val trussBaseX = 24

  var trussElement: Element = null
  val trussId = "truss ID"

  @BeforeMethod
  def setUpMethod(): Unit = {
    Halfborough.Reset()

    trussElement = new IIOMetadataNode( Truss.Tag )
    trussElement.setAttribute( "id", trussId )

    trussBaseElement = new IIOMetadataNode( TrussBase.Tag )
    trussBaseElement.setAttribute( "size", trussBaseSize.toString )
    trussBaseElement.setAttribute( "y", trussBaseY.toString )
    trussBaseElement.setAttribute( "x", trussBaseX.toString )
    trussBaseElement.appendChild( trussElement )

    element = new IIOMetadataNode( Halfborough.Tag )
    element.setAttribute( "id", id )
    element.setAttribute( "location", location )

    trussElement.appendChild( element )
  }

  @Test
  def constantTag(): Unit = {
    assertEquals( Halfborough.Tag, "halfborough" )
  }

  @Test
  def isA() {
    val instance: Halfborough = new Halfborough( element, null )
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
  def storesInitialAttributes() {
    val instance: Halfborough = new Halfborough( element, null )

    assertEquals( instance.id, id )
    assertEquals( instance.location, location )
  }

  @Test
  def storesAttachment() {
    val instance: Halfborough = new Halfborough( element, null )

    assertSame( instance, Attachment.Find( id ) )
  }

}
