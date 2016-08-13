package com.mobiletheatertech.plot;

/**
 * Represent a point in the plot space.
 * <p/>
 * Keeps track of extremes of ranges of coordinate values across all instances.
 *
 * @author dhs
 * @since 0.0.2
 */
public class Point {

    private Double x;
    private Double y;
    private Double z;

    static Double SmallX = Double.MAX_VALUE;
    static Double SmallY = Double.MAX_VALUE;
    static Double SmallZ = Double.MAX_VALUE;

    static Double LargeX = Double.MIN_VALUE;
    static Double LargeY = Double.MIN_VALUE;
    static Double LargeZ = Double.MIN_VALUE;

    /**
     * Construct a point at the specified coordinates.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param z Z-Coordinate
     */
    public Point( Double x, Double y, Double z ) {
        this.x = x;
        this.y = y;
        this.z = z;

        keepExtent();
    }

    public Point( Integer x, Integer y, Integer z ) {
        this.x = x.doubleValue();
        this.y = y.doubleValue();
        this.z = z.doubleValue();

        keepExtent();
    }

    void keepExtent() {
        SmallX = this.x < SmallX
                 ? this.x
                 : SmallX;
        SmallY = this.y < SmallY
                 ? this.y
                 : SmallY;
        SmallZ = this.z < SmallZ
                 ? this.z
                 : SmallZ;

        LargeX = this.x > LargeX
                 ? this.x
                 : LargeX;
        LargeY = this.y > LargeY
                 ? this.y
                 : LargeY;
        LargeZ = this.z > LargeZ
                 ? this.z
                 : LargeZ;
    }

    /**
     * Provide this point's x coordinate.
     *
     * @return X-coordinate
     */
    public Double x() {
        return x;
    }

    /**
     * Provide this point's y coordinate.
     *
     * @return Y-coordinate
     */
    public Double y() {
        return y;
    }

    /**
     * Provide this point's z coordinate.
     *
     * @return Z-coordinate
     */
    public Double z() {
        return z;
    }


    // Totally untested. Yar!
    Double slope( Point other) {
        Double x2 = other.x();
        Double y2 = other.y();

        Double changeInX = x - x2;
        Double changeInY = y - y2;

        return changeInY / changeInX;
    }


    /**
     * Provide the largest X value that has been used.
     *
     * @return X-coordinate
     */
    public static Double LargeX() {
        return LargeX;
    }

    /**
     * Provide the largest Y value that has been used.
     *
     * @return Y-coordinate
     */
    public static Double LargeY() {
        return LargeY;
    }

    /**
     * Provide the largest Z value that has been used.
     *
     * @return Z-coordinate
     */
    public static Double LargeZ() {
        return LargeZ;
    }

    /**
     * Provide the smallest X value that has been used.
     *
     * @return X-coordinate
     */
    public static Double SmallX() {
        return SmallX;
    }

    /**
     * Provide the smallest Y value that has been used.
     *
     * @return Y-coordinate
     */
    public static Double SmallY() {
        return SmallY;
    }

    /**
     * Provide the smallest Z value that has been used.
     *
     * @return Z-coordinate
     */
    public static Double SmallZ() {
        return SmallZ;
    }

    /**
     * Find the distance between two points
     *
     * @return distance
     * @since 0.0.5
     */
    public double distance( Point point ) {
        double legX = x - point.x();
        double legY = y - point.y();
        double legZ = z - point.z();

        return Math.sqrt( legX * legX + legY * legY + legZ * legZ );
    }

    public static Point OnALine( Point first, Point second, Double distanceFromFirst ) {

        // a. calculate the vector from o to g:
        Double vectorX = second.x() - first.x();
        Double vectorY = second.y() - first.y();
        Double vectorZ = second.z() - first.z();

        // b. calculate the proportion of hypotenuse
        Double factor = distanceFromFirst /
                Math.sqrt( vectorX * vectorX + vectorY * vectorY + vectorZ * vectorZ );

        // c. factor the lengths
        vectorX *= factor;
        vectorY *= factor;
        vectorZ *= factor;

        // d. calculate and Draw the new vector,
        return new Point( first.x() + vectorX, first.y() + vectorY, first.z() + vectorZ  );
    }

