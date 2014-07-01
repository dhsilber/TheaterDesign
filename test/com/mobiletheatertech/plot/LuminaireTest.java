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
    Element elementOnPipe = null;
    Element elementOnTruss = null;
    final String type = "6x9";
    final String pipeName = "luminaireTestPipe";
    final String trussId = "luminaireTestTruss";
    final String target = "frank";
    final String dimmer = "dimmer";
    final String circuit = "circuit";
    final String channel = "channel";
    final String color = "color";
    final String unit = "unit";
    Integer hangPoint1X=20;
    Integer hangPoint1Y=40;
    Integer hangPoint2X=30;
    Integer trussSize=12;
    Integer trussLength=120;
    String pipeLocation = "12";
    String trussLocation = "a 12";


    public LuminaireTest() {

    }

    @Test
    public void isMinderDom() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assert MinderDom.class.isInstance( luminaire );
    }

    @Test
    public void storesAttributes() throws Exception {
        // These are optional, so their absence should not cause a problem:
        elementOnPipe.removeAttribute("dimmer");
        elementOnPipe.removeAttribute("circuit");
        elementOnPipe.removeAttribute("channel");
        elementOnPipe.removeAttribute("color");
        elementOnPipe.removeAttribute("unit");

        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessString( luminaire, "location" ), pipeLocation );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "unit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "target" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        elementOnPipe.setAttribute("target", target);

        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        assertEquals( TestHelpers.accessString( luminaire, "location" ), pipeLocation );
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
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertNull(TestHelpers.accessString(luminaire, "id"));
    }

    @Test
    public void storesSelf() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        ArrayList<MinderDom> thing = Drawable.List();

        assert thing.contains( luminaire );
    }

    @Test
    public void registersLayer() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        HashMap<String, String> layers = Layer.List();

        assertTrue( layers.containsKey( Luminaire.LAYERNAME ) );
        assertEquals( layers.get( Luminaire.LAYERNAME ), Luminaire.LAYERTAG );
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFinePipe() throws Exception {
        new Luminaire(elementOnPipe);
    }

    /*
     * This is to ensure that no exception is thrown if data is OK.
     */
    @Test
    public void justFineTruss() throws Exception {
        new Luminaire(elementOnTruss);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'type' attribute.")
    public void noType() throws Exception {
        elementOnPipe.removeAttribute("type");
        new Luminaire(elementOnPipe);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'on' attribute.")
    public void noOn() throws Exception {
        elementOnPipe.removeAttribute("on");
        new Luminaire(elementOnPipe);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
          expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'location' attribute.")
    public void noLocation() throws Exception {
        elementOnPipe.removeAttribute("location");
        new Luminaire(elementOnPipe);
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test(expectedExceptions = MountingException.class,
//          expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
//                  "' has unknown mounting: 'bloorglew'.")
//    public void badLocation() throws Exception {
//        elementOnPipe.setAttribute("on", "bloorglew");
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.location();
//    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test(expectedExceptions = MountingException.class,
//          expectedExceptionsMessageRegExp = "Luminaire of type 'floob' has unknown mounting: 'bloorglew'.")
//    public void badLocationOtherType() throws Exception {
//        elementOnPipe.setAttribute("type", "floob");
//        elementOnPipe.setAttribute("on", "bloorglew");
////        new Luminaire( elementOnPipe );
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.location();
//    }

    @Test
    public void locate() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);
        Point actual = null;//luminaire.location();
        Point expected = new Point( 24, 34, 56 );
        assertEquals(actual, expected);
    }

    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                  "' has location -1 which is beyond the end of Pipe '" + pipeName + "'.")
    public void locateOffPipe() throws Exception {
        elementOnPipe.setAttribute("location", "-1");
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.location();
    }

    @Test
    public void verifyOKPipe() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        luminaire.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Pipe \\("+pipeName+"\\) location is not a number.")
    public void verifyBadPipeLocation() throws Exception {
        elementOnPipe.setAttribute("location", "a");
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();
    }
     @Test
    public void verifyRecordsLocation() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        luminaire.verify();

        assertNotNull( TestHelpers.accessPoint( luminaire, "point"));
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void domPlan() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.PLAN );
//
////        NodeList list = draw.root().getElementsByTagName( "use" );
//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 2 );
//        Node groupNode = group.item( 1 );
//        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
//        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.getAttribute( "class" ), Luminaire.LAYERTAG );
//
//        NodeList list = groupElement.getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( element.getAttribute( "x" ), "24" );
//        assertEquals( element.getAttribute( "y" ), "34" );
//
//        list = groupElement.getElementsByTagName( "path" );
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
//        list = groupElement.getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = groupElement.getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = groupElement.getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 5 );
///*
//       Issue is that circuit text is not displayed.
//       Root cause is that the code to modify the output based on Venue Circuiting is too complex and a bit broken.
// */
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        String text = element.getTextContent();
//        assertEquals( text, circuit );
//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, dimmer );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 4 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, color );
//    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void domPlanCircuitingOne() throws Exception {
//        venueElement.setAttribute( "circuiting", "one-to-one" );
//        new Venue( venueElement );
//        Draw draw = new Draw();
//        draw.getRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( element.getAttribute( "x" ), "24" );
//        assertEquals( element.getAttribute( "y" ), "34" );
//
//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 0 );
//
//        list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = draw.root().getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = draw.root().getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 4 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        String text = element.getTextContent();
//        assertEquals( text, dimmer );
//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, color );
//    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void domPlanCircuitingMany() throws Exception {
//        venueElement.setAttribute( "circuiting", "one-to-many" );
//        new Venue( venueElement );
//        Draw draw = new Draw();
//        draw.getRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( element.getAttribute( "x" ), "24" );
//        assertEquals( element.getAttribute( "y" ), "34" );
//
//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 0 );
//
//        list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = draw.root().getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
//
//        list = draw.root().getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 4 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        String text = element.getTextContent();
//        assertEquals( text, circuit );
//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        element = (Element) node;
//        text = element.getTextContent();
//        assertEquals( text, color );
//    }


    @Test
    public void domPlanWithTarget() throws Exception {
        Element zoneElement = new IIOMetadataNode( "zone" );
        zoneElement.setAttribute( "id", target );
        zoneElement.setAttribute( "x", "34" );
        zoneElement.setAttribute( "y", "44" );
        zoneElement.setAttribute( "r", "2" );
        new Zone( zoneElement );

        elementOnPipe.setAttribute("target", target);
        Luminaire luminaire = new Luminaire(elementOnPipe);

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

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void domPlanWithMissingTarget() throws Exception {
//        Element zoneElement = new IIOMetadataNode( "zone" );
//        zoneElement.setAttribute( "id", "bogus" );
//        zoneElement.setAttribute( "x", "34" );
//        zoneElement.setAttribute( "y", "44" );
//        zoneElement.setAttribute( "r", "2" );
//        new Zone( zoneElement );
//
//        elementOnPipe.setAttribute("target", target);
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//
//        Draw draw = new Draw();
//        draw.getRoot();
//        Minder.VerifyAll();
//
//        luminaire.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( element.getAttribute( "x" ), "24" );
//        assertEquals( element.getAttribute( "y" ), "34" );
//        assertEquals( element.getAttribute( "transform" ), "rotate(0,24,34)" );
//    }

    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();
        draw.getRoot();
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();

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
//        elementOnPipe = (Element) node;
//        assertEquals( elementOnPipe.getAttribute( "x" ), "15" );
//        assertEquals( elementOnPipe.getAttribute( "y" ), "9" );
//        assertEquals( elementOnPipe.getAttribute( "width" ), "18" );
//        assertEquals( elementOnPipe.getAttribute( "height" ), "12" );
//        assertEquals( elementOnPipe.getAttribute( "fill" ), "none" );
//        assertEquals( elementOnPipe.getAttribute( "stroke" ), "black" );
//        assertEquals( elementOnPipe.getAttribute( "stroke-width" ), "1" );
//        assertEquals( elementOnPipe.getAttribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        assertEquals( elementOnPipe.getAttribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
//    @Test
//    public void domFront() throws Exception {
//        Draw draw = new Draw();
//        draw.getRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.FRONT );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element element = (Element) node;
//        assertEquals( element.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( element.getAttribute( "x" ), "24" );
//        assertEquals( element.getAttribute( "y" ), "184" );
//
////        list = draw.root().getElementsByTagName( "path" );
////        assertEquals( list.getLength(), 1 );
////        node = list.item( 0 );
////        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        elementOnPipe = (Element) node;
////        assertEquals( elementOnPipe.getAttribute( "x" ), "15" );
////        assertEquals( elementOnPipe.getAttribute( "y" ), "9" );
////        assertEquals( elementOnPipe.getAttribute( "width" ), "18" );
////        assertEquals( elementOnPipe.getAttribute( "height" ), "12" );
////        assertEquals( elementOnPipe.getAttribute( "fill" ), "none" );
////        assertEquals( elementOnPipe.getAttribute( "stroke" ), "black" );
////        assertEquals( elementOnPipe.getAttribute( "stroke-width" ), "1" );
////        assertEquals( elementOnPipe.getAttribute( "d" ),
////                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
////
////        assertEquals( elementOnPipe.getAttribute( "d" ),
////                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.VenueReset();
        TestResets.MinderDomReset();
        TestResets.MountableReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
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

        Element hangPoint1 = new IIOMetadataNode( "hangpoint" );
        hangPoint1.setAttribute( "id", "jim" );
        hangPoint1.setAttribute("x", hangPoint1X.toString());
        hangPoint1.setAttribute("y", hangPoint1Y.toString());
        new HangPoint( hangPoint1 );

        Element hangPoint2 = new IIOMetadataNode( "hangpoint" );
        hangPoint2.setAttribute( "id", "joan" );
        hangPoint2.setAttribute("x", hangPoint2X.toString());
        hangPoint2.setAttribute( "y", "200" );
        new HangPoint( hangPoint2 );

        Element suspendElement1 = new IIOMetadataNode( "suspend" );
        suspendElement1.setAttribute( "ref", "jim" );
        suspendElement1.setAttribute( "distance", "1" );

        Element suspendElement2 = new IIOMetadataNode( "suspend" );
        suspendElement2.setAttribute( "ref", "joan" );
        suspendElement2.setAttribute( "distance", "2" );

        Element trussElement = new IIOMetadataNode("truss");
        trussElement.setAttribute("id", trussId );
        trussElement.setAttribute("size", trussSize.toString());
        trussElement.setAttribute("length", trussLength.toString());
        trussElement.appendChild( suspendElement1 );
        trussElement.appendChild( suspendElement2 );

        elementOnPipe = new IIOMetadataNode( "luminaire" );
        elementOnPipe.setAttribute( "type", type );
        elementOnPipe.setAttribute("on", pipeName);
        elementOnPipe.setAttribute("location", pipeLocation );
        elementOnPipe.setAttribute("dimmer", dimmer);
        elementOnPipe.setAttribute("circuit", circuit);
        elementOnPipe.setAttribute("channel", channel);
        elementOnPipe.setAttribute("color", color);
        elementOnPipe.setAttribute("unit", unit);

        elementOnTruss = new IIOMetadataNode( "luminaire" );
        elementOnTruss.setAttribute( "type", type );
        elementOnTruss.setAttribute("on", trussId);
        elementOnTruss.setAttribute("location", trussLocation );
        elementOnTruss.setAttribute("dimmer", dimmer);
        elementOnTruss.setAttribute("circuit", circuit);
        elementOnTruss.setAttribute("channel", channel);
        elementOnTruss.setAttribute("color", color);
        elementOnTruss.setAttribute("unit", unit);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
