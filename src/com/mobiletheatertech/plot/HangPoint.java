package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * <p> Hard hang point built into the ceiling of the venue. </p><p> XML tag is 'hangpoint'. There
 * are no children. Required attributes are 'x' and 'y', which define its location relative to the
 * venue. The z-coordinate is derived from the height of the venue. Optional attribute is 'id',
 * which becomes necessary if a hangpoint is to be referenced. </p>
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPoint extends Minder {

    private String id = null;
    private Integer x = null;
    private Integer y = null;

    /**
     * Extract each hangpoint description element from a list of XML nodes.
     *
     * @param list Set of hangpoint nodes
     * @throws AttributeMissingException This ends up copied to each thing that inherits from
     *                                   Minder. There needs to be a factory somewhere.
     */
    public static void ParseXML( NodeList list ) throws AttributeMissingException, LocationException {

        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new HangPoint( element );
                }
            }
        }
    }

    /**
     * Create a {@code HangPoint}
     * <p/>
     * It must be within the bounds of the {@link Venue}.
     *
     * @param element describes this {@code HangPoint}.
     * @throws AttributeMissingException if some required attribute is missing.
     * @throws LocationException         if some coordinate is outside the area of the {@link
     *                                   Venue}.
     */
    HangPoint( Element element ) throws AttributeMissingException, LocationException {
        id = element.getAttribute( "id" );
        System.out.println( "New Hangpoint: " + id );
        System.out.println( "New Hangpoint: " + this );
        x = getIntegerAttribute( element, "x" );
        y = getIntegerAttribute( element, "y" );

        int outcode = Venue.Contains2D( x, y );
        switch (outcode) {
            case Rectangle.OUT_LEFT:
            case Rectangle.OUT_RIGHT:
                throw new LocationException(
                        "HangPoint x value outside boundary of the venue" );
            case Rectangle.OUT_TOP:
            case Rectangle.OUT_BOTTOM:
                throw new LocationException(
                        "HangPoint y value outside boundary of the venue" );
        }

    }

    /**
     * @param id Name of {@code HangPoint} to find
     * @return {@code HangPoint} which matches specified {@code id}.
     */
    public static HangPoint Find( String id ) {

        System.err.println( "HangPoint.Find" );

        for (Minder thingy : Drawable.List()) {
            if (HangPoint.class.isInstance( thingy )) {
                if (((HangPoint) thingy).id.equals( id )) {
                    return (HangPoint) thingy;
                }
            }
        }
        return null;
    }

    public Point locate() {
        System.out.println( "Hangpoint (" + id + ") locate(): " + Venue.Height() );
        return new Point( x, y, Venue.Height() );

    }

    /**
     * Draw a {@code HangPoint} onto the provided plan canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLUE );
        canvas.draw( new Line2D.Float( x - 2, y - 2, x + 2, y + 2 ) );
        canvas.draw( new Line2D.Float( x + 2, y - 2, x - 2, y + 2 ) );
    }

    /**
     * Draw a {@code HangPoint} onto the provided section canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
    }

    /**
     * Draw a {@code HangPoint} onto the provided front canvas.
     *
     * @param canvas drawing space.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
    }

    @Override
    public void dom( Draw draw ) {
    }

}
