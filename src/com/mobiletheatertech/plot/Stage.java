package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;
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

    private Integer width = null;
    private Integer depth = null;
    private Integer x = null;
    private Integer y = null;
    private Integer z = null;

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }

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
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException, SizeException
    {
        super( element );

        width = getIntegerAttribute( element, "width" );
        depth = getIntegerAttribute( element, "depth" );
        x = getIntegerAttribute( element, "x" );
        y = getIntegerAttribute( element, "y" );
        z = getIntegerAttribute( element, "z" );

        if (0 >= width) throw new SizeException( "Stage", "width" );
        if (0 >= depth) throw new SizeException( "Stage", "depth" );


        if (!Venue.Contains2D( new Rectangle( x, y, width, depth ) )) {
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
        thing.point = new Point( x, wy + lastWidth.intValue(), z );
        thing.solid = shape;

        things.add( thing );

        return thing.point;
    }

    @Override
    public void dom( Draw draw, View mode ) throws ReferenceException {
        SvgElement element = null;
        int bottom = Venue.Height();

        switch (mode) {
            case PLAN:
                element = draw.rectangle( draw, x, y, width, depth, COLOR );
//                draw.appendRootChild( element );
                break;
            case SECTION:
                element = draw.line( draw, y, bottom, y, bottom - z, COLOR );
//                draw.appendRootChild( element );
                element = draw.line( draw, y, bottom - z, y + depth, bottom - z, COLOR );
//                draw.appendRootChild( element );
                element = draw.line( draw, y + depth, bottom - z, y + depth, bottom, COLOR );
//                draw.appendRootChild( element );
                break;
            case FRONT:
                element = draw.line( draw, x, bottom, x, bottom - z, COLOR );
//                draw.appendRootChild( element );
                element = draw.line( draw, x, bottom - z, x + width, bottom - z, COLOR );
//                draw.appendRootChild( element );
                element = draw.line( draw, x + width, bottom - z, x + width, bottom, COLOR );
//                draw.appendRootChild( element );
                break;
            case TRUSS:
                break;
        }
    }
}
