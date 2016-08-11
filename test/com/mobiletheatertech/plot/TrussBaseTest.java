package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.*;
import static org.testng.Assert.assertNotEquals;

/**
* Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:01 PM To change this template use
* File | Settings | File Templates.
*
* @since 0.0.5
*/
public class TrussBaseTest {

    Element baseElement = null;
    Truss truss = null;

    String id = "Truss ID";
//    String type = "truss:12";
    Double size = 24.0;
    Double x = 12.0;
    Double y = 32.2;
    Double rotation = 45.0;

    public TrussBaseTest() {
    }

    @Test
    public void isA() throws Exception {
        TrussBase trussBase = new TrussBase(baseElement);

        assert MinderDom.class.isInstance(trussBase);
    }

    @Test
    public void storesAttributes() throws Exception {
        TrussBase trussBase = new TrussBase(baseElement);

        assertEquals( TestHelpers.accessDouble(trussBase, "x"), x );
        assertEquals( TestHelpers.accessDouble(trussBase, "y"), y );
//        assertEquals( TestHelpers.accessString(trussBase, "type"), type );
        assertEquals( TestHelpers.accessDouble(trussBase, "size"), size );
        assertEquals( TestHelpers.accessDouble(trussBase, "rotation"), 0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        baseElement.setAttribute( "rotation", rotation.toString() );
        TrussBase trussBase = new TrussBase(baseElement);

        assertEquals( TestHelpers.accessDouble(trussBase, "x"), x );
        assertEquals( TestHelpers.accessDouble(trussBase, "y"), y );
//        assertEquals( TestHelpers.accessString(trussBase, "type"), type );
        assertEquals( TestHelpers.accessDouble(trussBase, "size"), size );
        assertEquals( TestHelpers.accessDouble(trussBase, "rotation"), rotation );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        assertNull( TestHelpers.accessString(trussBase, "id" ) );
    }

/*
    @Test
    public void storesSelf() throws Exception
    {
        TrussBase base = new TrussBase( baseElement );

        ArrayList<Minder> thing = Drawable.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( base );
        fail( "Move this to DrawableTest" );
    }
*/

    @Test
    public void marksProcessed() throws Exception {
        String emptyMark = baseElement.getAttribute( "processedMark" );
        assertEquals( emptyMark, "", "Should be unset" );

        TrussBase trussBase = new TrussBase( baseElement );

        String baseMark = TestHelpers.accessString(trussBase, "processedMark" );
        String elementMark = baseElement.getAttribute( "processedMark" );
        assertNotNull( baseMark );
        assertNotEquals( baseMark, "", "Should be set in TrussBase object" );
        assertNotEquals( elementMark, "", "Should be set in Element" );
        assertEquals( baseMark, elementMark, "should match" );
    }

    @Test
    public void findNull() throws Exception {
        new TrussBase( baseElement );

        TrussBase found = TrussBase.Find( null );

        assertNull(found);
    }

    @Test
    public void findsMarked() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        TrussBase found = TrussBase.Find( baseElement.getAttribute( "processedMark" ) );

        assertSame( found, trussBase);
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new TrussBase( baseElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        baseElement.removeAttribute("x");
        new TrussBase( baseElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        baseElement.removeAttribute("y");
        new TrussBase(baseElement);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "TrussBase instance is missing required 'size' attribute.")
    public void noSize() throws Exception {
        baseElement.removeAttribute("size");
        new TrussBase(baseElement);
    }

    @Test
    public void x() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        assertEquals( trussBase.x(), x );
    }

    @Test
    public void y() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        assertEquals( trussBase.y(), y );
    }

    @Test
    public void size() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        assertEquals( trussBase.size(), size );
    }

    @Test
    public void parseBaseOnly() throws Exception {
        String xml = "<plot>" +
                "<base size=\"24\" x=\"4\" y=\"1\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestHelpers.MinderDomReset();

        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 2 );

        ElementalLister truss = list.get( 0 );
        assert MinderDom.class.isInstance( truss );
        assert Truss.class.isInstance( truss );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<truss id=\"trussID\" size=\"12\" length=\"10\" >" +
                "<trussbase size=\"24\" x=\"4\" y=\"1\" />" +
                "</truss>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestHelpers.MinderDomReset();

        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 2 );

        ElementalLister truss = list.get( 0 );
        assert MinderDom.class.isInstance( truss );
        assert Truss.class.isInstance( truss );
    }

//    @Test
//    public void locate() throws Exception {
//        TrussBase base = new TrussBase(baseElement);
//
//        Point location = base.locate();
//
//        assertEquals( location.x(), (Integer) 12 );
//        assertEquals( location.y(), (Integer) 32 );
//        assertEquals( location.z(), 0 );
//    }

//    @Test
//    public void drawUnused() throws Exception {
//        new TrussBase(baseElement);
//
////        base.drawPlan( null );
//    }
//
//    @Test
//    public void domUnused() throws Exception {
//        TrussBase base = new TrussBase(baseElement);
//
//        base.dom( null, View.PLAN );
//    }

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
        System.err.println( "Starting TrussBaseTest method." );

        TestResets.YokeableReset();
        TestResets.ElementalListerReset();
        TestResets.ProsceniumReset();
        UniqueId.Reset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "TrussBase Venue Name" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        Venue venue = new Venue( venueElement );
        venue.getClass();
        Venue.Height();

        Element otherBase = new IIOMetadataNode( "trussbase" );
        otherBase.setAttribute( "ref", "jane" );
        otherBase.setAttribute( "distance", "200" );

        baseElement = new IIOMetadataNode( "trussbase" );
        baseElement.setAttribute( "size", size.toString() );
        baseElement.setAttribute("x", x.toString());
        baseElement.setAttribute("y", y.toString());

        Element truss1 = new IIOMetadataNode( "truss" );
        truss1.setAttribute("id", id);
        truss1.setAttribute( "size", "12" );
        truss1.setAttribute( "length", "120" );
        truss1.appendChild(baseElement);
        truss = new Truss( truss1 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}