package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/17/13 Time: 5:37 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Test {@code Airwall}
 *
 * @author dhs
 * @since 0.0.8
 */
public class AirwallTest {

    Element element = null;

    private Integer depth = 77;
    Integer width = 350;

    @Test
    public void isA() throws Exception {
        Airwall airwall = new Airwall( element );

        assert MinderDom.class.isInstance( airwall );
    }

    @Test
    public void storesAttributes() throws Exception {
        Airwall airwall = new Airwall( element );

        assertEquals( TestHelpers.accessInteger( airwall, "depth" ), (Integer) 77 );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp = "Airwall instance is at too small a depth")
    public void depthTooSmall() throws Exception {
        element.setAttribute( "depth", "0" );
        new Airwall( element );
    }

    @Test(expectedExceptions = LocationException.class,
          expectedExceptionsMessageRegExp = "Airwall instance is at too large a depth")
    public void depthTooLarge() throws Exception {
        element.setAttribute( "depth", "400" );
        new Airwall( element );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();

        draw.establishRoot();
        Airwall airwall = new Airwall( element );

        NodeList existingRectangles = draw.root().getElementsByTagName("line");
        assertEquals(existingRectangles.getLength(), 0);

        airwall.dom(draw, View.PLAN);

        NodeList rectangles = draw.root().getElementsByTagName("line");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element tableElement = (Element) groupNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);
        assertEquals(tableElement.getAttribute("x1"), "0" );
        assertEquals(tableElement.getAttribute("y1"), depth.toString() );
        assertEquals(tableElement.getAttribute("x2"), width.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.
        assertEquals(tableElement.getAttribute("y2"), depth.toString() );
        assertEquals(tableElement.getAttribute("stroke-width"), "2");
        assertEquals(tableElement.getAttribute("stroke"), Airwall.COLOR );
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
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", width.toString() );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        element = new IIOMetadataNode( "airwall" );
        element.setAttribute( "depth", depth.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }


}
