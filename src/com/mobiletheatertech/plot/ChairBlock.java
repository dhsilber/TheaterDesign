package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Represents a rectangular block of individual chairs, such as a hotel would lay out in a function
 * room for audience seating.
 * <p/>
 * XML tag is 'chairblock'. Required attributes are the set of: 'x', 'y', 'width', and 'depth', or
 * just 'perimeter'. Coordinates are relative to the page origin.
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

    private double x;
    private double y;
    private double width;
    private double depth;
    private String perimeter = null;
    private Shape perimeterShape = null;

    private static final String COLOR = "black";
    private static final String CHAIR = "chair";

//    /**
//     * Construct a {@code ChairBlock} for each element in a list of XML nodes.
//     *
//     * @param list of 'chairblock' nodes
//     * @throws AttributeMissingException if any attribute is missing from any {@code ChairBlock}
//     */
////   This ends up copied to each thing that inherits from Minder. There needs to be a factory somewhere.
//    public static void ParseXML(NodeList list)
//            throws AttributeMissingException, DataException, InvalidXMLException {
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item(index);
//
//            // Much of this copied to Suspend.Suspend - refactor
//            if (null != node) {
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    new ChairBlock(element);
//                }
//            }
//        }
//    }

    /**
     * Construct a {@code ChairBlock} from an XML Element.
     *
     * @param element DOM Element defining a block of chairs
     * @throws AttributeMissingException if any attribute is missing
     */
    public ChairBlock(Element element) throws AttributeMissingException, DataException, InvalidXMLException {
        super(element);

        String exceptionMessage =
                "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.";

        x = getOptionalIntegerAttribute(element, "x");
        y = getOptionalIntegerAttribute(element, "y");
        width = getOptionalIntegerAttribute(element, "width");
        depth = getOptionalIntegerAttribute(element, "depth");
        perimeter = getOptionalStringAttribute(element, "perimeter");

        if (!perimeter.isEmpty() && (0 != x && 0 != y && 0 != width && 0 != depth)) {
            throw new InvalidXMLException(exceptionMessage);
        }

        if (perimeter.isEmpty() && (0 == x || 0 == y || 0 == width || 0 == depth)) {
            throw new InvalidXMLException(exceptionMessage);
        }

        if (!perimeter.isEmpty()) {
            perimeterShape = new Shape(perimeter);
            x = perimeterShape.x();
            y = perimeterShape.y();
            width = perimeterShape.width();
            depth = perimeterShape.depth();
        }

        new Layer(LAYERTAG, LAYERNAME);
    }

    @Override
    public void verify() {
    }


    /**
     * Generate SVG DOM for a {@code ChairBlock}. <p/> The first {@code ChairBlock} generated also
     * generates the symbol definition used for each individual chair.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode - currently only supports {@code View.PLAN}
     */
    @Override
    public void dom(Draw draw, View mode) {
        if (View.PLAN != mode) {
            return;
        }

        if (!SYMBOLGENERATED) {
            SvgElement defs = draw.element("defs");
            draw.appendRootChild(defs);

            SvgElement symbol = defs.symbol( draw, CHAIR );
//            draw.element("symbol");
//            symbol.setAttribute("id", "chair");
//            symbol.setAttribute("overflow", "visible");
            defs.appendChild(symbol);

            int sideways = CHAIRWIDTH / 3;
            int forward = CHAIRDEPTH / 2;
            String path =                     "M -" + sideways + " -" + forward +
                    " L -" + sideways + " " + forward +
                    " L " + sideways + " " + forward +
                    " L " + sideways + " -" + forward;

            SvgElement perimeter = symbol.path( draw, path, COLOR );
//            draw.element("path");
//            perimeter.setAttribute("fill", "none");
//            perimeter.setAttribute("stroke", "black");
//            perimeter.setAttribute("stroke-width", "2");
//            perimeter.setAttribute("d",
//                    "M -" + sideways + " -" + forward +
//                            " L -" + sideways + " " + forward +
//                            " L " + sideways + " " + forward +
//                            " L " + sideways + " -" + forward
//            );
//            symbol.appendChild(perimeter);

            SYMBOLGENERATED = true;
        }

        SvgElement group = svgClassGroup( draw, LAYERTAG );
//        draw.element("g");
//        group.setAttribute("class", LAYERTAG);
        draw.appendRootChild(group);

        double rowCount = depth / (CHAIRDEPTH + FOOTSPACE);
        double rowOffset = FOOTSPACE + CHAIRDEPTH / 2;
        double columnCount = width / CHAIRWIDTH;
        int columnOffset = CHAIRWIDTH / 2 + 2;
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            Double yValue = y + rowIndex * (CHAIRWIDTH + FOOTSPACE) + rowOffset;
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                Double xValue = x + columnIndex * CHAIRWIDTH + columnOffset;
                if (null != perimeterShape && !perimeterShape.fits(xValue, yValue, CHAIRWIDTH, CHAIRDEPTH)) {
                    continue;
                }

                SvgElement use = group.use( draw, CHAIR, xValue.intValue(), yValue.intValue() );
//                draw.element("use");
//                use.setAttribute("xlink:href", "#chair");
////                System.err.println( "x: "+x+", columnIndex: "+columnIndex+", xValue: "+xValue );
//                use.setAttribute("x", xValue.toString());
////                System.err.println( "y: "+y+", rowIndex: "+rowIndex+", yValue: "+yValue );
//                use.setAttribute("y", yValue.toString());

//                group.appendChild(use);
//                draw.appendRootChild( use );
            }

        }
    }
}
