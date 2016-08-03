package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 12/8/14.
 */
public class DanceTileTest {

    Element element;
    Double startX = 2.5;
    Double endX = 42.5;
    Double startY = 15.0;
    Double endY = 90.6;

    @Test
    public void isA() throws Exception {
        DanceTile instance = new DanceTile( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        DanceTile danceTile = new DanceTile( element );

        assertEquals( TestHelpers.accessDouble( danceTile, "startX"), startX );
        assertEquals( TestHelpers.accessDouble( danceTile, "endX" ), endX );
        assertEquals( TestHelpers.accessDouble( danceTile, "startY"), startY );
        assertEquals( TestHelpers.accessDouble( danceTile, "endY"), endY );
    }

    @Test
    public void size() throws Exception {
        DanceTile danceTile = new DanceTile( element );
        assertEquals( danceTile.Size, 36.0 );
    }

    @Test
    public void counts() throws Exception {
        DanceTile danceTile = new DanceTile( element );

        assertEquals( TestHelpers.accessInteger(danceTile, "Count"), (Integer) 0 );

        Draw draw = new Draw();
        draw.establishRoot();
        danceTile.dom( draw, View.PLAN );

        assertEquals( TestHelpers.accessInteger(danceTile, "Count"), (Integer) 2 );
    }

    @Test
    public void countsMore() throws Exception {
        element.setAttribute( "endX", "110.8");
        DanceTile danceTile = new DanceTile( element );

        assertEquals( TestHelpers.accessInteger( danceTile, "Count" ), (Integer) 0 );

        Draw draw = new Draw();
        draw.establishRoot();
        danceTile.dom( draw, View.PLAN );

        assertEquals( TestHelpers.accessInteger( danceTile, "Count" ), (Integer) 6 );
    }

    @Test
    public void domPlanCoordinates() throws Exception {
        DanceTile danceTile = new DanceTile( element );

        Draw draw = new Draw();
        draw.establishRoot();
        danceTile.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        Double expectedX = startX;
        Double expectedY = startY;

        danceTile.dom(draw, View.PLAN);

        NodeList newGroups = draw.root().getElementsByTagName("g");
        assertEquals( newGroups.getLength(), 2);
        Node groupNode = newGroups.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;

        NodeList rectangles = draw.root().getElementsByTagName("rect");
        assertEquals(rectangles.getLength(), 3);
        Node rectanglesNode = rectangles.item(0);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element firstTile = (Element) rectanglesNode;
//        assertEquals(tableElement.attribute("class"), Table.LAYERTAG);

//        Solid deviceTemplateShape = deviceTemplate.getSolid();

        assertEquals(firstTile.getAttribute("x"), expectedX.toString() );
        assertEquals(firstTile.getAttribute("y"), expectedY.toString() );
//        Double width = deviceTemplateShape.width();
//        assertEquals(deviceElement.getAttribute("width"), width.toString() );
//        // Plot attribute is 'depth'. SVG attribute is 'height'.\
//        Double height = deviceTemplateShape.depth();
//        assertEquals(deviceElement.getAttribute("height"), height.toString() );
//        assertEquals(deviceElement.getAttribute("fill"), "black");
//        assertEquals(deviceElement.getAttribute("stroke"), "black");


        rectanglesNode = rectangles.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        firstTile = (Element) rectanglesNode;
        expectedY += 36;
        assertEquals(firstTile.getAttribute("x"), expectedX.toString() );
        assertEquals(firstTile.getAttribute("y"), expectedY.toString() );


        Double borderX = startX - 12;
        Double borderY = startY - 12;
        Double borderWidth = 60.0;
        Double borderHeight = 96.0;
        rectanglesNode = rectangles.item(2);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        firstTile = (Element) rectanglesNode;
        assertEquals(firstTile.getAttribute("x"), borderX.toString() );
        assertEquals(firstTile.getAttribute("y"), borderY.toString() );
        assertEquals(firstTile.getAttribute("width"), borderWidth.toString() );
        assertEquals(firstTile.getAttribute("height"), borderHeight.toString() );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        DanceTile.CountReset();
        Venue.Reset();

        element = new IIOMetadataNode( "dancetile" );
        element.setAttribute( "startX", startX.toString() );
        element.setAttribute( "startY", startY.toString() );
        element.setAttribute( "endX", endX.toString() );
        element.setAttribute( "endY", endY.toString() );

    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
