package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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
public class CableRun extends MinderDom implements Schematicable {

    static ArrayList<CableRun> RunList = new ArrayList<>();

    private String signal;
    private String source;
    private String sink;
    private String channel;
    private String routing;
    private String color;

    private static final String DIRECT = "direct";
//    private static final String COLOR = "green";

    Device sourceDevice = null;
    Device sinkDevice = null;
    Luminaire sourceLuminaire = null;
    Luminaire sinkLuminaire = null;
    Schematicable sourceThingy = null;
    Schematicable sinkThingy = null;

    PagePoint sourcePoint = null;
    PagePoint sinkPoint = null;

    Direction sourceIn = null;
    Direction sinkIn = null;

    static Integer Count = 0;
    ArrayList<Line2D.Double> schematicLines = new ArrayList<>();

    private static Boolean Legended = false;
     static Boolean Collated = false;

    /**
     * Construct a {@code CableRun} from an XML element.
     *
     * @param element DOM Element defining a CableRun
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public CableRun (Element element)
            throws AttributeMissingException, DataException, InvalidXMLException {
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

        color = CableType.Select( signal ).color();

        routing = getOptionalStringAttribute( element, "routing");
        if( ! "".equals( routing ) && ! DIRECT.equals( routing )) {
            throw new InvalidXMLException(this.getClass().getSimpleName() +
                    " from "+source+" to "+sink+" has invalid routing attribute '"+routing+"'.");

        }

//        if( ! Legended ) {
//            Legend.Register( this, 130.0, 7.0, LegendOrder.Device );
//            Legended = true;
//        }
    }

    @Override
    public Rectangle2D.Double schematicBox() {
        return null;
    }

    @Override
    public PagePoint schematicPosition() {
        return null;
    }

    @Override
    public PagePoint schematicCableIntersectPosition( CableRun run ) { return null; }

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
        sourceThingy = sourceDevice = Device.Select( source );
        sinkThingy = sinkDevice = Device.Select( sink );

        if( null == sourceDevice ) {
            sourceThingy = sourceLuminaire = Luminaire.Select( source );
        }

        if( null == sinkDevice) {
            sinkThingy = sinkLuminaire = Luminaire.Select( sink );
        }

        if( (null == sourceDevice && null == sourceLuminaire) ||
                (null == sinkDevice && null == sinkLuminaire) ) {
            StringBuilder message = new StringBuilder( "CableRun of " + signal );
            if ( null != sourceDevice || null != sourceLuminaire ) {
                message.append( " from " ).append( source );
            }
            if ( null != sinkDevice || null != sinkLuminaire ) {
                message.append( " to " ).append( sink );
            }
            message.append( " references unknown " );
            if ( null == sourceDevice && null == sourceLuminaire ) {
                message.append( "source '" ).append( source ).append( "'" );
            }
            if( null == sourceDevice && null == sourceLuminaire &&
                    null == sinkDevice && null == sinkLuminaire ) {
                message.append( " and " );
            }
            if ( null == sinkDevice && null == sinkLuminaire ) {
                message.append( "sink '" ).append( sink ).append( "'" );
            }
            message.append( "." );
            throw new InvalidXMLException( message.toString() );
        }

        RunList.add( this );
    }

    public static void Collate() {
        for( CableRun run : RunList ) {
            run.schematicLines = new ArrayList<>();
            run.precheck();
        }

        Collated = true;
    }

    /**
     * figure out which direction cable-run lines will hit things from and register that
     * information with the things.
     */
    private void precheck() {
//        Double buffer = 6.0;

        sourcePoint = sourceThingy.schematicPosition();
        sinkPoint = sinkThingy.schematicPosition();

        if( null == sourcePoint ) {
            System.out.println( sourceThingy.toString() + "is not shown, so no cables will be drawn in or out of it.");
            return;
        }

        if( null == sinkPoint ) {
            System.out.println( sinkThingy.toString() + "is not shown, so no cables will be drawn in or out of it.");
            return;
        }

        Line2D.Double line = new Line2D.Double(
                sourcePoint.x(), sourcePoint.y(), sinkPoint.x(), sinkPoint.y() );
        ArrayList<Schematicable> obstructionList = Schematic.FindObstruction( line );
        obstructionList.remove( sourceThingy );
        obstructionList.remove( sinkThingy );

        if ( obstructionList.size() == 0 ) {
//            schematicLines.add(new Line2D.Double(
//                    sourcePoint.x(), sourcePoint.y(), sinkPoint.x(), sinkPoint.y()) );

            Double slope = (sinkPoint.y() - sourcePoint.y()) / (sinkPoint.x() - sourcePoint.x());

            if( Math.abs( slope ) > 1.0 ) {
                if ( sourcePoint.y() > sinkPoint.y() ) {
                    sourceThingy.useCount( Direction.UP, this );
                    sinkThingy.useCount( Direction.Down, this );
                }
                else {
                    sourceThingy.useCount( Direction.Down, this );
                    sinkThingy.useCount( Direction.UP, this );
                }
            }
            else {
                if ( sourcePoint.x() < sinkPoint.x() ) {
                    sourceThingy.useCount( Direction.Right, this );
                    sinkThingy.useCount( Direction.Left, this );
                }
                else {
                    sourceThingy.useCount( Direction.Left, this );
                    sinkThingy.useCount( Direction.Right, this );
                }
            }

        }
        else {
//            Double top = Math.min(sourcePoint.y(), sinkPoint.y());
//            for( Schematicable obstruction : obstructionList ) {
//                if( obstruction.schematicBox().intersectsLine( line ) ) {
//                    top = Math.min( top, obstruction.schematicBox().getY() );
//                }
//            }
//            top -= buffer;
//            schematicLines.add( new Line2D.Double(
//                    sourcePoint.x(), sourcePoint.y(), sourcePoint.x(), top ) );
//            schematicLines.add(new Line2D.Double(
//                    sourcePoint.x(), top, sinkPoint.x(), top ) );
//            schematicLines.add(new Line2D.Double(
//                    sinkPoint.x(), top, sinkPoint.x(), sinkPoint.y() ) );

            sourceThingy.useCount( Direction.UP, this );
            sinkThingy.useCount( Direction.UP, this );
        }
    }

//    Somewhere in there, use java.awt.geom.Rectangle2D.intersectsLine() to find if the path
//            provided by findPath() intersects both endpoints of a cable-diversion. If so,
//    modify the path accordingly.
//    I'm specifying the ends of a cable-diversion as points, but I can make there be a rectangle
//            around each of those points for intercepting the generic cable path.

