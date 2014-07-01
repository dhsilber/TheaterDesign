package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 8:15 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.19
 */
public class LayerTest {
    String name = "Name text";
    String tag = "Tag text";

    @Test
    public void providesListWithOne() throws Exception {
        new Layer( name, tag );

        HashMap<String, String> thing = Layer.List();

        assertTrue( thing.containsKey( name ) );
        assertTrue( thing.containsValue( tag ) );
    }

    @Test
    public void providesListWithMultiple() throws Exception {
        new Layer( name, tag );

        String name2 = "Second name";
        String tag2 = "Tag number two";
        new Layer( name2, tag2 );

        HashMap<String, String> thing = Layer.List();

        assertTrue( thing.containsKey( name ) );
        assertTrue( thing.containsValue( tag ) );
        assertTrue( thing.containsKey( name2 ) );
        assertTrue( thing.containsValue( tag2 ) );
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
//        TestResets.LayerReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
