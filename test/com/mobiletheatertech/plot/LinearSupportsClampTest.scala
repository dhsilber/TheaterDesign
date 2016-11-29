/**
  * Created by DHS on 7/27/16.
  */
package com.mobiletheatertech.plot

import javax.imageio.metadata.IIOMetadataNode

import org.w3c.dom.Element
import org.testng.annotations._
import org.testng.Assert.{assertEquals, _}

import scala.Boolean

class LinearSupportsClampTest {

  private class SupporterForClampLinear extends LinearSupportsClamp {
    override def minLocation: Double = -12
    override def maxLocation: Double = 12
  }

  private class LinearSupportsClampBased extends LinearSupportsClamp {
    override val based = true
    override def minLocation: Double = -12
    override def maxLocation: Double = 12
  }

  private class LinearSupportsClampPositioned extends LinearSupportsClamp {
    override val positioned = true
    override def minLocation: Double = -12
    override def maxLocation: Double = 12
  }

  private class LinearSupportsClampSuspended extends LinearSupportsClamp {
    override val suspended = true
    override def minLocation: Double = -12
    override def maxLocation: Double = 12
  }

  private class UniqueIdLinearSupportsClamp(element: Element )
    extends UniqueId( element: Element )
      with LinearSupportsClamp {

    override def dom(draw: Draw, mode: View): Unit = ???

    override def verify(): Unit = ???
  }


  private class UniqueIdDoesNotSupportClamp( element: Element )
    extends UniqueId( element: Element ) {

    override def dom(draw: Draw, mode: View): Unit = ???

    override def verify(): Unit = ???
  }


  //  private var element: Element = null
//  private val id: String = "MountedID"
  private[plot] var luminaireElement: Element = null
  private var baseForPipeElement: Element = null
  private var pipeOnBaseElement: Element = null
  private[plot] val weight: Double = 9.4
  private[plot] val unit: String = "7"
  val owner = "nobody"
  private[plot] val `type`: String = "6x9"
  private[plot] val location: String = "13"
  private val baseX: Double = 40.0
  private val baseY: Double = 50.0
  private val pipeId: String = "Pipe ID"
  private val baseId: String = "TrussBase ID"
  private val length: Double = 37.5

