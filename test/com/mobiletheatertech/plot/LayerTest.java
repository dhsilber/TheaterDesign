package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import java.util.HashMap;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
        new Layer( "zig", "zag", color );
        Write writer = new Write();
        String output = writer.generateDesigner();
        CharSequence checkbox = "checkbox";
        CharSequence selector = "selectLayerzig";

        assertTrue(output.contains(checkbox));
        assertTrue(output.contains(selector));
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
