package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.testng.Assert._
import org.testng.annotations._
import org.w3c.dom.Element

/**
  * Created by dhs on 12/2/16.
  */
class LocationTest {

  val integer = 12
  val double = 23.2
  val complexNumber = 15
  val complexLetter = 'b'
  val integerString = integer.toString
  val complexString = complexLetter + " " + complexNumber.toString
  val vertexLetter: String = "c"

  @BeforeMethod
  def setUpMethod(): Unit = {
  }

  @Test
  def isA() {
    val instance = new Location( integerString )
    assertFalse( classOf[ Elemental ].isInstance( instance ) )
    assertFalse( classOf[ ElementalLister ].isInstance( instance ) )
  }

  @Test
  def distanceInteger() {
    val instance = new Location( integerString )

    assertTrue( instance.distanceProvided )
    assertEquals( instance.distance, integerString + ".0" )
  }

  @Test
  def distanceFromComplex() {
    val instance = new Location( complexString )

    assertTrue( instance.distanceProvided )
    assertEquals( instance.distance, complexString + ".0" )
  }

  @Test
  def vertexFromNumber() {
    val instance = new Location( integerString )

    assertFalse( instance.vertexProvided )
    assertTrue( instance.distanceProvided )
  }

  @Test
  def vertexFromComplex() {
    val instance = new Location( complexString )

    assertTrue( instance.vertexProvided )
    assertTrue( instance.distanceProvided )
    assertEquals( instance.vertex, complexLetter )
    assertEquals( instance.distance, complexString + ".0" )
  }

  @Test
  def vertexOnly() {
    val instance = new Location( vertexLetter )

    assertTrue( instance.vertexProvided )
    assertFalse( instance.distanceProvided )
    assertEquals( instance.vertex, vertexLetter.toString )
    assertEquals( instance.distance, 0 )
  }

  @Test
  def vertexOnlyToString(): Unit = {
    val instance = new Location( vertexLetter )

    assertEquals( instance.toString(), vertexLetter )
  }

  @Test
  def distanceOnlyToString(): Unit = {
    val instance = new Location( double.toString )

    assertEquals( instance.toString(), double.toString )
  }

  @Test
  def complexToString(): Unit = {
    val instance = new Location( complexString )

    assertEquals( instance.toString(), complexString + ".0" )
  }

  //  @Test( expectedExceptions = Array( classOf[ AttributeInvalidException ] ),
  //    expectedExceptionsMessageRegExp = "Moulding instance has invalid 'side' attribute." )
  //  @throws[ Exception ]
  //  def unsupportedSideAttributeValue {
  //    element.setAttribute( "side", sideUnsupported )
  //    val moulding: Moulding = new Moulding( element )
  //  }
}
