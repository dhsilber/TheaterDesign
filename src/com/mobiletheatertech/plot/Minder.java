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
public abstract class Minder extends Elemental {

    /**
     * Every <@code Minder}-derived object is referenced from this list.
     */
    public static ArrayList<Minder> LIST = new ArrayList<>();

    /**
     *
     */
    public Minder( Element element ) throws InvalidXMLException {
        if (null == element) {
            throw new InvalidXMLException( "Element unexpectedly null!" );
        }
        LIST.add( this );
    }

    /**
     * @throws InvalidXMLException
     * @throws LocationException
     */
    public static void VerifyAll()
            throws FeatureException, InvalidXMLException, LocationException, ReferenceException
    {
        for (Minder item : LIST) {
            item.verify();
        }
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas drawing media
     */
    public static void DrawAllPlan( Graphics2D canvas ) throws MountingException, ReferenceException {
        for (Minder item : LIST) {
            item.drawPlan( canvas );
        }
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas drawing media
     */
    public static void DrawAllSection( Graphics2D canvas ) throws MountingException, ReferenceException {
        for (Minder item : LIST) {
            item.drawSection( canvas );
        }
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas drawing media
     * @throws MountingException
     */
    public static void DrawAllFront( Graphics2D canvas )
            throws MountingException, ReferenceException
    {
        for (Minder item : LIST) {
            item.drawFront( canvas );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw graphics manager
     */
    public static void DomAllPlan( Draw draw ) throws MountingException, ReferenceException {
        for (Minder item : LIST) {
            item.dom( draw, View.PLAN );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw graphics manager
     */
    public static void DomAllSection( Draw draw ) throws MountingException, ReferenceException {
        for (Minder item : LIST) {
            item.dom( draw, View.SECTION );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the document after the drawing is done.
     *
     * @param draw graphics manager
     */
    public static void DomAllFront( Draw draw ) throws MountingException, ReferenceException {
        for (Minder item : LIST) {
            item.dom( draw, View.FRONT );
        }
    }

    /**
     * Hook to allow each {@code Minder}-derived instance to perform sanity checks after all XML has
     * been parsed.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @throws InvalidXMLException if an invalid combination of XML specifications is found
     * @throws LocationException   if certain plot items don't fit in available physical space
     */
    public abstract void verify()
            throws FeatureException, InvalidXMLException, LocationException, ReferenceException;

    /**
     * Hook to allow each {@code Minder}-derived instance to draw the plan view of that item.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @param canvas drawing media
     * @throws MountingException if mounting location cannot be established
     */
    public abstract void drawPlan( Graphics2D canvas ) throws MountingException, ReferenceException;

    /**
     * Hook to allow each {@code Minder}-derived instance to draw the section view of that item.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @param canvas drawing media
     * @throws MountingException if mounting location cannot be established
     */
    public abstract void drawSection( Graphics2D canvas )
            throws MountingException, ReferenceException;

    /**
     * Hook to allow each {@code Minder}-derived instance to draw the front view of that item.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @param canvas drawing media
     * @throws MountingException if mounting location cannot be established
     */
    public abstract void drawFront( Graphics2D canvas )
            throws MountingException, ReferenceException;

    /**
     * Hook to allow each {@code Minder}-derived instance to update the DOM for the generated SVG.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     * @throws MountingException if mounting location cannot be established
     */
    public abstract void dom( Draw draw, View mode ) throws MountingException, ReferenceException;

}
