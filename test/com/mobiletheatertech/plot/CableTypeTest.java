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
import static org.testng.Assert.assertSame;

/**
 * Created by dhs on 1/6/15.
 */
public class CableTypeTest {

    Element element = null;
    String color = "Blurple";
    String id = "type";

    @Test
    public void isA() throws Exception {
        CableType instance = new CableType( element );

        assert Elemental.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        CableType instance = new CableType( element );

        assertEquals(TestHelpers.accessString(instance, "id"), id );
        assertEquals(TestHelpers.accessString(instance, "schematicColor"), color );
    }

    @Test
    public void color() throws Exception {
        CableType instance = new CableType( element );

        assertEquals( instance.color(), color );
    }

    @Test
    public void storesSelf() throws Exception {
        CableType instance = new CableType( element );

        assertSame( CableType.Select( id ), instance );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<cable-type id=\""+id+"\" schematic-color=\""+color+"\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestResets.MinderDomReset();

        new Parse( stream );

        ArrayList<CableType> list = (ArrayList<CableType>)
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.CableType", "CABLETYPELIST");
        assertEquals( list.size(), 1 );
    }

    @Test
    public void domLegendItem() throws Exception {
        // Setup
        Draw draw = new Draw();
        draw.establishRoot();
        CableType run = new CableType(element);
        PagePoint startPoint = new PagePoint( 20.0, 10.0 );

        // Confirmation
        NodeList preLine = draw.root().getElementsByTagName( "line" );
        assertEquals( preLine.getLength(), 0 );
        NodeList preText = draw.root().getElementsByTagName( "text" );
        assertEquals( preText.getLength(), 0 );

        // The main event
        PagePoint endPoint = run.domLegendItem( draw, startPoint );

        // Checking the result
        NodeList lineList = draw.root().getElementsByTagName( "line" );
        assertEquals( lineList.getLength(), 1 );
        NodeList textList = draw.root().getElementsByTagName( "text" );
        assertEquals( textList.getLength(), 1 );

        Node useNode = lineList.item(0);
        assertEquals(useNode.getNodeType(), Node.ELEMENT_NODE);
        Element lineElement = (Element) useNode;
        Double endX = startPoint.x() + 12;
        assertEquals(lineElement.getAttribute("x1"), startPoint.x().toString() );
        assertEquals(lineElement.getAttribute("y1"), startPoint.y().toString());
        assertEquals(lineElement.getAttribute("x2"), endX.toString() );
        assertEquals(lineElement.getAttribute("y2"), startPoint.y().toString());
        assertEquals(lineElement.getAttribute("stroke"), color );
//        assertEquals(element.getAttribute("stroke-width"), "1" );

        Node textNode = textList.item(0);
        assertEquals(textNode.getNodeType(), Node.ELEMENT_NODE);
        Element textElement = (Element) textNode;
        Double x = startPoint.x() + Legend.TEXTOFFSET;
        Double y = startPoint.y() + 3;
        assertEquals(textElement.getAttribute("x"), x.toString() );
        assertEquals(textElement.getAttribute("y"), y.toString() );
        assertEquals(textElement.getAttribute("fill"), "black" );

        // TODO Check for text here

        assertEquals( endPoint, new PagePoint( startPoint.x(), startPoint.y() + 7 ));
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.CableTypeReset();

        element = new IIOMetadataNode("cable-type");
        element.setAttribute("id", id);
        element.setAttribute("schematic-color", color );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
