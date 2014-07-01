package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Defines a table.
 * <p/>
 * XML tag is 'table'. There are no children. Required attributes are 'width', 'depth', which define
 * the dimensions of the table, and 'x', 'y', and 'z', which define its location relative to the
 * venue.
 *
 * @author dhs
 * @since 0.0.3
 */
public class Table extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Table}s.
     */
    public static final String LAYERNAME = "Tables";

    /**
     * Tag for {@code Layer} of {@code Table}s.
     */
    public static final String LAYERTAG = "table";

    private Integer width = null;
    private Integer depth = null;
    private Integer height = null;
    private Integer x = null;
    private Integer y = null;
    private Integer z = null;

    /**
     * Extract the table description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     * @throws LocationException         If the table is outside the {@code Venue}.
     * @throws SizeException             If a length attribute is too short.
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML(NodeList list)
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException, SizeException {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item(index);

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Table(element);
            }

        }
    }

    /**
     * Creates a {@code Table}.
     * <p/>
     * It must fit into the {@link Venue}.
     *
     * @param element describes this {@code Table}.
     * @throws AttributeMissingException
     * @throws LocationException
     */
    public Table(Element element)
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException, SizeException {
        super(element);

        width = getIntegerAttribute(element, "width");
        depth = getIntegerAttribute(element, "depth");
        height = getIntegerAttribute(element, "height");
        x = getIntegerAttribute(element, "x");
        y = getIntegerAttribute(element, "y");
        z = getIntegerAttribute(element, "z");

        if (0 >= width) throw new SizeException("Table", "width");
        if (0 >= depth) throw new SizeException("Table", "depth");
        if (0 >= height) throw new SizeException("Table", "height");


        if (!Venue.Contains2D(new Rectangle(x, y, width, depth))) {
            throw new LocationException(
                    "Table should not extend beyond the boundaries of the venue.");
        }
        if (0 > z) {
            throw new LocationException(
                    "Table should not extend beyond the boundaries of the venue.");
        }
        if (z + height > Venue.Height()) {
            throw new LocationException(
                    "Table should not extend beyond the boundaries of the venue.");
        }
    }

    @Override
    public void verify() throws InvalidXMLException {
    }

//    /**
//     * Draw a {@code Table} onto the provided plan canvas.
//     *
//     * @param canvas drawing space.
//     */
//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//        canvas.setPaint( Color.ORANGE );
//        canvas.draw( new Rectangle( x, y, width, depth ) );
//    }
//
//    /**
//     * Draw a {@code Table} onto the provided section canvas.
//     *
//     * @param canvas drawing space.
//     */
//    @Override
//    public void drawSection( Graphics2D canvas ) throws ReferenceException {
//        int bottom = Venue.Height();
//        canvas.setPaint( Color.ORANGE );
//        canvas.draw( new Line2D.Float( y, bottom, y, bottom - z ) );
//        canvas.draw( new Line2D.Float( y, bottom - z, y + depth, bottom - z ) );
//        canvas.draw( new Line2D.Float( y + depth, bottom - z, y + depth, bottom ) );
//    }
//
//    /**
//     * Draw a {@code Table} onto the provided front canvas.
//     *
//     * @param canvas drawing space.
//     */
//    @Override
//    public void drawFront( Graphics2D canvas ) throws ReferenceException {
//        int bottom = Venue.Height();
//        canvas.setPaint( Color.ORANGE );
//        canvas.draw( new Line2D.Float( x, bottom, x, bottom - z ) );
//        canvas.draw( new Line2D.Float( x, bottom - z, x + width, bottom - z ) );
//        canvas.draw( new Line2D.Float( x + width, bottom - z, x + width, bottom ) );
//    }

    @Override
    public void dom(Draw draw, View mode) {
        switch (mode) {
            case TRUSS:
                return;
        }
        if (View.PLAN != mode) {
            return;
        }

        Element tableElement = draw.element("rect");
        tableElement.setAttribute("class", LAYERTAG);
        draw.appendRootChild(tableElement);
        tableElement.setAttribute("x", x.toString());
        tableElement.setAttribute("y", y.toString());
        tableElement.setAttribute("width", width.toString());
        tableElement.setAttribute("height", depth.toString());
        tableElement.setAttribute("fill", "none");
        tableElement.setAttribute("stroke", "brown");
    }
}
