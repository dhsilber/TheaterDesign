package com.mobiletheatertech.plot;

import org.w3c.dom.Element;


/**
 * MinderDom keeps a list of all of the plot items that are created and provides methods to ensure
 * that each plot item has a chance to draw itself.
 * <p/>
 * This provides a layer between the program and the DOM interface so that behaviour can be changed
 * more easily.
 *
 * @author dhs
 * @since 0.0.20 (split off from 0.0.2 code)
 */
public abstract class MinderDom extends Layerer {

//    private static Integer xOffset = 0;
//    private static Integer yOffset = 0;
//
//    Layer layer = null;
//
    /**
     *
     */
    public MinderDom( Element element ) throws DataException, InvalidXMLException {
        super (element);
//
//        String name = this.getClass().getSimpleName();
//        layer = Layer.Register( name, name );
//        layer.register( this );
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllPlan( Draw draw )
            throws InvalidXMLException, MountingException, ReferenceException {
        for (ElementalLister item : LIST) {
            if ( MinderDom.class.isInstance( item )) {
                MinderDom thing = (MinderDom) item;

                thing.dom( draw, View.PLAN );
            }
        }
        CableRun.DomAll( draw, View.PLAN );
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllSection( Draw draw )
            throws InvalidXMLException, MountingException, ReferenceException {
        for (ElementalLister item : LIST) {
            if ( MinderDom.class.isInstance( item )) {
                MinderDom thing = (MinderDom) item;
                thing.dom( draw, View.SECTION );
            }
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllFront( Draw draw )
            throws InvalidXMLException, MountingException, ReferenceException {
        for (ElementalLister item : LIST) {
            if ( MinderDom.class.isInstance( item )) {
                MinderDom thing = (MinderDom) item;
                thing.dom( draw, View.FRONT );
            }
        }
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllTruss( Draw draw )
            throws InvalidXMLException, MountingException, ReferenceException {
        // This is a terrible, terrible hack
//        HangPoint.SYMBOLGENERATED = false;

        for (ElementalLister item : LIST) {
            if ( MinderDom.class.isInstance( item )) {
                MinderDom thing = (MinderDom) item;
                thing.dom( draw, View.TRUSS );
            }
        }
    }

   /**
     * Hook to allow each {@code MinderDom}-derived instance to update the DOM for the generated SVG.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     * @throws MountingException if mounting location cannot be established
     */
    public abstract void dom( Draw draw, View mode )
            throws InvalidXMLException, MountingException, ReferenceException;


    // TODO Move this to SvgElement
    public static SvgElement svgClassGroup( Draw draw,
                                    String className ) {
        SvgElement element = draw.element("g");
        element.attribute("class", className);

        return element;
    }

//    public abstract Place drawingLocation() throws InvalidXMLException, MountingException, ReferenceException
//            ;

}
