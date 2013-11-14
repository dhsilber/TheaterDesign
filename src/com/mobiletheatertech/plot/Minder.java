package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.*;

/**
 * Minder keeps a list of all of the plot items that are created.
 * <p/>
 * All plot item classes need to implement a static method {@code ParseXML} to parse an XML
 * description of the item.
 *
 * @author dhs
 * @since 0.0.2
 */
public abstract class Minder extends MinderDom {

    /**
     * Every <@code Minder}-derived object is referenced from this list.
     */
//    public static ArrayList<Minder> LIST = new ArrayList<>();

    /**
     *
     */
    public Minder( Element element ) throws InvalidXMLException {
        super( element );
//        if (null == element) {
//            throw new InvalidXMLException( "Element unexpectedly null!" );
//        }
//        LIST.add( this );
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas drawing media
     */
    public static void DrawAllPlan( Graphics2D canvas ) throws MountingException, ReferenceException {
        for (MinderDom item : LIST) {
            if (Minder.class.isInstance( item )) {
                ((Minder) item).drawPlan( canvas );
            }
        }
    }

    /**
     * Draw each of the plot items that have been specified.
     *
     * @param canvas drawing media
     */
    public static void DrawAllSection( Graphics2D canvas ) throws MountingException, ReferenceException {
        for (MinderDom item : LIST) {
            if (Minder.class.isInstance( item )) {
                ((Minder) item).drawSection( canvas );
            }
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
        for (MinderDom item : LIST) {
            if (Minder.class.isInstance( item )) {
                ((Minder) item).drawFront( canvas );
            }
        }
    }

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
}
