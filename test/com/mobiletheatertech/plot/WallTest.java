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

import java.util.ArrayList;
import java.util.Iterator;

import static org.testng.Assert.*;

/**
 * Test {@code Wall}.
 *
 * @author dhs
 * @since 0.0.11
 */
public class WallTest {

    Double epsilon = 0.000001;

    Element wallElement = null;
    Element wallElement2 = null;
    Element wallElement3 = null;
    Double x1 = 12.0;
    Double y1 = 34.0;
    Double x2 = 19.0;
    Double y2 = 30.0;
    Double x3 = 78.0;
    Double y3 = 89.0;

    Element wallEndWithOpeningElement;
    Double wwoX1 = 10.0;
    Double wwoX2 = 155.9;
    Double wwoY = 57.0;

    Element openingElement;
    Double openHeight = 7.3;
    Double openWidth = 17.0;
    Double openStart = 15.4;

    Element openingElement2;
    Double open2Height = 7.3;
    Double open2Width = 17.0;
    Double open2Start = 55.4;




    @Test
    public void isA() throws Exception {
        Wall instance = new Wall( wallElement );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assert ProtoWall.class.isInstance( instance );
        assertFalse( UniqueId.class.isInstance( instance ) );

        assertTrue( Populate.class.isInstance( instance ) );
        assertFalse( IsClamp.class.isInstance( instance) );
//        assert Schematicable.class.isInstance( instance );
        assertFalse( Legendable.class.isInstance( instance ) );
    }

    @Test
    public void constantTag() {
        assertEquals( Wall.Tag(), "wall" );
    }

    @Test
    public void constantColor() {
        assertEquals( Wall.Color(), "black" );
    }

//    @Test
//    public void storesAttributes() throws Exception {
//        Wall wall = new Wall( wallElement );
//
//        assertEquals( TestHelpers.accessDouble( wall, "x1" ), x1 );
//        assertEquals( TestHelpers.accessDouble( wall, "y1" ), y1 );
//        assertEquals( TestHelpers.accessDouble( wall, "x2" ), x2 );
//        assertEquals( TestHelpers.accessDouble( wall, "y2" ), y2 );
//    }

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
    public void recalls() throws Exception {
        Wall wall = new Wall( wallElement );
        assertTrue( Wall.WallList().contains( wall ) );
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

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Wall.Tag ) );
//
//        new Wall( wallElement );
//
//        assertNotNull( Category.Select( Wall.Tag ) );
//    }

//    @Test
//    public void findNearestWall() throws Exception {
//        Element wallElementInner = new IIOMetadataNode( "wall" );
//        wallElementInner.setAttribute("x1", "100");
//        wallElementInner.setAttribute("y1", "150");
//        wallElementInner.setAttribute("x2", "100");
//        wallElementInner.setAttribute("y2", "250");
//
//        Wall wall = new Wall( wallElement );
//        wall.verify();
//        Wall wall2 = new Wall( wallElementInner );
//        wall2.verify();
//
//        assertSame(Wall.WallNearestPoint(new Point(90.0, 200.0, 0.0)), wall2);
//    }

//    @Test
//    public void findNearestWallPoint() throws Exception {
//        Element wallElement = new IIOMetadataNode( "wall" );
//        wallElement.setAttribute( "x1", "10" );
//        wallElement.setAttribute( "y1", "150" );
//        wallElement.setAttribute( "x2", "10" );
//        wallElement.setAttribute( "y2", "250" );
//
//        Wall wall = new Wall( wallElement );
//        wall.verify();
//        Wall wall2 = new Wall( wallElement );
//        wall2.verify();
//
//        Point nearest = wall2.nearestPointNearWall( new Point(90.0, 200.0, 0.0) );
//        assertEquals( nearest, new Point(12.0, 200.0, 0.0) );
//    }

//    @Test
//    public void findNextWallSegment() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        assertSame( wall1.next(), wall2 );
//        assertSame( wall2.next(), wall3 );
//        assertSame( wall3.next(), wall1 );
//    }

//    @Test
//    public void findPreviousWallSegment() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        assertSame( wall1.previous(), wall3 );
//        assertSame( wall2.previous(), wall1 );
//        assertSame( wall3.previous(), wall2 );
//    }

