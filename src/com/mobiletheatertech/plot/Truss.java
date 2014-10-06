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
 * Defines a box truss. <p> XML tag is 'truss'. Required children are 'suspend' elements. Required
 * attributes are 'size' (valid values are 12 and 20) and 'length'. </p><p> For now, I'm allowing
 * any length of truss, so that assemblies </p>
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

    private static int TrussCount = 0;
    private int trussCounted = Integer.MIN_VALUE;

    private String color = "dark blue";
    private Integer size = null;
    private Integer length = null;
    private Suspend suspend1 = null;
    private Suspend suspend2 = null;
    //    private String processedMark = null;
    private Element element = null;

    // Placeholder to allow tests to confirm that there is no base set, even though the base class is commented out entirely.
    private Integer base = null;

    Point point1 = null;
    Point point2 = null;
    Double rotation = null;
    Integer overHang = null;
    Point startA = null;


    /**
     * Creates a {@code Truss}.
     *
     * @param element Representation of an XML description of a {@code Truss}
     * @throws AttributeMissingException
     * @throws KindException
     */
    public Truss(Element element)
            throws AttributeMissingException, InvalidXMLException, KindException {
        super(element);

//        System.out.println("Starting location: "+Proscenium.Active().toString() );
        if (Proscenium.Active()) {
            throw new InvalidXMLException("Truss not yet supported with Proscenium.");
        }

        this.element = element;
        size = getIntegerAttribute(element, "size");
        length = getIntegerAttribute(element, "length");

        switch (size) {
            case 12:
            case 20:
                break;
            default:
                throw new KindException("Truss", size);
        }

//        processedMark = Mark.Generate();
//        element.setAttribute("processedMark", processedMark);

        new Category( CATEGORY, this.getClass() );
    }


    public void verify() throws InvalidXMLException, ReferenceException {
        assert null != element;
//        NodeList baseList = element.getElementsByTagName( "base" );

        NodeList suspendList = element.getElementsByTagName("suspend");

//        if (2 != suspendList.getLength() && 1 != baseList.getLength()) {
//            System.err.println( "Found " + suspendList.getLength() + " suspend child nodes" );
//            System.err.println( "Found " + baseList.getLength() + " base child nodes" );
//            throw new InvalidXMLException( "Truss must have exactly two suspend children" );
//        }

        if (2 != suspendList.getLength()) {
            System.err.println("Found " + suspendList.getLength() + " suspend child nodes");
            throw new InvalidXMLException("Truss (" + id + ") must have exactly two suspend children");
        }

        suspend1 = findSuspend(0, suspendList);
//        if(null==suspend1) {
//            throw new InvalidXMLException("Verify: suspend1 is null");
//        }
        suspend2 = findSuspend(1, suspendList);
//        if(null==suspend2) {
//            throw new InvalidXMLException("Verify: suspend2 is null");
//        }

        point1 = suspend1.locate();
        point2 = suspend2.locate();
        Float slope = slope(point1, point2);
        rotation = Math.toDegrees(Math.atan(slope));
//        Tan-1 (Slope Percent/100).
//        System.err.println("Slope: " + slope + "   Rotation:" + rotation);

        Double supportSpan = point1.distance(point2);
        Long span = Math.round(supportSpan);
        overHang = (length - span.intValue()) / 2;
//        System.out.println("verify() span: " + span + " overHang: " + overHang + ".");
        // Given suspend1 and the slope, find where the start of the truss will end up.
    }

    private Suspend findSuspend(int item, NodeList suspendList) {
//        System.out.println("findSuspend item: " + item + ".");
        Node node = suspendList.item(item);
//        System.out.println("findSuspend node: " + node + ".");
        // Much of this code is copied from HangPoint.ParseXML - refactor
        if (null != node) {
//            System.out.println("findSuspend node is not null.");
            if (node.getNodeType() == Node.ELEMENT_NODE) {
//                System.out.println("findSuspend node is an element.");
                Element parent = (Element) node;
                String mark = parent.getAttribute("processedMark");
//                System.out.println("findSuspend mark: " + mark + ".");
                Suspend found = Suspend.Find(mark);
//                System.out.println("findSuspend found: " + found + ".");

                return found;
            }
        }
//        System.out.println("findSuspend fell out bottom. Returning null.");
        return null;
    }

    // Totally untested. Yar!
    private float slope(Point point1, Point point2) {
        int x1 = point1.x();
        int y1 = point1.y();
        int x2 = point2.x();
        int y2 = point2.y();

        float changeInX = x1 - x2;
        float changeInY = y1 - y2;

        return changeInY / changeInX;
    }

    /*
    * Provide the location to draw a hanged thing at relative to the unrotated truss.
    *
    * The hanged thing can also be rotated relative to the pivot point of the truss so that it lines up correctly.
     */
    @Override
    public Point location(String location) throws InvalidXMLException, MountingException, ReferenceException {
        Character vertex = location.charAt(0);
        int offset = size / 2;
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

        if (null == suspend1) {
            throw new InvalidXMLException("suspend1 is null");
        }
        Point point = suspend1.locate();

        return new Point(point.x() - overHang + distance, point.y() + offset, point.z());
    }

    /*
    Used only by Luminaire.dom() in View.TRUSS mode.
     */
    public Point relocate( String location ) throws MountingException{
        Integer y;

        Character vertex = location.charAt(0);
//        System.out.println("relocate(): Count "+TrussCount );
        int offset = size / 2;
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
        Integer distance;
        try {
            distance = new Integer(distanceString.trim());
        } catch (NumberFormatException exception) {
            throw new MountingException("Truss (" + id + ") location not correctly formatted.");
        }

        if (0 > distance || distance > length) {
            throw new MountingException("Truss (" + id + ") does not include location " + distance.toString() + ".");
        }

        Point result = new Point (size + distance, y + offset, 0  );

        return result;
    }

    /*
Used only by Luminaire.dom() in code that only has effect in View.TRUSS mode.
     */
    public boolean farSide( String location ) throws MountingException{

        Character vertex = location.charAt(0);
//        System.out.println("relocate(): Count "+TrussCount );
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

        Integer transformX = point1.x() + SvgElement.OffsetX();
        Integer transformY = point1.y() + SvgElement.OffsetY();
        Point origin = new Point( transformX, transformY, point1.z() );
        return new Place(location(location), origin, rotation);
    }

