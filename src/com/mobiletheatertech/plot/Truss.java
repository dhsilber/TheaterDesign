/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Defines a box truss. <p> XML tag is 'truss'. Required children are 'suspend' or 'base' elements. Required
 * attributes are 'size' (valid values are 12 and 20) and 'length'. </p><p> For now, I'm allowing
 * any length of truss, but the count of truss is presuming ten-foot lengths. </p>
 *
 * @author dhs
 * @since 0.0.5
 */
public class Truss extends Mountable implements Legendable, Schematicable {

    /**
     * Name of {@code Layer} of {@code Pipe}s.
     */
    public static final String LAYERNAME = "Trusses";

    /**
     * Tag for {@code Layer} of {@code Pipe}s.
     */
    public static final String LAYERTAG = "truss";

    static final String CATEGORY = "truss";
    static boolean LEGENDREGISTERED = false;

    private static Double TrussCount = 0.0;
    private Double trussCounted = Double.MIN_VALUE;
    Integer trussSegmentCount = 0;

    private PagePoint schematicPosition = null;
    private final Double schematicHeight = 200.0;  // Room for two views of a piece of truss, plus space around them for luminaires.


    private String color = "dark blue";
    private Double size = null;
    private Double length = null;
    private Suspend suspend1 = null;
    private Suspend suspend2 = null;
    //    private String processedMark = null;
    private Element element = null;

    private Base base = null;
    static Integer BaseCount = 0;

    Point point1 = null;
    Point point2 = null;
    Double rotation = null;
    Double overHang = null;
//    Point startA = null;

    Double x = null;
    Double y = null;
    Double z = null;

    Point position = null;
    Boolean positioned = false;

    Point verticalCenter = null;

    /**
     * Creates a {@code Truss}.
     *
     * @param element Representation of an XML description of a {@code Truss}
     * @throws AttributeMissingException
     * @throws KindException
     */
    public Truss(Element element)
            throws AttributeMissingException, DataException, InvalidXMLException,
            KindException, MountingException {
        super(element);

        if (Proscenium.Active()) {
            throw new InvalidXMLException("Truss not yet supported with Proscenium.");
        }

        this.element = element;
        size = getDoubleAttribute(element, "size");
        length = getDoubleAttribute(element, "length");
        x = getOptionalDoubleAttributeOrNull(element, "x");
        y = getOptionalDoubleAttributeOrNull(element, "y");
        z = getOptionalDoubleAttributeOrNull( element, "z" );

        if( 12.0 != size && 20.5 != size ) {
                throw new KindException("Truss", size);
        }

        if ( null != x || null != y || null != z ) {
            if ( null == x || null == y || null == z ) {
                throw new InvalidXMLException( "Truss (" + id + ") explicitly positioned must have x, y, and z coordinates" );
            }
            positioned = true;
        }

//        processedMark = Mark.Generate();
//        element.setAttribute("processedMark", processedMark);

//        new Category( CATEGORY, this.getClass() );
    }
//

