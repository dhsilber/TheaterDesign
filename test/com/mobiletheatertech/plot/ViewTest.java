package com.mobiletheatertech.plot;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/9/13 Time: 6:00 PM To change this template use File
 * | Settings | File Templates.
 *
 * @since 0.0.7
 */

/*
This is probably overkill, but the enum could be changeds...
 */
public class ViewTest {

    @Test
    public void values() {
        assertNotNull( View.PLAN );
        assertNotNull( View.SECTION );
        assertNotNull( View.FRONT );
    }

    @Test
    public void testAll() {
        assertEquals( View.values().length, 3 );
    }

    @Test
    public void different() {
        assertNotEquals( View.PLAN, View.FRONT );
        assertNotEquals( View.PLAN, View.SECTION );
        assertNotEquals( View.SECTION, View.FRONT );
    }
}
