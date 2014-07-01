package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * MinderDom keeps a list of all of the plot items that are created and provides methods to ensure
 * that each plot item has a chance to draw itself.
 * <p/>
 * All plot item classes need to implement a static method {@code ParseXML} to parse an XML
 * description of the item.
 *
 * @author dhs
 * @since 0.0.20 (split off from 0.0.2 code)
 */
public abstract class MinderDom extends Elemental {

    /**
     * Every <@code MinderDom}-derived object is referenced from this list.
     */
    public static ArrayList<MinderDom> LIST = new ArrayList<>();

    /**
     *
     */
    public MinderDom( Element element ) throws InvalidXMLException {
        super (element);

//        if (null == element) {
//            throw new InvalidXMLException( this.getClass().getSimpleName() + " element unexpectedly null!" );
//        }

        LIST.add( this );
    }

    /**
     * @throws InvalidXMLException
     * @throws LocationException
     */
    public static void VerifyAll()
            throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException
    {
        for (MinderDom item : LIST) {
            item.verify();
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllPlan( Draw draw ) throws MountingException, ReferenceException {
        for (MinderDom item : LIST) {
//            System.out.println( "MinderDom.DomAllPlan: About to dom "+item.getClass().getSimpleName()+".");
            item.dom( draw, View.PLAN );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllSection( Draw draw ) throws MountingException, ReferenceException {
        for (MinderDom item : LIST) {
            item.dom( draw, View.SECTION );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllFront( Draw draw ) throws MountingException, ReferenceException {
        for (MinderDom item : LIST) {
            item.dom( draw, View.FRONT );
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllTruss( Draw draw ) throws MountingException, ReferenceException {
        // This is a terrible, terrible hack
        HangPoint.SYMBOLGENERATED = false;

        for (MinderDom item : LIST) {
            item.dom( draw, View.TRUSS );
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
            throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException;

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
