package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Generic pipe.
 * <p/>
 * XML tag is 'pipe'. Required attributes are 'length', 'x', 'y', and 'z'.
 * Coordinates are relative
 * to the {@code Proscenium} origin, if any, otherwise relative to the page origin.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Pipe extends Mountable {

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

//    private static ArrayList<Pipe> PIPELIST = new ArrayList<>();

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
     * Origin of {@code Space} which defines area used by this pipe.
     */
    private Point boxOrigin = null;

    private Double orientation = null;

    private Integer offsetX = null;

    private static final String COLOR = "black";


//    /**
//     * Find a specific {@code Pipe} from all that have been constructed.
//     *
//     * @param id of {@code Pipe} to find
//     * @return {@code Pipe}, or {@code null} if not found
//     */
//    public static Pipe Select( String id ) {
//        for (Pipe selection : PIPELIST) {
//            if (selection.id.equals( id )) {
//                return selection;
//            }
//        }
//        return null;
//    }


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
            InvalidXMLException, SizeException
    {
        super(element);

//        id = element.attribute( "id" );
        length = getIntegerAttribute(element, "length");
        Integer x = getIntegerAttribute(element, "x");
        Integer y = getIntegerAttribute(element, "y");
        Integer z = getIntegerAttribute(element, "z");
        start = new Point(x, y, z);

        if (0 >= length) {
            Mountable.Remove(this);
            throw new SizeException(this.toString(), "length");
        }

        orientation = getOptionalDoubleAttribute(element, "orientation");
        offsetX = getOptionalIntegerAttribute(element, "offsetx");

        new Layer(LAYERTAG, LAYERNAME);
    }

    /**
     * Confirm that this {@code Pipe}'s specification works with other Plot items.
     *
     * @throws LocationException if the pipe would at any point be outside of the venue
     */
    @Override
    public void verify() throws LocationException, ReferenceException {
        String identity = (id.equals(""))
                ? this.toString()
                : "Pipe (" + id + ")";


        if (Proscenium.Active()) {
            if (90.0 == orientation) {
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
            if (90.0 == orientation) {
                throw new ReferenceException("90-degree oriented only implemented with proscenium.");
            } else {

                boxOrigin = new Point(start.x(), start.y() - 1, start.z() - 1);

                Space space = new Space(boxOrigin, length, DIAMETER, DIAMETER);

                if (!Venue.Contains( space )) {
                    Mountable.Remove(this);
                    throw new LocationException(
                            identity + " should not extend beyond the boundaries of the venue.");
                }
            }
        }
    }

    /**
     * Provide the drawing location of a point along this {@code Pipe}.
     *
     * @param location in the x dimension
     * @return drawing location
     * @throws MountingException if location will be beyond the edge of the pipe
     */
    public Point location(String location) throws InvalidXMLException, MountingException {
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
                    point = Proscenium.Locate(new Point(offset,
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
        return new Place(location(location), boxOrigin, 0.0);
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

        Integer height = Venue.Height() - boxOrigin.z();

        Point drawBox = new Point(boxOrigin.x() + offsetX, boxOrigin.y(), boxOrigin.z());

        SvgElement group = svgClassGroup( draw, LAYERTAG );
//        draw.element("g");
//        group.setAttribute("class", LAYERTAG);
        draw.appendRootChild(group);

        SvgElement pipeRectangle = null;
//        draw.element("rect");
//        pipeRectangle.setAttribute("fill", "none");
//        pipeRectangle.setAttribute("stroke-width", "1");
//        pipeRectangle.setAttribute("stroke-opacity", "0.5");

        switch (mode) {
            case PLAN:
                if (90.0 == orientation) {
                    pipeRectangle = group.rectangle( draw, drawBox.x(), drawBox.y(), DIAMETER, length, COLOR );
//                    pipeRectangle.setAttribute("x", drawBox.x().toString());
//                    pipeRectangle.setAttribute("y", drawBox.y().toString());
//                    pipeRectangle.setAttribute("width", DIAMETER.toString());
//                    pipeRectangle.setAttribute("height", length.toString());
                } else {
                    pipeRectangle = group.rectangle( draw, drawBox.x(), drawBox.y(), length, DIAMETER, COLOR );
//                    pipeRectangle.setAttribute("x", drawBox.x().toString());
//                    pipeRectangle.setAttribute("y", drawBox.y().toString());
//                    pipeRectangle.setAttribute("width", length.toString());
//                    pipeRectangle.setAttribute("height", DIAMETER.toString());
                }
                break;
            case SECTION:
                pipeRectangle = group.rectangle( draw, drawBox.y(), height, DIAMETER, DIAMETER, COLOR );
//                pipeRectangle.setAttribute("x", drawBox.y().toString());
//                pipeRectangle.setAttribute("y", height.toString());
//                pipeRectangle.setAttribute("width", DIAMETER.toString());
//                pipeRectangle.setAttribute("height", DIAMETER.toString());
                break;
            case FRONT:
                pipeRectangle = group.rectangle( draw, drawBox.x(), height, length, DIAMETER, COLOR );
//                pipeRectangle.setAttribute("x", drawBox.x().toString());
//                pipeRectangle.setAttribute("y", height.toString());
//                pipeRectangle.setAttribute("width", length.toString());
//                pipeRectangle.setAttribute("height", DIAMETER.toString());
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