    // There is no counting for the lines - only for the things at the ends of the lines
    @Override
    public void useCount( Direction direction, CableRun run ) {
    }

    @Override
    public void preview( View view )
            throws CorruptedInternalInformationException, ReferenceException {

        if ( View.SCHEMATIC != view ) { return; }

        if ( !Collated ) {
            Collate();
        }


        if( null == sourcePoint || null == sinkPoint ) {
            return;
        }


        Double buffer = 6.0;

        sourcePoint = sourceThingy.schematicCableIntersectPosition( this );
        sinkPoint = sinkThingy.schematicCableIntersectPosition(this);

        Line2D.Double line = new Line2D.Double(
                sourcePoint.x(), sourcePoint.y(), sinkPoint.x(), sinkPoint.y() );
        ArrayList<Schematicable> obstructionList = Schematic.FindObstruction( line );
        obstructionList.remove( sourceThingy );
        obstructionList.remove( sinkThingy );

        if ( obstructionList.size() == 0 ) {
            schematicLines.add( line );
//                    new Line2D.Double(
//                    sourcePoint.x(), sourcePoint.y(), sinkPoint.x(), sinkPoint.y()) );

//            Double slope = (sinkPoint.y() - sourcePoint.y()) / (sinkPoint.x() - sourcePoint.x());
//
//            if( Math.abs( slope ) > 1.0 ) {
//                if ( sourcePoint.y() < sinkPoint.y() ) {
//                    sourceThingy.useCount( Direction.UP, this );
//                    sinkThingy.useCount( Direction.Down, this );
//                }
//                else {
//                    sourceThingy.useCount( Direction.Down, this );
//                    sinkThingy.useCount( Direction.UP, this );
//                }
//            }
//            else {
//                if ( sourcePoint.x() < sinkPoint.x() ) {
//                    sourceThingy.useCount( Direction.Right, this );
//                    sinkThingy.useCount( Direction.Left, this );
//                }
//                else {
//                    sourceThingy.useCount( Direction.Left, this );
//                    sinkThingy.useCount( Direction.Right, this );
//                }
//            }

        }
        else {
            Double top = Math.min(sourcePoint.y(), sinkPoint.y());
            for( Schematicable obstruction : obstructionList ) {
                if( obstruction.schematicBox().intersectsLine( line ) ) {
                    top = Math.min( top, obstruction.schematicBox().getY() );
                }
            }
            top -= buffer;
            schematicLines.add( new Line2D.Double(
                    sourcePoint.x(), sourcePoint.y(), sourcePoint.x(), top ) );
            schematicLines.add(new Line2D.Double(
                    sourcePoint.x(), top, sinkPoint.x(), top ) );
            schematicLines.add(new Line2D.Double(
                    sinkPoint.x(), top, sinkPoint.x(), sinkPoint.y() ) );
//
//            sourceThingy.useCount( Direction.UP, this );
//            sinkThingy.useCount( Direction.UP, this );
        }
    }

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
    public void dom( Draw draw, View mode ) throws InvalidXMLException, MountingException, ReferenceException {

        switch (mode) {
            case PLAN:
                domPlan(draw);
                break;
            case SCHEMATIC:
                domSchematic(draw);
                break;
            case SECTION:
            case FRONT:
            case TRUSS:
                return;
        }
    }

