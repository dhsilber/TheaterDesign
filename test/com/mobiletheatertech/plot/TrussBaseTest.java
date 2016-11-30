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
    Double z = -35.1;
    Double rotation = 45.0;

    Element prosceniumElement = null;
    Double prosceniumX = 100.0;
    Double prosceniumY = 200.0;
    Double prosceniumZ = 15.0;
    
    Element trussElement = null;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.YokeableReset();
        TestResets.ElementalListerReset();
        Proscenium.Reset();
        UniqueId.Reset();
        TestResets.LegendReset();
        TrussBase$.MODULE$.Reset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute( "name", "TrussBase Venue Name" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        Venue venue = new Venue( venueElement );
        venue.getClass();
        Venue.Height();

        prosceniumElement = new IIOMetadataNode( "proscenium" );
        prosceniumElement.setAttribute( "x", prosceniumX.toString() );
        prosceniumElement.setAttribute( "y", prosceniumY.toString() );
        prosceniumElement.setAttribute( "z", prosceniumZ.toString() );
        prosceniumElement.setAttribute( "width", "200" );
        prosceniumElement.setAttribute( "depth", "23" );
        prosceniumElement.setAttribute( "height", "144" );

        Element otherBase = new IIOMetadataNode( "trussbase" );
        otherBase.setAttribute( "ref", "jane" );
        otherBase.setAttribute( "distance", "200" );

        baseElement = new IIOMetadataNode( "trussbase" );
        baseElement.setAttribute( "size", size.toString() );
        baseElement.setAttribute("x", x.toString());
        baseElement.setAttribute("y", y.toString());

//        Element truss1 = new IIOMetadataNode( "truss" );
//        truss1.setAttribute("id", id);
//        truss1.setAttribute( "size", "12" );
//        truss1.setAttribute( "length", "120" );
//        truss1.appendChild(baseElement);
        
        trussElement = new IIOMetadataNode( Truss.Tag() );
        trussElement.setAttribute( "id", id);
        trussElement.setAttribute( "size", "12" );
        trussElement.setAttribute( "length", "120" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void isA() throws Exception {
        TrussBase instance = new TrussBase(baseElement);

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertFalse( Yokeable.class.isInstance( instance ) );

        assertTrue( Populate.class.isInstance( instance ) );
        assertFalse( Legendable.class.isInstance( instance ) );
    }

    @Test
    public void constantTag() {
        assertEquals( TrussBase$.MODULE$.Tag(), "trussbase" );
    }

    @Test
    public void constantColor() {
        assertEquals( TrussBase$.MODULE$.Color(), "taupe" );
    }

    @Test
    public void storesAttributes() throws Exception {
        TrussBase trussBase = new TrussBase(baseElement);

        assertEquals( TestHelpers.accessDouble(trussBase, "x"), x );
        assertEquals( TestHelpers.accessDouble(trussBase, "y"), y );
        assertEquals( TestHelpers.accessDouble(trussBase, "z"), 0.0 );
//        assertEquals( TestHelpers.accessString(trussBase, "type"), type );
        assertEquals( TestHelpers.accessDouble(trussBase, "size"), size );
        assertEquals( TestHelpers.accessDouble(trussBase, "rotation"), 0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        baseElement.setAttribute( "rotation", rotation.toString() );
        baseElement.setAttribute( "z", z.toString() );
        TrussBase trussBase = new TrussBase(baseElement);

        assertEquals( TestHelpers.accessDouble(trussBase, "x"), x );
        assertEquals( TestHelpers.accessDouble(trussBase, "y"), y );
        assertEquals( TestHelpers.accessDouble(trussBase, "z"), z );
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
    public void z() throws Exception {
        baseElement.setAttribute( "z", z.toString() );
        TrussBase base = new TrussBase( baseElement );

        assertEquals( base.z(), z );
    }

    @Test
    public void size() throws Exception {
        TrussBase trussBase = new TrussBase( baseElement );

        assertEquals( trussBase.size(), size );
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

    @Test
    public void mountPoint() {
        TrussBase trussBase = new TrussBase( baseElement );

        assertEquals( trussBase.mountPoint(),
                new Point( trussBase.x(), trussBase.y(), trussBase.z() + 2.0 ));
    }

    @Test
    public void verify() throws Exception {
        baseElement.setAttribute( "z", z.toString() );
        TrussBase instance = new TrussBase( baseElement );
        instance.verify();

        assertEquals( TestHelpers.accessObject(instance, "drawPlace"),
                new Point( x, y, z ) );
    }

    @Test
    public void verifyProscenium() throws Exception {
        new Proscenium( prosceniumElement );
        baseElement.setAttribute( "z", z.toString() );
        TrussBase instance = new TrussBase( baseElement );
        instance.verify();

        assertEquals( TestHelpers.accessObject(instance, "drawPlace"),
                new Point( prosceniumX + x, prosceniumY - y, prosceniumZ + z ) );
    }

    @Test
    public void trussTagCallbackRegistered() {
        TrussBase trussbase = new TrussBase( baseElement );

        assertEquals( trussbase.tags().size(), 1 );
        assertTrue( trussbase.tags().contains( Truss.LayerTag() ) );
    }

    @Test
    public void populateChildren() {
        baseElement.appendChild( trussElement );
        new TrussBase( baseElement );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venue = list.get( 0 );
        assert MinderDom.class.isInstance( venue );
        assert Venue.class.isInstance( venue );

        ElementalLister trussbase = list.get( 1 );
        assert MinderDom.class.isInstance( trussbase );
        assert TrussBase.class.isInstance( trussbase );

        ElementalLister truss = list.get( 2 );
        assert MinderDom.class.isInstance( truss );
        assert Truss.class.isInstance( truss );

        assertEquals( list.size(), 3 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

//    @Mocked
//    Venue mockVenue;

}