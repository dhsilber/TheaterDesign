package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Test {@code HangPoint }
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPointTest {

    Element element = null;

    public HangPointTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assert MinderDom.class.isInstance( hangPoint );
    }

    @Test
    public void storesAttributes() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        assertEquals( TestHelpers.accessString( hangPoint, "id" ), "Blather" );
        assertEquals( TestHelpers.accessInteger( hangPoint, "x" ), (Integer) 296 );
        assertEquals( TestHelpers.accessInteger( hangPoint, "y" ), (Integer) 320 );
    }

    @Test
    public void storesSelf() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        ArrayList<MinderDom> thing = Drawable.List();

        assert thing.contains( hangPoint );
    }

    @Test
    public void registersLayer() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        HashMap<String, String> layers = Layer.List();

        assertTrue( layers.containsKey( HangPoint.LAYERNAME ) );
        assertEquals( layers.get( HangPoint.LAYERNAME ), HangPoint.LAYERTAG );
    }

    @Test
    public void finds() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    @Test
    public void findsNothing() throws Exception {
        HangPoint found = HangPoint.Find( "Nothing" );

        assertNull( found );
    }

    @Test
    public void findIgnoresOther() throws Exception {
        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "x", "2" );
        pipeElement.setAttribute( "y", "4" );
        pipeElement.setAttribute( "z", "6" );
        new Pipe( pipeElement );
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    @Test
    public void findIgnoresOtherHangPoint() throws Exception {
        Element hangpointElement = new IIOMetadataNode( "hangpoint" );
        hangpointElement.setAttribute( "id", "Not Our Victim" );
        hangpointElement.setAttribute( "x", "2" );
        hangpointElement.setAttribute( "y", "4" );
        new HangPoint( hangpointElement );
        HangPoint hangPoint = new HangPoint( element );

        HangPoint found = HangPoint.Find( "Blather" );

        assertSame( found, hangPoint );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new HangPoint( element );
    }

    /**
     * The lack of an id attribute is not an error.
     *
     * @throws Exception
     */
    @Test
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint \\(Blather\\) is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint instance is missing required 'x' attribute.")
    public void noXWithoutID() throws Exception {
        element.removeAttribute( "id" );
        element.removeAttribute( "x" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint \\(Blather\\) is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "HangPoint instance is missing required 'y' attribute.")
    public void noYWithoutID() throws Exception {
        element.removeAttribute( "id" );
        element.removeAttribute( "y" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint x value outside boundary of the venue")
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "351" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint y value outside boundary of the venue")
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "401" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint x value outside boundary of the venue")
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "-1" );
        new HangPoint( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "HangPoint y value outside boundary of the venue")
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new HangPoint( element );
    }


    @Test
    public void parseTwoHangPoints() throws Exception {
        String xml = "<plot>" +
                "<hangpoint x=\"20\" y=\"30\" />" +
                "<hangpoint x=\"25\" y=\"35\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        new Parse( stream );

        // Final size of list
        ArrayList<MinderDom> list = Drawable.List();
        assertEquals( list.size(), 2 );

        MinderDom hangpoint = list.get( 0 );
        assert MinderDom.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );

        MinderDom hangpoint2 = list.get( 1 );
        assert MinderDom.class.isInstance( hangpoint2 );
        assert HangPoint.class.isInstance( hangpoint2 );

        assertNotSame( hangpoint, hangpoint2 );
    }

    @Test
    public void locate() throws Exception {
        HangPoint hangPoint = new HangPoint( element );

        Point location = hangPoint.locate();
        assertEquals( location.x(), (Integer) 296 );
        assertEquals( location.y(), (Integer) 320 );
        assertEquals( location.z(), (Integer) 240 );
    }

//    @Mocked
//    Graphics2D mockCanvas;
//
//    @Test
//    public void drawPlan() throws Exception {
//        HangPoint hangPoint = new HangPoint( element );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.BLUE );
//                mockCanvas.draw( new Line2D.Float( 294, 318, 298, 322 ) );
//                mockCanvas.draw( new Line2D.Float( 298, 318, 294, 322 ) );
//            }
//        };
//        hangPoint.drawPlan( mockCanvas );
//    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        HangPoint hangPoint = new HangPoint( element );

        hangPoint.dom( draw, View.PLAN );

//        NodeList list = draw.root().getElementsByTagName( "use" );
        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), HangPoint.LAYERTAG );

        NodeList list = groupElement.getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#hangpoint" );
    }

//    @Test
//    public void domUnused() throws Exception {
//        HangPoint hangPoint = new HangPoint( element );
//
//        hangPoint.dom( null, View.PLAN );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();
        TestResets.MinderDomReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "hangpoint" );
        element.setAttribute( "id", "Blather" );
        element.setAttribute( "x", "296" );
        element.setAttribute( "y", "320" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}