//    @Override
//    public void drawPlan(Graphics2D canvas) throws ReferenceException {
//        System.out.println( "About to drawPlan Truss" );
//        canvas.setPaint( Color.MAGENTA );
//        Point point1 = suspend1.locate();
//        Point point2 = suspend2.locate();
//
//        int x1 = point1.x();
//        int y1 = point1.y();
//        int x2 = point2.x();
//        int y2 = point2.y();
//        canvas.draw( new Line2D.Float( x1, y1, x2, y2 ) );
//
//        double supportSpan = point1.distance( point2 );
//        double overHang = (length - supportSpan) / 2;
//
//        drawHelper( canvas,
//                    (int) (point1.x() - overHang),
//                    point1.y() + size / 2,
//                    size, length );
/*
        int supportSpan =

        my $supportSpan = $point1->distance( $point2 );
        my $overHang = ($self->{Length} - $supportSpan) / 2;

          drawShape_horizontal( $drawPlan,
      $point1->x - $overHang,
      $point1->y + $self->{Size}/2,
      $self->{Size}, $self->{Length}, 1 );

        sub drawShape_horizontal {
        my $drawPlan = shift;
        my $x = shift;
        my $y = shift;
        my $width = shift;
        my $length = shift;

        # Draw edges
        $drawPlan->plotColorLine(
                $x, $y,
                $x + $length, $y,
                "magenta" );
        $drawPlan->plotColorLine(
                $x, $y - $width,
                $x + $length, $y - $width,
                "magenta" );
        $drawPlan->plotColorLine(
                $x, $y,
                $x, $y - $width,
                "magenta" );
        $drawPlan->plotColorLine(
                $x + $length, $y,
                $x + $length, $y - $width,
                "magenta" );

        # Draw cross-braces
        my $side1x = $x;
        my $side1y = $y;
        my $side2x = $side1x + $width;
        my $side2y = $side1y - $width;
        while ( $x + $length > $side1x ) {
            $drawPlan->plotColorLine( $side1x, $side1y, $side2x, $side2y, "magenta" );
            $side1x += ($width * 2);
            $drawPlan->plotColorLine( $side1x, $side1y, $side2x, $side2y, "magenta" );
            $side2x += ($width * 2);
        }
    }
*/


//        System.out.println("Done drawing Truss");
//    }

    /**
     * Draw a {@code Truss} onto the provided section canvas.
     * <p/>
     * This presumes that the truss is seen end-on in this view.
     * <p/>
     * //     * @param canvas surface to draw on
     *
     * @since 0.0.5
     */
//    @Override
//    public void drawSection(Graphics2D canvas) throws ReferenceException {
//        int bottom = Venue.Height();
//
//        System.out.println("About to drawSection Truss");
//        canvas.setPaint(Color.MAGENTA);
//        Point point1 = suspend1.locate();
//
//        int shift = size / 2;
//
//        canvas.draw(new Rectangle(point1.y() - shift, bottom - point1.z() - shift,
//                size, size));
//    }

