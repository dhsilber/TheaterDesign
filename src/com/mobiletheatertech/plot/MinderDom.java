package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.ArrayList;

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
public abstract class MinderDom extends Verifier {

    private static Integer xOffset = 0;
    private static Integer yOffset = 0;

    /**
     *
     */
    public MinderDom( Element element ) throws InvalidXMLException {
        super (element);
    }
//
//    public static void Offset( Integer x, Integer y ) {
//        xOffset = x;
//        yOffset = y;
//    }
//
//    public static Integer OffsetX() {
//        return xOffset;
//    }
//
//    public static Integer OffsetY() {
//        return yOffset;
//    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllPlan( Draw draw ) throws MountingException, ReferenceException {
        for (ElementalLister item : LIST) {
            if ( MinderDom.class.isInstance( item )) {
                MinderDom thing = (MinderDom) item;
//System.err.println("DomAllPlan " + thing.getClass().getSimpleName() + ": " + thing.toString() +".");

                thing.dom( draw, View.PLAN );
            }
        }
//System.err.println("DomAllPlan Done.");
    }

    /**
     * Give each of the plot items an opportunity to modify the SVG DOM.
     *
     * @param draw graphics manager
     */
    public static void DomAllSection( Draw draw ) throws MountingException, ReferenceException {
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
    public static void DomAllFront( Draw draw ) throws MountingException, ReferenceException {
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
    public static void DomAllTruss( Draw draw ) throws MountingException, ReferenceException {
        // This is a terrible, terrible hack
        HangPoint.SYMBOLGENERATED = false;

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
    public abstract void dom( Draw draw, View mode ) throws MountingException, ReferenceException;

    // TODO Move this to SvgElement
    public static SvgElement svgClassGroup( Draw draw,
                                    String className ) {
        SvgElement element = draw.element("g");
        element.attribute("class", className);

        return element;
    }

//    public static SvgElement svgCircle( Draw draw,
//                                        Integer x, Integer y, Integer r,
//                                        String color ) {
//        Integer xSet = x + xOffset;
//        Integer ySet = y + yOffset;
//
//        SvgElement element = draw.element("circle");
//        element.attribute("cx", xSet.toString());
//        element.attribute("cy", ySet.toString());
//        element.attribute("r", r.toString());
//        element.attribute("stroke", color);
//        element.attribute("fill", "none" );
//
//        return element;
//    }

//    public static SvgElement svgLine( Draw draw,
//                                      Integer x1, Integer y1, Integer x2, Integer y2,
//                                      String color ) {
//        Integer x1Set = x1 + xOffset;
//        Integer y1Set = y1 + yOffset;
//        Integer x2Set = x2 + xOffset;
//        Integer y2Set = y2 + yOffset;
//
//        SvgElement element = draw.element("line");
//        element.attribute("x1", x1Set.toString());
//        element.attribute("y1", y1Set.toString());
//        element.attribute("x2", x2Set.toString());
//        element.attribute("y2", y2Set.toString());
//        element.attribute("stroke", color);
////        element.attribute( "stroke-width", "2" );
//
//        return element;
//    }

//    public static SvgElement svgPath( Draw draw,
//                                      String path,
//                                      String color) {
//        SvgElement element = draw.element( "path" );
//        element.attribute("fill", "none");
//        element.attribute("stroke", color );
//        element.attribute("stroke-width", "2");
//        element.attribute("d", path );
//
//        return element;
//    }

//    public static SvgElement svgRectangle( Draw draw,
//                                    Integer x, Integer y, Integer width, Integer height,
//                                    String color ) {
//        Integer xSet = x + xOffset;
//        Integer ySet = y + yOffset;
//
//        SvgElement element = draw.element("rect");
//        element.attribute("x", xSet.toString());
//        element.attribute("y", ySet.toString());
//        element.attribute("width", width.toString());
//        element.attribute("height", height.toString());
//        element.attribute("stroke", color);
//        element.attribute("fill", "none");
//
//        return element;
//    }

//    public static SvgElement svgSymbol( Draw draw,
//                                        String id ) {
//        SvgElement element = draw.element("symbol");
//        element.attribute("id", id);
//        element.attribute("overflow", "visible");
//
//        return element;
//    }

//    public static SvgElement svgText( Draw draw,
//                               String text,
//                               Integer x, Integer y,
//                               String color ) {
//        Integer xSet = x + xOffset;
//        Integer ySet = y + yOffset;
//
//        SvgElement element = draw.element("text");
//
//        element.attribute( "x", xSet.toString() );
//        element.attribute( "y", ySet.toString() );
//        element.attribute( "fill", color );
//        element.attribute( "stroke", "none" );
//        element.attribute( "font-family", "serif" );
//        element.attribute( "font-size", "10" );
//
//        Text foo = draw.document().createTextNode( text );
//        element.appendChild( foo );
//
//        return element;
//    }
//
//    public static SvgElement svgUse( Draw draw,
//                                     String id,
//                                     Integer x, Integer y ) {
//        Integer xSet = x + xOffset;
//        Integer ySet = y + yOffset;
//
//        SvgElement element = draw.element("use");
//        element.attribute("xlink:href", "#" + id);
//        element.attribute("x", xSet.toString());
//        element.attribute("y", ySet.toString());
//
//        return element;
//    }
}
