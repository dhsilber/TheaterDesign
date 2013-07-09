package com.mobiletheatertech.plot;

/**
 * Represent a point in the plot space.
 * 
 * Keeps track of extremes of ranges of coordinate values across all instances.
 *
 * @author dhs
 * @since 0.0.2
 */
public class Point {
    
    int X;
    int Y;
    int Z;
    
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
        X = x;
        Y = y;
        Z = z;

        SmallX = X < SmallX ? X : SmallX;
        SmallY = Y < SmallY ? Y : SmallY;
        SmallZ = Z < SmallZ ? Z : SmallZ;

        LargeX = X > LargeX ? X : LargeX;
        LargeY = Y > LargeY ? Y : LargeY;
        LargeZ = Z > LargeZ ? Z : LargeZ;
    }
    
    /**
     * Provide this point's x coordinate.
     * 
     * @return X-coordinate
     */
    public int x(){
        return X;
    }
    
    /**
     * Provide this point's y coordinate.
     * 
     * @return Y-coordinate
     */
    public int y(){
        return Y;
    }
    
    /**
     * Provide this point's z coordinate.
     * 
     * @return Z-coordinate
     */
    public int z(){
        return Z;
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
        double legX = X - point.x();
        double legY = Y - point.y();
        double legZ = Z - point.z();

        return Math.sqrt( legX * legX + legY * legY + legZ * legZ );
    }
}
