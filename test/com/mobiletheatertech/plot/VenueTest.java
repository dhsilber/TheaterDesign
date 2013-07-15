package com.mobiletheatertech.plot;

import mockit.Expectations;
import mockit.Mocked;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Venue}
 *
 * @author dhs
 * @since 0.0.2
 */
public class VenueTest {

    Element element = null;

    public VenueTest() {
    }

    @Test
    public void isMinder() throws AttributeMissingException {
        Venue venue = new Venue( element );

        assert Minder.class.isInstance( venue );
    }

    @Test
    public void storesAttributes() throws Exception {
        Venue venue = new Venue( element );

        Field nameField = Venue.class.getDeclaredField( "name" );
        nameField.setAccessible( true );
        String name = (String) nameField.get( venue );
        assert name.equals( "Test Name" );

        assertEquals( TestHelpers.accessInteger( venue, "width" ), 1296 );
        assertEquals( TestHelpers.accessInteger( venue, "depth" ), 1320 );
        assertEquals( TestHelpers.accessInteger( venue, "height" ), 240 );
    }

    @Test
    public void storesSelf() throws AttributeMissingException {
        Venue venue = new Venue( element );
        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( venue );
    }

    @Test
    public void storesExtremePoints() throws Exception {

        TestHelpers.PointReset();

        new Venue( element );

        assertEquals( Point.LargeX(), 1296 );
        assertEquals( Point.LargeY(), 1320 );
        assertEquals( Point.LargeZ(), 240 );
        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue is missing required 'depth' attribute." )
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue is missing required 'height' attribute." )
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Venue( element );
    }

    @Test
    public void containsRectangle() throws AttributeMissingException {
        new Venue( element );

        assert Venue.Contains2D( new Rectangle( 0, 0, 1296, 1320 ) );
        assert Venue.Contains2D( new Rectangle( 1, 1, 1295, 1319 ) );
        assert !Venue.Contains2D( new Rectangle( -1, -1, 1296, 1320 ) );
    }

    @Test
    public void containsCoordinate() throws AttributeMissingException {
        new Venue( element );

        assertEquals( Venue.Contains2D( 1295, 1319 ), 0 );
        assertEquals( Venue.Contains2D( 1, 1 ), 0 );
        assertEquals( Venue.Contains2D( 0, 0 ), 0 );
        assertEquals( Venue.Contains2D( -1, 1 ), Rectangle.OUT_LEFT );
        assertEquals( Venue.Contains2D( 1, -1 ), Rectangle.OUT_TOP );
        assertEquals( Venue.Contains2D( 1297, 1320 ), Rectangle.OUT_RIGHT );
        assertEquals( Venue.Contains2D( 1296, 1321 ), Rectangle.OUT_BOTTOM );
    }

    @Test
    public void containsBoxFits() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 2, 4, 6 );
        Box box = new Box( point, 33, 55, 11 );

        assertTrue( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooWide() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 1296, 55, 11 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooDeep() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 1320, 11 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooTall() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 55, 240 );

        assertFalse( Venue.Contains( box ) );
    }


    @Test
    public void containsBoxOriginXTooSmall() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( -1, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1297, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, -1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1321, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1, -1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws AttributeMissingException {
        new Venue( element );
        Point point = new Point( 1, 1, 241 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void height() throws AttributeMissingException {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Height(), 24 );

        new Venue( element );

        assertEquals( Venue.Height(), 240 );
    }

    @Test
    public void width() throws AttributeMissingException {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Width(), 129 );

        new Venue( element );

        assertEquals( Venue.Width(), 1296 );
    }

    @Test
    public void depth() throws AttributeMissingException {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Depth(), 132 );

        new Venue( element );

        assertEquals( Venue.Depth(), 1320 );
    }

    @Mocked
    Graphics2D mockCanvas;

    @Test
    public void drawPlan() throws AttributeMissingException {
        Venue venue = new Venue( element );

        new Expectations() {
            {
                mockCanvas.setPaint( Color.BLACK );
                mockCanvas.draw( new Rectangle( 0, 0, 1296, 1320 ) );
            }
        };
        venue.drawPlan( mockCanvas );
    }

    @Test
    public void drawSection() throws AttributeMissingException {
        Venue venue = new Venue( element );

        new Expectations() {
            {
                mockCanvas.setPaint( Color.BLACK );
                mockCanvas.draw( new Rectangle( 0, 0, 1320, 240 ) );
            }
        };
        venue.drawSection( mockCanvas );
    }

    @Test
    public void drawFront() throws AttributeMissingException {
        Venue venue = new Venue( element );

        new Expectations() {
            {
                mockCanvas.setPaint( Color.BLACK );
                mockCanvas.draw( new Rectangle( 0, 0, 1296, 240 ) );
            }
        };
        venue.drawFront( mockCanvas );
    }


    @Test
    public void parseXML() {
        fail( "Must create test" );
    }

    @Test
    public void domSetsTitle() {
        fail( "Must create test" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode();
        element.setAttribute( "name", "Test Name" );
        element.setAttribute( "width", "1296" );
        element.setAttribute( "depth", "1320" );
        element.setAttribute( "height", "240" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}