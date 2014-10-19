package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * @author dhs
 * @since 0.0.5
 */
public class TrussTest {

    Element element = null;
    HangPoint hanger1 = null;
    HangPoint hanger2 = null;
    Element suspendElement1 = null;
    Element suspendElement2 = null;
//    Element baseElement = null;
    final String id = "trussID";
Integer size = 12;
    Integer length = 160;
    Integer x1 = 100;
    Integer y1 = 200;
    Integer x2 = 180;

    public TrussTest() {
    }

    @Test
    public void isA() throws Exception {
        Truss truss = new Truss( element );

        assert Mountable.class.isInstance(truss);
    }

    @Test
    public void isLegendable() throws Exception {
        Truss truss = new Truss( element );
        assert Legendable.class.isInstance( truss );
    }

    @Test
    public void storesAttributes() throws Exception {
        Truss truss = new Truss( element );

        assertEquals( TestHelpers.accessInteger( truss, "size" ), size );
        assertEquals( TestHelpers.accessInteger( truss, "length" ), length );
    }

//    @Test
//    public void marksProcessed() throws Exception {
//        String emptyMark = diversionElement.attribute( "processedMark" );
//        assertEquals( emptyMark, "", "Should be unset" );
//
//        Truss truss = new Truss( diversionElement );
//
//        String trussMark = TestHelpers.accessString( truss, "processedMark" );
//        String elementMark = diversionElement.attribute( "processedMark" );
//        assertNotNull( trussMark );
//        assertNotEquals( trussMark, "", "Should be set in Truss object" );
//        assertNotEquals( elementMark, "", "Should be set in Element" );
//        assertEquals( trussMark, elementMark, "should match" );
//    }
//
//    @Test
//    public void findNull() throws Exception {
//        new Truss( diversionElement );
//
//        Truss found = Truss.Find( null );
//
//        assertNull( found );
//    }
//
//    @Test
//    public void findsMarked() throws Exception {
//        Truss truss = new Truss( diversionElement );
//
//        Truss found = Truss.Find( diversionElement.attribute( "processedMark" ) );
//
//        assertSame( found, truss );
//    }

    @Test
    public void storesElement() throws Exception {
        Truss truss = new Truss( element );

        Field elementField = TestHelpers.accessField( truss, "element" );
        Element elementReference = (Element) elementField.get( truss );

        assertSame( elementReference, element );
    }

    @Test
    public void category() throws Exception {
        assertNull( Category.Select( Truss.CATEGORY ) );

        new Truss( element );

        assertNotNull( Category.Select( Truss.CATEGORY ) );
    }

