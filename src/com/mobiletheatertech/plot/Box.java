package com.mobiletheatertech.plot;

/**
 * An area in 3D space.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Box {
    private Point origin = null;
    private int width = 0;
    private int depth = 0;
    private int height = 0;

    /**
     * Construct a {@code Box}.
     *
     * @param origin Origin point for the {@code Box}.
     * @param width  Width of the {@code Box}.
     * @param depth  Depth of the {@code Box}.
     * @param height Height of the {@code Box}.
     */
    public Box( Point origin, int width, int depth, int height ) {
        this.origin = origin;
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    /**
     * Determine if the provided {@code Box} fits entirely within this one.
     *
     * @param box (Hopefully) inner {@code Box}.
     * @return true if inner {@code Box} fits entirely within this one.
     */
    public boolean contains( Box box ) {
        return (
                box.origin.x() >= origin.x() &&
                        box.origin.y() >= origin.y() &&
                        box.origin.z() >= origin.z() &&
                        box.origin.x() + box.width <= origin.x() + width &&
                        box.origin.y() + box.depth <= origin.y() + depth &&
                        box.origin.z() + box.height <= origin.z() + height
        );
    }
}
