package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 7/6/14.
 */
public class DeviceTemplateTest {

    private Element element = null;

    private final String name = "Device Type";
    private final Double width = 6.5;
    private final Double depth = 4.7;
    private final Double height = 2.3;
    private final String layerTag = "Device_Layer";
    private final String layerColor = "color";

//    private Solid solid = null;

    public DeviceTemplateTest() {

    }

    @Test
    public void isa() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        assert Verifier.class.isInstance( deviceTemplate );
    }

    @Test
    public void storesAttributes() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );

        assertEquals( TestHelpers.accessString( deviceTemplate, "id" ), name );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "width"), width );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "depth"), depth );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "height"), height );
        assertNull( TestHelpers.accessString(deviceTemplate, "layer") );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute("layer", layerTag);
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );

        assertEquals( TestHelpers.accessString( deviceTemplate, "id" ), name );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "width"), width );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "depth"), depth );
        assertEquals( TestHelpers.accessDouble(deviceTemplate, "height"), height );
        assertEquals( TestHelpers.accessString( deviceTemplate, "layer" ), layerTag);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) is missing required 'width' attribute.")
    public void noWidth() throws Exception {
        element.removeAttribute("width");
        new DeviceTemplate(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) is missing required 'depth' attribute.")
    public void noDepth() throws Exception {
        element.removeAttribute("depth");
        new DeviceTemplate(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) is missing required 'height' attribute.")
    public void noHeight() throws Exception {
        element.removeAttribute("height");
        new DeviceTemplate(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) value for 'width' attribute should not be negative.")
    public void noegativeWidth() throws Exception {
        element.setAttribute("width", "-3.2");
        new DeviceTemplate(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) value for 'depth' attribute should not be negative.")
    public void noegativeDepth() throws Exception {
        element.setAttribute("depth", "-1.2");
        new DeviceTemplate(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "DeviceTemplate \\("+name+"\\) value for 'height' attribute should not be negative.")
    public void noegativeHeight() throws Exception {
        element.setAttribute("height", "-5.2");
        new DeviceTemplate(element);
    }

    @Test
    public void recallsNull() {
        assertNull(DeviceTemplate.Select("bogus"));
    }

    @Test
    public void recalls() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        assertSame(DeviceTemplate.Select(name), deviceTemplate);
    }

    @Test
    public void solidWidth() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        Solid solid = deviceTemplate.getSolid();
        assertEquals(solid.width(), width);
    }

    @Test
    public void solidDepth() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        Solid solid = deviceTemplate.getSolid();
        assertEquals(solid.depth(), depth);
    }

    @Test
    public void solidHeight() throws Exception {
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        Solid solid = deviceTemplate.getSolid();
        assertEquals( solid.height(), height );
    }

    @Test
    public void layerDefined() throws Exception {
        new Layer( layerTag, "whatever", layerColor );
        element.setAttribute( "layer", layerTag );

        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        deviceTemplate.verify();

//TODO        this should check that the layer is activated
        Solid solid = deviceTemplate.getSolid();
        assertEquals( solid.height(), height );
    }

    @Test
    public void layerUndefined() throws Exception {
        TestResets.LayerReset();
        element.setAttribute( "layer", layerTag );

//TODO        This should throw an error because the layer is not defined
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
        deviceTemplate.verify();

        Solid solid = deviceTemplate.getSolid();
        assertEquals( solid.height(), height );
    }

    @Test
    public void layer() throws Exception {
        element.setAttribute( "layer", layerTag );
        DeviceTemplate deviceTemplate=new DeviceTemplate( element );

        assertEquals( deviceTemplate.layer(), layerTag );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.DeviceTemplateReset();

//        solid = new Solid( 1f, 2f, 3f );

        element = new IIOMetadataNode("device-template");
        element.setAttribute("type", name);
        element.setAttribute("width", width.toString());
        element.setAttribute("depth", depth.toString());
        element.setAttribute("height", height.toString());
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
//        solid=null;
    }
}
