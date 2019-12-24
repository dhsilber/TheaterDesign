package com.mobiletheatertech.plot

import org.testng.Assert._
import org.testng.annotations._

class ValidatedDoubleTest {
  val testValue = 0.7

  @Test
  def storesValid: Unit = {
    val foo = new ValidatedDouble( true, testValue )

    assertTrue( foo.valid )
  }

  @Test
  def storesInvalid: Unit = {
    val foo = new ValidatedDouble( false, testValue )

    assertFalse( foo.valid )
  }

  @Test
  def providesValue: Unit = {
    val foo = new ValidatedDouble( true, testValue )

    assertEquals( testValue, foo.value )
  }

}
