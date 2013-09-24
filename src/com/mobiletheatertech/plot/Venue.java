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
 * XML tag is 'venue'. Exactly one venue must be defined. Children may be any number of 'hangpoint'
 * and 'airwall' tags. Required attributes are 'name', 'width', 'depth', and 'height'. (I expect to
 * move the 'name' attribute into its own tag at some point.)
 *
 * @author dhs
 * @since 0.0.2
 */
public class Venue extends Minder implements Legendable {

    private static Venue StaticVenue = null;
    private String name = null;
    private Integer width = null;
    private Integer depth = null;
    private Integer height = null;

    /**
     * Extract the venue description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException
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
    public Venue( Element element ) throws AttributeMissingException {
        name = element.getAttribute( "name" );
        if (name.isEmpty()) {
            throw new AttributeMissingException( "Venue", null, "name" );
        }
        width = getIntegerAttribute( element, "width" );
        depth = getIntegerAttribute( element, "depth" );
        height = getIntegerAttribute( element, "height" );

        // Record the extreme point.
        new Point( width, depth, height );

        StaticVenue = this;

        Legend.Register( this, 30, 12 );
    }

    /**
     * Determine if the provided {@code Box} fits entirely within the {@code Venue}.
     *
     * @param box (Hopefully) inner {@code Box}.
     * @return true if inner {@code Box} fits entirely within the Venue.
     * @since 0.0.6
     */
    public static boolean Contains( Box box ) {
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
    public static boolean Contains2D( Rectangle rectangle ) {

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
    public static int Contains2D( int x, int y ) {

        Rectangle area = new Rectangle( 0, 0, StaticVenue.width, StaticVenue.depth );

        return area.outcode( x, y );
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Width() {
        return StaticVenue.width;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Depth() {
        return StaticVenue.depth;
    }

    /**
     * Provides the height of the venue.
     *
     * @return height of the venue
     */
    public static int Height() {
        return StaticVenue.height;
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
     * Make the venue's name into the title of the drawing.
     *
     * @param draw Provide access to the generated DOM.
     */
    @Override
    public void dom( Draw draw, View mode ) {

        StringBuilder title = new StringBuilder( name + " - " );

        switch (mode) {
            case PLAN:
                title.append( "Plan view" );
                break;
            case SECTION:
                title.append( "Section view" );
                break;
            case FRONT:
                title.append( "Front view" );
                break;

        }
        draw.setDocumentTitle( title.toString() );
    }

    /**
     * Callback used by {@code Legend} to allow this object to generate the information it needs to
     * put into the legend area.
     * <p/>
     * {@code Venue} puts out a 'text' element containing the name of the venue.
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
        text.setAttribute( "font-family", "serif" );
        text.setAttribute( "font-size", "10" );

        draw.appendRootChild( text );

        Text foo = draw.document().createTextNode( name );
        text.appendChild( foo );

        return new PagePoint( start.x(), start.y() + 12 );
    }
}
