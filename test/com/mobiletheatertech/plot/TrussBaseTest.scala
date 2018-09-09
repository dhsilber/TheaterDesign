package com.mobiletheatertech.plot

import org.testng.annotations._
import org.w3c.dom.Element
import javax.imageio.metadata.IIOMetadataNode
//import java.io.ByteArrayInputStream
//import java.io.InputStream
//import java.util.ArrayList
//import java.util.List

import org.testng.Assert._
import org.testng.Assert.assertNotEquals

/**
  * Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:01 PM To change this template use
  * File | Settings | File Templates.
  *
  * @since 0.0.5
  */
class TrussBaseTest {
  var baseElement: Element = null
  val truss: Truss = null
  val id: String = "Truss ID"
  val size: Double = 24.0
  val x: Double = 12.0
  val y: Double = 32.2
  val z: Double = -35.1
  val rotation: Double = 45.0
  var prosceniumElement: Element = null
  val prosceniumX: Double = 100.0
  val prosceniumY: Double = 200.0
  val prosceniumZ: Double = 15.0
  var trussElement: Element = null

  @BeforeMethod
  def setUpMethod() {
    TestResets.YokeableReset()
    TestResets.ElementalListerReset()
    Proscenium.Reset()
    UniqueId.Reset()
    TestResets.LegendReset()
    TrussBase.Reset()
    val venueElement: Element = new IIOMetadataNode
    venueElement.setAttribute("name", "TrussBase Venue Name")
    venueElement.setAttribute("room", "Test Name")
    venueElement.setAttribute("width", "350")
    venueElement.setAttribute("depth", "400")
    venueElement.setAttribute("height", "240")
    val venue: Venue = new Venue(venueElement)
    venue.getClass
    Venue.Height
    prosceniumElement = new IIOMetadataNode("proscenium")
    prosceniumElement.setAttribute("x", prosceniumX.toString)
    prosceniumElement.setAttribute("y", prosceniumY.toString)
    prosceniumElement.setAttribute("z", prosceniumZ.toString)
    prosceniumElement.setAttribute("width", "200")
    prosceniumElement.setAttribute("depth", "23")
    prosceniumElement.setAttribute("height", "144")
    val otherBase: Element = new IIOMetadataNode("trussbase")
    otherBase.setAttribute("ref", "jane")
    otherBase.setAttribute("distance", "200")
    baseElement = new IIOMetadataNode("trussbase")
    baseElement.setAttribute("size", size.toString)
    baseElement.setAttribute("x", x.toString)
    baseElement.setAttribute("y", y.toString)
    //        Element truss1 = new IIOMetadataNode( "truss" );
    //        truss1.setAttribute("id", id);
    //        truss1.setAttribute( "size", "12" );
    //        truss1.setAttribute( "length", "120" );
    //        truss1.appendChild(baseElement);
    trussElement = new IIOMetadataNode(Truss.Tag)
    trussElement.setAttribute("id", id)
    trussElement.setAttribute("size", "12")
    trussElement.setAttribute("length", "120")
  }

  @AfterMethod
  def tearDownMethod() {
  }

  @Test
  def isA() {
    val instance: TrussBase = new TrussBase(baseElement)
    assert(classOf[Elemental].isInstance(instance))
    assert(classOf[ElementalLister].isInstance(instance))
    assert(classOf[Verifier].isInstance(instance))
    assert(classOf[Layerer].isInstance(instance))
    assert(classOf[MinderDom].isInstance(instance))
    assertFalse(classOf[Yokeable].isInstance(instance))
    assertTrue(classOf[Populate].isInstance(instance))
    assertFalse(classOf[Legendable].isInstance(instance))
  }

  @Test def constantTag() {
    assertEquals(TrussBase.Tag, "trussbase")
  }

  @Test def constantColor() {
    assertEquals(TrussBase.Color, "taupe")
  }

