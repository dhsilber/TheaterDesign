package com.mobiletheatertech.plot

import org.testng.annotations.{Test, _}
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.imageio.metadata.IIOMetadataNode
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.reflect.Field
import java.util.ArrayList
import java.util.TreeMap

import scala.collection.mutable.ArrayBuffer
import org.testng.Assert._

/**
  * @author dhs
  * @since 0.0.5
  */
class TrussTest {
  var trussElement: Element = null
  var positionedTrussElement: Element = null
  var hanger1: HangPoint = null
  var hanger2: HangPoint = null
  var hanger3: HangPoint = null
  var trussOnBaseElement: Element = null
  var baseElement: Element = null
  var suspendElement1a: Element = null
  var suspendElement1b: Element = null
  var suspendElement2: Element = null
  var suspendElement3: Element = null
  var elementTrussDiagonal: Element = null
  var trussPositionedElement: Element = null
  //    Element baseElement = null;
  final val trussId: String = "trussId"
  final val trussDiagonalId: String = "trusDiagonalId"
  final val trussOnBaseId: String = "trussOnBaseId"
  val sizeIntegerString: String = "12"
  val size: Double = 12.0
  val length: Double = 160.0
  val x1: Double = 100.0
  val y1and2: Double = 200.0
  val x2: Double = 180.0
  val suspend3X: Double = 150.0
  val suspend3Y: Double = 150.0
  val x: Double = 73.7
  val y: Double = 12.5
  val z: Double = 22.4
  val baseSize: Double = 24.0
  val baseX: Double = 17.0
  val baseY: Double = 32.8
  val venueHeight: Double = 240.0
  val suspendDistance: Double = 1.0
  val negativeX: Double = -21.9
  var hangPoint1: Element = null
  var hangPoint2: Element = null
  var hangPoint3: Element = null
  private var prosceniumElement: Element = null
  private val prosceniumX: Integer = 200
  private val prosceniumY: Integer = 144
  private val prosceniumZ: Integer = 12
  var pipeElement: Element = null
  var luminaireElement: Element = null
  final val luminaireUnit: String = "unit"
  final val luminaireOwner: String = "owner"
  final val luminaireType: String = "Altman 6x9"
  val luminaireLocation: String = "b 12"

  var halfboroughElement: Element = null
  val halfbooughId = "halfboroughId"
  val halfboroughLocationD = "d"

