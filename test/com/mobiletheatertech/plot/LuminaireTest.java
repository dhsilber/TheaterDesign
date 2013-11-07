package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Test {@code Luminaire}.
 *
 * @author dhs
 * @since 0.0.7
 */
public class LuminaireTest {

    Element venueElement;
    Element element = null;
    final String type = "6x9";
    final String pipeName = "luminaireTestPipe";
    final String target = "frank";
    final String dimmer = "dimmer";
    final String circuit = "circuit";
    final String channel = "channel";
    final String color = "color";
    final String unit = "unit";

    public LuminaireTest() {

    }

    @Test
    public void failure() throws Exception {
        new Luminaire( element );
    }

    @Test
    public void isMinder() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        assert Minder.class.isInstance( luminaire );
    }

    @Test
    public void storesAttributes() throws Exception {
        element.removeAttribute( "dimmer" );
        element.removeAttribute( "circuit" );
        element.removeAttribute( "channel" );
        element.removeAttribute( "color" );
        element.removeAttribute( "unit" );

        Luminaire luminaire = new Luminaire( element );

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessInteger( luminaire, "location" ), (Integer) 12 );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "unit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "target" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "target", target );

        Luminaire luminaire = new Luminaire( element );

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessInteger( luminaire, "location" ), (Integer) 12 );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), circuit );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), dimmer );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), channel );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), color );
        assertEquals( TestHelpers.accessString( luminaire, "unit" ), unit );
        assertEquals( TestHelpers.accessString( luminaire, "target" ), target );
    }

    // Until such time as I properly implement this class' use of id.
    @Test
    public void idUnused() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        assertNull( TestHelpers.accessString( luminaire, "id" ) );
    }

    @Test
    public void storesSelf() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        ArrayList<Minder> thing = Drawable.List();

        assert thing.contains( luminaire );
    }

    @Test
    public void registersLayer() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        HashMap<String, String> layers = Layer.List();

        assertTrue( layers.containsKey( Luminaire.LAYERNAME ) );
        assertEquals( layers.get( Luminaire.LAYERNAME ), Luminaire.LAYERTAG );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Luminaire( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'type' attribute.")
    public void noType() throws Exception {
        element.removeAttribute( "type" );
        new Luminaire( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'on' attribute.")
    public void noOn() throws Exception {
        element.removeAttribute( "on" );
        new Luminaire( element );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'location' attribute.")
    public void noLocation() throws Exception {
        element.removeAttribute( "location" );
        new Luminaire( element );
    }

    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                  "' has unknown mounting: 'bloorglew'.")
    public void badLocation() throws Exception {
        element.setAttribute( "on", "bloorglew" );
        Luminaire luminaire = new Luminaire( element );
        luminaire.location();
    }

    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp = "Luminaire of type 'floob' has unknown mounting: 'bloorglew'.")
    public void badLocationOtherType() throws Exception {
        element.setAttribute( "type", "floob" );
        element.setAttribute( "on", "bloorglew" );
//        new Luminaire( element );
        Luminaire luminaire = new Luminaire( element );
        luminaire.location();
    }

    @Test
    public void locate() throws Exception {
        Luminaire luminaire = new Luminaire( element );
        Point actual = luminaire.location();
        Point expected = new Point( 24, 34, 56 );
        assertEquals( actual, expected );
    }

    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                  "' has location -1 which is beyond the end of Pipe '" + pipeName + "'.")
    public void locateOffPipe() throws Exception {
        element.setAttribute( "location", "-1" );
        Luminaire luminaire = new Luminaire( element );
        luminaire.location();
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire( element );

        luminaire.dom( draw, View.PLAN );

//        NodeList list = draw.root().getElementsByTagName( "use" );
        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Luminaire.LAYERTAG );

        NodeList list = groupElement.getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );

        list = groupElement.getElementsByTagName( "path" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        assertEquals( element.getAttribute( "x" ), "15" );
        assertEquals( element.getAttribute( "y" ), "9" );
        assertEquals( element.getAttribute( "width" ), "18" );
        assertEquals( element.getAttribute( "height" ), "12" );
        assertEquals( element.getAttribute( "fill" ), "none" );
        assertEquals( element.getAttribute( "stroke" ), "black" );
        assertEquals( element.getAttribute( "stroke-width" ), "1" );
        assertEquals( element.getAttribute( "d" ),
                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );

        list = groupElement.getElementsByTagName( "rect" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = groupElement.getElementsByTagName( "circle" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = groupElement.getElementsByTagName( "text" );
        assertEquals( list.getLength(), 5 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        String text = element.getTextContent();
        assertEquals( text, circuit );
        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, dimmer );
        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, channel );
        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, unit );
        node = list.item( 4 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, color );
    }

    @Test
    public void domPlanCircuitingOne() throws Exception {
        venueElement.setAttribute( "circuiting", "one-to-one" );
        new Venue( venueElement );
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire( element );

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );

        list = draw.root().getElementsByTagName( "path" );
        assertEquals( list.getLength(), 0 );

        list = draw.root().getElementsByTagName( "rect" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = draw.root().getElementsByTagName( "circle" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = draw.root().getElementsByTagName( "text" );
        assertEquals( list.getLength(), 4 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        String text = element.getTextContent();
        assertEquals( text, dimmer );
        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, channel );
        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, unit );
        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, color );
    }

    @Test
    public void domPlanCircuitingMany() throws Exception {
        venueElement.setAttribute( "circuiting", "one-to-many" );
        new Venue( venueElement );
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire( element );

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );

        list = draw.root().getElementsByTagName( "path" );
        assertEquals( list.getLength(), 0 );

        list = draw.root().getElementsByTagName( "rect" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = draw.root().getElementsByTagName( "circle" );
        assertEquals( list.getLength(), 1 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;

        list = draw.root().getElementsByTagName( "text" );
        assertEquals( list.getLength(), 4 );
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        String text = element.getTextContent();
        assertEquals( text, circuit );
        node = list.item( 1 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, channel );
        node = list.item( 2 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, unit );
        node = list.item( 3 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        element = (Element) node;
        text = element.getTextContent();
        assertEquals( text, color );
    }


    @Test
    public void domPlanWithTarget() throws Exception {
        Element zoneElement = new IIOMetadataNode( "zone" );
        zoneElement.setAttribute( "id", target );
        zoneElement.setAttribute( "x", "34" );
        zoneElement.setAttribute( "y", "44" );
        zoneElement.setAttribute( "r", "2" );
        new Zone( zoneElement );

        element.setAttribute( "target", target );
        Luminaire luminaire = new Luminaire( element );

        Draw draw = new Draw();
        draw.getRoot();
        Minder.VerifyAll();

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );
        assertEquals( element.getAttribute( "transform" ), "rotate(-45,24,34)" );
    }

    @Test
    public void domPlanWithMissingTarget() throws Exception {
        Element zoneElement = new IIOMetadataNode( "zone" );
        zoneElement.setAttribute( "id", "bogus" );
        zoneElement.setAttribute( "x", "34" );
        zoneElement.setAttribute( "y", "44" );
        zoneElement.setAttribute( "r", "2" );
        new Zone( zoneElement );

        element.setAttribute( "target", target );
        Luminaire luminaire = new Luminaire( element );

        Draw draw = new Draw();
        draw.getRoot();
        Minder.VerifyAll();

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );
        assertEquals( element.getAttribute( "transform" ), "rotate(0,24,34)" );
    }

    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire( element );

        luminaire.dom( draw, View.SECTION );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "34" );
        assertEquals( element.getAttribute( "y" ), "184" );

