package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.awt.*;

/**
 * The space in which theater is to be done.
 * <p/>
 * All aspects of the lighting plot which are descriptions of the venue are encapsulated here.
 * <p/>
 * XML tag is 'venue'. Exactly one venue must be defined. Children may be any number of 'wall',
 * 'hangpoint', and/or 'airwall' tags. Required attributes are 'room', 'width', 'depth', and
 * 'height'. (I expect to move the 'room' attribute into its own tag at some point.)
 * <p/>
 * There are four circuiting modes. They control how luminaire information
 * is displayed and apply to the venue as a whole. They are:
 * <dl>
 *     <dt>default</dt><dd>Venue dimmers are patched to arbitrary circuits
 *     as needed. Displays a dimmer number in a
 * rectangle, a channel number in a circle, and a circuit number in a
 * hexagon </dd>
 * <dt>one-to-one</dt><dd>Venue dimmers are circuited to only one location or
 * there is no installed circuiting.
 * Displays only the dimmer and channel.</dd>
 * <dt>one-to-many</dt><dd>Venue dimmers are circuited to multiple locations.
 * Displays the channel as normal and the circuit
 * in the rectangle.</dd>
 * </dl>
 *
 * @author dhs
 * @since 0.0.2
 */
/*
Not yet implemented:
 * <dt>direct</dt><dd>Venue does not provide circuiting. Board does not
 * allow for patching. Displays the dimmer/channel number in the rectangle.</dd>
 *
 * also, make 'patched' be a synonym for the default. (Make attribute required?)
 */
public class Venue extends MinderDom implements Legendable {

    public final static String ONETOMANY = "one-to-many";
    public final static String ONETOONE = "one-to-one";

    private static Venue StaticVenue = null;
    private String room = null;
    private Double width = null;
    private Double depth = null;
    private Double height = null;
    private String circuiting;
    private String building;

    private static final String COLOR = "black";


    /**
     * Construct a {@code Venue} from an XML Element.
     *
     * @param element DOM Element defining a venue.
     * @throws AttributeMissingException if any attribute is missing.
     */
    public Venue( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

//System.out.println(  "Just inside VENUE constructor.");

        room = getStringAttribute( "room" );
        width  = getDoubleAttribute( "width" );
        depth  = getDoubleAttribute( "depth" );
        height = getDoubleAttribute( "height" );
        building   = getOptionalStringAttribute( "building" );
        circuiting = getOptionalStringAttribute( "circuiting" );
        switch (circuiting) {
            case "":
            case "one-to-one":
            case "one-to-many":
                break;
            default:
                throw new InvalidXMLException( "'circuiting' attribute invalid." );
        }

        // Record the extreme point.
        new Point( width, depth, height );

        StaticVenue = this;

//        Legend.Register( this, room.length() * 9, 12, LegendOrder.Room );
    }

    public static Venue Venue() {
        return StaticVenue;
    }

    /**
     * Determine if the provided {@code Space} fits entirely within the {@code Venue}.
     *
     * @param space (Hopefully) inner {@code Space}.
     * @return true if inner {@code Space} fits entirely within the Venue.
     * @since 0.0.6
     */
    public static boolean Contains( Space space ) throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        Space room = new Space( new Point( 0.0, 0.0, 0.0 ), StaticVenue.width, StaticVenue.depth,
                            StaticVenue.height );
        return room.contains( space );
    }

    /**
     * Confirm that a specified rectangle fits into this venue.
     *
     * @param rectangle area to check
     * @return true if specified rectangle fits into this {@code Venue}.
     */

    public static boolean Contains2D( Rectangle rectangle ) throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width.intValue(), StaticVenue.depth.intValue() );

        return area.contains( rectangle );
    }

    /**
     * Confirm that a specified set of x, y coordinates is located within this venue.
     *
     * @param x coordinate to check
     * @param y coordinate to check
     * @return outcode {@link Rectangle result of outcode call}
     */
    public static int Contains2D( int x, int y ) throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width.intValue(), StaticVenue.depth.intValue() );

        return area.outcode( x, y );
    }

    /**
     * Provides the room of the venue.
     *
     * @return room of the venue
     */
    public static String Name() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.room;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static Double Width() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.width;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static Double Depth() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.depth;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static Double Height() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.height;
    }

    /**
     * Provides the building of the venue.
     *
     * @return building of the venue
     */
    public static String Building() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.building;
    }

    /**
     * Provides the circuiting mode of the venue.
     *
     * @return height of the venue
     */
    public static String Circuiting() throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        return StaticVenue.circuiting;
    }

    @Override
    public void verify() {
    }

//    /**
//     * Draw a plan view of this {@code Venue} to the provided canvas.
//     *
//     * @param canvas drawing region.
//     */
//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLACK );
//        canvas.draw( new Rectangle( 0, 0, width, depth ) );
//    }
//
//    /**
//     * Draw a section view of this {@code Venue} to the provided canvas.
//     *
//     * @param canvas drawing region.
//     */
//    @Override
//    public void drawSection( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLACK );
//        canvas.draw( new Rectangle( 0, 0, depth, height ) );
//    }
//
//    /**
//     * Draw a front view of this {@code Venue} to the provided canvas.
//     *
//     * @param canvas drawing region.
//     */
//    @Override
//    public void drawFront( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLACK );
//        canvas.draw( new Rectangle( 0, 0, width, height ) );
//    }

    /**
     * Make the venue's room into the title of the drawing.
     *
     * @param draw Provide access to the generated DOM.
     */
    @Override
    public void dom( Draw draw, View mode ) {

        switch (mode) {
            case PLAN:
                // Don't actually need this. It just muddies up the drawing.
//                draw.rectangle( draw, 0, 0, width, depth, COLOR );
//                draw.appendRootChild( element );
                break;
            case SECTION:
                draw.rectangle(draw, 0.0, 0.0, depth, height, COLOR);
//                draw.appendRootChild( element );
                break;
            case FRONT:
                draw.rectangle(draw, 0.0, 0.0, width, height, COLOR);
//                draw.appendRootChild( element );
                break;
            case TRUSS:
                break;
        }
    }

    @Override
    public void countReset() {
//        Count = 0;
    }

    /**
     * Callback used by {@code Legend} to allow this object to generate the information it needs to
     * put into the legend area.
     * <p/>
     * {@code Venue} puts out a 'text' element containing the room of the venue.
     *
     * @param draw  Canvas/DOM manager
     * @param start position on the canvas for this legend entry
     * @return start point for next {@code Legend} item
     */
    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        Double x = start.x() + 10;
        SvgElement text = draw.text( draw, room, x, start.y(), Legend.TEXTCOLOR );
//        draw.element("text");
//        text.setAttribute( "x", x.toString() );
//        text.setAttribute( "y", start.y().toString() );
//        text.setAttribute( "fill", "black" );
//        text.setAttribute( "stroke", "none" );
        text.attribute( "font-family", "serif" );
        text.attribute( "font-size", "10" );

//        draw.appendRootChild( text );

        Text foo = draw.document().createTextNode( room );
        text.appendChild( foo );

        return new PagePoint( start.x(), start.y() + 12 );
    }

    public String toString() {
        return building+room+"\nWidth: "+width+
                ", Depth: "+depth+", Height: "+height;
    }

    public static String ToString() {
        return StaticVenue.toString();
    }
}
