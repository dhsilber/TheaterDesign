package com.mobiletheatertech.plot;

import org.testng.SkipException;
import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.*;


/**
 * Created by dhs on 9/21/14.
 */
public class SvgElementTest {

    private Element element = null;

    Double x = 12.0;
    Double y = 25.0;
    Double x1 = 12.0;
    Double y1 = 23.0;
    Double x2 = 19.0;
    Double y2 = 27.0;
    Double width = 19.0;
    Double height = 27.0;
    Double radius = 17.0;
    Double xOffset = 120.0;
    Double yOffset = 76.0;
    Double x1Set = x1 + xOffset;
    Double y1Set = y1 + yOffset;
    Double x2Set = x2 + xOffset;
    Double y2Set = y2 + yOffset;
    Double xSet = x + xOffset;
    Double ySet = y + yOffset;

    String id = "Identification";
    String type = "Type";
    String text = "Test words";
    String path = "M 75 90 L 65 90 A 5 10 0 0 0 75 90";
    String pathOffset = "M 195.0 166.0 L 185.0 166.0 A 5 10 0 0 0 195.0 166.0";
    String color = "blue";
    String group = "g";

    Double dashOffset = 7.0;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        SvgElement.Offset( 0.0, 0.0 );
        element = new IIOMetadataNode( "bogus" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void hasElement() {
        SvgElement element = new SvgElement( new IIOMetadataNode() );
        Element w3cElement = element.element();

        assertTrue(Element.class.isInstance(w3cElement));
    }

    @Test
    public void getSetAttribute() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement defs = draw.element("defs");

        baseLine(draw, defs);
    }

    @Test
    public void offsetX() {
        SvgElement.Offset( xOffset, yOffset );

        assertEquals(SvgElement.OffsetX(), xOffset);
    }

    @Test
    public void offsetY() {
        SvgElement.Offset( xOffset, yOffset );

        assertEquals(SvgElement.OffsetY(), yOffset);
    }

    @Test
    public void svgCircle() throws InvalidXMLException {
        Draw draw = new Draw();

        SvgElement parent = draw.element("defs");

        baseCircle(draw, parent);
    }

    void baseCircle(Draw draw, SvgElement parent) {
        SvgElement result = parent.circle(draw, x, y, radius, color);

        assertEquals(result.attribute("cx"), x.toString());
        assertEquals(result.attribute("cy"), y.toString());
        assertEquals( result.attribute( "r"), radius.toString() );
        assertEquals( result.attribute( "stroke"), color );
        assertEquals(result.attribute("fill"), "none");

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgCircleOffset() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement.Offset( xOffset, yOffset );

        SvgElement parent = draw.element("defs");

        SvgElement result = parent.circle(draw, x, y, radius, color);

        assertEquals(result.attribute("cx"), xSet.toString());
        assertEquals( result.attribute( "cy"), ySet.toString() );
        assertEquals( result.attribute( "r"), radius.toString() );
        assertEquals( result.attribute( "stroke"), color );
        assertEquals( result.attribute( "fill"), "none" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    /**
     * Children of a symbol are a special case, in that no offset is applied to them.
     * When no offset is set, children of symbols are just like children of other
     * elements.
     *
     * @throws InvalidXMLException
     */
    @Test
    public void svgCircleSymbol() throws InvalidXMLException {
        Draw draw = new Draw();

        SvgElement symbol = draw.element("symbol");

        baseCircle(draw, symbol);
    }

    /**
     * Children of a symbol are a special case, in that no offset is applied to them.
     *
     * @throws InvalidXMLException
     */
    @Test
    public void svgCircleSymbolOffset() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement.Offset( xOffset, yOffset );

        SvgElement symbol = draw.element("symbol");

        baseCircle(draw, symbol);
    }

    @Test
    public void svgDashedLine() throws InvalidXMLException {
        Draw draw = new Draw();
        draw.establishRoot();
        SvgElement parent = draw.element("something");

        SvgElement result = parent.dashedLine(draw,
                x1, y1, x2, y2, color, dashOffset );

        assertEquals(result.attribute("x1"), x1.toString());
        assertEquals( result.attribute("y1"), y1.toString() );
        assertEquals(result.attribute("x2"), x2.toString());
        assertEquals(result.attribute("y2"), y2.toString());
        assertEquals( result.attribute("stroke"), color );
        assertEquals( result.attribute("stroke-dasharray"), "48" );
        assertEquals( result.attribute("stroke-dashoffset"), dashOffset.toString() );
//        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgGroup() throws Exception {
        String className = "class name";
        Draw draw = new Draw();

        SvgElement parent = draw.element("defs");

        SvgElement result = parent.group(draw, className );

        assertEquals(result.attribute("class"), className );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgLine() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        baseLine(draw, parent);
    }

    void baseLine(Draw draw, SvgElement parent) {
        SvgElement result = parent.line(draw, x1, y1, x2, y2, color);

        assertEquals(result.attribute("x1"), x1.toString());
        assertEquals( result.attribute("y1"), y1.toString() );
        assertEquals(result.attribute("x2"), x2.toString());
        assertEquals(result.attribute("y2"), y2.toString());
        assertEquals( result.attribute("stroke"), color );
//        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgLineOffset() throws InvalidXMLException {
        SvgElement.Offset( xOffset, yOffset );
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.line(draw, x1, y1, x2, y2, color);

        assertEquals(result.attribute("x1"), x1Set.toString());
        assertEquals( result.attribute("y1"), y1Set.toString() );
        assertEquals( result.attribute("x2"), x2Set.toString() );
        assertEquals( result.attribute("y2"), y2Set.toString());
        assertEquals( result.attribute("stroke"), color );
//        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgLineSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseLine(draw, symbol);
    }

    @Test
    public void svgLineSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset( xOffset, yOffset );
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseLine(draw, symbol);
    }

