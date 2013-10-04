package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:21 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * @author dhs
 * @since 0.0.7
 */
public class Legend {

    private static ArrayList<Legendable> LEGENDLIST = new ArrayList();
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
    public static void Register( Legendable callback, int width, int height ) {
        LEGENDLIST.add( callback );

        WIDEST = (width > WIDEST)
                 ? width
                 : WIDEST;

        HEIGHT += height + 7;
    }

    /**
     * After everything that might want a Legend slot has registered, draw the outline and start off
     * the legend with the name of the plot.
     */
    public static void Startup( Draw draw ) {
        DRAW = draw;
        INITIAL = new PagePoint( Venue.Width() + 20, 15 );

        Element box = draw.document().createElement( "rect" );
        box.setAttribute( "fill", "none" );
        Integer x = Venue.Width() + 5;
        box.setAttribute( "x", x.toString() );
        box.setAttribute( "y", "0" );
        box.setAttribute( "width", WIDEST.toString() );
        box.setAttribute( "height", HEIGHT.toString() );

        draw.appendRootChild( box );
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
     * Invoke all of the registered callbacks.
     */
    public static void Callback() {
        PagePoint start = INITIAL;
        PagePoint finish;
        for (Legendable item : LEGENDLIST) {
            finish = item.domLegendItem( DRAW, start );
            start = new PagePoint( INITIAL.x(), finish.y() + 7 );
        }
    }
}
