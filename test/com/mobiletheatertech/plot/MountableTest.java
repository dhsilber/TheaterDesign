package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by dhs on 12/16/13.
 */
public class MountableTest {


/**
 * Test {@code Mountable}
 *
 * @author dhs
 * @since 0.0.20 (split off from 0.0.2 code)
 */

    /**
     * Extend {@code Mountable} so that there is a concrete class to test with.
     */
    private class Mounted extends Mountable {

        public Mounted( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
            super( element );
        }

        @Override
        public PagePoint schematicLocation( String location ) throws InvalidXMLException {
            return null;
        }

        @Override
        public Point mountableLocation(String location) {
            return null;
        }

        @Override
        public Place rotatedLocation(String location)
                throws InvalidXMLException, MountingException, ReferenceException {
            return null;
        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dom( Draw draw, View mode ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        public PagePoint schematicPosition() { return null; }

        /**
         * This is schematicPosition() adjusted to take into account the number of
         * cables attempting to intersect a thing from that direction.
         *
         * @return a position along the edge of a thing
         */
        public PagePoint schematicCableIntersectPosition( CableRun run )
                throws CorruptedInternalInformationException, ReferenceException
        { return null; }

        public Rectangle2D.Double schematicBox()
        { return null; }

        public void schematicReset()  {}

        public void useCount( Direction direction, CableRun run ) {}

        public void preview( View view )
                throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
        {}

        public Place drawingLocation() throws InvalidXMLException, MountingException, ReferenceException
        { return null; }

    }

    //    private static Draw draw = null;
    private Element element = null;
    private String id = "MountedID";

    public MountableTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        Mounted foo = new Mounted( element );

        assert MinderDom.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
        assertEquals( list1.size(), 0 );

        Mounted pipe = new Mounted( element );

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
        assert list2.contains(pipe);
    }

//    @Test
//    public void UnstoresById() throws Exception {
//        Mounted pipe = new Mounted( diversionElement );
//        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
//        assertEquals( list1.size(), 1 );
//        assert list1.contains( pipe );
//
//        Mountable.Remove( id );
//
//        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
//        assertFalse( list2.contains( pipe ) );
//    }

    @Test
    public void UnstoresByReference() throws Exception {
        Mounted pipe = new Mounted( element );
        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
        assertEquals( list1.size(), 1 );
        assert list1.contains( pipe );

        Mountable.Remove( pipe );

        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
        assertFalse( list2.contains( pipe ) );
    }

//    @Test
//    public void UnstoresByBogusId() throws Exception {
//        Mounted pipe = new Mounted( diversionElement );
//        ArrayList<Mountable> list1 = (ArrayList<Mountable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
//        assertEquals( list1.size(), 1 );
//        assert list1.contains( pipe );
//
//        Mountable.Remove( "bogusId" );
//
//        ArrayList<Mountable> list2 = (ArrayList<Mountable>)
//                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
//        assertEquals(list2.size(), 1);
//    }

    @Test
    public void UnstoresByBogusReference() throws Exception {
        Mounted pipe = new Mounted( element );
        Mountable.Remove( pipe );

        Mountable.Remove( pipe );

        ArrayList<Mountable> list = (ArrayList<Mountable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mountable", "MOUNTABLELIST" );
        assertEquals(list.size(), 0);
    }

    @Test
    public void recallsNull() {
        assertNull( Mountable.Select( "bogus" ) );
    }

    @Test
    public void recalls() throws Exception {
        element.setAttribute("id", "friendly");
        Mounted pipe = new Mounted( element );
        Mountable found = Mountable.Select("friendly");
        assertNotNull( found );
        assertSame( found, pipe);
    }

    @Test
    public void storesIdAttribute() throws Exception {
        element.setAttribute( "id", "Pipe name" );
        Mounted pipe = new Mounted( element );
        assertEquals( TestHelpers.accessString( pipe, "id" ), "Pipe name" );
    }

    @Test
    public void storesSelf() throws Exception {
        Mounted pipe = new Mounted( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( pipe );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Mounted( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Mounted instance is missing required 'id' attribute." )
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        new Mounted( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Mounted id 'MountedID' is not unique." )
    public void repeatedId() throws Exception {
        new Mounted( element );
        new Mounted( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Mounted element unexpectedly null!" )
    public void NullElement() throws Exception {
        new Mounted( null );
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
        TestResets.MountableReset();

        element = new IIOMetadataNode( "mounted" );
        element.setAttribute( "id", id );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}