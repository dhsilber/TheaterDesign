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

    int x;
    int y;
    int z;

    static int SmallX;
    static int SmallY;
    static int SmallZ;

    static int LargeX;
    static int LargeY;
    static int LargeZ;

    /**
     * Construct a point at the specified coordinates.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @param z Z-Coordinate
     */
    public Point( int x, int y, int z ) {
        this.x = x;
        this.y = y;
        this.z = z;

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
    public Integer x() {
        return x;
    }

    /**
     * Provide this point's y coordinate.
     *
     * @return Y-coordinate
     */
    public Integer y() {
        return y;
    }

    /**
     * Provide this point's z coordinate.
     *
     * @return Z-coordinate
     */
    public Integer z() {
        return z;
    }

    /**
     * Provide the largest X value that has been used.
     *
     * @return X-coordinate
     */
    public static int LargeX() {
        return LargeX;
    }

    /**
     * Provide the largest Y value that has been used.
     *
     * @return Y-coordinate
     */
    public static int LargeY() {
        return LargeY;
    }

    /**
     * Provide the largest Z value that has been used.
     *
     * @return Z-coordinate
     */
    public static int LargeZ() {
        return LargeZ;
    }

    /**
     * Provide the smallest X value that has been used.
     *
     * @return X-coordinate
     */
    public static int SmallX() {
        return SmallX;
    }

    /**
     * Provide the smallest Y value that has been used.
     *
     * @return Y-coordinate
     */
    public static int SmallY() {
        return SmallY;
    }

    /**
     * Provide the smallest Z value that has been used.
     *
     * @return Z-coordinate
     */
    public static int SmallZ() {
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
        return (x == point.x() && y == point.y() && z == point.z());
    }

    @Override
    public String toString() {
        return "Point {" +
                " x=" + x +
                ", y=" + y +
                ", z=" + z +
                " }";
    }
}
