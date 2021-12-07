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
    Element elementOnLightingStand = null;
    Element definitionElement = null;

    final Integer unit = 1;
    final String owner = "owner";
    final String type = "Altman 6x9";
    final String pipeName = "luminaireTestPipe";
    final String lightingStandName = "luminaireTestLightingStand";
    final String trussId = "luminaireTestTruss";
    final String target = "frank";
    final String dimmer = "dimmer";
    final String circuit = "circuit";
    final String channel = "channel";
    final String color = "color";
    final String address = "address";
    final String info = "info";
    Double weight = 21.7;
    Integer hangPoint1X=20;
    Integer hangPoint1Y=40;
    Integer hangPoint2X=30;
    Integer trussSize=12;
    Integer trussLength=120;
    Double pipeLocation = 12.0;
    String trussLocation = "a 12";
    String lightingStandLocation = "b";
    String callShowData = "showData(evt)";
    String callHideData = "hideData(evt)";
    final static String letterOnlyLocation = "a";

    Pipe pipe = null;
    LightingStand lightingStand = null;
    LuminaireDefinition luminaireDefinition = null;

    final String id = pipeName + ":" + unit;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Venue.Reset();
        Proscenium.Reset();
        TestResets.ElementalListerReset();
        TestResets.MountableReset();
        TestResets.MinderDomReset();
        TestResets.YokeableReset();
        TestResets.LuminaireReset();
        TestResets.MinderDomReset();
