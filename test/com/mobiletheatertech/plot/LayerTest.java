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

    @Test
    public void providesName() throws Exception {
        Layer layer = new Layer( tag, name );

        assertEquals( layer.name(), name );
    }

    @Test
    public void providesListWithOne() throws Exception {
        new Layer( tag, name );

        HashMap<String, Layer> thing = Layer.List();

        assertTrue( thing.containsKey( tag ) );
        Layer layer = thing.get( tag );

        assertEquals( layer.name(), name );
    }

    @Test
    public void providesListWithMultiple() throws Exception {
        new Layer( tag, name );

        String name2 = "Second name";
        String tag2 = "Tag number two";
        new Layer( tag2, name2 );

        HashMap<String, Layer> thing = Layer.List();

        assertTrue( thing.containsKey( tag ) );
        assertEquals( thing.get( tag ).name(), name );
        assertTrue( thing.containsKey( tag2 ) );
        assertEquals( thing.get( tag2 ).name(), name2 );
    }

    @Test
    public void newLayerInactive() throws Exception {
        Layer foo = new Layer( tag, name );

        assertFalse(foo.active());
    }

    @Test
    public void activate() throws Exception {
        Layer foo = new Layer( tag, name );

        foo.activate();

        assertTrue(foo.active());
    }

    @Test( expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Layer "+tag+" is already defined." )
    public void noChangeName() throws Exception {
        new Layer( tag, name );
        new Layer( tag, "other name" );
    }

    @Test
    public void okSameName() throws Exception {
        new Layer( tag, name );
        new Layer( tag, name );
    }

//TODO    Test that layers are not drawn if not activated

      //TODO      Test that active layers are drawn

    @Test
    public void noLayerNoCheckbox() throws Exception {
        Write writer = new Write();
        String output = writer.generateIndex();
        CharSequence chars = "checkbox";

        assertFalse(output.contains(chars));
    }

    @Test
    public void layerCheckbox() throws Exception {
        new Layer( "zig", "zag" );
        Write writer = new Write();
        String output = writer.generateDesigner();
        CharSequence checkbox = "checkbox";
        CharSequence selector = "selectLayerzig";

        assertTrue(output.contains(checkbox));
        assertTrue(output.contains(selector));
    }


////    @Test
////    public void finds() throws Exception {
////        Layer layer = new Layer( elementOnPipe );
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