//    @Test
//    public void findNextCorner() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//        Point corner = new Point( x2 + 2.0, y2 + 2.0, 0.0 );
//
//        Point result = wall1.nextCorner( start, destination, wall2 );
//
//        assertEquals( result, corner );
//    }

//    @Test
//    public void findNextCorner2() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//        Point corner = new Point( x2 + 2, y2 + 2, 0.0 );
//
//        Point result = wall2.nextCorner( corner, destination, wall2 );
//
//        assertEquals( result, destination );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextCornerDiscontinuousX() throws Exception {
//        Double badX = x2 + 4;
//        wallElement2.setAttribute( "x2", badX.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//
//        wall1.nextCorner( start, destination, wall2 );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextCornerDiscontinuousY() throws Exception {
//        Double badY = y2 + 4;
//        wallElement2.setAttribute( "y2", badY.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//
//        wall1.nextCorner( start, destination, wall2 );
//    }

//    @Test
//    public void findNextCornerPrevious() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );
//
//        Point result = wall2.nextCorner( start, destination, wall2 );
//
//        assertEquals( result, corner );
//    }

//    @Test
//    public void findNextCornerPrevious2() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point destination = new Point( 18.0, 40.0, 0.0 );
//        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );
//
//        Point result = wall2.nextCorner( corner, destination, wall2 );
//
//        assertEquals( result, destination );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextCornerPreviousDiscontinuousX() throws Exception {
//        Double badX = x2 + 4;
//        wallElement2.setAttribute( "x2", badX.toString() );
//        Double badX3 = x3 + 99;
//        wallElement3.setAttribute( "x2", badX3.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//
//        wall2.nextCorner( start, destination, wall2 );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextCornerPreviousDiscontinuousY() throws Exception {
//        Double badY = y2 + 4;
//        wallElement2.setAttribute( "y2", badY.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//
//        wall2.nextCorner( start, destination, wall2 );
//    }

//    @Test
//    public void findNextNear() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//        Point corner = new Point( x2 + 2.0, y2 + 2.0, 0.0 );
//
//        Point result = null;
////        result = wall1.nextNear( start, destination, wall2 );
//
//        assertEquals( result, corner );
//    }

//    @Test
//    public void findNextNear2() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//        Point corner = new Point( x2 + 2, y2 + 2, 0.0 );
//
//        Point result = null;
////        result = wall2.nextNear( corner, destination, wall2 );
//
//        assertEquals( result, destination );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextNearDiscontinuousX() throws Exception {
//        Double badX = x2 + 4;
//        wallElement2.setAttribute( "x2", badX.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//
//        wall1.nextNear( start, destination, wall2 );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextNearDiscontinuousY() throws Exception {
//        Double badY = y2 + 4;
//        wallElement2.setAttribute( "y2", badY.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 18.0, 40.0, 0.0 );
//        Point destination = new Point( 46.0, 70.0, 0.0 );
//
//        wall1.nextNear( start, destination, wall2 );
//    }

//    @Test
//    public void findNextNearPrevious() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );
//
//        Point result = null;
////        result = wall2.nextNear( start, destination, wall2 );
//
//        assertEquals( result, corner );
//    }

//    @Test
//    public void findNextNearPrevious2() throws Exception {
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point destination = new Point( 18.0, 40.0, 0.0 );
//        Point corner = new Point( x2 - 2, y2 - 2, 0.0 );
//
//        Point result = null;
////        result = wall2.nextNear( corner, destination, wall2 );
//
//        assertEquals( result, destination );
//    }