    public void verify() throws InvalidXMLException, ReferenceException {
//        NodeList baseList = element.getElementsByTagName( "base" );

        if ( positioned ) {
            position = new Point( x, y, z );
            return;
        }

        NodeList suspendList = element.getElementsByTagName("suspend");
        NodeList baseList = element.getElementsByTagName( "base" );

        if (2 == suspendList.getLength()) {

            // ToDo The processedMark thing is really bogus. We should just create the objects found in the suspend list as they are encountered here.
            suspend1 = findSuspend(0, suspendList);/*ArrayList<Anchor> suspensions*/
            suspend2 = findSuspend(1, suspendList);

            assert (suspend1.locate().x() < suspend2.locate().x());

            suspensions.add( suspend1 );
            suspensions.add( suspend2 );

            point1 = suspend1.locate();
            point2 = suspend2.locate();
            Double slope = point1.slope( point2 );
            rotation = Math.toDegrees(Math.atan(slope));

            span = point1.distance(point2);
//            Long span = Math.round(span);
            overHang = (length - span) / 2;
            // Given suspend1 and the slope, find where the start of the truss will end up.

            suspend1.location( overHang );
            suspend2.location( overHang + span );
        }
        else if ( 1 == baseList.getLength() ) {
            // ToDo The processedMark thing is really bogus. We should just create the objects found in the base list as they are encountered here.
            base = findBase( baseList );

            verticalCenter = new Point( base.x(), base.y(), 0.0 );
            rotation = base.rotation();

//            positioned = true;
            x = base.x();
            y = base.y();
            z = 0.0;

            if ( ! LEGENDREGISTERED ) {
                Legend.Register( this, 2.0, 12.0, LegendOrder.Structure );
                LEGENDREGISTERED = true;
            }
        }
        else {
            System.err.println("Found " + suspendList.getLength() + " suspend child nodes");
            System.err.println("Found " + baseList.getLength() + " base child nodes");
            throw new InvalidXMLException("Truss (" + id + ") must have position, base, or exactly two suspend children");
        }
    }

