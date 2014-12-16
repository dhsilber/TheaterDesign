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
                    "Drawing \\(" + id + "\\) has invalid 'view' attribute. Valid is 'schematic'." )
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
