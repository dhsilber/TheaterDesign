package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.HashMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Create a {@code Layer} under user control.
 *
 * Created by dhs on 8/15/14.
 */
public class UserLayerTest {

    Element element = null;

    private final String id = "Layer_ID";
    private final String name = "Layer name";

    public UserLayerTest() {
    }

    @Test
    public void isa() throws Exception {
        UserLayer userLayer = new UserLayer( element );

        assert Elemental.class.isInstance( userLayer );
    }

    @Test
    public void storesAttributes() throws Exception {
        UserLayer userLayer = new UserLayer( element );

        assertEquals( TestHelpers.accessString(userLayer, "id"), id );
        assertEquals( TestHelpers.accessString(userLayer, "name"), name );
    }

    @Test
    public void makesLayer() throws Exception {
        new UserLayer( element );

        HashMap<String, Layer> thing = Layer.List();

        assertTrue( thing.containsKey( id ) );
        assertEquals( thing.get( id ).name(), name );
    }

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

    @Test
    public void noLayerNoCheckbox() throws Exception {
        Write writer = new Write();
        String output = writer.generateIndex();
        CharSequence chars = "checkbox";

        assertFalse(output.contains(chars));
    }

    @Test
    public void layerCheckbox() throws Exception {
        new UserLayer( element );
        Write writer = new Write();
        String output = writer.generateDesigner();
        CharSequence checkbox = "checkbox";
        CharSequence selector = "selectLayer" + id;

        assertTrue(output.contains(checkbox));
        assertTrue(output.contains(selector));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.LayerReset();

        element = new IIOMetadataNode( "layer" );
        element.setAttribute( "id", id );
        element.setAttribute( "name", name );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
