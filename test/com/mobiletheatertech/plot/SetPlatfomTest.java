package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by dhs on 1/21/15.
 */
public class SetPlatfomTest {

    Element element = null;
    Element element2 = null;

    Element prosceniumElement = null;

    final String id = "Lighitng Stand ID";
    String id2 = "Other Lighitng Stand ID";
    Draw draw;
    Double x = 76.7;
    Double y = 93.6;
    Double orientation = -45.0;
    String polygon = "-12 17 -8 20 8 20 12 17 14 6 14 -6 12 -17 8 -20 -8 -20 -12 -17 -14 -6 -14 6 ";
    String path = "M 64.7 110.6 68.7 113.6 84.7 113.6 88.7 110.6 90.7 99.6 90.7 87.6 88.7 76.6 84.7 73.6 68.7 73.6 64.7 76.6 62.7 87.6 62.7 99.6 Z";


    @Test
    public void isA() throws Exception {
        SetPlatform instance = new SetPlatform( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        SetPlatform instance = new SetPlatform( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), 0.0 );
    }

    @Test
    public void storesOptionalOrientationAttribute() throws Exception {
        element.setAttribute("orientation", orientation.toString());
        SetPlatform instance = new SetPlatform( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), orientation.doubleValue() );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "SetPlatform \\("+id+"\\) location must specify a letter in the range of 'a' to 'd'.")
    public void noLocation() throws Exception {
        SetPlatform instance = new SetPlatform( element );
//        instance.verify();
//
//        instance.location("");
    }

    @Test
    public void location() throws Exception {
        SetPlatform instance = new SetPlatform( element );
//        instance.verify();
//
//        Point point = instance.location( "a" );
//        assertEquals(point, new Point(x + 18, y, 144.0));
    }

    @Test
    public void rotatedLocation() throws Exception {
        SvgElement.Offset( 9.9, 1.1 );
        element.setAttribute("orientation", orientation.toString());
        SetPlatform instance = new SetPlatform( element );
//        instance.verify();
//
//        Place place = instance.rotatedLocation("d");
//        assertEquals( place.rotation(), orientation );
//        assertEquals( place.location(), new Point( x - 18, y, 144.0 ));
//        assertEquals( place.origin(), new Point( x + SvgElement.OffsetX(), y + SvgElement.OffsetY(), 144.0 ));
    }

//    Then test the legend stuff - register and uses symbol to draw

    @Test
    public void storePolygon() throws Exception {
        Element polygonElement = new IIOMetadataNode( "shape" );
        polygonElement.setAttribute( "polygon", polygon );
        element.appendChild( polygonElement );

        draw.establishRoot();

        SetPlatform instance = new SetPlatform( element );

        Field polygonField = TestHelpers.accessField( instance, "Polygons" );
        ArrayList<Shape> polygons = (ArrayList<Shape>) polygonField.get( instance );

        assertEquals( polygons.size(), 1 );
    }

    @Test
    public void domNotProscenium() throws Exception {
        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void domPlan() throws Exception {
        new Proscenium( prosceniumElement );

        Element polygonElement = new IIOMetadataNode( "shape" );
        polygonElement.setAttribute( "polygon", polygon );
        element.appendChild( polygonElement );

        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), SetPlatform.TAG);

        NodeList list = groupElement.getElementsByTagName("path");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        String foundPath = new String( element.getAttribute("d") );
        assertEquals( foundPath, path );
    }

    @Test
    public void domPlanRotated() throws Exception {
        SvgElement.Offset(3.8, 9.2);
        element.setAttribute("orientation", orientation.toString());
        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

//        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), SetPlatform.TAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
//        assertEquals(element.getAttribute("xlink:href"), "#"+SetPlatform.TAG);
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, x + SvgElement.OffsetX() );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, y + SvgElement.OffsetY() );
        thisX = x+SvgElement.OffsetX();
        thisY = y+SvgElement.OffsetY();
        assertEquals(element.getAttribute("transform"), "rotate(-45.0,"+thisX+","+thisY+")");
    }

    @Test
    public void domTruss() throws Exception {
        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

//        instance.dom(draw, View.TRUSS);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void domSection() throws Exception {
        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

//        instance.dom(draw, View.SECTION);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void domFront() throws Exception {
        draw.establishRoot();
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

//        instance.dom(draw, View.FRONT);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 1);
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<set-platform x=\"" + x +
                "\" y=\"" + y +
                "\" >" +
                    "<shape polygon=\"" + path +
                    "\" />" +
                "</set-platform>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream(xml.getBytes());

        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 0 );

//        new Parse(stream);

        assertEquals( list.size(), 1 );
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.MountableReset();
        SvgElement.Offset(0.0, 0.0);
//        TestResets.SetPlatformReset();
//        Schematic.CountX = 0;
//        Schematic.CountY = 1;

        element = new IIOMetadataNode("set-platform");
//        element.setAttribute("id", id);
        element.setAttribute("x", x.toString() );
        element.setAttribute("y", y.toString() );

        element2 = new IIOMetadataNode("set-platform");
//        element2.setAttribute("id", id2 );
        element2.setAttribute("x", x.toString() );
        element2.setAttribute("y", y.toString() );




        TestResets.ProsceniumReset();

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", "550" );
        venueElement.setAttribute( "depth", "400" );
        venueElement.setAttribute( "height", "263" );
        new Venue( venueElement );

        Double width = 330.0;
        Double depth = 22.0;
        Double height = 250.0;
        Double x = 250.0;
        Double y = 144.0;
        Double z = 12.0;

        prosceniumElement = new IIOMetadataNode( "proscenium" );
        prosceniumElement.setAttribute( "width", width.toString() );
        prosceniumElement.setAttribute( "height", height.toString() );
        prosceniumElement.setAttribute( "depth", depth.toString() );
        prosceniumElement.setAttribute( "x", x.toString() );
        prosceniumElement.setAttribute( "y", y.toString() );
        prosceniumElement.setAttribute( "z", z.toString() );

        
        

        draw = new Draw();



        TestResets.ElementalListerReset();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
