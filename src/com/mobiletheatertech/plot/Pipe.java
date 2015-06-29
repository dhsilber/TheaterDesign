package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.geom.Rectangle2D;

/**
 * Generic pipe.
 * <p/>
 * XML tag is 'pipe'. Required attributes are 'length', 'x', 'y', and 'z'.
 * Coordinates are relative
 * to the {@code Proscenium} origin, if any, otherwise relative to the page origin.
 *
 * If the pipe location is specified with a point, it is presumed to be horizontal
 * and aligned along the x axis.
 *
 * If the pipe is specified with a pair of anchor points, then the pipe can be in any
 * orientation. The start end is towards the lesser X value, or the lesser Y value,
 * or the lesser Z value, in that order.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Pipe extends Mountable implements Schematicable {

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
    public static final Double DIAMETER = 2.0;

    protected static final String CHEESEBOROUGH = "cheeseborough";

//    private static ArrayList<Pipe> PIPELIST = new ArrayList<>();

    private Element element = null;


    private PagePoint schematicPosition = null;


    /**
     * Length of pipe. I'm using inches so that the line thickness isn't ridiculous, but that isn't
     * enforced.
     */
    private Double length = null;

    /**
     * Start of pipe.
     */
    private Point start = null;

    /**
     * Origin of {@code Space} which defines area used by this pipe.
     */
    private Point boxOrigin = null;

    private Double orientation = null;

    private Double offsetX = null;

    private static final String COLOR = "black";

    private Cheeseborough support1 = null;
    private Cheeseborough support2 = null;
    private Point point1 = null;
    private Point point2 = null;
    private Double rotation = null;
    private Double overHang = null;


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
    public Pipe(Element element)
            throws AttributeMissingException, DataException,
            InvalidXMLException, MountingException, SizeException
    {
        super(element);

//        System.out.println( "Just inside Pipe constructor." );

        this.element = element;
//        id = element.attribute( "id" );
        length = getDoubleAttribute(element, "length");
        Double x = getOptionalDoubleAttributeOrNull(element, "x");
        Double y = getOptionalDoubleAttributeOrNull(element, "y");
        Double z = getOptionalDoubleAttributeOrNull( element, "z" );

        try {
            start = new Point(x, y, z);
        }
        catch ( NullPointerException e ) {
            if ( null != x || null != y || null != z ) {
                    throw new InvalidXMLException(
                            "Pipe (" + id + ") explicitly positioned must have x, y, and z coordinates" );
//            positioned = true;
            }
        }

        if (0 >= length) {
            Mountable.Remove(this);
            throw new SizeException(this.toString(), "length");
        }

        orientation = getOptionalDoubleAttributeOrZero(element, "orientation");
        offsetX = getOptionalDoubleAttributeOrZero(element, "offsetx");

        new Layer(LAYERTAG, LAYERNAME, COLOR );
    }

    /**
     * Confirm that this {@code Pipe}'s specification works with other Plot items.
     *
     * @throws LocationException if the pipe would at any point be outside of the venue
     */
    @Override
    public void verify() throws LocationException, ReferenceException {
        System.err.println( "Pipe starting.");
        if( null == start ) { System.err.println( "start is null at top of Pipe.verify()" ); }
        assert( null != id ) : "Id is null at top of verify()";

        String identity = "Pipe (" + id + ")";

//        System.err.println( "Identity: "+ identity );

        NodeList cheeseboroughList = element.getElementsByTagName( CHEESEBOROUGH );
        if( 2 == cheeseboroughList.getLength() ) {
            calculateLocationFromSupports( cheeseboroughList );
        }

        if (Proscenium.Active()) {
//            System.err.println( "Proscenium.Active()" );
            if (90.0 == orientation) {
//                System.err.println( "90.0 == orientation" );
                boxOrigin = Proscenium.Locate(new Point(start.x() - 1, start.y(), start.z() - 1));

                Space space = new Space(boxOrigin, DIAMETER, length, DIAMETER);

                if (!Venue.Contains( space )) {
                    Point end = new Point(start.x() - 1 + DIAMETER, start.y() + length, start.z() - 1 + DIAMETER);
                    Mountable.Remove(this);
                    throw new LocationException(
                            identity + " should not extend beyond the boundaries of the venue.\n" +
                                    "Start: " + boxOrigin.toString() + "\n" +
                                    "End  : " + end.toString() + "\n" +
                                    "Venue: " + Venue.ToString()
                    );
                }
            } else {
//                System.err.println( "90.0 != orientation" );
//            Integer depth = Venue.Depth();
//            Point origin = Proscenium.Origin();
//            boxOrigin = new Point( origin.x() + start.x() - length / 2,
//                                   origin.y() - start.y() - 1,
//                                   origin.z() + start.z() - 1 );
                boxOrigin = Proscenium.Locate(new Point(start.x(),// - length / 2,
                        start.y() - 1,
                        start.z() - 1));

                Space space = new Space(boxOrigin, length, DIAMETER, DIAMETER);

                if (!Venue.Contains( space )) {
                    Mountable.Remove(this);
                    throw new LocationException(
                            identity + " should not extend beyond the boundaries of the venue.");
                }
            }
        } else {
//            System.err.println( "! Proscenium.Active()" );
//            assert( null != orientation );
            if (90.0 == orientation) {
//                System.err.println( "90.0 == orientation" );
                throw new ReferenceException("90-degree oriented only implemented with proscenium.");
            } else {
                System.err.println( "90.0 != orientation" );
//Fails right here:
                boxOrigin = new Point(start.x(), start.y() - 1, start.z() - 1);

                Space space = new Space(boxOrigin, length, DIAMETER, DIAMETER);

                if (!Venue.Contains( space )) {
                    Mountable.Remove(this);
                    throw new LocationException(
                            identity + " should not extend beyond the boundaries of the venue.");
                }
            }
        }
//        System.err.println( "Pipe verified.");
    }

    public void calculateLocationFromSupports( NodeList cheeseboroughList ) {
        support1 = findCheeseborough(0, cheeseboroughList);
        support2 = findCheeseborough(1, cheeseboroughList);

        point1 = support1.locate();
        point2 = support2.locate();
        Double slope = slope(point1, point2);
//        rotation = Math.toDegrees(Math.atan(slope));

        Double supportSpan = point1.distance(point2);
        Long span = Math.round(supportSpan);
        overHang = (length - span.intValue()) / 2;

        start = slopeToPoint( slope, overHang );
    }

    Point slopeToPoint( Double slope, Double overHang ) {
        return null;
    }

    private Cheeseborough findCheeseborough(int item, NodeList cheeseboroughList) {
        Node node = cheeseboroughList.item(item);
        // Much of this code is copied from HangPoint.ParseXML - refactor
        if (null != node) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String reference = element.getAttribute("ref");
                Cheeseborough found = Cheeseborough.Find(reference);

                return found;
            }
        }
        return null;
    }

    public static void SchematicPositionReset() {
        for( Mountable mountable : MountableList() ) {
            if (Pipe.class.isInstance( mountable ) ) {
                Pipe pipe = (Pipe) mountable;
                pipe.schematicPosition = null;
            }
        }
    }

    @Override
    public PagePoint schematicLocation( String location ) {
        if( null == schematicPosition ) {
            return schematicPosition;
        }
        Double place = new Double( location );
        PagePoint schematicLocation;
        if (90.0 == orientation) {
            schematicLocation = new PagePoint( schematicPosition.x(),
                    schematicPosition.y() + place );
        } else {
            schematicLocation = new PagePoint( schematicPosition.x() + place,
                    schematicPosition.y() );
        }
        return schematicLocation;
    }

    @Override
    public PagePoint schematicPosition() {
        return schematicPosition;
    }

    @Override
    public PagePoint schematicCableIntersectPosition( CableRun run ) { return null; }

    @Override
    public Rectangle2D.Double schematicBox() {
        return null;
    }

    @Override
    public void schematicReset() {}

    /**
     * Provide the drawing location of a point along this {@code Pipe}.
     *
     * @param location in the x dimension
     * @return drawing location
     * @throws MountingException if location will be beyond the edge of the pipe
     */
    public Point mountableLocation(String location) throws InvalidXMLException, MountingException {
        Integer offset;
        try {
            offset = new Integer(location);
        } catch (NumberFormatException exception) {
            throw new InvalidXMLException("Pipe (" + id + ") location is not a number.");
        }

        Point point;
        if (Proscenium.Active()) {
            if (90 == orientation) {
                point = Proscenium.Locate(new Point(start.x() + offsetX, start.y() - offset, start.z()));
                if ((offset < 0) || (length < offset)) {
                    throw new MountingException("beyond the end of (proscenium, perpendicular) Pipe");
                }
            } else {
                if ((start.x() < 0) && (start.x() + length > 0)) {
                    // Given a pipe that crosses the centerline, the offset is from the centerline.
                    point = Proscenium.Locate(new Point(offset.doubleValue(),
                            start.y() - 1,
                            start.z() - 1));

                    if ((offset < start.x()) || (start.x() + length < offset)) {
                        throw new MountingException("beyond the end of (proscenium, crosses center) Pipe");
                    }
                } else {
                    // For a pipe that doesn't cross the centerline, the offset is relative to the
                    // start of the pipe.
                    point = Proscenium.Locate(new Point(start.x() /*- (length / 2)*/ + offset,
                            start.y() - 1,
                            start.z() - 1));
                    if ((offset < 0) || (length < offset)) {
                        throw new MountingException("beyond the end of (proscenium, off-center) Pipe");
                    }
                }
            }
        } else {
            // When there is no proscenium
            point = new Point(start.x() + offset, start.y(), start.z());
            if ((offset < 0) || (length < offset)) {
                throw new MountingException("beyond the end of (non-proscenium) Pipe");
            }
        }
//        System.out.println(
//                "Pipe.location(): " + point.toString() + " Start: " + start.toString() );
        return point;
    }

    @Override
    public Place rotatedLocation(String location)
            throws InvalidXMLException, MountingException, ReferenceException {
        // Pipes are not yet able to be rotated, so this just passes through to location().
        return new Place(mountableLocation(location), boxOrigin, 0.0);
    }

    @Override
    public void useCount( Direction direction, CableRun run ) {
    }

    @Override
    public void preview( View view ) {
        switch ( view ) {
            case SCHEMATIC:
                schematicPosition = Schematic.Position( length, 2.0 );
        }
    }

    @Override
    public Place drawingLocation() {
        return null;
    }

    /**
     * Generate SVG DOM for a {@code Pipe}
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     * @throws ReferenceException
     */
    @Override
    public void dom(Draw draw, View mode) throws ReferenceException {
        switch (mode) {
            case TRUSS:
                return;
        }

        Double height = Venue.Height() - boxOrigin.z();

        Point drawBox = new Point(boxOrigin.x() + offsetX, boxOrigin.y(), boxOrigin.z());

        SvgElement group = svgClassGroup( draw, LAYERTAG );
        draw.appendRootChild(group);

        switch (mode) {
            case PLAN:
                if (90.0 == orientation) {
                    group.rectangle( draw, drawBox.x(), drawBox.y(), DIAMETER, length, COLOR );
                } else {
                    group.rectangle( draw, drawBox.x(), drawBox.y(), length, DIAMETER, COLOR );
                    group.text( draw, id, 38.0, drawBox.y() + DIAMETER, COLOR);
                }
                break;
            case SCHEMATIC:
                if (90.0 == orientation) {
                    group.rectangleAbsolute(draw,
                            schematicPosition.x() - DIAMETER / 2,
                            schematicPosition.y() - length / 2,
                            DIAMETER, length, COLOR);
                } else {
                    group.rectangleAbsolute(draw,
                            schematicPosition.x() - length / 2,
                            schematicPosition.y() - DIAMETER / 2,
                            length, DIAMETER, COLOR );
                }
                break;
            case SECTION:
                group.rectangle( draw, drawBox.y(), height, DIAMETER, DIAMETER, COLOR );
                break;
            case FRONT:
                group.rectangle( draw, drawBox.x(), height, length, DIAMETER, COLOR );
                break;
            default:
        }
//        group.appendChild(pipeRectangle);
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
