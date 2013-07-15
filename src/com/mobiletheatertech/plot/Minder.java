package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.*;
import java.util.ArrayList;

/**
 * Minder keeps a list of all of the plot items that are created.
 * <p/>
 * All plot item classes need to implement a static method {@code ParseXML} to parse an XML
 * description of the item.
 *
 * @author dhs
 * @since 0.0.2
 */
public abstract class Minder {

    public static ArrayList<Minder> LIST = new ArrayList<>();

    public Minder() {
        LIST.add( this );
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas Drawing area.
     */
    /*
     * Draw each object to the graphics canvas.
     * 
     * Apply Transform as needed to move drawing onto display area.
     * 
     */
    public static void DrawAllPlan( Graphics2D canvas ) {
        for (Minder item : LIST) {
            item.drawPlan( canvas );
        }
    }


    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas Drawing area.
     */
    /*
     * Draw each object to the graphics canvas.
     *
     * Apply Transform as needed to move drawing onto display area.
     *
     */
    public static void DrawAllSection( Graphics2D canvas ) {
        for (Minder item : LIST) {
            item.drawSection( canvas );
        }
    }


    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas Drawing area.
     */
    /*
     * Draw each object to the graphics canvas.
     *
     * Apply Transform as needed to move drawing onto display area.
     *
     */
    public static void DrawAllFront( Graphics2D canvas ) {
        for (Minder item : LIST) {
            item.drawFront( canvas );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw Graphics manager.
     */
    public static void DomAllPlan( Draw draw ) {
        for (Minder item : LIST) {
            item.dom( draw );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw Graphics manager.
     */
    public static void DomAllSection( Draw draw ) {
        for (Minder item : LIST) {
            item.dom( draw );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw Graphics manager.
     */
    public static void DomAllFront( Draw draw ) {
        for (Minder item : LIST) {
            item.dom( draw );
        }
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and convert it to an
     * {@code Integer}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return Integer value of attribute
     * @throws AttributeMissingException
     */
    protected Integer getIntegerAttribute( Element element, String name )
            throws AttributeMissingException
    {

        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            throw new AttributeMissingException(
                    this.getClass().getSimpleName(), name );
        }

        return new Integer( value );
    }

    /**
     * Acquire the named attribute from the {@link org.w3c.dom.Element Element} and convert it to a
     * {@code String}.
     *
     * @param element DOM Element defining a venue.
     * @param name    name of attribute.
     * @return Integer value of attribute
     * @throws AttributeMissingException
     */
    protected String getStringAttribute( Element element, String name )
            throws AttributeMissingException
    {

        String value = element.getAttribute( name );
        if (value.isEmpty()) {
            throw new AttributeMissingException(
                    this.getClass().getSimpleName(), name );
        }

        return value;
    }

    /**
     * All plot item classes need to implement a method to draw the plan view of that item.
     *
     * @param canvas Drawing media.
     */
    public abstract void drawPlan( Graphics2D canvas );

    /**
     * All plot item classes need to implement a method to draw the section view of that item.
     *
     * @param canvas Drawing media.
     */
    public abstract void drawSection( Graphics2D canvas );

    /**
     * All plot item classes need to implement a method to draw the front view of that item.
     *
     * @param canvas Drawing media.
     */
    public abstract void drawFront( Graphics2D canvas );

    /**
     * This is called for each Plot item to give it a chance to update the DOM for the generated
     * SVG. Items that make use of this functionality will replace this comment with specifics.
     *
     * @param draw Canvas/DOM manager.
     */
    public abstract void dom( Draw draw );

}
