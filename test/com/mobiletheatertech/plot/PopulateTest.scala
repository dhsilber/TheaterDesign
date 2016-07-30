package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.w3c.dom.Element

import org.testng.annotations._
import org.testng.Assert.{assertEquals, _}

/**
  * Created by DHS on 7/29/16.
  */
class PopulateTest {

  private class Populator( element: Element ) extends Populate {
  }

  var elementWithChildren: Element = null
  var childElement: Element = null
  var otherChildElement: Element = null
  val childTag: String = "childTag"
  val otherChildTag: String = "otherChildTag"
  val parentTag: String = "parentTag"

  @Test
  @throws[Exception]
  def isA {
    val instance1 = new Populator( elementWithChildren )
    assert(classOf[Populate].isInstance(instance1))
  }

  @Test
  def tagCallbackRegistered: Unit = {
    def callBack( element: Element ): Unit = {
    }

    val thingy = new Populator( elementWithChildren )
    thingy.tagCallback( childTag, callBack )

    assertEquals( thingy.tags.size, 1 )
  }

  @Test
  def tagCallbackUsed: Unit = {
    var called: Int = 0
    def callBack( element: Element ): Unit = {
      called += 1
    }

    val thingy = new Populator( elementWithChildren )
    thingy.tagCallback( childTag, callBack )
    thingy.populate( elementWithChildren )

    assertEquals( called, 1 )
  }

  @BeforeMethod
  @throws[Exception]
  def setUpMethod: Unit = {
    childElement = new IIOMetadataNode( childTag )
    elementWithChildren = new IIOMetadataNode( parentTag )
    elementWithChildren.appendChild( childElement )
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }
}

object PopulateTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}

