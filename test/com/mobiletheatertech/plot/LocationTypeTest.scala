package com.mobiletheatertech.plot

import org.testng.annotations.Test
import org.testng.Assert._


/**
  * Created with IntelliJ IDEA. User: dhs Date: 8/9/13 Time: 6:00 PM To change this template use File
  * | Settings | File Templates.
  *
  * @since 0.0.7
  */
/*
This is probably overkill, but the enum could be changed...
 */ 

class LocationTypeTest {
  
  @Test
  def values(): Unit = {
    assertNotNull(LocationType.ANY)
    assertNotNull(LocationType.SIMPLE)
    assertNotNull(LocationType.COMPLEX)
    assertNotNull(LocationType.VERTEX)

    println( LocationType.ANY )
    println( LocationType.SIMPLE )
    println( LocationType.COMPLEX )
    println( LocationType.VERTEX )
  }

  @Test
  def different(): Unit = {
    assertNotEquals(LocationType.ANY, LocationType.SIMPLE)
    assertNotEquals(LocationType.ANY, LocationType.COMPLEX)
    assertNotEquals(LocationType.ANY, LocationType.VERTEX)
    assertNotEquals(LocationType.SIMPLE, LocationType.COMPLEX)
    assertNotEquals(LocationType.SIMPLE, LocationType.VERTEX)
    assertNotEquals(LocationType.COMPLEX, LocationType.VERTEX)
  }
}
