package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 9/9/14.
 */
public class DisplayTest {

    Element element = null;

    String category = "Name of Category";

    @Test
    public void isElemental() throws Exception {
        Display display = new Display( element );

        assert Elemental.class.isInstance( display );
    }

    @Test
    public void storesAttributes() throws Exception {
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "category" ), category );
    }

    @Test( expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Display instance is missing required 'category' attribute." )
    public void noCategory() throws Exception {
        element.removeAttribute( "category" );
        new Display( element );
    }

    @Test
    public void category() throws Exception {
        Display display = new Display( element );

        assertEquals( display.category(), category );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "display" );
        element.setAttribute( "category", category );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
