package com.mobiletheatertech.plot;

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 12/6/13 Time: 3:40 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.21
 */
public class ShapeTest {

    String square = "1 1 1 8 8 8 8 1"; // square
    String polygon = "1 1 21 1 21 34 12 34 12 54 1 54";

//    string empty="";
//    String badShape=""

    Double xRelative = 76.7;
    Double yRelative = 93.6;
    String polygonRelative = "-12 17 -8 20 8 20 12 17 14 6 14 -6 12 -17 8 -20 -8 -20 -12 -17 -14 -6 -14 6 ";
    String pathRelative = "M 64.7 110.6 68.7 113.6 84.7 113.6 88.7 110.6 90.7 99.6 90.7 87.6 88.7 76.6 84.7 73.6 68.7 73.6 64.7 76.6 62.7 87.6 62.7 99.6 Z";


    @Test
    public void createsSquareVertexList() throws Exception {
        Shape shape = new Shape(square);
        Field vertexListField = TestHelpers.accessField(shape, "vertices");
        ArrayList<Point> vertices = (ArrayList<Point>) vertexListField.get(shape);

        assertNotNull(vertices);
        assertEquals(4, vertices.size());
        assertTrue(vertices.get(0).equals(new Point(1, 1, 0)));
        assertTrue(vertices.get(1).equals(new Point(1, 8, 0)));
        assertTrue(vertices.get(2).equals(new Point(8, 8, 0)));
        assertTrue(vertices.get(3).equals(new Point(8, 1, 0)));
    }

    @Test
    public void createsPolygonVertexList() throws Exception {
        Shape shape = new Shape(polygon);
        Field vertexListField = TestHelpers.accessField(shape, "vertices");
        ArrayList<Point> vertices = (ArrayList<Point>) vertexListField.get(shape);

        assertNotNull(vertices);
        assertEquals(vertices.size(), 6);
        assertTrue(vertices.get(0).equals(new Point(1, 1, 0)));
        assertTrue(vertices.get(1).equals(new Point(21, 1, 0)));
        assertTrue(vertices.get(2).equals(new Point(21, 34, 0)));
        assertTrue(vertices.get(3).equals(new Point(12, 34, 0)));
        assertTrue(vertices.get(4).equals(new Point(12, 54, 0)));
        assertTrue(vertices.get(5).equals(new Point(1, 54, 0)));
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Empty Shape specification.")
    public void empty() throws Exception {
        String empty = "";
        new Shape(empty);
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Invalid Shape specification.")
    public void tooFewVertices() throws Exception {
        String vertices = "1 2 3 4";
        new Shape(vertices);
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Invalid Shape specification.")
    public void incompleteVertex() throws Exception {
        String vertices = "1 2 3 4 5 6 7";
        new Shape(vertices);
    }

    @Test
    public void x() throws DataException {
        Shape shape = new Shape(square);

        assertEquals(shape.x(), 1.0 );
    }

    @Test
    public void y() throws DataException {
        Shape shape = new Shape(square);

        assertEquals(shape.y(), 1.0 );
    }

    @Test
    public void width() throws DataException {
        Shape shape = new Shape(square);

        assertEquals(shape.width(), 7.0 );
    }

    @Test
    public void depth() throws DataException {
        Shape shape = new Shape(square);

        assertEquals(shape.depth(), 7.0 );
    }

    @Test
    public void fitsSquarePass() throws DataException {
        Shape shape = new Shape(square);

        assertTrue(shape.fits(2, 2, 3, 3));
    }

    @Test
    public void fitsPolygonPass() throws DataException {
        Shape shape = new Shape(polygon);

        assertTrue(shape.fits(2, 2, 3, 3));
    }

    @Test
    public void fitsSquareFail() throws DataException {
        Shape shape = new Shape(square);

        assertFalse(shape.fits(2, 2, 5, 5));
    }

    @Test
    public void fitsPolygonOutsideFail() throws DataException {
        Shape shape = new Shape(polygon);

        assertFalse(shape.fits(2, 37, 5, 5));
    }

    @Test
    public void fitsPolygonIntersectsFail() throws DataException {
        Shape shape = new Shape(polygon);

        assertFalse(shape.fits(2, 32, 5, 5));
    }

    @Test
    public void toSvgPath() throws Exception {
        Shape instance = new Shape( polygonRelative );

        String path = instance.toSvgPath( xRelative, yRelative );

        assertEquals( path, pathRelative );
    }
}
