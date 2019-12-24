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
  val negativeIntegerString = '-' + integer.toString
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

    val vertex = instance.vertex
    val distance = instance.distance

    assertFalse( vertex.valid )
    assertTrue( distance.valid )

    assertEquals( distance.value, integerString.toDouble )
  }

  @Test
  def distanceFromNegativeInteger() {
    val instance = new Location( negativeIntegerString )

    val vertex = instance.vertex
    val distance = instance.distance

    assertFalse( vertex.valid )
    assertTrue( distance.valid )

    assertEquals( distance.value, negativeIntegerString.toDouble )
  }

  @Test
  def distanceFromComplex() {
    val instance = new Location( complexString )

    val vertex = instance.vertex
    val distance = instance.distance

    assertTrue( vertex.valid )
    assertTrue( distance.valid )

    assertEquals( distance.value, complexNumber.toDouble )
  }

  @Test
  def vertexFromNumber() {
    val instance = new Location( integerString )

    val vertex = instance.vertex
    val distance = instance.distance

    assertFalse( vertex.valid )
    assertTrue( distance.valid )
  }

  @Test
  def vertexFromComplex() {
    val instance = new Location( complexString )

    val vertex = instance.vertex
    val distance = instance.distance

    assertTrue( vertex.valid )
    assertTrue( distance.valid )

    assertEquals( vertex.value, complexLetter )
  }

  @Test
  def vertexOnly() {
    val instance = new Location( vertexLetter )

    val vertex = instance.vertex
    val distance = instance.distance

    assertTrue( vertex.valid )
    assertFalse( distance.valid )

    assertEquals( vertex.value.toString, vertexLetter.toString )
  }

  @Test
  def vertexOnlyToString(): Unit = {
    val instance = new Location( vertexLetter )

    assertTrue( instance.vertex.valid )
    assertFalse( instance.distance.valid )

    assertEquals( instance.toString(), vertexLetter )
  }

  @Test
  def distanceOnlyToString(): Unit = {
    val instance = new Location( double.toString )

    assertTrue( instance.distance.valid )
    assertFalse( instance.vertex.valid )

    assertEquals( instance.toString(), double.toString )
  }

  @Test
  def complexToString(): Unit = {
    val instance = new Location( complexString )

    assertTrue( instance.vertex.valid )
    assertTrue( instance.distance.valid )

    assertEquals( instance.toString(), complexString + ".0" )
  }
}
