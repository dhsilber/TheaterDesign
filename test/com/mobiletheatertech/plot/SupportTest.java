package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 6/27/13 Time: 4:46 PM To change this template use File | Settings | File
 * Templates.
 */ 

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.imageio.metadata.IIOMetadataNode;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Element;


/**
 * @author dhs
 * @since 0.0.5
 */
public class SupportTest {

    Element element = null;
    HangPoint hanger1 = null;
    Truss truss = null;

    public SupportTest()
    {
    }

    @Test
    public void isMinder() throws Exception
    {
        Support support = new Support( element );

        assert Minder.class.isInstance( support );
    }

    @Test
    public void storesAttributes() throws Exception
    {
        Support support = new Support( element );

        assertEquals( TestHelpers.accessString( support, "refId" ), "jim" );
        assertEquals( TestHelpers.accessInteger( support, "distance" ), 32 );
    }
/*

    @Test
    public void storesSelf() throws Exception
    {
        Support support = new Support( element );

        ArrayList<Minder> thing = Drawable.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( support );
        fail( "Move this to DrawableTest" );
    }
*/

    @Test
    public void marksProcessed() throws Exception {
        String emptyMark = element.getAttribute( "processedMark" );
        assertEquals( emptyMark, "", "Should be unset" );

        Support support = new Support( element );

        String supportMark = TestHelpers.accessString( support, "processedMark" );
        String elementMark = element.getAttribute( "processedMark" );
        assertNotNull( supportMark );
        assertNotEquals( supportMark, "", "Should be set in Support object" );
        assertNotEquals( elementMark, "", "Should be set in Element" );
        assertEquals( supportMark, elementMark, "should match" );
    }

    @Test
    public void findNull() throws Exception {
        new Support( element );

        Support found = Support.Find( null );

        assertNull( found );
    }

    @Test
    public void findsMarked() throws Exception {
        Support support = new Support( element );

        Support found = Support.Find( element.getAttribute( "processedMark" ) );

        assertSame( found, support );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception
    {
        new Support( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Support is missing required 'ref' attribute" )
    public void noRef() throws Exception
    {
        element.removeAttribute( "ref" );
        new Support( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Support is missing required 'distance' attribute" )
    public void noDistance() throws Exception
    {
        element.removeAttribute( "distance" );
        new Support( element );
    }

    @Test( expectedExceptions = ReferenceException.class,
           expectedExceptionsMessageRegExp = "Cannot support from unknown hangpoint ref 302." )
    public void missingRefTarget() throws Exception
    {
        element.setAttribute( "ref", "302" );
        new Support( element );
    }

    @Test
    public void referencesHangPoint() throws Exception
    {
        Support support = new Support( element );
        Field hangPointField = TestHelpers.accessField( support, "ref" );
        HangPoint hangPoint = (HangPoint) hangPointField.get( support );

        assertNotNull( hangPoint );
        assertSame( hangPoint, hanger1 );
    }

/*
    @Test
    public void associatesWithParentTruss() throws Exception
    {
        Support support = new Support( element );
        Field parentTrussField = TestHelpers.accessField( support, "truss" );
        Truss parentTruss = (Truss) parentTrussField.get( support );

        assertNotNull( parentTruss );
        assertSame( parentTruss, truss );
        Check that parent truss has reference to this support.

    }
*/

    @Test
    public void parse() throws Exception
    {
        String xml = "<plot>" +
                "<hangpoint id=\"victoria\" x=\"20\" y=\"30\" />" +
                "<hangpoint id=\"albert\" x=\"25\" y=\"35\" />" +
                "<truss size=\"12\" length=\"10\" >" +
                "<support ref=\"albert\" distance=\"1\" />" +
                "<support ref=\"victoria\" distance=\"3\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestHelpers.MinderReset();

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

        Minder support1 = list.get( 3 );
        assert Minder.class.isInstance( support1 );
        assert Support.class.isInstance( support1 );

        Minder support2 = list.get( 4 );
        assert Minder.class.isInstance( support2 );
        assert Support.class.isInstance( support2 );

        assertNotSame( support1, support2 );

    }

    @Test
    public void locate() throws Exception {
        Support support = new Support( element );

        Point location = support.locate();

        assertEquals( location.x(), 100 );
        assertEquals( location.y(), 200 );
        assertEquals( location.z(), 208 );
    }

    @Test
    public void drawUnused()   throws Exception
    {
        Support support = new Support( element );

        support.draw( null );
    }

    @Test
    public void domUnused()   throws Exception
    {
        Support support = new Support( element );

        support.dom( null );
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

//    @Mocked
//    Venue mockVenue;


    @BeforeMethod
    public void setUpMethod() throws Exception
    {
        System.err.println( "Starting SupportTest method." );

        TestHelpers.MinderReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "Support Venue Name" );
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

        Element otherSupport = new IIOMetadataNode( "support" );
        otherSupport.setAttribute( "ref", "jane" );
        otherSupport.setAttribute( "distance", "200" );

        element = new IIOMetadataNode( "support" );
        element.setAttribute( "ref", "jim" );
        element.setAttribute( "distance", "32" );

        Element truss1 = new IIOMetadataNode( "truss" );
        truss1.setAttribute( "size", "12" );
        truss1.setAttribute( "length", "320" );
        truss1.appendChild( element );
        truss1.appendChild( otherSupport );
        truss = new Truss( truss1 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception
    {
    }
}