//        Schematic.CountX = 0;
//        Schematic.CountY = 1;
//        TestResets.SchematicReset();
        UniqueId.Reset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        Venue venue = new Venue( venueElement );
        venue.verify();

        Element pipeElement = new IIOMetadataNode( "pipe" );
        pipeElement.setAttribute( "id", pipeName );
        pipeElement.setAttribute( "length", "120" );
        pipeElement.setAttribute( "x", "12" );
        pipeElement.setAttribute( "y", "34" );
        pipeElement.setAttribute( "z", "56" );
        pipe = new Pipe( pipeElement );
        pipe.verify();

        Element lightingStandElement = new IIOMetadataNode( "lighting-stand" );
        lightingStandElement.setAttribute("id", lightingStandName );
        lightingStandElement.setAttribute("x", "12");
        lightingStandElement.setAttribute("y", "34");
        lightingStand = new LightingStand( lightingStandElement );
        lightingStand.verify();

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
        new Suspend( suspendElement1 );

        Element suspendElement2 = new IIOMetadataNode( "suspend" );
        suspendElement2.setAttribute( "ref", "joan" );
        suspendElement2.setAttribute( "distance", "2" );
        new Suspend( suspendElement2 );

        Element trussElement = new IIOMetadataNode("truss");
        trussElement.setAttribute("id", trussId );
        trussElement.setAttribute("size", trussSize.toString());
        trussElement.setAttribute("length", trussLength.toString());
        trussElement.appendChild( suspendElement1 );
        trussElement.appendChild( suspendElement2 );
        Truss truss = new Truss( trussElement );
        truss.verify();


        Integer width = 13;
        Integer length = 27;
        definitionElement = new IIOMetadataNode( "luminaire-definition" );
        definitionElement.setAttribute( "name", type );
        definitionElement.setAttribute( "width", width.toString() );
        definitionElement.setAttribute( "length", length.toString() );
        definitionElement.setAttribute( "weight", weight.toString() );
        definitionElement.appendChild(new IIOMetadataNode("svg"));
        luminaireDefinition = new LuminaireDefinition( definitionElement );


        elementOnPipe = new IIOMetadataNode( Luminaire.Tag );
        elementOnPipe.setAttribute("owner", owner);
        elementOnPipe.setAttribute( "type", type );
        elementOnPipe.setAttribute("on", pipeName);
        elementOnPipe.setAttribute("location", pipeLocation.toString() );
        elementOnPipe.setAttribute("circuit", circuit);
        elementOnPipe.setAttribute("dimmer", dimmer);
        elementOnPipe.setAttribute("channel", channel);
        elementOnPipe.setAttribute("color", color);
        elementOnPipe.setAttribute("address", address);
        elementOnPipe.setAttribute("info", info);

        elementOnTruss = new IIOMetadataNode( Luminaire.Tag );
        elementOnTruss.setAttribute( "type", type );
        elementOnTruss.setAttribute("on", trussId);
        elementOnTruss.setAttribute("location", trussLocation);
        elementOnTruss.setAttribute("dimmer", dimmer);
        elementOnTruss.setAttribute("circuit", circuit);
        elementOnTruss.setAttribute("channel", channel);
        elementOnTruss.setAttribute("color", color);
        elementOnTruss.setAttribute("owner", owner);

        elementOnLightingStand = new IIOMetadataNode( Luminaire.Tag );
        elementOnLightingStand.setAttribute( "type", type );
        elementOnLightingStand.setAttribute("on", lightingStandName );
        elementOnLightingStand.setAttribute("location", lightingStandLocation );
        elementOnLightingStand.setAttribute("dimmer", dimmer);
        elementOnLightingStand.setAttribute("circuit", circuit);
        elementOnLightingStand.setAttribute("channel", channel);
        elementOnLightingStand.setAttribute("color", color);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void isA() throws Exception {
        Luminaire instance = new Luminaire(elementOnPipe);

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertFalse( UniqueId.class.isInstance( instance ) );

        assert IsClamp.class.isInstance( instance);
//        assert Schematicable.class.isInstance( instance );
        assertFalse( Legendable.class.isInstance( instance ) );
    }

    @Test
    public void storesAttributes() throws Exception {
        // These are optional, so their absence should not cause a problem:
        elementOnPipe.removeAttribute("dimmer");
        elementOnPipe.removeAttribute("circuit");
        elementOnPipe.removeAttribute("channel");
        elementOnPipe.removeAttribute("color");

        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        Location loc = (Location) TestHelpers.accessObject( luminaire, "location" );
        assertEquals( loc.toString(), pipeLocation.toString() );
        assertEquals( TestHelpers.accessString( luminaire, "owner" ), owner );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), "" );
        assertEquals( TestHelpers.accessString( luminaire, "target" ), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        elementOnPipe.setAttribute("target", target);

        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( TestHelpers.accessString( luminaire, "type" ), type );
        assertEquals( TestHelpers.accessString( luminaire, "on" ), pipeName );
        Location loc = (Location) TestHelpers.accessObject( luminaire, "location" );
        assertEquals( loc.toString(), pipeLocation.toString() );
        assertEquals( TestHelpers.accessString( luminaire, "owner" ), owner );
        assertEquals( TestHelpers.accessString( luminaire, "circuit" ), circuit );
        assertEquals( TestHelpers.accessString( luminaire, "dimmer" ), dimmer );
        assertEquals( TestHelpers.accessString( luminaire, "channel" ), channel );
        assertEquals( TestHelpers.accessString( luminaire, "color" ), color );
        assertEquals( TestHelpers.accessString( luminaire, "target" ), target );
    }

    @Test
    public void unitBuildsID() throws Exception {
        Luminaire instance = new Luminaire(elementOnPipe);
        instance.unit( unit );

        assertEquals( instance.id, pipeName + ":" + unit );
    }

        // TODO: commented out 2014-07-15 as it was hanging the whole test run.
    // Until such time as I properly implement this class' use of id.
