package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.awt.*;

/**
 * <p> Hard hang point built into the ceiling of the venue. </p><p> XML tag is 'hangpoint'. There
 * are no children. Required attributes are 'x' and 'y', which define its location relative to the
 * venue. The z-coordinate is derived from the height of the venue. Optional attribute is 'id',
 * which becomes necessary if a hangpoint is to be referenced. </p>
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPoint extends MinderDom {

    /**
     * Name of {@code Layer} of {@code HangPoint}s.
     */
    public static final String LAYERNAME = "Hangpoints";

    /**
     * Name of {@code Layer} of {@code HangPoint}s.
     */
    public static final String LAYERTAG = "hangpoint";

    // This is public to allow for a terrible, terrible hack in MinderDom.DomAllTruss().
    public static Boolean SYMBOLGENERATED = false;

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
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException
    {

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
    HangPoint( Element element )
            throws AttributeMissingException, InvalidXMLException, LocationException, ReferenceException
    {
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

        new Layer( LAYERNAME, LAYERTAG );
    }

    /**
     * Find a specific {@code HangPoint} in the set of plot objects.
     *
     * @param id Name of {@code HangPoint} to find
     * @return {@code HangPoint} which matches specified {@code id}.
     */
    public static HangPoint Find( String id ) {

        for (MinderDom thingy : Drawable.List()) {
            if (HangPoint.class.isInstance( thingy )) {
                if (((HangPoint) thingy).id.equals( id )) {
                    return (HangPoint) thingy;
                }
            }
        }
        return null;
    }

    public static void Draw( Draw draw, Integer x, Integer y, Integer textX, Integer textY, String id ) {
//System.out.println( "HangPoint.Draw( "+x.toString()+", "+ y.toString()+", "+ textX.toString()+", "+ textY.toString()+", "+ id+" )" );

        Element group = draw.element( "g" );
        group.setAttribute( "class", LAYERTAG );
        draw.appendRootChild( group );

        Element use = draw.element( "use" );
        use.setAttribute( "xlink:href", "#hangpoint" );
        use.setAttribute( "x", x.toString() );
        use.setAttribute( "y", y.toString() );
        group.appendChild( use );

        Element idText = draw.element( "text" );
        idText.setAttribute( "x", textX.toString() );
        idText.setAttribute( "y", textY.toString() );
        idText.setAttribute( "fill", "blue" );
        idText.setAttribute( "stroke", "none" );
        idText.setAttribute( "font-family", "sans-serif" );
        idText.setAttribute( "font-weight", "100" );
        idText.setAttribute( "font-size", "12pt" );
        idText.setAttribute( "text-anchor", "left" );
        group.appendChild( idText );

        Text textId = draw.document().createTextNode( id );
        idText.appendChild( textId );
    }

    /**
     * Provide the location of this {@code HangPoint}.
     *
     * @return coordinate of this {@code HangPoint}'s location
     */
    public Point locate() throws ReferenceException {
//        System.out.println( "Hangpoint (" + id + ") locate(): " + Venue.Height() );
        return new Point( x, y, Venue.Height() );

    }

    @Override
    public void verify() throws InvalidXMLException {
    }

//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) {
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) {
//    }

    /**
     * Draw a {@code HangPoint} onto the provided plan canvas.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        if (( View.PLAN == mode || View.TRUSS == mode ) && !SYMBOLGENERATED) {

            Element defs = draw.element( "defs" );
            draw.appendRootChild( defs );

            Element symbol = draw.element( "symbol" );
            symbol.setAttribute( "id", "hangpoint" );
            symbol.setAttribute( "overflow", "visible" );
            defs.appendChild( symbol );

            Element circle = draw.element( "circle" );
            circle.setAttribute( "fill", "none" );
            circle.setAttribute( "stroke", "blue" );
            circle.setAttribute( "stroke-width", "1" );
            circle.setAttribute( "cx", "0" );
            circle.setAttribute( "cy", "0" );
            circle.setAttribute( "r", "4" );
            symbol.appendChild( circle );

            Element line = draw.element( "line" );
            line.setAttribute( "x1", "0" );
            line.setAttribute( "y1", "-6" );
            line.setAttribute( "x2", "0" );
            line.setAttribute( "y2", "6" );
            line.setAttribute( "stroke", "blue" );
            line.setAttribute( "stroke-width", "1" );
            symbol.appendChild( line );

            Element line2 = draw.element( "line" );
            line2.setAttribute( "x1", "-6" );
            line2.setAttribute( "y1", "0" );
            line2.setAttribute( "x2", "6" );
            line2.setAttribute( "y2", "0" );
            line2.setAttribute( "stroke", "blue" );
            line2.setAttribute( "stroke-width", "1" );
            symbol.appendChild( line2 );

            SYMBOLGENERATED = true;
        }

        if (View.PLAN != mode) {
            return;
        }

        Integer unitTextX = x + 12;
        Integer unitTextY = y + 12;

        Draw( draw, x, y, unitTextX, unitTextY, id );
    }

}
