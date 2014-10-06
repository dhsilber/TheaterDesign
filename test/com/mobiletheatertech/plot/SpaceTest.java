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
        Point point = new Point( 1, 2, 3 );
        Space space = new Space( point, 12, 13, 14 );

        assertEquals( TestHelpers.accessPoint( space, "origin" ), point );
        assertEquals( TestHelpers.accessInteger( space, "width" ), (Integer) 12 );
        assertEquals( TestHelpers.accessInteger( space, "depth" ), (Integer) 13 );
        assertEquals( TestHelpers.accessInteger( space, "height" ), (Integer) 14 );
    }

    @Test
    public void storesMultipleCoordinates() throws Exception {
        Point point1 = new Point( 1, 2, 3 );
        Point point2 = new Point( 4, 5, 6 );
        Space space1 = new Space( point1, 12, 13, 14 );

        assertEquals( TestHelpers.accessPoint( space1, "origin" ), point1 );
        assertEquals( TestHelpers.accessInteger( space1, "width" ), (Integer) 12 );
        assertEquals( TestHelpers.accessInteger( space1, "depth" ), (Integer) 13 );
        assertEquals( TestHelpers.accessInteger( space1, "height" ), (Integer) 14 );

        Space space2 = new Space( point2, 22, 23, 24 );

        assertEquals( TestHelpers.accessPoint( space2, "origin" ), point2 );
        assertEquals( TestHelpers.accessInteger( space2, "width" ), (Integer) 22 );
        assertEquals( TestHelpers.accessInteger( space2, "depth" ), (Integer) 23 );
        assertEquals( TestHelpers.accessInteger( space2, "height" ), (Integer) 24 );
    }

    @Test
    public void containsBoxFits() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 2, 4, 6 );
        Space space = new Space( point, 33, 55, 11 );

        assertTrue( container.contains( space ) );
    }

    @Test
    public void containsBoxTooWide() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 100, 55, 11 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxTooDeep() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 33, 150, 11 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxTooTall() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Space space = new Space( point, 33, 55, 50 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginXTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( -1, 1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 101, 1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, -1, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 151, 1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, -1 );
        Space space = new Space( point, 1, 1, 1 );

        assertFalse( container.contains( space ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws AttributeMissingException {
        Space container = new Space( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 51 );
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