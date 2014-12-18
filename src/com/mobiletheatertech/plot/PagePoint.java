package com.mobiletheatertech.plot;


/**
 * Represent a point on the plot drawing.
 * <p/>
 * Keeps track of extremes of ranges of coordinate values across all instances.
 *
 * @author dhs
 * @since 0.0.7
 */
public class PagePoint {

    private Double x;
    private Double y;

//    static int SmallX;
//    static int SmallY;
//
//    static int LargeX;
//    static int LargeY;

    /**
     * Construct a point at the specified coordinates.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     */
    public PagePoint( Double x, Double y ) {
        this.x = x;
        this.y = y;

//        SmallX = this.x < SmallX
//                 ? this.x
//                 : SmallX;
//        SmallY = this.y < SmallY
//                 ? this.y
//                 : SmallY;
//
//        LargeX = this.x > LargeX
//                 ? this.x
//                 : LargeX;
//        LargeY = this.y > LargeY
//                 ? this.y
//                 : LargeY;
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

//    /**
//     * Provide the largest X value that has been used.
//     *
//     * @return X-coordinate
//     */
//    public static int LargeX() {
//        return LargeX;
//    }
//
//    /**
//     * Provide the largest Y value that has been used.
//     *
//     * @return Y-coordinate
//     */
//    public static int LargeY() {
//        return LargeY;
//    }
//
//    /**
//     * Provide the smallest X value that has been used.
//     *
//     * @return X-coordinate
//     */
//    public static int SmallX() {
//        return SmallX;
//    }
//
//    /**
//     * Provide the smallest Y value that has been used.
//     *
//     * @return Y-coordinate
//     */
//    public static int SmallY() {
//        return SmallY;
//    }
//
//    /**
//     * Find the distance between two points
//     *
//     * @return distance
//     * @since 0.0.5
//     */
//    public double distance( PagePoint point ) {
//        double legX = x - point.x();
//        double legY = y - point.y();
//
//        return Math.sqrt( legX * legX + legY * legY );
//    }

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

        PagePoint point = (PagePoint) other;
        return (x.equals( point.x() ) && y.equals( point.y() ) );
    }

    @Override
    public String toString() {
        return "PagePoint {" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
