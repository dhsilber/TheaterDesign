package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 8:31 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.7
 */
public class PagePointTest {

    private PagePoint point1 = null;
    private PagePoint point2 = null;

    public PagePointTest() {
    }

    @Test
    public void storesCoordinates() {
        PagePoint point = new PagePoint( 1.0, 2.0 );

        assertEquals( point.x(), 1.0 );
        assertEquals( point.y(), 2.0 );
    }

    @Test
    public void storesMultipleCoordinates() {
        PagePoint point1 = new PagePoint( 1.0, 2.0 );
        PagePoint point2 = new PagePoint( 4.0, 5.0 );

        assertEquals( point1.x(), 1.0 );
        assertEquals( point1.y(), 2.0 );

        assertEquals( point2.x(), 4.0 );
        assertEquals( point2.y(), 5.0 );
    }

//    @Test
//    public void extremeValuesDecreasing() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( 1, 2);
//        new PagePoint( -1, -2 );
//
//        assertEquals( PagePoint.LargeX(), 1 );
//        assertEquals( PagePoint.LargeY(), 2 );
//
//        assertEquals( PagePoint.SmallX(), -1 );
//        assertEquals( PagePoint.SmallY(), -2 );
//    }
//
//    @Test
//    public void extremeValuesIncreasing() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( -1, -2 );
//        new PagePoint( 1, 2 );
//
//        assertEquals( PagePoint.LargeX(), 1 );
//        assertEquals( PagePoint.LargeY(), 2 );
//
//        assertEquals( PagePoint.SmallX(), -1 );
//        assertEquals( PagePoint.SmallY(), -2 );
//    }
//
//    /**
//     * @throws Exception
//     * @since 0.0.6
//     */
//    @Test
//    public void extremeValueSmallX() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( -1, 0 );
//
//        assertEquals( PagePoint.LargeX(), 0 );
//        assertEquals( PagePoint.LargeY(), 0 );
//
//        assertEquals( PagePoint.SmallX(), -1 );
//        assertEquals( PagePoint.SmallY(), 0 );
//    }
//
//    /**
//     * @throws Exception
//     * @since 0.0.6
//     */
//    @Test
//    public void extremeValueLargeX() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( 1, 0 );
//
//        assertEquals( PagePoint.LargeX(), 1 );
//        assertEquals( PagePoint.LargeY(), 0 );
//
//        assertEquals( PagePoint.SmallX(), 0 );
//        assertEquals( PagePoint.SmallY(), 0 );
//    }
//
//    /**
//     * @throws Exception
//     * @since 0.0.6
//     */
//    @Test
//    public void extremeValueSmallY() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( 0, -1 );
//
//        assertEquals( PagePoint.LargeX(), 0 );
//        assertEquals( PagePoint.LargeY(), 0 );
//
//        assertEquals( PagePoint.SmallX(), 0 );
//        assertEquals( PagePoint.SmallY(), -1 );
//    }
//
//    /**
//     * @throws Exception
//     * @since 0.0.6
//     */
//    @Test
//    public void extremeValueLargeY() throws Exception {
//        TestHelpers.PagePointReset();
//
//        new PagePoint( 0, 1 );
//
//        assertEquals( PagePoint.LargeX(), 0 );
//        assertEquals( PagePoint.LargeY(), 1 );
//
//        assertEquals( PagePoint.SmallX(), 0 );
//        assertEquals( PagePoint.SmallY(), 0 );
//    }

//    @Test
//    public void distance2D() {
//        PagePoint point1 = new PagePoint( 1, 2 );
//        PagePoint point2 = new PagePoint( 2, 3 );
//
//        double howFar = point1.distance( point2 );
//
//        assertEquals( howFar, 1.414, 0.001 );
//    }

    /**
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
        PagePoint pointOne = new PagePoint( point1.x(), point1.y() );
        assertTrue( point1.equals( pointOne ) );
        assertTrue( pointOne.equals( point1 ) );
    }

    @Test
    public void equalsOtherClass() {
        assertFalse( point1.equals( this ) );
    }

    @Test
    public void toStringTest() {
        assertEquals( point1.toString(), "PagePoint {x=22.0, y=33.0}" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        point1 = new PagePoint( 22.0, 33.0 );
        point2 = new PagePoint( 22.0, 34.0 );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}