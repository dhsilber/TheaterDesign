package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

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

        assert Minder.class.isInstance( proscenium );
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

        ArrayList<Minder> thing = Drawable.List();

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

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'depth' attribute." )
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'height' attribute." )
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'x' attribute." )
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'y' attribute." )
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Proscenium instance is missing required 'z' attribute." )
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeWidth() throws Exception {
        element.setAttribute( "width", "600" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeDepth() throws Exception {
        element.setAttribute( "depth", "257" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeHeight() throws Exception {
        element.setAttribute( "height", "252" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "436" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "379" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooLargeZ() throws Exception {
        element.setAttribute( "z", "14" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should have a positive width." )
    public void tooSmallWidth() throws Exception {
        element.setAttribute( "width", "0" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should have a positive depth." )
    public void tooSmallDepth() throws Exception {
        element.setAttribute( "depth", "0" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should have a positive height." )
    public void tooSmallHeight() throws Exception {
        element.setAttribute( "height", "0" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "114" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new Proscenium( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Proscenium should not extend beyond the boundaries of the venue." )
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
        assertEquals( fixed.z(), 72, "Z" );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderReset();

        new Parse( stream );

        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 1 );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp =
                   "Multiple Prosceniums are not currently supported." )
    public void parseMultiple() throws Exception {
        String xml = "<plot>" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "<proscenium width=\"12\" height=\"22\" depth=\"65\" x=\"30\" y=\"6\" z=\"9\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderReset();

        new Parse( stream );

        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 2 );
    }

//    @Mocked
//    Graphics2D mockCanvas;

//    @Test
//    public void draw() throws Exception {
//        Proscenium proscenium = new Proscenium( element );
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
//        Proscenium proscenium = new Proscenium( element );
//
//        proscenium.dom( null );
//    }

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
        venueElement.setAttribute( "name", "Test Name" );
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