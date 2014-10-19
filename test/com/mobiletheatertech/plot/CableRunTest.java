package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

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
    private Element element = null;

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

//    private Solid solid = null;

    @Test
    public void isa() throws Exception {
        CableRun cableRun = new CableRun( element );
        assert MinderDom.class.isInstance( cableRun );
    }

    @Test
    public void isLegendable() throws Exception {
        CableRun cableRun = new CableRun( element );
        assert Legendable.class.isInstance( cableRun );
    }

    @Test
    public void storesAttributes() throws Exception {
        CableRun cableRun=new CableRun( element );

        assertEquals( TestHelpers.accessString( cableRun, "signal" ), signalName );
        assertEquals( TestHelpers.accessString( cableRun, "source" ), sourceName );
        assertEquals( TestHelpers.accessString( cableRun, "sink" ), sinkName );
        assertEquals( TestHelpers.accessString(cableRun, "channel"), "" );
        assertEquals( TestHelpers.accessString(cableRun, "routing"), "" );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("channel", channel);
        element.setAttribute("routing", validRouting);
        CableRun cableRun=new CableRun( element );

        assertEquals( TestHelpers.accessString( cableRun, "signal" ), signalName );
        assertEquals( TestHelpers.accessString( cableRun, "source" ), sourceName );
        assertEquals( TestHelpers.accessString( cableRun, "sink" ), sinkName );
        assertEquals( TestHelpers.accessString(cableRun, "channel"), channel );
        assertEquals( TestHelpers.accessString(cableRun, "routing"), validRouting );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun is missing required 'signal', 'source', and 'sink' attributes.")
    public void noRequiredAttributes() throws Exception {
        element.removeAttribute("signal");
        element.removeAttribute("source");
        element.removeAttribute("sink");
        new CableRun(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun from "+sourceName+" to "+sinkName+" is missing required 'signal' attribute.")
    public void noSignal() throws Exception {
        element.removeAttribute("signal");
        new CableRun(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" to "+sinkName+" is missing required 'source' attribute.")
    public void noSource() throws Exception {
        element.removeAttribute("source");
        new CableRun(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" from "+sourceName+" is missing required 'sink' attribute.")
    public void noSink() throws Exception {
        element.removeAttribute("sink");
        new CableRun(element);
    }


    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun to "+sinkName+" is missing required 'signal' and 'source' attributes.")
    public void noSignalSource() throws Exception {
        element.removeAttribute("signal");
        element.removeAttribute("source");
        new CableRun(element);
    }


    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun from "+sourceName+" is missing required 'signal' and 'sink' attributes.")
    public void noSignalSink() throws Exception {
        element.removeAttribute("signal");
        element.removeAttribute("sink");
        new CableRun(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" is missing required 'source' and 'sink' attributes.")
    public void noSourceSink() throws Exception {
        element.removeAttribute("source");
        element.removeAttribute("sink");
        new CableRun(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun from "+sourceName+" to "+sinkName+" has invalid routing attribute '"+invalidRouting+"'.")
    public void invalidRouting() throws Exception {
        element.setAttribute("routing", invalidRouting);
        new CableRun(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" to "+sinkName+" references unknown source '"+ bogusSource+"'.")
    public void verifyUnknownSourceDevice() throws Exception {
        element.setAttribute("source", bogusSource);
        CableRun run = new CableRun(element);
        run.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" from "+sourceName+" references unknown sink '"+bogusSink+"'.")
    public void verifyUnknownSinkDevice() throws Exception {
        element.setAttribute("sink", bogusSink);
        CableRun run = new CableRun(element);
        run.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "CableRun of "+signalName+" references unknown source '"+ bogusSource+"' and sink '"+bogusSink+"'.")
    public void verifyUnknownSourceSinkDevices() throws Exception {
        element.setAttribute("source", bogusSource);
        element.setAttribute("sink", bogusSink);
        CableRun run = new CableRun(element);
        run.verify();
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

    @Test
    public void dom() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        CableRun run = new CableRun(element);
        run.verify();

        run.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.attribute("class"), Pipe.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("line");
//        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), tableX.toString() );
        assertEquals(element.getAttribute("y1"), tableY.toString() );
        assertEquals(element.getAttribute("y2"), table2Y.toString() );
        assertEquals(element.getAttribute("x2"), table2X.toString() );
        assertEquals(element.getAttribute("stroke"), "green" );
        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

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
        CableRun run = new CableRun(cableRunElement);
        run.verify();

        run.dom( draw, View.PLAN );

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
        assertEquals(element.getAttribute("stroke"), "green" );
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
        CableRun run = new CableRun(cableRunElement);
        run.verify();

        run.dom( draw, View.PLAN );

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
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "25.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "25.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "25.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
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
        CableRun run = new CableRun(cableRunElement);
        run.verify();

        run.dom( draw, View.PLAN );

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
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(5);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
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
        CableRun run = new CableRun(cableRunElement);
        run.verify();

        run.dom( draw, View.PLAN );

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
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(1);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "12.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "20.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(2);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "20.0" );
        assertEquals(element.getAttribute("x2"), "2.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(3);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "2.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "2.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(4);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "2.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        node = list.item(5);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals(element.getAttribute("x1"), "310.0" );
        assertEquals(element.getAttribute("y1"), "14.0" );
        assertEquals(element.getAttribute("x2"), "310.0" );
        assertEquals(element.getAttribute("y2"), "14.0" );
        assertEquals(element.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );
    }

    @Test
    public void domLegendItem() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        CableRun run = new CableRun(element);
        run.verify();

        NodeList preLine = draw.root().getElementsByTagName( "line" );
        assertEquals( preLine.getLength(), 0 );
        NodeList preText = draw.root().getElementsByTagName( "text" );
        assertEquals( preText.getLength(), 0 );

        PagePoint startPoint = new PagePoint( 20.0, 10.0 );
        PagePoint endPoint = run.domLegendItem( draw, startPoint );

        NodeList lineList = draw.root().getElementsByTagName( "line" );
        assertEquals( lineList.getLength(), 1 );
        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 1 );


        Node useNode = lineList.item(0);
        assertEquals(useNode.getNodeType(), Node.ELEMENT_NODE);
        Element lineElement = (Element) useNode;
        Double endX = startPoint.x() + 12;
        assertEquals(lineElement.getAttribute("x1"), startPoint.x().toString() );
        assertEquals(lineElement.getAttribute("y1"), startPoint.y().toString());
        assertEquals(lineElement.getAttribute("x2"), endX.toString() );
        assertEquals(lineElement.getAttribute("y2"), startPoint.y().toString());
        assertEquals(lineElement.getAttribute("stroke"), "green" );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        Node textNode = textList.item(0);
        assertEquals(textNode.getNodeType(), Node.ELEMENT_NODE);
        Element textElement = (Element) textNode;
        Double x = startPoint.x() + 20;
        Double y = startPoint.y() + 3;
        assertEquals(textElement.getAttribute("x"), x.toString() );
        assertEquals(textElement.getAttribute("y"), y.toString() );
        assertEquals(textElement.getAttribute("fill"), "black" );

        // TODO Check for text here

        assertEquals( endPoint, new PagePoint( startPoint.x(), startPoint.y() + 7 ));
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
        Device source = new Device( sourceElement );
        source.verify();

        sinkElement = new IIOMetadataNode( "device" );
        sinkElement.setAttribute( "id", sinkName );
        sinkElement.setAttribute( "on", table2Name );
        sinkElement.setAttribute( "is", templateName );
        Device sink = new Device( sinkElement );
        sink.verify();

        element = new IIOMetadataNode("cable-run");
        element.setAttribute("signal", signalName );
        element.setAttribute("source", sourceName );
        element.setAttribute("sink", sinkName );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
//        solid=null;
    }
}