//    @Test
//    public void idUnused() throws Exception {
//        Luminaire luminaire = new Luminaire(baseElement);
//
//        assertNull(TestHelpers.accessString(luminaire, "id"));
//    }

    @Test
    public void storesSelf() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( luminaire );
    }

    @Test
    public void registersLayer() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        HashMap<String, Layer> layers = Layer.List();

        assertTrue( layers.containsKey( Luminaire.LAYERTAG ) );
        assertEquals( layers.get( Luminaire.LAYERTAG ).name(), Luminaire.LAYERNAME );
    }

    @Test
    public void registersOnMountable() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();

        assertTrue( pipe.contains( luminaire ) );
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
            expectedExceptionsMessageRegExp = "Luminaire instance is missing required 'owner' attribute.")
    public void noOwner() throws Exception {
        elementOnPipe.removeAttribute("owner");
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

    @Test(expectedExceptions = MountingException.class,
                expectedExceptionsMessageRegExp = "Luminaire of type '" + type +
                        "' has unknown mounting: 'bloorglew'.")
        public void badLocation() throws Exception {
            elementOnPipe.setAttribute("on", "bloorglew");
            Luminaire luminaire = new Luminaire(elementOnPipe);
            luminaire.verify();
//            luminaire.drawingLocation();
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp = "Luminaire of type 'floob' has unknown mounting: 'bloorglew'.")
    public void badLocationOtherType() throws Exception {
        elementOnPipe.setAttribute("type", "floob");
        elementOnPipe.setAttribute("on", "bloorglew");
//        new Luminaire( baseElement );
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();
//            luminaire.drawingLocation();
    }

    @Test(expectedExceptions = MountingException.class,
          expectedExceptionsMessageRegExp =
                  pipeName + " does not include invalid location -1.0." )
//                  "Luminaire of type '" + type +
//                  "' has location -1 which is beyond the end of pipe '" + pipeName + "'.")
    public void locateOffPipe() throws Exception {
        elementOnPipe.setAttribute("location", "-1");
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();
//            luminaire.drawingLocation();
    }

    @Test
    public void verifyOKPipe() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        luminaire.verify();
    }

    @Test(expectedExceptions = MountingException.class,
            expectedExceptionsMessageRegExp =
                    "Luminaire on pipe \\("+pipeName+"\\) has invalid location '"
                            + letterOnlyLocation + "'." )
    public void verifyBadPipeLocation() throws Exception {
        elementOnPipe.setAttribute( "location", letterOnlyLocation );
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();
    }

    @Test
    public void verifyRecordsLocation() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        luminaire.verify();

        assertNotNull(TestHelpers.accessPoint(luminaire, "point"));
    }

    @Test
    public void unit() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.unit( unit );

        assertEquals( luminaire.unit(), unit );
    }

    @Test
    public void type() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.type(), type );
    }

    @Test
    public void on() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.on(), pipeName );
    }

    @Test
    public void location() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        ValidatedDouble distance = luminaire.location().distance();

        assertTrue( distance.valid() );

        assertEquals( (Double)distance.value(), pipeLocation );
    }

    @Test
    public void circuit() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.circuit(), circuit );
    }

    @Test
    public void dimmer() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.dimmer(), dimmer );
    }

    @Test
    public void channel() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.channel(), channel );
    }

    @Test
    public void color() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.color(), color );
    }

    @Test
    public void target() throws Exception {
        elementOnPipe.setAttribute("target", target);
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.target(), target );
    }

    @Test
    public void address() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.address(), address );
    }

    @Test
    public void info() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);

        assertEquals( luminaire.info(), info );
    }

    @Test
    public void weight() throws Exception {
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();

        assertEquals( (Double)luminaire.weight(), weight );
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();
        pipe.numberLuminaires();

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
        Element diversionElement = (Element) node;
        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
        assertEquals( diversionElement.getAttribute( "y" ), "34.0" );
        assertEquals( diversionElement.getAttribute( "id" ), id );
        assertEquals( diversionElement.getAttribute( "onmouseover" ), callShowData );
        assertEquals( diversionElement.getAttribute( "onmouseout" ), callHideData );

//        list = groupElement.getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        assertEquals( diversionElement.getAttribute( "x" ), "15" );
//        assertEquals( diversionElement.getAttribute( "y" ), "9" );
//        assertEquals( diversionElement.getAttribute( "width" ), "18" );
//        assertEquals( diversionElement.getAttribute( "height" ), "12" );
//        assertEquals( diversionElement.getAttribute( "fill" ), "none" );
//        assertEquals( diversionElement.getAttribute( "stroke" ), "black" );
//        assertEquals( diversionElement.getAttribute( "stroke-width" ), "1" );
//        assertEquals( diversionElement.getAttribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        list = groupElement.getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;
//
//        list = groupElement.getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;

        list = groupElement.getElementsByTagName( "text" );
        assertEquals( list.getLength(), 1 );
/*
       Issue is that circuit text is not displayed.
       Root cause is that the code to modify the output based on Venue Circuiting is too complex and a bit broken.
*/
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element textElement = (Element) node;
        assertEquals( textElement.getTextContent(), unit.toString() );

        domGeneratesData( groupElement, luminaire );

//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, dimmer );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 4 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, color );
    }

    @Test
    public void domPlanRotatedPipe() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();

        Element rotatedPipeElement = new IIOMetadataNode( "pipe" );
        rotatedPipeElement.setAttribute( "id", "rotatedPipe" );
        rotatedPipeElement.setAttribute( "length", "120" );
        rotatedPipeElement.setAttribute( "x", "12" );
        rotatedPipeElement.setAttribute( "y", "34" );
        rotatedPipeElement.setAttribute( "z", "56" );
        rotatedPipeElement.setAttribute( "orientation", "90" );
        Pipe pipe = new Pipe( rotatedPipeElement );

        elementOnPipe.setAttribute( "on", "rotatedPipe" );
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.unit( 1 );
//        pipe.numberLuminaires();
        luminaire.verify();

        luminaire.dom( draw, View.PLAN );

