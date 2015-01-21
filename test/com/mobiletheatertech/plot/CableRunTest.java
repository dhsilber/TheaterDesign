package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

/**
 * @author dhs
 * @since 2014-08-29
 */
public class CableRunTest {

    Element venueElement = null;
    Element wallElement1 = null;
    Element wallElement2 = null;
    Element wallElement3 = null;
    Element wallElement4 = null;

    Element tableElement = null;
    Element tableElement2 = null;
    Element templateElement = null;
    Element sourceElement = null;
    Element sinkElement = null;
    Element sourceLuminaireElement = null;
    Element sinkLuminaireElement = null;
    private Element element = null;
    
    Element deviceToLuminaireElement = null;
    Element luminaireToLuminaireElement = null;
    Element luminaireToDeviceElement = null;

    private final String signalName = "Signal";
//    private final String bogusSignal = "Bogus Signal";
    private final String sourceName = "Source device";
    private final String bogusSource = "Bogus Source";
    private final String sinkName = "Sink device";
    private final String bogusSink = "Bogus Sink";
    private final String channel = "Optional Channel";
    private final String invalidRouting = "Place-marker routing";
    private final String validRouting = "direct";
    private final String tableName = "control";
    private final Double tableX = 4.0;
    private final Double tableY = 5.0;
    private final Double tableZ = 6.0;
    private final String table2Name = "other table";
    private final Double table2X = 14.0;
    private final Double table2Y = 15.0;
    private final Double table2Z = 16.0;
    private final String templateName = "thingy";
    private final String color = "tangimarine";

    Device sourceDevice = null;
    Device sinkDevice = null;
    LightingStand lightingStand = null;
    LuminaireDefinition luminaireDefinition = null;
    Luminaire sourceLuminaire = null;
    Luminaire sinkLuminaire = null;
    
    String standId = "stand name";
    Double standX = 4.6;
    Double standY = 2.9;
    String luminaireType = "light type";
    String sourceLuminaireLocation = "a";
    String sourceLuminaireUnit = "source light unit";
    String sourceLuminaireId = standId + ":" + sourceLuminaireUnit;
    String sinkLuminaireLocation = "d";
    String sinkLuminaireUnit = "sink light unit";
    String sinkLuminaireId = standId + ":" + sinkLuminaireUnit;

//    private Solid solid = null;

    @Test
    public void isA() throws Exception {
        CableRun instance = new CableRun( signalName, sourceName, sinkName, "", "" );

        assert Schematicable.class.isInstance( instance );
    }

    @Test
    public void storesArguments() throws Exception {
        CableRun instance = new CableRun( signalName, sourceName, sinkName, channel, validRouting );

        assertEquals( TestHelpers.accessString( instance, "signal" ), signalName );
        assertEquals( TestHelpers.accessString( instance, "source" ), sourceName );
        assertEquals( TestHelpers.accessString( instance, "sink" ), sinkName );
        assertEquals( TestHelpers.accessString( instance, "channel"), channel );
        assertEquals( TestHelpers.accessString( instance, "routing"), validRouting );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" to "+sinkName+" references unknown source '"+ bogusSource+"'.")
    public void verifyUnknownSourceDevice() throws Exception {
        element.setAttribute("source", bogusSource);
        UserCableRun run = new UserCableRun(element);
        CableRun ran = run.cableRun();
        ran.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" from "+sourceName+" references unknown sink '"+bogusSink+"'.")
    public void verifyUnknownSinkDevice() throws Exception {
        element.setAttribute("sink", bogusSink);
        UserCableRun run = new UserCableRun(element);
        CableRun ran = run.cableRun();
        ran.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" references unknown source '"+ bogusSource+"' and sink '"+bogusSink+"'.")
    public void verifyUnknownSourceSinkDevices() throws Exception {
        element.setAttribute("source", bogusSource);
        element.setAttribute("sink", bogusSink);
        UserCableRun run = new UserCableRun(element);
        CableRun ran = run.cableRun();
        ran.verify();
    }