    @Test
    public void svgPath() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        basePath(draw, parent);
    }

    void basePath(Draw draw, SvgElement parent) {
        SvgElement result = parent.path(draw, path, color);

        assertEquals( result.attribute( "d"), path );
        assertEquals( result.attribute( "stroke"), color );
        assertEquals(result.attribute("stroke-width"), "2");
        assertEquals(result.attribute("fill"), color );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    /*
    Unlike all of the other SvgElement shape-drawing things, 'path' does not have
    a version that adjusts for the current offest.

    In order to make it not fail, I need to parse a path and update selected elements.
    I will do that work if I ever need to.
    @Test
    public void svgPathOffset() throws InvalidXMLException {
        SvgElement.Offset( xOffset, yOffset );
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.path( draw, path, color );

        assertEquals( result.attribute( "d"), pathOffset );
        assertEquals( result.attribute( "stroke"), color );
        assertEquals( result.attribute("stroke-width"), "2" );
        assertEquals( result.attribute( "fill"), "none" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }
     */

    @Test
    public void svgPathSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        basePath(draw, symbol);
    }

    @Test
    public void svgPathSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        basePath(draw, symbol);
    }

    @Test
    public void svgRectangle() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        baseRectangle(draw, parent);
    }

    void baseRectangle(Draw draw, SvgElement parent) {
        SvgElement result = parent.rectangle( draw, x, y, width, height, color );

        assertEquals( result.attribute( "x"), x.toString() );
        assertEquals( result.attribute( "y"), y.toString() );
        assertEquals( result.attribute( "width"), width.toString() );
        assertEquals(result.attribute("height"), height.toString());
        assertEquals( result.attribute( "stroke"), color );
        assertEquals(result.attribute("fill"), "none");

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgRectangleOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.rectangle( draw, x, y, width, height, color );

        assertEquals( result.attribute( "x"), xSet.toString() );
        assertEquals( result.attribute( "y"), ySet.toString() );
        assertEquals( result.attribute( "width"), width.toString() );
        assertEquals(result.attribute("height"), height.toString());
        assertEquals( result.attribute( "stroke"), color );
        assertEquals(result.attribute("fill"), "none");

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgRectangleSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseRectangle(draw, symbol);
    }

    @Test
    public void svgRectangleSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseRectangle(draw, symbol);
    }

