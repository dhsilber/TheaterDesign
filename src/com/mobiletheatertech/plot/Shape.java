package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 12/6/13 Time: 3:40 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.21
 */


import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Represents an area in space.
 * <p/>
 * Provides a method to determine if a rectangle fits entirely withing the {@code Shape}.
 */
public class Shape {
    Polygon polygon;
    int[] xPoints;
    int[] yPoints;

    public Shape(String prototype) throws DataException {

        if (prototype.isEmpty()) {
            throw new DataException("Empty Shape specification.");
        }

        String[] numbers = prototype.split("\\s+");
        int size = numbers.length;
        int pointCount = size / 2;

        xPoints = new int[pointCount];
        yPoints = new int[pointCount];

        // Need at least three points to make a shape.
        if (6 > size) {
            throw new DataException("Invalid Shape specification.");
        }

        for (int index = 0, pointIndex = 0; index < size; ) {
            Integer x = Integer.valueOf(numbers[index]);

            xPoints[pointIndex] = x;

            index++;
            if (index >= size) {
                throw new DataException("Invalid Shape specification.");
            }

            Integer y = Integer.valueOf(numbers[index]);

            yPoints[pointIndex] = y;

            index++;
            pointIndex++;
        }

        polygon = new Polygon(xPoints, yPoints, pointCount);
    }

    double x() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getX();
    }

    double y() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getY();
    }

    double width() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getWidth();
    }

    double depth() {
        Rectangle2D rectangle = polygon.getBounds2D();

        return rectangle.getHeight();
    }

    String toSvgPath( Double startX, Double startY ) {

        if( Proscenium.Active() ) {
            startX = Proscenium.Origin().x() + startX;
            startY = Proscenium.Origin().y() - startY;
        }

        StringBuilder result = new StringBuilder( "M " );

        for( int index = 0; index < xPoints.length; index++ ) {
            Double x = new Double( xPoints[index] ) + startX;
            result.append( x.toString() );
            result.append( " " );

            Double y = new Double( yPoints[index] ) + startY;
            result.append(y.toString());
            result.append( " " );
        }

        result.append( "Z" );

        return result.toString();
    }

    Boolean fits(double x, double y, double width, double depth) {
        return polygon.contains(x, y);
    }
}
