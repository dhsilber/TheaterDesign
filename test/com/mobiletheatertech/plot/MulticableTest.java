package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

/**
 * Created by dhs on 1/8/15.
 */
public class MulticableTest {

    Element element = null;

    String id = "flavor";
    Double startX = 6.7;
    Double startY = 3.6;
    Double startZ = 33.0;
    Double endX = 9.7;
    Double endY = 23.1;
    Double endZ = 1.1;
    String color = "black";


    @Test
    public void isA() throws Exception {
        Multicable instance = new Multicable( id, startX, startY, startZ );

//        assert Schematicable.class.isInstance( instance );
//        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void domDrawsBothEnds() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        UserMulticable user = new UserMulticable( element );
        Multicable start = user.start();
        Multicable end = user.end();

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        start.dom(draw, View.PLAN);
        end.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 3);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);

        NodeList circleList = groupElement.getElementsByTagName("circle");
        assertEquals(circleList.getLength(), 1);
        Node circleNode = circleList.item( 0 );
        assertEquals( circleNode.getNodeType(), Node.ELEMENT_NODE );

        Element circleElement = (Element) circleNode;
//        assertEquals(circleElement.getAttribute("xlink:href"), "#"+LightingStand.TAG);
        Double thisX = new Double( circleElement.getAttribute("cx") );
        assertEquals( thisX, startX - SvgElement.OffsetX() );
        Double thisY = new Double( circleElement.getAttribute("cy") );
        assertEquals( thisY, startY - SvgElement.OffsetY() );
        Double thisR = new Double( circleElement.getAttribute("r") );
        assertEquals( thisR, 5.0 );

        groupNode = group.item(2);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        groupElement = (Element) groupNode;
//        assertEquals(groupElement.getAttribute("class"), LightingStand.TAG);

//        NodeList rectList = groupElement.getElementsByTagName("circle");
        assertEquals(circleList.getLength(), 1);
        Node rectNode = circleList.item( 0 );
        assertEquals( rectNode.getNodeType(), Node.ELEMENT_NODE );

        Element rectElement = (Element) rectNode;
//        assertEquals(rectElement.getAttribute("xlink:href"), "#"+LightingStand.TAG);
        Double thatX = new Double( rectElement.getAttribute("cx") );
        assertEquals( thatX, endX - SvgElement.OffsetX() );
        Double thatY = new Double( rectElement.getAttribute("cy") );
        assertEquals( thatY, endY - SvgElement.OffsetY() );
        Double thatWidth = new Double( rectElement.getAttribute("r") );
        assertEquals( thatWidth, 5.0 );
//        Double thatHeight = new Double( rectElement.getAttribute("height") );
//        assertEquals( thatHeight, 10.0 );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.ElementalListerReset();

        element = new IIOMetadataNode("multicable");
        element.setAttribute("id", id);
        element.setAttribute("start-x", startX.toString() );
        element.setAttribute("start-y", startY.toString());
        element.setAttribute("start-z", startZ.toString() );
        element.setAttribute("end-x", endX.toString() );
        element.setAttribute("end-y", endY.toString() );
        element.setAttribute("end-z", endZ.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
