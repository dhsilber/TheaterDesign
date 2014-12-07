package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 8:15 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.19
 */
public class LayerTest {
    private final String name = "Name text";
    private final String tag = "Tag text";
    private final String color = "ultraviolet";
    private final String color2 = "infrared";

    Element wallElement1 = null;

    @Test
    public void providesName() throws Exception {
        Layer layer = new Layer( tag, name, color );

        assertEquals( layer.name(), name );
    }

    @Test
    public void providesColor() throws Exception {
        Layer layer = new Layer( tag, name, color );

        assertEquals( layer.color(), color );
    }

    @Test
    public void providesListWithOne() throws Exception {
        new Layer( tag, name, color );

        HashMap<String, Layer> thing = Layer.List();

        assertTrue( thing.containsKey( tag ) );
        Layer layer = thing.get( tag );

        assertEquals( layer.name(), name );
    }

    @Test
    public void RegisterCreatesLayer() throws Exception {
        HashMap<String, Layer> list = Layer.List();
        assertEquals( list.size(), 0 );

        Layer.Register( tag, name );

        list = Layer.List();
        assertEquals( list.size(), 1 );
        assertTrue( list.containsKey( tag ) );

        Layer layer = list.get( tag );
        assertEquals( layer.name(), name );
        assertEquals( layer.color(), "black" );
    }

    @Test
    public void RegisterProvidesExistingLayer() throws Exception {
        new Layer( tag, name, color );

        HashMap<String, Layer> list = Layer.List();
        assertEquals(list.size(), 1);

        Layer.Register( tag, name );

        list = Layer.List();
        assertEquals( list.size(), 1 );
        assertTrue( list.containsKey( tag ) );

        Layer layer = list.get( tag );
        assertEquals( layer.name(), name );
        assertEquals( layer.color(), color );
    }

    @Test
    public void Retrieve() throws Exception{
        Layer initial = Layer.Register( tag, name );
        Layer found = Layer.Retrieve( tag );

        assertSame( found, initial );
    }

    @Test( expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Layer "+tag+" does not exist." )
    public void RetrieveFail() throws Exception {
        Layer.Retrieve( tag );
    }

    @Test
    public void providesListWithMultiple() throws Exception {
        new Layer( tag, name, color );

        String name2 = "Second name";
        String tag2 = "Tag number two";
        new Layer( tag2, name2, color2 );

        HashMap<String, Layer> thing = Layer.List();

        assertTrue( thing.containsKey( tag ) );
        assertEquals( thing.get( tag ).name(), name );
        assertTrue( thing.containsKey( tag2 ) );
        assertEquals( thing.get( tag2 ).name(), name2 );
    }

    @Test
    public void newLayerInactive() throws Exception {
        Layer foo = new Layer( tag, name, color );

        assertFalse(foo.active());
    }

    @Test
    public void activate() throws Exception {
        Layer foo = new Layer( tag, name, color );

        foo.activate();

        assertTrue(foo.active());
    }

    @Test( expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Layer "+tag+" is already defined." )
    public void noChangeName() throws Exception {
        new Layer( tag, name, color );
        new Layer( tag, "other name", color );
    }

    @Test( expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Layer "+tag+" is already defined." )
    public void noChangeColor() throws Exception {
        new Layer( tag, name, color );
        new Layer( tag, name, color2 );
    }

    @Test
    public void okSameNameColor() throws Exception {
        new Layer( tag, name, color );
        new Layer( tag, name, color );
    }

//TODO    Test that layers are not drawn if not activated

      //TODO      Test that active layers are drawn

    @Test
    public void noLayerNoCheckbox() throws Exception {
        Write writer = new Write();
//        String output = writer.generateIndex();
        CharSequence chars = "checkbox";

//        assertFalse(output.contains(chars));
    }

    @Test
    public void layerCheckbox() throws Exception {
        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Venue room" );
        venueElement.setAttribute( "width", "32.4" );
        venueElement.setAttribute( "depth", "57.5" );
        venueElement.setAttribute( "height", "12.0" );
        new Venue( venueElement );

        new Layer( "zig", "zag", color );
        Write writer = new Write();
        String output = writer.generateDesigner();
        CharSequence checkbox = "checkbox";
        CharSequence selector = "selectLayerzig";

        assertTrue(output.contains(checkbox));
        assertTrue(output.contains(selector));
    }

    @Test
    public void register() throws Exception {
        Layer layer = new Layer( tag, name, color );

        wallElement1 = new IIOMetadataNode( "wall" );
        wallElement1.setAttribute( "x1", "0" );
        wallElement1.setAttribute( "y1", "0" );
        wallElement1.setAttribute( "x2", "350" );
        wallElement1.setAttribute( "y2", "0" );
        Wall wall = new Wall( wallElement1 );

        layer.register( wall );

        ArrayList<Layerer> contents = layer.contents();

        assert( contents.contains( wall ) );
    }

////    @Test
////    public void finds() throws Exception {
////        Layer layer = new Layer( baseElement );
////
////        HangPoint found = HangPoint.Find( "Blather" );
////
////        assertSame( found, hangPoint );
////    }
////
////    @Test
////    public void findsNothing() throws Exception {
////        HangPoint found = HangPoint.Find( "Nothing" );
////
////        assertNull( found );
////    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.LayerReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