  @BeforeMethod
  @throws[Exception]
  def setUpMethod {
    //    TestResets.YokeableReset
    TestResets.LuminaireReset()
    TestResets.ElementalListerReset()
    UniqueId.Reset()

    //    element = new IIOMetadataNode("mounted")
    //    element.setAttribute("id", id)

    //    val width: Integer = 13
    //    val length: Integer = 27

    //    val definitionElement: Element = new IIOMetadataNode("luminaire-definition")
    //    definitionElement.setAttribute("name", `type`)
    //    definitionElement.setAttribute("width", width.toString)
    //    definitionElement.setAttribute("length", length.toString)
    //    definitionElement.setAttribute("weight", weight.toString)
    //    definitionElement.appendChild(new IIOMetadataNode("svg"))
    //    new LuminaireDefinition(definitionElement)

    luminaireElement = new IIOMetadataNode("luminaire")
    luminaireElement.setAttribute("type", `type`)
    luminaireElement.setAttribute("on", pipeId)
    luminaireElement.setAttribute("location", location)
    luminaireElement.setAttribute("unit", unit)
    luminaireElement.setAttribute("owner", owner)


    baseForPipeElement = new IIOMetadataNode("pipebase")
    //        baseForPipeElement.setAttribute( "size", baseSize.toString() );
    baseForPipeElement.setAttribute( "id", baseId )
    baseForPipeElement.setAttribute("x", baseX.toString)
    baseForPipeElement.setAttribute("y", baseY.toString)

    pipeOnBaseElement = new IIOMetadataNode("pipe")
    pipeOnBaseElement.setAttribute("id", pipeId)
    pipeOnBaseElement.setAttribute("length", length.toString)
    pipeOnBaseElement.appendChild(baseForPipeElement)
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod {
  }

  @Test
  @throws[Exception]
  def isA {
    val instance1 = new SupporterForClampLinear
    assert(classOf[LinearSupportsClamp].isInstance(instance1))

    val instance2 = new UniqueIdLinearSupportsClamp( pipeOnBaseElement )
    assert(classOf[LinearSupportsClamp].isInstance(instance2))

    val instance3 = new UniqueIdDoesNotSupportClamp( baseForPipeElement )
    assertFalse(classOf[LinearSupportsClamp].isInstance(instance3))
  }

  @Test
  def globalVarBasedBoolean: Unit = {
    val instance = new SupporterForClampLinear
    instance.based.isInstanceOf[ Boolean ]
    assertFalse( instance.based )

    val basedSupported = new LinearSupportsClampBased
    assertTrue( basedSupported.based )
  }

  @Test
  def globalVarPositionedBoolean: Unit = {
    val instance = new SupporterForClampLinear
    instance.positioned.isInstanceOf[ Boolean ]
    assertFalse( instance.positioned )

    val positionedSupported = new LinearSupportsClampPositioned
    assertTrue( positionedSupported.positioned )
  }

  @Test
  def globalVarSuspendedBoolean: Unit = {
    val instance = new SupporterForClampLinear
    instance.suspended.isInstanceOf[ Boolean ]
    assertFalse( instance.suspended )

    val suspendedSupported = new LinearSupportsClampSuspended
    assertTrue( suspendedSupported.suspended )
  }

  @Test(expectedExceptions = Array(classOf[DataException]),
    expectedExceptionsMessageRegExp = "mounted element unexpectedly null!" )
  @throws[Exception]
  def hangNull {
    val mounted = new SupporterForClampLinear
    mounted.hang( null, 0.1 )
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "does not include location -12.1." )
  @throws[Exception]
  def locationTooSmall {
    val pipe: SupporterForClampLinear = new SupporterForClampLinear
    val light = new Luminaire( luminaireElement )
    pipe.hang( light, -12.1 )
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "does not include location 12.1." )
  @throws[Exception]
  def locationTooLarge {
    val pipe: SupporterForClampLinear = new SupporterForClampLinear
    val light = new Luminaire( luminaireElement )
    pipe.hang( light, 12.1 )
  }

  @Test
  @throws[Exception]
  def hangIsClamp {
    val pipe: SupporterForClampLinear = new SupporterForClampLinear
    assertEquals( pipe.IsClampList.size, 0 )
    val light = new Luminaire( luminaireElement )
    pipe.hang( light, 0.3 )
    assertEquals( pipe.IsClampList.size, 1 )
  }

  @Test
  @throws[Exception]
  def containsUnfound {
    val mounted = new SupporterForClampLinear
    assertFalse( mounted.contains( new Luminaire( luminaireElement ) ) )
  }

  @Test
  @throws[Exception]
  def containsFound {
    val pipe: SupporterForClampLinear = new SupporterForClampLinear
    val light = new Luminaire( luminaireElement )
    pipe.hang( light, 0.3 )
    assertTrue( pipe.contains( light ) )
  }

  @Test
  @throws[Exception]
  def selectEmpty {
    assertEquals( ElementalLister.List().size(), 0 )
    val found: LinearSupportsClamp = LinearSupportsClamp.Select( "bogus" )
    assertNull( found )
  }

  @Test
  @throws[Exception]
  def selectMatch {
    val target = new UniqueIdLinearSupportsClamp( pipeOnBaseElement )
    assertEquals( target.id, pipeId )
    assertEquals( ElementalLister.List().size(), 1 )
    val found: LinearSupportsClamp = LinearSupportsClamp.Select( pipeId )
    assertSame( found, target )
  }

  @Test
  @throws[Exception]
  def selectMatchNotMountable {
    new UniqueIdLinearSupportsClamp( pipeOnBaseElement )
    new UniqueIdDoesNotSupportClamp( baseForPipeElement )
    assertEquals( ElementalLister.List().size(), 2 )
    val found: LinearSupportsClamp = LinearSupportsClamp.Select( baseId )
    assertNull( found )
  }

  @Test
  @throws[Exception]
  def selectNoMatch {
    new UniqueIdLinearSupportsClamp( pipeOnBaseElement )
    assertEquals( ElementalLister.List().size(), 1 )
    val found: LinearSupportsClamp = LinearSupportsClamp.Select( "bogus" )
    assertNull( found )
  }
}

object LinearSupportsClampTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass {
  }
}

