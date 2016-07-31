package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/24/13 Time: 6:57 PM To change this template use
 * File | Settings | File Templates.
 */

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * A wall.
 * <p/>
 * XML tag is 'wall'. Required attributes are 'x1', 'y1', 'x2', 'y2', which specify beginning and
 * end coordinates for the wall segment. Height & z-coordinates are not specified, as the wall is
 * presumed to extend from floor to ceiling. Children may be 'opening' elements.
 * <p/>
 * Flat. Extending the full height of the venue. Don't worry about doorways and archways, as those
 * will be subsidiary elements.
 *
 * @author dhs
 * @since 0.0.11
 */
public class Wall extends MinderDom {

    private static final String COLOR = "black";

    private Double x1 = null;
    private Double y1 = null;
    private Double x2 = null;
    private Double y2 = null;

    // keep a pair of references to adjacent walls
    private Wall next = null;
    private Point nextCornerPoint = null;
    private Wall previous = null;
    private Point previousCornerPoint = null;

    int space = 2;

    private static ArrayList<Wall> WallList = new ArrayList<>();

    private TreeSet<Opening> openingList = new TreeSet<>( new OpeningComparator() );

    static final String CATEGORY = "wall";


    /**
     * Construct a {@code Wall} from an XML Element.
     *
     * @param element DOM Element defining a wall
     * @throws AttributeMissingException if any attribute is missing
     */
    public Wall( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        x1 = getDoubleAttribute( "x1" );
        y1 = getDoubleAttribute( "y1" );
        x2 = getDoubleAttribute( "x2" );
        y2 = getDoubleAttribute( "y2" );

        WallList.add( this );

        NodeList openings = element.getElementsByTagName( "opening" );
        int length = openings.getLength();
        for (int index = 0; index < length; index++) {
            Node node = openings.item( index );

            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subElement = (Element) node;
                    openingList.add( new Opening( subElement ) );
                }
            }
        }

