package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
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

    
    Element circleElement= null;
    Double circleRadius = 2.3;
    
    String tallTriangle = "0 -20 4 20 -4 20";
    String tallTrianglePath = "M 76.7 73.6 80.7 113.6 72.7 113.6 Z";
//    String wideTriangle = "-20 0 20 -4 20 4";
    
//    String wide = "-7 -12 7 -12 11 12 -11 12";
    

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.YokeableReset();
        SvgElement.Offset(0.0, 0.0);
//        TestResets.SetPlatformReset();
//        Schematic.CountX = 0;
//        Schematic.CountY = 1;
        
        circleElement = new IIOMetadataNode( Shape.Tag );
        circleElement.setAttribute( "circle", circleRadius.toString() );

        element = new IIOMetadataNode( SetPlatform.Tag() );
//        element.setAttribute("id", id);
        element.setAttribute("x", x.toString() );
        element.setAttribute("y", y.toString() );

        element2 = new IIOMetadataNode( SetPlatform.Tag() );
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

    @Test
    public void constantTag() {
        assertEquals( SetPlatform.Tag(), "set-platform" );
    }

    @Test
    public void constantColor() {
        assertEquals( SetPlatform.Color(), "green" );
    }

    @Test
    public void isA() throws Exception {
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );
        assertTrue( Populate.class.isInstance( instance ) );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), 0.0 );
    }

    @Test
    public void storesOptionalOrientationAttribute() throws Exception {
        element.appendChild( circleElement );
        element.setAttribute("orientation", orientation.toString());
        SetPlatform instance = new SetPlatform( element );

        assertEquals(TestHelpers.accessDouble(instance, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(instance, "orientation"), orientation.doubleValue() );
    }

    @Test
    public void tagCallbackRegisteredShape() {
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        assertTrue( instance.tags().contains( Shape.Tag ) );
        assertEquals( instance.tags().size(), 1 );
    }

    @Test
    public void populateChildrenShapeStored() {
        element.appendChild( circleElement );
        SetPlatform platform = new SetPlatform( element );

        assertEquals( platform.shapes().size(), 1 );
    }

    @Test
    public void populateChildrenShapesNotListed() {
        element.appendChild( circleElement );
        new SetPlatform( element );

        ArrayList<ElementalLister> list = ElementalLister.List();

        ElementalLister paltform = list.get( 0 );
        assert MinderDom.class.isInstance( paltform );
        assert SetPlatform.class.isInstance( paltform );

        assertEquals( list.size(), 1 );
    }

    @Test
    public void storePolygon() throws Exception {
        Element polygonElement = new IIOMetadataNode( "shape" );
        polygonElement.setAttribute( "polygon", polygon );
        element.appendChild( polygonElement );

        draw.establishRoot();

        SetPlatform instance = new SetPlatform( element );

        assertEquals( instance.shapes().size(), 1 );
    }

    @Test( expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "SetPlatform has no Shape." )
    public void noShape() {
        new SetPlatform( element );
    }

    @Test
    public void domPlan() throws Exception {
        draw.establishRoot();
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute("class"), SetPlatform.Tag() );
        assertEquals( groupElement.getAttribute("stroke"), SetPlatform.Color() );

        NodeList list = groupElement.getElementsByTagName("circle");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals( element.getAttribute("cx"), x.toString() );
        assertEquals( element.getAttribute("cy"), y.toString() );
        assertEquals( element.getAttribute("r"), circleRadius.toString() );
        assertEquals( element.getAttribute("stroke"), SetPlatform.Color() );
    }

    @Test
    public void domPlanTwice() throws Exception {
        Double largerRadius = circleRadius * 2;
        Element secondCircleElement = new IIOMetadataNode( Shape.Tag );
        secondCircleElement.setAttribute( "circle", largerRadius.toString() );
        element.appendChild( secondCircleElement );
        draw.establishRoot();
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute("class"), SetPlatform.Tag() );

        NodeList list = groupElement.getElementsByTagName("circle");

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals( element.getAttribute("cx"), x.toString() );
        assertEquals( element.getAttribute("cy"), y.toString() );
        assertEquals( element.getAttribute("r"), largerRadius.toString() );

        node = list.item( 1 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        element = (Element) node;
        assertEquals( element.getAttribute("cx"), x.toString() );
        assertEquals( element.getAttribute("cy"), y.toString() );
        assertEquals( element.getAttribute("r"), circleRadius.toString() );

        assertEquals(list.getLength(), 2 );
    }

    @Test
    public void domPlanProscenium() throws Exception {
        new Proscenium( prosceniumElement );

//        Element polygonElement = new IIOMetadataNode( "shape" );
//        polygonElement.setAttribute( "polygon", polygon );
//        element.appendChild( polygonElement );

        draw.establishRoot();
        element.appendChild( circleElement );
        SetPlatform instance = new SetPlatform( element );

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        instance.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals( groupElement.getAttribute("class"), SetPlatform.Tag() );

        NodeList list = groupElement.getElementsByTagName("circle");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        Double ex = Proscenium.Origin().x() + x;
        Double wy = Proscenium.Origin().y() - y;
        assertEquals( element.getAttribute("cx"), ex.toString() );
        assertEquals( element.getAttribute("cy"), wy.toString() );
        assertEquals( element.getAttribute("r"), circleRadius.toString() );
    }

    @Test
    public void domPlanRotated() throws Exception {
//        SvgElement.Offset(3.8, 9.2);

        Element polygonElement = new IIOMetadataNode( "shape" );
        polygonElement.setAttribute( "polygon", tallTriangle );
        element.appendChild( polygonElement );

        element.setAttribute("orientation", orientation.toString());
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
        assertEquals( groupElement.getAttribute("class"), SetPlatform.Tag() );

        NodeList list = groupElement.getElementsByTagName("path");
        assertEquals(list.getLength(), 1);

        Node node = list.item( 0 );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
//        assertEquals( element.getAttribute("x"), x.toString() );
//        assertEquals( element.getAttribute("y"), y.toString() );
        assertEquals( element.getAttribute("d"), tallTrianglePath );

        Double thisX = x+SvgElement.OffsetX();
        Double thisY = y+SvgElement.OffsetY();
        assertEquals(element.getAttribute("transform"),
                "rotate(-45.0,"+thisX+","+thisY+")");
    }