    void domSchematic(Draw draw) {

        if( null == sourcePoint || null == sinkPoint ) {
            return;
        }

        SvgElement group = draw.element("g");
        group.attribute("class", this.getClass().getSimpleName() );
        if (null != sourceDevice) {
            group.attribute("class", sourceDevice.layer() );
        }
        draw.appendRootChild(group);

        for( Line2D.Double line : schematicLines ) {
            group.lineAbsolute( draw, line, color );
        }
    }

    void domPlan(Draw draw) throws InvalidXMLException, MountingException, ReferenceException {
        Place sourcePoint = (null != sourceDevice) ? sourceDevice.location() : sourceLuminaire.location();
        Place sinkPoint = (null != sinkDevice) ? sinkDevice.location() : sinkLuminaire.location();

        ArrayList<Point> vertices = null;
        try {
            vertices = findPath( sourcePoint.location(), sinkPoint.location() );
        }
        catch ( DataException e ) {
            System.out.println( "In drawing, cable run between "+
                    sourceDevice.toString() +
                    " and " +
                    sinkDevice.toString() +
                    ", but discovered discontinuous wall segments.");
            return;
        }
        CableDiversion.InsertDiversion( vertices );

        SvgElement group = draw.element("g");
        group.attribute("class", this.getClass().getSimpleName() );
        if (null != sourceDevice) {
            group.attribute("class", sourceDevice.layer() );
        }
        draw.appendRootChild(group);

        Point previous = sourcePoint.location();
        for( Point point : vertices ) {

            appendLineSegment( draw, group, previous, point );
            previous = point;
        }
    }


    ArrayList<Point> findPath( Point sourcePoint, Point sinkPoint )
            throws DataException, ReferenceException {
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

            list.add( sourceWallPoint );

            // Find point on some wall nearest sink. We'll be using this as
            // a destination as we work our way around the room.
            Point sinkGround = new Point( sinkPoint.x(), sinkPoint.y(), 0.0 );
            Wall sinkWall = Wall.WallNearestPoint(sinkGround);
            Point sinkWallPoint = sinkWall.nearestPointNearWall( sinkGround );

            Wall currentWall = sourceWall;
            Point currentPoint = sourceWallPoint;
            while ( ! currentPoint.equals( sinkWallPoint ) ) {
                // find corner of current wall in the direction we need to go,
                Point nextCorner = currentWall.nextCorner( currentPoint, sinkWallPoint, sinkWall );
                Wall nextWall = currentWall.nextNear( currentPoint, sinkWallPoint, sinkWall );

                if ( nextCorner.equals( sinkWallPoint ) ) {
                    break;
                }

                // draw line to that corner
                list.add( nextCorner );
                // find another wall that shares that corner and make it be current wall
                // repeat above three steps until we are at sinkWall.
                currentPoint = nextCorner;
                currentWall = nextWall;
            }

            // Now that we've found any intermediate points, finish up by adding
            // the last two points before the sink device.
            list.add( sinkWallPoint );
            list.add( sinkGround );
        }

        Count++;

        list.add( sinkPoint );

        return list;
    }

    void appendLineSegment( Draw draw, SvgElement group, Point one, Point two ) {
        group.line( draw, one.x(), one.y(), two.x(), two.y(), color );
    }


//    @Override
//    public void countReset() {
//        Count = 0;
//    }
//
//    /**
//     * Callback used by {@code Legend} to allow this object to generate the information it needs to
//     * put into the legend area.
//     * <p/>
//     * {@code LuminaireDefinition} puts out a 'use' element to draw its icon and the name of the
//     * type of luminaire.
//     *
//     * @param draw  Canvas/DOM manager
//     * @param start position on the canvas for this legend entry
//     * @return start point for next {@code Legend} item
//     */
//    @Override
//    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
//        if ( 0 >= Count ) { return start; }
//
//        Double endLine = start.x() + 12;
//
//        draw.lineAbsolute(draw, start.x(), start.y(), endLine, start.y(), "green" );
//
//        String words = source + " cable run";
//        Double x = start.x() + Legend.TEXTOFFSET;
//        Double y = start.y() + 3;
//        draw.textAbsolute(draw, words, x, y, Legend.TEXTCOLOR);
//
//        return new PagePoint( start.x(), start.y() + 7 );
//    }

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
