package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.fail;

/**
 * Created by dhs on 9/9/14.
 */
public class DrawingTest {
    
    Element drawingElement = null;

    String name = "cataName";

    final String id = "Name";
    String filename = "filename";

    @Test
    public void isa() throws Exception {
        Drawing drawing = new Drawing( drawingElement );

        assert ElementalLister.class.isInstance( drawing );
    }

    @Test
    public void storesAttributes() throws Exception {
        Drawing drawing = new Drawing( drawingElement );

        assertEquals(TestHelpers.accessString(drawing, "id"), id);
        assertEquals( TestHelpers.accessString( drawing, "filename" ), filename );
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
     @Test
    public void filename() throws Exception{
        Drawing drawing = new Drawing( drawingElement );

        assertEquals( drawing.filename(), filename );
    }

    @Test
    public void findChildDisplay() throws Exception {
        Element displayElement = new IIOMetadataNode( "display" );
        displayElement.setAttribute( "category", name );
        drawingElement.appendChild( displayElement );

        Draw draw = new Draw();
        draw.establishRoot();
        Drawing drawing = new Drawing( drawingElement );

        ArrayList<String> displays = drawing.displayList;

        assertEquals( displays.size(), 1 );
//        Category category = displays.get( 0 );
//        assertEquals(category.name(), name);
        fail();
    }

    @Test
//    public void recallsNull() {
//        assertNull(Drawing.Select("bogus"));
//    }
//
//    @Test
//    public void recalls() throws Exception {
//        Drawing drawing = new Drawing( drawingElement );
//
//        assertSame(Drawing.Select(drawingName), drawing);
//    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.CategoryReset();

        new Category( name, DrawingTest.class );

        drawingElement = new IIOMetadataNode( "drawing" );
        drawingElement.setAttribute( "id", id );
        drawingElement.setAttribute( "filename", filename );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
