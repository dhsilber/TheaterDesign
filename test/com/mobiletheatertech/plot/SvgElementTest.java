package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;


/**
 * Created by dhs on 9/21/14.
 */
public class SvgElementTest {

    /**
     * Extended {@code MinderDom} so that there is a concrete class to test with.
     */
    private class MindedDom extends MinderDom {

        public MindedDom( Element element ) throws InvalidXMLException {
            super( element );
        }

        @Override
        public void verify() throws InvalidXMLException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void dom( Draw draw, View mode ) {

//            throw new UnsupportedOperationException(
//                    "Not supported yet." ); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private Element element = null;

    Integer x = 12;
    Integer y = 25;
    Integer x1 = 12;
    Integer y1 = 23;
    Integer x2 = 19;
    Integer y2 = 27;
    Integer width = 19;
    Integer height = 27;
    Integer radius = 17;
    Integer xOffset = 120;
    Integer yOffset = 76;
    Integer x1Set = x1 + xOffset;
    Integer y1Set = y1 + yOffset;
    Integer x2Set = x2 + xOffset;
    Integer y2Set = y2 + yOffset;
    Integer xSet = x + xOffset;
    Integer ySet = y + yOffset;

    String id = "Identification";
    String text = "Test words";
    String path = "Path here";
    String pathOffset = "Path with offset corrections here";
    String color = "blue";

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
        assertEquals( result.attribute( "cy"), y.toString() );
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
        assertEquals(result.attribute("fill"), "none");

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

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

        fail("Check enclosed text element.");
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
        assertEquals( result.attribute("stroke-width"), "2" );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );

        fail( "Check enclosed text element.");
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

        baseUse(draw, parent);
    }

    void baseUse(Draw draw, SvgElement parent) {
        SvgElement result = parent.use( draw, id, x, y );

        assertEquals( result.attribute( "xlink:href"), "#" + id );
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

        SvgElement result = parent.use( draw, id, x, y );

        assertEquals( result.attribute( "xlink:href"), "#" + id );
        assertEquals( result.attribute( "x"), xSet.toString() );
        assertEquals( result.attribute( "y"), ySet.toString() );

        Node childNode = parent.element().getLastChild();
        assert( childNode.isSameNode( result.element() ) );
    }

    @Test
    public void svgUseSymbol() throws InvalidXMLException {
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseUse(draw, symbol);
    }

    @Test
    public void svgUseSymbolOffset() throws InvalidXMLException {
        SvgElement.Offset(xOffset, yOffset);
        Draw draw = new Draw();
        SvgElement symbol = draw.element("symbol");

        baseUse(draw, symbol);
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        SvgElement.Offset( 0, 0 );
        element = new IIOMetadataNode( "bogus" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
}
