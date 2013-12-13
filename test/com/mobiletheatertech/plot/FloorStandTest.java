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
// * Created with IntelliJ IDEA. User: dhs Date: 11/26/13 Time: 11:14 PM To change this template use
// * File | Settings | File Templates.
// */
//public class FloorStandTest {
//
//    Element element = null;
//    Luminaire luminaire = null;
//    Integer x = 12;
//    Integer y=32;
//    Integer width=17;
//    Integer depth=15;
//
//    public FloorStandTest() {
//    }
//
//    @Test
//    public void isMinderDom() throws Exception {
//        FloorStand floorStand = new FloorStand( element );
//
//        assert MinderDom.class.isInstance( floorStand );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        FloorStand floorStand = new FloorStand( element );
//
//        assertEquals( TestHelpers.accessInteger( floorStand, "x" ), x );
//        assertEquals( TestHelpers.accessInteger( floorStand, "y" ), y );
//        assertEquals( TestHelpers.accessInteger( floorStand, "width" ), width );
//        assertEquals( TestHelpers.accessInteger( floorStand, "depth" ), depth );
//    }
//
//    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        FloorStand floorStand = new FloorStand( element );
//
//        assertNull( TestHelpers.accessString( floorStand, "id" ) );
//    }
//
///*
//    @Test
//    public void storesSelf() throws Exception
//    {
//        FloorStand floorStand = new FloorStand( element );
//
//        ArrayList<Minder> thing = Drawable.List();
//        assertNotNull( thing, "List should exist" );
//
//        assert thing.contains( floorStand );
//        fail( "Move this to DrawableTest" );
//    }
//*/
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new FloorStand( element );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "FloorStand instance is missing required 'x' attribute.")
//    public void noX() throws Exception {
//        element.removeAttribute( "x" );
//        new FloorStand( element );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "FloorStand instance is missing required 'y' attribute.")
//    public void noY() throws Exception {
//        element.removeAttribute( "y" );
//        new FloorStand( element );
//    }
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<luminaire size=\"12\" length=\"10\" >" +
//                "<floorStand x=\"4\" y=\"1\" />" +
//                "</luminaire>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream( xml.getBytes() );
//
//        TestResets.MinderDomReset();
//
//        new Parse( stream );
//
//        // Final size of list
//        ArrayList<MinderDom> list = Drawable.List();
//        assertEquals( list.size(), 2 );
//
////        Minder luminaire = list.get( 0 );
////        assert Minder.class.isInstance( luminaire );
////        assert Luminaire.class.isInstance( luminaire );
//    }
//
//    @Test
//    public void locate() throws Exception {
//        FloorStand floorStand = new FloorStand( element );
//
//        Point location = floorStand.locate();
//
//        assertEquals( location.x(), (Integer) 12 );
//        assertEquals( location.y(), (Integer) 32 );
//        assertEquals( location.z(), (Integer) 0 );
//    }
//
//    @Test
//    public void drawUnused() throws Exception {
//        new FloorStand( element );
//
////        floorStand.drawPlan( null );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        FloorStand floorStand = new FloorStand( element );
//
//        floorStand.dom( null, View.PLAN );
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
//        System.err.println( "Starting FloorStandTest method." );
//
//        TestResets.MinderDomReset();
//
//        Element venueElement = new IIOMetadataNode();
//        venueElement.setAttribute( "name", "FloorStand Venue Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        Venue venue = new Venue( venueElement );
//        venue.getClass();
//        Venue.Height();
//
//        Element otherFloorStand = new IIOMetadataNode( "floorStand" );
//        otherFloorStand.setAttribute( "ref", "jane" );
//        otherFloorStand.setAttribute( "distance", "200" );
//
//        element = new IIOMetadataNode( "floorStand" );
//        element.setAttribute( "x", "12" );
//        element.setAttribute( "y", "32" );
//        element.setAttribute( "width", width.toString() );
//        element.setAttribute( "depth", depth.toString() );
//
//        Element luminaire1 = new IIOMetadataNode( "luminaire" );
//        luminaire1.setAttribute( "size", "12" );
//        luminaire1.setAttribute( "length", "320" );
//        luminaire1.appendChild( element );
//        luminaire1.appendChild( otherFloorStand );
//        luminaire = new Luminaire( luminaire1 );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}