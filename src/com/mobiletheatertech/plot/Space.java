package com.mobiletheatertech.plot;

/**
 * An area in 3D space.
 *
 * @author dhs
 * @since 0.0.6
 */
public class Space {
    private Point origin = null;
    private Double width = 0.0;
    private Double depth = 0.0;
    private Double height = 0.0;

    /**
     * Construct a {@code Space}.
     *
     * @param origin Origin point for the {@code Space}.
     * @param width  Width of the {@code Space}.
     * @param depth  Depth of the {@code Space}.
     * @param height Height of the {@code Space}.
     */
    public Space(Point origin, Double width, Double depth, Double height) {
        this.origin = origin;
        this.width = width;
        this.depth = depth;
        this.height = height;
    }

    public Space(Point origin, Integer width, Integer depth, Integer height) {
        this.origin = origin;
        this.width = width.doubleValue();
        this.depth = depth.doubleValue();
        this.height = height.doubleValue();
    }

    /**
     * Determine if the provided {@code Space} fits entirely within this one.
     *
     * @param space (Hopefully) inner {@code Space}.
     * @return true if inner {@code Space} fits entirely within this one.
     */
    public boolean contains(Space space) {
        return (
                space.origin.x() >= origin.x() &&
                        space.origin.y() >= origin.y() &&
                        space.origin.z() >= origin.z() &&
                        space.origin.x() + space.width <= origin.x() + width &&
                        space.origin.y() + space.depth <= origin.y() + depth &&
                        space.origin.z() + space.height <= origin.z() + height
        );
    }

    public String toString() {
        return "Space: origin " + origin.toString() + ", width " + width + ", depth " + depth + ", height " + height;
    }
}
