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

    private Integer x = null;
    private Integer y = null;

    /**
     * Extract each hangpoint description element from a list of XML nodes.
     *
     * @param list Set of hangpoint nodes
     * @throws AttributeMissingException This ends up copied to each thing that inherits from
     *                                   Minder. There needs to be a factory somewhere.
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     */
    public static void ParseXML( NodeList list ) throws AttributeMissingException, InvalidXMLException, LocationException {

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
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     * @throws LocationException         if some coordinate is outside the area of the {@link
     *                                   Venue}.
     */
    HangPoint( Element element ) throws AttributeMissingException, InvalidXMLException, LocationException {
        super( element );

        id = getOptionalStringAttribute( element, "id" );
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
     * Find a specific {@code HangPoint} in the set of plot objects.
     *
     * @param id Name of {@code HangPoint} to find
     * @return {@code HangPoint} which matches specified {@code id}.
     */
    public static HangPoint Find( String id ) {

        for (Minder thingy : Drawable.List()) {
            if (HangPoint.class.isInstance( thingy )) {
                if (((HangPoint) thingy).id.equals( id )) {
                    return (HangPoint) thingy;
                }
            }
        }
        return null;
    }

    /**
     * Provide the location of this {@code HangPoint}.
     *
     * @return coordinate of this {@code HangPoint}'s location
     */
    public Point locate() {
//        System.out.println( "Hangpoint (" + id + ") locate(): " + Venue.Height() );
        return new Point( x, y, Venue.Height() );

    }

    @Override
    public void verify() throws InvalidXMLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Draw a {@code HangPoint} onto the provided plan canvas.
     *
     * @param canvas drawing media.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLUE );
        canvas.draw( new Line2D.Float( x - 2, y - 2, x + 2, y + 2 ) );
        canvas.draw( new Line2D.Float( x + 2, y - 2, x - 2, y + 2 ) );
    }

    @Override
    public void drawSection( Graphics2D canvas ) {
    }

    @Override
    public void drawFront( Graphics2D canvas ) {
    }

    @Override
    public void dom( Draw draw, View mode ) {
    }

}
