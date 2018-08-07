package com.mobiletheatertech.plot

import org.testng.annotations._
import org.testng.Assert.assertEquals

/**
  * Created by dhs on 12/13/16.
  */
class GearListTest {
  private val item = "Test string"
  private val owner = "Owner string"

  private class Empty {}

  private class GearlyThing( val itemName: String ) extends Empty with Gear {
    override def item(): String = itemName
  }

  @BeforeMethod
  def setUpMethod() {
    GearList.Reset()
  }

  @AfterMethod
  def tearDownMethod() {
  }

  @Test
  def constantItem: Unit = {
    assertEquals( GearList.ITEM, 0 )
  }

  @Test
  def constantQuantity: Unit = {
    assertEquals( GearList.QUANTITY, 1 )
  }

  @Test
  def constantExtent: Unit = {
    assertEquals( GearList.EXTENT, 2 )
  }

  @Test
  def checkByItemEmpty() {
    assertEquals(GearList.Check(item), 0.asInstanceOf[Integer])
  }

  @Test
  def storesGear() {
    val gearly = new GearlyThing( item )

    assertEquals(GearList.Check(item), 0.asInstanceOf[Integer])
    //        new Gearlie( item );
    GearList.Add(gearly)
    assertEquals(GearList.Check(item), 1.asInstanceOf[Integer])
  }

  @Test
  def storesItemTwice() {
    val gearly = new GearlyThing( item )

    assertEquals(GearList.Check(item), 0.asInstanceOf[Integer])
    GearList.Add( gearly )
    GearList.Add( gearly )
    assertEquals(GearList.Check(item), 2.asInstanceOf[Integer])
  }

  @Test
  def getList() {
    val jim = "Jim"
    val jane = "Jane"

    val gearlyJim = new GearlyThing( jim )
    val gearlyJane = new GearlyThing( jane )

    GearList.Add( gearlyJim )
    GearList.Add( gearlyJim )
    GearList.Add( gearlyJim )
    GearList.Add( gearlyJane )
    val data = GearList.Report
//    assertEquals(data(0)(0), jim + "'s room" )
    assertEquals(data(0)(GearList.ITEM), jim )
    assertEquals(data(0)(GearList.QUANTITY), 3.asInstanceOf[Integer])
//    assertEquals(data(1)(0), jane + "'s room" )
    assertEquals(data(1)(GearList.ITEM), jane )
    assertEquals(data(1)(GearList.QUANTITY), 1.asInstanceOf[Integer])
  }

  @Test
  def getListEmpty() {
    val data = GearList.Report
  }

  @Test
  def getListMore() {
    val jim = "Jim"
    val jane = "Jane"
    val jo = "Jo"
    val joe = "Joe"

    val gearlyJim = new GearlyThing( jim )
    val gearlyJane = new GearlyThing( jane )
    val gearlyJo = new GearlyThing( jo )
    val gearlyJoe = new GearlyThing( joe )

    GearList.Add( gearlyJim )
    GearList.Add( gearlyJim )
    GearList.Add( gearlyJim )
    GearList.Add( gearlyJane )
    GearList.Add( gearlyJo )
    GearList.Add( gearlyJoe )
    val data = GearList.Report
    assertEquals(GearList.Size, 4)
    assertEquals(data(0)(GearList.ITEM), jim )
    assertEquals(data(0)(GearList.QUANTITY), 3.asInstanceOf[Integer])
    assertEquals(data(1)(GearList.ITEM), jane )
    assertEquals(data(1)(GearList.QUANTITY), 1.asInstanceOf[Integer])
    assertEquals(data(2)(GearList.ITEM), jo )
    assertEquals(data(2)(GearList.QUANTITY), 1.asInstanceOf[Integer])
    assertEquals(data(3)(GearList.ITEM), joe )
    assertEquals(data(3)(GearList.QUANTITY), 1.asInstanceOf[Integer])
  }
}

object GearListTest {
  @BeforeClass
  def setUpClass() {
  }

  @AfterClass
  def tearDownClass() {
  }
}