//        NodeList list = draw.root().getElementsByTagName( "use" );
        NodeList group = draw.root().getElementsByTagName( "g" );
        assertEquals( group.getLength(), 2 );
        Node groupNode = group.item( 1 );
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Luminaire.LAYERTAG );
//        assertEquals( groupElement.getAttribute( "transform" ), "rotate(270.0,12.0,34.0)" );

        NodeList list = groupElement.getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element diversionElement = (Element) node;
        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( diversionElement.getAttribute( "x" ), "12.0" );
        assertEquals( diversionElement.getAttribute( "y" ), "46.0" );
        assertEquals( diversionElement.getAttribute( "id" ), "rotatedPipe:1" );
        assertEquals( diversionElement.getAttribute( "onmouseover" ), callShowData );
        assertEquals( diversionElement.getAttribute( "onmouseout" ), callHideData );

        list = groupElement.getElementsByTagName( "text" );
        assertEquals( list.getLength(), 1 );
/*
       Issue is that circuit text is not displayed.
       Root cause is that the code to modify the output based on Venue Circuiting is too complex and a bit broken.
*/
        node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element textElement = (Element) node;
        assertEquals( textElement.getTextContent(), unit.toString() );

        domGeneratesData( groupElement, luminaire );
    }

//    @Test
//    public void domPlanCircuitingOne() throws Exception {
//        venueElement.setAttribute( "circuiting", "one-to-one" );
//        new Venue( venueElement );
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element diversionElement = (Element) node;
//        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
//        assertEquals( diversionElement.getAttribute( "y" ), "34.0" );
//
//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 0 );
//
//        list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;
//
//        list = draw.root().getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;
//
//        list = draw.root().getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 4 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        String text = diversionElement.getTextContent();
//        assertEquals( text, dimmer );
//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, color );
//    }
//
//    @Test
//    public void domPlanCircuitingMany() throws Exception {
//        venueElement.setAttribute( "circuiting", "one-to-many" );
//        new Venue( venueElement );
//        Draw draw = new Draw();
//        draw.establishRoot();
//        Luminaire luminaire = new Luminaire(elementOnPipe);
//        luminaire.verify();
//
//        luminaire.dom( draw, View.PLAN );
//
//        NodeList list = draw.root().getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element diversionElement = (Element) node;
//        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
//        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
//        assertEquals( diversionElement.getAttribute( "y" ), "34.0" );
//
//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 0 );
//
//        list = draw.root().getElementsByTagName( "rect" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;
//
//        list = draw.root().getElementsByTagName( "circle" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
////        baseElement = (Element) node;
//
//        list = draw.root().getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 4 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        String text = diversionElement.getTextContent();
//        assertEquals( text, circuit );
//        node = list.item( 1 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, channel );
//        node = list.item( 2 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, unit );
//        node = list.item( 3 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        text = diversionElement.getTextContent();
//        assertEquals( text, color );
//    }


    // TODO: commented out 2014-07-15 as it was hanging the whole test run.
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
        draw.establishRoot();
        MinderDom.VerifyAll();

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element diversionElement = (Element) node;
        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
        assertEquals( diversionElement.getAttribute( "y" ), "34.0" );
        assertEquals( diversionElement.getAttribute( "transform" ), "rotate(-45,24.0,34.0)" );
    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
    @Test
    public void domPlanWithMissingTarget() throws Exception {
        Element zoneElement = new IIOMetadataNode( "zone" );
        zoneElement.setAttribute( "id", "bogus" );
        zoneElement.setAttribute( "x", "34" );
        zoneElement.setAttribute( "y", "44" );
        zoneElement.setAttribute( "r", "2" );
        new Zone( zoneElement );

        elementOnPipe.setAttribute("target", target);
        Luminaire luminaire = new Luminaire(elementOnPipe);

        Draw draw = new Draw();
        draw.establishRoot();
        MinderDom.VerifyAll();

        luminaire.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element diversionElement = (Element) node;
        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
        assertEquals( diversionElement.getAttribute( "y" ), "34.0" );
        assertEquals( diversionElement.getAttribute( "transform" ), "rotate(0,24.0,34.0)" );
    }

    // TODO: commented out 2014-07-15 as it was hanging the whole test run.
    @Test
    public void domSection() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();

        luminaire.dom(draw, View.SECTION);

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals(list.getLength(), 1);
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element diversionElement = (Element) node;
        assertEquals(diversionElement.getAttribute("xlink:href"), "#" + type);
        assertEquals(diversionElement.getAttribute("x"), "34.0");
        assertEquals( diversionElement.getAttribute( "y" ), "184.0" );

