package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.*;

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
    public void notNull() {
        String markOne = Mark.Generate();

        assertNotNull( markOne );
    }

    @Test
    public void differentNumbers() {
        String markOne = Mark.Generate();
        String markTwo = Mark.Generate();

        assertNotEquals( markOne, markTwo );
    }

    @Test
    public void increments() throws Exception {
        Object inititialObject =
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mark", "NUMBER" );
        assertNotNull( inititialObject );
        Integer initial = (Integer) inititialObject;

        Mark.Generate();

        Object incrementedObject =
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Mark", "NUMBER" );
        assertNotNull( incrementedObject );
        Integer incremented = (Integer) incrementedObject;

        initial++;
        assertEquals( incremented, initial );

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