//        new Category( CATEGORY, this.getClass() );
    }

    /**
     * Provides the list of all {@code Wall}s defined.
     *
     * @return list of {@code Wall}s
     */
    public static List<Wall> WallList() {
        return WallList;
    }

    public Wall next() {
        return next;
    }

    /**
     * Find the nearest corner to the starting point specified. If the
     * destination point is nearer than either corner, return that instead.
     *
     * @param startingPoint
     * @param destinationPoint
     * @return
     */
    public Point nextCorner( Point startingPoint, Point destinationPoint,
                             Wall destinationWall ) throws DataException {

        if ( this == destinationWall ) {
            return destinationPoint;
        }

        if ( null == nextCornerPoint || null == previousCornerPoint ) {
            throw new DataException( "Cannot find abutting wall." );
        }

        if ( nextCornerPoint.equals( startingPoint ) ) {
            return previousCornerPoint;
        }

        if ( previousCornerPoint.equals( startingPoint ) ) {
            return nextCornerPoint;
        }

//        Double destination = startingPoint.distance( destinationPoint );
        Double nextCorner = destinationPoint.distance( nextCornerPoint );
        Double previousCorner = destinationPoint.distance( previousCornerPoint );

//        if ( destination <= nextCorner || destination <= previousCorner ) {
//            System.out.println( ".  Returning destination " + destinationPoint.toString() );
//            return destinationPoint;
//        }
//        else
//        if ( nextCorner < previousCorner && ! nextCornerPoint.equals( startingPoint ) ) {
            if ( nextCorner < previousCorner ) {
            return nextCornerPoint;
        }
        else
//        if ( ! previousCornerPoint.equals( startingPoint ) )
        {
            return previousCornerPoint;
        }
//        else {
//            System.out.println( ".  Returning next " + nextCornerPoint.toString() + " by default.");
//            return nextCornerPoint;
//        }
    }

    /**
     * Find the nearest corner to the starting point specified. If the
     * destination point is nearer than either corner, return that instead.
     *
     * @param startingPoint
     * @param destinationPoint
     * @return
     */
    public Wall nextNear( Point startingPoint, Point destinationPoint, Wall destinationWall ) {
//System.out.print("nextNear. start: " + startingPoint.toString() + ", end: " + destinationPoint.toString());

        if ( this == destinationWall ) {
            return this;
        }

        if ( nextCornerPoint.equals( startingPoint ) ) {
            return previous;
        }

        if ( previousCornerPoint.equals( startingPoint ) ) {
            return next;
        }

//        Double destination = startingPoint.distance( destinationPoint );
        Double nextCorner = destinationPoint.distance( nextCornerPoint );
        Double previousCorner = destinationPoint.distance( previousCornerPoint );

//        System.out.print("... Distances - destination: " + destination.toString()
//                + ", nextCorner: " + nextCorner.toString()
//                + ", previousCorner: " + previousCorner.toString());
//
//        if ( destination <= nextCorner || destination <= previousCorner ) {
//            System.out.println( ".  Returning current wall " + this.toString() );
//            return this;
//        }
//        else
//        if ( nextCorner < previousCorner && ! nextCornerPoint.equals( startingPoint ) ) {
            if ( nextCorner < previousCorner ) {
//            System.out.println( ".  Returning next " + next.toString() );
            return next;
        }
        else
//            if ( ! previousCornerPoint.equals( startingPoint ) )
            {
//            System.out.println( ".  Returning previous " + previous.toString() );
            return previous;
        }
//        else {
//            System.out.println( ".  Returning next " + next.toString() + " by default.");
//            return next;
//        }
    }

    public Wall previous() {
        return previous;
    }

    /**
     *
     * @param point
     * @return
     */
    public static Wall WallNearestPoint( Point point ) {
        Double previousDistance = Double.MAX_VALUE;
        Wall previousWall  = null;
        for( Wall wall : WallList ){
            Point closestPoint = Point.GetClosestPointOnSegment(
                    wall.x1 * 1.0, wall.y1, wall.x2 * 1.0, wall.y2, point.x(), point.y() );
            double distance = closestPoint.distance( point );
            if( distance < previousDistance ) {
                previousDistance = distance;
                previousWall = wall;
            }
        }

        return previousWall;
    }

    public Point nearestPointOnWall( Point point ) {
        return Point.GetClosestPointOnSegment( x1, y1, x2, y2, point.x(), point.y() );
    }

    public Point nearestPointNearWall( Point point ) throws ReferenceException {
        Point found = Point.GetClosestPointOnSegment( x1, y1, x2, y2, point.x(), point.y() );

        Double centerX = Venue.Width() / 2;
        Double centerY = Venue.Depth() / 2;
        if ( x1.equals( x2 )) {
            if ( centerX > found.x() ) {
                return new Point( found.x() + space, found.y(), found.z() );
            }
            else {
                return new Point( found.x() - space, found.y(), found.z() );
            }
        }
        else {
            if ( centerY > found.y() ) {
                return new Point( found.x(), found.y() + space, found.z() );
            }
            else {
                return new Point( found.x(), found.y() - space, found.z() );
            }
        }
    }

    @Override
    public void verify() throws FeatureException, ReferenceException {
        if (openingList.size() > 0) {
            if (x1.equals( x2 )) {
//                    Line2D line = new Line2D.Float( x1, y1, x2, y1 + opening.start() );
//                }
            }
            else if (y1.equals( y2 )) {

            }
            else {
                throw new FeatureException( "Wall at angle does not yet support openings." );
            }
        }

        Double centerX = Venue.Width() / 2;
        Double centerY = Venue.Depth() / 2;

        for( Wall wall : WallList ) {
            if ( this == wall ) { continue; }

//System.out.println("Wall.verify(): " + this.toString() + ": " + wall.toString());

            Double x = null;
            Double y = null;

            if ( this.x1.equals( wall.x1 ) && this.y1.equals( wall.y1 ) ) {
                previous = wall;
                x = (centerX > x1)
                        ? x1 + space
                        : x1 - space;
                y = (centerY > y1)
                        ? y1 + space
                        : y1 - space;
                previousCornerPoint = new Point( x, y, 0.0 );
            }
            else if ( this.x1.equals( wall.x2 ) && this.y1.equals( wall.y2 ) ) {
                previous = wall;
                x = (centerX > x1)
                        ? x1 + space
                        : x1 - space;
                y = (centerY > y1)
                        ? y1 + space
                        : y1 - space;
                previousCornerPoint = new Point( x, y, 0.0 );
            }
//            else {
//                In this case there is no adjacent wall segment and nextCorner() fails
//                        (null pointer exception) when previousCornerPoint is referenced.
//
//                    This should not be an error for a wall.
//                    It is only a problem when CableRun attempts to traverse the walls.
//
//                    nextCorner() should check for previousCornerPoint being null and throw an exception.
//                            Then CableRun can catch that exception and provide a reasonable message that
//                            explains why that cable run didn't get computed.
//            }

            if ( this.x2.equals( wall.x1 ) && this.y2.equals( wall.y1 ) ) {
                next = wall;
                x = (centerX > x2)
                        ? x2 + space
                        : x2 - space;
                y = (centerY > y2)
                        ? y2 + space
                        : y2 - space;
                nextCornerPoint = new Point( x, y, 0.0 );
            }
            else if ( this.x2.equals( wall.x2 ) && this.y2.equals( wall.y2 ) ) {
                next = wall;
                x = (centerX > x2)
                        ? x2 + space
                        : x2 - space;
                y = (centerY > y2)
                        ? y2 + space
                        : y2 - space;
                nextCornerPoint = new Point( x, y, 0.0 );
            }
        }
//        System.out.print("Wall.verify() Done.  " + this.toString());
//        System.out.print( ".  previous: " + previousCornerPoint.toString()  );
//        System.out.println(".  next: " + nextCornerPoint.toString());

    }

    /**
     * Draw the wall.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        switch (mode) {
            case TRUSS:
                return;
        }
        if (mode != View.PLAN) {
            return;
        }

        Iterator<Opening> iterator = openingList.iterator();
        if (openingList.size() > 0)

        {
            if (x1.equals( x2 )) {
                Double yOne = y1;
                Double yTwo;
                while (iterator.hasNext()) {
                    Opening opening = iterator.next();
                    yTwo = y1 + opening.start();

                    SvgElement line = draw.line( draw, x1, yOne, x2, yTwo, COLOR );
                    line.attribute( "stroke-width", "2" );

                    yOne = yTwo + opening.width();
                }
                SvgElement line = draw.line( draw, x1, yOne, x2, y2, COLOR );
                line.attribute( "stroke-width", "2" );
            }
            else {
                Double xOne = x1;
                Double xTwo;
                while (iterator.hasNext()) {
                    Opening opening = iterator.next();
                    xTwo = x1 + opening.start();

                    SvgElement line = draw.line( draw, xOne, y1, xTwo, y2, COLOR );
                    line.attribute( "stroke-width", "2" );

                    xOne = xTwo + opening.width();
                }
                SvgElement line = draw.line( draw, xOne, y1, x2, y2, COLOR );
                line.attribute( "stroke-width", "2" );
            }
        }

        else

        {
            SvgElement line = draw.line( draw, x1, y1, x2, y2, COLOR );
            line.attribute( "stroke-width", "2" );
        }
    }

    public String toString() {
        return "Wall: x1 " + x1
                + ", y1 " + y1
                + ", x2 " + x2
                + ", y2 " + y2;
    }
}
