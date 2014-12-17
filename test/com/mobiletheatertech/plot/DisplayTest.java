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

    String layerName = "Name of Layer";
    String deviceName = "Id of device";

    @Test
    public void isA() throws Exception {
        element.removeAttribute("device");
        Display display = new Display( element );

        assert Elemental.class.isInstance( display );
    }

    @Test
    public void storesLayerAttribute() throws Exception {
        element.removeAttribute("device");
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "layerName" ), layerName );
        assertEquals( TestHelpers.accessString( display, "deviceName" ), "" );
    }

    @Test
    public void storesDeviceAttribute() throws Exception {
        element.removeAttribute( "layer" );
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "layerName" ), "" );
        assertEquals( TestHelpers.accessString( display, "deviceName" ), deviceName );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer' or 'device' attributes." )
    public void noAttributes() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute( "device" );
        new Display( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer' or 'device' attributes." )
    public void bothAttributes() throws Exception {
        new Display( element );
    }

    @Test
    public void layer() throws Exception {
        element.removeAttribute( "device" );
        Display display = new Display( element );

        assertEquals( display.layer(), layerName );
    }

    @Test
    public void device() throws Exception {
        element.removeAttribute( "layer" );
        Display display = new Display( element );

        assertEquals( display.device(), deviceName );
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
        element.setAttribute( "layer", layerName );
        element.setAttribute( "device", deviceName );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
