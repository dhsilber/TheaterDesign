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

        assertEquals( point2.x(), (Double)4.0 );
        assertEquals( point2.y(), (Double)5.0 );
        assertEquals( point2.z(), (Double)6.0 );
    }

    @Test
    public void extremeValuesDecreasing() throws Exception {
        TestResets.PointReset();

        new Point( x, y, z );
        new Point( -1.0, -2.0, -3.0 );

        assertEquals( Point.LargeX(), x );
        assertEquals( Point.LargeY(), y );
        assertEquals( Point.LargeZ(), z );

        assertEquals( Point.SmallX(), (Double)(-1.0) );
        assertEquals( Point.SmallY(), (Double)(-2.0 ));
        assertEquals( Point.SmallZ(), (Double)(-3.0) );
    }

    @Test
    public void extremeValuesIncreasing() throws Exception {
        TestResets.PointReset();

        new Point( -1.0, -2.0, -3.0 );
        new Point( x, y, z );

        assertEquals( Point.LargeX(), x );
        assertEquals( Point.LargeY(), y );
        assertEquals( Point.LargeZ(), z );

        assertEquals( Point.SmallX(), (Double)(-1.0) );
        assertEquals( Point.SmallY(),(Double)( -2.0) );
        assertEquals( Point.SmallZ(), (Double)(-3.0) );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallX() throws Exception {
        TestResets.PointReset();

        new Point( -1.0, 0.0, 0.0 );

        assertEquals( Point.LargeX(), (Double)0.0 );
        assertEquals( Point.LargeY(), (Double)0.0 );
        assertEquals( Point.LargeZ(), (Double)0.0 );

        assertEquals( Point.SmallX(), (Double)(-1.0) );
        assertEquals( Point.SmallY(), (Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeX() throws Exception {
        TestResets.PointReset();

        new Point( 1.0, 0.0, 0.0 );

        assertEquals( Point.LargeX(), (Double)1.0 );
        assertEquals( Point.LargeY(), (Double)0.0 );
        assertEquals( Point.LargeZ(), (Double)0.0 );

        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(), (Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallY() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, -1.0, 0.0 );

        assertEquals( Point.LargeX(), (Double)0.0 );
        assertEquals( Point.LargeY(), (Double)0.0 );
        assertEquals( Point.LargeZ(), (Double)0.0 );

        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(), (Double)(-1.0) );
        assertEquals( Point.SmallZ(), (Double)0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeY() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 1.0, 0.0 );

        assertEquals( Point.LargeX(), (Double)0.0 );
        assertEquals( Point.LargeY(), (Double)1.0 );
        assertEquals( Point.LargeZ(), (Double)0.0 );

        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(), (Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)0.0 );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueSmallZ() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 0.0, -1.0 );

        assertEquals( Point.LargeX(), (Double)0.0 );
        assertEquals( Point.LargeY(), (Double)0.0 );
        assertEquals( Point.LargeZ(), (Double)0.0 );

        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(), (Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)(-1.0) );
    }

    /**
     * @throws Exception
     * @since 0.0.6
     */
    @Test
    public void extremeValueLargeZ() throws Exception {
        TestResets.PointReset();

        new Point( 0.0, 0.0, 1.0 );

        assertEquals( Point.LargeX(), (Double)0.0 );
        assertEquals( Point.LargeY(), (Double)0.0 );
        assertEquals( Point.LargeZ(), (Double)1.0 );

        assertEquals( Point.SmallX(), (Double)0.0 );
        assertEquals( Point.SmallY(),(Double)0.0 );
        assertEquals( Point.SmallZ(), (Double)0.0 );
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

    @Test
    public void onALineZeroDistance() {
        Point place = Point.OnALine( point1, point2, 0.0 );

        assertEquals( place, point1, "position should match first point" );
        assertNotSame( place, point1, "points should not be same object" );
    }

    @Test
    public void onALinePositiveDistance() {
        Double distance = point1.distance( point2 );
        Point place = Point.OnALine( point1, point2, distance );

        assertEquals( place, point2 );
        assertNotSame( place, point2 );
    }

    @Test
    public void onALinePositiveDistance2() {
        Point knownPlace = new Point( 12, 13, 14 );
        Double distance = point1.distance( knownPlace );
        Point place = Point.OnALine( point1, knownPlace, distance );

        assertEquals( place, knownPlace );
        assertNotSame( place, knownPlace );
    }

    @Test
    public void onALineNegativeDistance() {
        Double distance = point1.distance( point2 );
        Point negative = new Point(
                point1.x() - (point1.x() - point2.x()),
                point1.y() - (point1.y() - point2.y()),
                point1.z() + (point1.z() - point2.z()) );

        Point place = Point.OnALine( point1, point2, -distance );

        assertEquals( place, negative );
    }

    @Test
    public void onALineNegativeDistance2() {
        Point knownPlace = new Point( 32, 43, 54 );
        Double distance = point1.distance( knownPlace );
        Point negative = new Point(
                point1.x() - 10,
                point1.y() - 10,
                point1.z() - 10 );

        Point place = Point.OnALine( point1, knownPlace, -distance );

        assertEquals( place, negative );
    }

    @Test
    public void onALineXNegative() {
        Point knownPlace = new Point( -12, 43, 54 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, distance );

        assertEquals( place, knownPlace );
    }

    @Test
    public void onALineYNegative() {
        Point knownPlace = new Point( 32, -43, 54 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, distance );

        assertEquals( place, knownPlace );
    }

    @Test
    public void onALineZNegative() {
        Point knownPlace = new Point( 32, 43, -54 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, distance );

        assertEquals( place, knownPlace );
    }

    @Test
    public void onALineXNegativeNegativeDistance() {
        Point knownPlace = new Point( -12, 43, 54 );
        Point destination = new Point( 56, 23, 34 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, -distance );

        assertEquals( place, destination );
    }

    @Test
    public void onALineYNegativeNegativeDistance() {
        Point knownPlace = new Point( 32, -43, 54 );
        Point destination = new Point( 12, 109, 34 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, -distance );

        assertEquals( place, destination );
    }

    @Test
    public void onALineZNegativeNegativeDistance() {
        Point knownPlace = new Point( 32, 43, -54 );
        Point destination = new Point( 12, 23, 142 );
        Double distance = point1.distance( knownPlace );

        Point place = Point.OnALine( point1, knownPlace, -distance );

        assertEquals( place, destination );
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