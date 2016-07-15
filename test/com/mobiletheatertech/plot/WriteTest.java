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

        public MindedDom( Element element ) throws DataException, InvalidXMLException {
            super( element );
        }
        public void verify() {}

        @Override
        public void dom( Draw draw, View view ) {
            this.view = view;
        }
    }

    Element venueElement;


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
        System.err.println( "Pathname: " + pathName );
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

        assertEquals( nodes.getLength(), 6 );

        int styleType = nodes.item( 3 ).getNodeType();
        assertEquals( styleType, Element.ELEMENT_NODE );
        Element style = (Element) nodes.item( 3 );
        assertEquals( style.getTagName(), "style");
        assertEquals( style.getAttribute( "type" ), "text/css" );
        assertEquals( style.getFirstChild().getNodeType(), Node.CDATA_SECTION_NODE );
        assertEquals( style.getFirstChild().getTextContent(), Write.CSS );
    }

    @Test
    public void startFileMakesScript() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        assertEquals( nodes.getLength(), 6 );

        int scriptType = nodes.item( 4 ).getNodeType();
        assertEquals( scriptType, Element.ELEMENT_NODE );
        Element script = (Element) nodes.item( 4 );
        assertEquals( script.getTagName(), "script");
        assertEquals( script.getAttribute( "type" ), "text/ecmascript" );
        assertNotNull( script.getFirstChild() );
        assertEquals( script.getFirstChild().getNodeType(), Node.CDATA_SECTION_NODE );
        assertEquals( script.getFirstChild().getTextContent(), Write.ECMAScript);
    }

    @Test
    public void startFileMakesPersistentTextBox() throws ReferenceException {
        Write write = new Write();
        Draw draw = write.startFile();

        Element rootElement = draw.root();
        NodeList nodes = rootElement.getChildNodes();

        assertEquals( nodes.getLength(), 6 );

        int nodeType = nodes.item( 5 ).getNodeType();
        assertEquals( nodeType, Element.ELEMENT_NODE );
        Element textElement = (Element) nodes.item( 5 );
        assertEquals( textElement.getTagName(), "text");
        assertEquals( textElement.getAttribute( "id" ), "persistent" );
        assertEquals( textElement.getAttribute( "fill" ), "black" );
        assertEquals( textElement.getAttribute( "stroke" ), "none" );
        assertEquals( textElement.getAttribute( "font-size" ), "12" );
        assertEquals( textElement.getAttribute( "visibility" ), "hidden" );

        assertEquals( textElement.getTextContent(), "initial content" );
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

        Element deviceElement = new IIOMetadataNode( "device" );
        deviceElement.setAttribute( "id", "Fred" );
        deviceElement.setAttribute( "is", deviceType );
        deviceElement.setAttribute( "x", "1.0" );
        deviceElement.setAttribute( "y", "2.0" );
        deviceElement.setAttribute( "z", "0" );
        deviceElement.setAttribute( "layer", layerId );
        Device device = new Device( deviceElement );
        device.verify();

        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 0 );

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
    public void writeIndividualDrawingSetsViewSchematic() throws Exception {
        String layerId = "MindedDom";
        String layerName = "layer Name";
        String layerColor = "blue";

        Element drawingElement = new IIOMetadataNode( "drawing" );
        drawingElement.setAttribute( "id", "drawing" );
        drawingElement.setAttribute( "filename", "file/file" );
        drawingElement.setAttribute( "view", "schematic" );

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

        assertEquals( TestHelpers.accessView(bogusMindedDom, "view"), View.SCHEMATIC );
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
        TestResets.MinderDomReset();
//        TestResets.DeviceReset();
        TestResets.ElementalListerReset();

        venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Room" );
        venueElement.setAttribute( "width", "350" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "240" );
        new Venue( venueElement );

        Element eventElement = new IIOMetadataNode( "event" );
        eventElement.setAttribute( "name", "WriteTest event" );
        new Event( eventElement );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}