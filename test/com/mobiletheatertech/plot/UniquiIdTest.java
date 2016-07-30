package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.*;

/**
 * Created by DHS on 7/27/16.
 */
public class UniquiIdTest {

    private class Unique extends UniqueId {

        public Unique( Element element ) {
            super( element );
        }

        @Override
        public void verify()
                throws AttributeMissingException, DataException, FeatureException,
                InvalidXMLException, LocationException, MountingException,
                ReferenceException { }

        @Override
        public void dom(Draw draw, View mode)
                throws InvalidXMLException, MountingException, ReferenceException { }

    }

    private Element element = null;
    private final String id = "uniqueId";

    @Test
    public void isA() throws Exception {
        Unique instance = new Unique( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assert UniqueId.class.isInstance( instance );
    }

    @Test
    public void storesIdAttribute() throws Exception {
        Unique instance = new Unique( element );

        assertEquals( instance.id, id );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Unique instance is missing required 'id' attribute." )
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        new Unique( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Unique id '" + id + "' is not unique." )
    public void repeatedId() throws Exception {
        new Unique( element );
        new Unique( element );
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
        UniqueId.Reset();

        element = new IIOMetadataNode( "unique" );
        element.setAttribute( "id", id );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
