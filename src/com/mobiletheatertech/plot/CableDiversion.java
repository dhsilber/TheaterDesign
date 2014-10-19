package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by dhs on 9/16/14.
 */
public class CableDiversion extends Elemental {

    private static ArrayList<CableDiversion> DIVERSIONLIST = new ArrayList<>();
    private ArrayList<Point> pointList = new ArrayList<>();

    public CableDiversion ( Element element ) throws AttributeMissingException, InvalidXMLException {
        super(element);

        DIVERSIONLIST.add( this );

        NodeList points = element.getElementsByTagName( "diversion-point" );
        int length = points.getLength();
        for ( int index = 0; index < length; index++ ) {
            Node node = points.item( index );

            if ( null != node ) {
                if ( node.getNodeType() == Node.ELEMENT_NODE ) {
                    Element subElement = (Element) node;
                    CableDiversionPoint point = new CableDiversionPoint( subElement );
                    pointList.add( point.point() );
                }
            }
        }
    }

    public static void InsertDiversion( ArrayList<Point> vertices ) {

        ArrayList<CableDiversion> lastMatches = new ArrayList<>();
        ArrayList<Integer> lastIndices = new ArrayList<>();

        // Find all of the diversions for which the last element intersect the
        // path and keep a list of them.
        // Also keep a list of indices into the vertex list for the point before
        // this intersection.
        FindLastIntercepts(vertices, lastMatches, lastIndices);
        Point aPoint;

        // Now go through the diversions again. When one is found in which
        // the first element intersects the path, see if the last element of
        // that diversion is also on that path, and if so, insert the
        // diversion into the path.
        aPoint = vertices.get( 0 );
        for ( int index = 1; index < vertices.size(); index++ ) {
            Point bPoint = vertices.get( index);
//        for ( Point bPoint : vertices ) {
            for ( CableDiversion diversion : DIVERSIONLIST ) {
                Rectangle rect = MakeRectangle(diversion.first());
                if( rect.intersectsLine( aPoint.x(), aPoint.y(), bPoint.x(), bPoint.y() ) ) {
                    if ( lastMatches.contains( diversion ) ) {

                        int good = vertices.indexOf( aPoint );
//                        vertices.addAll( good, diversion.pointList );
                        int bad = vertices.indexOf( bPoint );
//                        Point cPoint = aPoint;
//                        for ( int index =  Point dPoint = vertices.get( ))

                        int vertexIndex = lastIndices.get( lastMatches.indexOf( diversion ) );
                        Point otherA = vertices.get( vertexIndex );

                        UpdateVertexList( vertices, diversion.pointList, good + 1, vertexIndex + 1 );

                    }
                }
            }
            aPoint = bPoint;
        }
    }

    static void FindLastIntercepts(
            ArrayList<Point> vertices,
            ArrayList<CableDiversion> lastMatches,
            ArrayList<Integer> lastIndices) {
        Point aPoint = vertices.get( 0 );
        for ( Point bPoint : vertices ) {
            for ( CableDiversion diversion : DIVERSIONLIST ) {
                if( Intersects(diversion.last(), aPoint, bPoint) ) {
                    lastMatches.add( diversion );
                    lastIndices.add( vertices.indexOf( aPoint ) );
                }
            }
            aPoint = bPoint;
        }
    }

    static boolean Intersects( Point interception, Point start, Point end ) {
        Rectangle rect = MakeRectangle( interception );
        return rect.intersectsLine( start.x(), start.y(), end.x(), end.y() );
    }

    static void UpdateVertexList(
            ArrayList<Point> vertices, ArrayList<Point> diversionPoints, int start, int end ) {

        if ( start == end ) {
            // Both intercepts are in the same wall. No point
            // to the diversion.
            return;
        }
        else  {
            vertices.subList( start, end ).clear();
            if ( start < end ) {
                vertices.addAll( start, diversionPoints );
            }
            else {
                vertices.addAll( start, ReverseList( diversionPoints ) );
            }
        }
    }

    private static Rectangle MakeRectangle( Point point ) {
        return new Rectangle( point.x().intValue() - 5, point.y().intValue() - 5, 11, 11 );
    }

    private static ArrayList<Point> ReverseList( ArrayList<Point> list ) {
        ArrayList<Point> backwards = new ArrayList<>();

        for ( int index = list.size(); --index >= 0; ) {
            backwards.add( list.get( index ));
        }

        return backwards;
    }

    public Point first() {
        return pointList.get( 0 );
    }

    public Point last() {
        return pointList.get( pointList.size() - 1 );
    }
}
