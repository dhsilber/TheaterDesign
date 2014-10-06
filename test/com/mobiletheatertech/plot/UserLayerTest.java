package com.mobiletheatertech.plot;

import com.mobiletheatertech.plot.*;
import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Created by dhs on 8/15/14.
 */
public class UserLayerTest {

    Element element = null;

    private final String id = "Layer ID";
    private final String name = "Layer name";

    public UserLayerTest() {
    }

    @Test
    public void isMinderDom() throws Exception {
        UserLayer userLayer = new UserLayer( element );

        assert MinderDom.class.isInstance( userLayer );
    }

    @Test
    public void storesAttributes() throws Exception {
        UserLayer userLayer = new UserLayer( element );

        assertEquals( TestHelpers.accessString(userLayer, "id"), id );
        assertEquals( TestHelpers.accessString(userLayer, "name"), name );
    }

    @Test
    public void makesLayer() throws Exception {
        UserLayer userLayer = new UserLayer( element );

        HashMap<String, String> thing = Layer.List();

        assertTrue( thing.containsKey( name ) );
        assertTrue( thing.containsValue( id ) );
    }

//    @Test
//    public void recallsNull() {
//        assertNull( UserLayer.Select( "bogus" ) );
//    }
//
//    @Test
//    public void recalls() throws Exception {
//        UserLayer definition = new UserLayer( element );
//        assertSame( UserLayer.Select( "6x9" ), definition );
//    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new UserLayer( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "UserLayer instance is missing required 'id' attribute." )
    public void noId() throws Exception {
        element.removeAttribute( "id" );
        new UserLayer( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "UserLayer \\("+id+"\\) is missing required 'name' attribute." )
    public void noName() throws Exception {
        element.removeAttribute( "name" );
        new UserLayer( element );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
//        TestResets.UserLayerReset();

//        Element venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "room", "Test Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        new Venue( venueElement );

        element = new IIOMetadataNode( "layer" );
        element.setAttribute( "id", id );
        element.setAttribute( "name", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
