package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

/**
 * Generic pipe.
 * <p/>
 * XML tag is 'pipe'. Required attributes are 'length', 'x', 'y', and 'z'. Coordinates are relative
 * to the page origin, which is suboptimal for lighting plots.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Pipe extends Minder {

    private static ArrayList<Pipe> PIPELIST = new ArrayList<>();

    /**
     * Identifier of pipe. (optional)
     */
//    private String id = null;

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
     *
     * @param element DOM Element defining a pipe.
     * @throws AttributeMissingException if any attribute is missing.
     * @throws SizeException             if the length is too short.
     * @throws LocationException         if any part of the pipe is outside the bounds of the {@code
     *                                   Venue}.
     */
    /*
    A natural origin of a pipe is the center of one end. To position
     its drawing box in space, a small offset has to be applied.
     */
    public Pipe( Element element )
            throws AttributeMissingException, SizeException
    {
        PIPELIST.add( this );

        id = element.getAttribute( "id" );
        length = getIntegerAttribute( element, "length" );
        Integer x = getIntegerAttribute( element, "x" );
        Integer y = getIntegerAttribute( element, "y" );
        Integer z = getIntegerAttribute( element, "z" );
        start = new Point( x, y, z );

        if (0 >= length) throw new SizeException( "Pipe", "length" );
    }

    public Point location( Integer offset ) {
        Point point;
        if (Proscenium.Active()) {
            point = Proscenium.Locate( new Point( start.x() + offset,
                                                  start.y() - 1,
                                                  start.z() - 1 ) );
        }
        else {
            point = new Point( start.x() + offset, start.y(), start.z() );
        }
        System.out.println(
                "Pipe.location(): " + point.toString() + " Start: " + start.toString() );
        return point;
    }

    @Override
    public void verify() throws InvalidXMLException, LocationException {
        String identity = (id.equals( "" ))
                          ? this.toString()
                          : "Pipe (" + id + ")";

        if (Proscenium.Active()) {
//            Integer depth = Venue.Depth();
//            Point origin = Proscenium.Origin();
//            boxOrigin = new Point( origin.x() + start.x() - length / 2,
//                                   origin.y() - start.y() - 1,
//                                   origin.z() + start.z() - 1 );
            boxOrigin = Proscenium.Locate( new Point( start.x() - length / 2,
                                                      start.y() - 1,
                                                      start.z() - 1 ) );

            Box box = new Box( boxOrigin, length, Diameter, Diameter );

            if (!Venue.Contains( box )) {
                throw new LocationException(
                        identity +
                                " should not extend beyond the boundaries of the venue." );
            }
        }
        else {
            boxOrigin = new Point( start.x(), start.y() - 1, start.z() - 1 );

            Box box = new Box( boxOrigin, length, Diameter, Diameter );

            if (!Venue.Contains( box )) {
                throw new LocationException(
                        identity +
                                " should not extend beyond the boundaries of the venue." );
            }
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

        canvas.draw( new Rectangle( boxOrigin.x(), boxOrigin.y(), length, Diameter ) );
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

        System.out.println( "Pipe section Z: " + start.z() );
        canvas.draw( new Ellipse2D.Float( boxOrigin.y(), bottom - boxOrigin.z(), Diameter,
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

        System.out.println( "Pipe front Z: " + start.z() );
        canvas.draw( new Rectangle( boxOrigin.x(), bottom - boxOrigin.z(), length, Diameter ) );
    }

    @Override
    public void dom( Draw draw, View mode ) {
    }

    @Override
    public String toString() {
        return "Pipe {" +
//                "id='" + id + '\'' +
                ", origin=" + start +
                ", length=" + length +
                '}';
    }
}
