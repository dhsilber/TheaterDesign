package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/17/13 Time: 5:37 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Test {@code Airwall}
 *
 * @author dhs
 * @since 0.0.8
 */
public class AirwallTest {

    Element element = null;

    @Test
    public void isMinder() throws Exception {
        Airwall airwall = new Airwall( element );

        assert Minder.class.isInstance( airwall );
    }

    @Test
    public void storesAttributes() throws Exception {
        Airwall airwall = new Airwall( element );

        assertEquals( TestHelpers.accessInteger( airwall, "depth" ), (Integer) 77 );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp = "Airwall instance is at too small a depth")
    public void depthTooSmall() throws Exception {
        element.setAttribute( "depth", "0" );
        new Airwall( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp = "Airwall instance is at too large a depth")
    public void depthTooLarge() throws Exception {
        element.setAttribute( "depth", "400" );
        new Airwall( element );
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
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "airwall" );
        element.setAttribute( "depth", "77" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }


}