  @Test
  def storesAttributes() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(TestHelpers.accessDouble(trussBase, "x"), x)
    assertEquals(TestHelpers.accessDouble(trussBase, "y"), y)
    assertEquals(TestHelpers.accessDouble(trussBase, "z"), 0.0)
    //        assertEquals( TestHelpers.accessString(trussBase, "type"), type );
    assertEquals(TestHelpers.accessDouble(trussBase, "size"), size)
    assertEquals(TestHelpers.accessDouble(trussBase, "rotation"), 0.0)
  }

  @Test
  def storesOptionalAttributes() {
    baseElement.setAttribute("rotation", rotation.toString)
    baseElement.setAttribute("z", z.toString)
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(TestHelpers.accessDouble(trussBase, "x"), x)
    assertEquals(TestHelpers.accessDouble(trussBase, "y"), y)
    assertEquals(TestHelpers.accessDouble(trussBase, "z"), z)
    //        assertEquals( TestHelpers.accessString(trussBase, "type"), type );
    assertEquals(TestHelpers.accessDouble(trussBase, "size"), size)
    assertEquals(TestHelpers.accessDouble(trussBase, "rotation"), rotation)
  }

  // Until such time as I properly implement this class' use of id.
  @Test
  def idUnused() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertNull(TestHelpers.accessString(trussBase, "id"))
  }

  @Test
  def marksProcessed() {
    val emptyMark: String = baseElement.getAttribute("processedMark")
    assertEquals(emptyMark, "", "Should be unset")
    val trussBase: TrussBase = new TrussBase(baseElement)
    val baseMark: String = TestHelpers.accessString(trussBase, "processedMark")
    val elementMark: String = baseElement.getAttribute("processedMark")
    assertNotNull(baseMark)
    assertNotEquals(baseMark, "", "Should be set in TrussBase object")
    assertNotEquals(elementMark, "", "Should be set in Element")
    assertEquals(baseMark, elementMark, "should match")
  }

  @Test
  def findNull() {
    new TrussBase(baseElement)
    val found: TrussBase = TrussBase.Find(null)
    assertNull(found)
  }

  @Test
  def findsMarked() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    val found: TrussBase = TrussBase.Find(baseElement.getAttribute("processedMark"))
    assertSame(found, trussBase)
  }

  /*
       * This is to ensure that no exception is thrown if data is OK.
       */
  @Test
  def justFine() {
    new TrussBase(baseElement)
  }

  @Test(expectedExceptions = Array(classOf[AttributeMissingException]),
    expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'x' attribute.")
  def noX() {
    baseElement.removeAttribute("x")
    new TrussBase(baseElement)
  }

  @Test(expectedExceptions = Array(classOf[AttributeMissingException]),
    expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'y' attribute.")
  def noY() {
    baseElement.removeAttribute("y")
    new TrussBase(baseElement)
  }

  @Test(expectedExceptions = Array(classOf[AttributeMissingException]),
    expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'size' attribute.")
  def noSize() {
    baseElement.removeAttribute("size")
    new TrussBase(baseElement)
  }

  @Test
  def hasX() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(trussBase.x, x)
  }

  @Test
  def hasY() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(trussBase.y, y)
  }

  @Test
  def hasZ() {
    baseElement.setAttribute("z", z.toString)
    val base: TrussBase = new TrussBase(baseElement)
    assertEquals(base.z, z)
  }

  @Test
  def hasSize() {
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(trussBase.size, size)
  }

  @Test
  def hasTruss() {
    baseElement.appendChild(trussElement)
    val trussBase: TrussBase = new TrussBase(baseElement)
    assertEquals(trussBase.truss.id, id)
  }

  @Test
  def mountPoints() {
    baseElement.setAttribute("z", z.toString)
    val trussBase: TrussBase = new TrussBase(baseElement)
    val locations: Array[Point] = trussBase.mountPoints
    assertEquals(locations( 0 ), new Point(x - 3.0, y - 3.0, z + 2.0))
    assertEquals(locations( 1 ), new Point(x + 3.0, y - 3.0, z + 2.0))
    assertEquals(locations( 2 ), new Point(x - 3.0, y + 3.0, z + 2.0))
    assertEquals(locations( 3 ), new Point(x + 3.0, y + 3.0, z + 2.0))
    assertEquals(locations.length, 4)
  }

  @Test
  def verify() {
    baseElement.setAttribute("z", z.toString)
    val instance: TrussBase = new TrussBase(baseElement)
    instance.verify()
    assertEquals(TestHelpers.accessObject(instance, "drawPlace"), new Point(x, y, z))
  }

  @Test
  def verifyProscenium() {
    new Proscenium(prosceniumElement)
    baseElement.setAttribute("z", z.toString)
    val instance: TrussBase = new TrussBase(baseElement)
    instance.verify()
    assertEquals(TestHelpers.accessObject(instance, "drawPlace"), new Point(prosceniumX + x, prosceniumY - y, prosceniumZ + z))
  }

  @Test def trussTagCallbackRegistered() {
    val trussbase: TrussBase = new TrussBase(baseElement)
    assertEquals(trussbase.populateTags.size, 1)
    assertTrue(trussbase.populateTags.contains(Truss.LayerTag))
  }

  @Test def populateChildren() {
    baseElement.appendChild(trussElement)
    new TrussBase(baseElement)
    val list: java.util.ArrayList[ElementalLister] = ElementalLister.List
    val venue: ElementalLister = list.get(0)
    assert(classOf[MinderDom].isInstance(venue))
    assert(classOf[Venue].isInstance(venue))
    val trussbase: ElementalLister = list.get(1)
    assert(classOf[MinderDom].isInstance(trussbase))
    assert(classOf[TrussBase].isInstance(trussbase))
    val truss: ElementalLister = list.get(2)
    assert(classOf[MinderDom].isInstance(truss))
    assert(classOf[Truss].isInstance(truss))
    assertEquals(list.size, 3)
  }

  //    @Mocked
  //    Venue mockVenue;
}

object TrussBaseTest {
  @BeforeClass
  @throws[Exception]
  def setUpClass() {
  }

  @AfterClass
  @throws[Exception]
  def tearDownClass() {
  }
}

