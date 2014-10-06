package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Generic pipe.
 * <p/>
 * XML tag is 'pipe'. Required attributes are 'length', 'x', 'y', and 'z'. Coordinates are relative
 * to the {@code Proscenium} origin, if any, otherwise relative to the page origin.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Pipe extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Pipe}s.
     */
    public static final String LAYERNAME = "Pipes";

    /**
     * Tag for {@code Layer} of {@code Pipe}s.
     */
    public static final String LAYERTAG = "pipe";

    /**
     * Diameter of a generic pipe.
     */
    public static final Integer DIAMETER = 2;

    private static ArrayList<Pipe> PIPELIST = new ArrayList<>();

    /**
     * Length of pipe. I'm using inches so that the line thickness isn't ridiculous, but that isn't
     * enforced.
     */
    private Integer length = null;

    /**
     * Start of pipe.
     */
    private Point start = null;

    /**
     * Origin of {@code Box} which defines area used by this pipe.
     */
    private Point boxOrigin = null;

    /**
     * Construct a {@code Pipe} for each element in a list of XML nodes.
     *
     * @param list of 'pipe' nodes
     * @throws AttributeMissingException if any attribute is missing from any {@code Pipe}
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     * @throws SizeException             if a length is too short
     */

    /*
    This ends up copied to each thing that inherits from
     *                                   Minder. There needs to be a factory somewhere.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException, SizeException
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
     * Find a specific {@code Pipe} from all that have been constructed.
     *
     * @param id of {@code Pipe} to find
     * @return {@code Pipe}, or {@code null} if not found
     */
    public static Pipe Select( String id ) {
        for (Pipe selection : PIPELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        return null;
    }

    /**
     * Construct a {@code Pipe} from an XML Element.
     * <p/>
     * Keep a list of defined pipes.
     *
     * @param element DOM Element defining a pipe
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     * @throws SizeException             if the length is too short
     */
    /*
    A natural origin of a pipe is the center of one end. To position
     its drawing box in space, a small start has to be applied.
     */
    public Pipe( Element element )
            throws AttributeMissingException, InvalidXMLException, SizeException
    {
        super( element );

        id = element.getAttribute( "id" );
        length = getIntegerAttribute( element, "length" );
        Integer x = getIntegerAttribute( element, "x" );
        Integer y = getIntegerAttribute( element, "y" );
        Integer z = getIntegerAttribute( element, "z" );
        start = new Point( x, y, z );

        if (0 >= length) throw new SizeException( this.toString(), "length" );

        PIPELIST.add( this );

        new Layer( LAYERNAME, LAYERTAG );
    }

    /**
     * Provide the drawing location of a point along this {@code Pipe}.
     *
     * @param offset in the x dimension
     * @return drawing location
     * @throws MountingException if location will be beyond the edge of the pipe
     */
    public Point location( Integer offset ) throws MountingException {
        Point point;
        if (Proscenium.Active()) {
            if ((start.x() < 0) && (start.x() + length > 0)) {
                // Given a pipe that crosses the centerline, the offset is from the centerline.
                point = Proscenium.Locate( new Point( offset,
                                                      start.y() - 1,
                                                      start.z() - 1 ) );

                if ((offset < start.x()) || (start.x() + length < offset)) {
                    throw new MountingException( "beyond the end of Pipe" );
                }
            }

            else {
                // For a pipe that doesn't cross the centerline, the offset is relative to the
                // start of the pipe.
                point = Proscenium.Locate( new Point( start.x() /*- (length / 2)*/ + offset,
                                                      start.y() - 1,
                                                      start.z() - 1 ) );
                if ((offset < 0) || (length < offset)) {
                    throw new MountingException( "beyond the end of Pipe" );
                }
            }
        }

        else {
            // When there is no proscenium
            point = new Point( start.x() + offset, start.y(), start.z() );
            if ((offset < 0) || (length < offset)) {
                throw new MountingException( "beyond the end of Pipe" );
            }
        }
//        System.out.println(
//                "Pipe.location(): " + point.toString() + " Start: " + start.toString() );
        return point;
    }

    /**
     * Confirm that this {@code Pipe}'s specification works with other Plot items.
     *
     * @throws LocationException if the pipe would at any point be outside of the venue
     */
    @Override
    public void verify() throws LocationException, ReferenceException {
        String identity = (id.equals( "" ))
                          ? this.toString()
                          : "Pipe (" + id + ")";

        if (Proscenium.Active()) {
//            Integer depth = Venue.Depth();
//            Point origin = Proscenium.Origin();
//            boxOrigin = new Point( origin.x() + start.x() - length / 2,
//                                   origin.y() - start.y() - 1,
//                                   origin.z() + start.z() - 1 );
            boxOrigin = Proscenium.Locate( new Point( start.x(),// - length / 2,
                                                      start.y() - 1,
                                                      start.z() - 1 ) );

            Box box = new Box( boxOrigin, length, DIAMETER, DIAMETER );

            if (!Venue.Contains( box )) {
                PIPELIST.remove( this );
                throw new LocationException(
                        identity +
                                " should not extend beyond the boundaries of the venue." );
            }
        }
        else {
            boxOrigin = new Point( start.x(), start.y() - 1, start.z() - 1 );

            Box box = new Box( boxOrigin, length, DIAMETER, DIAMETER );

            if (!Venue.Contains( box )) {
                PIPELIST.remove( this );
                throw new LocationException(
                        identity +
                                " should not extend beyond the boundaries of the venue." );
            }
        }
    }

//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) throws ReferenceException {
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) throws ReferenceException {
//    }

    /**
     * Generate SVG DOM for a {@code Pipe}
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     * @throws ReferenceException
     */
    @Override
    public void dom( Draw draw, View mode ) throws ReferenceException {
        Integer height = Venue.Height() - boxOrigin.z();


        Element group = draw.element( "g" );
        group.setAttribute( "class", LAYERTAG );
        draw.appendRootChild( group );

        Element dimmerRectangle = draw.element( "rect" );
        dimmerRectangle.setAttribute( "height", DIAMETER.toString() );
        dimmerRectangle.setAttribute( "fill", "none" );
        group.appendChild( dimmerRectangle );

        switch (mode) {
            case PLAN:
                dimmerRectangle.setAttribute( "x", boxOrigin.x().toString() );
                dimmerRectangle.setAttribute( "y", boxOrigin.y().toString() );
                dimmerRectangle.setAttribute( "width", length.toString() );
                break;
            case SECTION:
                dimmerRectangle.setAttribute( "x", boxOrigin.y().toString() );
                dimmerRectangle.setAttribute( "y", height.toString() );
                dimmerRectangle.setAttribute( "width", DIAMETER.toString() );
                break;
            case FRONT:
                dimmerRectangle.setAttribute( "x", boxOrigin.x().toString() );
                dimmerRectangle.setAttribute( "y", height.toString() );
                dimmerRectangle.setAttribute( "width", length.toString() );
                break;
            default:

        }
    }

    /**
     * Describe this {@code Pipe}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "Pipe { " +
//                "id='" + id + '\'' +
                "origin=" + start +
                ", length=" + length +
                " }";
    }
}