//    @Override
//    public void drawFront(Graphics2D canvas) throws ReferenceException {
//        int bottom = Venue.Height();
//
//        System.out.println("About to drawFront Truss");
//        canvas.setPaint(Color.MAGENTA);
//        Point point1 = suspend1.locate();
//        Point point2 = suspend2.locate();
//
//        double supportSpan = point1.distance(point2);
//        double overHang = (length - supportSpan) / 2;
//
//        drawHelper(canvas,
//                (int) (point1.x() - overHang),
//                bottom - point1.z() + size / 2,
//                size, length);
//    }

//    private void drawHelper(Graphics2D canvas, int x, int y, int width, int length) {
//
//        canvas.setPaint(Color.MAGENTA);
//        canvas.draw(new Line2D.Float(x, y, x + length, y));
//        canvas.draw(new Line2D.Float(x, y - width, x + length, y - width));
//        canvas.draw(new Line2D.Float(x, y, x, y - width));
//        canvas.draw(new Line2D.Float(x + length, y, x + length, y - width));
//
//    }
    @Override
    public void dom(Draw draw, View mode) {
        SvgElement trussRectangle = null;//draw.element("rect");
        SvgElement group = null;

        // Common setup:
        switch (mode) {
            case PLAN:
            case TRUSS:
                group = svgClassGroup( draw, LAYERTAG );
//                draw.element("g");
//                group.setAttribute("class", LAYERTAG);
                draw.appendRootChild(group);

//                trussRectangle.setAttribute("height", size.toString());
//                trussRectangle.setAttribute("fill", "none");
//                trussRectangle.setAttribute("stroke", color);
//                trussRectangle.setAttribute("width", length.toString());
                break;
            default:
                return;
        }

        // Separate details:
        switch (mode) {
            case PLAN:
                Integer x1 = point1.x();// + MinderDom.OffsetX();
                Integer y1 = point1.y();// + MinderDom.OffsetY();
                Integer xPlan = x1 - overHang;
                Integer yPlan = y1 - size / 2;

                trussRectangle = group.rectangle( draw, xPlan, yPlan, length, size, color );
                Integer transformX = x1 + SvgElement.OffsetX();
                Integer transformY = y1 + SvgElement.OffsetY();
                trussRectangle.attribute("transform",
                        "rotate(" + rotation + "," + transformX + "," + transformY + ")");
//                trussRectangle.setAttribute("x", xPlan.toString());
//                trussRectangle.setAttribute("y", yPlan.toString());
//                group.appendChild(trussRectangle);
                break;
            case TRUSS:
                Integer yTruss1 = TrussCount * 320 + 80;
                trussRectangle= group.rectangle( draw, size, yTruss1, length, size, color );
//                trussRectangle.setAttribute("x", size.toString());
//                trussRectangle.setAttribute("y", yTruss1.toString());

//                System.out.println( "dom() Count: "+TrussCount);
                Integer yTruss2 = TrussCount * 320 + 220;
//                draw.element("rect");
//                group.appendChild(trussRectangle);

                SvgElement group2 =  svgClassGroup( draw, LAYERTAG );
//                draw.element("g");
//                group.setAttribute("class", LAYERTAG);
                draw.appendRootChild(group2);
                SvgElement trussRectangle2 = group2.rectangle( draw, size, yTruss2, length, size, color );
//                trussRectangle2.setAttribute("height", size.toString());
//                trussRectangle2.setAttribute("fill", "none");
//                trussRectangle2.setAttribute("stroke", color);
//                trussRectangle2.setAttribute("width", length.toString());
//                group2.appendChild(trussRectangle2);

//                trussRectangle2.setAttribute("x", size.toString());
//                trussRectangle2.setAttribute("y", yTruss2.toString());

                Integer hangOneX = size + overHang;
                Integer hangOneY = yTruss1 + size / 2;
                HangPoint.Draw( draw, hangOneX, hangOneY, hangOneX, hangOneY + size+ size, suspend1.ref() );

                Integer hangTwoX = size + length - overHang;
                Integer hangTwoY = yTruss1 + size / 2;
                HangPoint.Draw( draw, hangTwoX, hangTwoY, hangTwoX, hangTwoY + size+size, suspend2.ref() );

//                Integer hangThreeX = size + overHang;
//                Integer hangThreeY = yTruss2 - size / 2;
//                HangPoint.Draw( draw, hangThreeX, hangThreeY, hangThreeX, hangThreeY + size, suspend1.id );
//
//                Integer hangFourX = size + length - overHang;
//                Integer hangFourY = yTruss2 - size / 2;
//                HangPoint.Draw( draw, hangFourX, hangFourY, hangFourX, hangFourY + size, suspend1.id );

                Integer textX=size *2 + length;
                Integer textY = yTruss1;
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

                Integer textX2=size *2 + length;
                Integer textY2 = yTruss2;
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
                break;
            default:
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
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }
}
