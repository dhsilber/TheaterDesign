package com.mobiletheatertech.plot;

import mockit.Expectations;
import mockit.Mocked;
import org.apache.batik.svggen.SVGGraphics2D;
import org.testng.annotations.*;
import org.w3c.dom.Document;

import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

/**
 * @author dhs
 * @since 0.0.1
 */
public class DrawTest {

    public DrawTest() {
    }

    @Mocked
    SVGGraphics2D mockGraphics;

    @Test
    public void draw() {
        new Expectations() {
            {
                new SVGGraphics2D( (Document) any );
            }
        };
        new Draw();
    }

    @Test
    public void canvas() {
        Draw draw = new Draw();
        assertSame( draw.canvas(), mockGraphics );
    }

    @Test
    public void transform() {
        fail( "Test that transform shifts coordinates appropriately" );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}