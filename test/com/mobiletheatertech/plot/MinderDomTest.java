package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Test {@code MinderDom}
 *        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
i
 * @author dhs
 * @since 0.0.20 (split off from 0.0.2 code)
 */
public class MinderDomTest {

    /**
     * Extended {@code MinderDom} so that there is a concrete class to test with.
     */
    private class MindedDom extends MinderDom {

        public MindedDom( Element element )
                throws AttributeMissingException, DataException, InvalidXMLException {
            super( element );
        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dom( Draw draw, View mode ) {

//            throw new UnsupportedOperationException(
//                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private Element element = null;

    @Test
    public void isa() throws Exception {
        MindedDom foo = new MindedDom( element );

        assert Elemental.class.isInstance( foo );
        assert ElementalLister.class.isInstance( foo );
        assert Verifier.class.isInstance( foo );
        assert MinderDom.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        MindedDom foo = new MindedDom( element );
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
           expectedExceptionsMessageRegExp = "MindedDom element unexpectedly null!" )
    public void nullElement() throws Exception {
        new MindedDom( null );
    }

    @Test
    public void createsClassLayer() throws Exception {
        assertFalse(Layer.List().containsKey("MindedDom"));

        new MindedDom( element );

        assertTrue(Layer.List().containsKey("MindedDom"));
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