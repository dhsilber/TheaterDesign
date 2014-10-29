package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

/**
 * Created by dhs on 6/22/14.
 */
public class DeviceTest {

    Element tableElement = null;
    Element templateElement = null;
    Element layeredTemplateElement = null;
    Element element = null;
    Element layeredElement = null;
    Element stackedElement = null;
    Element stackedTemplateElement = null;

    final String deviceName = "Intercom base station";
    final String tableName = "control table";
    final String templateName = "Clear-Com CS-210";
    final String layeredTemplateName = "Clear-Com RS-100A";
    final String layerTag = "intercom";
    final String layerColor = "green";
    String stackedTemplateName = "Stacked tempolate";

    Double tableWidth = 1.0;
    Double tableDepth = 2.0;
    Double tableHeight = 3.0;
    Double tableX = 4.0;
    Double tableY = 5.0;
    Double tableZ = 6.0;

    Double width = 7.0;
    Double depth = 8.0;
    Double height = 9.0;
    Double x = 33.0;
    Double y = 29.0;
    Double z = 87.0;
    Double orientation = 90.0;
    Double zero = 0.0;

    Double stackedWidth = 1.0;
    Double stackedDepth = 2.0;
    Double stackedHeight = 3.0;
    Double stackedX = 2.0;
    Double stackedY = 2.3;
    Double stackedZ = 1.2;

    @Test
    public void isA() throws Exception {
        Device device = new Device( element );

        assert MinderDom.class.isInstance( device );
    }

    @Test
    public void storesAttributes() throws Exception {
        // These are optional, so their absence should not cause a problem:
        element.removeAttribute( "x" );
        element.removeAttribute( "y" );
        element.removeAttribute( "z" );
        Device device = new Device( element );

        assertEquals( TestHelpers.accessString( device, "id" ), deviceName );
        assertEquals( TestHelpers.accessString( device, "on" ), tableName );
        assertEquals( TestHelpers.accessString( device, "is" ), templateName );
        assertEquals( TestHelpers.accessDouble(device, "x"), zero );
        assertEquals( TestHelpers.accessDouble(device, "y"), zero );
        assertEquals( TestHelpers.accessDouble(device, "z"), zero );
        assertEquals( TestHelpers.accessDouble(device, "orientation"), zero );
    }

    @Test
    public void storesOptionalOnAttribute() throws Exception {
        element.removeAttribute( "x" );
        element.removeAttribute( "y" );
        element.removeAttribute( "z" );
        element.setAttribute("on", tableName);

        Device device = new Device( element );

        assertEquals( TestHelpers.accessString( device, "id" ), deviceName );
        assertEquals( TestHelpers.accessString( device, "on" ), tableName );
        assertEquals( TestHelpers.accessString( device, "is" ), templateName );
        assertEquals( TestHelpers.accessDouble(device, "x"), zero );
        assertEquals( TestHelpers.accessDouble(device, "y"), zero );
        assertEquals( TestHelpers.accessDouble(device, "z"), zero );
        assertEquals( TestHelpers.accessDouble(device, "orientation"), zero );
    }

    @Test
    public void storesOptionalCoordinateAttributes() throws Exception {
        element.removeAttribute( "on" );
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());
        element.setAttribute("z", z.toString() );

        Device device = new Device( element );