    /*
            Make a couple of suspend objects that are children of this truss
            and confirm that they are properly associated
     */
    @Test
    public void verifySuspendReferences() throws Exception {
        Truss truss = new Truss( element );
        new Suspend( suspendElement1 );
        new Suspend( suspendElement2 );

        truss.verify();


        Field suspendField1 = TestHelpers.accessField( truss, "suspend1" );
        Suspend suspend1 = (Suspend) suspendField1.get( truss );

        Field suspendField2 = TestHelpers.accessField( truss, "suspend2" );
        Suspend suspend2 = (Suspend) suspendField2.get( truss );

        assert Suspend.class.isInstance( suspend1 );
        assert Suspend.class.isInstance( suspend2 );

        Field baseField = TestHelpers.accessField( truss, "base" );
        Object baseReference = baseField.get(truss);
        assertNull(baseReference);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) must have exactly two suspend children")
    public void verifyNoSuspends() throws Exception {
        element.removeChild( suspendElement1 );
        element.removeChild( suspendElement2 );
        Truss truss = new Truss( element );

        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) must have exactly two suspend children")
    public void verifyTooFewSuspends() throws Exception {
        element.removeChild( suspendElement1 );
        Truss truss = new Truss( element );

        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) must have exactly two suspend children")
    public void verifyTooManySuspends() throws Exception {
        Element suspendElement3 = new IIOMetadataNode( "suspend" );
        suspendElement3.setAttribute( "ref", "joan" );
        suspendElement3.setAttribute( "distance", "2" );
        element.appendChild( suspendElement3 );

        Truss truss = new Truss( element );

        truss.verify();
    }

//    @Test
//    public void verifyBase() throws Exception {
//        elementOnPipe.removeChild( suspendElement1 );
//        elementOnPipe.removeChild( suspendElement2 );
//        elementOnPipe.appendChild( baseElement );
//        Truss truss = new Truss( elementOnPipe );
//
//        truss.verify();
//
//        Field suspendField1 = TestHelpers.accessField( truss, "suspend1" );
//        Field suspendField2 = TestHelpers.accessField( truss, "suspend2" );
//
//        assertNull( suspendField1 );
//        assertNull( suspendField2 );
//
//        Field baseField = TestHelpers.accessField( truss, "base" );
//
//        assertNotNull( baseField );
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineSize12() throws Exception {
        new Truss( element );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineSize20() throws Exception {
        element.setAttribute( "size", "20" );
        new Truss( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) is missing required 'size' attribute.")
    public void noSize() throws Exception {
        element.removeAttribute( "size" );
        new Truss( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) is missing required 'length' attribute.")
    public void noLength() throws Exception {
        element.removeAttribute( "length" );
        new Truss( element );
    }

    @Test(expectedExceptions = KindException.class,
          expectedExceptionsMessageRegExp =
                  "Truss of size 302 not supported. Try 12 or 20.")
    public void unsupportedSize() throws Exception {
        element.setAttribute( "size", "302" );
        new Truss( element );
    }

//    @Test( expectedExceptions = StructureException.class,
//        expectedExceptionsMessageRegExp = 
//        "Truss should not extend beyond the boundaries of the venue")
//    public void noSuspend() throws Exception {
//        fail( "Need to test this in concert with suspend");
//        elementOnPipe.setAttribute( "depth", "401");
//        Truss truss = new Truss( elementOnPipe );
//    }
//    
//    @Test( expectedExceptions = StructureException.class,
//        expectedExceptionsMessageRegExp = 
//        "Truss should not extend beyond the boundaries of the venue")
//    public void tooFewSuspend() throws Exception {
//        fail( "Need to test this in concert with suspend");
//        elementOnPipe.setAttribute( "depth", "401");
//        Truss truss = new Truss( elementOnPipe );
//    }

    @Test
    public void location() throws Exception {
        Truss truss = new Truss( element );
        new Suspend( suspendElement1 );
        new Suspend( suspendElement2 );
        truss.verify();
        assertNotNull( TestHelpers.accessObject(truss, "suspend1"));
        assertNotNull( TestHelpers.accessObject(truss, "suspend2"));

        Point point = truss.location( "a 17");
        assertEquals( point, new Point(77, 194, 239));
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) location not correctly formatted.")
    public void locationFormatNoDistance() throws Exception{
        Truss truss = new Truss( element );

        truss.location( "a");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\("+id+"\\) location does not include a valid vertex.")
    public void locationFormatNoVertex() throws Exception{
        Truss truss = new Truss( element );

        truss.location( "17");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + id + "\\) does not include location 161.")
    public void locationOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.location( "a 161");
    }

    @Test(expectedExceptions=MountingException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + id + "\\) does not include location -1.")
    public void locationNegativeOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.location( "a -1");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss \\(" + id + "\\) location does not include a valid vertex.")
    public void locationVertexOffTruss() throws Exception{
        Truss truss = new Truss( element );

        truss.location( "e 16");
    }

    @Test(expectedExceptions=InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Truss not yet supported with Proscenium.")
    public void trussWithProscenium() throws Exception {
        Element prosceniumElement = new IIOMetadataNode("proscenium");
        prosceniumElement.setAttribute("width", "260");
        prosceniumElement.setAttribute("height", "200");
        prosceniumElement.setAttribute("depth", "22");
        prosceniumElement.setAttribute("x", "160");
        prosceniumElement.setAttribute("y", "150");
        prosceniumElement.setAttribute("z", "14");
        new Proscenium(prosceniumElement);
        assertTrue( Proscenium.Active());
        assertTrue( (Boolean)TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Proscenium","ACTIVE"));

        new Truss( element );
    }

    @Test
    public void rotatedLocation() throws Exception {
        Truss truss = new Truss( element );
        new Suspend( suspendElement1 );
        new Suspend( suspendElement2 );

        truss.verify();

        Place place = truss.rotatedLocation( "b 23");

        assertEquals( place.origin(), TestHelpers.accessPoint( truss, "point1"));
        assertNotNull( TestHelpers.accessDouble(truss, "rotation"));
        assertEquals( place.rotation(), -0.0);
        assertEquals( place.location(), new Point(83, 206, 239));
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Truss truss = new Truss( element );
        new Suspend(suspendElement1);
        new Suspend(suspendElement2);
        truss.verify();

        truss.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Truss.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("rect");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("width"), length.toString());
        assertEquals(element.getAttribute("height"), size.toString());
        assertEquals(element.getAttribute("fill"), "none");
        assertEquals(element.getAttribute("stroke"), "dark blue");
        Integer x = x1 - (length - (x2 - x1)) / 2;
        assertEquals(element.getAttribute("x"), x.toString());
        Integer y = y1 - size / 2;
        assertEquals(element.getAttribute("y"), y.toString());
        assertEquals(element.getAttribute("transform"), "rotate(-0.0," + x1.toString() + "," + y1.toString() + ")");
    }

//    @Test
//    public void domPlanProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( elementOnPipe );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element elementOnPipe = (Element) node;
//        Integer ex = prosceniumX + x;
//        Integer wy = prosceniumY - (y - 1);
//        assertEquals( elementOnPipe.attribute( "x" ), ex.toString() );
//        assertEquals( elementOnPipe.attribute( "y" ), wy.toString() );
//    }
//
//    @Test
//    public void domPlanNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( elementOnPipe );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element elementOnPipe = (Element) node;
//        assertEquals( elementOnPipe.attribute( "x" ), x.toString() );
//        assertEquals( elementOnPipe.attribute( "y" ), ((Integer) (y - 1)).toString() );
//    }

//    @Test
//    public void domSection() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( elementOnPipe );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 2 );
//        Node groupNode = group.item( 1 );
//        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
//        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.attribute( "class" ), Pipe.LAYERTAG );
//
//        NodeList list = groupElement.getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element elementOnPipe = (Element) node;
//        assertEquals( elementOnPipe.attribute( "width" ), Pipe.DIAMETER.toString() );
//        assertEquals( elementOnPipe.attribute( "height" ), Pipe.DIAMETER.toString() );
//        assertEquals( elementOnPipe.attribute( "fill" ), "none" );
//    }
//
//    @Test
//    public void domSectionProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( elementOnPipe );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element elementOnPipe = (Element) node;
//        Integer wye = prosceniumY - (y - 1);
//        Integer zee = Venue.Height() - (prosceniumZ + z - 1);
//        assertEquals( elementOnPipe.attribute( "x" ), wye.toString() );
//        assertEquals( elementOnPipe.attribute("y"), zee.toString() );
//    }
//
//    @Test
//    public void domSectionNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Pipe pipe = new Pipe( elementOnPipe );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element elementOnPipe = (Element) node;
//        Integer wye = y - 1;
//        Integer zee = Venue.Height() - (z - 1);
//        assertEquals( elementOnPipe.attribute( "x" ), wye.toString() );
//        assertEquals( elementOnPipe.attribute( "y" ), zee.toString() );
//    }

    @Test
    public void parseWithSuspends() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"bill\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"betty\" x=\"7\" y=\"8\" />" +
                "<truss id=\"id\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"bill\" distance=\"3\" />" +
                "<suspend ref=\"betty\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 5 );
    }

//    @Test
//    public void parseWithBase() throws Exception {
//        String xml = "<plot>" +
//                "<truss size=\"12\" length=\"1\" >" +
//                "<base x=\"55\" y=\"27\" />" +
//                "</truss>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestHelpers.MinderDomReset();
//
//        new Parse( stream );
//
//        ArrayList<Minder> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//    }

    @Test
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"roger\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"renee\" x=\"7\" y=\"8\" />" +
                "<truss id=\"id\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"roger\" distance=\"3\" />" +
                "<suspend ref=\"renee\" distance=\"3\" />" +
                "</truss>" +
                "<truss id=\"id2\" size=\"12\" length=\"1\" >" +
                "<suspend ref=\"roger\" distance=\"3\" />" +
                "<suspend ref=\"renee\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 8 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ProsceniumReset();
        TestResets.MountableReset();
        TestResets.MinderDomReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
        hangPoint1.setAttribute( "id", "jim" );
        hangPoint1.setAttribute("x", x1.toString());
        hangPoint1.setAttribute("y", y1.toString());
        hanger1 = new HangPoint( hangPoint1 );

        Element hangPoint2 = new IIOMetadataNode( "hangpoint" );
        hangPoint2.setAttribute( "id", "joan" );
        hangPoint2.setAttribute("x", x2.toString());
        hangPoint2.setAttribute( "y", "200" );
        hanger2 = new HangPoint( hangPoint2 );

        suspendElement1 = new IIOMetadataNode( "suspend" );
        suspendElement1.setAttribute( "ref", "jim" );
        suspendElement1.setAttribute( "distance", "1" );

        suspendElement2 = new IIOMetadataNode( "suspend" );
        suspendElement2.setAttribute( "ref", "joan" );
        suspendElement2.setAttribute( "distance", "2" );

//        baseElement = new IIOMetadataNode( "base" );
//        baseElement.setAttribute( "x", "1" );
//        baseElement.setAttribute( "y", "2" );

        element = new IIOMetadataNode("truss");
        element.setAttribute("id", id);
        element.setAttribute("size", size.toString());
        element.setAttribute("length", length.toString());
        element.appendChild( suspendElement1 );
        element.appendChild( suspendElement2 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}