    // ToDo The processedMark thing is really bogus. We should just create the objects found in the suspend list as they are encountered here.
    private Suspend findSuspend(int item, NodeList suspendList) {
        Node node = suspendList.item(item);
        // Much of this code is copied from HangPoint.ParseXML - refactor
        if (null != node) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String mark = element.getAttribute("processedMark");
                Suspend found = Suspend.Find(mark);

                return found;
            }
        }
        return null;
    }

    // ToDo The processedMark thing is really bogus. We should just create the objects found in the base list as they are encountered here.
    private Base findBase( NodeList baseList ) {
        Node node = baseList.item( 0 );
        // Much of this code is copied from HangPoint.ParseXML - refactor
        if (null != node) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String mark = element.getAttribute("processedMark");
                Base found = Base.Find(mark);

                return found;
            }
        }
        return null;
    }

    @Override
    public PagePoint schematicLocation( String location ) throws InvalidXMLException, MountingException {
        Character vertex = location.charAt(0);
        Integer distance = locationDistance(location);

        Double offset = size / 2;

        switch (vertex) {
            case 'a':
            case 'c':
                offset *= -1;
                break;
            case 'b':
            case 'd':
                break;
            default:
                throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
        }

        Double verticalOffset = 0.0;
        switch (vertex) {
            case 'a':
            case 'b':
                verticalOffset -= schematicHeight / 4;
                break;
            case 'c':
            case 'd':
                verticalOffset += schematicHeight / 4;
                break;
            default:
                throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
        }

        return new PagePoint(
                schematicPosition.x() - length / 2 + distance,
                schematicPosition.y() + offset + verticalOffset );
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

    /*
    * Provide the location to draw a hanged thing at relative to the unrotated truss.
    *
    * The hanged thing can also be rotated relative to the pivot point of the truss so that it lines up correctly.
     */
    @Override
    public Point mountableLocation(String location) throws InvalidXMLException, MountingException, ReferenceException {
        Character vertex = location.charAt(0);
        Integer distance = locationDistance(location);

        if( null != base ) {
            Double northOffset = size / 2 * -1;
            Double westOffset = size / 2 * -1;

            switch (vertex) {
                case 'a':
                    break;
                case 'b':
                    westOffset *= -1;
                    break;
                case 'c':
                    northOffset *= -1;
                    break;
                case 'd':
                    northOffset *= -1;
                    westOffset *= -1;
                    break;
                default:
                    throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
            }

            return new Point( x + westOffset, y + northOffset, z + distance );

        }
        else {
            Double offset = size / 2;

            switch (vertex) {
                case 'a':
                case 'c':
                    offset *= -1;
                    break;
                case 'b':
                case 'd':
                    break;
                default:
                    throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
            }

            if ( positioned ) {
                Double verticalOffset = size / 2;
                switch (vertex) {
                    case 'a':
                    case 'b':
                        break;
                    case 'c':
                    case 'd':
                        verticalOffset *= -1;
                        break;
                    default:
                        throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
                }

                return new Point( x - length / 2 + distance, y + offset, z + verticalOffset );
            }
            else {
                if (null == suspend1) {
                    throw new InvalidXMLException("suspend1 is null");
                }
                Point point = suspend1.locate();

                Double verticalOffset = 0.0;
                switch (vertex) {
                    case 'a':
                    case 'b':
                        break;
                    case 'c':
                    case 'd':
                        verticalOffset += size;
                        break;
                    default:
                        throw new InvalidXMLException( "Truss (" + id + ") location does not include a valid vertex.");
                }

                return new Point(point.x() - overHang + distance, point.y() + offset, point.z() - verticalOffset );
            }
        }
    }

    @Override
    public Integer locationDistance(String location) throws InvalidXMLException, MountingException {
        String distanceString = location.substring(1);
        Integer distance;
        try {
            distance = new Integer(distanceString.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidXMLException("Truss (" + id + ") location must include vertex and distance.");
        }

        if (0 > distance || distance > length) {
            throw new MountingException("Truss (" + id + ") does not include location " + distance.toString() + ".");
        }
        return distance;
    }

    /*
    Used only by Luminaire.dom() in View.TRUSS mode.
     */
    public Point relocate( String location ) throws MountingException{
        Double y;

        Character vertex = location.charAt(0);
        Double offset = size / 2;     //         * @param suspensions

        switch (vertex) {
            case 'a':
                offset *= -1;
            case 'b':
                y = trussCounted * 320 + 80 + size / 2;
                break;
            case 'c':
                offset *= -1;
            case 'd':
                y = trussCounted * 320 + 220 + size / 2;
                break;
            default:
                throw new MountingException("Truss (" + id + ") location does not include a valid vertex.");
        }

        String distanceString = location.substring(1);
        Double distance;
        try {
            distance = new Double(distanceString.trim());
        } catch (NumberFormatException exception) {
            throw new MountingException("Truss (" + id + ") location not correctly formatted.");
        }

        if (0 > distance || distance > length) {
            throw new MountingException("Truss (" + id + ") does not include location " + distance.toString() + ".");
        }

        Point result = new Point (size + distance, y + offset, 0.0  );

        return result;
    }

    /*
Used only by Luminaire.dom() in code that only has effect in View.TRUSS mode.
     */
    public boolean farSide( String location ) throws MountingException{

        Character vertex = location.charAt(0);
        switch (vertex) {
            case 'a':
            case 'c':
                return false;
            case 'b':
            case 'd':
                return true;
            default:
                throw new MountingException("Truss (" + id + ") location does not include a valid vertex.");
        }
    }

    /*
    Everything that location() does, bundled with the information a hanged thing needs to position itself.
     */
    @Override




    public Place rotatedLocation(String location) throws InvalidXMLException, MountingException, ReferenceException {
        verify();

//        System.err.println( "RotatedLocation()" );

        if( positioned ) {
//            System.err.println( "positioned" );
            return new Place( mountableLocation(location), position, 0.0 );
        }
        else if ( null != base ) {
//            System.err.println( "base" );
            Double transformX = base.x() + SvgElement.OffsetX();
            Double transformY = base.y() + SvgElement.OffsetY();
            Point origin = new Point( transformX, transformY, 0.0 );
//            System.err.println( "base end" );
            return new Place(mountableLocation(location), origin, base.rotation() );
        }
        else {
//            System.err.println( "standard" );
            Double transformX = point1.x() + SvgElement.OffsetX();
            Double transformY = point1.y() + SvgElement.OffsetY();
            Point origin = new Point( transformX, transformY, point1.z() );
//            System.err.println( "standard end" );
            return new Place(mountableLocation(location), origin, rotation);
        }
    }

    @Override
    public void useCount( Direction direction, CableRun run ) {
    }

    @Override
    public void preview( View view ) {
        switch ( view ) {
            case SCHEMATIC:
                schematicPosition = Schematic.Position( length, schematicHeight );
        }
    }

    @Override
    public Place drawingLocation() {
        return null;
    }

    /**
     * Elucidate the support for this.
     *
     * @return
     */
    @Override
    public String suspensionPoints() {
        if( positioned ) {
            return "Truss is positioned.";
        }
        else if( null != base ) {
            return "Truss is set on end, on a base.";
        }
        else if( (null != suspend1) && (null != suspend2) ) {
            return "Truss is suspended at " + overHang + " and at " + (length - overHang);
        }
        else {
            return "Cannot figure out how this Truss is held up";
        }
    }

    @Override
    public String calculateIndividualLoad( Luminaire luminaire ) throws InvalidXMLException, MountingException {
        assert ( 2 == suspensions.size() );

        Suspend one = suspensions.get(0);
        Suspend two = suspensions.get(1);

        Integer locationDistance = locationDistance( luminaire.locationValue() );
        Double oneDistance = one.location() - locationDistance;
        Double twoDistance = two.location() - locationDistance;

        Double load = luminaire.weight();
        Double first = (load * twoDistance) / span;
        one.load( first );
        Double second = - (load * oneDistance) / span;
        two.load( second );

        DecimalFormat fourPlaces = new DecimalFormat( "###.####" );
        return " " + fourPlaces.format( first ) + " on " + one.refId() + ". "
                + fourPlaces.format( second ) + " on " + two.refId() + ".";
    }

    @Override
    public String totalSuspendLoads() {
        assert ( 2 == suspensions.size() );

        Suspend one = suspensions.get(0);
        Suspend two = suspensions.get(1);

        DecimalFormat fourPlaces = new DecimalFormat( "###.####" );
        return " " + fourPlaces.format( one.load() ) + " on " + one.refId() + ". "
                + fourPlaces.format( two.load() ) + " on " + two.refId() + ".";
    }

    @Override
    public void dom(Draw draw, View mode) {
        SvgElement trussRectangle = null;//draw.element("rect");
        SvgElement group = null;

        // Common setup:
        switch (mode) {
            case PLAN:
//            case TRUSS:
            case SCHEMATIC:
                group = svgClassGroup( draw, LAYERTAG );
                draw.appendRootChild(group);
                break;
            default:
                return;
        }

        // Separate details:
        switch (mode) {
            case PLAN:
                if ( positioned ) {
                    group.rectangle( draw, x - length / 2, y - size / 2, length, size, color );
                }
                else if ( null != base ) {
                    SvgElement verticalTruss =
                            group.rectangle( draw, x - size / 2, y - size / 2, size, size, color );

                    Double transformX = x + SvgElement.OffsetX();
                    Double transformY = y + SvgElement.OffsetY();
                    String transform =
                            "rotate(" + base.rotation() + "," + transformX + "," + transformY + ")";
                    verticalTruss.attribute( "transform", transform );
                    BaseCount++;

                }
                else {
//                    System.err.println( "PLAN not positioned." );
                    Double x1 = point1.x();// + MinderDom.OffsetX();
                    Double y1 = point1.y();// + MinderDom.OffsetY();
                    Double xPlan = x1 - overHang;
                    Double yPlan = y1 - size / 2;

                    trussRectangle = group.rectangle( draw, xPlan, yPlan, length, size, color );
                    Double transformX = x1 + SvgElement.OffsetX();
                    Double transformY = y1 + SvgElement.OffsetY();
                    trussRectangle.attribute("transform",
                            "rotate(" + rotation + "," + transformX + "," + transformY + ")");
                }
                break;
            case SCHEMATIC:
                if ( null != base ) {

                }
                else {
                    PagePoint trussTop =
                            new PagePoint( schematicPosition.x(), schematicPosition.y() - schematicHeight / 4 );
                    PagePoint trussBottom =
                            new PagePoint( schematicPosition.x(), schematicPosition.y() + schematicHeight / 4 );

                    group.rectangleAbsolute( draw,
                            trussTop.x() - length / 2, trussTop.y() - size / 2,
                            length, size, color );

                    SvgElement group2 =  svgClassGroup( draw, LAYERTAG );
                    draw.appendRootChild(group2);
                    group.rectangleAbsolute( draw,
                            trussBottom.x() - length / 2, trussBottom.y() - size / 2,
                            length, size, color );


//                    // For hangpoints, which is a lower priority today.
//                    if ( ! positioned ) {
//                        Double hangOneX = size + overHang;
//                        Double hangOneY = yTruss1 + size / 2;
//                        HangPoint.Draw(draw, hangOneX, hangOneY, hangOneX, hangOneY + size + size, suspend1.ref());
//
//                        Double hangTwoX = size + length - overHang;
//                        Double hangTwoY = yTruss1 + size / 2;
//                        HangPoint.Draw(draw, hangTwoX, hangTwoY, hangTwoX, hangTwoY + size + size, suspend2.ref());
//                    }


                    Double topTextX = trussTop.x() + length / 2 + Schematic.TextSpace;
                    Double topTextY = trussTop.y();
                    SvgElement idText = group.textAbsolute( draw, id + " top layer", topTextX, topTextY, color );

                    Double bottomTextX = trussBottom.x() + length / 2 + Schematic.TextSpace;
                    Double bottomTextY = trussBottom.y();
                    SvgElement idText2 = group.textAbsolute(draw, id + " bottom layer", bottomTextX, bottomTextY, color);


                    trussCounted = TrussCount;
                    TrussCount++;
                }
                break;
            default:
//                System.err.println( "default." );
                return;
        }



//        transform = "rotate(-45 100 100)"
//        switch (mode) {
//            case PLAN:
//                dimmerRectangle.setAttribute( "x", x1.toString() );
//                dimmerRectangle.setAttribute( "y", y1.toString() );
//                dimmerRectangle.setAttribute( "width", length.toString() );
//                break;
//            case SECTION:
//                dimmerRectangle.setAttribute( "x", boxOrigin.y().toString() );
//                dimmerRectangle.setAttribute( "y", height.toString() );
//                dimmerRectangle.setAttribute( "width", DIAMETER.toString() );
//                break;
//            case FRONT:
//                dimmerRectangle.setAttribute( "x", boxOrigin.x().toString() );
//                dimmerRectangle.setAttribute( "y", height.toString() );
//                dimmerRectangle.setAttribute( "width", length.toString() );
//                break;
//            default:
//
//        }
//        System.err.println( "Dom done!" );
    }

    @Override
    public void countReset() {
//        Count = 0;
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        if( 0 >= BaseCount ) { return start; };

        SvgElement group = svgClassGroup( draw, LAYERNAME );
        group.attribute( "transform", "translate(" + start.x() + "," + start.y() + ")" );
        draw.appendRootChild(group);

        group.rectangleAbsolute(draw, 0.0, 0.0, 12.0, 12.0, color);
        group.rectangleAbsolute(draw, 3.0, 3.0, 6.0, 6.0, color);

        Double x = Legend.TEXTOFFSET;
        Double y = 8.0;
        group.textAbsolute(draw, "Truss on base", x, y, Legend.TEXTCOLOR);

        x = Legend.QUANTITYOFFSET;
        group.textAbsolute(draw, BaseCount.toString(), x, y, Legend.TEXTCOLOR);

        PagePoint finish = new PagePoint( start.x(), start.y() + 9 );
        return finish;
    }
}
