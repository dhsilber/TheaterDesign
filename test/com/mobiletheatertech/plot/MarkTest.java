package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 6/16/13 Time: 3:24 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.5
 */
public class MarkTest {

    public MarkTest() {
    }

    @Test
    public void notNull() throws Exception {
        String markOne = Mark.Generate();

        assertNotNull( markOne );
    }

    @Test
    public void differentNumbers() throws Exception {
        String markOne = Mark.Generate();
        String markTwo = Mark.Generate();

        assertNotEquals( markOne, markTwo );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }
}