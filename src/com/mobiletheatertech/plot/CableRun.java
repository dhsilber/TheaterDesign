package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Represents a connection between two {@code Device}s.
 *
 * While this connection could be a single cable, all that I'm promising
 * is a virtual cable. Once a length is computed for the connection, it is
 * some other part of the program's job to figure out what size & how many
 * real-world cables will be needed.
 *
 * Enough information needs to be provided that the software can figure
 * out which devices/ports are to be connected and what kind of cable is
 * needed.
 * <p/>
 * XML tag is 'CableRun'.
 * <p/>
 * Required attributes are:<dl>
 *     <dt>signal</dt><dd>a type of connection</dd>
 *     <dt>source</dt><dd>the device providing the signal</dd>
 *     <dt>sink</dt><dd>the device which receives the signal</dd> </dl>
 * <p/>
 * Optional attributes are:<dl>
 *     <dt>channel</dt><dd>specify which of similar connections to use</dd>
 * </dl>
 *
 * @author dhs
 * @since 2014-01-12
 */
public class CableRun extends MinderDom implements Legendable {

    private String signal;
    private String source;
    private String sink;
    private String channel;
    private String routing;

    private static final String DIRECT = "direct";
    private static final String COLOR = "green";

    Device sourceDevice = null;
    Device sinkDevice = null;

    private static Boolean Legended = false;

    /**
     * Construct a {@code CableRun} from an XML element.
     *
     * @param element DOM Element defining a CableRun
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public CableRun (Element element)
            throws AttributeMissingException, InvalidXMLException {
        super(element);

        /*
        A lot of this code is error detection and reporting on the
         attribute values, which I've mostly done in Elemental. What
         is different here is that I want to make the error messages
         report ALL of the problems with this element's attributes at
         once rather than having the thing break immediately on detecting
         each error.

        If I need to move this up into Elemental, I will need to pass
            in an array of: attribute name, variable name, variable type,
            and error reporting prefix. This means that I will have to use
             reflection to set the variables, which means things will break
             easily if I typo a variable name, but hey, that's what tests
             are for.
         */

        signal = getOptionalStringAttribute( element, "signal" );
        source = getOptionalStringAttribute( element, "source" );
        sink = getOptionalStringAttribute( element, "sink" );
        channel = getOptionalStringAttribute( element, "channel" );

        if (signal.isEmpty() || source.isEmpty() || sink.isEmpty() ) {
            StringBuilder message = new StringBuilder ( "CableRun " );
            if ( ! signal.isEmpty() ) {
                message.append( "of " ).append( signal ).append( " " );
            }
            if ( ! source.isEmpty() ) {
                message.append( "from " ).append( source ).append( " " );
            }
            if ( ! sink.isEmpty() ) {
                message.append( "to " ).append( sink ).append( " " );
            }
            message.append( "is missing required" );

            int signalCommaPosition = 0;
            int beforeLastErrorPosition = 0;
            int errorCount = 0;

            if ( signal.isEmpty() ) {
                message.append( " 'signal'" );
                signalCommaPosition = message.length();
                errorCount++;
            }
            if ( source.isEmpty() ) {
                beforeLastErrorPosition = message.length();
                message.append( " 'source'" );
                errorCount++;
            }
            if ( sink.isEmpty() ) {
                beforeLastErrorPosition = message.length();
                message.append( " 'sink'" );
                errorCount++;
            }

            if ( 2 <= errorCount) {
                message.insert( beforeLastErrorPosition, " and" );
            }

            if ( 3 == errorCount ) {
                message.insert( beforeLastErrorPosition, "," );
                message.insert( signalCommaPosition, "," );
            }

            message.append( " attribute." );
            if (1 < errorCount) {
                message.insert( message.length() - 1, 's' );
            }
            throw new AttributeMissingException( message );
        }

        routing = getOptionalStringAttribute( element, "routing");
        if( ! "".equals( routing ) && ! DIRECT.equals( routing )) {
            throw new InvalidXMLException(this.getClass().getSimpleName() +
                    " from "+source+" to "+sink+" has invalid routing attribute '"+routing+"'.");

        }

        if( ! Legended ) {
            Legend.Register( this, 130, 7, LegendOrder.Device );
            Legended = true;
        }
    }

    /**
     * Confirm that the two specified devices actually refer to
     * {@code Device}s that have been instantiated.
     *
     * @throws InvalidXMLException
     */
    /*
    Since we'll need references to the actual Devices when it is time
    to draw them, we'll keep them rather than searching them out again
    later.
    */
    @Override
    public void verify() throws InvalidXMLException {
        sourceDevice = Device.Select( source );
        sinkDevice = Device.Select( sink );

        if( null == sourceDevice || null == sinkDevice ){
            StringBuilder message = new StringBuilder( "CableRun of " + signal );
            if ( null != sourceDevice ) {
                message.append( " from " ).append( source );
            }
            if ( null != sinkDevice ) {
                message.append( " to " ).append( sink );
            }
            message.append( " references unknown " );
            if ( null == sourceDevice ) {
                message.append( "source '" ).append( source ).append( "'" );
            }
            if( null == sourceDevice && null == sinkDevice ) {
                message.append( " and " );
            }
            if ( null == sinkDevice ) {
                message.append( "sink '" ).append( sink ).append( "'" );
            }
            message.append( "." );
            throw new InvalidXMLException( message.toString() );
        }
    }