//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        baseElement = (Element) node;
//        assertEquals( baseElement.attribute( "x" ), "15" );
//        assertEquals( baseElement.attribute( "y" ), "9" );
//        assertEquals( baseElement.attribute( "width" ), "18" );
//        assertEquals( baseElement.attribute( "height" ), "12" );
//        assertEquals( baseElement.attribute( "fill" ), "none" );
//        assertEquals( baseElement.attribute( "stroke" ), "black" );
//        assertEquals( baseElement.attribute( "stroke-width" ), "1" );
//        assertEquals( baseElement.attribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        assertEquals( baseElement.attribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
    }

//    @Test
//    public void domSchematicTwiceSetsPostion() throws Exception {
//        lightingStand.preview( View.SCHEMATIC );
//        Luminaire instance1 = new Luminaire(elementOnLightingStand);
//        elementOnLightingStand.setAttribute( "location", "c" );
//        elementOnLightingStand.setAttribute( "unit", "other unit" );
//        Luminaire instance2 = new Luminaire(elementOnLightingStand);
//        instance1.verify();
//        instance2.verify();
//        instance1.preview(View.SCHEMATIC);
//        instance2.preview(View.SCHEMATIC);
//        Draw draw = new Draw();
//        draw.establishRoot();
//        lightingStand.dom(draw, View.SCHEMATIC);
//
//        instance1.dom(draw, View.SCHEMATIC);
//        instance2.dom(draw, View.SCHEMATIC);
////
////        assertEquals( instance1.schematicPosition(),
////                new PagePoint( Schematic.FirstX - LightingStand.Space * 0.5, Schematic.FirstY ));
////        assertEquals( instance2.schematicPosition(),
////                new PagePoint( Schematic.FirstX + LightingStand.Space * 0.5, Schematic.FirstY ));
//        fail();
//    }