  @BeforeMethod
  @throws[Exception]
  def setUpMethod() {
    Proscenium.Reset()
    TestResets.ElementalListerReset()
    TestResets.MountableReset()
    TestResets.MinderDomReset()
    TestResets.LuminaireReset()
    Truss.Reset()
    UniqueId.Reset()
    assertTrue(ElementalLister.List.isEmpty)
    assertTrue(Layer.List.isEmpty)

    val venueElement: Element = new IIOMetadataNode("venue")
    venueElement.setAttribute("room", "Test Name")
    venueElement.setAttribute("width", "350")
    venueElement.setAttribute("depth", "400")
    venueElement.setAttribute("height", venueHeight.toString)
    new Venue(venueElement)

    prosceniumElement = new IIOMetadataNode("proscenium")
    prosceniumElement.setAttribute("width", "260")
    prosceniumElement.setAttribute("height", "200")
    prosceniumElement.setAttribute("depth", "22")
    prosceniumElement.setAttribute("x", prosceniumX.toString)
    prosceniumElement.setAttribute("y", prosceniumY.toString)
    prosceniumElement.setAttribute("z", prosceniumZ.toString)

    hangPoint1 = new IIOMetadataNode("hangpoint")
    hangPoint1.setAttribute("id", "jim")
    hangPoint1.setAttribute("x", x1.toString)
    hangPoint1.setAttribute("y", y1and2.toString)

    hangPoint2 = new IIOMetadataNode("hangpoint")
    hangPoint2.setAttribute("id", "joan")
    hangPoint2.setAttribute("x", x2.toString)
    hangPoint2.setAttribute("y", y1and2.toString)

    hangPoint3 = new IIOMetadataNode("hangpoint")
    hangPoint3.setAttribute("id", "fred")
    hangPoint3.setAttribute("x", suspend3X.toString)
    hangPoint3.setAttribute("y", suspend3Y.toString)

    suspendElement1a = new IIOMetadataNode("suspend")
    suspendElement1a.setAttribute("ref", "jim")
    suspendElement1a.setAttribute("distance", suspendDistance.toString)

    suspendElement1b = new IIOMetadataNode("suspend")
    suspendElement1b.setAttribute("ref", "jim")
    suspendElement1b.setAttribute("distance", suspendDistance.toString)

    suspendElement2 = new IIOMetadataNode("suspend")
    suspendElement2.setAttribute("ref", "joan")
    suspendElement2.setAttribute("distance", suspendDistance.toString)

    suspendElement3 = new IIOMetadataNode("suspend")
    suspendElement3.setAttribute("ref", "fred")
    suspendElement3.setAttribute("distance", suspendDistance.toString)

    //        baseElement = new IIOMetadataNode( "base" );
    //        baseElement.setAttribute( "x", "1" );
    //        baseElement.setAttribute( "y", "2" );

    trussElement = new IIOMetadataNode("truss")
    trussElement.setAttribute("id", trussId)
    trussElement.setAttribute("size", size.toString)
    trussElement.setAttribute("length", length.toString)
    trussElement.appendChild(suspendElement1a)
    trussElement.appendChild(suspendElement2)

    positionedTrussElement = new IIOMetadataNode("truss")
    positionedTrussElement.setAttribute("id", trussId)
    positionedTrussElement.setAttribute("size", size.toString)
    positionedTrussElement.setAttribute("length", length.toString)
    positionedTrussElement.setAttribute("x", x.toString)
    positionedTrussElement.setAttribute("y", y.toString)
    positionedTrussElement.setAttribute("z", z.toString)

    elementTrussDiagonal = new IIOMetadataNode("truss")
    elementTrussDiagonal.setAttribute("id", trussDiagonalId)
    elementTrussDiagonal.setAttribute("size", size.toString)
    elementTrussDiagonal.setAttribute("length", length.toString)
    elementTrussDiagonal.appendChild(suspendElement1b)
    elementTrussDiagonal.appendChild(suspendElement3)

    baseElement = new IIOMetadataNode("trussbase")
    baseElement.setAttribute("size", baseSize.toString)
    baseElement.setAttribute("x", baseX.toString)
    baseElement.setAttribute("y", baseY.toString)

    trussOnBaseElement = new IIOMetadataNode("truss")
    trussOnBaseElement.setAttribute("id", trussOnBaseId)
    trussOnBaseElement.setAttribute("size", size.toString)
    trussOnBaseElement.setAttribute("length", length.toString)
    //        trussOnBaseElement.appendChild(baseElement);

    trussPositionedElement = new IIOMetadataNode("truss")
    trussPositionedElement.setAttribute("id", TrussTest.trussPositionedId)
    trussPositionedElement.setAttribute("size", size.toString)
    trussPositionedElement.setAttribute("length", length.toString)
    trussPositionedElement.setAttribute("x", x.toString)
    trussPositionedElement.setAttribute("y", y.toString)
    trussPositionedElement.setAttribute("z", z.toString)

    pipeElement = new IIOMetadataNode(Pipe.Tag)

    luminaireElement = new IIOMetadataNode("luminaire")
    luminaireElement.setAttribute("unit", luminaireUnit)
    luminaireElement.setAttribute("owner", luminaireOwner)
    luminaireElement.setAttribute("type", luminaireType)
    luminaireElement.setAttribute("location", luminaireLocation)

    halfboroughElement = new IIOMetadataNode( Halfborough.Tag )
    halfboroughElement.setAttribute( "id", halfbooughId )
    halfboroughElement.setAttribute( "location", halfboroughLocationD )
  }

  @AfterMethod
  @throws[Exception]
  def tearDownMethod() {
  }

