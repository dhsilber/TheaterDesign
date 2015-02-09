package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.fail;

/**
 * Created by dhs on 9/9/14.
 */
public class DrawingTest {
    
    Element drawingElement = null;

    String name = "cataName";

    final String id = "Name";
    String filename = "filename";
    String schematic = "schematic";

    @Test
    public void isa() throws Exception {
        Drawing instance = new Drawing( drawingElement );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        Drawing instance = new Drawing( drawingElement );

        assertEquals( TestHelpers.accessString( instance, "id"), id);
        assertEquals( TestHelpers.accessString( instance, "filename" ), filename );
        assertSame(TestHelpers.accessView(instance, "view"), View.PLAN );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        drawingElement.setAttribute("view", schematic );
        Drawing instance = new Drawing( drawingElement );

        assertEquals( TestHelpers.accessString( instance, "id"), id);
        assertEquals( TestHelpers.accessString( instance, "filename" ), filename );
        assertSame(TestHelpers.accessView(instance, "view"), View.SCHEMATIC );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Drawing instance is missing required 'id' attribute.")
    public void noId() throws Exception {
        drawingElement.removeAttribute("id");
        new Drawing( drawingElement );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp =
                    "Drawing \\(" + id + "\\) is missing required 'filename' attribute." )
    public void noFilename() throws Exception {
        drawingElement.removeAttribute("filename");
        new Drawing( drawingElement );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Drawing \\(" + id + "\\) has invalid 'view' attribute. Valid is 'schematic' or 'spreadsheet'." )
    public void invalidView() throws Exception {
        drawingElement.setAttribute("view", "bogus");
        new Drawing( drawingElement );
    }

    @Test
    public void filename() throws Exception {
        Drawing drawing = new Drawing( drawingElement );

        assertEquals( drawing.filename(), filename );
    }

    @Test
    public void view() throws Exception {
        drawingElement.setAttribute("view", schematic );
        Drawing drawing = new Drawing( drawingElement );

        assertEquals( drawing.view(), View.SCHEMATIC );
    }

    @Test
    public void storeDisplayLayer() throws Exception {
        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "layer", name );
        drawingElement.appendChild( displayElement );
        Draw draw = new Draw();
        draw.establishRoot();

        Drawing drawing = new Drawing( drawingElement );

        ArrayList<String> layers = drawing.layers;
        assertEquals( layers.size(), 1 );
        assertEquals(layers.get(0), name);
        ArrayList<String> devices = drawing.devices;
        assertEquals( devices.size(), 0 );
        ArrayList<String> mountables = drawing.mountables;
        assertEquals( mountables.size(), 0 );
    }

    @Test
    public void storeDisplayDevice() throws Exception {
        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "device", name );
        drawingElement.appendChild( displayElement );
        Draw draw = new Draw();
        draw.establishRoot();

        Drawing drawing = new Drawing( drawingElement );

        ArrayList<String> devices = drawing.devices;
        assertEquals( devices.size(), 1 );
        assertEquals( devices.get( 0 ), name );
        ArrayList<String> layers = drawing.layers;
        assertEquals( layers.size(), 0 );
        ArrayList<String> mountables = drawing.mountables;
        assertEquals( mountables.size(), 0 );
    }

    @Test
    public void storeDisplayMountable() throws Exception {
        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "mountable", name );
        drawingElement.appendChild( displayElement );
        Draw draw = new Draw();
        draw.establishRoot();

        Drawing drawing = new Drawing( drawingElement );

        ArrayList<String> mountables = drawing.mountables;
        assertEquals( mountables.size(), 1 );
        assertEquals( mountables.get( 0 ), name );
        ArrayList<String> devices = drawing.devices;
        assertEquals( devices.size(), 0 );
        ArrayList<String> layers = drawing.layers;
        assertEquals( layers.size(), 0 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        drawingElement = new IIOMetadataNode( "drawing" );
        drawingElement.setAttribute( "id", id );
        drawingElement.setAttribute( "filename", filename );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
