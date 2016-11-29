package com.mobiletheatertech.plot

import org.testng.annotations._
import org.testng.Assert._

/**
  * Created by DHS on 9/25/16.
  */
class InstanceIdTest {

  val id = "test ID"

  @Test
  @throws[Exception]
  def isA: Unit = {
    val instance = new InstanceId( id: String )

    assertFalse( classOf[ String ].isInstance( instance ) )
  }

  @Test
  @throws[ Exception ]
  def stores: Unit = {
    val instance = new InstanceId( id: String )

      assertEquals( instance.string, id )
  }

  @Test
  @throws[ Exception ]
  def returnsString: Unit = {
    val instance = new InstanceId( id: String )

    assertEquals( instance.id, s"($id)" )
  }

  @Test
  @throws[ Exception ]
  def returnsInstanceNull: Unit = {
    val instance = new InstanceId( null )

    assertEquals( instance.id, "instance" )
  }

  @Test
  @throws[ Exception ]
  def returnsInstanceEmpty: Unit = {
    val instance = new InstanceId( "" )

    assertEquals( instance.id, "instance" )
  }

}
