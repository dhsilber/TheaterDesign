package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;


/**
 * Created by dhs on 2/3/16.
 */
public class DistanceTest {

    @Test
    public void constructWithInches() {
        Double inch = 17.0;

        Distance distance = new Distance( inch );

        assertEquals((Double)distance.inch(), inch);
    }

    @Test
    public void inchToFeet() {
        Double inch = 18.0;

        Distance distance = new Distance( inch );

        assertEquals( distance.foot(), 1.5 );
    }

    @Test
    public void inchToFeetString() {
        Double inch = 18.0;

        Distance distance = new Distance( inch );

        assertEquals( distance.toString(), "1'6\"" );
    }

    @Test
    public void inchNegativeToFeetString() {
        Double inch = -38.0;

        Distance distance = new Distance( inch );

        assertEquals( distance.toString(), "-3'2\"" );
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