    /*
    Swiped from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Returnsclosestpointonsegmenttopoint.htm
     */
    /**
     * Returns closest point on segment to point - NOTE THIS IS IN 2D SPACE
     *
     * @param ss
     *            segment start point
     * @param se
     *            segment end point
     * @param p
     *            point to found closest point on segment
     * @return closest point on segment to p
     */
    public static Point GetClosestPointOnSegment(Point ss, Point se, Point p)
    {
        return GetClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
    }

    /*
    Swiped from http://www.java2s.com/Code/Java/2D-Graphics-GUI/Returnsclosestpointonsegmenttopoint.htm
     */
    /**
     * Returns closest point on segment to point - NOTE THIS IS IN 2D SPACE
     *
     * @param sx1
     *            segment x coord 1
     * @param sy1
     *            segment y coord 1
     * @param sx2
     *            segment x coord 2
     * @param sy2
     *            segment y coord 2
     * @param px
     *            point x coord
     * @param py
     *            point y coord
     * @return closets point on segment to point
     */
    public static Point GetClosestPointOnSegment( Double sx1, Double sy1,
                                                  Double sx2, Double sy2,
                                                  Double px, Double py)
    {
        double xDelta = sx2 - sx1;
        double yDelta = sy2 - sy1;

        if ((xDelta == 0) && (yDelta == 0))
        {
            throw new IllegalArgumentException("Segment start equals segment end");
        }

        double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) /
                (xDelta * xDelta + yDelta * yDelta);

        final Point closestPoint;
        if (u < 0)
        {
            closestPoint = new Point(sx1, sy1, 0.0);
        }
        else if (u > 1)
        {
            closestPoint = new Point(sx2, sy2, 0.0);
        }
        else
        {
            closestPoint = new Point( sx1 + u * xDelta, sy1 + u * yDelta, 0.0);
        }

        return closestPoint;
    }

    /**
     * Check for equality
     * <p/>
     * Much of this from the example provided in http://www.javaworld.com/javaworld/jw-06-2004/jw-0614-equals.html?page=2
     *
     * @return true if {@code Point}s are equal
     * @since 0.0.6
     */
    public boolean equals( Object other ) {

        if (this == other) return true;

        if (null == other) return false;

        if (getClass() != other.getClass()) return false;

        Point point = (Point) other;
        boolean same = ( x.equals( point.x() ) && y.equals( point.y() ) && z.equals( point.z() ) );
        if (!same) {
//            System.out.println( "X: "+x+", "+point.x()+
//                    " Y: "+y+", "+point.y()+
//                    " Z: "+z+", "+point.z()+"." );
        }
        return same;
    }

    /*
     * Started going down the path of finding the coordinates for each hanged item, but turning the math into an
      * algorithm is defeating me right now.
      *
      * @author dhs
      * @since 0.0.23
     */
//    public Point pointOnSlope( Double slope, Double distance ) {
//        // Given (x1 - x)slope = (y1 - y), solve for x1
//        double slopeTimesX = x * slope;
//        // also, x1 * slope, but we'll later isolate x1 & divide both sides by slope,
//        // so just leave x1 intact.
//
//        // Gather simple numbers
//        double rightNumber = y + slopeTimesX;
//
//        // x1 = (y1 / slope) - (rightNumber / slope)
//        rightNumber /= slope;
//
//        // x1 = (y1 / slope) - rightNumber
//
//
//
//        // Given circle equation with radius 'distance':
//        // (x1 -x)^2 + (y1-y)^2 = distance ^2
//        // (((y1 /slope) - rightNumber) - x)^2 + (y1 - y)^2 = distance ^2
//        double distSquared = distance * distance;
//
//
//
//return this;
//    }

    @Override
    public String toString() {
        return "Point {" +
                " x=" + x +
                ", y=" + y +
                ", z=" + z +
                " }";
    }
}
