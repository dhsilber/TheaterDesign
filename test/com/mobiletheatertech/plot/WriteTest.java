package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

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

    public WriteTest() {
    }

    @Test
    // TODO Is it even possible for this to happen?
//        ( expectedExceptions=SystemDataMissingException.class,
//        expectedExceptionsMessageRegExp = "User has no home directory")
    public void noHome() throws Exception
    {
        fail( "Must throw exception if user's home is not available." );
    }

    @Test
    public void directory() throws Exception {
        Random random = new Random();
        String directoryName = ((Integer) random.nextInt()).toString();
        String pathName = System.getProperty( "user.home" ) + "/Dropbox/Plot/out/" + directoryName;
        System.err.println( "Pathname: " + pathName );
        File tmp = new File( pathName );
        assertFalse( tmp.exists() );

        new Write().init( directoryName );
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

        File[] contents = tmp.listFiles();
        assertEquals( contents.length, 7 );
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