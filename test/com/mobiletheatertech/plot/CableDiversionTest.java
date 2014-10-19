package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by dhs on 9/16/14.
 */
public class CableDiversionTest {

    Element diversionElement = null;
    Element point1Element = null;
    Element pointLastElement = null;

    Double x1 = 60.0;
    Double y1 = 18.0;
    Double z1 = 0.0;
    Double xLast = 152.0;
    Double yLast = 70.0;
    Double zLast = 0.0;

    ArrayList<Point> vertices = null;

    Point one = new Point( 12, 18, 0 );
    Point two = new Point( 152, 18, 0 );
    Point three = new Point( 152, 180, 0 );

    Point start = new Point( x1, y1, z1 );
    Point last = new Point( xLast, yLast, zLast );

    @Test
    public void isA() throws Exception {
        CableDiversion diversion = new CableDiversion( diversionElement );

        assert Elemental.class.isInstance( diversion );
    }

    @Test
    public void noDiversion() throws Exception {
        CableDiversion.InsertDiversion(vertices);

        assertSame( vertices.get( 0 ), one );
        assertSame( vertices.get( 1 ), two );
        assertSame( vertices.get( 2 ), three );
    }

    @Test
    public void oneStepDiversion() throws Exception {
        diversionElement.appendChild(point1Element);
        diversionElement.appendChild( pointLastElement );
        new CableDiversion( diversionElement );

//        ArrayList<Point> vertexList
        CableDiversion.InsertDiversion(vertices);

        assertEquals( vertices.get( 0 ), one );
        assertEquals( vertices.get( 1 ), start );
        assertEquals( vertices.get( 2 ), last );
        assertEquals( vertices.get( 3 ), three );
    }

    @Test
    public void internalUpdateVertexList() throws Exception {
        ArrayList<Point> diversion = new ArrayList<>();
        diversion.add(start);
        diversion.add( last );

        CableDiversion.UpdateVertexList(vertices, diversion, 1, 2);

        assertSame( vertices.get( 0 ), one );
        assertSame( vertices.get( 1 ), start );
        assertSame( vertices.get( 2 ), last );
        assertSame( vertices.get( 3 ), three );
    }

    @Test
    public void internalFindLastIntercepts() throws Exception {
        diversionElement.appendChild( point1Element );
        diversionElement.appendChild(pointLastElement);
        CableDiversion diversion = new CableDiversion( diversionElement );

        ArrayList<CableDiversion> lastMatches = new ArrayList<>();
        ArrayList<Integer> lastIndices = new ArrayList<>();

        CableDiversion.FindLastIntercepts(vertices, lastMatches, lastIndices);

        assertSame( lastMatches.get( 0 ), diversion );
        assertEquals(lastIndices.get(0), (Integer) 1 );
    }

    @Test
    public void internalIntercepts() throws Exception {
        Point intercept = new Point( 12, 24, 0 );
        Point start = new Point( 12, 10, 0 );
        Point end = new Point( 12, 30, 0 );

        assertTrue(CableDiversion.Intersects(intercept, start, end));
    }

    @Test
    public void internalInterceptsFail() throws Exception {
        Point intercept = new Point( 12, 54, 0 );
        Point start = new Point( 12, 10, 0 );
        Point end = new Point( 12, 30, 0 );

        assertFalse(CableDiversion.Intersects(intercept, start, end));
    }

    @Test
    public void first() throws Exception {
        diversionElement.appendChild( point1Element );
        diversionElement.appendChild( pointLastElement );
        CableDiversion diversion = new CableDiversion( diversionElement );

        assertEquals( diversion.first(), start );
    }

    @Test
    public void last() throws Exception {
        diversionElement.appendChild( point1Element );
        diversionElement.appendChild( pointLastElement );
        CableDiversion diversion = new CableDiversion( diversionElement );

        assertEquals( diversion.last(), last );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.CableDiversionReset();

        point1Element = new IIOMetadataNode( "diversion-point" );
        point1Element.setAttribute( "x", x1.toString() );
        point1Element.setAttribute( "y", y1.toString() );
        point1Element.setAttribute( "z", z1.toString() );

        pointLastElement = new IIOMetadataNode( "diversion-point" );
        pointLastElement.setAttribute( "x", xLast.toString() );
        pointLastElement.setAttribute( "y", yLast.toString() );
        pointLastElement.setAttribute( "z", zLast.toString() );

        diversionElement = new IIOMetadataNode( "cable-diversion" );

        vertices = new ArrayList<>();
        vertices.add( one );
        vertices.add( two );
        vertices.add( three );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
