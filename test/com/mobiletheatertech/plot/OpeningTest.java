package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/26/13 Time: 1:34 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.12
 */
public class OpeningTest {

    Element element = null;

    Double height = 23.0;
    Double width = 25.0;
    Double start = 2.0;


    @Test
    public void isElemental() throws Exception {
        Opening opening = new Opening( element );

        assert Elemental.class.isInstance( opening );
    }

    @Test
    public void storesAttributes() throws Exception {
        Opening opening = new Opening( element );

        assertEquals( TestHelpers.accessDouble(opening, "height"), height );
        assertEquals( TestHelpers.accessDouble(opening, "width"), width );
        assertEquals( TestHelpers.accessDouble( opening, "start" ), start );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Opening instance is missing required 'height' attribute." )
    public void noHeight() throws Exception {
        element.removeAttribute( "height" );
        new Opening( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Opening instance is missing required 'width' attribute." )
    public void noWidth() throws Exception {
        element.removeAttribute( "width" );
        new Opening( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Opening instance is missing required 'start' attribute." )
    public void noStart() throws Exception {
        element.removeAttribute( "start" );
        new Opening( element );
    }

    @Test
    public void start() throws Exception {
        Opening opening = new Opening( element );

        assertEquals( opening.start(), start );
    }

    @Test
    public void width() throws Exception {
        Opening opening = new Opening( element );

        assertEquals( opening.width(), width );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "opening" );
        element.setAttribute( "height", height.toString() );
        element.setAttribute( "width", width.toString() );
        element.setAttribute( "start", start.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