//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextNearPreviousDiscontinuousX() throws Exception {
//        Double badX = x2 + 4;
//        wallElement2.setAttribute( "x2", badX.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//
//        wall2.nextNear( start, destination, wall2 );
//    }
//
//    @Test(expectedExceptions = DataException.class,
//            expectedExceptionsMessageRegExp = "Cannot find abutting wall.")
//    public void findNextNearPreviousDiscontinuousY() throws Exception {
//        Double badY = y2 + 4;
//        wallElement2.setAttribute( "y2", badY.toString() );
//
//        Wall wall1 = new Wall( wallElement );
//        Wall wall2 = new Wall( wallElement2 );
//        Wall wall3 = new Wall( wallElement3 );
//
//        wall1.verify();
//        wall2.verify();
//        wall3.verify();
//
//        Point start = new Point( 46.0, 70.0, 0.0 );
//        Point destination = new Point( 28.0, 40.0, 0.0 );
//
//        wall2.nextNear( start, destination, wall2 );
//    }

    @Test
    public void populateChildrenOpeningNone() {
        Wall wall = new Wall( wallElement );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venueInstance = list.get( 0 );
        assert MinderDom.class.isInstance( venueInstance );
        assert Venue.class.isInstance( venueInstance );

        ElementalLister wallInstance = list.get( 1 );
        assert MinderDom.class.isInstance( wallInstance );
        assert Wall.class.isInstance( wallInstance );

        assertEquals( list.size(), 2 );
        assertSame( wallInstance, wall );

        assertEquals( wall.openingList().size(), 0 );
    }

    @Test
    public void populateChildrenOpeningOne() {
        wallEndWithOpeningElement.appendChild( openingElement );
        Wall wall = new Wall(wallEndWithOpeningElement);

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venueInstance = list.get( 0 );
        assert MinderDom.class.isInstance( venueInstance );
        assert Venue.class.isInstance( venueInstance );

        ElementalLister wallInstance = list.get( 1 );
        assert MinderDom.class.isInstance( wallInstance );
        assert Wall.class.isInstance( wallInstance );

        assertEquals( list.size(), 2 );
        assertSame( wallInstance, wall );

        assertEquals( wall.openingList().size(), 1 );
    }

    @Test
    public void populateChildrenOpeningTwo() {
        wallEndWithOpeningElement.appendChild( openingElement );
        wallEndWithOpeningElement.appendChild( openingElement2 );

        checkTwoChildOpenings();
    }

    @Test
    public void populateChildrenOpeningTwoReversed() {
        wallEndWithOpeningElement.appendChild( openingElement2 );
        wallEndWithOpeningElement.appendChild( openingElement );

        checkTwoChildOpenings();
    }

    void checkTwoChildOpenings() {
        Wall wall = new Wall(wallEndWithOpeningElement);

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister venueInstance = list.get( 0 );
        assert MinderDom.class.isInstance( venueInstance );
        assert Venue.class.isInstance( venueInstance );

        ElementalLister wallInstance = list.get( 1 );
        assert MinderDom.class.isInstance( wallInstance );
        assert Wall.class.isInstance( wallInstance );

        assertEquals( list.size(), 2 );
        assertSame( wallInstance, wall );

        assertEquals( wall.openingList().size(), 2 );
        Iterator<Opening> iterator = wall.openingList().iterator();
        Opening one = iterator.next();
        assertNotNull( one );
        Opening two = iterator.next();
        assertNotNull( two );
        assertNotSame( one, two );
        assert one.start() < two.start();
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

    @Test
    public void domChildOpeningSideWall() throws Exception {
        wallElement = new IIOMetadataNode( "wall" );
        wallElement.setAttribute( "x1", "20" );
        wallElement.setAttribute("y1", "30");
        wallElement.setAttribute("x2", "20");
        wallElement.setAttribute("y2", "110");
        wallElement.appendChild(openingElement);

        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );
        wall.verify();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "30.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        Double y2Value = Double.parseDouble( wallElement.getAttribute( "y2" ) );
        assertEquals( y2Value, 45.4, epsilon );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        Double y1Value = Double.parseDouble( wallElement.getAttribute( "y1" ) );
        assertEquals( y1Value, 62.4, epsilon );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "110.0" );

        assertEquals( list.getLength(), 2 );
    }

    @Test
    public void domDiagonalWallOpening() throws Exception {
        wallElement = new IIOMetadataNode( Wall.Tag() );
        wallElement.setAttribute( "x1", "10" );
        wallElement.setAttribute( "y1", "20" );
        wallElement.setAttribute( "x2", "310" );
        wallElement.setAttribute( "y2", "420" );

        openingElement = new IIOMetadataNode( Opening.Tag );
        openingElement.setAttribute( "start", "5.0" );
        openingElement.setAttribute( "width", "5.0" );
        openingElement.setAttribute( "height", "72.0" );

        wallElement.appendChild(openingElement);

        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );
        wall.verify();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "10.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "20.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "13.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "24.0" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "16.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "28.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "310.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "420.0" );

        assertEquals( list.getLength(), 2 );
    }

    @Test
    public void domChildOpeningSideWallTwo() throws Exception {
        wallElement = new IIOMetadataNode( "wall" );
        wallElement.setAttribute( "x1", "20" );
        wallElement.setAttribute("y1", "30");
        wallElement.setAttribute("x2", "20");
        wallElement.setAttribute("y2", "110");
        wallElement.appendChild(openingElement);
        wallElement.appendChild(openingElement2);

        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );
        wall.verify();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "30.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        Double y2Value = Double.parseDouble( wallElement.getAttribute( "y2" ) );
        assertEquals( y2Value, 45.4, epsilon );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        Double y1Value = Double.parseDouble( wallElement.getAttribute( "y1" ) );
        assertEquals( y1Value, 62.4, epsilon );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        Double y2Value2 = Double.parseDouble( wallElement.getAttribute( "y2" ) );
        assertEquals( y2Value2, 85.4, epsilon );

        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "20.0" );
        Double y1Value2 = Double.parseDouble( wallElement.getAttribute( "y1" ) );
        assertEquals( y1Value2, 102.4, epsilon );
        assertEquals( wallElement.getAttribute( "x2" ), "20.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "110.0" );

        assertEquals( list.getLength(), 3 );
    }

    @Test
    public void domDiagonalTwoWallOpenings() throws Exception {
        wallElement = new IIOMetadataNode( Wall.Tag() );
        wallElement.setAttribute( "x1", "10" );
        wallElement.setAttribute( "y1", "20" );
        wallElement.setAttribute( "x2", "310" );
        wallElement.setAttribute( "y2", "420" );

        openingElement = new IIOMetadataNode( Opening.Tag );
        openingElement.setAttribute( "start", "5.0" );
        openingElement.setAttribute( "width", "5.0" );
        openingElement.setAttribute( "height", "72.0" );

        openingElement2 = new IIOMetadataNode( Opening.Tag );
        openingElement2.setAttribute( "start", "50.0" );
        openingElement2.setAttribute( "width", "5.0" );
        openingElement2.setAttribute( "height", "72.0" );

        wallElement.appendChild(openingElement);

        wallElement.appendChild(openingElement2);

        Draw draw = new Draw();
        draw.establishRoot();
        Wall wall = new Wall( wallElement );
        wall.verify();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );

        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "10.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "20.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "13.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "24.0" );

        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "16.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "28.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "40.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "60.0" );

        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        wallElement = (Element) node;
        assertEquals( wallElement.getAttribute( "x1" ), "43.0" );
        assertEquals( wallElement.getAttribute( "y1" ), "64.0" );
        assertEquals( wallElement.getAttribute( "x2" ), "310.0" );
        assertEquals( wallElement.getAttribute( "y2" ), "420.0" );

        assertEquals( list.getLength(), 3 );
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
        TestResets.ElementalListerReset();

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


        wallEndWithOpeningElement = new IIOMetadataNode( Wall.Tag() );
        wallEndWithOpeningElement.setAttribute( "x1", wwoX1.toString() );
        wallEndWithOpeningElement.setAttribute( "y1", wwoY.toString() );
        wallEndWithOpeningElement.setAttribute( "x2", wwoX2.toString() );
        wallEndWithOpeningElement.setAttribute( "y2", wwoY.toString() );

        openingElement = new IIOMetadataNode( Opening.Tag );
        openingElement.setAttribute( "width", openWidth.toString() );
        openingElement.setAttribute( "height", openHeight.toString() );
        openingElement.setAttribute( "start", openStart.toString() );

        openingElement2 = new IIOMetadataNode( Opening.Tag );
        openingElement2.setAttribute( "width", open2Width.toString() );
        openingElement2.setAttribute( "height", open2Height.toString() );
        openingElement2.setAttribute( "start", open2Start.toString() );

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
