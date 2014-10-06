package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Created by dhs on 8/18/14.
 */
public class VerifierTest {

    int count = 0;

    /**
     * Extended {@code Verifier} so that there is a concrete class to test with.
     */
    private class Verified extends Verifier {

        public Verified( Element element ) throws InvalidXMLException {
            super( element );
        }

        @Override
        public void verify() throws InvalidXMLException {
            count++;
        }
    }

    //    private static Draw draw = null;
    private Element element = null;

    @Test
    public void isa() throws Exception {
        Verified foo = new Verified( element );

        assert Elemental.class.isInstance( foo );
        assert ElementalLister.class.isInstance( foo );
        assert Verifier.class.isInstance( foo );
    }

    @Test
    public void stores() throws Exception {
        Verified foo = new Verified( element );
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
            expectedExceptionsMessageRegExp = "Verified element unexpectedly null!" )
    public void nullElement() throws Exception {
        new Verified( null );
    }

    @Test
    public void noVerifiers() throws Exception {
        TestResets.ElementalListerReset();

        assertTrue( ElementalLister.List().isEmpty());

        Verifier.VerifyAll();

        assertEquals( count, 0 );
    }

    @Test
    public void verifies() throws Exception {
        TestResets.ElementalListerReset();

        new Verified( element );

        Verifier.VerifyAll();

        assertEquals( count, 1 );
    }

    @Test
    public void verifiesMultiple() throws Exception {
        TestResets.ElementalListerReset();

        new Verified( element );
        new Verified( element );

        Verifier.VerifyAll();

        assertEquals( count, 2 );
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
        count = 0;
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}