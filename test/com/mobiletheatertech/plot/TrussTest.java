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
Integer size = 12;
    Integer length = 320;
    Integer x1 = 100;
    Integer y1 = 200;
    Integer x2 = 180;

    public TrussTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        Truss truss = new Truss( element );

        assert MinderDom.class.isInstance(truss);
    }

    @Test
    public void storesAttributes() throws Exception {
        Truss truss = new Truss( element );

        assertEquals( TestHelpers.accessInteger( truss, "size" ), (Integer) 12 );
        assertEquals( TestHelpers.accessInteger( truss, "length" ), (Integer) 320 );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Truss truss = new Truss( element );

        assertNull( TestHelpers.accessString( truss, "id" ) );
    }

    @Test
    public void marksProcessed() throws Exception {
        String emptyMark = element.getAttribute( "processedMark" );
        assertEquals( emptyMark, "", "Should be unset" );

        Truss truss = new Truss( element );

        String trussMark = TestHelpers.accessString( truss, "processedMark" );
        String elementMark = element.getAttribute( "processedMark" );
        assertNotNull( trussMark );
        assertNotEquals( trussMark, "", "Should be set in Truss object" );
        assertNotEquals( elementMark, "", "Should be set in Element" );
        assertEquals( trussMark, elementMark, "should match" );
    }

    @Test
    public void findNull() throws Exception {
        new Truss( element );

        Truss found = Truss.Find( null );

        assertNull( found );
    }

    @Test
    public void findsMarked() throws Exception {
        Truss truss = new Truss( element );

        Truss found = Truss.Find( element.getAttribute( "processedMark" ) );

        assertSame( found, truss );
    }

    @Test
    public void storesElement() throws Exception {
        Truss truss = new Truss( element );

        Field elementField = TestHelpers.accessField( truss, "element" );
        Element elementReference = (Element) elementField.get( truss );

        assertSame( elementReference, element );
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
          expectedExceptionsMessageRegExp = "Truss must have exactly two suspend children")
    public void verifyNoSuspends() throws Exception {
        element.removeChild( suspendElement1 );
        element.removeChild( suspendElement2 );
        Truss truss = new Truss( element );

        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss must have exactly two suspend children")
    public void verifyTooFewSuspends() throws Exception {
        element.removeChild( suspendElement1 );
        Truss truss = new Truss( element );

        truss.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp = "Truss must have exactly two suspend children")
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
//        element.removeChild( suspendElement1 );
//        element.removeChild( suspendElement2 );
//        element.appendChild( baseElement );
//        Truss truss = new Truss( element );
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
          expectedExceptionsMessageRegExp = "Truss instance is missing required 'size' attribute.")
    public void noSize() throws Exception {
        element.removeAttribute( "size" );
        new Truss( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Truss instance is missing required 'length' attribute.")
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
//        element.setAttribute( "depth", "401");
//        Truss truss = new Truss( element );
//    }
//    
//    @Test( expectedExceptions = StructureException.class,
//        expectedExceptionsMessageRegExp = 
//        "Truss should not extend beyond the boundaries of the venue")
//    public void tooFewSuspend() throws Exception {
//        fail( "Need to test this in concert with suspend");
//        element.setAttribute( "depth", "401");
//        Truss truss = new Truss( element );
//    }

    @Test
    public void parseWithSuspends() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"jim\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"joan\" x=\"7\" y=\"8\" />" +
                "<truss size=\"12\" length=\"1\" >" +
                "<suspend ref=\"jim\" distance=\"3\" />" +
                "<suspend ref=\"joan\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<MinderDom> list = Drawable.List();
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
                "<hangpoint id=\"jim\" x=\"7\" y=\"8\" />" +
                "<hangpoint id=\"joan\" x=\"7\" y=\"8\" />" +
                "<truss size=\"12\" length=\"1\" >" +
                "<suspend ref=\"jim\" distance=\"3\" />" +
                "<suspend ref=\"joan\" distance=\"3\" />" +
                "</truss>" +
                "<truss size=\"12\" length=\"1\" >" +
                "<suspend ref=\"jim\" distance=\"3\" />" +
                "<suspend ref=\"joan\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<MinderDom> list = Drawable.List();
        assertEquals( list.size(), 8 );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
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
        assertEquals(element.getAttribute("stroke"), "green");
        Integer x = x1 - (length - (x2 - x1)) / 2;
        assertEquals(element.getAttribute("x"), x.toString());
        Integer y = y1 - size / 2;
        assertEquals(element.getAttribute("y"), y.toString());
        assertEquals(element.getAttribute("transform"), "rotate(-0.0," + x1.toString() + "," + y1.toString() + ")");
    }

//    @Test
//    public void domPlanProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( element );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        Integer ex = prosceniumX + x;
//        Integer wy = prosceniumY - (y - 1);
//        assertEquals( element.getAttribute( "x" ), ex.toString() );
//        assertEquals( element.getAttribute( "y" ), wy.toString() );
//    }
//
//    @Test
//    public void domPlanNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        Pipe pipe = new Pipe( element );
//        pipe.verify();
//
//        pipe.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "x" ), x.toString() );
//        assertEquals( element.getAttribute( "y" ), ((Integer) (y - 1)).toString() );
//    }

//    @Test
//    public void domSection() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        Pipe pipe = new Pipe( element );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 2 );
//        Node groupNode = group.item( 1 );
//        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
//        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.getAttribute( "class" ), Pipe.LAYERTAG );
//
//        NodeList list = groupElement.getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "width" ), Pipe.DIAMETER.toString() );
//        assertEquals( element.getAttribute( "height" ), Pipe.DIAMETER.toString() );
//        assertEquals( element.getAttribute( "fill" ), "none" );
//    }
//
//    @Test
//    public void domSectionProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        new Proscenium( prosceniumElement );
//        Pipe pipe = new Pipe( element );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        Integer wye = prosceniumY - (y - 1);
//        Integer zee = Venue.Height() - (prosceniumZ + z - 1);
//        assertEquals( element.getAttribute( "x" ), wye.toString() );
//        assertEquals( element.getAttribute("y"), zee.toString() );
//    }
//
//    @Test
//    public void domSectionNoProscenium() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        Pipe pipe = new Pipe( element );
//        pipe.verify();
//
//        pipe.dom( draw, View.SECTION );
//
//        NodeList list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        Integer wye = y - 1;
//        Integer zee = Venue.Height() - (z - 1);
//        assertEquals( element.getAttribute( "x" ), wye.toString() );
//        assertEquals( element.getAttribute( "y" ), zee.toString() );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
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
        element.setAttribute("size", size.toString());
        element.setAttribute("length", length.toString());
        element.appendChild( suspendElement1 );
        element.appendChild( suspendElement2 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}