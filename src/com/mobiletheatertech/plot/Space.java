package com.mobiletheatertech.plot;

/**
 * An area in 3D space.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Space {
    private Point origin = null;
    private int width = 0;
    private int depth = 0;
    private int height = 0;

    /**
     * Construct a {@code Space}.
     *
     * @param origin Origin point for the {@code Space}.
     * @param width  Width of the {@code Space}.
     * @param depth  Depth of the {@code Space}.
     * @param height Height of the {@code Space}.
     */
    public Space( Point origin, int width, int depth, int height ) {
        this.origin = origin;
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    /**
     * Determine if the provided {@code Space} fits entirely within this one.
     *
     * @param space (Hopefully) inner {@code Space}.
     * @return true if inner {@code Space} fits entirely within this one.
     */
    public boolean contains( Space space ) {
        return (
                space.origin.x() >= origin.x() &&
                        space.origin.y() >= origin.y() &&
                        space.origin.z() >= origin.z() &&
                        space.origin.x() + space.width <= origin.x() + width &&
                        space.origin.y() + space.depth <= origin.y() + depth &&
                        space.origin.z() + space.height <= origin.z() + height
        );
    }
}
