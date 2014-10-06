package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Proscenium}.
 *
 * @author dhs
 * @since 0.0.7
 */
public class ProsceniumTest {

    Element element = null;

    public ProsceniumTest() {
    }

    @Test
    public void isMinder() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        assert MinderDom.class.isInstance( proscenium );
    }

    @Test
    public void storesAttributes() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        assertEquals( TestHelpers.accessInteger( proscenium, "width" ), (Integer) 330 );
        assertEquals( TestHelpers.accessInteger( proscenium, "depth" ), (Integer) 22 );
        assertEquals( TestHelpers.accessInteger( proscenium, "height" ), (Integer) 250 );
        assertEquals( TestHelpers.accessInteger( proscenium, "x" ), (Integer) 250 );
        assertEquals( TestHelpers.accessInteger( proscenium, "y" ), (Integer) 144 );
        assertEquals( TestHelpers.accessInteger( proscenium, "z" ), (Integer) 12 );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        assertNull( TestHelpers.accessString( proscenium, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Proscenium proscenium = new Proscenium( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( proscenium );
    }

    @Test
    public void active() throws Exception {
        assertFalse( Proscenium.Active() );
        new Proscenium( element );
        assertTrue( Proscenium.Active() );
    }

    @Test
    public void origin() throws Exception {
        TestResets.ProsceniumReset();
        assertNull( Proscenium.Origin() );
        new Proscenium( element );
        Point origin = Proscenium.Origin();
        assert new Point( 250, 144, 12 ).equals( origin );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'width' attribute.")
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'depth' attribute.")
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'height' attribute.")
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'z' attribute.")
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeWidth() throws Exception {
        element.setAttribute( "width", "600" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeDepth() throws Exception {
        element.setAttribute( "depth", "257" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeHeight() throws Exception {
        element.setAttribute( "height", "252" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "436" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "379" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooLargeZ() throws Exception {
        element.setAttribute( "z", "14" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive width.")
    public void tooSmallWidth() throws Exception {
        element.setAttribute( "width", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive depth.")
    public void tooSmallDepth() throws Exception {
        element.setAttribute( "depth", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = SizeException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should have a positive height.")
    public void tooSmallHeight() throws Exception {
        element.setAttribute( "height", "0" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "114" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new Proscenium( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp =
                  "Proscenium should not extend beyond the boundaries of the venue.")
    public void tooSmallZ() throws Exception {
        element.setAttribute( "z", "-1" );
        new Proscenium( element );
    }

    @Test
    public void locateOrigin() throws Exception {
        new Proscenium( element );
        Point fixed = Proscenium.Locate( new Point( 0, 0, 0 ) );
        assert new Point( 250, 144, 12 ).equals( fixed );
    }

    @Test
    public void locateUSRHigh() throws Exception {
        new Proscenium( element );
        Point fixed = Proscenium.Locate( new Point( 100, 120, 60 ) );
//        assert new Point( 150, 24, 72 ).equals( fixed );
        assertEquals( fixed.x(), (Integer) 350, "X" );
        assertEquals( fixed.y(), (Integer) 24, "Y" );
        assertEquals( fixed.z(), (Integer) 72, "Z" );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        // TODO Takes too long
//        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 1 );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
          expectedExceptionsMessageRegExp =
                  "Multiple Prosceniums are not currently supported.")
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        // TODO Takes too long
//        new Parse( stream );

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 2 );
    }

//    @Mocked
//    Graphics2D mockCanvas;

//    @Test
//    public void draw() throws Exception {
//        Proscenium proscenium = new Proscenium( elementOnPipe );
//
//        new Expectations() {
//            {
//                mockCanvas.setPaint( Color.orange );
//                mockCanvas.draw( new Rectangle( 56, 16, 288, 144 ) );
//            }
//        };
//        proscenium.drawPlan( mockCanvas );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        Proscenium proscenium = new Proscenium( elementOnPipe );
//
//        proscenium.dom( null );
//    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Proscenium proscenium = new Proscenium( element );

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        proscenium.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 4 );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85" );
        assertEquals( element.getAttribute( "y1" ), "144" );
        assertEquals( element.getAttribute( "x2" ), "85" );
        assertEquals( element.getAttribute( "y2" ), "166" );
        assertEquals( element.getAttribute( "stroke" ), "black" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "415" );
        assertEquals( element.getAttribute( "y1" ), "144" );
        assertEquals( element.getAttribute( "x2" ), "415" );
        assertEquals( element.getAttribute( "y2" ), "166" );
        assertEquals( element.getAttribute( "stroke" ), "black" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "" );
        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85" );
        assertEquals( element.getAttribute( "y1" ), "144" );
        assertEquals( element.getAttribute( "x2" ), "415" );
        assertEquals( element.getAttribute( "y2" ), "144" );
        assertEquals( element.getAttribute( "stroke" ), "grey" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.3" );
        assertEquals( element.getAttribute( "stroke-width" ), "1" );

        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), "85" );
        assertEquals( element.getAttribute( "y1" ), "166" );
        assertEquals( element.getAttribute( "x2" ), "415" );
        assertEquals( element.getAttribute( "y2" ), "166" );
        assertEquals( element.getAttribute( "stroke" ), "grey" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.1" );
        assertEquals( element.getAttribute( "stroke-width" ), "1" );
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

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "550" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "263" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "proscenium" );
        element.setAttribute( "width", "330" );
        element.setAttribute( "height", "250" );
        element.setAttribute( "depth", "22" );
        element.setAttribute( "x", "250" );
        element.setAttribute( "y", "144" );
        element.setAttribute( "z", "12" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}