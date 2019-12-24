package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode
import org.testng.Assert._
import org.testng.annotations.{BeforeMethod, Test}
import org.w3c.dom.Element

class LocationOrderingTest {

  var luminaireNegative: Luminaire = null
  var luminairePositive: Luminaire = null
  var luminaireSamePositive: Luminaire = null

  @BeforeMethod
  def setUpMethod: Unit = {

    val luminaireNegativeElement = new IIOMetadataNode( Luminaire.Tag )
    luminaireNegativeElement.setAttribute( "on", "pipe" )
    luminaireNegativeElement.setAttribute( "type", "type" )
    luminaireNegativeElement.setAttribute( "owner", "owner" )
    luminaireNegativeElement.setAttribute( "location", "-12" )
    luminaireNegative = new Luminaire( luminaireNegativeElement )

    val luminairePositiveElement = new IIOMetadataNode( Luminaire.Tag )
    luminairePositiveElement.setAttribute( "on", "pipe" )
    luminairePositiveElement.setAttribute( "type", "type" )
    luminairePositiveElement.setAttribute( "owner", "owner" )
    luminairePositiveElement.setAttribute( "location", "37" )
    luminairePositive = new Luminaire( luminairePositiveElement )

    val luminaireSamePositiveElement = new IIOMetadataNode( Luminaire.Tag )
    luminaireSamePositiveElement.setAttribute( "on", "pipe" )
    luminaireSamePositiveElement.setAttribute( "type", "type" )
    luminaireSamePositiveElement.setAttribute( "owner", "owner" )
    luminaireSamePositiveElement.setAttribute( "location", "37" )
    luminaireSamePositive = new Luminaire( luminaireSamePositiveElement )
  }


  @Test
  def locationIncreasing: Unit = {
    assert( 0 < LocationOrdering.compare( luminaireNegative, luminairePositive ) )
  }

  @Test
  def locationDecreasing: Unit = {
    assert( 0 > LocationOrdering.compare( luminairePositive, luminaireNegative ) )
  }

  @Test
  def locationSame: Unit = {
    assertEquals( 0, LocationOrdering.compare( luminairePositive, luminaireSamePositive ) )
  }

}
