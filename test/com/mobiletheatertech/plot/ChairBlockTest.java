package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;
import java.lang.reflect.Field;
import java.util.HashMap;

import static org.testng.Assert.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/17/13 Time: 11:43 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.9
 */
public class ChairBlockTest {

    Draw draw = null;
    Element element = null;
    Element perimeterElement = null;
    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final Integer FOOTSPACE = 11;
    private static final Integer X = 20;
    private static final Integer Y = 40;
    private static final Integer WIDTH = 100;
    private static final Integer DEPTH = 120;
    private static final Integer ZERO = 0;
    //    private static final String perimeter = "M 200 300 L 180 280";
    private static final String PERIMETER = "200 300 180 280 900 400";

    @Test
    public void isMinderDom() throws Exception {
        ChairBlock chairBlock = new ChairBlock(element);

        assert MinderDom.class.isInstance(chairBlock);
    }

    @Test
    public void storesAttributesLocation() throws Exception {
        ChairBlock chairBlock = new ChairBlock(element);

        assertEquals(TestHelpers.accessInteger(chairBlock, "x"), X);
        assertEquals(TestHelpers.accessInteger(chairBlock, "y"), Y);
        assertEquals(TestHelpers.accessInteger(chairBlock, "width"), WIDTH);
        assertEquals(TestHelpers.accessInteger(chairBlock, "depth"), DEPTH);
        assertEquals(TestHelpers.accessString(chairBlock, "perimeter"), "");
    }

    @Test
    public void storesAttributesperimeter() throws Exception {
        ChairBlock chairBlock = new ChairBlock(perimeterElement);

        assertEquals(TestHelpers.accessInteger(chairBlock, "x"), ZERO);
        assertEquals(TestHelpers.accessInteger(chairBlock, "y"), ZERO);
        assertEquals(TestHelpers.accessInteger(chairBlock, "width"), ZERO);
        assertEquals(TestHelpers.accessInteger(chairBlock, "depth"), ZERO);
        assertEquals(TestHelpers.accessString(chairBlock, "perimeter"), PERIMETER);
    }

