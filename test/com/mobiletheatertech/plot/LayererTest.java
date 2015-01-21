package com.mobiletheatertech.plot;

import com.mobiletheatertech.plot.*;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by dhs on 12/2/14.
 */
public class LayererTest {

    private class Layered extends Layerer {

        public Layered( Element element ) throws DataException, InvalidXMLException {
            super( element );
        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

    }


    private Element element = null;

    @Test
    public void isa() throws Exception {
        Layerer foo = new Layered( element );

        assert Elemental.class.isInstance( foo );
        assert ElementalLister.class.isInstance( foo );
        assert Verifier.class.isInstance( foo );
        assert Layerer.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        Layerer foo = new Layered( element );
        ArrayList<ElementalLister> thing = ElementalLister.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( foo );
    }

    /*
    This is to some extent redundant with ElementalTest/nullElement()
    but as MinderDom is also a building-block class, it seems appropriate
    to test here as well?
     */
    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Layered element unexpectedly null!" )
    public void nullElement() throws Exception {
        new Layered( null );
    }

    @Test
    public void createsClassLayer() throws Exception {
        assertFalse(Layer.List().containsKey("Layered"));

        new Layered( element );

        assertTrue(Layer.List().containsKey("Layered"));
    }

    // TODO: tests for DomAllPlan, DomAllSection, DomAllFront, DomAllTruss
    // They should all invoke the appropriate methods and skip anything that isn't a MinderDom

    @BeforeClass
    public static void setUpClass() throws Exception {
//        draw = new Draw();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "bogus" );

        TestResets.LayerReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}