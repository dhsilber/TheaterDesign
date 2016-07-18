package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 1/3/15.
 */
//public class CableCounterTest {
//
//    Element runElement;
//    private final String signalName = "Signal";
//    private final String sourceName = "Source device";
//    private final String sinkName = "Sink device";
//
//    @Test
//    public void add() throws Exception {
//        UserCableRun run = new UserCableRun(runElement);
//        CableRun cableRun = run.cableRun();
//        CableCounter counter = new CableCounter();
//
//        counter.add( Direction.UP, cableRun );
//
//        assertEquals( counter.count(Direction.UP), 1 );
//    }
//
//    @BeforeClass
//    public static void setUpClass() throws Exception {
//    }
//
//    @AfterClass
//    public static void tearDownClass() throws Exception {
//    }
//
//    @BeforeMethod
//    public void setUpMethod() throws Exception {
//
//        runElement = new IIOMetadataNode("cable-run");
//        runElement.setAttribute("signal", signalName );
//        runElement.setAttribute("source", sourceName );
//        runElement.setAttribute("sink", sinkName );
//    }
//
//    @AfterMethod
//    public void tearDownMethod() throws Exception {
//    }
//}
