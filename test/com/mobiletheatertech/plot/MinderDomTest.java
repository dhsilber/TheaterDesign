package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertNotNull;

/**
 * Test {@code MinderDom}
 *
 * @author dhs
 * @since 0.0.20 (split off from 0.0.2 code)
 */
public class MinderDomTest {

    /**
     * Extended {@code Minder} so that there is a concrete class to test with.
     */
    private class MindedDom extends MinderDom {

        public MindedDom( Element element ) throws InvalidXMLException {
            super( element );
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
    }

    private static Draw draw = null;
    private Element element = null;

    public MinderDomTest() {
    }

    @Test
    public void isElemental() throws Exception {
        MindedDom foo = new MindedDom( element );

        assert Elemental.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        MindedDom foo = new MindedDom( element );
        ArrayList<MinderDom> thing = Drawable.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( foo );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
           expectedExceptionsMessageRegExp = "Element unexpectedly null!" )
    public void NullElement() throws Exception {
        new MindedDom( null );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        draw = new Draw();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "bogus" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}