    @Test
    public void verifySavesReference() throws Exception {
        UserCableRun run = new UserCableRun(element);
        CableRun instance = run.cableRun();

//        ArrayList<CableRun> runlist = (ArrayList)
//                TestHelpers.accessStaticObjectNotNull( "com.mobiletheatertech.plot.CableRun", "RunList" );
        ArrayList<CableRun> runlist = (ArrayList)
                TestHelpers.accessStaticObjectNotNull( CableRun.class.getName(), "RunList" );
        assertEquals( runlist.size(), 0 );

        instance.verify();

        assert runlist.contains( instance );
        assertEquals( runlist.size(), 1 );
    }

    @Test
    public void CollateCounts() throws Exception {
        UserCableRun run = new UserCableRun(element);
        CableRun ran = run.cableRun();
        ran.verify();

        CableRun.Collate();

//        assertEquals( sourceDevice.cablesIn[ Direction.Right.ordinal() ], 1 );
//        assertEquals( sinkDevice.cablesIn[ Direction.Left.ordinal() ], 1 );
////        assertEquals( sourceDevice.cablesIn[ Direction.Down.ordinal() ], (Integer) 1 );
////        assertEquals( sinkDevice.cablesIn[ Direction.UP.ordinal() ], (Integer) 1 );
    }

//    @Test
//    public void recallsNull() {
//        assertNull(CableRun.Select("bogus"));
//    }
//
//    @Test
//    public void recalls() throws Exception {
//        CableRun cableRun=new CableRun( diversionElement );
//        assertSame(CableRun.Select(name), cableRun);
//    }
//
//    @Test
//    public void layerDefined() throws Exception {
//        new Layer( layerTag, "whatever" );
//        diversionElement.setAttribute( "layer", layerTag );
//
//        CableRun cableRun=new CableRun( diversionElement );
//        cableRun.verify();
//
////TODO        this should check that the layer is activated
//        Solid solid = cableRun.getSolid();
//        assertEquals( solid.height(), height );
//    }
//
//    @Test
//    public void layerUndefined() throws Exception {
//        TestResets.LayerReset();
//        diversionElement.setAttribute( "layer", layerTag );
//
////TODO        This should throw an error because the layer is not defined
//        CableRun cableRun=new CableRun( diversionElement );
//        cableRun.verify();
//
//        Solid solid = cableRun.getSolid();
//        assertEquals( solid.height(), height );
//    }
//
//    @Test
//    public void layer() throws Exception {
//        diversionElement.setAttribute( "layer", layerTag );
//        CableRun cableRun=new CableRun( diversionElement );
//
//        assertEquals( cableRun.layer(), layerTag );
//    }

    // Redundant
//    @Test
//    public void dom() throws Exception {
//        Draw draw = new Draw();
//        draw.establishRoot();
//        UserCableRun run = new UserCableRun(element);
//        CableRun ran = run.cableRun();
//        ran.verify();
//
//        ran.dom( draw, View.PLAN );
//
//        NodeList group = draw.root().getElementsByTagName("g");
//        assertEquals(group.getLength(), 2);
//        Node groupNode = group.item(1);
//        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
//        Element groupElement = (Element) groupNode;
////        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);
//
//        NodeList list = groupElement.getElementsByTagName("line");
////        assertEquals(list.getLength(), 1);
//        Node node = list.item(0);
//        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
//        Element element = (Element) node;
//        assertEquals(element.getAttribute("x1"), tableX.toString() );
//        assertEquals(element.getAttribute("y1"), tableY.toString() );
//        assertEquals(element.getAttribute("x2"), table2X.toString() );
//        assertEquals(element.getAttribute("y2"), table2Y.toString() );
//        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
//    }

