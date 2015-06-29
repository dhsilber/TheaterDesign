package com.mobiletheatertech.plot;

/**
 * Created by dhs on 12/18/13.
 */
public class Place {

    private Point location = null;
    private Point origin = null;
    private Double rotation = null;

    public Place( Point location, Point origin, Double rotation ) {
        this.location = location;
        this.origin = origin;
        this.rotation = rotation;
    }

    public Point location() {
        return location;
    }

    public Point origin() {
        return origin;
    }

    public Double rotation() {
        return rotation;
    }

    @Override
    public String toString()
    {
        return "Location: " + location.toString() +
                "  Origin: " + origin.toString() +
                "  Rotation: " + rotation.toString();
    }
}
