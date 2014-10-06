package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.ArrayList;

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
public class Table extends Stackable implements Legendable {

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }

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

    static final String CATEGORY = "table";

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

        id = getOptionalStringAttribute(element, "id");

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

        new Category( CATEGORY, this.getClass() );
    }

    public static Table Select( String type ) {
        for (ElementalLister selection : LIST) {
            if( Table.class.isInstance( selection )) {
                Table selected = (Table) selection;
                if (selected.id.equals( type )) {
                    return selected;
                }
            }
        }
        return null;
    }

    /**
     *
     *
     * @throws InvalidXMLException
     */
    @Override
    public void verify() throws InvalidXMLException {
    }

    /**
     * Find a place for the shape given to fit on this table.
     *
     * @param shape
     * @return
     */
    public Point location( Solid shape ) {
        int ex = x;
        int wy = y;
        int ze = z;

        Double lastWidth = 0.0;

        for ( Thing item : things ) {
            ex = Math.max( ex, item.point.x() );
            wy = Math.max( wy, item.point.y() );
            ze = Math.max( ze, item.point.z() );

            lastWidth = item.solid.getWidth();
        }
        Thing thing = new Thing();
        thing.point = new Point( x, wy + lastWidth.intValue(), z + height );
        thing.solid = shape;

        things.add( thing );

        return thing.point;
    }

    @Override
    public void dom(Draw draw, View mode) {
        switch (mode) {
            case TRUSS:
                return;
        }
        if (View.PLAN != mode) {
            return;
        }

//        System.err.println("Drawing Table "+id+".");


        // Plot attribute is 'depth'. SVG attribute is 'height'.
        SvgElement tableElement = draw.rectangle( draw, x, y, width, depth, "brown" )
                .svgClass(LAYERTAG)
        ;

//        Element tableElement = draw.element("rect");
//        tableElement.setAttribute("class", LAYERTAG);
//        draw.appendRootChild(tableElement);
//        tableElement.setAttribute("x", x.toString());
//        tableElement.setAttribute("y", y.toString());
//        tableElement.setAttribute("width", width.toString());
//        tableElement.setAttribute("height", depth.toString());
//        tableElement.setAttribute("fill", "none");
//        tableElement.setAttribute("stroke", "brown");
    }

    public String toString() {
        return "Table ("+id+"): "+height+", "+width+", "+depth+" at "+x+", "+y+", "+z+".";
    }
}