    @Test
    public void storesPerimeterShape() throws Exception {
        ChairBlock chairBlock = new ChairBlock(perimeterElement);

        Field shapeField = TestHelpers.accessField(chairBlock, "perimeterShape");
        Shape shape = (Shape) shapeField.get(chairBlock);
        assertNotNull(shape);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.")
    public void muddledXML() throws Exception {
        element.setAttribute("perimeter", PERIMETER);
        new ChairBlock(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.")
    public void missingX() throws Exception {
        element.removeAttribute("x");
        new ChairBlock(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.")
    public void missingY() throws Exception {
        element.removeAttribute("y");
        new ChairBlock(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.")
    public void missingWidth() throws Exception {
        element.removeAttribute("width");
        new ChairBlock(element);
    }

    @Test(expectedExceptions = InvalidXMLException.class,
            expectedExceptionsMessageRegExp =
                    "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.")
    public void missingDepth() throws Exception {
        element.removeAttribute("depth");
        new ChairBlock(element);
    }

    @Test
    public void registersLayer() throws Exception {
        // Sanity check:
        HashMap<String, String> layers = Layer.List();
        assertFalse(layers.containsKey(ChairBlock.LAYERNAME));

        ChairBlock chairBlock = new ChairBlock(element);

        layers = Layer.List();
        assertTrue(layers.containsKey(ChairBlock.LAYERNAME));
        assertEquals(layers.get(ChairBlock.LAYERNAME), ChairBlock.LAYERTAG);
    }

    @Test
    public void muddledXMLNoLayer() throws Exception {
        // Sanity check:
        HashMap<String, String> layers = Layer.List();
        assertFalse(layers.containsKey(ChairBlock.LAYERNAME));

        element.setAttribute("perimeter", PERIMETER);
        try {
            new ChairBlock(element);
        } catch (Exception e) {
        }

        layers = Layer.List();
        assertFalse(layers.containsKey(ChairBlock.LAYERNAME));
    }

    @Test
    public void hasChairWidthSet() throws Exception {
        Object chairWidthObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.ChairBlock",
                        "CHAIRWIDTH");
        assertNotNull(chairWidthObject);
        Integer chairWidth = (Integer) chairWidthObject;

        assertEquals(chairWidth, CHAIRWIDTH);
    }

    @Test
    public void hasChairDepthSet() throws Exception {
        Object chairDepthObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.ChairBlock",
                        "CHAIRDEPTH");
        assertNotNull(chairDepthObject);
        Integer chairDepth = (Integer) chairDepthObject;

        assertEquals(chairDepth, CHAIRDEPTH);
    }

    private void domCheckSymbolGeneration() throws Exception {
        NodeList defsList = draw.root().getElementsByTagName("defs");
        assertEquals(defsList.getLength(), 2);
        /* 0th element is the empty defs tag generated by Batik - why? */
        Node defsNode = defsList.item(1);
        assertEquals(defsNode.getNodeType(), Node.ELEMENT_NODE);
        Element defsElement = (Element) defsNode;
        assertEquals(defsElement.getAttribute("id"), "");

        NodeList list = draw.root().getElementsByTagName("symbol");
        assertEquals(list.getLength(), 1);

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("id"), "chair");
    }

    @Test
    public void domFirstGeneratesSymbol() throws Exception {
        draw.getRoot();

        ChairBlock chairBlock = new ChairBlock(element);
        chairBlock.dom(draw, View.PLAN);

        domCheckSymbolGeneration();
    }

    @Test
    public void domSecondDoesNothingMore() throws Exception {
        draw.getRoot();

        ChairBlock chairBlock1 = new ChairBlock(element);
        chairBlock1.dom(draw, View.PLAN);
        ChairBlock chairBlock2 = new ChairBlock(element);
        chairBlock2.dom(draw, View.PLAN);

        domCheckSymbolGeneration();

//        NodeList defsList = draw.root().getElementsByTagName( "defs" );
//        assertEquals( defsList.getLength(), 1 );
//
//        NodeList list = draw.root().getElementsByTagName( "symbol" );
//        assertEquals( list.getLength(), 1 );

    }

    @Test
    public void domPlan() throws Exception {
        draw.getRoot();
        ChairBlock chairBlock = new ChairBlock(element);

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chairBlock.dom(draw, View.PLAN);


//        NodeList createdGroups = draw.root().getElementsByTagName( "g" );
//        assertEquals( createdGroups.getLength(), 2 );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), ChairBlock.LAYERTAG);

        int count = (WIDTH / CHAIRWIDTH) * (DEPTH / (CHAIRDEPTH + FOOTSPACE));
        String expectedX = Integer.toString(X + CHAIRWIDTH / 2 + 2);
        String expectedY = Integer.toString(Y + CHAIRDEPTH / 2 + FOOTSPACE);
        assert (count > 0);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), count);

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#chair");
        assertEquals(element.getAttribute("x"), expectedX);
        assertEquals(element.getAttribute("y"), expectedY);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        Element venueElement = new IIOMetadataNode("venue");
        venueElement.setAttribute("room", "Test Name");
        venueElement.setAttribute("width", "350");
        venueElement.setAttribute("depth", "400");
        venueElement.setAttribute("height", "240");
        new Venue(venueElement);

        element = new IIOMetadataNode("chairblock");
        element.setAttribute("x", X.toString());
        element.setAttribute("y", Y.toString());
        element.setAttribute("width", WIDTH.toString());
        element.setAttribute("depth", DEPTH.toString());

        perimeterElement = new IIOMetadataNode("chairblock");
        perimeterElement.setAttribute("perimeter", PERIMETER);

        TestResets.ChairBlockReset();
        TestResets.LayerReset();
        draw = new Draw();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
