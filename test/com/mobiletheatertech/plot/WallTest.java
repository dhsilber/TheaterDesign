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

/**
 * Test {@code Wall}.
 *
 * @author dhs
 * @since 0.0.11
 */
public class WallTest {

    Element element = null;
    Integer x1 = 12;
    Integer y1 = 34;
    Integer x2 = 56;
    Integer y2 = 78;

    @Test
    public void isMinder() throws Exception {
        Wall wall = new Wall( element );

        assert Minder.class.isInstance( wall );
    }

    @Test
    public void storesAttributes() throws Exception {
        Wall wall = new Wall( element );

        assertEquals( TestHelpers.accessInteger( wall, "x1" ), x1 );
        assertEquals( TestHelpers.accessInteger( wall, "y1" ), y1 );
        assertEquals( TestHelpers.accessInteger( wall, "x2" ), x2 );
        assertEquals( TestHelpers.accessInteger( wall, "y2" ), y2 );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Wall instance is missing required 'x1' attribute." )
    public void noX1() throws Exception {
        element.removeAttribute( "x1" );
        new Wall( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Wall instance is missing required 'y1' attribute." )
    public void noY1() throws Exception {
        element.removeAttribute( "y1" );
        new Wall( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Wall instance is missing required 'x2' attribute." )
    public void noX2() throws Exception {
        element.removeAttribute( "x2" );
        new Wall( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Wall instance is missing required 'y2' attribute." )
    public void noY2() throws Exception {
        element.removeAttribute( "y2" );
        new Wall( element );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        Wall wall = new Wall( element );

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        wall.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "x1" ), x1.toString() );
        assertEquals( element.getAttribute( "y1" ), y1.toString() );
        assertEquals( element.getAttribute( "x2" ), x2.toString() );
        assertEquals( element.getAttribute( "y2" ), y2.toString() );

        assertEquals( element.getAttribute( "stroke" ), "black" );
        assertEquals( element.getAttribute( "stroke-width" ), "2" );

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "id", "luminaireTestPipe" );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "x", "12" );
        pipeElement.setAttribute( "y", "34" );
        pipeElement.setAttribute( "z", "56" );
        new Pipe( pipeElement );

        element = new IIOMetadataNode( "wall" );
        element.setAttribute( "x1", x1.toString() );
        element.setAttribute( "y1", y1.toString() );
        element.setAttribute( "x2", x2.toString() );
        element.setAttribute( "y2", y2.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