    @Test
    public void domFourWallsDirect() throws Exception {
        Element tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", "up left");
        tableElement.setAttribute("width", "1");
        tableElement.setAttribute("depth", "2");
        tableElement.setAttribute("height", "36");
        tableElement.setAttribute("x", "120" );
        tableElement.setAttribute("y", "20" );
        tableElement.setAttribute("z", "0" );
        new Table( tableElement );

        Element tableElement2 = new IIOMetadataNode("table");
        tableElement2.setAttribute("id", "up right" );
        tableElement2.setAttribute("width", "1");
        tableElement2.setAttribute("depth", "2");
        tableElement2.setAttribute("height", "36");
        tableElement2.setAttribute("x", "130" );
        tableElement2.setAttribute("y", "20" );
        tableElement2.setAttribute("z", "0" );
        new Table( tableElement2 );

        Element sourceElement = new IIOMetadataNode( "device" );
        sourceElement.setAttribute( "id", "roger" );
        sourceElement.setAttribute( "on", "up left" );
        sourceElement.setAttribute( "is", templateName );
        Device source = new Device( sourceElement );
        source.verify();

        Element sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", "harriet" );
        sinkElement.setAttribute( "on", "up right" );
        sinkElement.setAttribute( "is", templateName );
        Device sink = new Device( sinkElement );
        sink.verify();

        Element cableRunElement = new IIOMetadataNode("cable-run");
        cableRunElement.setAttribute("signal", signalName );
        cableRunElement.setAttribute("source", "roger" );
        cableRunElement.setAttribute("sink", "harriet" );

        Draw draw = new Draw();
        draw.establishRoot();
        UserCableRun run = new UserCableRun(cableRunElement);
        CableRun ran = run.cableRun();
        ran.verify();

        ran.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), "120.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "130.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domFourWallsNoCorner() throws Exception {
        Element tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", "up left");
        tableElement.setAttribute("width", "1");
        tableElement.setAttribute("depth", "2");
        tableElement.setAttribute("height", "3");
        tableElement.setAttribute("x", "25" );
        tableElement.setAttribute("y", "20" );
        tableElement.setAttribute("z", "0" );
        new Table( tableElement );

        Element tableElement2 = new IIOMetadataNode("table");
        tableElement2.setAttribute("id", "up right" );
        tableElement2.setAttribute("width", "1");
        tableElement2.setAttribute("depth", "2");
        tableElement2.setAttribute("height", "3");
        tableElement2.setAttribute("x", "310" );
        tableElement2.setAttribute("y", "14" );
        tableElement2.setAttribute("z", "0" );
        new Table( tableElement2 );

        Element sourceElement = new IIOMetadataNode( "device" );
        sourceElement.setAttribute( "id", "roger" );
        sourceElement.setAttribute( "on", "up left" );
        sourceElement.setAttribute( "is", templateName );
        Device source = new Device( sourceElement );
        source.verify();

        Element sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", "harriet" );
        sinkElement.setAttribute( "on", "up right" );
        sinkElement.setAttribute( "is", templateName );
        Device sink = new Device( sinkElement );
        sink.verify();

        Element cableRunElement = new IIOMetadataNode("cable-run");
        cableRunElement.setAttribute("signal", signalName );
        cableRunElement.setAttribute("source", "roger" );
        cableRunElement.setAttribute("sink", "harriet" );

        Draw draw = new Draw();
        draw.establishRoot();
        UserCableRun run = new UserCableRun(cableRunElement);
        CableRun ran = run.cableRun();
        ran.verify();

        ran.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
//        assertEquals(list.getLength(), 6);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), "25.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "25.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "25.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "25.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "25.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domFourWallsOneCorner() throws Exception {
        Element tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", "up left");
        tableElement.setAttribute("width", "1");
        tableElement.setAttribute("depth", "2");
        tableElement.setAttribute("height", "3");
        tableElement.setAttribute("x", "12" );
        tableElement.setAttribute("y", "20" );
        tableElement.setAttribute("z", "0" );
        new Table( tableElement );

        Element tableElement2 = new IIOMetadataNode("table");
        tableElement2.setAttribute("id", "up right" );
        tableElement2.setAttribute("width", "1");
        tableElement2.setAttribute("depth", "2");
        tableElement2.setAttribute("height", "3");
        tableElement2.setAttribute("x", "310" );
        tableElement2.setAttribute("y", "14" );
        tableElement2.setAttribute("z", "0" );
        new Table( tableElement2 );

        Element sourceElement = new IIOMetadataNode( "device" );
        sourceElement.setAttribute( "id", "roger" );
        sourceElement.setAttribute( "on", "up left" );
        sourceElement.setAttribute( "is", templateName );
        Device source = new Device( sourceElement );
        source.verify();

        Element sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", "harriet" );
        sinkElement.setAttribute( "on", "up right" );
        sinkElement.setAttribute( "is", templateName );
        Device sink = new Device( sinkElement );
        sink.verify();

        Element cableRunElement = new IIOMetadataNode("cable-run");
        cableRunElement.setAttribute("signal", signalName );
        cableRunElement.setAttribute("source", "roger" );
        cableRunElement.setAttribute("sink", "harriet" );

        Draw draw = new Draw();
        draw.establishRoot();
        UserCableRun run = new UserCableRun(cableRunElement);
        CableRun ran = run.cableRun();
        ran.verify();

        ran.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
//        assertEquals(list.getLength(), 6);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "12.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(5);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domFourWallsTwoCorners() throws Exception {
        Element tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", "up left");
        tableElement.setAttribute("width", "1");
        tableElement.setAttribute("depth", "2");
        tableElement.setAttribute("height", "3");
        tableElement.setAttribute("x", "12" );
        tableElement.setAttribute("y", "20" );
        tableElement.setAttribute("z", "0" );
        new Table( tableElement );

        Element tableElement2 = new IIOMetadataNode("table");
        tableElement2.setAttribute("id", "up right" );
        tableElement2.setAttribute("width", "1");
        tableElement2.setAttribute("depth", "2");
        tableElement2.setAttribute("height", "3");
        tableElement2.setAttribute("x", "310" );
        tableElement2.setAttribute("y", "14" );
        tableElement2.setAttribute("z", "0" );
        new Table( tableElement2 );

        Element sourceElement = new IIOMetadataNode( "device" );
        sourceElement.setAttribute( "id", "roger" );
        sourceElement.setAttribute( "on", "up left" );
        sourceElement.setAttribute( "is", templateName );
        Device source = new Device( sourceElement );
        source.verify();

        Element sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", "harriet" );
        sinkElement.setAttribute( "on", "up right" );
        sinkElement.setAttribute( "is", templateName );
        Device sink = new Device( sinkElement );
        sink.verify();

        Element cableRunElement = new IIOMetadataNode("cable-run");
        cableRunElement.setAttribute("signal", signalName );
        cableRunElement.setAttribute("source", "roger" );
        cableRunElement.setAttribute("sink", "harriet" );

        Draw draw = new Draw();
        draw.establishRoot();
        UserCableRun run = new UserCableRun(cableRunElement);
        CableRun ran = run.cableRun();
        ran.verify();

        ran.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
//        assertEquals(list.getLength(), 6);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "12.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(5);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domAlongLightingStand() throws Exception {
        UserCableRun run = new UserCableRun( luminaireToLuminaireElement );
        CableRun instance = run.cableRun();
        instance.verify();
        CableRun.Collate();
        Draw draw = new Draw();
        draw.establishRoot();

        instance.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
        assertEquals(list.getLength(), 3);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "12.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domSchematicDeviceToLuminaire() throws Exception {
        UserCableRun run = new UserCableRun( deviceToLuminaireElement );
        CableRun instance = run.cableRun();
        instance.verify();
        CableRun.Collate();
        Draw draw = new Draw();
        draw.establishRoot();
        sourceDevice.preview(View.SCHEMATIC);
        lightingStand.preview( View.SCHEMATIC );
        sinkLuminaire.preview( View.SCHEMATIC );
        sourceDevice.dom(draw, View.SCHEMATIC);
        lightingStand.dom( draw, View.SCHEMATIC );
        luminaireDefinition.dom( draw, View.SCHEMATIC );
        sinkLuminaire.dom(draw, View.SCHEMATIC);

        NodeList preGroup = draw.root().getElementsByTagName("g");
        assertEquals(preGroup.getLength(), 4);

        instance.dom( draw, View.SCHEMATIC );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 5);
        Node groupNode = group.item(4);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        // No layer, so no class attribute
        assertEquals(groupElement.getAttribute("class"), "" );

