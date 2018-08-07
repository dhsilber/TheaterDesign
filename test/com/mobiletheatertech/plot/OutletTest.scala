package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.{Element, Node, NodeList}
/**
  * Created by dhs on 3/1/17.
  */
class OutletTest {

  var element: Element = null


  @BeforeMethod
  def setUpMethod(): Unit = {
    element = new IIOMetadataNode( Outlet.Tag )

  }

  @Test
  def isA(): Unit = {
    val instance = new Outlet( element )

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
    assertEquals( Outlet.Tag, "outlet" )
  }


}
