package com.mobiletheatertech.plot;

import mockit.Expectations;
import mockit.Mocked;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.*;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code Venue}.
 *
 * @author dhs
 * @since 0.0.2
 */
public class VenueTest {

    Element element = null;
    String building = "Building Name";
    String room = "Room Name";

    public VenueTest() {
    }

    @Test
    public void isMinder() throws Exception {
        Venue venue = new Venue( element );

        assert Minder.class.isInstance( venue );
    }

    @Test
    public void hasOnetoone() throws Exception {
        new Venue( element );

        assertEquals( Venue.ONETOONE, "one-to-one" );
    }

    @Test
    public void hasOnetomany() throws Exception {
        new Venue( element );

        assertEquals( Venue.ONETOMANY, "one-to-many" );
    }

    @Test
    public void storesAttributes() throws Exception {
//        element.removeAttribute( "circuiting" );
        element.removeAttribute( "building" );

        Venue venue = new Venue( element );

        assertEquals( TestHelpers.accessString( venue, "building" ), "" );
        assertEquals( TestHelpers.accessString( venue, "room" ), room );
        assertEquals( TestHelpers.accessInteger( venue, "width" ), (Integer) 1296 );
        assertEquals( TestHelpers.accessInteger( venue, "depth" ), (Integer) 1320 );
        assertEquals( TestHelpers.accessInteger( venue, "height" ), (Integer) 240 );
        assertEquals( TestHelpers.accessString( venue, "circuiting" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "circuiting", "one-to-one" );

        Venue venue = new Venue( element );

        assertEquals( TestHelpers.accessString( venue, "building" ), building );
        assertEquals( TestHelpers.accessString( venue, "room" ), room );
        assertEquals( TestHelpers.accessInteger( venue, "width" ), (Integer) 1296 );
        assertEquals( TestHelpers.accessInteger( venue, "depth" ), (Integer) 1320 );
        assertEquals( TestHelpers.accessInteger( venue, "height" ), (Integer) 240 );
        assertEquals( TestHelpers.accessString( venue, "circuiting" ), "one-to-one" );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Venue venue = new Venue( element );

        assertNull( TestHelpers.accessString( venue, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Venue venue = new Venue( element );
        ArrayList<MinderDom> thing = Drawable.List();

        assert thing.contains( venue );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains() throws Exception {
        Point point = new Point( 1, 2, 3 );
        Box box = new Box( point, 4, 5, 6 );
        Venue.Contains( box );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains2Dboolean() throws Exception {
        Rectangle rectangle = new Rectangle( 1, 2, 3, 4 );
        Venue.Contains2D( rectangle );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueContains2Dint() throws Exception {
        Venue.Contains2D( 1, 2 );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueName() throws Exception {
        Venue.Name();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueWidth() throws Exception {
        Venue.Width();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueDepth() throws Exception {
        Venue.Depth();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueHeight() throws Exception {
        Venue.Height();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueCircuiting() throws Exception {
        Venue.Circuiting();
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Venue is not defined." )
    public void noVenueBuilding() throws Exception {
        Venue.Building();
    }

    @Test
    public void storesExtremePoints() throws Exception {

        TestResets.PointReset();

        new Venue( element );

        assertEquals( Point.LargeX(), 1296 );
        assertEquals( Point.LargeY(), 1320 );
        assertEquals( Point.LargeZ(), 240 );
        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'room' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "room" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'depth' attribute." )
    public void noDepth() throws Exception {
        element.removeAttribute( "depth" );
        new Venue( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Venue instance is missing required 'height' attribute." )
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Venue( element );
    }

    @Test
    public void containsRectangle() throws Exception {
        new Venue( element );

        assert Venue.Contains2D( new Rectangle( 0, 0, 1296, 1320 ) );
        assert Venue.Contains2D( new Rectangle( 1, 1, 1295, 1319 ) );
        assert !Venue.Contains2D( new Rectangle( -1, -1, 1296, 1320 ) );
    }

    @Test
    public void containsCoordinate() throws Exception {
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
    public void containsBoxFits() throws Exception {
        new Venue( element );
        Point point = new Point( 2, 4, 6 );
        Box box = new Box( point, 33, 55, 11 );

        assertTrue( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooWide() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 1296, 55, 11 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooDeep() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 1320, 11 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxTooTall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 55, 240 );

        assertFalse( Venue.Contains( box ) );
    }


    @Test
    public void containsBoxOriginXTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( -1, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1297, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, -1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1321, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, -1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws Exception {
        new Venue( element );
        Point point = new Point( 1, 1, 241 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( Venue.Contains( box ) );
    }

    @Test
    public void height() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Height(), 24 );

        new Venue( element );

        assertEquals( Venue.Height(), 240 );
    }

    @Test
    public void width() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Width(), 129 );

        new Venue( element );

        assertEquals( Venue.Width(), 1296 );
    }

    @Test
    public void depth() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Depth(), 132 );

        new Venue( element );

        assertEquals( Venue.Depth(), 1320 );
    }

    @Test
    public void room() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", "Venue Name" );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Name(), "Venue Name" );

        new Venue( element );

        assertEquals( Venue.Name(), room );
    }

    @Test
    public void building() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "building", building );
        venueElement.setAttribute( "room", room );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );

        new Venue( venueElement );

        assertEquals( Venue.Building(), building );

        element.removeAttribute( "building" );

        new Venue( element );

        assertEquals( Venue.Building(), "" );
    }

    @Test
    public void circuiting() throws Exception {
        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "room", room );
        venueElement.setAttribute( "width", "129" );
        venueElement.setAttribute( "depth", "132" );
        venueElement.setAttribute( "height", "24" );
        venueElement.setAttribute( "circuiting", "one-to-many" );

        new Venue( venueElement );

        assertEquals( Venue.Circuiting(), "one-to-many" );

        new Venue( element );

        assertEquals( Venue.Circuiting(), "" );
    }

    @Test
    public void circuitingOne() throws Exception {
        element.setAttribute( "circuiting", "one-to-one" );
        new Venue( element );

        assertEquals( Venue.Circuiting(), "one-to-one" );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "'circuiting' attribute invalid." )
    public void circuitingInvalid() throws Exception {
        element.setAttribute( "circuiting", "bogus" );
        new Venue( element );
    }

    @Mocked
    Graphics2D mockCanvas;

    @Test
    public void drawPlan() throws Exception {
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
    public void drawSection() throws Exception {
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
    public void drawFront() throws Exception {
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
        // See ParseTest.createsVenue*()
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();

        element = new IIOMetadataNode();
        element.setAttribute( "building", building );
        element.setAttribute( "room", room );
        element.setAttribute( "width", "1296" );
        element.setAttribute( "depth", "1320" );
        element.setAttribute( "height", "240" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}