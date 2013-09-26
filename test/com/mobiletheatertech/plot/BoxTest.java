package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Test {@code Box}
 *
 * @author dhs
 * @since 0.0.6
 */
public class BoxTest {

    public BoxTest() {
    }

    @Test
    public void storesCoordinates() throws Exception {
        Point point = new Point( 1, 2, 3 );
        Box box = new Box( point, 12, 13, 14 );

        assertEquals( TestHelpers.accessPoint( box, "origin" ), point );
        assertEquals( TestHelpers.accessInteger( box, "width" ), (Integer) 12 );
        assertEquals( TestHelpers.accessInteger( box, "depth" ), (Integer) 13 );
        assertEquals( TestHelpers.accessInteger( box, "height" ), (Integer) 14 );
    }

    @Test
    public void storesMultipleCoordinates() throws Exception {
        Point point1 = new Point( 1, 2, 3 );
        Point point2 = new Point( 4, 5, 6 );
        Box box1 = new Box( point1, 12, 13, 14 );

        assertEquals( TestHelpers.accessPoint( box1, "origin" ), point1 );
        assertEquals( TestHelpers.accessInteger( box1, "width" ), (Integer) 12 );
        assertEquals( TestHelpers.accessInteger( box1, "depth" ), (Integer) 13 );
        assertEquals( TestHelpers.accessInteger( box1, "height" ), (Integer) 14 );

        Box box2 = new Box( point2, 22, 23, 24 );

        assertEquals( TestHelpers.accessPoint( box2, "origin" ), point2 );
        assertEquals( TestHelpers.accessInteger( box2, "width" ), (Integer) 22 );
        assertEquals( TestHelpers.accessInteger( box2, "depth" ), (Integer) 23 );
        assertEquals( TestHelpers.accessInteger( box2, "height" ), (Integer) 24 );
    }

    @Test
    public void containsBoxFits() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 2, 4, 6 );
        Box box = new Box( point, 33, 55, 11 );

        assertTrue( container.contains( box ) );
    }

    @Test
    public void containsBoxTooWide() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 100, 55, 11 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxTooDeep() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 150, 11 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxTooTall() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 1 );
        Box box = new Box( point, 33, 55, 50 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginXTooSmall() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( -1, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginXTooLarge() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 101, 1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooSmall() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, -1, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginYTooLarge() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 151, 1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooSmall() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, -1 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
    }

    @Test
    public void containsBoxOriginZTooLarge() throws AttributeMissingException {
        Box container = new Box( new Point( 0, 0, 0 ), 100, 150, 50 );
        Point point = new Point( 1, 1, 51 );
        Box box = new Box( point, 1, 1, 1 );

        assertFalse( container.contains( box ) );
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