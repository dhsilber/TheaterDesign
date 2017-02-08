package com.mobiletheatertech.plot

import org.testng.annotations.{Test, _}
import org.testng.Assert.assertEquals

/**
  * Created by dhs on 12/12/16.
  */
class GearTest {

  val itemValue = "item value"
  val ownerValue = "mine!"

  private class Empty {

  }

  private class BeingAGear extends Empty with Gear {
    override def item(): String = itemValue

    override def owner(): String = ownerValue
  }

  @BeforeMethod
  def setUpMethod() {
  }

  @AfterMethod
  def tearDownMethod() {
  }

  @Test
  def isA() {
    val instance = new BeingAGear
    assert(classOf[ Gear ].isInstance( instance ))
  }

  @Test
  def item() {
    val instance = new BeingAGear

    assertEquals( instance.item, itemValue )
  }

  @Test
  def owner() {
    val instance = new BeingAGear

    assertEquals( instance.owner, ownerValue )
  }

  object TrussTest {
    @BeforeClass
    def setUpClass() {
    }

    @AfterClass
    def tearDownClass() {
    }
  }
}