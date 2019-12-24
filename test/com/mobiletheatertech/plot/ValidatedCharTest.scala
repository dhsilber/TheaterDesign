package com.mobiletheatertech.plot

import org.testng.Assert._
import org.testng.annotations._

class ValidatedCharTest {
  val testValue = 'q'

  @Test
  def storesValid: Unit = {
    val foo = new ValidatedChar( true, testValue )

    assertTrue( foo.valid )
  }

  @Test
  def storesInvalid: Unit = {
    val foo = new ValidatedChar( false, testValue )

    assertFalse( foo.valid )
  }

  @Test
  def providesValue: Unit = {
    val foo = new ValidatedChar( true, testValue )

    assertEquals( testValue, foo.value )
  }

}
