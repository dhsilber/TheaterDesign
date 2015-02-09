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
    String mountableName = "Id of mountable thing";

    @Test
    public void isA() throws Exception {
        element.removeAttribute("device");
        element.removeAttribute( "mountable" );
        Display display = new Display( element );

        assert Elemental.class.isInstance( display );
    }

    @Test
    public void storesLayerAttribute() throws Exception {
        element.removeAttribute("device");
        element.removeAttribute( "mountable" );
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "layerName" ), layerName );
        assertEquals( TestHelpers.accessString( display, "deviceName" ), "" );
        assertEquals( TestHelpers.accessString( display, "mountableName" ), "" );
    }

    @Test
    public void storesDeviceAttribute() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute( "mountable" );
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "layerName" ), "" );
        assertEquals( TestHelpers.accessString( display, "deviceName" ), deviceName );
        assertEquals( TestHelpers.accessString( display, "mountableName" ), "" );
    }

    @Test
    public void storesMountableAttribute() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute( "device" );
        Display display = new Display( element );

        assertEquals( TestHelpers.accessString( display, "layerName" ), "" );
        assertEquals( TestHelpers.accessString( display, "deviceName" ), "" );
        assertEquals( TestHelpers.accessString( display, "mountableName" ), mountableName );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer', 'device', or 'mountable' attributes." )
    public void noAttributes() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute("device");
        element.removeAttribute("mountable");
        new Display( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer', 'device', or 'mountable' attributes." )
    public void allAttributes() throws Exception {
        new Display( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer', 'device', or 'mountable' attributes." )
    public void layerAndDeviceAttributes() throws Exception {
        element.removeAttribute("mountable");
        new Display( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer', 'device', or 'mountable' attributes." )
    public void layerAndMountableAttributes() throws Exception {
        element.removeAttribute("device");
        new Display( element );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Display requires one of 'layer', 'device', or 'mountable' attributes." )
    public void deviceAndMountableAttributes() throws Exception {
        element.removeAttribute( "layer" );
        new Display( element );
    }

    @Test
    public void layer() throws Exception {
        element.removeAttribute( "device" );
        element.removeAttribute("mountable");
        Display display = new Display( element );

        assertEquals( display.layer(), layerName );
    }

    @Test
    public void device() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute("mountable");
        Display display = new Display( element );

        assertEquals( display.device(), deviceName );
    }

    @Test
    public void mountable() throws Exception {
        element.removeAttribute( "layer" );
        element.removeAttribute( "device" );
        Display display = new Display( element );

        assertEquals( display.mountable(), mountableName );
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
        element.setAttribute( "mountable", mountableName );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
