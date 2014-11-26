package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.*;
import java.util.ArrayList;

/**
 * Defines a stage.
 * <p/>
 * XML tag is 'stage'. There are no children. Required attributes are 'width', 'depth', which define
 * the dimensions of the stage, and 'x', 'y', and 'z', which define its location relative to the
 * venue.
 *
 * @author dhs
 * @since 0.0.3
 */
public class Stage extends Stackable implements Legendable {

    private Double width = null;
    private Double depth = null;
    private Double x = null;
    private Double y = null;
    private Double z = null;

    private final static String COLOR = "orange";

    static final String CATEGORY = "stage";

    /**
     * Creates a {@code Stage}.
     * <p/>
     * It must fit into the {@link Venue}.
     *
     * @param element describes this {@code Stage}.
     * @throws AttributeMissingException
     * @throws LocationException
     */
    public Stage( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException, LocationException, ReferenceException, SizeException
    {
        super( element );

        width = getDoubleAttribute(element, "width");
        depth = getDoubleAttribute(element, "depth");
        x = getDoubleAttribute(element, "x");
        y = getDoubleAttribute(element, "y");
        z = getDoubleAttribute(element, "z");

        if (0 >= width) throw new SizeException( "Stage", "width" );
        if (0 >= depth) throw new SizeException( "Stage", "depth" );


        if (!Venue.Contains2D( new Rectangle( x.intValue(), y.intValue(), width.intValue(), depth.intValue() ) )) {
            throw new LocationException(
                    "Stage should not extend beyond the boundaries of the venue." );
        }
        if (0 > z) {
            throw new LocationException(
                    "Stage should not extend beyond the boundaries of the venue." );
        }
        if (z > Venue.Height()) {
            throw new LocationException(
                    "Stage should not extend beyond the boundaries of the venue." );
        }

        new Category( CATEGORY, this.getClass() );
    }

    public ArrayList<Device> risers() {
        return null;
    }

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
        thing.point = new Point( x, wy + lastWidth, z );
        thing.solid = shape;

        thingsOnThis.add( thing );

        return thing.point;
    }

    @Override
    public void dom( Draw draw, View mode ) throws ReferenceException {
        Double bottom = Venue.Height();

        switch (mode) {
            case PLAN:
                draw.rectangle( draw, x, y, width, depth, COLOR );
                break;
            case SECTION:
                draw.line( draw, y, bottom, y, bottom - z, COLOR );
                draw.line( draw, y, bottom - z, y + depth, bottom - z, COLOR );
                draw.line( draw, y + depth, bottom - z, y + depth, bottom, COLOR );
                break;
            case FRONT:
                draw.line( draw, x, bottom, x, bottom - z, COLOR );
                draw.line( draw, x, bottom - z, x + width, bottom - z, COLOR );
                draw.line( draw, x + width, bottom - z, x + width, bottom, COLOR );
                break;
            case TRUSS:
                break;
        }
    }

    @Override
    public void countReset() {
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }
}
