package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/24/13 Time: 6:57 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

/**
 * Test {@code Wall}.
 *
 * @author dhs
 * @since 0.0.11
 */
public class WallTest {

    Element wallElement = null;
    Element wallElement2 = null;
    Element wallElement3 = null;
    Double x1 = 12.0;
    Double y1 = 34.0;
    Double x2 = 19.0;
    Double y2 = 30.0;
    Double x3 = 78.0;
    Double y3 = 89.0;



    @Test
    public void isMinderDom() throws Exception {
        Wall wall = new Wall( wallElement );

        assert MinderDom.class.isInstance( wall );
    }

    @Test
    public void storesAttributes() throws Exception {
        Wall wall = new Wall( wallElement );

        assertEquals( TestHelpers.accessDouble( wall, "x1" ), x1 );
        assertEquals( TestHelpers.accessDouble( wall, "y1" ), y1 );
        assertEquals( TestHelpers.accessDouble( wall, "x2" ), x2 );
        assertEquals( TestHelpers.accessDouble( wall, "y2" ), y2 );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Wall instance is missing required 'x1' attribute.")
    public void noX1() throws Exception {
        wallElement.removeAttribute("x1");
        new Wall( wallElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Wall instance is missing required 'y1' attribute.")
    public void noY1() throws Exception {
        wallElement.removeAttribute("y1");
        new Wall( wallElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Wall instance is missing required 'x2' attribute.")
    public void noX2() throws Exception {
        wallElement.removeAttribute("x2");
        new Wall( wallElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Wall instance is missing required 'y2' attribute.")
    public void noY2() throws Exception {
        wallElement.removeAttribute("y2");
        new Wall( wallElement );
    }

    @Test
    public void verifyAngledWall() throws Exception {
        Wall wall = new Wall( wallElement );
        wall.verify();
    }

    @Test(expectedExceptions = FeatureException.class,
          expectedExceptionsMessageRegExp = "Wall at angle does not yet support openings.")
    public void verifyAngledWallWithOpening() throws Exception {
        Element openingElement = new IIOMetadataNode( "opening" );
        openingElement.setAttribute( "width", "6" );
        openingElement.setAttribute( "height", "8" );
        openingElement.setAttribute( "start", "5" );
        wallElement.appendChild(openingElement);

        Wall wall = new Wall( wallElement );
        wall.verify();
    }

    @Test
    public void findChildOpeningSideWall() throws Exception {
        wallElement = new IIOMetadataNode( "wall" );
        wallElement.setAttribute( "x1", "20" );
        wallElement.setAttribute("y1", "30");
        wallElement.setAttribute("x2", "20");
        wallElement.setAttribute("y2", "70");

        Element openingElement = new IIOMetadataNode( "opening" );
        openingElement.setAttribute("width", "6");
        openingElement.setAttribute( "height", "8" );
        openingElement.setAttribute( "start", "17" );
        wallElement.appendChild(openingElement);

        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );
        wall.verify();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 2 );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "30.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "47.0" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "53.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "70.0" );
    }

    @Test
    public void recalls() throws Exception {
        Wall wall = new Wall( wallElement );
        assertTrue( Wall.WallList().contains( wall ) );
    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Wall.CATEGORY ) );
//
//        new Wall( wallElement );
//
//        assertNotNull( Category.Select( Wall.CATEGORY ) );
//    }

    @Test
    public void findNearestWall() throws Exception {
        Element wallElementInner = new IIOMetadataNode( "wall" );
        wallElementInner.setAttribute("x1", "100");
        wallElementInner.setAttribute("y1", "150");
        wallElementInner.setAttribute("x2", "100");
        wallElementInner.setAttribute("y2", "250");

        Wall wall = new Wall( wallElement );
        wall.verify();
        Wall wall2 = new Wall( wallElementInner );
        wall2.verify();

        assertSame(Wall.WallNearestPoint(new Point(90.0, 200.0, 0.0)), wall2);
    }

    @Test
    public void findNearestWallPoint() throws Exception {
        Element wallElement = new IIOMetadataNode( "wall" );
        wallElement.setAttribute( "x1", "10" );
        wallElement.setAttribute( "y1", "150" );
        wallElement.setAttribute( "x2", "10" );
        wallElement.setAttribute( "y2", "250" );

        Wall wall = new Wall( wallElement );
        wall.verify();
        Wall wall2 = new Wall( wallElement );
        wall2.verify();

        Point nearest = wall2.nearestPointNearWall( new Point(90.0, 200.0, 0.0) );
        assertEquals( nearest, new Point(12.0, 200.0, 0.0) );
    }

    @Test
    public void findNextWallSegment() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        assertSame( wall1.next(), wall2 );
        assertSame( wall2.next(), wall3 );
        assertSame( wall3.next(), wall1 );
    }

    @Test
    public void findPreviousWallSegment() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        assertSame( wall1.previous(), wall3 );
        assertSame( wall2.previous(), wall1 );
        assertSame( wall3.previous(), wall2 );
    }

    @Test
    public void findNextCorner() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );
        Point corner = new Point( x2 + 2.0, y2 + 2.0, 0.0 );

        Point result = wall1.nextCorner( start, destination, wall2 );

