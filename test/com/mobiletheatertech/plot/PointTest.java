package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * @author dhs
 * @since 0.0.2
 */
public class PointTest {

    private Point point1 = null;
    private Point point2 = null;

    public PointTest() {
    }

    @Test
    public void storesCoordinates() {
        Point point = new Point( 1, 2, 3 );

        assertEquals( point.x(), (Integer) 1 );
        assertEquals( point.y(), (Integer) 2 );
        assertEquals( point.z(), 3 );
    }

    @Test
    public void storesMultipleCoordinates() {
        Point point1 = new Point( 1, 2, 3 );
        Point point2 = new Point( 4, 5, 6 );

        assertEquals( point1.x(), (Integer) 1 );
        assertEquals( point1.y(), (Integer) 2 );
        assertEquals( point1.z(), 3 );

        assertEquals( point2.x(), (Integer) 4 );
        assertEquals( point2.y(), (Integer) 5 );
        assertEquals( point2.z(), 6 );
    }

    @Test
    public void extremeValuesDecreasing() throws Exception {
        TestHelpers.PointReset();

        new Point( 1, 2, 3 );
        new Point( -1, -2, -3 );

        assertEquals( Point.LargeX(), 1 );
        assertEquals( Point.LargeY(), 2 );
        assertEquals( Point.LargeZ(), 3 );

        assertEquals( Point.SmallX(), -1 );
        assertEquals( Point.SmallY(), -2 );
        assertEquals( Point.SmallZ(), -3 );
    }

    @Test
    public void extremeValuesIncreasing() throws Exception {
        TestHelpers.PointReset();

        new Point( -1, -2, -3 );
        new Point( 1, 2, 3 );

        assertEquals( Point.LargeX(), 1 );
        assertEquals( Point.LargeY(), 2 );
        assertEquals( Point.LargeZ(), 3 );

        assertEquals( Point.SmallX(), -1 );
        assertEquals( Point.SmallY(), -2 );
        assertEquals( Point.SmallZ(), -3 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallX() throws Exception {
        TestHelpers.PointReset();

        new Point( -1, 0, 0 );

        assertEquals( Point.LargeX(), 0 );
        assertEquals( Point.LargeY(), 0 );
        assertEquals( Point.LargeZ(), 0 );

        assertEquals( Point.SmallX(), -1 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeX() throws Exception {
        TestHelpers.PointReset();

        new Point( 1, 0, 0 );

        assertEquals( Point.LargeX(), 1 );
        assertEquals( Point.LargeY(), 0 );
        assertEquals( Point.LargeZ(), 0 );

        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallY() throws Exception {
        TestHelpers.PointReset();

        new Point( 0, -1, 0 );

        assertEquals( Point.LargeX(), 0 );
        assertEquals( Point.LargeY(), 0 );
        assertEquals( Point.LargeZ(), 0 );

        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), -1 );
        assertEquals( Point.SmallZ(), 0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeY() throws Exception {
        TestHelpers.PointReset();

        new Point( 0, 1, 0 );

        assertEquals( Point.LargeX(), 0 );
        assertEquals( Point.LargeY(), 1 );
        assertEquals( Point.LargeZ(), 0 );

        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallZ() throws Exception {
        TestHelpers.PointReset();

        new Point( 0, 0, -1 );

        assertEquals( Point.LargeX(), 0 );
        assertEquals( Point.LargeY(), 0 );
        assertEquals( Point.LargeZ(), 0 );

        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), -1 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeZ() throws Exception {
        TestHelpers.PointReset();

        new Point( 0, 0, 1 );

        assertEquals( Point.LargeX(), 0 );
        assertEquals( Point.LargeY(), 0 );
        assertEquals( Point.LargeZ(), 1 );

        assertEquals( Point.SmallX(), 0 );
        assertEquals( Point.SmallY(), 0 );
        assertEquals( Point.SmallZ(), 0 );
    }

    @Test
    public void distance2D() {
        Point point1 = new Point( 1, 2, 0 );
        Point point2 = new Point( 2, 3, 0 );

        double howFar = point1.distance( point2 );

        assertEquals( howFar, 1.414, 0.001 );
    }

    @Test
    public void distance3D() {
        Point point1 = new Point( 1, 2, 5 );
        Point point2 = new Point( 2, 3, 6 );

        double howFar = point1.distance( point2 );

        assertEquals( howFar, 1.732, 0.001 );
    }

    /**
     * @since 0.0.6
     */
    @Test
    public void equalsReflexive() {
        assertTrue( point1.equals( point1 ) );
    }

    @Test
    public void equalsNonNull() {
        assertFalse( point1.equals( null ) );
    }

    @Test
    public void equalsNotEquals() {
        assertFalse( point1.equals( point2 ) );
        assertFalse( point2.equals( point1 ) );
    }

    @Test
    public void equalsEquals() {
        Point pointOne = new Point( point1.x(), point1.y(), point1.z() );
        assertTrue( point1.equals( pointOne ) );
        assertTrue( pointOne.equals( point1 ) );
    }

    @Test
    public void equalsOtherClass() {
        assertFalse( point1.equals( this ) );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        point1 = new Point( 22, 33, 44 );
        point2 = new Point( 22, 33, 1 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}