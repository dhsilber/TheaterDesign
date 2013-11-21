package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a rectangular block of individual chairs, such as a hotel would lay out in a function
 * room for audience seating.
 * <p/>
 * XML tag is 'chairblock'. Required attributes are the set of: 'x', 'y', 'width', and 'depth', or
 * just 'path'. Coordinates are relative to the page origin.
 *
 * @author dhs
 * @since 0.0.9
 */
public class ChairBlock extends MinderDom {

    /**
     * Name of {@code Layer} of {@code ChairBlock}s.
     */
    public static final String LAYERNAME = "Chair Blocks";

    /**
     * Tag for {@code Layer} of {@code ChairBlock}s.
     */
    public static final String LAYERTAG = "chairblock";

    private static boolean SYMBOLGENERATED = false;

    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final Integer FOOTSPACE = 11;

    private Integer x = null;
    private Integer y = null;
    private Integer width = null;
    private Integer depth = null;
    private String path = null;

    /**
     * Construct a {@code ChairBlock} for each element in a list of XML nodes.
     *
     * @param list of 'chairblock' nodes
     * @throws AttributeMissingException if any attribute is missing from any {@code ChairBlock}
     */
//   This ends up copied to each thing that inherits from Minder. There needs to be a factory somewhere.
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new ChairBlock( element );
                }
            }
        }
    }

    /**
     * Construct a {@code ChairBlock} from an XML Element.
     *
     * @param element DOM Element defining a block of chairs
     * @throws AttributeMissingException if any attribute is missing
     */
    public ChairBlock( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        x = getOptionalIntegerAttribute( element, "x" );
        y = getOptionalIntegerAttribute( element, "y" );
        width = getOptionalIntegerAttribute( element, "width" );
        depth = getOptionalIntegerAttribute( element, "depth" );
        path = getOptionalStringAttribute( element, "path" );

        new Layer( LAYERNAME, LAYERTAG );
    }

    @Override
    public void verify() {
    }
//
//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//        // Draw just the outline of the ChairBlock... left here for use in debugging.
////        canvas.setPaint( Color.BLACK );
////        canvas.draw( new Rectangle( x, y, width, depth ) );
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) {
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) {
//    }

    /**
     * Generate SVG DOM for a {@code ChairBlock}. <p/> The first {@code ChairBlock} generated also
     * generates the symbol definition used for each individual chair.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode - currently only supports {@code View.PLAN}
     */
    @Override
    public void dom( Draw draw, View mode ) {
        if (View.PLAN != mode) {
            return;
        }

        if (!SYMBOLGENERATED) {
            Element defs = draw.element( "defs" );
            draw.appendRootChild( defs );

            Element symbol = draw.element( "symbol" );
            symbol.setAttribute( "id", "chair" );
            symbol.setAttribute( "overflow", "visible" );
            defs.appendChild( symbol );

            Element path = draw.element( "path" );
            path.setAttribute( "fill", "none" );
            path.setAttribute( "stroke", "black" );
            path.setAttribute( "stroke-width", "2" );
            int sideways = CHAIRWIDTH / 3;
            int forward = CHAIRDEPTH / 2;
            path.setAttribute( "d",
                               "M -" + sideways + " -" + forward +
                                       " L -" + sideways + " " + forward +
                                       " L " + sideways + " " + forward +
                                       " L " + sideways + " -" + forward
            );
            symbol.appendChild( path );

            SYMBOLGENERATED = true;
        }

        Element group = draw.element( "g" );
        group.setAttribute( "class", LAYERTAG );
        draw.appendRootChild( group );
        int rowCount = depth / (CHAIRDEPTH + FOOTSPACE);
        int rowOffset = FOOTSPACE + CHAIRDEPTH / 2;
        int columnCount = width / CHAIRWIDTH;
        int columnOffset = CHAIRWIDTH / 2 + 2;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Integer yValue = y + rowIndex * (CHAIRWIDTH + FOOTSPACE) + rowOffset;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Element use = draw.element( "use" );
                use.setAttribute( "xlink:href", "#chair" );
                Integer xValue = x + columnIndex * CHAIRWIDTH + columnOffset;
//                System.err.println( "x: "+x+", columnIndex: "+columnIndex+", xValue: "+xValue );
                use.setAttribute( "x", xValue.toString() );

//                System.err.println( "y: "+y+", rowIndex: "+rowIndex+", yValue: "+yValue );
                use.setAttribute( "y", yValue.toString() );


                group.appendChild( use );
//                draw.appendRootChild( use );
            }

        }
    }
}
