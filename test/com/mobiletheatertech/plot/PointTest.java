package com.mobiletheatertech.plot;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author dhs
 * @since 0.0.2
 */
public class PointTest {
    
    public PointTest() {
    }

    @Test
    public void storesCoordinates() {
        Point point = new Point( 1, 2, 3 );

        assertEquals( point.x(), 1 );
        assertEquals( point.y(), 2 );
        assertEquals( point.z(), 3 );
    }

    @Test
    public void storesMultipleCoordinates() {
        Point point1 = new Point( 1, 2, 3 );
        Point point2 = new Point( 4, 5, 6 );

        assertEquals( point1.x(), 1 );
        assertEquals( point1.y(), 2 );
        assertEquals( point1.z(), 3 );

        assertEquals( point2.x(), 4 );
        assertEquals( point2.y(), 5 );
        assertEquals( point2.z(), 6 );
    }

    @Test
    public void extremeValues() throws Exception {
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