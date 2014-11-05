//package com.mobiletheatertech.plot;
//
///**
// * Created with IntelliJ IDEA. User: dhs Date: 6/27/13 Time: 4:46 PM To change this template use File | Settings | File
// * Templates.
// */
//
//import org.testng.annotations.*;
//import org.w3c.dom.Element;
//
//import javax.imageio.metadata.IIOMetadataNode;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//
//import static org.testng.Assert.*;
//
//
///**
// * @author dhs
// * @since 0.0.5
// */
//public class SupportTest {
//
//    Element baseElement = null;
//    HangPoint hanger1 = null;
//    Truss truss = null;
//
//    public SupportTest() {
//    }
//
//    @Test
//    public void isMinder() throws Exception {
//        Support support = new Support( baseElement );
//
//        assert Minder.class.isInstance( support );
//    }
//
//    @Test
//    public void storesAttributes() throws Exception {
//        Support support = new Support( baseElement );
//
//        assertEquals( TestHelpers.accessString( support, "refId" ), "jim" );
//        assertEquals( TestHelpers.accessInteger( support, "distance" ), 32 );
//    }
//
//    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        Support support = new Support( baseElement );
//
//        assertNull( TestHelpers.accessString( support, "id" ) );
//    }
//
///*
//
//    @Test
//    public void storesSelf() throws Exception
//    {
//        Support support = new Support( baseElement );
//
//        ArrayList<Minder> thing = Drawable.List();
//        assertNotNull( thing, "List should exist" );
//
//        assert thing.contains( support );
//        fail( "Move this to DrawableTest" );
//    }
//*/
//
//    @Test
//    public void marksProcessed() throws Exception {
//        String emptyMark = baseElement.attribute( "processedMark" );
//        assertEquals( emptyMark, "", "Should be unset" );
//
//        Support support = new Support( baseElement );
//
//        String supportMark = TestHelpers.accessString( support, "processedMark" );
//        String elementMark = baseElement.attribute( "processedMark" );
//        assertNotNull( supportMark );
//        assertNotEquals( supportMark, "", "Should be set in Support object" );
//        assertNotEquals( elementMark, "", "Should be set in Element" );
//        assertEquals( supportMark, elementMark, "should match" );
//    }
//
//    @Test
//    public void findNull() throws Exception {
//        new Support( baseElement );
//
//        Support found = Support.Find( null );
//
//        assertNull( found );
//    }
//
//    @Test
//    public void findsMarked() throws Exception {
//        Support support = new Support( baseElement );
//
//        Support found = Support.Find( baseElement.attribute( "processedMark" ) );
//
//        assertSame( found, support );
//    }
//
//    /*
//     * This is to ensure that no exception is thrown if data is OK.
//     */
//    @Test
//    public void justFine() throws Exception {
//        new Support( baseElement );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "Support is missing required 'ref' attribute")
//    public void noRef() throws Exception {
//        baseElement.removeAttribute( "ref" );
//        new Support( baseElement );
//    }
//
//    @Test(expectedExceptions = AttributeMissingException.class,
//          expectedExceptionsMessageRegExp = "Support is missing required 'distance' attribute")
//    public void noDistance() throws Exception {
//        baseElement.removeAttribute( "distance" );
//        new Support( baseElement );
//    }
//
//    @Test(expectedExceptions = ReferenceException.class,
//          expectedExceptionsMessageRegExp = "Cannot support from unknown hangpoint ref 302.")
//    public void missingRefTarget() throws Exception {
//        baseElement.setAttribute( "ref", "302" );
//        new Support( baseElement );
//    }
//
//    @Test
//    public void referencesHangPoint() throws Exception {
//        Support support = new Support( baseElement );
//        Field hangPointField = TestHelpers.accessField( support, "ref" );
//        HangPoint hangPoint = (HangPoint) hangPointField.get( support );
//
//        assertNotNull( hangPoint );
//        assertSame( hangPoint, hanger1 );
//    }
//
///*
//    @Test
//    public void associatesWithParentTruss() throws Exception
//    {
//        Support support = new Support( baseElement );
//        Field parentTrussField = TestHelpers.accessField( support, "truss" );
//        Truss parentTruss = (Truss) parentTrussField.get( support );
//
//        assertNotNull( parentTruss );
//        assertSame( parentTruss, truss );
//        Check that parent truss has reference to this support.
//
//    }
//*/
//
//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<hangpoint id=\"victoria\" x=\"20\" y=\"30\" />" +
//                "<hangpoint id=\"albert\" x=\"25\" y=\"35\" />" +
//                "<truss size=\"12\" length=\"10\" >" +
//                "<support ref=\"albert\" distance=\"1\" />" +
//                "<support ref=\"victoria\" distance=\"3\" />" +
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
//        assertEquals( list.size(), 5 );
//
//        Minder hangpoint = list.get( 0 );
//        assert Minder.class.isInstance( hangpoint );
//        assert HangPoint.class.isInstance( hangpoint );
//
//        Minder hangpoint2 = list.get( 1 );
//        assert Minder.class.isInstance( hangpoint2 );
//        assert HangPoint.class.isInstance( hangpoint2 );
//
//        Minder truss = list.get( 2 );
//        assert Minder.class.isInstance( truss );
//        assert Truss.class.isInstance( truss );
//
//        Minder support1 = list.get( 3 );
//        assert Minder.class.isInstance( support1 );
//        assert Support.class.isInstance( support1 );
//
//        Minder support2 = list.get( 4 );
//        assert Minder.class.isInstance( support2 );
//        assert Support.class.isInstance( support2 );
//
//        assertNotSame( support1, support2 );
//
//    }
//
//    @Test
//    public void locate() throws Exception {
//        Support support = new Support( baseElement );
//
//        Point location = support.locate();
//
//        assertEquals( location.x(), (Integer) 100 );
//        assertEquals( location.y(), (Integer) 200 );
//        assertEquals( location.z(), 208 );
//    }
//
//    @Test
//    public void drawUnused() throws Exception {
//        Support support = new Support( baseElement );
//
//        support.draw( null );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        Support support = new Support( baseElement );
//
//        support.dom( null );
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
//        System.err.println( "Starting SupportTest method." );
//
//        TestHelpers.MinderDomReset();
//
//        Element venueElement = new IIOMetadataNode();
//        venueElement.setAttribute( "name", "Support Venue Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        Venue venue = new Venue( venueElement );
//        venue.getClass();
//        Venue.Height();
//
//        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
//        hangPoint1.setAttribute( "id", "jim" );
//        hangPoint1.setAttribute( "x", "100" );
//        hangPoint1.setAttribute( "y", "200" );
//        hanger1 = new HangPoint( hangPoint1 );
//
//        Element otherSupport = new IIOMetadataNode( "support" );
//        otherSupport.setAttribute( "ref", "jane" );
//        otherSupport.setAttribute( "distance", "200" );
//
//        baseElement = new IIOMetadataNode( "support" );
//        baseElement.setAttribute( "ref", "jim" );
//        baseElement.setAttribute( "distance", "32" );
//
//        Element truss1 = new IIOMetadataNode( "truss" );
//        truss1.setAttribute( "size", "12" );
//        truss1.setAttribute( "length", "320" );
//        truss1.appendChild( baseElement );
//        truss1.appendChild( otherSupport );
//        truss = new Truss( truss1 );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}
