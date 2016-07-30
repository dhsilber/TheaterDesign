package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 7/3/14.
 */
public class StackableTest {

    /**
     * Extend {@code Yokeable} so that there is a concrete class to test with.
     */
    private class Stacked extends Stackable {

        public Stacked( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
            super( element );
        }

//        @Override
//        public Point location( String location ) {
//            return null;
//        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dom( Draw draw, View mode ) {
            throw new UnsupportedOperationException(
                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Point location( Solid shape ) {
            return null;
        }
    }

    private Element element = null;
    private String id = "StackedID";

    @Test
    public void isMinderDom() throws Exception {
        Stacked foo = new Stacked( element );

        assert MinderDom.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        ArrayList<Stackable> list1 = (ArrayList<Stackable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Stackable", "STACKABLELIST" );
        assertEquals( list1.size(), 0 );

        Stacked table = new Stacked( element );

        ArrayList<Stackable> list2 = (ArrayList<Stackable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Stackable", "STACKABLELIST" );
        assert list2.contains(table);
    }

    @Test
    public void recallsNull() {
        assertNull( Stackable.Select( "bogus" ) );
    }

    @Test
    public void recalls() throws Exception {
        element.setAttribute("id", "friendly");
        Stacked table = new Stacked( element );
        Stackable found = Stackable.Select("friendly");
        assertNotNull( found );
        assertSame( found, table);
    }

    @Test
    public void storesIdAttribute() throws Exception {
        element.setAttribute( "id", "Pipe name" );
        Stacked table = new Stacked( element );

        assertEquals(TestHelpers.accessString(table, "id"), "Pipe name");
    }

    @Test
    public void storesAttributes() throws Exception {

        int size = Stackable.STACKABLELIST.size();

        element.removeAttribute("id");
        Stacked table = new Stacked( element );

        assertEquals(TestHelpers.accessString(table, "id"), "");
        assertEquals( Stackable.STACKABLELIST.size(), size );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("id", id);

        Stacked table = new Stacked( element );

        assertEquals(TestHelpers.accessString(table, "id"), id );
    }

    @Test
    public void storesSelf() throws Exception {
        Stacked table = new Stacked( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( table );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Stacked( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Stacked id 'StackedID' is not unique." )
    public void repeatedId() throws Exception {
        new Stacked( element );
        new Stacked( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Stacked element unexpectedly null!" )
    public void NullElement() throws Exception {
        new Stacked( null );
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
        TestResets.StackableReset();

        element = new IIOMetadataNode( "stacked" );
        element.setAttribute( "id", id );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
