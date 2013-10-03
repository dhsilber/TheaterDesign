/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Defines a box truss. <p> XML tag is 'truss'. Required children are 'suspend' elements. Required
 * attributes are 'size' (valid values are 12 and 20) and 'length'. </p><p> For now, I'm allowing
 * any length of truss, so that assemblies </p>
 *
 * @author dhs
 * @since 0.0.5
 */
public class Truss extends Minder {

    private Integer size = null;
    private Integer length = null;
    private Suspend suspend1 = null;
    private Suspend suspend2 = null;
    private String processedMark = null;
    private Element element = null;

    public static void ParseXML( NodeList list ) throws AttributeMissingException, InvalidXMLException, KindException {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new Truss( element );
                }
            }
        }
    }

    /**
     * Creates a {@code Truss}.
     *
     * @param element Representation of an XML description of a {@code Truss}
     * @throws AttributeMissingException
     * @throws KindException
     */
    public Truss( Element element )
            throws AttributeMissingException, InvalidXMLException, KindException
    {
        super( element );

        this.element = element;
        size = getIntegerAttribute( element, "size" );
        length = getIntegerAttribute( element, "length" );

        switch (size) {
            case 12:
            case 20:
                break;
            default:
                throw new KindException( "Truss", size );
        }

        processedMark = Mark.Generate();
        element.setAttribute( "processedMark", processedMark );
    }

    /**
     * @param mark Mark string to match while searching for a {@code Truss}
     * @return Provides {@code Truss} whose mark matches specified string
     */
    // Copied to Suspend - refactor to Minder?
    public static Truss Find( String mark ) {
        for (Minder thingy : Drawable.List()) {
            if (Truss.class.isInstance( thingy )) {
                if (((Truss) thingy).processedMark.equals( mark )) {
                    return (Truss) thingy;
                }
            }
        }
        return null;
    }

//    public static void VerifyAll() throws InvalidXMLException {
//        for (Minder thingy : Drawable.List()) {
//            if (Truss.class.isInstance( thingy )) {
//                ((Truss) thingy).verify();
//            }
//        }
//
//    }

    public void verify() throws InvalidXMLException {
        assert null != element;
//        NodeList baseList = element.getElementsByTagName( "base" );
        NodeList suspendList = element.getElementsByTagName( "suspend" );

//        if (2 != suspendList.getLength() && 1 != baseList.getLength()) {
//            System.err.println( "Found " + suspendList.getLength() + " suspend child nodes" );
//            System.err.println( "Found " + baseList.getLength() + " base child nodes" );
//            throw new InvalidXMLException( "Truss must have exactly two suspend children" );
//        }

        if (2 != suspendList.getLength()) {
            System.err.println( "Found " + suspendList.getLength() + " suspend child nodes" );
            throw new InvalidXMLException( "Truss must have exactly two suspend children" );
        }

        suspend1 = findSuspend( 0, suspendList );
        suspend2 = findSuspend( 1, suspendList );
    }

    private Suspend findSuspend( int item, NodeList suspendList ) {
        Node node = suspendList.item( item );
        // Much of this code is copied from HangPoint.ParseXML - refactor
        if (null != node) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element parent = (Element) node;
                String mark = parent.getAttribute( "processedMark" );
                return Suspend.Find( mark );
            }
        }
        return null;
    }

    // Totally untested. Yar!
    private float slope( Point point1, Point point2 ) {
        int x1 = point1.x();
        int y1 = point1.y();
        int x2 = point2.x();
        int y2 = point2.y();

        float changeInX = x1 - x2;
        float changeInY = y1 - y2;

        return changeInY / changeInX;
    }

    @Override
    public void drawPlan( Graphics2D canvas ) {
        System.out.println( "About to drawPlan Truss" );
        canvas.setPaint( Color.MAGENTA );
        Point point1 = suspend1.locate();
        Point point2 = suspend2.locate();

        int x1 = point1.x();
        int y1 = point1.y();
        int x2 = point2.x();
        int y2 = point2.y();
        canvas.draw( new Line2D.Float( x1, y1, x2, y2 ) );

        double supportSpan = point1.distance( point2 );
        double overHang = (length - supportSpan) / 2;

        drawHelper( canvas,
                    (int) (point1.x() - overHang),
                    point1.y() + size / 2,
                    size, length );
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


        System.out.println( "Done drawing Truss" );
    }

    /**
     * Draw a {@code Truss} onto the provided section canvas.
     * <p/>
     * This presumes that the truss is seen end-on in this view.
     *
     * @param canvas surface to draw on
     * @since 0.0.5
     */
    @Override
    public void drawSection( Graphics2D canvas ) {
        int bottom = Venue.Height();

        System.out.println( "About to drawSection Truss" );
        canvas.setPaint( Color.MAGENTA );
        Point point1 = suspend1.locate();

        int shift = size / 2;

        canvas.draw( new Rectangle( point1.y() - shift, bottom - point1.z() - shift,
                                    size, size ) );
    }

    @Override
    public void drawFront( Graphics2D canvas ) {
        int bottom = Venue.Height();

        System.out.println( "About to drawFront Truss" );
        canvas.setPaint( Color.MAGENTA );
        Point point1 = suspend1.locate();
        Point point2 = suspend2.locate();

        double supportSpan = point1.distance( point2 );
        double overHang = (length - supportSpan) / 2;

        drawHelper( canvas,
                    (int) (point1.x() - overHang),
                    bottom - point1.z() + size / 2,
                    size, length );
    }


    private void drawHelper( Graphics2D canvas, int x, int y, int width, int length ) {

        canvas.setPaint( Color.MAGENTA );
        canvas.draw( new Line2D.Float( x, y, x + length, y ) );
        canvas.draw( new Line2D.Float( x, y - width, x + length, y - width ) );
        canvas.draw( new Line2D.Float( x, y, x, y - width ) );
        canvas.draw( new Line2D.Float( x + length, y, x + length, y - width ) );

    }

    @Override
    public void dom( Draw draw, View mode ) {
    }

}
