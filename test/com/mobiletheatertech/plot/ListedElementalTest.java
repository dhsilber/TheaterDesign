package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * Created by dhs on 7/15/14.
 */
public class ListedElementalTest {

    /**
     * Extended {@code MinderDom} so that there is a concrete class to test with.
     */
    private class Listed extends ElementalLister {

        public Listed( Element element ) throws InvalidXMLException {
            super( element );

        }
    }

    //    private static Draw draw = null;
    private Element element = null;
    private String name = "thing";

    @Test
    public void isa() throws Exception {
        Listed foo = new Listed( element );

        assert Elemental.class.isInstance( foo );
        assert ElementalLister.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        Listed foo = new Listed( element );
        ArrayList<ElementalLister> thing = ElementalLister.List();
        assertNotNull( thing, "List should exist" );

        assert thing.contains( foo );
    }

    /*
    This is to some extent redundant with ElementalTest/nullElement()
    but as ElementalLister is also a building-block class, it seems appropriate
    to test here as well?
     */
    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Listed diversionElement unexpectedly null!" )
    public void nullElement() throws Exception {
        new Listed( null );
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
        element = new IIOMetadataNode( "bogus" );
//        diversionElement.setAttribute( "id", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}