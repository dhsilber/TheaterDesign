package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 12/6/13 Time: 3:40 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.21
 */

//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.LinearRing;
//import com.vividsolutions.jts.geom.Polygon;
//import com.vividsolutions.jts.operation.predicate.RectangleContains;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Represents an area in space.
 * <p/>
 * Provides a method to determine if a rectangle fits entirely withing the {@code Shape}.
 */
public class Shape {
    //    ArrayList<Point> vertices=new ArrayList<>();
//    int smallX = Integer.MAX_VALUE;
//    int smallY = Integer.MAX_VALUE;
//    int largeX;
//    int largeY;
    Polygon polygon;
    int[] xPoints;
    int[] yPoints;
    Area area;


    public Shape(String prototype) throws DataException {

        if (prototype.isEmpty()) {
            throw new DataException("Empty Shape specification.");
        }

        String[] numbers = prototype.split("\\s+");
        int size = numbers.length;
        int pointCount = size / 2;

//        Coordinate[] points= new Coordinate[size/2];

        xPoints = new int[pointCount];
        yPoints = new int[pointCount];

        // Need at least three points to make a shape.
        if (6 > size) {
            throw new DataException("Invalid Shape specification.");
        }

        for (int index = 0, pointIndex = 0; index < size; ) {
            Integer x = Integer.valueOf(numbers[index]);
//            smallX = x < smallX ? x : smallX;
//            largeX = x > largeX ? x : largeX;


            xPoints[pointIndex] = x;

            index++;
            if (index >= size) {
                throw new DataException("Invalid Shape specification.");
            }

            Integer y = Integer.valueOf(numbers[index]);
//            smallY = y < smallY ? y : smallY;
//            largeY = y > largeY ? y : largeY;

            yPoints[pointIndex] = y;

            index++;

//            points[pointIndex]    =        new Coordinate(x,y);

//            System.out.println("Found x: " + x + ", y: " + y);
//            vertices.add(new Point(x, y,0));
        }

        polygon = new Polygon(xPoints, yPoints, pointCount);
        System.err.println(polygon.toString());
//        area=new Area(polygon);
//        System.out.println( area.toString() );

//        GeometryFactory factory= new GeometryFactory();
//
////        LinearRing ring = factory.createLinearRing(points);
//        polygon = factory.createPolygon(points);
    }

    double x() {
//        return smallX;
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getX();
    }

    double y() {
//        return smallY;
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getY();
    }

    double width() {
//        return largeX - smallX;
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getWidth();
    }

    double depth() {
//        return largeY - smallY;
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getHeight();
    }

    Boolean fits(double x, double y, double width, double depth) {

//        return polygon.contains(x,y,width,depth);

        return polygon.contains(x, y);

    }
}
