package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.*;


/**
 * @author dhs
 * @since 0.0.5
 */
public class SuspendTest {

    Element element = null;
    HangPoint hanger1 = null;
    Truss truss = null;

    public SuspendTest() {
    }

    @Test
    public void isMinder() throws Exception {
        Suspend suspend = new Suspend( element );

        assert Minder.class.isInstance( suspend );
    }

    @Test
    public void storesAttributes() throws Exception {
        Suspend suspend = new Suspend( element );

        assertEquals( TestHelpers.accessString( suspend, "refId" ), "jim" );
        assertEquals( TestHelpers.accessInteger( suspend, "distance" ), 32 );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Suspend suspend = new Suspend( element );

        assertNull( TestHelpers.accessString( suspend, "id" ) );
    }

/*

    @Test
    public void storesSelf() throws Exception
    {
        Suspend suspend = new Suspend( element );

        ArrayList<Minder> thing = Drawable.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( suspend );
        fail( "Move this to DrawableTest" );
    }
*/

    @Test
    public void marksProcessed() throws Exception {
        String emptyMark = element.getAttribute( "processedMark" );
        assertEquals( emptyMark, "", "Should be unset" );

        Suspend suspend = new Suspend( element );

        String suspendMark = TestHelpers.accessString( suspend, "processedMark" );
        String elementMark = element.getAttribute( "processedMark" );
        assertNotNull( suspendMark );
        assertNotEquals( suspendMark, "", "Should be set in Suspend object" );
        assertNotEquals( elementMark, "", "Should be set in Element" );
        assertEquals( suspendMark, elementMark, "should match" );
    }

    @Test
    public void findNull() throws Exception {
        new Suspend( element );

        Suspend found = Suspend.Find( null );

        assertNull( found );
    }

    @Test
    public void findsMarked() throws Exception {
        Suspend suspend = new Suspend( element );

        Suspend found = Suspend.Find( element.getAttribute( "processedMark" ) );

        assertSame( found, suspend );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Suspend( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp =
                   "Suspend instance is missing required 'ref' attribute." )
    public void noRef() throws Exception {
        element.removeAttribute( "ref" );
        new Suspend( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp =
                   "Suspend instance is missing required 'distance' attribute." )
    public void noDistanceWithoutID() throws Exception {
        element.removeAttribute( "distance" );
        new Suspend( element );
    }

    @Test
    public void noDistance() {
        fail( "Missing distance attribute exception message should mention id of referenced HangPoint." );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Cannot suspend from unknown hangpoint ref 302." )
    public void missingRefTarget() throws Exception {
        element.setAttribute( "ref", "302" );
        new Suspend( element );
    }

    @Test
    public void referencesHangPoint() throws Exception {
        Suspend suspend = new Suspend( element );
        Field hangPointField = TestHelpers.accessField( suspend, "ref" );
        HangPoint hangPoint = (HangPoint) hangPointField.get( suspend );

        assertNotNull( hangPoint );
        assertSame( hangPoint, hanger1 );
    }

/*
    @Test
    public void associatesWithParentTruss() throws Exception
    {
        Suspend suspend = new Suspend( element );
        Field parentTrussField = TestHelpers.accessField( suspend, "truss" );
        Truss parentTruss = (Truss) parentTrussField.get( suspend );

        assertNotNull( parentTruss );
        assertSame( parentTruss, truss );
        Check that parent truss has reference to this suspend.

    }
*/

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<hangpoint id=\"victoria\" x=\"20\" y=\"30\" />" +
                "<hangpoint id=\"albert\" x=\"25\" y=\"35\" />" +
                "<truss size=\"12\" length=\"10\" >" +
                "<suspend ref=\"albert\" distance=\"1\" />" +
                "<suspend ref=\"victoria\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderReset();

        new Parse( stream );

        // Final size of list
        ArrayList<Minder> list = Drawable.List();
        assertEquals( list.size(), 5 );

        Minder hangpoint = list.get( 0 );
        assert Minder.class.isInstance( hangpoint );
        assert HangPoint.class.isInstance( hangpoint );

        Minder hangpoint2 = list.get( 1 );
        assert Minder.class.isInstance( hangpoint2 );
        assert HangPoint.class.isInstance( hangpoint2 );

        Minder truss = list.get( 2 );
        assert Minder.class.isInstance( truss );
        assert Truss.class.isInstance( truss );

        Minder suspend1 = list.get( 3 );
        assert Minder.class.isInstance( suspend1 );
        assert Suspend.class.isInstance( suspend1 );

        Minder suspend2 = list.get( 4 );
        assert Minder.class.isInstance( suspend2 );
        assert Suspend.class.isInstance( suspend2 );

        assertNotSame( suspend1, suspend2 );

    }

    @Test
    public void locate() throws Exception {
        Suspend suspend = new Suspend( element );

        Point location = suspend.locate();

        assertEquals( location.x(), (Integer) 100 );
        assertEquals( location.y(), (Integer) 200 );
        assertEquals( location.z(), 208 );
    }

    @Test
    public void drawUnused() throws Exception {
        Suspend suspend = new Suspend( element );

        suspend.drawPlan( null );
    }

    @Test
    public void domUnused() throws Exception {
        Suspend suspend = new Suspend( element );

        suspend.dom( null, View.PLAN );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

//    @Mocked
//    Venue mockVenue;


    @BeforeMethod
    public void setUpMethod() throws Exception {
        System.err.println( "Starting SuspendTest method." );

        TestResets.MinderReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Suspend Venue Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        Venue venue = new Venue( venueElement );
        venue.getClass();
        Venue.Height();

        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
        hangPoint1.setAttribute( "id", "jim" );
        hangPoint1.setAttribute( "x", "100" );
        hangPoint1.setAttribute( "y", "200" );
        hanger1 = new HangPoint( hangPoint1 );

        Element otherSuspend = new IIOMetadataNode( "suspend" );
        otherSuspend.setAttribute( "ref", "jane" );
        otherSuspend.setAttribute( "distance", "200" );

        element = new IIOMetadataNode( "suspend" );
        element.setAttribute( "ref", "jim" );
        element.setAttribute( "distance", "32" );

        Element truss1 = new IIOMetadataNode( "truss" );
        truss1.setAttribute( "size", "12" );
        truss1.setAttribute( "length", "320" );
        truss1.appendChild( element );
        truss1.appendChild( otherSuspend );
        truss = new Truss( truss1 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}