//    @Test
//    public void domSchematic() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//
//        lightingStand.preview(View.SCHEMATIC);
//        Luminaire luminaire = new Luminaire(elementOnLightingStand);
//        luminaire.verify();
//        luminaire.preview(View.SCHEMATIC);
//        lightingStand.dom(draw, View.SCHEMATIC);
//
//        luminaire.dom( draw, View.SCHEMATIC );
//
////        NodeList list = draw.root().getElementsByTagName( "use" );
//        NodeList group = draw.root().getElementsByTagName( "g" );
//        assertEquals( group.getLength(), 3 ); // One of them is the LightingStand
//        Node groupNode = group.item( 2 );
//        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
//        Element groupElement = (Element) groupNode;
//        assertEquals( groupElement.getAttribute( "class" ), Luminaire.LAYERTAG );
////        assertEquals( groupElement.getAttribute( "transform" ).substring( 0, 11 ), "rotate(0.0," );
//
//        NodeList list = groupElement.getElementsByTagName( "use" );
//        assertEquals( list.getLength(), 1 );
//        Node node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        Element diversionElement = (Element) node;
//        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
////        Double x = Schematic.FirstX - LightingStand.Space / 2;
////        Double y = Schematic.FirstY;
////        assertEquals( diversionElement.getAttribute( "x" ), x.toString() );
////        assertEquals( diversionElement.getAttribute( "y" ), y.toString() );
////        fail();
//
//        assertEquals( diversionElement.getAttribute( "transform" ), "" );
//
//        list = groupElement.getElementsByTagName( "text" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        diversionElement = (Element) node;
//        String text = diversionElement.getTextContent();
//        assertEquals( text, unit );
////        x -= 1;
////        y += 2;
////        assertEquals( diversionElement.getAttribute( "x" ), x.toString() );
////        assertEquals( diversionElement.getAttribute( "y" ), y.toString() );
//    }
//
//    @Test
//    public void domSchematicStoresObstruction() throws Exception {
//        lightingStand.preview( View.SCHEMATIC );
//        Luminaire instance1 = new Luminaire(elementOnLightingStand);
//        elementOnLightingStand.setAttribute( "location", "c" );
//        elementOnLightingStand.setAttribute( "unit", "other unit" );
//        Luminaire instance2 = new Luminaire(elementOnLightingStand);
//        instance1.verify();
//        instance2.verify();
//        instance1.preview(View.SCHEMATIC);
//        instance2.preview(View.SCHEMATIC);
//        Draw draw = new Draw();
//        draw.establishRoot();
//        lightingStand.dom(draw, View.SCHEMATIC);
//        Double width = luminaireDefinition.width();
//        Double height = luminaireDefinition.length();
////        Rectangle2D.Double rectangle1 =
////                new Rectangle2D.Double(
////                        Schematic.FirstX - LightingStand.Space * 0.5 - width / 2,
////                        Schematic.FirstY - height / 2,
////                        width, height );
////        Rectangle2D.Double rectangle2 =
////                new Rectangle2D.Double(
////                        Schematic.FirstX + LightingStand.Space * 0.5 - width / 2,
////                        Schematic.FirstY - height / 2,
////                        width, height );
////fail();
//
//        instance1.dom(draw, View.SCHEMATIC);
//        instance2.dom(draw, View.SCHEMATIC);
//
////        assertEquals( instance1.schematicBox(), rectangle1 );
////        assertEquals( instance2.schematicBox(), rectangle2 );
//    }
//
//    @Test
//    public void domSchematicRegistersObstruction() throws Exception {
//        lightingStand.preview( View.SCHEMATIC );
//        Luminaire instance1 = new Luminaire(elementOnLightingStand);
//        elementOnLightingStand.setAttribute( "location", "c" );
//        elementOnLightingStand.setAttribute( "unit", "other unit" );
//        Luminaire instance2 = new Luminaire(elementOnLightingStand);
//        instance1.verify();
//        instance2.verify();
//        instance1.preview(View.SCHEMATIC);
//        instance2.preview(View.SCHEMATIC);
//        Draw draw = new Draw();
//        draw.establishRoot();
//        lightingStand.dom(draw, View.SCHEMATIC);
//
//        ArrayList<Schematicable> list = (ArrayList)
//                TestHelpers.accessStaticObject(
//                        "com.mobiletheatertech.plot.Schematic", "ObstructionList" );
//        assertEquals( list.size(), 0 );
//
//        instance1.dom(draw, View.SCHEMATIC);
//        instance2.dom(draw, View.SCHEMATIC);
//
//        assert list.contains( instance1 );
//        assert list.contains( instance2 );
//    }

    // TODO: commented out 2014-04-22 as it was hanging the whole test run.
    @Test
    public void domFront() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Luminaire luminaire = new Luminaire(elementOnPipe);
        luminaire.verify();

        luminaire.dom( draw, View.FRONT );

        NodeList list = draw.root().getElementsByTagName( "use" );
        assertEquals( list.getLength(), 1 );
        Node node = list.item( 0 );
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element diversionElement = (Element) node;
        assertEquals( diversionElement.getAttribute( "xlink:href" ), "#" + type );
        assertEquals( diversionElement.getAttribute( "x" ), "24.0" );
        assertEquals( diversionElement.getAttribute( "y" ), "184.0" );

