package com.mobiletheatertech.plot;

//import org.apache.xalan.xsltc.runtime.Node;
import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.imageio.metadata.IIOMetadataNode;
import java.io.File;
import java.util.Random;

import static org.testng.Assert.*;

/**
 * Test {@code Write}.
 *
 * @author dhs
 * @since 0.0.1
 */
public class WriteTest {

    class MindedDom extends MinderDom {

        public View view;

        public MindedDom( Element element )
                throws AttributeMissingException, DataException, InvalidXMLException {
            super( element );
        }
        public void verify() {}

        @Override
        public void dom( Draw draw, View view ) {
            this.view = view;
        }
    }

    Element venueElement;

    String venueRoom = "Test Room";
    Integer venueHWidth = 350;
    Integer venueHDepth = 400;
    Integer venueHeight = 240;


    @BeforeMethod
    public void setUpMethod() throws Exception {
        Venue.Reset();
        TestResets.MinderDomReset();
//        TestResets.DeviceReset();
        TestResets.ElementalListerReset();
        UniqueId.Reset();
        TestResets.DeviceTemplateReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", venueRoom );
        venueElement.setAttribute( "width", venueHWidth.toString() );
        venueElement.setAttribute( "depth", venueHDepth.toString() );
        venueElement.setAttribute( "height", venueHeight.toString() );
        new Venue( venueElement );

        Element eventElement = new IIOMetadataNode( "event" );
        eventElement.setAttribute( "id", "WriteTest event" );
        new Event( eventElement );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

//    @Test
//    // TODO Is it even possible for this to happen?
////        ( expectedExceptions=SystemDataMissingException.class,
////        expectedExceptionsMessageRegExp = "User has no home directory")
//    public void noHome() throws Exception
//    {
//        fail( "Must throw exception if user's home is not available." );
//    }

    @Test
    public void directory() throws Exception {
        Random random = new Random();
        String directoryName = ((Integer) random.nextInt()).toString();
        String pathName = System.getProperty( "user.home" ) + "/Dropbox/Plot/out/" + directoryName;
        System.err.println( "Pathname for test results: " + pathName );
        File tmp = new File( pathName );
        assertFalse( tmp.exists() );

//        String[] arguments = new String[]{ "foo", "~/Dropbox/Plot/out/" + directoryName + "/" };
        String[] arguments = new String[]{ directoryName };
        Configuration.Initialize( arguments );

//        new Write().init( directoryName );
        new Write().init();
        tmp = new File( pathName );
        assertTrue( tmp.exists() );
        assertTrue( tmp.isDirectory() );

        File drawings = new File( pathName + "/drawings.html" );
        assertTrue( drawings.exists() );

        File designer = new File( pathName + "/designer.html" );
        assertTrue( designer.exists() );

        File css = new File( pathName + "/styles.css" );
        assertTrue( css.exists() );

        File plan = new File( pathName + "/plan.svg" );
        assertTrue( plan.exists() );

        File section = new File( pathName + "/section.svg" );
        assertTrue( section.exists() );
        File front = new File( pathName + "/front.svg" );
        assertTrue( front.exists() );

        File truss = new File( pathName + "/truss.svg" );
        assertTrue( truss.exists() );

        File weight = new File( pathName + "/weights" );
        assertTrue( weight.exists() );

        File[] contents = tmp.listFiles();
        assertEquals( contents.length, 8 );
    }

    @Test
    public void startFileMakesSvgRoot() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();

        assertEquals( rootElement.getTagName(), "svg" );
    }

    // This is mostly to confirm that I can actually extract the attribute
    // I need in startFileMakesPlotNamespace()
    @Test
    public void startFileSetsSvgFill() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();

