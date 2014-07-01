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
 *
 * @author dhs
 * @since 0.0.2
 */
public class Venue extends Minder implements Legendable {

    public final static String ONETOMANY = "one-to-many";
    public final static String ONETOONE = "one-to-one";

    private static Venue StaticVenue = null;
    private String room = null;
    private Integer width = null;
    private Integer depth = null;
    private Integer height = null;
    private String circuiting;
    private String building;

    /**
     * Extract the venue description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException
    {

        Node node = list.item( 0 );
        if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            new Venue( element );
        }
    }

    /**
     * Construct a {@code Venue} from an XML Element.
     *
     * @param element DOM Element defining a venue.
     * @throws AttributeMissingException if any attribute is missing.
     */
    public Venue( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

//        room = element.getAttribute( "room" );
//        if (room.isEmpty()) {
//            throw new AttributeMissingException( "Venue", null, "room" );
//        }
        room = getStringAttribute( element, "room" );
        width = getIntegerAttribute( element, "width" );
        depth = getIntegerAttribute( element, "depth" );
        height = getIntegerAttribute( element, "height" );
        building = getOptionalStringAttribute( element, "building" );
        circuiting = getOptionalStringAttribute( element, "circuiting" );
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

    /**
     * Determine if the provided {@code Box} fits entirely within the {@code Venue}.
     *
     * @param box (Hopefully) inner {@code Box}.
     * @return true if inner {@code Box} fits entirely within the Venue.
     * @since 0.0.6
     */
    public static boolean Contains( Box box ) throws ReferenceException {
        if (null == StaticVenue) {
            throw new ReferenceException( "Venue is not defined." );
        }

        Box room = new Box( new Point( 0, 0, 0 ), StaticVenue.width, StaticVenue.depth,
                            StaticVenue.height );
        return room.contains( box );
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

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width, StaticVenue.depth );

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

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width, StaticVenue.depth );

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
    public static int Width() throws ReferenceException {
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
    public static int Depth() throws ReferenceException {
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
    public static int Height() throws ReferenceException {
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

    /**
     * Draw a plan view of this {@code Venue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawPlan( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, width, depth ) );
    }

    /**
     * Draw a section view of this {@code Venue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, depth, height ) );
    }

    /**
     * Draw a front view of this {@code Venue} to the provided canvas.
     *
     * @param canvas drawing region.
     */
    @Override
    public void drawFront( Graphics2D canvas ) {
        canvas.setPaint( Color.BLACK );
        canvas.draw( new Rectangle( 0, 0, width, height ) );
    }

    /**
     * Make the venue's room into the title of the drawing.
     *
     * @param draw Provide access to the generated DOM.
     */
    @Override
    public void dom( Draw draw, View mode ) {
        switch (mode) {
            case TRUSS:
                return;
        }
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
        Element text = draw.element( "text" );
        Integer x = start.x() + 10;
        text.setAttribute( "x", x.toString() );
        text.setAttribute( "y", start.y().toString() );
        text.setAttribute( "fill", "black" );
        text.setAttribute( "stroke", "none" );
        text.setAttribute( "font-family", "serif" );
        text.setAttribute( "font-size", "10" );

        draw.appendRootChild( text );

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
