package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/23/13 Time: 1:10 PM To change this template use
 * File | Settings | File Templates.
 */

import org.testng.annotations.*;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * Test {@code Grid}.
 *
 * @author dhs
 * @since 0.0.10
 */
public class GridTest {

    Element element = null;

    Double startx = 13.0;
    Double starty = 15.0;
    Double startz = 7.0;

    Double venueWidth = 351.0;
    Double venueDepth = 401.0;

    Element prosceniumElement;
    Double prosceniumX = 110.3;
    Double prosceniumY = 120.7;
    Double prosceniumZ = 12.2;

    @BeforeMethod
    public void setUpMethod() throws Exception {
        element = new IIOMetadataNode( "grid" );

        Element venueElement = new IIOMetadataNode( "venue" );
        venueElement.setAttribute( "room", "Test Name" );
        venueElement.setAttribute( "width", venueWidth.toString() );
        venueElement.setAttribute( "depth", venueDepth.toString() );
        venueElement.setAttribute( "height", "241" );
        new Venue( venueElement );

        prosceniumElement = new IIOMetadataNode( "proscenium" );
        prosceniumElement.setAttribute( "x", prosceniumX.toString() );
        prosceniumElement.setAttribute( "y", prosceniumY.toString() );
        prosceniumElement.setAttribute( "z", prosceniumZ.toString() );
        prosceniumElement.setAttribute( "width", "200" );
        prosceniumElement.setAttribute( "depth", "23" );
        prosceniumElement.setAttribute( "height", "144" );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
        Proscenium.Reset();
        SvgElement.Offset( 0.0, 0.0 );
    }

    @Test
    public void constantTag() {
        assertEquals( Grid.Tag, "grid" );
    }

    @Test
    public void constantColor() {
        assertEquals( Grid.Color, "blue" );
    }

    @Test
    public void constantScaleThickness() {
        assertEquals( Grid.SCALETHICKNESS, (Double)21.0 );
    }

    @Test
    public void isA() throws Exception {
        Grid grid = new Grid( element );

        assert MinderDom.class.isInstance(grid);
    }

    @Test
    public void storesAttributes() throws Exception {
        Grid grid = new Grid( element );

        assertEquals( TestHelpers.accessDouble(grid, "startx"), (Double)0.0 );
        assertEquals( TestHelpers.accessDouble( grid, "starty" ), (Double)0.0 );
        assertEquals( TestHelpers.accessDouble( grid, "startz" ), (Double)0.0 );
    }

    @Test
    public void storesOptionalAttributes() throws Exception {
        element.setAttribute( "startx", startx.toString() );
        element.setAttribute( "starty", starty.toString() );
        element.setAttribute( "startz", startz.toString() );

        Grid grid = new Grid( element );

        assertEquals( TestHelpers.accessDouble( grid, "startx" ), startx );
        assertEquals( TestHelpers.accessDouble( grid, "starty" ), starty );
        assertEquals( TestHelpers.accessDouble( grid, "startz" ), startz );
    }

//    @Test
//    public void layer() throws Exception {
//        assertNull( Category.Select( Grid.Tag ) );
//
//        new Grid( element );
//
//        assertNotNull( Category.Select( Grid.Tag ) );
//    }

    @Test
    public void domBorderLines() throws Exception {
        borderLinesCommon();
    }