//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        assertEquals( element.getAttribute( "x" ), "15" );
//        assertEquals( element.getAttribute( "y" ), "9" );
//        assertEquals( element.getAttribute( "width" ), "18" );
//        assertEquals( element.getAttribute( "height" ), "12" );
//        assertEquals( element.getAttribute( "fill" ), "none" );
//        assertEquals( element.getAttribute( "stroke" ), "black" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );
//        assertEquals( element.getAttribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        assertEquals( element.getAttribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
    }

    @Test
    public void domFront() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire( element );

        luminaire.dom( draw, View.FRONT );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "184" );

//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        assertEquals( element.getAttribute( "x" ), "15" );
//        assertEquals( element.getAttribute( "y" ), "9" );
//        assertEquals( element.getAttribute( "width" ), "18" );
//        assertEquals( element.getAttribute( "height" ), "12" );
//        assertEquals( element.getAttribute( "fill" ), "none" );
//        assertEquals( element.getAttribute( "stroke" ), "black" );
//        assertEquals( element.getAttribute( "stroke-width" ), "1" );
//        assertEquals( element.getAttribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        assertEquals( element.getAttribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();
        TestResets.MinderReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "name", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "id", pipeName );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "x", "12" );
        pipeElement.setAttribute( "y", "34" );
        pipeElement.setAttribute( "z", "56" );
        new Pipe( pipeElement );

        element = new IIOMetadataNode( "luminaire" );
        element.setAttribute( "type", type );
        element.setAttribute( "on", pipeName );
        element.setAttribute( "location", "12" );
        element.setAttribute( "dimmer", dimmer );
        element.setAttribute( "circuit", circuit );
        element.setAttribute( "channel", channel );
        element.setAttribute( "color", color );
        element.setAttribute( "unit", unit );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
