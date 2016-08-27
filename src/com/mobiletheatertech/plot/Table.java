package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

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
public class Table extends Stackable implements Legendable {

    /**
     * Name of {@code Layer} of {@code Table}s.
     */
    public static final String LAYERNAME = "Tables";

    /**
     * Tag for {@code Layer} of {@code Table}s.
     */
    public static final String LAYERTAG = "table";

    private Double width = null;
    private Double depth = null;
    private Double height = null;
    private Double x = null;
    private Double y = null;
    private Double z = null;

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
            throws AttributeMissingException, DataException, InvalidXMLException, LocationException, ReferenceException, SizeException {
        super(element);

        id = getOptionalStringAttribute( "id" );

        width = getDoubleAttribute( "width" );
        depth = getDoubleAttribute( "depth" );
        height = getDoubleAttribute( "height" );
        x = getDoubleAttribute( "x" );
        y = getDoubleAttribute( "y" );
        z = getDoubleAttribute( "z" );

        if (0 >= width) throw new SizeException("Table", "width");
        if (0 >= depth) throw new SizeException("Table", "depth");
        if (0 >= height) throw new SizeException("Table", "height");


        if (!Venue.Contains2D(new Rectangle(x.intValue(), y.intValue(), width.intValue(), depth.intValue()))) {
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

//        new Category( Tag, this.getClass() );
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
        Double ex = x;
        Double wy = y;
        Double ze = z;

        Double lastWidth = 0.0;

        for ( Thing item : thingsOnThis) {
            ex = Math.max( ex, item.point.x() );
            wy = Math.max( wy, item.point.y() );
            ze = Math.max( ze, item.point.z() );

            lastWidth = item.solid.width();
        }
        Thing thing = new Thing();
        thing.point = new Point( x, wy + lastWidth, z + height );
        thing.solid = shape;

        thingsOnThis.add( thing );

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

    @Override
    public void legendCountReset() {
//        Count = 0;
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }


    public String toString() {
        return "Table ("+id+"): "+height+", "+width+", "+depth+" at "+x+", "+y+", "+z+".";
    }
}
