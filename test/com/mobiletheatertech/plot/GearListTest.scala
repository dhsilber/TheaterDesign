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
    assertEquals(data(0)(0), jim + "'s room" )
    assertEquals(data(0)(1), jim )
    assertEquals(data(0)(2), 3.asInstanceOf[Integer])
    assertEquals(data(1)(0), jane + "'s room" )
    assertEquals(data(1)(1), jane )
    assertEquals(data(1)(2), 1.asInstanceOf[Integer])
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
    assertEquals(data(0)(0), jim )
    assertEquals(data(0)(1), 3.asInstanceOf[Integer])
    assertEquals(data(1)(0), jane )
    assertEquals(data(1)(1), 1.asInstanceOf[Integer])
    assertEquals(data(2)(0), jo )
    assertEquals(data(2)(1), 1.asInstanceOf[Integer])
    assertEquals(data(3)(0), joe )
    assertEquals(data(3)(1), 1.asInstanceOf[Integer])
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