//    Somewhere in there, use java.awt.geom.Rectangle2D.intersectsLine() to find if the path
//            provided by findPath() intersects both endpoints of a cable-diversion. If so,
//    modify the path accordingly.
//    I'm specifying the ends of a cable-diversion as points, but I can make there be a rectangle
//            around each of those points for intercepting the generic cable path.

    /**
     * Generate SVG DOM for a {@code CableRun}. Basicly just a set of lines
     * tracing the path a cable would take between the two {@code Device}s.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     */
    /*
    Because we had to search out the devices when we verified them, we
    already have reference to them.
     */
    @Override
    public void dom( Draw draw, View mode ) throws ReferenceException {

        Point sourcePoint = sourceDevice.location();
        Point sinkPoint = sinkDevice.location();

        ArrayList<Point> vertices = findPath( sourcePoint, sinkPoint );
        CableDiversion.InsertDiversion( vertices );

System.err.println("Drawing CableRun "+ this.toString() +".");

        SvgElement group = draw.element("g");
        group.attribute("class", "CableRun" );
        group.attribute("class", sourceDevice.layer() );
        draw.appendRootChild(group);

        Point previous = sourcePoint;
System.err.println("... previous point:  "+ previous.toString() +".");
        for( Point point : vertices ) {
System.err.println("... next point: "+ point.toString() +".");

            appendLineSegment( draw, group, previous, point );
            previous = point;
        }
    }

    ArrayList<Point> findPath( Point sourcePoint, Point sinkPoint )
            throws ReferenceException {
        ArrayList<Point> list = new ArrayList<>(20);

        // Not every cable run needs to go to the floor. Check here for
        // sinkPoint being nearer than ground.
        if(     sourcePoint.distance( sinkPoint ) > sourcePoint.z()
                && ! DIRECT.equals( routing )
                ) {

            // If not a direct run, go first to the ground
            Point sourceGround = new Point( sourcePoint.x(), sourcePoint.y(), 0.0 );
            list.add( sourceGround );

            // ... and then to the wall.
            Wall sourceWall = Wall.WallNearestPoint(sourceGround);
            Point sourceWallPoint = sourceWall.nearestPointNearWall( sourceGround );

System.err.println("\nsourceWallPoint: "+ sourceWallPoint.toString() +".");

            list.add( sourceWallPoint );

            // Find point on some wall nearest sink. We'll be using this as
            // a destination as we work our way around the room.
            Point sinkGround = new Point( sinkPoint.x(), sinkPoint.y(), 0.0 );
            Wall sinkWall = Wall.WallNearestPoint(sinkGround);
            Point sinkWallPoint = sinkWall.nearestPointNearWall( sinkGround );

            Wall currentWall = sourceWall;
            Point currentPoint = sourceWallPoint;
            while ( ! currentPoint.equals( sinkWallPoint ) ) {
//                System.out.println( "Current Wall: " + currentWall.toString() + ".  Sink Wall: " + sinkWall.toString() );
                // find corner of current wall in the direction we need to go,
                Point nextCorner = currentWall.nextCorner( currentPoint, sinkWallPoint, sinkWall );
                Wall nextWall = currentWall.nextNear( currentPoint, sinkWallPoint, sinkWall );

                if ( nextCorner.equals( sinkWallPoint ) ) {
                    break;
                }

//                if( currentPoint.equals( ))

System.err.println("... nextCorner: "+ nextCorner.toString() +".");

                // draw line to that corner
                list.add( nextCorner );
                // find another wall that shares that corner and make it be current wall
                // repeat above three steps until we are at sinkWall.
                currentPoint = nextCorner;
                currentWall = nextWall;
            }

            // Now that we've found any intermediate points, finish up by adding
            // the last two points before the sink device.
System.err.println("... sinkWallPoint: "+ sinkWallPoint.toString() +".");
            list.add( sinkWallPoint );
System.err.println("... sinkGround: "+ sinkGround.toString() +".");
            list.add( sinkGround );
        }

System.err.println("... sinkPoint: "+ sinkPoint.toString() +".");
        list.add( sinkPoint );

        return list;
    }

    void appendLineSegment( Draw draw, SvgElement group, Point one, Point two ) {
        group.line( draw, one.x(), one.y(), two.x(), two.y(), "green" );
    }


    /**
     * Callback used by {@code Legend} to allow this object to generate the information it needs to
     * put into the legend area.
     * <p/>
     * {@code LuminaireDefinition} puts out a 'use' element to draw its icon and the name of the
     * type of luminaire.
     *
     * @param draw  Canvas/DOM manager
     * @param start position on the canvas for this legend entry
     * @return start point for next {@code Legend} item
     */
    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {

        Double endLine = start.x() + 12;

        draw.line( draw, start.x(), start.y(), endLine, start.y(), COLOR );

        String words = sourceDevice.layer() + " cable run";
        Double x = start.x() + Legend.TEXTOFFSET;
        Double y = start.y() + 3;
        draw.text( draw, words, x, y, Legend.TEXTCOLOR );

        return new PagePoint( start.x(), start.y() + 7 );
    }

    /**
     * Describe this {@code CableRun}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "CableRun: " + signal
                + ". Source: " + source
                + ". Sink: " + sink
                + ". Channel: " + channel
                ;
    }
}
