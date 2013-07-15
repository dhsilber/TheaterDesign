package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

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
public class Stage extends Minder {

    private Integer width = null;
    private Integer depth = null;
    private Integer x = null;
    private Integer y = null;
    private Integer z = null;

    /**
     * Extract the stage description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     * @throws LocationException         If the stage is outside the {@code Venue}.
     * @throws SizeException             If a length attribute is too short.
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, LocationException, SizeException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Stage( element );
            }
        }
    }


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
            throws AttributeMissingException, LocationException, SizeException
    {
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
    }

    /**
     * Draw a {@code Stage} onto the provided plan canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.ORANGE );
        canvas.draw( new Rectangle( x, y, width, depth ) );
    }

    /**
     * Draw a {@code Stage} onto the provided section canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        int bottom = Venue.Height();
        canvas.setPaint( Color.ORANGE );
        canvas.draw( new Line2D.Float( y, bottom, y, bottom - z ) );
        canvas.draw( new Line2D.Float( y, bottom - z, y + depth, bottom - z ) );
        canvas.draw( new Line2D.Float( y + depth, bottom - z, y + depth, bottom ) );
    }

    /**
     * Draw a {@code Stage} onto the provided front canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
        int bottom = Venue.Height();
        canvas.setPaint( Color.ORANGE );
        canvas.draw( new Line2D.Float( x, bottom, x, bottom - z ) );
        canvas.draw( new Line2D.Float( x, bottom - z, x + width, bottom - z ) );
        canvas.draw( new Line2D.Float( x + width, bottom - z, x + width, bottom ) );
    }

    @Override
    public void dom( Draw draw ) {
    }
}
