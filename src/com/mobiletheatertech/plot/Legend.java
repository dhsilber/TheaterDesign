package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import java.util.Map;
import java.util.TreeMap;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:21 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * @author dhs
 * @since 0.0.7
 */
public class Legend {

    public static final String TEXTCOLOR = "black";

    private static TreeMap<Integer, Legendable> LEGENDLIST = new TreeMap<>();
    private static Integer HEIGHT = 0;
    private static Integer WIDEST = 0;

    private static PagePoint INITIAL;
    private static Draw DRAW;

    static final String CATEGORY = "legend";

    static final Integer TEXTOFFSET = 20;

    /**
     * Register a callback function that will draw an individual legend entry
     *
     * @param callback {@code Legendable} method which will provide the SVG DOM for an individual
     *                 legend entry
     * @param width    to allow for this entry
     * @param height   to allow for this entry
     */
    public static void Register( Legendable callback, int width, int height, LegendOrder order ) {

        LEGENDLIST.put( order.next(), callback );

        WIDEST = (width > WIDEST)
                 ? width
                 : WIDEST;

        HEIGHT += height + 7;
    }

    /**
     * After everything that might want a Legend slot has registered, draw the outline and start off
     * the legend with the name of the plot.
     */
    public static void Startup( Draw draw, View mode, Integer start, Integer width ) throws ReferenceException {
        DRAW = draw;

        Element group = draw.document().createElement( "g" );
        group.setAttribute( "class", "legend" );
        draw.appendRootChild( group );

        Integer x = start;
        Integer y = 17;

        Integer center = x + (width / 2);
        headerText( draw, group, center, y, Event.Name() );
        y += 17;
        headerText( draw, group, center, y, Venue.Building() );
        y += 17;
        headerText( draw, group, center, y, Venue.Name() );
        y += 17;

        INITIAL = new PagePoint( start + 20, y );

        // TODO Adding the Y offset to the box height is really cheating
        Integer boxHeight = y + HEIGHT + SvgElement.OffsetY();

        Element box = draw.document().createElement( "rect" );
        box.setAttribute( "fill", "none" );
        box.setAttribute( "x", x.toString() );
        box.setAttribute( "y", "0" );
        box.setAttribute( "width", width.toString() );
        box.setAttribute( "height", boxHeight.toString() );

        group.appendChild( box );
    }

    /**
     * Draw a centered heading for the Legend
     *
     * @param draw
     * @param parent
     * @param center
     * @param y
     * @param text
     * @throws ReferenceException
     */
    public static void headerText(
            Draw draw, Element parent, Integer center, Integer y, String text )
            throws ReferenceException
    {
        Text textNode = draw.document().createTextNode( text );
        SvgElement element = draw.element("text");
        element.attribute( "class", "heading" );
        element.attribute( "x", center.toString() );
        element.attribute( "y", y.toString() );
        element.appendChild( textNode );
        parent.appendChild( element.element() );
    }

    /**
     * Provide the width of the widest legend entry.
     *
     * @return width of widest legend
     * @since 0.0.13
     */
    public static Integer Widest() {
        return WIDEST;
    }

    /**
     * Provide a width for the Legend box which will give the completed
     * drawing the proportions of 11" x 17" paper.
     *
     * @return
     * @throws ReferenceException
     */
    public static Integer PlanWidth() throws ReferenceException {
        Double widthAvailable = (Venue.Depth() * 1.54) - Venue.Width() - 5;
        Integer width = widthAvailable.intValue();

        return width;
    }

    /**
     * Invoke all of the registered callbacks.
     */
    public static void Callback() {
        PagePoint start = INITIAL;
        PagePoint finish;
        for (Map.Entry<Integer, Legendable> entry : LEGENDLIST.entrySet()) {
            finish = entry.getValue().domLegendItem( DRAW, start );
            start = new PagePoint( INITIAL.x(), finish.y() + 7 );
        }
    }
}
