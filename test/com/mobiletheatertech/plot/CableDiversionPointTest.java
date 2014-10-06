package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 9/16/14.
 */
public class CableDiversionPointTest {

    Element element = null;

    Integer x = 12;
    Integer y = 24;
    Integer z = 48;

    Point point = new Point( x, y, z );
//    String category = "Name of Category";

    @Test
    public void isA() throws Exception {
        CableDiversionPoint diversionPoint = new CableDiversionPoint( element );

        assert Elemental.class.isInstance( diversionPoint );
    }

    @Test
    public void storesAttributes() throws Exception {
        CableDiversionPoint diversionPoint = new CableDiversionPoint( element );

        assertEquals( TestHelpers.accessPoint( diversionPoint, "point" ), point );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "CableDiversionPoint instance is missing required 'x' attribute." )
    public void noX() throws Exception {
        element.removeAttribute( "x" );
        new CableDiversionPoint( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "CableDiversionPoint instance is missing required 'y' attribute." )
    public void noY() throws Exception {
        element.removeAttribute( "y" );
        new CableDiversionPoint( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "CableDiversionPoint instance is missing required 'z' attribute." )
    public void noZ() throws Exception {
        element.removeAttribute( "z" );
        new CableDiversionPoint( element );
    }

    @Test
    public void point() throws Exception {
        CableDiversionPoint diversionPoint = new CableDiversionPoint( element );

        assertEquals( diversionPoint.point(), point );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "diversion-point" );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
        element.setAttribute( "z", z.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