  @Test
  @throws[Exception]
  def isA() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val instance: Truss = new Truss(trussElement)
    assert(classOf[Elemental].isInstance(instance))
    assert(classOf[ElementalLister].isInstance(instance))
    assert(classOf[Verifier].isInstance(instance))
    assert(classOf[Layerer].isInstance(instance))
    assert(classOf[MinderDom].isInstance(instance))
    assert(classOf[UniqueId].isInstance(instance))
    assertFalse(classOf[Yokeable].isInstance(instance))
    assert(classOf[LinearSupportsClamp].isInstance(instance))
    assert(classOf[Populate].isInstance(instance))
    assert(classOf[Legendable].isInstance(instance))
    //        assert Schematicable.class.isInstance( instance );
  }

  @Test
  @throws[Exception]
  def linearSupportsClampHasVertex() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val instance: Truss = new Truss(trussElement)
    assertTrue(instance.hasVertex)
  }

  @Test def constantLayerName() {
    assertEquals(Truss.LayerName, "Trusses")
  }

  @Test def constantLayerTag() {
    assertEquals(Truss.LayerTag, "truss")
  }

  @Test def constantColor() {
    assertEquals(Truss.Color, "dark blue")
  }

  @Test def parentIsTrussBase() {
    baseElement.appendChild(trussOnBaseElement)
    val trussBase: TrussBase = new TrussBase(baseElement)
    val list: ArrayList[ElementalLister] = ElementalLister.List
    val venue: ElementalLister = list.get(0)
    assert(classOf[MinderDom].isInstance(venue))
    assert(classOf[Venue].isInstance(venue))
    val trussbase: ElementalLister = list.get(1)
    assert(classOf[MinderDom].isInstance(trussbase))
    //        System.out.println( "parentIsTrussBase: " + trussbase.getClass().toString() );
    assert(classOf[TrussBase].isInstance(trussbase))
    val truss: ElementalLister = list.get(2)
    assert(classOf[MinderDom].isInstance(truss))
    assert(classOf[Truss].isInstance(truss))
    val actualTruss: Truss = truss.asInstanceOf[Truss]
    assertEquals(list.size, 3)
    //        System.out.println( "Trussbase " + trussbase.toString() );
    //        System.out.println( "actualTruss " + actualTruss.toString() );
    //        System.out.println( "parent " + actualTruss.parentParse().toString() );
    assertSame(trussBase, actualTruss.parentParse)
  }

  @Test def getPositionFromParentTrussBase() {
    baseElement.appendChild(trussOnBaseElement)
    val trussBase: TrussBase = new TrussBase(baseElement)
    val truss: Truss = trussBase.truss

    val baseAttachmentB: Point = trussBase.mountPoints()(1)
    val target: Point = new Point(baseAttachmentB.x, baseAttachmentB.y, baseAttachmentB.z + truss.length)

    assertEquals(truss.mountableLocation(new Location("b")), target)
  }

  @Test def globalVariableLegendRegistered() {
    assertEquals(Truss.LegendRegistered, false)
  }

  @Test
  @throws[Exception]
  def endAttachmentsUnused() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val instance: Truss = new Truss(elementTrussDiagonal)
    assertTrue(instance.endAttachments.isEmpty)
  }

  @Test def baseCountIncrement() {
    assertEquals(Truss.BaseCount, 0)
    Truss.BaseCountIncrement()
    assertEquals(Truss.BaseCount, 1)
  }

  @Test
  @throws[Exception]
  def storesAttributes() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    assertEquals(TestHelpers.accessDouble(truss, "size"), size)
    assertEquals(TestHelpers.accessDouble(truss, "length"), length)
    assertNull(TestHelpers.accessDouble(truss, "x"))
    assertNull(TestHelpers.accessDouble(truss, "y"))
    assertNull(TestHelpers.accessDouble(truss, "z"))
  }

  @Test
  @throws[Exception]
  def storeOptionalsAttributes() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    trussElement.setAttribute("x", x.toString)
    trussElement.setAttribute("y", y.toString)
    trussElement.setAttribute("z", z.toString)
    val truss: Truss = new Truss(trussElement)
    assertEquals(TestHelpers.accessDouble(truss, "size"), size)
    assertEquals(TestHelpers.accessDouble(truss, "length"), length)
    assertEquals(TestHelpers.accessDouble(truss, "x"), x)
    assertEquals(TestHelpers.accessDouble(truss, "y"), y)
    assertEquals(TestHelpers.accessDouble(truss, "z"), z)
  }

  @Test
  @throws[Exception]
  def positionedNot() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    assertEquals(TestHelpers.accessBoolean(truss, "positioned"), false)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def positionedNoX() {
    trussPositionedElement.removeAttribute("x")
    new Truss(trussPositionedElement)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def positionedNoY() {
    trussPositionedElement.removeAttribute("y")
    new Truss(trussPositionedElement)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def positionedNoZ() {
    trussPositionedElement.removeAttribute("z")
    new Truss(trussPositionedElement)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def positionedNoXY() {
    trussPositionedElement.removeAttribute("x")
    trussPositionedElement.removeAttribute("y")
    new Truss(trussPositionedElement)
  }

  @Test
  @throws[Exception]
  def positioned() {
    val truss: Truss = new Truss(trussPositionedElement)
    assertEquals(TestHelpers.accessBoolean(truss, "positioned"), true )
  }

  @Test
  @throws[Exception]
  def Values() {
    val truss: Truss = new Truss(trussPositionedElement)
    //        truss.verify();
    val start: Point = TestHelpers.accessObject(truss, "start").asInstanceOf[Point]
    assertEquals(start.x, x)
    assertEquals(start.y, y)
    assertEquals(start.z, z)
  }

  @Test
  @throws[Exception]
  def storesElement() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    val elementField: Field = TestHelpers.accessField(truss, "element")
    val elementReference: Element = elementField.get(truss).asInstanceOf[Element]
    assertSame(elementReference, trussElement)
  }

  @Test
  @throws[Exception]
  def baseChild() {
    val base: TrussBase = new TrussBase(baseElement)
    val truss: Truss = new Truss(trussOnBaseElement, base)
    truss.verify()
    val baseField: Field = TestHelpers.accessField(truss, "trussBase")
    val trussBase: TrussBase = baseField.get(truss).asInstanceOf[TrussBase]
    assert(classOf[TrussBase].isInstance(trussBase))
    val suspendField1: Field = TestHelpers.accessField(truss, "suspend1")
    val suspend1: Any = suspendField1.get(truss)
    assertNull(suspend1)
    val suspendField2: Field = TestHelpers.accessField(truss, "suspend2")
    val suspend2: Any = suspendField2.get(truss)
    assertNull(suspend2)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussOnBaseId\\) must have position, one trussbase, or two suspend children.")
  @throws[Exception]
  def noBase() {
    trussOnBaseElement.removeChild(baseElement)
    new Truss(trussOnBaseElement)
    //        truss.verify();
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussOnBaseId\\) must have position, one trussbase, or two suspend children.")
  @throws[Exception]
  def tooManyBases() {
    val baseElementExtra: Element = new IIOMetadataNode("trussbase")
    baseElementExtra.setAttribute("size", baseSize.toString)
    baseElementExtra.setAttribute("x", "12")
    baseElementExtra.setAttribute("y", "2009")
    trussOnBaseElement.appendChild(baseElement)
    trussOnBaseElement.appendChild(baseElementExtra)
    new Truss(trussOnBaseElement)
    //        truss.verify();
  }

  /*
              Make a couple of suspend objects that are children of this truss
              and confirm that they are properly associated
       */ @Test
  @throws[Exception]
  def suspendChildren() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    new Suspend(suspendElement1a)
    new Suspend(suspendElement2)
    //        truss.verify();
    val suspendField1: Field = TestHelpers.accessField(truss, "suspend1")
    val suspend1: Suspend = suspendField1.get(truss).asInstanceOf[Suspend]
    val suspendField2: Field = TestHelpers.accessField(truss, "suspend2")
    val suspend2: Suspend = suspendField2.get(truss).asInstanceOf[Suspend]
    assertTrue(classOf[Suspend].isInstance(suspend1))
    assertTrue(classOf[Suspend].isInstance(suspend2))
    val baseField: Field = TestHelpers.accessField(truss, "trussBase")
    val baseReference: Any = baseField.get(truss)
    assertNull(baseReference)
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) must have position, one trussbase, or two suspend children.")
  @throws[Exception]
  def noSuspends() {
    trussElement.removeChild(suspendElement1a)
    trussElement.removeChild(suspendElement2)
    new Truss(trussElement)
    //        truss.verify();
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) must have position, one trussbase, or two suspend children.")
  @throws[Exception]
  def tooFewSuspends() {
    trussElement.removeChild(suspendElement1a)
    new Truss(trussElement)
    //        truss.verify();
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) must have position, one trussbase, or two suspend children.")
  @throws[Exception]
  def tooManySuspends() {
    // This is just broken.
    // A new 'element' should have been constructed in setUpMethod() that has two
    //   suspendElement# in it, but apparently the removal in the above test is not
    //   being overwritten.
    trussElement.appendChild(suspendElement1a)
    trussElement.appendChild(suspendElement2)
    trussElement.appendChild(suspendElement3)
    new Truss(trussElement)
    //        truss.verify();
  }

  /*
       * This is to ensure that no exception is thrown if data is OK.
       */ @Test
  @throws[Exception]
  def justFineSize12() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    new Truss(trussElement)
  }

  /*
       * This is to ensure that no exception is thrown if data is OK.
       */ @Test
  @throws[Exception]
  def justFineSize12Integer() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    trussElement.setAttribute("size", sizeIntegerString)
    new Truss(trussElement)
  }

  /*
       * This is to ensure that no exception is thrown if data is OK.
       */ @Test
  @throws[Exception]
  def justFineSize20() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    trussElement.setAttribute("size", "20.5")
    new Truss(trussElement)
  }

  /*
       * This is to ensure that no exception is thrown if data is OK.
       */ @Test
  @throws[Exception]
  def justFinePositioned() {
    trussElement.removeChild(suspendElement1a)
    trussElement.removeChild(suspendElement2)
    trussElement.removeChild(suspendElement3)
    trussElement.setAttribute("x", x.toString)
    trussElement.setAttribute("y", y.toString)
    trussElement.setAttribute("z", z.toString)
    new Truss(trussElement)
  }

  @Test(expectedExceptions = Array(classOf[AttributeMissingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) is missing required 'size' attribute.")
  @throws[Exception]
  def noSize() {
    trussElement.removeAttribute("size")
    new Truss(trussElement)
  }

  @Test(expectedExceptions = Array(classOf[AttributeMissingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) is missing required 'length' attribute.")
  @throws[Exception]
  def noLength() {
    trussElement.removeAttribute("length")
    new Truss(trussElement)
  }

  @Test(expectedExceptions = Array(classOf[KindException]),
    expectedExceptionsMessageRegExp = "Truss of size 302.0 not supported. Try 12.0 or 20.5.")
  @throws[Exception]
  def unsupportedSize() {
    trussElement.setAttribute("size", "302")
    new Truss(trussElement)
  }

  @Test
  @throws[Exception]
  def location() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    //        truss.verify();
    assertNotNull(TestHelpers.accessObject(truss, "suspend1"))
    assertEquals(truss.suspend1.locate, new Point(x1, y1and2, venueHeight - suspendDistance))
    assertNotNull(TestHelpers.accessObject(truss, "suspend2"))
    assertEquals(truss.suspend2.locate, new Point(x2, y1and2, venueHeight - suspendDistance))
    val point: Point = truss.mountableLocation(new Location("a 17"))
    assertEquals(point, new Point(77.0, 194.0, 239.0))
  }

  @Test
  @throws[Exception]
  def locationVertexC() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    new Suspend(suspendElement1a)
    new Suspend(suspendElement2)
    truss.verify()
    assertNotNull(TestHelpers.accessObject(truss, "suspend1"))
    assertNotNull(TestHelpers.accessObject(truss, "suspend2"))
    val point: Point = truss.mountableLocation(new Location("c 17"))
    assertEquals(point, new Point(77.0, 194.0, 227.0))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def locationFormatNoDistance() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    truss.mountableLocation(new Location("a"))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussId\\) location does not include a valid vertex." )
  @throws[Exception]
  def locationFormatNoVertex() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    truss.mountableLocation(new Location("17"))
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def locationOffTruss() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    truss.mountableLocation(new Location("a 161"))
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def locationNegativeOffTruss() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    truss.mountableLocation(new Location("a -1"))
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def locationVertexOffTruss() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    truss.mountableLocation(new Location("e 16"))
  }

  @Test
  @throws[Exception]
  def locationPositionedVertexA() {
    val truss: Truss = new Truss(trussPositionedElement)
    val point: Point = truss.mountableLocation(new Location("a 17"))
    assertEquals(point, new Point(x - length / 2 + 17, y - size / 2, z + size / 2))
  }

  @Test
  @throws[Exception]
  def locationPositionedVertexB() {
    val truss: Truss = new Truss(trussPositionedElement)
    val point: Point = truss.mountableLocation(new Location("b 97"))
    assertEquals(point, new Point(x - length / 2 + 97, y + size / 2, z + size / 2))
  }

  @Test
  @throws[Exception]
  def locationPositionedVertexC() {
    val truss: Truss = new Truss(trussPositionedElement)
    val point: Point = truss.mountableLocation(new Location("c 15"))
    assertEquals(point, new Point(x - length / 2 + 15, y - size / 2, z - size / 2))
  }

  @Test
  @throws[Exception]
  def locationPositionedVertexD() {
    val truss: Truss = new Truss(trussPositionedElement)
    val point: Point = truss.mountableLocation(new Location("d 150"))
    assertEquals(point, new Point(x - length / 2 + 150, y + size / 2, z - size / 2))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children." )
  @throws[Exception]
  def locationPositionedFormatNoDistance() {
    val truss: Truss = new Truss(trussPositionedElement)
    truss.mountableLocation(new Location("a"))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) location does not include a valid vertex." )
  @throws[Exception]
  def locationPositionedFormatNoVertex() {
    val truss: Truss = new Truss(trussPositionedElement)
    truss.mountableLocation(new Location("17"))
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) location does not include a valid vertex." )
  @throws[Exception]
  def locationPositionedOffTruss() {
    val truss: Truss = new Truss(trussPositionedElement)
    truss.mountableLocation(new Location("a 161"))
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) does not include location -1." )
  @throws[Exception]
  def locationPositionedNegativeOffTruss() {
    val truss: Truss = new Truss(trussPositionedElement)
    truss.mountableLocation(new Location("a -1"))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss \\(trussPositionedId\\) location does not include a valid vertex.")
  @throws[Exception]
  def locationPositionedVertexOffTruss() {
    val truss: Truss = new Truss(trussPositionedElement)
    truss.mountableLocation(new Location("e 16"))
  }

  @Test(expectedExceptions = Array(classOf[InvalidXMLException]),
    expectedExceptionsMessageRegExp = "Truss not yet supported with Proscenium.")
  @throws[Exception]
  def trussWithProscenium() {
    val prosceniumElement: Element = new IIOMetadataNode("proscenium")
    prosceniumElement.setAttribute("width", "260")
    prosceniumElement.setAttribute("height", "200")
    prosceniumElement.setAttribute("depth", "22")
    prosceniumElement.setAttribute("x", "160")
    prosceniumElement.setAttribute("y", "150")
    prosceniumElement.setAttribute("z", "14")
    new Proscenium(prosceniumElement)
    assertTrue(Proscenium.Active)
    assertTrue(TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Proscenium", "ACTIVE").asInstanceOf[Boolean])
    new Truss(trussElement)
  }

  @Test
  @throws[Exception]
  def rotatedLocation() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    new Suspend(suspendElement1a)
    new Suspend(suspendElement2)
    truss.verify()
    val place: Place = truss.rotatedLocation(new Location("b 23"))
    assertEquals(place.origin, TestHelpers.accessPoint(truss, "point1"))
    assertNotNull(TestHelpers.accessDouble(truss, "rotation"))
    assertEquals(place.rotation, -0.0)
    assertEquals(place.location, new Point(83.0, 206.0, 239.0))
  }

  @Test
  @throws[Exception]
  def rotatedLocationBeforeVerify() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val truss: Truss = new Truss(trussElement)
    new Suspend(suspendElement1a)
    new Suspend(suspendElement2)
    val place: Place = truss.rotatedLocation(new Location("b 23"))
    assertEquals(place.origin, TestHelpers.accessPoint(truss, "point1"))
    assertNotNull(TestHelpers.accessDouble(truss, "rotation"))
    assertEquals(place.rotation, -0.0)
    assertEquals(place.location, new Point(83.0, 206.0, 239.0))
  }

  @Test
  @throws[Exception]
  def rotatedLocationDiagonal() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    new Suspend(suspendElement1b)
    new Suspend(suspendElement3)
    val truss: Truss = new Truss(elementTrussDiagonal)
    val place: Place = truss.rotatedLocation(new Location("b 23"))
    assertEquals(place.origin, TestHelpers.accessPoint(truss, "point1"))
    assertNotNull(TestHelpers.accessDouble(truss, "rotation"))
    assertEquals(place.rotation, -45.0)
    assertEquals(place.location, new Point(83.0, 206.0, 239.0))
    fail("Does rotatedLocation give correct results for truss at a diagonal to the room coordinates?")
  }

  @Test def minLocation() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertEquals(truss.minLocation, 0.0)
  }

  @Test def maxLocation() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertEquals(truss.maxLocation, length)
  }

  @Test def processLuminaire() {
    positionedTrussElement.appendChild(luminaireElement)
    val truss: Truss = new Truss(positionedTrussElement)
    val last: IsClamp = truss.IsClampList.last
    assertEquals(last.getClass, classOf[Luminaire])
    val luminaire: Luminaire = last.asInstanceOf[Luminaire]
    assertEquals(luminaire.id, trussId + ":" + luminaireUnit)
  }

  @Test(expectedExceptions = Array(classOf[MountingException]),
    expectedExceptionsMessageRegExp = //"Truss \\(trussId\\) does not include location 162.")
      "Truss \\(trussId\\) unit 'unit' does not include location c 162.0.")
  def processLuminaireLocationOutOfRange() {
    luminaireElement.setAttribute("location", "c 162")
    positionedTrussElement.appendChild(luminaireElement)
    new Truss(positionedTrussElement)
  }

  @Test def luminaireCallbackRegistered() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertTrue(truss.tags.contains(Luminaire.LAYERTAG))
    assertEquals(truss.tags.size, 3)
  }

  @Test def cheeseboroughCallbackRegistered() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertTrue(truss.tags.contains(Cheeseborough.TAG))
    assertEquals(truss.tags.size, 1)
  }

  @Test def halfboroughCallbackRegistered() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertTrue(truss.tags.contains(Halfborough.Tag))
    assertEquals(truss.tags.size, 3)
  }

  @Test def pipeCallbackRegistered() {
    val truss: Truss = new Truss(positionedTrussElement)
    assertTrue(truss.tags.contains(Pipe.Tag))
    assertEquals(truss.tags.size, 3)
  }

  @Test def populateChildren() {
    positionedTrussElement.appendChild(luminaireElement)
    val truss: Truss = new Truss(positionedTrussElement)
    val list: ArrayBuffer[IsClamp] = truss.IsClampList
    assertEquals(list.size, 1)
  }

  @Test
  def halfBoroughChildAttaches(): Unit = {
    val base: TrussBase = new TrussBase( baseElement )
    val truss: Truss = new Truss( trussOnBaseElement, base )
    val halfborough = new Halfborough( halfboroughElement, truss )

    fail( "Truss should have halfborough attached. Also, in HalfboroughTest check that we get a good position.")
  }

  @Test
  @throws[Exception]
  def domPlanPositioned() {
    val draw: Draw = new Draw
    draw.establishRoot()
    val truss: Truss = new Truss(trussPositionedElement)
    truss.verify()
    truss.dom(draw, View.PLAN)
    val group: NodeList = draw.root.getElementsByTagName("g")
    assertEquals(group.getLength, 2)
    val groupNode: Node = group.item(1)
    assertEquals(groupNode.getNodeType, Node.ELEMENT_NODE)
    val groupElement: Element = groupNode.asInstanceOf[Element]
    assertEquals(groupElement.getAttribute("class"), Truss.LayerTag)
    val list: NodeList = groupElement.getElementsByTagName("rect")
    assertEquals(list.getLength, 1)
    val node: Node = list.item(0)
    assertEquals(node.getNodeType, Node.ELEMENT_NODE)
    val element: Element = node.asInstanceOf[Element]
    assertEquals(element.getAttribute("width"), length.toString)
    assertEquals(element.getAttribute("height"), size.toString)
    assertEquals(element.getAttribute("fill"), "none")
    assertEquals(element.getAttribute("stroke"), "dark blue")
    val ex: Double = x - length / 2
    assertEquals(element.getAttribute("x"), ex.toString)
    val wy: Double = y - size / 2
    assertEquals(element.getAttribute("y"), wy.toString)
  }

  @Test
  @throws[Exception]
  def domPlanBased() {
    val draw: Draw = new Draw
    draw.establishRoot()
    val base: TrussBase = new TrussBase(baseElement)
    val truss: Truss = new Truss(trussOnBaseElement, base)
    truss.verify()
    truss.dom(draw, View.PLAN)
    val group: NodeList = draw.root.getElementsByTagName("g")
    assertEquals(group.getLength, 2)
    val groupNode: Node = group.item(1)
    assertEquals(groupNode.getNodeType, Node.ELEMENT_NODE)
    val groupElement: Element = groupNode.asInstanceOf[Element]
    assertEquals(groupElement.getAttribute("class"), Truss.LayerTag)
    val list: NodeList = groupElement.getElementsByTagName("rect")
    assertEquals(list.getLength, 1)
    val node: Node = list.item(0)
    assertEquals(node.getNodeType, Node.ELEMENT_NODE)
    val element: Element = node.asInstanceOf[Element]
    assertEquals(element.getAttribute("width"), size.toString)
    assertEquals(element.getAttribute("height"), size.toString)
    assertEquals(element.getAttribute("fill"), "none")
    assertEquals(element.getAttribute("stroke"), "dark blue")
    val ex: Double = baseX - size / 2
    assertEquals(element.getAttribute("x"), ex.toString)
    val wy: Double = baseY - size / 2
    assertEquals(element.getAttribute("y"), wy.toString)
    val originX: Double = baseX + SvgElement.OffsetX
    val originY: Double = baseY + SvgElement.OffsetY
    assertEquals(element.getAttribute("transform"), "rotate(0.0," + originX.toString + "," + originY.toString + ")")
  }

  @Test
  @throws[Exception]
  def domPlanSuspended() {
    hanger1 = new HangPoint(hangPoint1)
    hanger2 = new HangPoint(hangPoint2)
    hanger3 = new HangPoint(hangPoint3)
    val draw: Draw = new Draw
    draw.establishRoot()
    val truss: Truss = new Truss(trussElement)
    new Suspend(suspendElement1a)
    new Suspend(suspendElement2)
    truss.verify()
    truss.dom(draw, View.PLAN)
    val group: NodeList = draw.root.getElementsByTagName("g")
    assertEquals(group.getLength, 2)
    val groupNode: Node = group.item(1)
    assertEquals(groupNode.getNodeType, Node.ELEMENT_NODE)
    val groupElement: Element = groupNode.asInstanceOf[Element]
    assertEquals(groupElement.getAttribute("class"), Truss.LayerTag)
    val list: NodeList = groupElement.getElementsByTagName("rect")
    assertEquals(list.getLength, 1)
    val node: Node = list.item(0)
    assertEquals(node.getNodeType, Node.ELEMENT_NODE)
    val element: Element = node.asInstanceOf[Element]
    assertEquals(element.getAttribute("width"), length.toString)
    assertEquals(element.getAttribute("height"), size.toString)
    assertEquals(element.getAttribute("fill"), "none")
    assertEquals(element.getAttribute("stroke"), "dark blue")
    val x: Double = x1 - (length - (x2 - x1)) / 2
    assertEquals(element.getAttribute("x"), x.toString)
    val y: Double = y1and2 - size / 2
    assertEquals(element.getAttribute("y"), y.toString)
    assertEquals(element.getAttribute("transform"), "rotate(-0.0," + x1.toString + "," + y1and2.toString + ")")
  }

  @Test
  @throws[Exception]
  def legendRegistered() {
    TestResets.LegendReset()
    val base: TrussBase = new TrussBase(baseElement)
    new Truss(trussOnBaseElement, base)
    val legendList: java.util.TreeMap[Integer, Legendable] =
      TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Legend",
        "LEGENDLIST").asInstanceOf[java.util.TreeMap[Integer, Legendable]]
    assertEquals(legendList.size, 1)
    val order: Integer = legendList.lastKey
    assert(order >= LegendOrder.Structure.initial)
    assert(order < LegendOrder.Luminaire.initial)
  }

  @Test
  @throws[Exception]
  def legendRegisteredOnce() {
    TestResets.LegendReset()
    val base: TrussBase = new TrussBase(baseElement)
    new Truss(trussOnBaseElement, base)
    trussOnBaseElement.setAttribute("id", "differentBaseTruss")
    new Truss(trussOnBaseElement, base)
    val legendList: java.util.TreeMap[Integer, Legendable] =
      TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Legend",
        "LEGENDLIST").asInstanceOf[java.util.TreeMap[Integer, Legendable]]
    assertEquals(legendList.size, 1)
  }

  @Test
  @throws[Exception]
  def domLegendItem() {
    val draw: Draw = new Draw
    draw.establishRoot()
    val base: TrussBase = new TrussBase(baseElement)
    val truss: Truss = new Truss(trussOnBaseElement, base)
    truss.verify()
    //        truss.dom(draw, View.PLAN);
    Truss.BaseCountIncrement()
    val startPoint: PagePoint = new PagePoint(20.0, 10.0)
    val preGroup: NodeList = draw.root.getElementsByTagName("g")
    assertEquals(preGroup.getLength, 1)
    val endPoint: PagePoint = truss.domLegendItem(draw, startPoint)
    //        NodeList group = draw.root().getElementsByTagName( "g" );
    //        assertEquals( group.getLength(), 1 );
    //        Node groupNod = group.item(0);
    //        Element groupElem = (Element) groupNod;
    val groupList: NodeList = draw.root.getElementsByTagName("g")
    assertEquals(groupList.getLength, 2)
    val groupNode: Node = groupList.item(1)
    assertEquals(groupNode.getNodeType, Node.ELEMENT_NODE)
    val groupElement: Element = groupNode.asInstanceOf[Element]
    val rectList: NodeList = groupElement.getElementsByTagName("rect")
    assertEquals(rectList.getLength, 2)
    var rectNode: Node = rectList.item(0)
    assertEquals(rectNode.getNodeType, Node.ELEMENT_NODE)
    var rectElement: Element = rectNode.asInstanceOf[Element]
    assertEquals(rectElement.getAttribute("x"), "0.0")
    assertEquals(rectElement.getAttribute("y"), "0.0")
    assertEquals(rectElement.getAttribute("width"), "12.0")
    assertEquals(rectElement.getAttribute("height"), "12.0")
    rectNode = rectList.item(1)
    assertEquals(rectNode.getNodeType, Node.ELEMENT_NODE)
    rectElement = rectNode.asInstanceOf[Element]
    val innerX: Double = startPoint.x + 3
    val innerY: Double = startPoint.y + 3
    assertEquals(rectElement.getAttribute("x"), "3.0")
    assertEquals(rectElement.getAttribute("y"), "3.0")
    assertEquals(rectElement.getAttribute("width"), "6.0")
    assertEquals(rectElement.getAttribute("height"), "6.0")
    val textList: NodeList = groupElement.getElementsByTagName("text")
    assertEquals(textList.getLength, 2)
    var textNode: Node = textList.item(0)
    assertEquals(textNode.getNodeType, Node.ELEMENT_NODE)
    var textElement: Element = textNode.asInstanceOf[Element]
    var x: Double = Legend.TEXTOFFSET
    val y: Double = 8.0
    assertEquals(textElement.getAttribute("x"), x.toString)
    assertEquals(textElement.getAttribute("y"), y.toString)
    assertEquals(textElement.getAttribute("fill"), "black")
    textNode = textList.item(1)
    assertEquals(textNode.getNodeType, Node.ELEMENT_NODE)
    textElement = textNode.asInstanceOf[Element]
    x = Legend.QUANTITYOFFSET
    assertEquals(textElement.getAttribute("x"), x.toString)
    assertEquals(textElement.getAttribute("y"), y.toString)
    assertEquals(textElement.getAttribute("fill"), "black")
    // TODO Check for text here
    assertEquals(endPoint, new PagePoint(startPoint.x, startPoint.y + 9))
  }

  @Test
  @throws[Exception]
  def parseWithSuspends() {
    val xml: String = "<plot>" + "<hangpoint id=\"bill\" x=\"7\" y=\"8\" />" + "<hangpoint id=\"betty\" x=\"7\" y=\"8\" />" + "<truss id=\"id\" size=\"12\" length=\"1\" >" + "<suspend ref=\"bill\" distance=\"3\" />" + "<suspend ref=\"betty\" distance=\"3\" />" + "</truss>" + "</plot>"
    val stream: InputStream = new ByteArrayInputStream(xml.getBytes)
    TestResets.MinderDomReset()
    new Parse(stream)
    val list: java.util.ArrayList[ElementalLister] = ElementalLister.List
    assertEquals(list.size, 5)
  }

  @Test
  @throws[Exception]
  def parseMultiple() {
    val xml: String = "<plot>" + "<hangpoint id=\"roger\" x=\"7\" y=\"8\" />" + "<hangpoint id=\"renee\" x=\"7\" y=\"8\" />" + "<truss id=\"id\" size=\"12\" length=\"1\" >" + "<suspend ref=\"roger\" distance=\"3\" />" + "<suspend ref=\"renee\" distance=\"3\" />" + "</truss>" + "<truss id=\"id2\" size=\"12\" length=\"1\" >" + "<suspend ref=\"roger\" distance=\"3\" />" + "<suspend ref=\"renee\" distance=\"3\" />" + "</truss>" + "</plot>"
    val stream: InputStream = new ByteArrayInputStream(xml.getBytes)
    TestResets.MinderDomReset()
    new Parse(stream)
    val list: java.util.ArrayList[ElementalLister] = ElementalLister.List
    assertEquals(list.size, 8)
  }

  object TrussTest {
    final val trussPositionedId: String = "trussPositionedId"
    final val expectedExceptionsMessageRegExpWithTrussPosition: String = "Truss \\(trussPositionedId\\) must have position, one trussbase, or two suspend children."
    final val expectedExceptionsMessageRegExpLocation161: String = "Truss \\(trussPositionedId\\) does not include location 161."
    final val expectedExceptionsMessageRegExpLocationNegative1: String = "Truss \\(trussPositionedId\\) does not include location -1."

    @BeforeClass
    @throws[Exception]
    def setUpClass() {
    }

    @AfterClass
    @throws[Exception]
    def tearDownClass() {
    }
  }
}