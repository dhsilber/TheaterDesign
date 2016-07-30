package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.w3c.dom.Element

import org.testng.annotations._
import org.testng.Assert.{assertEquals, _}

/**
  * Created by DHS on 7/28/16.
  */
class IsClampTest {


  private class BeingAClamp extends IsClamp {
  }


  @Test
  @throws[Exception]
  def isA {
    val instance = new BeingAClamp
    assert(classOf[ IsClamp ].isInstance(instance))
  }

  @BeforeMethod
  @throws[Exception]
  def setUpMethod {
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }
}

object IsClampTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}

