package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */
public class ChairTest {

    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final Integer FOOTSPACE = 11;
    private static final String COLOR = "black";
//    private static final String CHAIR = "chair";

//    public static final String LAYERTAG = "Chair";

    Draw draw = null;
    Element element = null;
//    Element chairLineElement = null;
    Double x = 12.5;
    Double y = 45.0;
    Double orientation = 90.0;
    Integer line = 7;
    String layerTag = "fred";
    Double radius = 17.8;
    Double space = 34.7;
    Double opening = 33.33;
    Integer count = 75;

    @Test
    public void isA() throws Exception {
        Chair instance = new Chair(element);

        assert Elemental.class.isInstance( instance );
        assert ElementalLister.class.isInstance( instance );
        assert Verifier.class.isInstance( instance );
        assert Layerer.class.isInstance( instance );
        assert MinderDom.class.isInstance( instance );

        assert Legendable.class.isInstance( instance );
    }

    @Test
    public void storesAttributes() throws Exception {
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalOrientationAttribute() throws Exception {
        element.setAttribute("orientation", orientation.toString());
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), orientation.doubleValue() );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalLineAttribute() throws Exception {
        element.setAttribute("line", line.toString() );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), line );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalLayerAttribute() throws Exception {
        element.setAttribute("layer", layerTag );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0);
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals( TestHelpers.accessString(chair, "layerName"), layerTag );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalRadiusAttribute() throws Exception {
        element.setAttribute("r", radius.toString() );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "radius"), radius);
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals( TestHelpers.accessString(chair, "layerName"), Chair.LAYERTAG );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalSpaceAttribute() throws Exception {
        element.setAttribute("space", space.toString() );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), space );
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals( TestHelpers.accessString(chair, "layerName"), Chair.LAYERTAG );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalOpeningAttribute() throws Exception {
        element.setAttribute("opening", opening.toString() );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals( TestHelpers.accessString(chair, "layerName"), Chair.LAYERTAG );
        assertEquals(TestHelpers.accessDouble(chair, "opening"), opening );
        assertNull(TestHelpers.accessInteger(chair, "count"));
    }

    @Test
    public void storesOptionalCountAttribute() throws Exception {
        element.setAttribute("count", count.toString() );
        Chair chair = new Chair(element);

        assertEquals(TestHelpers.accessDouble(chair, "x"), x.doubleValue());
        assertEquals(TestHelpers.accessDouble(chair, "y"), y.doubleValue());
        assertNull(TestHelpers.accessDouble(chair, "radius"));
        assertEquals(TestHelpers.accessDouble(chair, "space"), 0.0);
        assertEquals(TestHelpers.accessDouble(chair, "orientation"), 0.0 );
        assertEquals( TestHelpers.accessInteger(chair, "line"), (Integer) 0 );
        assertEquals( TestHelpers.accessString(chair, "layerName"), Chair.LAYERTAG );
        assertNull(TestHelpers.accessDouble(chair, "opening"));
        assertEquals(TestHelpers.accessInteger(chair, "count"), count );
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Chair instance is missing required 'x' attribute.")
    public void noX() throws Exception {
        element.removeAttribute("x");
        new Chair(element);
    }

    @Test(expectedExceptions = AttributeMissingException.class,
            expectedExceptionsMessageRegExp = "Chair instance is missing required 'y' attribute.")
    public void noY() throws Exception {
        element.removeAttribute("y");
        new Chair(element);
    }

    @Test
    public void verifyRadiusCircle() throws Exception {
        element.setAttribute( "r", "60" );
        Chair chair = new Chair( element );
        chair.verify();
        Double chairFitDouble = (2 * Math.PI * 60) / CHAIRWIDTH;
        Integer chairFit = chairFitDouble.intValue();
        assertEquals(TestHelpers.accessInteger(chair, "chairFit"), chairFit );
//        assertEquals(TestHelpers.accessDouble(chair, "angle"), (CHAIRWIDTH * 10) / (2 * Math.PI) );
    }

    @Test
    public void verifyCountCircle() throws Exception {
        element.setAttribute( "count", "10" );
        Chair chair = new Chair( element );
        chair.verify();
        assertEquals(TestHelpers.accessDouble(chair, "radius"), (CHAIRWIDTH * 10.0) / (2.0 * Math.PI) );
        assertEquals(TestHelpers.accessInteger(chair, "chairFit"), (Integer) 10 );
        assertEquals(TestHelpers.accessDouble(chair, "angle"), 36.0 );
        assertEquals(TestHelpers.accessDouble(chair, "circumference"), CHAIRWIDTH * 10.0 );
    }

    @Test
    public void verifyCountOpeningCircle() throws Exception {
        element.setAttribute( "count", "3" );
        element.setAttribute( "opening", "25" );
        Chair chair = new Chair( element );
        chair.verify();
        assertEquals(TestHelpers.accessInteger(chair, "chairFit"), (Integer) 3 );
        assertEquals(TestHelpers.accessDouble(chair, "circumference"), CHAIRWIDTH * 4.0 );
        assertEquals(TestHelpers.accessDouble(chair, "angle"), 90.0 );
        assertEquals(TestHelpers.accessDouble(chair, "radius"), (CHAIRWIDTH * 4) / (2 * Math.PI) );
    }

    @Test
    public void hasChairWidthSet() throws Exception {
        Object chairWidthObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Chair",
                        "CHAIRWIDTH");
        assertNotNull(chairWidthObject);
        Integer chairWidth = (Integer) chairWidthObject;

        assertEquals( chairWidth, CHAIRWIDTH );
    }

    @Test
    public void hasChairDepthSet() throws Exception {
        Object chairDepthObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Chair",
                        "CHAIRDEPTH");
        assertNotNull(chairDepthObject);
        Integer chairDepth = (Integer) chairDepthObject;

        assertEquals( chairDepth, CHAIRDEPTH );
    }

    @Test
    public void hasColorSet() throws Exception {
        Object colorObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Chair",
                        "COLOR");
        assertNotNull( colorObject );
        String color = (String) colorObject;

        assertEquals( color, COLOR );
    }

    @Test
    public void hasChairNameSet() throws Exception {
        Object chairNameObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Chair",
                        "CHAIR");
        assertNotNull( chairNameObject );
        String chairName = (String) chairNameObject;

        assertEquals( chairName, "chair" );
    }

    @Test
    public void hasLayerTagSet() throws Exception {
        Object layerTagObject =
                TestHelpers.accessStaticObject("com.mobiletheatertech.plot.Chair",
                        "LAYERTAG");
        assertNotNull(layerTagObject);
        String layerTag = (String) layerTagObject;

        assertEquals( layerTag, "Chair" );
    }

    @Test
    public void registersLayer() throws Exception {
        new Chair( element );

        HashMap<String, Layer> layers = Layer.List();

        assertTrue( layers.containsKey( Chair.LAYERTAG ) );
        assertEquals( layers.get( Chair.LAYERTAG ).name(), Chair.LAYERNAME );
    }

    @Test
    public void registersLegend() throws Exception {
        TestResets.LegendReset();

        new Chair( element );

        TreeMap<Integer, Legendable> legendList = (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
        Integer order = legendList.lastKey();
        assert( order >= LegendOrder.Furniture.ordinal() );
        assert( order < LegendOrder.Structure.ordinal() );

    }

    @Test
    public void registersLegendOnce() throws Exception {
        TestResets.LegendReset();

        new Chair( element );
        new Chair( element );

        TreeMap<Integer, Legendable> legendList = (TreeMap<Integer, Legendable>)
                TestHelpers.accessStaticObject( "com.mobiletheatertech.plot.Legend", "LEGENDLIST" );
        assertEquals( legendList.size(), 1 );
    }

    @Test
    public void domLegendItemMovesPagePoint() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        PagePoint start = new PagePoint( 3.2, 4.5 );

        Chair chair = new Chair( element );
        chair.verify();
        Chair.CHAIRCOUNT++;

        PagePoint finish = chair.domLegendItem( draw, start );

        assertNotEquals(start, finish);
    }

    @Test
    public void domLegendItemContent() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();
        PagePoint start = new PagePoint( 3.2, 4.5 );

        String eleven = "11";
        element.setAttribute( "line", eleven );
        Chair chair = new Chair( element );
        chair.verify();
        Chair.CHAIRCOUNT++;

        chair.domLegendItem( draw, start );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals( list.getLength(), 1 );

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#chair");
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, start.x() );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, start.y() + 4 );
        assertEquals( element.getAttribute("transform"), "scale(0.3)" );


        NodeList textList = groupElement.getElementsByTagName("text");
        assertEquals( textList.getLength(), 2 );

        Node nameNode = textList.item( 0 );
        assertEquals( nameNode.getNodeType(), Node.ELEMENT_NODE );
        Element nameElement = (Element) nameNode;
        assertEquals( nameElement.getAttribute( "x" ), Legend.TEXTOFFSET );
        assertEquals( nameElement.getAttribute( "y" ), start.y() + 7.0 );
        String name = nameElement.getTextContent();
        assertEquals( name, Chair.CHAIR );

        Node countNode = textList.item( 1 );
        assertEquals( countNode.getNodeType(), Node.ELEMENT_NODE );
        Element countElement = (Element) countNode;
        assertEquals( countElement.getAttribute( "x" ), Legend.QUANTITYOFFSET );
        assertEquals( countElement.getAttribute( "y" ), start.y() + 7.0 );
        String count = countElement.getTextContent();
        assertEquals( count, eleven );
    }

    @Test
    public void domCounts() throws Exception {
        draw.establishRoot();

        assertEquals( Chair.Count(), 0 );

        Chair chair = new Chair(element);
        chair.dom(draw, View.PLAN);

        assertEquals( Chair.Count(), 1 );
    }

    @Test
    public void domCountsLine() throws Exception {
        draw.establishRoot();

        assertEquals( Chair.Count(), 0 );

        element.setAttribute( "line", "12" );
        Chair chair = new Chair(element);
        chair.dom(draw, View.PLAN);

        assertEquals( Chair.Count(), 12 );
    }

    private void domCheckSymbolGeneration() throws Exception {
        NodeList defsList = draw.root().getElementsByTagName("defs");
        assertEquals(defsList.getLength(), 2);
        /* 0th baseElement is the empty defs tag generated by Batik - why? */
        Node defsNode = defsList.item(1);
        assertEquals(defsNode.getNodeType(), Node.ELEMENT_NODE);
        Element defsElement = (Element) defsNode;
        assertEquals( defsElement.getAttribute("id"), "" );

        NodeList list = draw.root().getElementsByTagName("symbol");
        assertEquals(list.getLength(), 1);

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("id"), "chair");
    }

    @Test
    public void domFirstGeneratesSymbol() throws Exception {
        draw.establishRoot();

        Chair chair = new Chair(element);
        chair.dom(draw, View.PLAN);

        domCheckSymbolGeneration();
    }

    @Test
    public void domSecondDoesNothingMore() throws Exception {
        draw.establishRoot();

        Chair chair1 = new Chair(element);
        chair1.dom(draw, View.PLAN);
        Chair chair2 = new Chair(element);
        chair2.dom(draw, View.PLAN);

        domCheckSymbolGeneration();
    }

    @Test
    public void domSymbolColor() throws Exception {
        draw.establishRoot();
        Chair chair = new Chair(element);

        chair.dom(draw, View.PLAN);

        NodeList defsList = draw.root().getElementsByTagName("defs");
        assertEquals(defsList.getLength(), 2);
        /* 0th baseElement is the empty defs tag generated by Batik - why? */
        Node defsNode = defsList.item(1);
        assertEquals(defsNode.getNodeType(), Node.ELEMENT_NODE);
        Element defsElement = (Element) defsNode;
        assertEquals(defsElement.getAttribute("id"), "");

        NodeList symbolList = draw.root().getElementsByTagName("symbol");
        assertEquals(symbolList.getLength(), 1);

        Node symbolNode = symbolList.item(0);
        assertEquals( symbolNode.getNodeType(), Node.ELEMENT_NODE);
        Element symbolElement = (Element) symbolNode;

        NodeList pathList = symbolElement.getElementsByTagName("path");
        assertEquals(pathList.getLength(), 1);

        Node pathNode = pathList.item(0);
        assertEquals( pathNode.getNodeType(), Node.ELEMENT_NODE);
        Element pathElement = (Element) pathNode;
        assertEquals( pathElement.getAttribute( "stroke"), COLOR );
    }

    @Test
    public void domPlanSingle() throws Exception {
        draw.establishRoot();
        Chair chair = new Chair(element);

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);

        checkRotatedChair(list, 0, x, y, 0.0 );
    }

    @Test
    public void domPlanSingleRotated() throws Exception {
        Double offsetX = 13.6;
        Double offsetY = 4.8;
        SvgElement.Offset( offsetX, offsetY );
        Double shiftedX = x + offsetX;
        Double shiftedY = y + offsetY;

        draw.establishRoot();
        element.setAttribute("orientation", orientation.toString());
        Chair chair = new Chair(element);

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals(list.getLength(), 1);

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#chair");
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, shiftedX );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, shiftedY );

        assertEquals( element.getAttribute( "transform" ), "rotate("+orientation+","+shiftedX+","+shiftedY+")" );
    }

    @Test
    public void domPlanLine() throws Exception {
        draw.establishRoot();

        element.setAttribute( "line", line.toString() );
        Chair chair = new Chair(element);

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals( list.getLength(), (int) line );

        checkRotatedChair(list, 0, x, y, 0.0);

        Node lastNode = list.item( line - 1 );
        assertEquals(lastNode.getNodeType(), Node.ELEMENT_NODE);
        Element lastElement = (Element) lastNode;
        assertEquals(lastElement.getAttribute("xlink:href"), "#chair");
        Double lastX = new Double( lastElement.getAttribute("x") );
        Double expectedLastX = x + (CHAIRWIDTH * (line - 1));
        assertEquals( lastX, expectedLastX );
        Double lastY = new Double( lastElement.getAttribute("y") );
        assertEquals( lastY, y );
        assertEquals( lastElement.getAttribute( "transform" ), "rotate(0.0," + expectedLastX + "," + y + ")" );
    }

    @Test
    public void domPlanCircle() throws Exception {
        draw.establishRoot();

        Double fourChairRadius = 4 * Chair.CHAIRWIDTH / 2 / Math.PI;

        element.setAttribute( "r", fourChairRadius.toString() );
        checkCircle(fourChairRadius);
    }

    @Test
    public void domPlanCountedCircle() throws Exception {
        draw.establishRoot();

        Double fourChairRadius = 4 * Chair.CHAIRWIDTH / 2 / Math.PI;

        element.setAttribute( "count", "4" );
        checkCircle(fourChairRadius);
    }

    @Test
    public void domPlanCircleSpace() throws Exception {
        draw.establishRoot();

        Double fourChairRadius = 4 * (Chair.CHAIRWIDTH + space + 0.1) / 2 / Math.PI;

        element.setAttribute( "r", fourChairRadius.toString() );
        element.setAttribute( "space", space.toString() );
        checkCircle(fourChairRadius);
    }

    void checkCircle(Double fourChairRadius) throws AttributeMissingException, DataException, InvalidXMLException, FeatureException, LocationException, MountingException, ReferenceException {
        Chair chair = new Chair(element);
        chair.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals( list.getLength(), 4 );

        Double expectX = x;
        Double expectY = y + fourChairRadius;
        checkRotatedChair(list, 0, expectX, expectY, 0.0 );
        expectX = x - fourChairRadius;
        expectY = y;
        checkRotatedChair(list, 1, expectX, expectY, 90.0 );
        expectX = x;
        expectY = y - fourChairRadius;
        checkRotatedChair(list, 2, expectX, expectY, 180.0 );
        expectX = x + fourChairRadius;
        expectY = y;
        checkRotatedChair(list, 3, expectX, expectY, 270.0 );
    }

    void checkRotatedChair(NodeList list, int index, Double expectX, Double expectY, Double rotation ) {
        Node node = list.item( index );
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#chair");
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, expectX, 0.0001 );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, expectY, 0.0001 );
        // Check for thisX & thisY in string rather than expectX & expectY.
        // Values for x & y are checked above with a delta.
        // The check below is to make sure that they make it into the string.
        assertEquals( element.getAttribute( "transform" ), "rotate("+rotation+","+ thisX+","+ thisY+")" );
    }

    // TODO Allow for lines of chairs to be rotated.
    // See URL in comment below for math.
    @Test
    public void domPlanLineRotatedFromOrigin() throws Exception {
        draw.establishRoot();

//        http://gamedev.stackexchange.com/questions/18340/get-position-of-point-on-circumference-of-circle-given-an-angle

        Double degrees = 60.0;
        Integer count = 5;
        element.setAttribute( "x", "0" );
        element.setAttribute( "y", "0" );
        element.setAttribute( "orientation", degrees.toString() );
        element.setAttribute( "line", count.toString() );
        Double expectedLastX = Math.cos( Math.toRadians( degrees ) ) * (count - 1) * CHAIRWIDTH;
        Double expectedLastY = Math.sin( Math.toRadians( degrees ) ) * (count - 1) * CHAIRWIDTH;

        Chair chair = new Chair(element);

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals( list.getLength(), (int) count );

        Node node = list.item(0);
        assertEquals(node.getNodeType(), Node.ELEMENT_NODE);
        Element element = (Element) node;
        assertEquals(element.getAttribute("xlink:href"), "#chair");
        Double thisX = new Double( element.getAttribute("x") );
        assertEquals( thisX, 0.0 );
        Double thisY = new Double( element.getAttribute("y") );
        assertEquals( thisY, 0.0 );
        assertEquals( element.getAttribute( "transform" ), "rotate(60.0,0.0,0.0)" );

        Node lastNode = list.item( count - 1 );
        assertEquals(lastNode.getNodeType(), Node.ELEMENT_NODE);
        Element lastElement = (Element) lastNode;
        assertEquals(lastElement.getAttribute("xlink:href"), "#chair");
        Double lastX = new Double( lastElement.getAttribute("x") );
        assertEquals( lastX, expectedLastX );
        Double lastY = new Double( lastElement.getAttribute("y") );
        assertEquals( lastY, expectedLastY );
        assertEquals( lastElement.getAttribute( "transform" ),
                "rotate(60.0," + expectedLastX + "," + expectedLastY + ")" );
    }

    @Test
    public void domPlanCountedCircleWithOpening() throws Exception {
        draw.establishRoot();

        Double fourChairRadius = 4 * Chair.CHAIRWIDTH / 2 / Math.PI;

        element.setAttribute( "count", "3" );
        element.setAttribute( "opening", "25");
        Chair chair = new Chair(element);
        chair.verify();

        NodeList existingGroups = draw.root().getElementsByTagName("g");
        assertEquals(existingGroups.getLength(), 1);

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);

        NodeList list = groupElement.getElementsByTagName("use");
        assertEquals( list.getLength(), 3 );

        Double expectX = x;
        Double expectY = y + fourChairRadius;
        checkRotatedChair(list, 0, expectX, expectY, 0.0 );
        expectX = x - fourChairRadius;
        expectY = y;
        checkRotatedChair(list, 1, expectX, expectY, 90.0 );
        expectX = x + fourChairRadius;
        expectY = y;
        checkRotatedChair(list, 2, expectX, expectY, 270.0 );
    }

    @Test
    public void domNormalLayer() throws Exception {
        draw.establishRoot();
        Chair chair = new Chair( element );

        chair.dom( draw, View.PLAN );

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), Chair.LAYERTAG);
    }

    @Test
    public void domLayer() throws Exception {
        new Layer( layerTag, layerTag, "black" );
        draw.establishRoot();
        element.setAttribute("layer", layerTag);
        Chair chair = new Chair( element );

        chair.dom(draw, View.PLAN);

        NodeList group = draw.root().getElementsByTagName("g");
        assertEquals(group.getLength(), 2);
        Node groupNode = group.item(1);
        assertEquals(groupNode.getNodeType(), Node.ELEMENT_NODE);
        Element groupElement = (Element) groupNode;
        assertEquals(groupElement.getAttribute("class"), layerTag);
    }

    @Test
    public void parse() throws Exception {
        String xml = "<plot>" +
                "<chair x=\"20\" y=\"30\" />" +
                "</plot>";
        InputStream stream = new ByteArrayInputStream( xml.getBytes() );

        TestResets.MinderDomReset();

        //TODO Takes too long to complete:
        new Parse( stream );

        // Final size of list
        ArrayList<ElementalLister> list = ElementalLister.List();
        assertEquals( list.size(), 1 );

        ElementalLister chair = list.get( 0 );
        assert MinderDom.class.isInstance( chair );
        assert Chair.class.isInstance( chair );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {

        TestResets.ChairReset();
//        Element venueElement = new IIOMetadataNode("venue");
//        venueElement.setAttribute("room", "Test Name");
//        venueElement.setAttribute("width", "350");
//        venueElement.setAttribute("depth", "400");
//        venueElement.setAttribute("height", "240");
//        new Venue(venueElement);

        element = new IIOMetadataNode("chair");
        element.setAttribute("x", x.toString());
        element.setAttribute("y", y.toString());

//        chairLineElement = new IIOMetadataNode("chair");
//        chairLineElement.setAttribute("x", x.toString());
//        chairLineElement.setAttribute("y", y.toString());

        draw = new Draw();
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
