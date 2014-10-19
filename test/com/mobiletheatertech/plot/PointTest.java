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

    Double x = 1.0;
    Double y = 2.0;
    Double z = 3.0;

    public PointTest() {
    }

    @Test
    public void storesCoordinates() {
        Point point = new Point( x, y, z );

        assertEquals( point.x(), x );
        assertEquals( point.y(), y );
        assertEquals( point.z(), z );
    }

    @Test
    public void storesCoordinatesInteger() {
        Point point = new Point( x.intValue(), y.intValue(), z.intValue() );

        assertEquals( point.x(), x );
        assertEquals( point.y(), y );
        assertEquals( point.z(), z );
    }

    @Test
    public void storesMultipleCoordinates() {
        Point point = new Point( x, y, z );
        Point point2 = new Point( 4, 5, 6 );

        assertEquals( point.x(), x );
        assertEquals( point.y(), y );
        assertEquals( point.z(), z );

        assertEquals( point2.x(), 4.0 );
        assertEquals( point2.y(), 5.0 );
        assertEquals( point2.z(), 6.0 );
    }

    @Test
    public void extremeValuesDecreasing() throws Exception {
        TestResets.PointReset();

        new Point( x, y, z );
        new Point( -1.0, -2.0, -3.0 );

        assertEquals( Point.LargeX(), x );
        assertEquals( Point.LargeY(), y );
        assertEquals( Point.LargeZ(), z );

        assertEquals( Point.SmallX(), -1.0 );
        assertEquals( Point.SmallY(), -2.0 );
        assertEquals( Point.SmallZ(), -3.0 );
    }

    @Test
    public void extremeValuesIncreasing() throws Exception {
        TestResets.PointReset();

        new Point( -1.0, -2.0, -3.0 );
        new Point( x, y, z );

        assertEquals( Point.LargeX(), x );
        assertEquals( Point.LargeY(), y );
        assertEquals( Point.LargeZ(), z );

        assertEquals( Point.SmallX(), -1.0 );
        assertEquals( Point.SmallY(), -2.0 );
        assertEquals( Point.SmallZ(), -3.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallX() throws Exception {
        TestResets.PointReset();

        new Point( -1.0, 0.0, 0.0 );

        assertEquals( Point.LargeX(), 0.0 );
        assertEquals( Point.LargeY(), 0.0 );
        assertEquals( Point.LargeZ(), 0.0 );

        assertEquals( Point.SmallX(), -1.0 );
        assertEquals( Point.SmallY(), 0.0 );
        assertEquals( Point.SmallZ(), 0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeX() throws Exception {
        TestResets.PointReset();

        new Point( 1.0, 0.0, 0.0 );

        assertEquals( Point.LargeX(), 1.0 );
        assertEquals( Point.LargeY(), 0.0 );
        assertEquals( Point.LargeZ(), 0.0 );

        assertEquals( Point.SmallX(), 0.0 );
        assertEquals( Point.SmallY(), 0.0 );
        assertEquals( Point.SmallZ(), 0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallY() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, -1.0, 0.0 );

        assertEquals( Point.LargeX(), 0.0 );
        assertEquals( Point.LargeY(), 0.0 );
        assertEquals( Point.LargeZ(), 0.0 );

        assertEquals( Point.SmallX(), 0.0 );
        assertEquals( Point.SmallY(), -1.0 );
        assertEquals( Point.SmallZ(), 0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeY() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 1.0, 0.0 );

        assertEquals( Point.LargeX(), 0.0 );
        assertEquals( Point.LargeY(), 1.0 );
        assertEquals( Point.LargeZ(), 0.0 );

        assertEquals( Point.SmallX(), 0.0 );
        assertEquals( Point.SmallY(), 0.0 );
        assertEquals( Point.SmallZ(), 0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallZ() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 0.0, -1.0 );

        assertEquals( Point.LargeX(), 0.0 );
        assertEquals( Point.LargeY(), 0.0 );
        assertEquals( Point.LargeZ(), 0.0 );

        assertEquals( Point.SmallX(), 0.0 );
        assertEquals( Point.SmallY(), 0.0 );
        assertEquals( Point.SmallZ(), -1.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeZ() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 0.0, 1.0 );

        assertEquals( Point.LargeX(), 0.0 );
        assertEquals( Point.LargeY(), 0.0 );
        assertEquals( Point.LargeZ(), 1.0 );

        assertEquals( Point.SmallX(), 0.0 );
        assertEquals( Point.SmallY(), 0.0 );
        assertEquals( Point.SmallZ(), 0.0 );
    }

    @Test
    public void distance2D() {
        Point point1 = new Point( 1.0, 2.0, 0.0 );
        Point point2 = new Point( 2.0, 3.0, 0.0 );

        double howFar = point1.distance( point2 );

        assertEquals( howFar, 1.414, 0.001 );
    }

    @Test
    public void distance3D() {
        Point point1 = new Point( 1.0, 2.0, 5.0 );
        Point point2 = new Point( 2.0, 3.0, 6.0 );

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

//    @Test
//    public void pointOnSlope() {
//        Point result = point1.pointOnSlope( 1.0, 14.0 );
//
//        assertEquals( result, new Point( 32, 43, 44 ) );
//    }
//
//    @Test
//    public void pointOnSlopeZeroDistance() {
//        Point result = point1.pointOnSlope( 1.0, 0.0 );
//
//        assertEquals( result, point1 );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        point1 = new Point( 22.0, 33.0, 44.0 );
        point2 = new Point( 22.0, 33.0, 1.0 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}