        assertEquals( rootElement.getAttribute( "fill" ), "black" );
        assertEquals( rootElement.getAttributeNS( null, "fill" ), "black" );
    }

    @Test
    public void startFileMakesPlotNamespace() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();

        assertEquals( rootElement.getAttribute( "xmlns:plot" ),
                "http://www.davidsilber.name/namespaces/plot" );
    }

    @Test
    public void startFileMakesCss() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        int styleType = nodes.item( 3 ).getNodeType();
        assertEquals( styleType, Element.ELEMENT_NODE );
        Element style = (Element) nodes.item( 3 );
        assertEquals( style.getTagName(), "style");
        assertEquals( style.getAttribute( "type" ), "text/css" );
        assertEquals( style.getFirstChild().getNodeType(), Node.CDATA_SECTION_NODE );
        assertEquals( style.getFirstChild().getTextContent(), Write.CSS );

        assertEquals( nodes.getLength(), 6 );
    }

    @Test
    public void startFileMakesScript() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        int scriptType = nodes.item( 4 ).getNodeType();
        assertEquals( scriptType, Element.ELEMENT_NODE );
        Element script = (Element) nodes.item( 4 );
        assertEquals( script.getTagName(), "script");
        assertEquals( script.getAttribute( "type" ), "text/ecmascript" );
        assertNotNull( script.getFirstChild() );
        assertEquals( script.getFirstChild().getNodeType(), Node.CDATA_SECTION_NODE );
        assertEquals( script.getFirstChild().getTextContent(), Write.ECMAScript);

        assertEquals( nodes.getLength(), 6 );
    }

    @Test
    public void startFileMakesPersistentTextBox() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        int nodeType = nodes.item( 5 ).getNodeType();
        assertEquals( nodeType, Element.ELEMENT_NODE );
        Element foreignElement = (Element) nodes.item( 5 );
        assertEquals( foreignElement.getTagName(), "foreignObject" );
        assertEquals( foreignElement.getAttribute( "id" ), "persistent");
        assertEquals( foreignElement.getAttribute( "requiredExtensions" ),
                "http://www.w3.org/1999/xhtml");

        assertEquals( nodes.getLength(), 6 );
    }

    @Test
    public void startFilePersistentTextBoxContains() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        int nodeType = nodes.item( 5 ).getNodeType();
        assertEquals( nodeType, Element.ELEMENT_NODE );
        Element foreignElement = (Element) nodes.item( 5 );
        assertEquals( foreignElement.getTagName(), "foreignObject" );
        assertEquals( foreignElement.getAttribute( "id" ), "persistent");

//        Element persistent = draw.document().getElementById( "persistent" );

        nodes = foreignElement.getChildNodes();

        nodeType = nodes.item( 0 ).getNodeType();
        assertEquals( nodeType, Element.ELEMENT_NODE );
        Element bodyElement = (Element) nodes.item( 0 );
        assertEquals( bodyElement.getTagName(), "body" );
        assertEquals( bodyElement.getAttribute( "xmlns" ), "http://www.w3.org/1999/xhtml");

        nodes = bodyElement.getChildNodes();

        Element tableElement = checkElement( nodes, 0, "table" );

        nodes = tableElement.getChildNodes();

        checkTableRow(nodes, 0, "on" );
        checkTableRow(nodes, 1, "unit" );
        checkTableRow(nodes, 2, "location" );
        checkTableRow(nodes, 3, "type" );
        checkTableRow(nodes, 4, "color" );
        checkTableRow(nodes, 5, "address" );
        checkTableRow(nodes, 6, "channel" );
        checkTableRow(nodes, 7, "dimmer" );
        checkTableRow(nodes, 8, "circuit" );
        checkTableRow(nodes, 9, "info" );

        assertEquals( nodes.getLength(), 10 );
    }

    private void checkTableRow( NodeList nodes, Integer index, String data ) {
        Element row1Element = checkElement( nodes, index, "tr" );

        NodeList rowNodes = row1Element.getChildNodes();

        Element headerElement = checkElement( rowNodes, 0, "th" );
        assertEquals( headerElement.getTextContent(), data );

        Element dataElement = checkElement( rowNodes, 1, "td" );
        assertEquals( dataElement.getAttribute( "id" ), "persistent-" + data );
        assertEquals( dataElement.getTextContent(), data + " data" );
    }

    private Element checkElement(NodeList nodes, Integer index, String tag) {
        int nodeType = nodes.item( index ).getNodeType();
        assertEquals( nodeType, Element.ELEMENT_NODE );
        Element element = (Element) nodes.item( index );
        assertEquals( element.getTagName(), tag );

        return element;
    }

    @Test
    public void weightCalculations() {
        Random random = new Random();
        String directoryName = ((Integer) random.nextInt()).toString();
        String pathName = System.getProperty( "user.home" ) + "/Dropbox/Plot/out/" + directoryName;
        System.err.println( "Pathname: " + pathName );
        File parentDirectory = new File( pathName );
        parentDirectory.mkdir();
        String weightsPath = pathName + "/weights";

        new Write().writeWeightCalculations( pathName );
        File checkDirectory = new File( weightsPath );
        assertTrue( checkDirectory.exists() );
        assertTrue( checkDirectory.isDirectory() );
    }

