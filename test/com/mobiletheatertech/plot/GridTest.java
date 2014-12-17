package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/23/13 Time: 1:10 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGElement;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * Test {@code Grid}.
 *
 * @author dhs
 * @since 0.0.10
 */
public class GridTest {

    Element element = null;

    Double startx = 17.0;
    Double starty = -65.0;
    Double startz = 7.0;

    @Test
    public void isA() throws Exception {
        Grid grid = new Grid( element );

        assert MinderDom.class.isInstance(grid);
    }

    @Test
    public void constantCategory() {
        assertEquals( Grid.CATEGORY, "grid" );
    }

    @Test
    public void storesAttributes() throws Exception {
        Grid grid = new Grid( element );

        assertEquals( TestHelpers.accessDouble(grid, "startx"), 0.0 );
        assertEquals( TestHelpers.accessDouble( grid, "starty" ), 0.0 );
        assertEquals( TestHelpers.accessDouble( grid, "startz" ), 0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "startx", startx.toString() );
        element.setAttribute( "starty", starty.toString() );
        element.setAttribute( "startz", startz.toString() );

        Grid grid = new Grid( element );

        assertEquals( TestHelpers.accessDouble( grid, "startx" ), startx );
        assertEquals( TestHelpers.accessDouble( grid, "starty" ), starty );
        assertEquals( TestHelpers.accessDouble( grid, "startz" ), startz );
    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Grid.CATEGORY ) );
//
//        new Grid( element );
//
//        assertNotNull( Category.Select( Grid.CATEGORY ) );
//    }

    @Test
    public void dom() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        Grid grid = new Grid( element );

        grid.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
//        assertEquals( list.getLength(), 17 );

        Double x1 = 1 + SvgElement.OffsetX();
        Double y1 = SvgElement.OffsetY();
        Double x2 = 1 + SvgElement.OffsetX();
        Double y2 = 401 + SvgElement.OffsetY();
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), x1.toString() );
        assertEquals( element.getAttribute( "y1" ), y1.toString() );
        assertEquals( element.getAttribute( "x2" ), x2.toString() );
        assertEquals( element.getAttribute( "y2" ), y2.toString() );
        assertEquals( element.getAttribute( "stroke" ), "blue" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.2" );

        x1 = 337 + SvgElement.OffsetX();
        y1 = SvgElement.OffsetY();
        x2 = 337 + SvgElement.OffsetX();
        y2 = 401 + SvgElement.OffsetY();
        node = list.item( 7 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), x1.toString() );
        assertEquals( element.getAttribute( "y1" ), y1.toString() );
        assertEquals( element.getAttribute( "x2" ), x2.toString() );
        assertEquals( element.getAttribute( "y2" ), y2.toString() );
        assertEquals( element.getAttribute( "stroke" ), "blue" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.1" );

        x1 = SvgElement.OffsetX();
        y1 = 1 + SvgElement.OffsetY();
        x2 = 351 + SvgElement.OffsetX();
        y2 = 1 + SvgElement.OffsetY();
        node = list.item( 8 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), x1.toString() );
        assertEquals( element.getAttribute( "y1" ), y1.toString() );
        assertEquals( element.getAttribute( "x2" ), x2.toString() );
        assertEquals( element.getAttribute( "y2" ), y2.toString() );
        assertEquals( element.getAttribute( "stroke" ), "blue" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.2" );

        x1 = SvgElement.OffsetX();
        y1 = 385 + SvgElement.OffsetY();
        x2 = 351 + SvgElement.OffsetX();
        y2 = 385 + SvgElement.OffsetY();
        node = list.item( 16 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), x1.toString() );
        assertEquals( element.getAttribute( "y1" ), y1.toString() );
        assertEquals( element.getAttribute( "x2" ), x2.toString() );
        assertEquals( element.getAttribute( "y2" ), y2.toString() );
        assertEquals( element.getAttribute( "stroke" ), "blue" );
        assertEquals( element.getAttribute( "stroke-opacity" ), "0.1" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "grid" );

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "351" );
        venueElement.setAttribute( "depth", "401" );
        venueElement.setAttribute( "height", "241" );
        new Venue( venueElement );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
