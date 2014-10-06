//package com.mobiletheatertech.plot;
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//
//import static org.testng.Assert.assertEquals;
//import static org.testng.Assert.assertNull;
//
///**
//* Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:01 PM To change this template use
//* File | Settings | File Templates.
//*
//* @since 0.0.5
//*/
//public class BaseTest {
//
//    Element elementOnPipe = null;
//    Truss truss = null;
//
//    public BaseTest() {
//    }
//
//    @Test
//    public void isMinder() throws Exception {
//        Base base = new Base( elementOnPipe );
//
//        assert Minder.class.isInstance( base );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        Base base = new Base( elementOnPipe );
//
//        assertEquals( TestHelpers.accessInteger( base, "x" ), 12 );
//        assertEquals( TestHelpers.accessInteger( base, "y" ), 32 );
//    }
//
//    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        Base base = new Base( elementOnPipe );
//
//        assertNull( TestHelpers.accessString( base, "id" ) );
//    }
//
///*
//    @Test
//    public void storesSelf() throws Exception
//    {
//        Base base = new Base( elementOnPipe );
//
//        ArrayList<Minder> thing = Drawable.List();
//        assertNotNull( thing, "List should exist" );
//
//        assert thing.contains( base );
//        fail( "Move this to DrawableTest" );
//    }
//*/
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new Base( elementOnPipe );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "Base instance is missing required 'x' attribute.")
//    public void noX() throws Exception {
//        elementOnPipe.removeAttribute( "x" );
//        new Base( elementOnPipe );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "Base instance is missing required 'y' attribute.")
//    public void noY() throws Exception {
//        elementOnPipe.removeAttribute( "y" );
//        new Base( elementOnPipe );
//    }
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<truss size=\"12\" length=\"10\" >" +
//                "<base x=\"4\" y=\"1\" />" +
//                "</truss>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestHelpers.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<Minder> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
//        Minder truss = list.get( 0 );
//        assert Minder.class.isInstance( truss );
//        assert Truss.class.isInstance( truss );
//    }
//
//    @Test
//    public void locate() throws Exception {
//        Base base = new Base( elementOnPipe );
//
//        Point location = base.locate();
//
//        assertEquals( location.x(), (Integer) 12 );
//        assertEquals( location.y(), (Integer) 32 );
//        assertEquals( location.z(), 0 );
//    }
//
//    @Test
//    public void drawUnused() throws Exception {
//        new Base( elementOnPipe );
//
////        base.drawPlan( null );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        Base base = new Base( elementOnPipe );
//
//        base.dom( null, View.PLAN );
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
////    @Mocked
////    Venue mockVenue;
//
//
//    @BeforeMethod
//    public void setUpMethod() throws Exception {
//        System.err.println( "Starting BaseTest method." );
//
//        TestHelpers.MinderDomReset();
//
//        Element venueElement = new IIOMetadataNode();
//        venueElement.setAttribute( "name", "Base Venue Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        Venue venue = new Venue( venueElement );
//        venue.getClass();
//        Venue.Height();
//
//        Element otherBase = new IIOMetadataNode( "base" );
//        otherBase.setAttribute( "ref", "jane" );
//        otherBase.setAttribute( "distance", "200" );
//
//        elementOnPipe = new IIOMetadataNode( "base" );
//        elementOnPipe.setAttribute( "x", "12" );
//        elementOnPipe.setAttribute( "y", "32" );
//
//        Element truss1 = new IIOMetadataNode( "truss" );
//        truss1.setAttribute( "size", "12" );
//        truss1.setAttribute( "length", "320" );
//        truss1.appendChild( elementOnPipe );
//        truss1.appendChild( otherBase );
//        truss = new Truss( truss1 );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}