//
//    @Test
//    public void scaleLine() {
//        Draw draw = new Draw();
//        SvgElement parent = draw.element("defs");
//
//        Point start = new Point( x1, y1, 0.0 );
//        SvgElement result = parent.scaleLine(draw, start, width, height );
//
//        assertEquals(result.tag(), group);
//        assertEquals(result.attribute("class"), "scale");
//
//        NodeList list = result.element().getChildNodes();
//
//        assertEquals( list.getLength(), 8 );
//        for( int index = 0; index < 8; index += 2) {
//            Node node = list.item( index );
//            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element1 = (Element) node;
//
//                each(element1, index );
//
//                assertEquals(element1.getAttribute("stroke-dasharray"), "", "index: " + index );
//                assertEquals(element1.getAttribute("stroke-dashoffset"), "", "index: " + index );
//            }
//        }
//        for( int index = 1; index < 8; index += 2 ) {
//            Node node = list.item(index);
//            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element1 = (Element) node;
//
//                each(element1, index );
//
//                assertEquals(element1.getAttribute("stroke-dasharray"), "48", "index: " + index );
////                assertEquals(element1.getAttribute("stroke-dashoffset"), "90", "index: " + index );
//            }
//        }
//
//        Node childNode = parent.element().getLastChild();
//        assert( childNode.isSameNode( result.element() ) );
//    }
//
//    private void each( Element element, int index ) {
//        assertEquals( element.getTagName(), "line", "index: " + index );
//        assertEquals( element.getAttribute("stroke-width"), "3", "index: " + index );
//    }


    @Test
    public void svgSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.symbol(draw, id);

        assertEquals( result.attribute( "id"), id );
        assertEquals( result.attribute( "overflow"), "visible" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgText() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        baseText(draw, parent);
    }

    void baseText(Draw draw, SvgElement parent) {
        SvgElement result = parent.text(draw, text, x, y, color);

        assertEquals( result.attribute("x"), x.toString() );
        assertEquals( result.attribute("y"), y.toString() );
        assertEquals( result.attribute("fill"), color );
//        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );

        String contents = result.element().getTextContent();
        assertEquals( contents, text );
    }

    @Test
    public void svgTextOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.text(draw, text, x, y, color);

        assertEquals( result.attribute("x"), xSet.toString() );
        assertEquals( result.attribute("y"), ySet.toString() );
        assertEquals( result.attribute("fill"), color );
//        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );

        String contents = result.element().getTextContent();
        assertEquals(contents, text);
    }

    @Test
    public void svgTextSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseText(draw, symbol);
    }

    @Test
    public void svgTextSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseText( draw, symbol );
    }

    @Test
    public void svgUse() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.use( draw, type, x, y );

        baseUse(draw, parent, result);
    }

    @Test
    public void svgUseWithId() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.use( draw, type, x, y, id );

        baseUse(draw, parent, result);
        assertEquals( result.attribute( "id"), id );
    }

    void baseUse(Draw draw, SvgElement parent, SvgElement result) {
        assertEquals( result.attribute( "xlink:href"), "#" + type );
        assertEquals( result.attribute( "x"), x.toString() );
        assertEquals( result.attribute( "y"), y.toString() );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgUseOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        SvgElement result = parent.use( draw, type, x, y );

        assertEquals( result.attribute( "xlink:href"), "#" + type );
        assertEquals( result.attribute( "x"), xSet.toString() );
        assertEquals( result.attribute( "y"), ySet.toString() );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgUseSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        SvgElement result = symbol.use( draw, type, x, y );

        baseUse(draw, symbol, result);
    }

    @Test
    public void svgUseSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        SvgElement result = symbol.use( draw, type, x, y );

        baseUse(draw, symbol, result);
    }

    @Test
    public void plotData() throws Exception {
        Draw draw = new Draw();
        SvgElement parent = draw.element("defs");

        String lightingStandName = "Jessie";
        Element lightingStandElement = new IIOMetadataNode( "lighting-stand" );
        lightingStandElement.setAttribute("id", lightingStandName );
        lightingStandElement.setAttribute("x", "12");
        lightingStandElement.setAttribute("y", "34");
        LightingStand lightingStand = new LightingStand( lightingStandElement );
        lightingStand.verify();

        Element elementOnLightingStand = new IIOMetadataNode( "luminaire" );
        elementOnLightingStand.setAttribute( "type", type );
        elementOnLightingStand.setAttribute("on", lightingStandName );
        elementOnLightingStand.setAttribute("location", "a" );
        elementOnLightingStand.setAttribute("unit", "1" );

        Luminaire luminaire = new Luminaire( elementOnLightingStand );

        SvgElement result = parent.data( draw, luminaire );

        assertNotNull( result.element() );
        assertEquals( result.element().getTagName(), "plot:Luminaire" );
    }

    @Test
    public void addMouseover() {
        String over = "overscript(evt)";
        String out = "outscript(evt)";
        Draw draw = new Draw();
        SvgElement bogus = draw.element("bogus");
        bogus.mouseover( over, out );

        assertEquals( bogus.attribute( "onmouseover"), over );
        assertEquals( bogus.attribute( "onmouseout"), out );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}
