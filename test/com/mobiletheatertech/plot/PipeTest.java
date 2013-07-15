package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Test {@code Pipe}.
 *
 * @author dhs
 * @since 0.0.6
 */
public class PipeTest {

    Element element = null;

    public PipeTest() {

    }

    @Test
    public void isMinder() throws Exception {
        Pipe pipe = new Pipe( element );

        assert Minder.class.isInstance( pipe );
    }

    @Test
    public void storesAttributes() throws Exception {
        Pipe pipe = new Pipe( element );

        assertEquals( TestHelpers.accessInteger( pipe, "length" ), 120 );
        assertTrue( new Point( 12, 23, 34 ).equals( TestHelpers.accessPoint( pipe, "origin" ) ) );
        assertEquals( TestHelpers.accessPoint( pipe, "origin" ), new Point( 12, 23, 34 ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Pipe pipe = new Pipe( element );

        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( pipe );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Pipe( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Pipe is missing required 'length' attribute." )
    public void noLength() throws Exception {
        element.removeAttribute( "length" );
        new Pipe( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Pipe is missing required 'x' attribute." )
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new Pipe( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Pipe is missing required 'y' attribute." )
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new Pipe( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Pipe is missing required 'z' attribute." )
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new Pipe( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should have a positive length." )
    public void tooSmallLengthZero() throws Exception {
        element.setAttribute( "length", "0" );
        new Pipe( element );
    }

    @Test( expectedExceptions = SizeException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should have a positive length." )
    public void tooSmallLength() throws Exception {
        element.setAttribute( "length", "-1" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooLargeLength() throws Exception {
        element.setAttribute( "length", "339" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooSmallX() throws Exception {
        element.setAttribute( "x", "-1" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooLargeX() throws Exception {
        element.setAttribute( "x", "351" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooSmallY() throws Exception {
        element.setAttribute( "y", "-1" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooLargeY() throws Exception {
        element.setAttribute( "y", "401" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooSmallZ() throws Exception {
        element.setAttribute( "z", "-1" );
        new Pipe( element );
    }

    @Test( expectedExceptions = LocationException.class,
           expectedExceptionsMessageRegExp =
                   "Pipe should not extend beyond the boundaries of the venue." )
    public void tooLargeZ() throws Exception {
        element.setAttribute( "z", "241" );
        new Pipe( element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode();
        element.setAttribute( "length", "120" );  // 10' pipe.
        element.setAttribute( "x", "12" );
        element.setAttribute( "y", "23" );
        element.setAttribute( "z", "34" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
