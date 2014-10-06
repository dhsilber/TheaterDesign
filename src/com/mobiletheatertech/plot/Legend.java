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

    private static TreeMap<Integer, Legendable> LEGENDLIST = new TreeMap<>();
    private static Integer HEIGHT = 0;
    private static Integer WIDEST = 0;

    private static PagePoint INITIAL;
    private static Draw DRAW;

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
    public static void Startup( Draw draw ) throws ReferenceException {
        DRAW = draw;

        Element group = draw.document().createElement( "g" );
        group.setAttribute( "class", "legend" );
        draw.appendRootChild( group );

        Integer x = Venue.Width() + 5;
        Integer y = 17;

        Integer center = x + (Width() / 2);
        headerText( draw, group, center, y, Event.Name() );
        y += 17;
        headerText( draw, group, center, y, Venue.Building() );
        y += 17;
        headerText( draw, group, center, y, Venue.Name() );
        y += 17;

        INITIAL = new PagePoint( Venue.Width() + 20, y );
        Integer boxHeight = y + HEIGHT;

        Element box = draw.document().createElement( "rect" );
        box.setAttribute( "fill", "none" );
        box.setAttribute( "x", x.toString() );
        box.setAttribute( "y", "0" );
        box.setAttribute( "width", Width().toString() );
        box.setAttribute( "height", boxHeight.toString() );

        group.appendChild( box );
    }

    public static void headerText(
            Draw draw, Element parent, Integer center, Integer y, String text )
            throws ReferenceException
    {
        Text textNode = draw.document().createTextNode( text );
        Element element = draw.element( "text" );
        element.setAttribute( "class", "heading" );
        element.setAttribute( "x", center.toString() );
        element.setAttribute( "y", y.toString() );
        element.appendChild( textNode );
        parent.appendChild( element );
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

    public static Integer Width() throws ReferenceException {
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
