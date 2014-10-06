package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 4/15/14.
 *
 * @author dhs
 * @since 0.0.24
 */
public class SegmentTest {

    Element element = null;

    Integer x1=12;
    Integer y1=23;
    Integer x2=34;
    Integer y2=45;

    @Test
    public void isElemental() throws Exception {
        Segment segment = new Segment( element );

        assert Elemental.class.isInstance( segment );
    }

    @Test
    public void storesAttributes() throws Exception {
        Segment segment = new Segment(element);

        assertEquals( TestHelpers.accessInteger( segment, "x1" ), x1);
        assertEquals( TestHelpers.accessInteger( segment, "y1" ), y1);
        assertEquals( TestHelpers.accessInteger( segment, "x2" ), x2);
        assertEquals( TestHelpers.accessInteger( segment, "y2" ), y2);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance is missing required 'x1' attribute.")
    public void noX1() throws Exception {
        element.removeAttribute("x1");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'x1' attribute should not be zero.")
    public void tooSmallX1Zero() throws Exception {
        element.setAttribute("x1", "0");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'x1' attribute should not be negative.")
    public void tooSmallX1() throws Exception {
        element.setAttribute("x1", "-1");
        new Segment(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance is missing required 'y1' attribute.")
    public void noY1() throws Exception {
        element.removeAttribute("y1");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'y1' attribute should not be zero.")
    public void tooSmallY1Zero() throws Exception {
        element.setAttribute("y1", "0");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'y1' attribute should not be negative.")
    public void tooSmallY1() throws Exception {
        element.setAttribute("y1", "-1");
        new Segment(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance is missing required 'x2' attribute.")
    public void noX2() throws Exception {
        element.removeAttribute("x2");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'x2' attribute should not be zero.")
    public void tooSmallX2Zero() throws Exception {
        element.setAttribute("x2", "0");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'x2' attribute should not be negative.")
    public void tooSmallX2() throws Exception {
        element.setAttribute("x2", "-1");
        new Segment(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance is missing required 'y2' attribute.")
    public void noY2() throws Exception {
        element.removeAttribute("y2");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'y2' attribute should not be zero.")
    public void tooSmallY2Zero() throws Exception {
        element.setAttribute("y2", "0");
        new Segment(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Segment instance value for 'y2' attribute should not be negative.")
    public void tooSmallY2() throws Exception {
        element.setAttribute("y2", "-1");
        new Segment(element);
    }

//    @Test(expectedExceptions = LocationException.class,
//            expectedExceptionsMessageRegExp =
//                    "Segment instance should not extend beyond the boundaries of the venue.")
//    public void tooLargeX1() throws Exception {
//        diversionElement.setAttribute("x1", "195");
//        Segment segment = new Segment(diversionElement);
//        segment.verify();
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Segment(element);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
//        draw = new Draw();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "segment" );

        element.setAttribute( "x1", x1.toString() );
        element.setAttribute( "y1", y1.toString() );
        element.setAttribute( "x2", x2.toString() );
        element.setAttribute( "y2", y2.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
