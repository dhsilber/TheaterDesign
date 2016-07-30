package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.assertSame;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Created by dhs on 12/18/13.
 */
public class PlaceTest {

    private Point location = null;
    private Point origin = null;
    private Double rotation = null;

    @Test
    public void stores() throws Exception {
        Place place = new Place( location, origin, rotation );

        assertSame(TestHelpers.accessPoint(place, "location"), location );
        assertSame(TestHelpers.accessPoint(place, "origin"), origin );
        assertSame(TestHelpers.accessDouble( place, "rotation"), rotation );
    }

    @Test
    public void recallLocation() throws Exception {
        Place place = new Place( location, origin, rotation );

        assertSame( place.location(), location );
    }

    @Test
    public void recallOrigin() throws Exception {
        Place place = new Place( location, origin, rotation );

        assertSame( place.origin(), origin );
    }

    @Test
    public void recallRotation() throws Exception {
        Place place = new Place( location, origin, rotation );

        assertSame( place.rotation(), rotation );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        location = new Point( 22.0, 33.0, 44.0 );
        origin = new Point( 22.0, 33.0, 1.0 );
        rotation = 4.5;
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }


}