//    @Test
//    public void domSection() throws Exception {
//        draw.establishRoot();
//        element.appendChild( circleElement );
//        SetPlatform instance = new SetPlatform( element );
//
//        NodeList existingGroups = draw.root().getElementsByTagName("g");
//        assertEquals(existingGroups.getLength(), 1);
//
////        instance.dom(draw, View.SECTION);
//
//        NodeList group = draw.root().getElementsByTagName("g");
//        assertEquals(group.getLength(), 1);
//    }
//
//    @Test
//    public void domFront() throws Exception {
//        draw.establishRoot();
//        element.appendChild( circleElement );
//        SetPlatform instance = new SetPlatform( element );
//
//        NodeList existingGroups = draw.root().getElementsByTagName("g");
//        assertEquals(existingGroups.getLength(), 1);
//
////        instance.dom(draw, View.FRONT);
//
//        NodeList group = draw.root().getElementsByTagName("g");
//        assertEquals(group.getLength(), 1);
//    }

//    @Test
//    public void parse() throws Exception {
//        String xml = "<plot>" +
//                "<set-platform x=\"" + x +
//                "\" y=\"" + y +
//                "\" >" +
//                    "<shape polygon=\"" + path +
//                    "\" />" +
//                "</set-platform>" +
//                "</plot>";
//        InputStream stream = new ByteArrayInputStream(xml.getBytes());
//
//        ArrayList<ElementalLister> list = ElementalLister.List();
//        assertEquals( list.size(), 0 );
//
//        new Parse(stream);
//
//        assertEquals( list.size(), 1 );
//    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}
