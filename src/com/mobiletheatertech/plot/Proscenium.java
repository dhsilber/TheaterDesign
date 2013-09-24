package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 7/30/13 Time: 1:59 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Represents a proscenium arch.
 * <p/>
 * XML tag is 'proscenium'.
 * <p/>
 * Required attributes are:<dl> <dt>x</dt><dd>coordinate of center of proscenium with respect to
 * venue</dd> <dt>y</dt><dd>coordinate of center of proscenium with respect to venue</dd>
 * <dt>z</dt><dd>coordinate of floor level of proscenium stage with respect to venue</dd>
 * <dt>width</dt><dd>width of proscenium opening</dd> <dt>depth</dt><dd>thickness of proscenium
 * wall</dd> <dt>height</dt><dd>height of proscenium opening</dd>
 *
 * @author dhs
 * @since 0.0.7
 */
public class Proscenium extends Minder {

    private static boolean ACTIVE = false;
    private static Point ORIGIN = null;

    private Integer width = null;
    private Integer depth = null;
    private Integer height = null;
    private Integer x = null;
    private Integer y = null;
    private Integer z = null;

    /**
     * Extract the stage description element from a list of XML nodes.
     *
     * @param list of XML nodes
     * @throws AttributeMissingException if a required attribute is missing
     * @throws LocationException         if the stage is outside the {@code Venue}
     * @throws SizeException             if a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, LocationException, SizeException, InvalidXMLException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Proscenium( element );
            }
        }
    }

    public static boolean Active() {
        return ACTIVE;
    }

    public static Point Origin() {
        return ORIGIN;
    }

    public static Point Locate( Point unfixed ) {
        return new Point(
                ORIGIN.x() + unfixed.x(),
                ORIGIN.y() - unfixed.y(),
                ORIGIN.z() + unfixed.z() );
    }

    public Proscenium( Element element )
            throws AttributeMissingException, LocationException, SizeException, InvalidXMLException
    {
        if (Active()) {
            throw new InvalidXMLException( "Multiple Prosceniums are not currently supported." );
        }

        width = getIntegerAttribute( element, "width" );
        depth = getIntegerAttribute( element, "depth" );
        height = getIntegerAttribute( element, "height" );
        x = getIntegerAttribute( element, "x" );
        y = getIntegerAttribute( element, "y" );
        z = getIntegerAttribute( element, "z" );

        if (0 >= width) throw new SizeException( "Proscenium", "width" );
        if (0 >= depth) throw new SizeException( "Proscenium", "depth" );
        if (0 >= height) throw new SizeException( "Proscenium", "depth" );

        System.err.println( this );


        if (!Venue.Contains2D( new Rectangle( x - width / 2, y, width, depth ) )) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue." );
        }
        if (0 > z) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue." );
        }
        if (z + height > Venue.Height()) {
            throw new LocationException(
                    "Proscenium should not extend beyond the boundaries of the venue." );
        }

        ACTIVE = true;
        ORIGIN = new Point( x, y, z );
    }

    @Override
    public String toString() {
        return "Proscenium: " + width + " by " + height + " by " + depth + " at (" + x + "," + y +
                "," + z + ")";
    }

    @Override
    public void verify() throws InvalidXMLException {
    }

    /**
     * @param canvas Drawing media.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( x - width / 2, y, width, depth ) );

        // Draw CenterLine
        float[] dashPattern = { 6, 2, 3, 2 };
        canvas.setStroke(
                new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dashPattern,
                                 0 ) );
        canvas.draw( new Line2D.Float( x, 0, x, Venue.Depth() ) );
        canvas.setStroke( new BasicStroke( 1 ) );
    }

    /**
     * @param canvas Drawing media.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        int bottom = Venue.Height();
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( y, bottom - z - height, depth, height ) );
    }

    /**
     * @param canvas Drawing media.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
        int bottom = Venue.Height();
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( x - width / 2, bottom - z - height, width, height ) );

        // Draw CenterLine
        float[] dashPattern = { 6, 2, 3, 2 };
        canvas.setStroke(
                new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dashPattern,
                                 0 ) );
        canvas.draw( new Line2D.Float( x, 0, x, Venue.Height() ) );
        canvas.setStroke( new BasicStroke( 1 ) );
    }

    @Override
    public void dom( Draw draw, View mode ) {
    }
}

