package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Generic pipe.
 * <p/>
 * XML tag is 'pipe'. Required attributes are 'length', 'x', 'y', and 'z'.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Pipe extends Minder {

    /**
     * Length of pipe. I'm using inches so that the line thickness isn't ridiculous, but that isn't
     * enforced.
     */
    private Integer length = null;

    /**
     * Start of pipe.
     */
    private Point origin = null;

    /**
     * Origin of {@code Box} which defines area used by this pipe.
     */
    private Point boxOrigin = null;

    /**
     * Diameter of a generic pipe.
     */
    private static int Diameter = 2;

    /**
     * Extract each Pipe description element from a list of XML nodes.
     *
     * @param list Set of hangpoint nodes
     * @throws AttributeMissingException This ends up copied to each thing that inherits from
     *                                   Minder. There needs to be a factory somewhere.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, LocationException, SizeException
    {

        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new Pipe( element );
                }
            }
        }
    }

    /**
     * Construct a {@code Pipe} from an XML Element.
     *
     * @param element DOM Element defining a pipe.
     * @throws AttributeMissingException if any attribute is missing.
     * @throws SizeException             if the length is too short.
     * @throws LocationException         if any part of the pipe is outside the bounds of the {@code
     *                                   Venue}.
     */
    /*
    A natural origin of a pipe is the center of one end. To position
     its box in space, a small offset has to be applied.
     */
    public Pipe( Element element )
            throws AttributeMissingException, LocationException, SizeException
    {
        length = getIntegerAttribute( element, "length" );
        Integer x = getIntegerAttribute( element, "x" );
        Integer y = getIntegerAttribute( element, "y" );
        Integer z = getIntegerAttribute( element, "z" );
        origin = new Point( x, y, z );
        boxOrigin = new Point( x, y - 1, z - 1 );

        if (0 >= length) throw new SizeException( "Pipe", "length" );

        Box box = new Box( boxOrigin, length, Diameter, Diameter );

        if (!Venue.Contains( box )) {
            throw new LocationException(
                    "Pipe should not extend beyond the boundaries of the venue." );
        }
    }

    /**
     * Draw this {@code pipe} onto the plan view.
     *
     * @param canvas Medium on which to draw.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );

        canvas.draw( new Rectangle( origin.x(), origin.y() - 1, length, Diameter ) );
    }

    /**
     * Draw this {@code pipe} onto the section view.
     *
     * @param canvas Medium on which to draw.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        int bottom = Venue.Height();

        canvas.setPaint( Color.BLACK );

        System.out.println( "Pipe section Z: " + origin.z() );
        canvas.draw( new Ellipse2D.Float( origin.y() - 1, bottom - origin.z() - 1, Diameter,
                                          Diameter ) );
    }

    /**
     * Draw this {@code pipe} onto the front view.
     *
     * @param canvas Medium on which to draw.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
        int bottom = Venue.Height();

        canvas.setPaint( Color.BLACK );

        System.out.println( "Pipe front Z: " + origin.z() );
        canvas.draw( new Rectangle( origin.x(), bottom - origin.z() - 1, length, Diameter ) );
    }

    @Override
    public void dom( Draw draw ) {
    }

}