        assertEquals( result, corner );
    }

    @Test
    public void findNextCorner2() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point destination = new Point( 46.0, 70.0, 0.0 );
        Point corner = new Point( x2 + 2, y2 + 2, 0.0 );

        Point result = wall2.nextCorner( corner, destination, wall2 );

        assertEquals( result, destination );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextCornerDiscontinuousX() throws Exception {
        Double badX = x2 + 4;
        wallElement2.setAttribute( "x2", badX.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );

        wall1.nextCorner( start, destination, wall2 );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextCornerDiscontinuousY() throws Exception {
        Double badY = y2 + 4;
        wallElement2.setAttribute( "y2", badY.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );

        wall1.nextCorner( start, destination, wall2 );
    }

    @Test
    public void findNextCornerPrevious() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );
        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );

        Point result = wall2.nextCorner( start, destination, wall2 );

        assertEquals( result, corner );
    }

    @Test
    public void findNextCornerPrevious2() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point destination = new Point( 18.0, 40.0, 0.0 );
        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );

        Point result = wall2.nextCorner( corner, destination, wall2 );

        assertEquals( result, destination );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextCornerPreviousDiscontinuousX() throws Exception {
        Double badX = x2 + 4;
        wallElement2.setAttribute( "x2", badX.toString() );
        Double badX3 = x3 + 99;
        wallElement3.setAttribute( "x2", badX3.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );

        wall2.nextCorner( start, destination, wall2 );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextCornerPreviousDiscontinuousY() throws Exception {
        Double badY = y2 + 4;
        wallElement2.setAttribute( "y2", badY.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );

        wall2.nextCorner( start, destination, wall2 );
    }

    @Test
    public void findNextNear() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );
        Point corner = new Point( x2 + 2.0, y2 + 2.0, 0.0 );

        Point result = null;
//        result = wall1.nextNear( start, destination, wall2 );

        assertEquals( result, corner );
    }

    @Test
    public void findNextNear2() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point destination = new Point( 46.0, 70.0, 0.0 );
        Point corner = new Point( x2 + 2, y2 + 2, 0.0 );

        Point result = null;
//        result = wall2.nextNear( corner, destination, wall2 );

        assertEquals( result, destination );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextNearDiscontinuousX() throws Exception {
        Double badX = x2 + 4;
        wallElement2.setAttribute( "x2", badX.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );

        wall1.nextNear( start, destination, wall2 );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextNearDiscontinuousY() throws Exception {
        Double badY = y2 + 4;
        wallElement2.setAttribute( "y2", badY.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 18.0, 40.0, 0.0 );
        Point destination = new Point( 46.0, 70.0, 0.0 );

        wall1.nextNear( start, destination, wall2 );
    }

    @Test
    public void findNextNearPrevious() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );
        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );

        Point result = null;
//        result = wall2.nextNear( start, destination, wall2 );

        assertEquals( result, corner );
    }

    @Test
    public void findNextNearPrevious2() throws Exception {
        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point destination = new Point( 18.0, 40.0, 0.0 );
        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );

        Point result = null;
//        result = wall2.nextNear( corner, destination, wall2 );

        assertEquals( result, destination );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextNearPreviousDiscontinuousX() throws Exception {
        Double badX = x2 + 4;
        wallElement2.setAttribute( "x2", badX.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );

        wall2.nextNear( start, destination, wall2 );
    }

    @Test(expectedExceptions = DataException.class,
            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
    public void findNextNearPreviousDiscontinuousY() throws Exception {
        Double badY = y2 + 4;
        wallElement2.setAttribute( "y2", badY.toString() );

        Wall wall1 = new Wall( wallElement );
        Wall wall2 = new Wall( wallElement2 );
        Wall wall3 = new Wall( wallElement3 );

        wall1.verify();
        wall2.verify();
        wall3.verify();

        Point start = new Point( 46.0, 70.0, 0.0 );
        Point destination = new Point( 28.0, 40.0, 0.0 );

        wall2.nextNear( start, destination, wall2 );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), x1.toString() );
        assertEquals( wallElement.getAttribute( "y1" ), y1.toString() );
        assertEquals( wallElement.getAttribute( "x2" ), x2.toString() );
        assertEquals( wallElement.getAttribute( "y2" ), y2.toString() );

        assertEquals( wallElement.getAttribute( "stroke" ), "black" );
        assertEquals( wallElement.getAttribute( "stroke-width" ), "2" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.WallReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        wallElement = new IIOMetadataNode( "wall" );
        wallElement.setAttribute( "x1", x1.toString() );
        wallElement.setAttribute( "y1", y1.toString() );
        wallElement.setAttribute( "x2", x2.toString() );
        wallElement.setAttribute( "y2", y2.toString() );

        wallElement2 = new IIOMetadataNode( "wall" );
        wallElement2.setAttribute( "x1", x2.toString() );
        wallElement2.setAttribute( "y1", y2.toString() );
        wallElement2.setAttribute( "x2", x3.toString() );
        wallElement2.setAttribute( "y2", y3.toString() );

        wallElement3 = new IIOMetadataNode( "wall" );
        wallElement3.setAttribute( "x1", x3.toString() );
        wallElement3.setAttribute( "y1", y3.toString() );
        wallElement3.setAttribute( "x2", x1.toString() );
        wallElement3.setAttribute( "y2", y1.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