//    TODO    Test that correct HTML/Javascript bits are generated for active layers.


//    @Test
//    public void checkbox() throws Exception {
//        String name="name of thing";
//        String tag="tag for thing";
//        HashMap<String,String> thing=new HashMap<>(  );
//        thing                       .put( name,tag );
//
//        Write write=new Write( "stringValue" );
//
//        write.
//
//        String result=
//
//        assertEquals( thing,
//                      "<input type=\"radio\" name=\"setup\" onclick=\"parent.selectsetup();\" checked=\"checked\" value=\"" +
//                              tag + "\" />" +
//                              name + "<br />\n" );
//    }

    @Test
    public void drawingCounts() throws Exception {
        String layerId = "layer ID";
        String layerName = "layer Name";
        String layerColor = "blue";
        String deviceType = "device Type";

        Element drawingElement = new IIOMetadataNode( "drawing" );
        drawingElement.setAttribute( "id", "drawing" );
        drawingElement.setAttribute( "filename", "file/file" );

        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "layer", layerId );
        drawingElement.appendChild( displayElement );

        Drawing drawing = new Drawing( drawingElement );
        new Layer( layerId, layerName, layerColor );

        Element deviceTemplateElement = new IIOMetadataNode( "device-template" );
        deviceTemplateElement.setAttribute( "type", deviceType );
        deviceTemplateElement.setAttribute( "width", "1.0" );
        deviceTemplateElement.setAttribute( "depth", "2.0" );
        deviceTemplateElement.setAttribute( "height", "3.0" );
        deviceTemplateElement.setAttribute( "layer", layerId );

        DeviceTemplate deviceTemplate = new DeviceTemplate( deviceTemplateElement );

        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 0 );

        Element deviceElement = new IIOMetadataNode( "device" );
        deviceElement.setAttribute( "id", "Fred" );
        deviceElement.setAttribute( "is", deviceType );
        deviceElement.setAttribute( "x", "1.0" );
        deviceElement.setAttribute( "y", "2.0" );
        deviceElement.setAttribute( "z", "0" );
        deviceElement.setAttribute( "layer", layerId );
        Device device = new Device( deviceElement );
        device.verify();

