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
     * @throws InvalidXMLException       if more than one 'proscenium' element is found or if null
     *                                   element is somehow presented to constructor
     * @throws LocationException         if the stage is outside the {@code Venue}
     * @throws SizeException             if a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException, LocationException,
            ReferenceException, SizeException
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

    /**
     * True if a {@code Proscenium} has been defined. False otherwise.
     *
     * @return true or false
     */
    public static boolean Active() {
        return ACTIVE;
    }

    /**
     * Provide the origin of the coordinates used for a lighting plot.
     *
     * @return center of proscenium at the upstage side of the openng
     */
    public static Point Origin() {
        return ORIGIN;
    }

    /**
     * Provide the drawing point, given a point relative to the origin established by the {@code
     * Proscenium}.
     *
     * @param unfixed point relative to the {@code Proscenium}'s origin
     * @return the drawing point
     */
    public static Point Locate( Point unfixed ) {
        return new Point(
                ORIGIN.x() + unfixed.x(),
                ORIGIN.y() - unfixed.y(),
                ORIGIN.z() + unfixed.z() );
    }

    /**
     * Construct a {@code Proscenium} from an XML Element.
     * <p/>
     * Keep track of the defined origin.
     *
     * @param element DOM Element defining a proscenium
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if more than one 'proscenium' element is found or if null
     *                                   element is somehow presented to constructor
     * @throws LocationException         if the proscenium would not fit in the venue
     * @throws SizeException             if any dimension is less than zero
     */
    public Proscenium( Element element )
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException, SizeException
    {
        super( element );

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
        if (0 >= height) throw new SizeException( "Proscenium", "height" );


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
    public void verify() throws InvalidXMLException {
    }

    /**
     * @param canvas drawing media
     */
    @Override
    public void drawPlan( Graphics2D canvas ) throws ReferenceException {
//        canvas.setPaint( Color.BLACK );
//        canvas.draw( new Rectangle( x - width / 2, y, width, depth ) );

        // Draw CenterLine
        float[] dashPattern = { 6, 2, 3, 2 };
        canvas.setStroke(
                new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dashPattern,
                                 0 ) );
        canvas.draw( new Line2D.Float( x, 0, x, Venue.Depth() ) );
        canvas.setStroke( new BasicStroke( 1 ) );
    }

    /**
     * @param canvas drawing media
     */
    @Override
    public void drawSection( Graphics2D canvas ) throws ReferenceException {
        int bottom = Venue.Height();
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( y, bottom - z - height, depth, height ) );
    }

    /**
     * @param canvas drawing media
     */
    @Override
    public void drawFront( Graphics2D canvas ) throws ReferenceException {
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

    /**
     * Draw the proscenium.
     * <p/>
     * Currently only drawing the plan view.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        if (View.PLAN == mode) {
            Integer startX = x - width / 2;
            Integer startY = y;
            Integer endX = x + width / 2;
            Integer endY = y + depth;

            // SR end of proscenium arch
            Element line = draw.element( "line" );
            line.setAttribute( "x1", startX.toString() );
            line.setAttribute( "y1", startY.toString() );
            line.setAttribute( "x2", startX.toString() );
            line.setAttribute( "y2", endY.toString() );
            line.setAttribute( "stroke", "black" );
            line.setAttribute( "stroke-width", "1" );
            draw.appendRootChild( line );

            // SL end of proscenium arch
            line = draw.element( "line" );
            line.setAttribute( "x1", endX.toString() );
            line.setAttribute( "y1", startY.toString() );
            line.setAttribute( "x2", endX.toString() );
            line.setAttribute( "y2", endY.toString() );
            line.setAttribute( "stroke", "black" );
            line.setAttribute( "stroke-width", "1" );
            draw.appendRootChild( line );

            // US side of proscenium arch
            line = draw.element( "line" );
            line.setAttribute( "x1", startX.toString() );
            line.setAttribute( "y1", startY.toString() );
            line.setAttribute( "x2", endX.toString() );
            line.setAttribute( "y2", startY.toString() );
            line.setAttribute( "stroke", "grey" );
            line.setAttribute( "stroke-opacity", "0.3" );
            line.setAttribute( "stroke-width", "1" );
            draw.appendRootChild( line );

            // DS side of proscenium arch
            line = draw.element( "line" );
            line.setAttribute( "x1", startX.toString() );
            line.setAttribute( "y1", endY.toString() );
            line.setAttribute( "x2", endX.toString() );
            line.setAttribute( "y2", endY.toString() );
            line.setAttribute( "stroke", "grey" );
            line.setAttribute( "stroke-opacity", "0.1" );
            line.setAttribute( "stroke-width", "1" );
            draw.appendRootChild( line );

        }
    }

    /**
     * Describe this {@code Proscenium}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "Proscenium: " + width + " by " + height + " by " + depth + " at (" + x + "," + y +
                "," + z + ")";
    }
}

