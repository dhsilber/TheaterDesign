package com.mobiletheatertech.plot;

import org.testng.annotations.*;

import org.w3c.dom.Element;

import javax.imageio.metadata.IIOMetadataNode;

import java.util.HashMap;
import java.util.TreeMap;

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
    
    Element deviceElement = null;
    String deviceName = "individual device";
    Double x = 3.6;
    Double y = 1.8;
    Double z = 0.1;

//    private Solid solid = null;

    public DeviceTemplateTest() {

    }

    @Test
    public void isa() throws Exception {
        DeviceTemplate deviceTemplate = new DeviceTemplate( element );
        assert Layerer.class.isInstance( deviceTemplate );
        assert Verifier.class.isInstance( deviceTemplate );
        assert ElementalLister.class.isInstance( deviceTemplate );
        assert Elemental.class.isInstance( deviceTemplate );
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
        assertEquals(TestHelpers.accessString(deviceTemplate, "layer"), layerTag);
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
    public void storesSVG() throws Exception {
        Element svgElement = new IIOMetadataNode( "svg" );
        element.appendChild( svgElement );

        DeviceTemplate deviceTemplate = new DeviceTemplate( element );

        Object svgStored = TestHelpers.accessObject( deviceTemplate, "svg" );
        assertNotNull( svgStored );
    }

    @Test
    public void initialCountZero() throws Exception {
        DeviceTemplate deviceTemplate = new DeviceTemplate( element );

        assertEquals(TestHelpers.accessInteger(deviceTemplate, "count"), (Integer) 0);
    }

    @Test
    public void incrementsCount() throws Exception {
        DeviceTemplate deviceTemplate = new DeviceTemplate( element );
        deviceTemplate.count();

        assertEquals( TestHelpers.accessInteger( deviceTemplate, "count" ), (Integer) 1 );
    }

    @Test
    public void recallsNull() {
        assertNull(DeviceTemplate.Select("bogus"));
    }

    @Test
    public void recalls() throws Exception {
        DeviceTemplate deviceTemplate = new DeviceTemplate( element );
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
//        new Layer( layerTag, "whatever", layerColor );
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

        new DeviceTemplate( element );
//        DeviceTemplate deviceTemplate=new DeviceTemplate( element );
//        deviceTemplate.verify();
//
//        Solid solid = deviceTemplate.getSolid();
//        assertEquals( solid.height(), height );
    }

    @Test
    public void layer() throws Exception {
        element.setAttribute("layer", layerTag);
        DeviceTemplate deviceTemplate = new DeviceTemplate( element );

        assertEquals(deviceTemplate.layer(), layerTag);
    }

    @Test
    public void registersLayer() throws Exception {
        TestResets.LayerReset();
        HashMap<String, Layer> layers = Layer.List();
        assertFalse(layers.containsKey(layerTag));

        element.setAttribute("layer", layerTag);
        new DeviceTemplate( element );

        layers = Layer.List();
        assertTrue(layers.containsKey(layerTag));
    }

    @Test
    public void registersLegend() throws Exception {
        TestResets.LegendReset();

        new DeviceTemplate( element );

        TreeMap<Integer, Legendable> legendList = (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
    }

    @Test
    public void domLegendItemNoCount() throws Exception {
        Draw draw = new Draw();
        PagePoint start = new PagePoint( 3.2, 4.5 );

        DeviceTemplate deviceTemplate = new DeviceTemplate( element );
        PagePoint finish = deviceTemplate.domLegendItem( draw, start );

        assertEquals( finish, start );
    }

    @Test
    public void domLegendItemYesCount() throws Exception {
        Draw draw = new Draw();
        PagePoint start = new PagePoint( 3.2, 4.5 );

        DeviceTemplate deviceTemplate = new DeviceTemplate( element );
        /*Device device =*/ new Device( deviceElement );
        PagePoint finish = deviceTemplate.domLegendItem( draw, start );

        assertNotEquals( start, finish );
//        assertNotEquals( finish, start );
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
        TestResets.LayerReset();

        Layer.Register(layerTag, layerTag);

//        solid = new Solid( 1f, 2f, 3f );

        element = new IIOMetadataNode("device-template");
        element.setAttribute("type", name);
        element.setAttribute("width", width.toString());
        element.setAttribute("depth", depth.toString());
        element.setAttribute("height", height.toString());

        deviceElement = new IIOMetadataNode( "device" );
        deviceElement.setAttribute( "id", deviceName );
        deviceElement.setAttribute( "is", name );
        deviceElement.setAttribute( "x", x.toString() );
        deviceElement.setAttribute( "y", y.toString() );
        deviceElement.setAttribute( "z", z.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
//        solid=null;
    }
}