//        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 0 );

        Write write = new Write();
        write.writeIndividualDrawing(drawing);

        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 1 );

        write.writeIndividualDrawing( drawing );

        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 1 );
    }

    @Test
    public void writeIndividualDrawingSetsViewPlan() throws Exception {
        String layerId = "MindedDom";
        String layerName = "layer Name";
        String layerColor = "blue";

        Element drawingElement = new IIOMetadataNode( "drawing" );
        drawingElement.setAttribute( "id", "drawing" );
        drawingElement.setAttribute( "filename", "file/file" );

        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "layer", layerId );
        drawingElement.appendChild( displayElement );

        Drawing drawing = new Drawing( drawingElement );
        new Layer( layerId, layerName, layerColor );

        Element bogusMindedDomElement = new IIOMetadataNode( "bogus" );
        MindedDom bogusMindedDom = new MindedDom( bogusMindedDomElement );

        assertNull( TestHelpers.accessView(bogusMindedDom, "view" ) );

        Write write = new Write();
        write.writeIndividualDrawing(drawing);

        assertEquals( TestHelpers.accessView(bogusMindedDom, "view"), View.PLAN );
    }

    @Test
    public void drawPlanLegendStartup() throws Exception {
        Write write = new Write();
        Draw draw = write.drawPlan();

        NodeList groupList = draw.root().getElementsByTagName( "g" );
        // item 0 exists before dom() adds any content.
        Node groupNode = groupList.item(1);
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Legend.Tag );

        NodeList groupBoxes = groupElement.getElementsByTagName( "rect" );
        Node boxNode = groupBoxes.item( 0 );
        assertEquals( boxNode.getNodeType(), Node.ELEMENT_NODE );
        Element boxElement = (Element) boxNode;
        Double x = venueHWidth + SvgElement.OffsetX() * 2 + 5;
        assertEquals( boxElement.getAttribute( "x" ), x.toString() );
        assertEquals( boxElement.getAttribute( "y" ), "1" );
        assertEquals( boxElement.getAttribute( "width" ), Legend.PlanWidth().toString() );
        Double height = Legend.Y + Legend.HEIGHT + SvgElement.OffsetY();
        assertEquals( boxElement.getAttribute( "height" ), height.toString() );
        assertEquals( boxElement.getAttribute( "fill" ), "none" );
    }

    @Test
    public void drawSectionLegendStartup() throws Exception {
        Write write = new Write();
        Draw draw = write.drawSection();

        NodeList groupList = draw.root().getElementsByTagName( "g" );
        // item 0 exists before dom() adds any content.
        Node groupNode = groupList.item(1);
        assertEquals( groupNode.getNodeType(), Node.ELEMENT_NODE );
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute( "class" ), Legend.Tag );

        NodeList groupBoxes = groupElement.getElementsByTagName( "rect" );
        Node boxNode = groupBoxes.item( 0 );
        assertEquals( boxNode.getNodeType(), Node.ELEMENT_NODE );
        Element boxElement = (Element) boxNode;
        Double x = venueHDepth + SvgElement.OffsetX() * 2 + 5;
        assertEquals( boxElement.getAttribute( "x" ), x.toString() );
        assertEquals( boxElement.getAttribute( "y" ), "1" );
        assertEquals( boxElement.getAttribute( "width" ), Legend.PlanWidth().toString() );
        Double height = Legend.Y + Legend.HEIGHT + SvgElement.OffsetY();
        assertEquals( boxElement.getAttribute( "height" ), height.toString() );
        assertEquals( boxElement.getAttribute( "fill" ), "none" );
    }

//    @Test
//    public void writeIndividualDrawingSetsViewSchematic() throws Exception {
//        String layerId = "MindedDom";
//        String layerName = "layer Name";
//        String layerColor = "blue";
//
//        Element drawingElement = new IIOMetadataNode( "drawing" );
//        drawingElement.setAttribute( "id", "drawing" );
//        drawingElement.setAttribute( "filename", "file/file" );
//        drawingElement.setAttribute( "view", "schematic" );
//
//        Element displayElement = new IIOMetadataNode( "display" );
//        displayElement.setAttribute( "layer", layerId );
//        drawingElement.appendChild( displayElement );
//
//        Drawing drawing = new Drawing( drawingElement );
//        new Layer( layerId, layerName, layerColor );
//
//        Element bogusMindedDomElement = new IIOMetadataNode( "bogus" );
//        MindedDom bogusMindedDom = new MindedDom( bogusMindedDomElement );
//
//        assertNull( TestHelpers.accessView(bogusMindedDom, "view" ) );
//
//        Write write = new Write();
//        write.writeIndividualDrawing(drawing);
//
////        assertEquals( TestHelpers.accessView(bogusMindedDom, "view"), View.SCHEMATIC );
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}