package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by dhs on 5/28/14.
 */
public class InferenceTest {

    Session show;

    String key = "SetupName";
    String data = "Session Name";

    public InferenceTest() {
    }

    @Test
    public void reset() throws Exception {
        Inference.Reset( );

        assertEquals( Inference.Count(), 0 );
    }

    @Test
    public void count() throws Exception {
        Inference.Add(key, data);

        assertEquals( Inference.Count(), 1 );
    }

    @Test
    public void add() throws Exception {
        Inference.Add( key, data );

        assertEquals( Inference.Count(), 1 );
//        assertEquals( Inference.Get( key ), data );
    }

    @Test
    public void get() throws Exception {
        Inference.Add( key, data );
        Inference.Add( key, "different data" );

        assertEquals( Inference.Count(), 1 );
        ArrayList<String> values = Inference.Get( key );
        assertNotNull( values);
        assertEquals( values.size(), 2);
    }

//    @Test
//    public void useKeytwice() throws Exception {
//        Inference.Add( key, data );
//        Inference.Add( key, "different data" );
//
//        assertEquals( Inference.Count(), 1 );
////        assertEquals( Inference.Get( key ), data );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Inference.Reset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}