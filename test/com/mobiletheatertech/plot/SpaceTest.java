package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Test {@code Space}
 *
 * @author dhs
 * @since 0.0.6
 */
public class SpaceTest {

    public SpaceTest() {
    }

    @Test
    public void storesCoordinates() throws Exception {
        Point point = new Point( 1.0, 2.0, 3.0 );
        Space space = new Space( point, 12.0, 13.0, 14.0 );

        assertEquals( TestHelpers.accessPoint( space, "origin" ), point );
        assertEquals( TestHelpers.accessDouble(space, "width"), (Double)12.0 );
        assertEquals( TestHelpers.accessDouble(space, "depth"), (Double)13.0 );
        assertEquals( TestHelpers.accessDouble(space, "height"), (Double)14.0 );
    }

    @Test
    public void storesMultipleCoordinates() throws Exception {
        Point point1 = new Point( 1.0, 2.0, 3.0 );
        Point point2 = new Point( 4.0, 5.0, 6.0 );
        Space space1 = new Space( point1, 12, 13, 14 );

        assertEquals( TestHelpers.accessPoint( space1, "origin" ), point1 );
        assertEquals( TestHelpers.accessDouble(space1, "width"), (Double)12.0 );
        assertEquals( TestHelpers.accessDouble(space1, "depth"), (Double)13.0 );
        assertEquals( TestHelpers.accessDouble(space1, "height"), (Double)14.0 );

        Space space2 = new Space( point2, 22, 23, 24 );

        assertEquals( TestHelpers.accessPoint( space2, "origin" ), point2 );
        assertEquals( TestHelpers.accessDouble(space2, "width"), (Double)22.0 );
        assertEquals( TestHelpers.accessDouble(space2, "depth"), (Double)23.0 );
        assertEquals( TestHelpers.accessDouble( space2, "height" ), (Double)24.0 );
    }

    @Test
    public void containsBoxFits() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 2.0, 4.0, 6.0 );
        Space space = new Space( point, 33, 55, 11 );

        assertTrue( container.contains( space ) );
    }

    @Test
    public void containsBoxTooWide() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 1.0, 1.0 );
        Space space = new Space( point, 100, 55, 11 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxTooDeep() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 1.0, 1.0 );
        Space space = new Space( point, 33, 150, 11 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxTooTall() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 1.0, 1.0 );
        Space space = new Space( point, 33, 55, 50 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginXTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( -1.0, 1.0, 1.0 );
        Space space = new Space( point, 1.0, 1.0, 1.0 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 101.0, 1.0, 1.0 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, -1.0, 1.0 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 151.0, 1.0 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 1.0, -1.0 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0.0, 0.0, 0.0 ), 100, 150, 50 );
        Point point = new Point( 1.0, 1.0, 51.0 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
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