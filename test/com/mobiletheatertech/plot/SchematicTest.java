package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 12/16/14.
 */
public class SchematicTest {

    Double firstX = 100.0;
    Double firstY = 100.0;
    Double height = 12.9;
    Double width = 99.4;
    Double distance = 200.0;

    @Test
    public void constants() {
        assertEquals( Schematic.FirstX, firstX );
        assertEquals( Schematic.FirstY, firstY );
    }

    @Test
    public void positionFirst() {
        assertEquals( Schematic.Position( width, height), new PagePoint( firstX, firstY ) );
    }

    @Test
    public void positionSecond() {
        Schematic.Position( width, height );
        assertEquals( Schematic.Position( width, height), new PagePoint( firstX + distance, firstY ) );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Schematic.Count = 0;
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
