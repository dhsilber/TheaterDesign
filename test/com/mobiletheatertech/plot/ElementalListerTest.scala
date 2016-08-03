package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.w3c.dom.Element
import org.testng.annotations._
import org.testng.Assert.{assertEquals, _}

/**
  * Created by DHS on 8/3/16.
  */
class ElementalListerTest {


  var element: Element = null

  @Test
  @throws[Exception]
  def isA {
    val instance = new ElementalLister( element )
    assert(classOf[ Elemental ].isInstance(instance))
    assert(classOf[ ElementalLister ].isInstance(instance))
  }

  @Test
  @throws[Exception]
  def add {
    val instance = new ElementalLister( element )

    assertTrue( ElementalLister.List().contains( instance ) )
    assertEquals( ElementalLister.List().size(), 1 )
  }

  @Test
  @throws[Exception]
  def remove {
    val instance = new ElementalLister( element )

    ElementalLister.Remove( instance )

    assertEquals( ElementalLister.List().size(), 0 )
  }

  @BeforeMethod
  @throws[Exception]
  def setUpMethod: Unit = {
    TestResets.ElementalListerReset()

    element = new IIOMetadataNode( "bogus" )
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }
}

object ElementalListerTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}