        NodeList list = groupElement.getElementsByTagName("line");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        PagePoint sourcePoint = sourceDevice.schematicPosition();
        PagePoint sinkPoint = sinkLuminaire.schematicPosition();
        assertEquals(element.getAttribute("x1"), sourcePoint.x().toString() );
        assertEquals(element.getAttribute("y1"), sourcePoint.y().toString() );
        assertEquals(element.getAttribute("x2"), sinkPoint.x().toString() );
        assertEquals(element.getAttribute("y2"), sinkPoint.y().toString() );
        assertEquals(element.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
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
        TestResets.DeviceReset();
        TestResets.ElementalListerReset();
        TestResets.StackableReset();
//        TestResets.DeviceReset();
        TestResets.MountableReset();
        TestResets.LuminaireReset();
        TestResets.CableRunReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        wallElement1 = new IIOMetadataNode( "wall" );
        wallElement1.setAttribute( "x1", "0" );
        wallElement1.setAttribute( "y1", "0" );
        wallElement1.setAttribute( "x2", "350" );
        wallElement1.setAttribute( "y2", "0" );
        Wall wall1 = new Wall( wallElement1 );

        wallElement2 = new IIOMetadataNode( "wall" );
        wallElement2.setAttribute( "x1", "350" );
        wallElement2.setAttribute( "y1", "0" );
        wallElement2.setAttribute( "x2", "350" );
        wallElement2.setAttribute( "y2", "400" );
        Wall wall2 = new Wall( wallElement2 );

        wallElement3 = new IIOMetadataNode( "wall" );
        wallElement3.setAttribute( "x1", "350" );
        wallElement3.setAttribute( "y1", "400" );
        wallElement3.setAttribute( "x2", "0" );
        wallElement3.setAttribute( "y2", "400" );
        Wall wall3 = new Wall( wallElement3 );

        wallElement4 = new IIOMetadataNode( "wall" );
        wallElement4.setAttribute( "x1", "0" );
        wallElement4.setAttribute( "y1", "400" );
        wallElement4.setAttribute( "x2", "0" );
        wallElement4.setAttribute( "y2", "0" );
        Wall wall4 = new Wall( wallElement4 );

        wall1.verify();
        wall2.verify();
        wall3.verify();
        wall4.verify();

        tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", tableName);
        tableElement.setAttribute("width", "1");
        tableElement.setAttribute("depth", "2");
        tableElement.setAttribute("height", "3");
        tableElement.setAttribute("x", tableX.toString() );
        tableElement.setAttribute("y", tableY.toString() );
        tableElement.setAttribute("z", tableZ.toString() );
        new Table( tableElement );

        tableElement2 = new IIOMetadataNode("table");
        tableElement2.setAttribute("id", table2Name );
        tableElement2.setAttribute("width", "1");
        tableElement2.setAttribute("depth", "2");
        tableElement2.setAttribute("height", "3");
        tableElement2.setAttribute("x", table2X.toString() );
        tableElement2.setAttribute("y", table2Y.toString() );
        tableElement2.setAttribute("z", table2Z.toString() );
        new Table( tableElement2 );

        templateElement = new IIOMetadataNode("device-template");
        templateElement.setAttribute("type", templateName );
        templateElement.setAttribute("width", "7");
        templateElement.setAttribute("depth", "8");
        templateElement.setAttribute("height", "9");
        new DeviceTemplate( templateElement );

        sourceElement = new IIOMetadataNode( "device" );
        sourceElement.setAttribute( "id", sourceName );
        sourceElement.setAttribute( "on", tableName );
        sourceElement.setAttribute( "is", templateName );
        sourceDevice = new Device( sourceElement );
        sourceDevice.verify();
        
        sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", sinkName );
        sinkElement.setAttribute( "on", table2Name );
        sinkElement.setAttribute( "is", templateName );
        sinkDevice = new Device( sinkElement );
        sinkDevice.verify();

        element = new IIOMetadataNode("cable-run");
        element.setAttribute("signal", signalName );
        element.setAttribute("source", sourceName );
        element.setAttribute("sink", sinkName );

        Integer width = 13;
        Integer length = 27;
        Element definitionElement = new IIOMetadataNode( "luminaire-definition" );
        definitionElement.setAttribute( "name", luminaireType );
        definitionElement.setAttribute( "width", width.toString() );
        definitionElement.setAttribute("length", length.toString());
        definitionElement.appendChild( new IIOMetadataNode( "svg" ) );
        luminaireDefinition = new LuminaireDefinition( definitionElement );
        luminaireDefinition.verify();

        Element lightingStandElement = new IIOMetadataNode( "lighting-stand" );
        lightingStandElement.setAttribute( "id", standId );
        lightingStandElement.setAttribute( "x", standX.toString() );
        lightingStandElement.setAttribute( "y", standY.toString() );
        lightingStand = new LightingStand( lightingStandElement );
        lightingStand.verify();

        sourceLuminaireElement = new IIOMetadataNode( "luminaire" );
        sourceLuminaireElement.setAttribute( "on", standId );
        sourceLuminaireElement.setAttribute( "type", luminaireType );
        sourceLuminaireElement.setAttribute( "unit", sourceLuminaireUnit );
        sourceLuminaireElement.setAttribute( "location", sourceLuminaireLocation );
        sourceLuminaire = new Luminaire( sourceLuminaireElement );
        sourceLuminaire.verify();

        sinkLuminaireElement = new IIOMetadataNode( "luminaire" );
        sinkLuminaireElement.setAttribute( "on", standId );
        sinkLuminaireElement.setAttribute( "type", luminaireType );
        sinkLuminaireElement.setAttribute( "unit", sinkLuminaireUnit );
        sinkLuminaireElement.setAttribute( "location", sinkLuminaireLocation );
        sinkLuminaire = new Luminaire( sinkLuminaireElement );
        sinkLuminaire.verify();

        Element cableTypeElement = new IIOMetadataNode( "cable-type" );
        cableTypeElement.setAttribute( "id", signalName );
        cableTypeElement.setAttribute( "schematic-color", color );
        new CableType( cableTypeElement );

        deviceToLuminaireElement = new IIOMetadataNode( "cable-run" );
        deviceToLuminaireElement.setAttribute( "signal", signalName );
        deviceToLuminaireElement.setAttribute( "source", sourceName );
        deviceToLuminaireElement.setAttribute("sink", sinkLuminaireId);

        luminaireToLuminaireElement = new IIOMetadataNode( "cable-run" );
        luminaireToLuminaireElement.setAttribute( "signal", signalName );
        luminaireToLuminaireElement.setAttribute( "source", sourceLuminaireId );
        luminaireToLuminaireElement.setAttribute( "sink", sinkLuminaireId );

        luminaireToDeviceElement = new IIOMetadataNode( "cable-run" );
        luminaireToDeviceElement.setAttribute( "signal", signalName );
        luminaireToDeviceElement.setAttribute( "source", sourceLuminaireId );
        luminaireToDeviceElement.setAttribute( "sink", sinkName );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
//        solid=null;
    }
}