        assertEquals( TestHelpers.accessString( device, "id" ), deviceName );
        assertEquals( TestHelpers.accessString( device, "on" ), "" );
        assertEquals( TestHelpers.accessString( device, "is" ), templateName );
        assertEquals( TestHelpers.accessDouble(device, "x"), x );
        assertEquals( TestHelpers.accessDouble(device, "y"), y );
        assertEquals( TestHelpers.accessDouble(device, "z"), z );
        assertEquals( TestHelpers.accessDouble(device, "orientation"), zero );
    }

    @Test
    public void storesOptionalOrientationAttribute() throws Exception {
        element.removeAttribute( "on" );
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString() );
        element.setAttribute("z", z.toString() );
        element.setAttribute("orientation", orientation.toString() );

        Device device = new Device( element );

        assertEquals( TestHelpers.accessString( device, "id" ), deviceName );
        assertEquals( TestHelpers.accessString( device, "on" ), "" );
        assertEquals( TestHelpers.accessString( device, "is" ), templateName );
        assertEquals( TestHelpers.accessDouble(device, "x"), x );
        assertEquals( TestHelpers.accessDouble(device, "y"), y );
        assertEquals( TestHelpers.accessDouble(device, "z"), z );
        assertEquals( TestHelpers.accessDouble(device, "orientation"), orientation );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Device instance is missing required 'id' attribute.")
    public void noId() throws Exception {
        element.removeAttribute("id");
        new Device(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Device \\(" + deviceName + "\\) " +
                    "needs either the 'on' attribute or the set of x, y, and z coordinates.")
    public void noX() throws Exception {
        element.removeAttribute( "on" );
        element.removeAttribute( "y" );
        element.removeAttribute( "z" );
        new Device(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Device \\(" + deviceName + "\\) " +
                    "needs either the 'on' attribute or the set of x, y, and z coordinates.")
    public void noY() throws Exception {
        element.removeAttribute( "on" );
        element.removeAttribute( "x" );
        element.removeAttribute( "z" );
        new Device(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Device \\(" + deviceName + "\\) " +
                    "needs either the 'on' attribute or the set of x, y, and z coordinates.")
    public void noZ() throws Exception {
        element.removeAttribute( "on" );
        element.removeAttribute( "x" );
        element.removeAttribute( "y" );
        new Device(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Device \\("+deviceName+"\\) is missing required 'is' attribute.")
    public void noIs() throws Exception {
        element.removeAttribute("is");
        new Device(element);
    }

    @Test
    public void validIs() throws Exception {
        new Table( tableElement );
        new DeviceTemplate(templateElement);
        Device device = new Device(element);
        device.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class, expectedExceptionsMessageRegExp =
            "Device \\("+deviceName+"\\) 'is' reference \\("+templateName+"\\) does not exist.")
    public void invalidIs() throws Exception {
        Device device = new Device(element);
        device.verify();
    }

    @Test
    public void validOn() throws Exception {
        new Table( tableElement );
        new DeviceTemplate(templateElement);
        Device device = new Device(element);
        device.verify();
    }

    @Test(expectedExceptions = InvalidXMLException.class, expectedExceptionsMessageRegExp =
            "Device \\("+deviceName+"\\) 'on' reference \\("+tableName+"\\) does not exist.")
    public void invalidOn() throws Exception {
        new DeviceTemplate(templateElement);
        Device device = new Device(element);
        device.verify();
    }

//    @Test(expectedExceptions = InvalidXMLException.class, expectedExceptionsMessageRegExp =
//            "Device \\("+deviceName+"\\) 'on' reference \\("+tableName+"\\) does not exist.")
//    public void invalidCoordinates() throws Exception {
//        element.removeAttribute( "on" );
//        x = - x;
//        element.setAttribute( "x", x.toString() );
//        new DeviceTemplate(templateElement);
//        Device device = new Device(element);
//        device.verify();
//    }

    @Test
    public void validIsOnDefinedAfterDevice() throws Exception {
        Device device = new Device(element);
        new Table( tableElement );
        new DeviceTemplate(templateElement);
        device.verify();
    }

    @Test
    public void storesSelf() throws Exception {
        Device device = new Device( element );

        ArrayList<ElementalLister> thing = ElementalLister.List();

        assert thing.contains( device );
    }

    @Test
    public void recallsNull() {
        assertNull(Device.Select("bogus"));
    }

    @Test
    public void recalls() throws Exception {
        Device device = new Device( element );
        assertSame(Device.Select(deviceName), device);
    }

    @Test
    public void addsToGearList() throws Exception {
        assertEquals( GearList.Check(templateName), (Integer)0 );

        new Device( element );

        assertEquals(GearList.Check(templateName), (Integer) 1);
    }

    @Test
    public void noLayer() throws Exception {
        new Table( tableElement );
        new DeviceTemplate( templateElement );
        Device device = new Device( element );
        device.verify();

        assertNull(device.layer());
        assertEquals( TestHelpers.accessString( device, "color" ), "black" );
    }

    @Test
    public void layer() throws Exception {
        new Table( tableElement );
        new DeviceTemplate( layeredTemplateElement );
        Device device = new Device( layeredElement );
        device.verify();

        assertEquals(device.layer(), layerTag);
    }

    @Test
    public void color() throws Exception {
        new Table( tableElement );
        new DeviceTemplate( layeredTemplateElement );
        Device device = new Device( layeredElement );
        device.verify();

        assertEquals( TestHelpers.accessString( device, "color" ), layerColor);
    }

    @Test
    public void is() throws Exception {
        new Table( tableElement );
        new DeviceTemplate( templateElement );
        Device device = new Device( element );
        device.verify();

        assertEquals( device.is(), templateName );
    }

    @Test
    public void location() throws Exception {
        new Table( tableElement );
        new DeviceTemplate(templateElement);
        Device device = new Device( element );
        device.verify();

        Point place = device.location();

        assertEquals( place.x, tableX );
        assertEquals( place.y, tableY );
        assertEquals( place.z, tableZ + tableHeight );
    }

    @Test
    public void locationOnDevice() throws Exception {
        new Table( tableElement );
        new DeviceTemplate(templateElement);
        Device device = new Device( element );
        new DeviceTemplate( stackedTemplateElement );
        Device stacked = new Device( stackedElement );
        device.verify();
        stacked.verify();

        Point place = stacked.location();

        assertEquals( place.y, tableY + stackedY );
        assertEquals( place.x, tableX + stackedX );
        assertEquals( place.z, tableZ + tableHeight + height );
    }

    @Test
    public void domPlanOn() throws Exception {
        DeviceTemplate deviceTemplate =  new DeviceTemplate(templateElement);
        Table table = new Table( tableElement );

        Draw draw = new Draw();

        draw.establishRoot();
        Device device = new Device( element );
        device.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        device.dom(draw, View.PLAN);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element deviceElement = (Element) groupNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);

        Solid deviceTemplateShape = deviceTemplate.getSolid();
        Point tableLocation = table.thingsOnThis.get(0).point;

        assertEquals(deviceElement.getAttribute("x"), tableLocation.x().toString() );
        assertEquals(deviceElement.getAttribute("y"), tableLocation.y().toString() );
        Double width = deviceTemplateShape.width();
        assertEquals(deviceElement.getAttribute("width"), width.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.\
        Double height = deviceTemplateShape.depth();
        assertEquals(deviceElement.getAttribute("height"), height.toString() );
        assertEquals(deviceElement.getAttribute("fill"), "black" );
        assertEquals(deviceElement.getAttribute("stroke"), "black" );
    }

    @Test
    public void domPlanCoordinates() throws Exception {
        DeviceTemplate deviceTemplate =  new DeviceTemplate(templateElement);

        Draw draw = new Draw();

        draw.establishRoot();
        element.removeAttribute("on");
        Device device = new Device( element );
        device.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        Double expectedX = x - width / 2.0;
        Double expectedY = y - depth / 2.0;

        device.dom(draw, View.PLAN);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element deviceElement = (Element) groupNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);

        Solid deviceTemplateShape = deviceTemplate.getSolid();

        assertEquals(deviceElement.getAttribute("x"), expectedX.toString() );
        assertEquals(deviceElement.getAttribute("y"), expectedY.toString() );
        Double width = deviceTemplateShape.width();
        assertEquals(deviceElement.getAttribute("width"), width.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.\
        Double height = deviceTemplateShape.depth();
        assertEquals(deviceElement.getAttribute("height"), height.toString() );
        assertEquals(deviceElement.getAttribute("fill"), "black");
        assertEquals(deviceElement.getAttribute("stroke"), "black");
    }

    @Test
    public void domPlanCoordinatesRotated90() throws Exception {
        DeviceTemplate deviceTemplate =  new DeviceTemplate(templateElement);

        Draw draw = new Draw();

        draw.establishRoot();
        element.removeAttribute("on");
        element.setAttribute( "orientation", "90" );
        Device device = new Device( element );
        device.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("rect");
        assertEquals(existingGroups.getLength(), 0);

        Double expectedX = x - depth / 2.0;
        Double expectedY = y - width / 2.0;

        device.dom(draw, View.PLAN);

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 1);
        Node groupNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element deviceElement = (Element) groupNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);

        Solid deviceTemplateShape = deviceTemplate.getSolid();

        assertEquals(deviceElement.getAttribute("x"), expectedX.toString() );
        assertEquals(deviceElement.getAttribute("y"), expectedY.toString() );
        // Plot attribute is 'depth'. SVG attribute is 'height'.\
        Double width = deviceTemplateShape.depth();
        assertEquals(deviceElement.getAttribute("width"), width.toString());
        Double height = deviceTemplateShape.width();
        assertEquals(deviceElement.getAttribute("height"), height.toString() );
        assertEquals(deviceElement.getAttribute("fill"), "black");
        assertEquals(deviceElement.getAttribute("stroke"), "black");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
//        TestResets.GearListReset();
        TestResets.ElementalListerReset(); // so that Tables are reset.
//        TestResets.TableThingsReset();
        TestResets.DeviceTemplateReset();
        TestResets.DeviceReset();
        TestResets.StackableReset();

        Element venueElement = new IIOMetadataNode();
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "550");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        Element layerElement = new IIOMetadataNode();
        layerElement.setAttribute("id", layerTag);
        layerElement.setAttribute("name", "Layer Description" );
        layerElement.setAttribute("color", layerColor );
        new UserLayer( layerElement );

        tableElement = new IIOMetadataNode("table");
        tableElement.setAttribute("id", tableName);
        tableElement.setAttribute("width", tableWidth.toString() );
        tableElement.setAttribute("depth", tableDepth.toString() );
        tableElement.setAttribute("height", tableHeight.toString() );
        tableElement.setAttribute("x", tableX.toString() );
        tableElement.setAttribute("y", tableY.toString() );
        tableElement.setAttribute("z", tableZ.toString() );

        templateElement = new IIOMetadataNode("device-template");
        templateElement.setAttribute("type", templateName );
        templateElement.setAttribute("width", width.toString() );
        templateElement.setAttribute("depth", depth.toString() );
        templateElement.setAttribute("height", height.toString() );
        
        stackedTemplateElement = new IIOMetadataNode("device-stackedTemplate");
        stackedTemplateElement.setAttribute("type", stackedTemplateName );
        stackedTemplateElement.setAttribute("width", stackedWidth.toString() );
        stackedTemplateElement.setAttribute("depth", stackedDepth.toString() );
        stackedTemplateElement.setAttribute("height", stackedHeight.toString() );

        layeredTemplateElement = new IIOMetadataNode("device-template");
        layeredTemplateElement.setAttribute("type", layeredTemplateName );
        layeredTemplateElement.setAttribute("width", "7");
        layeredTemplateElement.setAttribute("depth", "8");
        layeredTemplateElement.setAttribute("height", "9");
        layeredTemplateElement.setAttribute("layer", layerTag);

        element = new IIOMetadataNode( "device" );
        element.setAttribute( "id", deviceName );
        element.setAttribute( "on", tableName );
        element.setAttribute( "is", templateName );
        element.setAttribute( "x", x.toString() );
        element.setAttribute( "y", y.toString() );
        element.setAttribute( "z", z.toString() );

        layeredElement = new IIOMetadataNode( "device" );
        layeredElement.setAttribute( "id", deviceName );
        layeredElement.setAttribute( "on", tableName );
        layeredElement.setAttribute( "is", layeredTemplateName );

        stackedElement = new IIOMetadataNode( "device" );
        stackedElement.setAttribute( "id", deviceName );
        stackedElement.setAttribute( "on", tableName );
        stackedElement.setAttribute( "is", stackedTemplateName );
        stackedElement.setAttribute( "x", stackedX.toString() );
        stackedElement.setAttribute("y", stackedY.toString());
        stackedElement.setAttribute("z", stackedZ.toString());
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
