package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Test {@code Luminaire}.
 *
 * @author dhs
 * @since 0.0.7
 */
public class LuminaireTest {

    Element element = null;
    final String type = "6x9";
    final String pipeName = "luminaireTestPipe";

    public LuminaireTest() {

    }

    @Test
    public void isMinder() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        assert Minder.class.isInstance( luminaire );
    }

    @Test
    public void storesAttributes() throws Exception {
        Luminaire luminaire = new Luminaire( element );

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessInteger( luminaire, "location" ), (Integer) 12 );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "unit" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "circuit", "A12" );
        element.setAttribute( "dimmer", "19b" );
        element.setAttribute( "channel", "97" );
        element.setAttribute( "color", "R342" );
        element.setAttribute( "unit", "9" );

        Luminaire luminaire = new Luminaire( element );

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessInteger( luminaire, "location" ), (Integer) 12 );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), "A12" );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), "19b" );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), "97" );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), "R342" );
        assertEquals( TestHelpers.accessString( luminaire, "unit" ), "9" );
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

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFine() throws Exception {
        new Luminaire( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'type' attribute." )
    public void noType() throws Exception {
        element.removeAttribute( "type" );
        new Luminaire( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'on' attribute." )
    public void noOn() throws Exception {
        element.removeAttribute( "on" );
        new Luminaire( element );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
           expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'location' attribute." )
    public void noLocation() throws Exception {
        element.removeAttribute( "location" );
        new Luminaire( element );
    }


    @Test( expectedExceptions = MountingException.class,
           expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                   "' has unknown mounting: 'bloorglew'." )
    public void badLocation() throws Exception {
        element.setAttribute( "on", "bloorglew" );
        Luminaire luminaire = new Luminaire( element );
        luminaire.location();
    }

    @Test( expectedExceptions = MountingException.class,
           expectedExceptionsMessageRegExp = "Luminaire of type 'floob' has unknown mounting: 'bloorglew'." )
    public void badLocationOtherType() throws Exception {
        element.setAttribute( "type", "floob" );
        element.setAttribute( "on", "bloorglew" );
        new Luminaire( element );
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

    @Test( expectedExceptions = MountingException.class,
           expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                   "' has location -1 which is beyond the end of Pipe '" + pipeName + "'." )
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

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element element = (Element) node;
        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( element.getAttribute( "x" ), "24" );
        assertEquals( element.getAttribute( "y" ), "34" );

        list = draw.root().getElementsByTagName( "path" );
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

//        assertEquals( element.getAttribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
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
        Element venueElement = new IIOMetadataNode( "venue" );
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
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