//        list = draw.root().getElementsByTagName( "path" );
//        assertEquals( list.getLength(), 1 );
//        node = list.item( 0 );
//        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
//        baseElement = (Element) node;
//        assertEquals( baseElement.attribute( "x" ), "15" );
//        assertEquals( baseElement.attribute( "y" ), "9" );
//        assertEquals( baseElement.attribute( "width" ), "18" );
//        assertEquals( baseElement.attribute( "height" ), "12" );
//        assertEquals( baseElement.attribute( "fill" ), "none" );
//        assertEquals( baseElement.attribute( "stroke" ), "black" );
//        assertEquals( baseElement.attribute( "stroke-width" ), "1" );
//        assertEquals( baseElement.attribute( "d" ),
//                      "M 16 14 L 19 9 L 29 9 L 32 14 L 29 19 L 19 19 Z" );
//
//        assertEquals( baseElement.attribute( "d" ),
//                      "M 25 39 L 28 34 L 38 34 L 41 39 L 38 44 L 28 44 Z" );
    }

    void domGeneratesData( Element group, Luminaire luminaire ) {
        Node protoData = group.getLastChild();
        assertNotNull( protoData );
        assertEquals( protoData.getNodeType(), Node.ELEMENT_NODE );
        Element data = (Element) protoData;
        assertEquals( data.getTagName(), "plot:Luminaire" );
        TestHelpers.checkAttribute( data, "type", luminaire.type() );
        TestHelpers.checkAttribute( data, "on", luminaire.on() );
        TestHelpers.checkAttribute( data, "location", luminaire.location().toString() );
        TestHelpers.checkAttribute( data, "circuit", luminaire.circuit() );
        TestHelpers.checkAttribute( data, "dimmer", luminaire.dimmer() );
        TestHelpers.checkAttribute( data, "channel", luminaire.channel() );
        TestHelpers.checkAttribute( data, "color", luminaire.color() );
        TestHelpers.checkAttribute( data, "target", luminaire.target() );
        TestHelpers.checkAttribute( data, "address", luminaire.address() );
        TestHelpers.checkAttribute( data, "info", luminaire.info() );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

}
