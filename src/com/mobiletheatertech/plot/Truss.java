/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Defines a box truss. <p> XML tag is 'truss'. Required children are 'suspend' or 'base' elements. Required
 * attributes are 'size' (valid values are 12 and 20) and 'length'. </p><p> For now, I'm allowing
 * any length of truss, but the count of truss is presuming ten-foot lengths. </p>
 *
 * @author dhs
 * @since 0.0.5
 */
public class Truss extends Mountable implements Legendable {

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
            throws AttributeMissingException, DataException, InvalidXMLException, KindException {
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
                throw new InvalidXMLException("Truss (" + id + ") must have x, y, and z coordinates or exactly two suspend children");
            }
            positioned = true;
        }

//        processedMark = Mark.Generate();
//        element.setAttribute("processedMark", processedMark);

        new Category( CATEGORY, this.getClass() );
    }


    public void verify() throws InvalidXMLException, ReferenceException {
//        NodeList baseList = element.getElementsByTagName( "base" );

        if ( positioned ) {
            position = new Point( x, y, z );
            return;
        }

        NodeList suspendList = element.getElementsByTagName("suspend");
        NodeList baseList = element.getElementsByTagName( "base" );

        if (2 == suspendList.getLength()) {

            suspend1 = findSuspend(0, suspendList);
            suspend2 = findSuspend(1, suspendList);

            point1 = suspend1.locate();
            point2 = suspend2.locate();
            Double slope = slope(point1, point2);
            rotation = Math.toDegrees(Math.atan(slope));

            Double supportSpan = point1.distance(point2);
            Long span = Math.round(supportSpan);
            overHang = (length - span.intValue()) / 2;
            // Given suspend1 and the slope, find where the start of the truss will end up.
        }
        else if ( 1 == baseList.getLength() ) {
            base = findBase( baseList );

            verticalCenter = new Point( base.x(), base.y(), 0.0 );
            rotation = base.rotation();

//            positioned = true;
            x = base.x();
            y = base.y();
            z = 0.0;

            if ( ! LEGENDREGISTERED ) {
                Legend.Register( this, 2, 12, LegendOrder.Structure );
                LEGENDREGISTERED = true;
            }
        }
        else {
            System.err.println("Found " + suspendList.getLength() + " suspend child nodes");
            System.err.println("Found " + baseList.getLength() + " base child nodes");
            throw new InvalidXMLException("Truss (" + id + ") must have a base or exactly two suspend children");
        }
    }

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

    // Totally untested. Yar!
    private Double slope(Point point1, Point point2) {
        Double x1 = point1.x();
        Double y1 = point1.y();
        Double x2 = point2.x();
        Double y2 = point2.y();

        Double changeInX = x1 - x2;
        Double changeInY = y1 - y2;

        return changeInY / changeInX;
    }

    @Override
    public PagePoint schematicLocation( String location ) { return null; }

    /*
    * Provide the location to draw a hanged thing at relative to the unrotated truss.
    *
    * The hanged thing can also be rotated relative to the pivot point of the truss so that it lines up correctly.
     */
    @Override
    public Point location(String location) throws InvalidXMLException, MountingException, ReferenceException {
        Character vertex = location.charAt(0);

        String distanceString = location.substring(1);
        Integer distance;
        try {
            distance = new Integer(distanceString.trim());
        } catch (NumberFormatException exception) {
            throw new InvalidXMLException("Truss (" + id + ") location not correctly formatted.");
        }

        if (0 > distance || distance > length) {
            throw new MountingException("Truss (" + id + ") does not include location " + distance.toString() + ".");
        }

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
                        throw new InvalidXMLException("Truss (" + id + ") location does not include a valid vertex.");
                }

                return new Point(point.x() - overHang + distance, point.y() + offset, point.z() - verticalOffset );
            }
        }
    }

    /*
    Used only by Luminaire.dom() in View.TRUSS mode.
     */
    public Point relocate( String location ) throws MountingException{
        Double y;

        Character vertex = location.charAt(0);
        Double offset = size / 2;
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
            return new Place( location( location ), position, 0.0 );
        }
        else if ( null != base ) {
//            System.err.println( "base" );
            Double transformX = base.x() + SvgElement.OffsetX();
            Double transformY = base.y() + SvgElement.OffsetY();
            Point origin = new Point( transformX, transformY, 0.0 );
//            System.err.println( "base end" );
            return new Place(location(location), origin, base.rotation() );
        }
        else {
//            System.err.println( "standard" );
            Double transformX = point1.x() + SvgElement.OffsetX();
            Double transformY = point1.y() + SvgElement.OffsetY();
            Point origin = new Point( transformX, transformY, point1.z() );
//            System.err.println( "standard end" );
            return new Place(location(location), origin, rotation);
        }
    }

    @Override
    public void dom(Draw draw, View mode) {
        SvgElement trussRectangle = null;//draw.element("rect");
        SvgElement group = null;

        // Common setup:
        switch (mode) {
            case PLAN:
            case TRUSS:
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
            case TRUSS:
                if ( null != base ) {

                }
                else if( ! positioned ) {
                    Double yTruss1 = TrussCount * 320 + 80;
                    trussRectangle= group.rectangle( draw, size, yTruss1, length, size, color );

                    Double yTruss2 = TrussCount * 320 + 220;

                    SvgElement group2 =  svgClassGroup( draw, LAYERTAG );
                    draw.appendRootChild(group2);
                    SvgElement trussRectangle2 = group2.rectangle( draw, size, yTruss2, length, size, color );
//                trussRectangle2.setAttribute("height", size.toString());
//                trussRectangle2.setAttribute("fill", "none");
//                trussRectangle2.setAttribute("stroke", color);
//                trussRectangle2.setAttribute("width", length.toString());
//                group2.appendChild(trussRectangle2);

//                trussRectangle2.setAttribute("x", size.toString());
//                trussRectangle2.setAttribute("y", yTruss2.toString());

                    Double hangOneX = size + overHang;
                    Double hangOneY = yTruss1 + size / 2;
                    HangPoint.Draw( draw, hangOneX, hangOneY, hangOneX, hangOneY + size+ size, suspend1.ref() );

                    Double hangTwoX = size + length - overHang;
                    Double hangTwoY = yTruss1 + size / 2;
                    HangPoint.Draw( draw, hangTwoX, hangTwoY, hangTwoX, hangTwoY + size+size, suspend2.ref() );

//                Integer hangThreeX = size + overHang;
//                Integer hangThreeY = yTruss2 - size / 2;
//                HangPoint.Draw( draw, hangThreeX, hangThreeY, hangThreeX, hangThreeY + size, suspend1.id );
//
//                Integer hangFourX = size + length - overHang;
//                Integer hangFourY = yTruss2 - size / 2;
//                HangPoint.Draw( draw, hangFourX, hangFourY, hangFourX, hangFourY + size, suspend1.id );

                    Double textX=size *2 + length;
                    Double textY = yTruss1;
                    SvgElement idText = group.text( draw, id + " top layer", textX, textY, color );
//                draw.element("text");
//                idText.setAttribute( "x", textX.toString() );
//                idText.setAttribute( "y", textY.toString() );
//                idText.setAttribute( "fill", color );
//                idText.setAttribute( "stroke", "none" );
//                idText.setAttribute( "font-family", "sans-serif" );
//                idText.setAttribute( "font-weight", "100" );
//                idText.setAttribute( "font-size", "12pt" );
//                idText.setAttribute( "text-anchor", "left" );
//                group.appendChild( idText );

//                Text textId = draw.document().createTextNode( id + " top layer" );
//                idText.appendChild( textId );

                    Double textX2=size *2 + length;
                    Double textY2 = yTruss2;
                    SvgElement idText2 = group.text(draw, id + " bottom layer", textX2, textY2, color);
//                draw.element("text");
//                idText2.setAttribute("x", textX2.toString());
//                idText2.setAttribute("y", textY2.toString());
//                idText2.setAttribute("fill", color);
//                idText2.setAttribute("stroke", "none");
//                idText2.setAttribute("font-family", "sans-serif");
//                idText2.setAttribute("font-weight", "100");
//                idText2.setAttribute("font-size", "12pt");
//                idText2.setAttribute("text-anchor", "left");
//                group.appendChild( idText2 );

//                Text textId2 = draw.document().createTextNode( id + " bottom layer" );
//                idText2.appendChild(textId2);


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