    @Test
    public void domBorderLinesOffset() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        borderLinesCommon();
    }

    @Test
    public void domBorderLinesProscenium() throws Exception {
        new Proscenium( prosceniumElement );

        borderLinesCommon();
    }

    @Test
    public void domBorderLinesOffsetProscenium() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        new Proscenium(prosceniumElement);

        borderLinesCommon();
    }

    void borderLinesCommon() throws Exception {

        Draw draw = new Draw();
        draw.establishRoot();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        Grid grid = new Grid( element );
        // Note that start{xyz} are all zero.

        grid.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 25 );

        Integer lineSpacer = 3;

        //  top scale line
        Double x1 = Grid.SCALETHICKNESS - lineSpacer + Grid.ExtraLeftSpace;
        Double y = Grid.SCALETHICKNESS - lineSpacer;
        Double x2 = Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS - Grid.ExtraLeftSpace;
        Node node = list.item( 0 );
        checkEdgeLine(node, x1, y, x2, y);

        //  bottom scale line
        x1 = Grid.SCALETHICKNESS - lineSpacer + Grid.ExtraLeftSpace;
        y = Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS + lineSpacer;
        x2 = Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS - Grid.ExtraLeftSpace;
        node = list.item( 1 );
        checkEdgeLine(node, x1, y, x2, y);

        //  left scale line
        Double x = Grid.SCALETHICKNESS - lineSpacer + Grid.ExtraLeftSpace;
        Double y1 = Grid.SCALETHICKNESS - lineSpacer;
        Double y2 = Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS;// + lineSpacer * 2;
        node = list.item( 2 );
        checkEdgeLine(node, x, y1, x, y2);

        //  right scale line
        x = Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS + lineSpacer - Grid.ExtraLeftSpace;
        y1 = Grid.SCALETHICKNESS - lineSpacer;
        y2 = Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS + lineSpacer;// * 2;
        node = list.item( 3 );
        checkEdgeLine(node, x, y1, x, y2);
    }

    @Test
    public void domBorderDashedLines() throws Exception {
        Draw draw = new Draw();
        draw.establishRoot();

        borderDashedLinesCommon( 0.0, 0.0 );
    }

    @Test
    public void domBorderDashedLinesOffset() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        borderDashedLinesCommon( -13.0, -15.0 );
    }

    @Test
    public void domBorderDashedLinesProscenium() throws Exception {
        // Note that start{xyz} are all zero.
        new Proscenium(prosceniumElement);

        Double xShift = - (prosceniumX + SvgElement.OffsetX() ) % 48;
        Double yShift = - (prosceniumY + SvgElement.OffsetY() ) % 48;

        borderDashedLinesCommon( xShift, yShift );
    }

    @Test
    public void domBorderDashedLinesOffsetProscenium() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        new Proscenium( prosceniumElement );

        borderDashedLinesCommon( -27.3, -39.7 );
    }

    void borderDashedLinesCommon( Double xShift, Double yShift ) throws Exception
    {
        Draw draw = new Draw();
        draw.establishRoot();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        Grid grid = new Grid( element );

        grid.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "line" );
        assertEquals( list.getLength(), 25 );

        Integer lineSpacer = 3;

        // top
        Double x1 = Grid.SCALETHICKNESS + Grid.ExtraLeftSpace;
        Double y = Grid.SCALETHICKNESS - lineSpacer * 2;
        Double x2 = Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS - Grid.ExtraLeftSpace;
        Node node = list.item( 4 );
        checkEdgeDashedLine(node, x1, y, x2, y, xShift );

        //  bottom scale line
        y = Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS + lineSpacer * 2;
        node = list.item( 5 );
        checkEdgeDashedLine(node, x1, y, x2, y, xShift );

        //  left scale line
        Double x = Grid.SCALETHICKNESS - lineSpacer * 2 + Grid.ExtraLeftSpace;
        Double y1 = Grid.SCALETHICKNESS;
        Double y2 = Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS;
        node = list.item( 6 );
        checkEdgeDashedLine(node, x, y1, x, y2, yShift );

        //  right scale line
        x = Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS + lineSpacer * 2 - Grid.ExtraLeftSpace;
        node = list.item( 7 );
        checkEdgeDashedLine(node, x, y1, x, y2, yShift );
    }

    @Test
    public void domScale() throws Exception {
        scaleCommon();
    }

    @Test
    public void domScaleOffset() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        scaleCommon();
    }

    @Test
    public void domScaleProscenium() throws Exception {
        new Proscenium( prosceniumElement );

        scaleCommon();
    }

    @Test
    public void domScaleOffsetProscenium() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        new Proscenium(prosceniumElement);

        scaleCommon();
    }

    void scaleCommon() throws Exception {

        Draw draw = new Draw();
        draw.establishRoot();

        NodeList prelist = draw.root().getElementsByTagName( "text" );
        assertEquals( prelist.getLength(), 0 );

        Grid grid = new Grid( element );

        grid.dom( draw, View.PLAN );

        NodeList list = draw.root().getElementsByTagName( "text" );

        // top
        int index = 0;
        Integer value = Proscenium.Origin().x().intValue() / 48 * -4;
        for( Double position = Grid.SCALETHICKNESS + (Proscenium.Origin().x() + grid.startx) % 48;
             position <= Venue.Width() + Grid.SCALETHICKNESS * 2 + grid.startx;
             position += 48 )
        {
            Node node = list.item( index );
            checkScaleNumber(node, index,
                    position, 8.0,
                    value.toString(), "middle" );

            index++;
            value += 4;
        }

        // bottom
        value = Proscenium.Origin().x().intValue() / 48 * -4;
        for( Double position = Grid.SCALETHICKNESS + (Proscenium.Origin().x() + grid.startx) % 48;
             position <= Venue.Width() + Grid.SCALETHICKNESS * 2 + grid.startx + Grid.ExtraLeftSpace;
             position += 48 )
        {
            Node node = list.item( index );
            checkScaleNumber(node, index,
                    position, Venue.Depth() + SvgElement.OffsetY() + Grid.SCALETHICKNESS + 16.0,
                    value.toString(), "middle" );

            index++;
            value += 4;
        }

        // left
        int direction = 1;
        if (Proscenium.Active()) {
            value = Proscenium.Origin().y().intValue() / 48 * 4;
            direction = -1;
        }
        else {
            value = 0;
        }
        for( Double position = Grid.SCALETHICKNESS + (Proscenium.Origin().y() + grid.starty) % 48;
             position <= Venue.Depth() + Grid.SCALETHICKNESS * 2 + grid.startx;
             position += 48 )
        {
            Node node = list.item( index );
             checkScaleNumber(node, index,
                    1.0, position,
                    value.toString(), "left" );

            index++;
            value += 4 * direction;
        }

        // right
        if (Proscenium.Active()) {
            value = Proscenium.Origin().y().intValue() / 48 * 4;
        }
        else {
            value = 0;
        }
        for( Double position = Grid.SCALETHICKNESS + (Proscenium.Origin().y() + grid.starty) % 48;
             position <= Venue.Depth() + Grid.SCALETHICKNESS * 2 + grid.startx;
             position += 48 )
        {
            Node node = list.item( index );
            checkScaleNumber(node, index,
                    Venue.Width() + SvgElement.OffsetX() + Grid.SCALETHICKNESS + 11 - Grid.ExtraLeftSpace, position,
                    value.toString(), "right" );

            index++;
            value += 4 * direction;
        }

        assertEquals( list.getLength(), index );
    }

    private void checkScaleNumber(Node node, Integer index, Double x, Double y,
                                  String value, String anchor )
    {
        Element textElement;
        String text;
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        textElement = (Element) node;
        assertEquals( textElement.getAttribute( "x" ), x.toString(), "index: " + index );
        assertEquals( textElement.getAttribute( "y" ), y.toString(), "index: " + index );
        assertEquals( textElement.getAttribute( "fill" ), Grid.Color, "index: " + index );
        assertEquals( textElement.getAttribute( "stroke" ), "none", "index: " + index );
        assertEquals( textElement.getAttribute( "font-weight" ), "100", "index: " + index );
        assertEquals( textElement.getAttribute( "font-family" ), "sans-serif", "index: " + index );
        assertEquals( textElement.getAttribute( "font-size" ), "10", "index: " + index );
        assertEquals( textElement.getAttribute( "text-anchor" ), anchor, "index: " + index );

        text = textElement.getTextContent();
        assertEquals( text, value, "index: " + index );
    }

    @Test
    public void domGrid() throws Exception {
        // Note that start{xyz} are all zero.

        gridCommon( gridCommonSetup(), 0.0, 0.0 );
    }

    @Test
    public void domGridOffset() throws Exception {
        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        gridCommon( gridCommonSetup(), 0.0, 0.0 );
    }


    @Test
    public void domGridProscenium() throws Exception
    {
        // Note that start{xyz} are all zero.
        new Proscenium( prosceniumElement );

        gridProsceniumCommon();
    }

    @Test
    public void domGridOffsetProscenium() throws Exception
    {
        new Proscenium(prosceniumElement);

        element.setAttribute("startx", startx.toString());
        element.setAttribute("starty", starty.toString());
        element.setAttribute("startz", startz.toString());

        gridProsceniumCommon();
    }

    void gridProsceniumCommon() throws Exception
    {
        NodeList list = gridCommonSetup();

        // centerline
        Double x = prosceniumX + SvgElement.OffsetX();
        Double y1 = Grid.SCALETHICKNESS;
        Double y2 = venueDepth + Grid.SCALETHICKNESS + SvgElement.OffsetY();
        Node centerlineNode = list.item( 10 );
        checkLine( centerlineNode, x, y1, x, y2, 10 );

        // Proscenium line
        Double x1 = Grid.SCALETHICKNESS;
        Double x2 = venueWidth + Grid.SCALETHICKNESS + SvgElement.OffsetX() - Grid.ExtraLeftSpace;
        Double y = prosceniumY + SvgElement.OffsetY();
        Node prosceniumlineNode = list.item( 18 );
        checkLine( prosceniumlineNode, x1, y, x2, y, 18 );

        gridCommon( list, 14.3, 24.7 );

    }

    private NodeList gridCommonSetup() throws AttributeMissingException, DataException, InvalidXMLException, MountingException, ReferenceException {
        Grid grid = new Grid( element );

        Draw draw = new Draw();
        draw.establishRoot();

        NodeList prelist = draw.root().getElementsByTagName( "line" );
        assertEquals( prelist.getLength(), 0 );

        grid.dom( draw, View.PLAN );

        return draw.root().getElementsByTagName( "line" );
    }

    void gridCommon( NodeList list, Double xOffset, Double yOffset ) throws Exception
    {
        // first eight are drawn by a call to scaleLine() and are tested in
        // domBorder{|Offset|OffseProscenium|Proscenium()
        int index = 8;

        // vertical lines
        Double x = SvgElement.OffsetX() + xOffset;
        Double y1 = Grid.SCALETHICKNESS;
        Double y2 = venueDepth + Grid.SCALETHICKNESS + SvgElement.OffsetY();

        while ( x < Venue.Width() + SvgElement.OffsetX() ) {
            Node node = list.item( index );
            checkLine( node, x, y1, x, y2, index );

            x += 48;
            index++;
        }

        // horizontal lines
        Double x1 = Grid.SCALETHICKNESS;
        Double x2 = venueWidth + Grid.SCALETHICKNESS + SvgElement.OffsetX() - Grid.ExtraLeftSpace;
        Double y = SvgElement.OffsetY() + yOffset;

        while ( y < Venue.Depth() + SvgElement.OffsetX() ) {
            Node node = list.item( index );
            checkLine( node, x1, y, x2, y, index );

            y += 48;
            index++;
        }

        assertEquals( list.getLength(), 25 );
    }

    private void checkEdgeLine(Node node, Double x1, Double y1, Double x2, Double y2)
    {
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element lineElement = (Element) node;
        assertEquals( lineElement.getAttribute( "x1" ), x1.toString() );
        assertEquals( lineElement.getAttribute( "y1" ), y1.toString() );
        assertEquals( lineElement.getAttribute( "x2" ), x2.toString() );
        assertEquals( lineElement.getAttribute( "y2" ), y2.toString() );
        assertEquals( lineElement.getAttribute( "stroke" ), "blue" );
        assertEquals( lineElement.getAttribute( "stroke-width" ), "3" );
        assertEquals( lineElement.getAttribute( "stroke-dasharray" ), "" );
        assertEquals( lineElement.getAttribute( "stroke-dashoffset" ), "" );
        assertEquals( lineElement.getAttribute( "stroke-opacity" ), "" );
    }

    private void checkEdgeDashedLine(Node node,
                                     Double x1, Double y1, Double x2, Double y2,
                                     Double dashoffset )
    {
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element lineElement = (Element) node;

        assertEquals( lineElement.getAttribute( "x1" ), x1.toString() );
        assertEquals( lineElement.getAttribute( "y1" ), y1.toString() );
        assertEquals( lineElement.getAttribute( "x2" ), x2.toString() );
        assertEquals( lineElement.getAttribute( "y2" ), y2.toString() );
        assertEquals( lineElement.getAttribute( "stroke" ), "blue" );
        assertEquals( lineElement.getAttribute( "stroke-width" ), "3" );
        assertEquals( lineElement.getAttribute( "stroke-dasharray" ), "48" );
        Double foundOffset = new Double( lineElement.getAttribute( "stroke-dashoffset" ) );
        assertEquals( foundOffset, dashoffset, 0.000001 );
        assertEquals( lineElement.getAttribute( "stroke-opacity" ), "" );
    }

    private Element checkLine(Node node,
                              Double x1, Double y1, Double x2, Double y2,
                              int index )
    {
        assertEquals( node.getNodeType(), Node.ELEMENT_NODE );
        Element lineElement = (Element) node;

        assertEquals( lineElement.getAttribute( "x1" ), x1.toString(), "index: " + index );
        Double wy1 = new Double( lineElement.getAttribute( "y1" ) );
        assertEquals( wy1, y1, 0.000001, "index: " + index );
        assertEquals( lineElement.getAttribute( "x2" ), x2.toString(), "index: " + index );
        Double wy2 = new Double( lineElement.getAttribute( "y2" ) );
        assertEquals( wy2, y2, 0.000001, "index: " + index );
        assertEquals( lineElement.getAttribute( "stroke" ), "blue", "index: " + index );
        assertEquals( lineElement.getAttribute( "stroke-width" ), "", "index: " + index );
        assertEquals( lineElement.getAttribute( "stroke-dasharray" ), "", "index: " + index );
        assertEquals( lineElement.getAttribute( "stroke-dashoffset" ), "", "index: " + index );
        assertEquals( lineElement.getAttribute( "stroke-opacity" ), "0.2", "index: " + index );

        return lineElement;
    }


    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
}
