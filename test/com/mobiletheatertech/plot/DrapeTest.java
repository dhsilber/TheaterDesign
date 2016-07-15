package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import scala.Int;
import scala.collection.immutable.List;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/30/13 Time: 4:01 AM To change this template use
 * File | Settings | File Templates.
 */
public class DrapeTest {

    Element element = null;
    Element drapeBase1Element = null;
    Element drapeBase2Element = null;
    Element drapeBase3Element = null;

    Double firstX = 1.0;
    Double firstY = 2.0;
    Double secondX = 3.0;
    Double secondY = 4.0;
    Double thirdX = 5.0;
    Double thirdY = 6.0;

    @Test
    public void isA() throws Exception {
        Drape instance = new Drape( element );

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        Drape drape = new Drape( element );
    }

    @Test
    public void storeOptionalsAttributes() throws Exception {
        Drape drape = new Drape( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Drape must have at least two drapebase children")
    public void constructorNoDrapeBases() throws Exception {
        element.removeChild( drapeBase1Element );
        element.removeChild( drapeBase2Element );
        Drape drape = new Drape( element );
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp = "Drape must have at least two drapebase children")
    public void constructorTooFewDrapeBases() throws Exception {
        element.removeChild( drapeBase1Element );
        Drape drape = new Drape( element );
    }

    @Test
    public void hasTwoDrapeBases() throws Exception {
        Drape drape = new Drape( element );

        List gearList = drape.gear();
        assertEquals( gearList.length(), 2 );
    }

    @Test
    public void hasThreefDrapeBases() throws Exception {
        element.appendChild( drapeBase3Element );
        Drape drape = new Drape( element );

        List gearList = drape.gear();
        assertEquals( gearList.length(), 3 );
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<drape  >" +
                "<drapebase  x=\"5\" y=\"6\" />" +
                "<drapebase  x=\"7\" y=\"8\" />" +
                "</drape>" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

//        TestHelpers.MinderDomReset();

        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 3 );

        ElementalLister Drape = list.get( 0 );
        assert MinderDom.class.isInstance( Drape );
        assert Drape.class.isInstance( Drape );

        ElementalLister DrapeBase = list.get( 1 );
        assert MinderDom.class.isInstance( DrapeBase );
        assert DrapeBase.class.isInstance( DrapeBase );

        ElementalLister DrapeBase2 = list.get( 2 );
        assert MinderDom.class.isInstance( DrapeBase2 );
        assert DrapeBase.class.isInstance( DrapeBase2 );
    }

    @Test
    public void domPlantwo() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        element.appendChild( drapeBase3Element );
        Drape drape = new Drape( element );
        drape.verify();

        drape.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Drape$.MODULE$.LayerTag() );

        NodeList list = groupElement.getElementsByTagName("line");
        assertEquals(list.getLength(), 2);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), firstX.toString());
        assertEquals(element.getAttribute("y1"), firstY.toString());
        assertEquals(element.getAttribute("x2"), secondX.toString());
        assertEquals(element.getAttribute("y2"), secondY.toString());
        Node node1 = list.item(1);
        assertEquals(node1.getNodeType(), Node.ELEMENT_NODE);
        Element element1 = (Element) node1;
        assertEquals(element1.getAttribute("x1"), secondX.toString());
        assertEquals(element1.getAttribute("y1"), secondY.toString());
        assertEquals(element1.getAttribute("x2"), thirdX.toString());
        assertEquals(element1.getAttribute("y2"), thirdY.toString());
    }

    @Test
    public void domPlan() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        Drape drape = new Drape( element );
        drape.verify();

        drape.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Drape$.MODULE$.LayerTag() );

        NodeList list = groupElement.getElementsByTagName("line");
        assertEquals(list.getLength(), 1);
        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("x1"), firstX.toString());
        assertEquals(element.getAttribute("y1"), firstY.toString());
        assertEquals(element.getAttribute("x2"), secondX.toString());
        assertEquals(element.getAttribute("y2"), secondY.toString());
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        TestResets.MinderDomReset();

//        Element venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "room", "Test Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        new Venue( venueElement );

        drapeBase1Element = new IIOMetadataNode( "drapebase" );
        drapeBase1Element.setAttribute( "x", firstX.toString() );
        drapeBase1Element.setAttribute( "y", firstY.toString() );

        drapeBase2Element = new IIOMetadataNode( "drapebase" );
        drapeBase2Element.setAttribute( "x", secondX.toString() );
        drapeBase2Element.setAttribute( "y", secondY.toString() );

        drapeBase3Element = new IIOMetadataNode( "drapebase" );
        drapeBase3Element.setAttribute( "x", thirdX.toString() );
        drapeBase3Element.setAttribute( "y", thirdY.toString() );

        element = new IIOMetadataNode("truss");
        element.appendChild( drapeBase1Element );
        element.appendChild( drapeBase2Element );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
