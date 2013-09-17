package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/15/13 Time: 6:21 PM To change this template use
 * File | Settings | File Templates.
 *
 * @since 0.0.7
 */
public class Legend {

    private static ArrayList<Legendable> LEGENDLIST = new ArrayList();
    private static Integer HEIGHT = 0;
    private static Integer WIDEST = 0;

    private static PagePoint INITIAL;
    private static Draw DRAW;

    private static Element box;

    public static void Register( Legendable callback, int width, int height ) {
        LEGENDLIST.add( callback );

        WIDEST = (width > WIDEST)
                 ? width
                 : WIDEST;

        HEIGHT += height + 7;
    }

    /**
     * After everything that might want a Legend slot has registered and before we close out
     * graphical drawing in favor of DOM manipulation, draw the outline and start off the legend
     * with the name of the plot.
     */
    public static void Startup( Draw draw ) {
        DRAW = draw;
        INITIAL = new PagePoint( Venue.Width() + 20, 15 );

//        Graphics2D canvas = draw.canvas();
//        canvas.setPaint( Color.BLACK );
//        canvas.draw( new Rectangle( Venue.Width() + 10, 10, WIDEST, HEIGHT ) );

        box = draw.document().createElement( "rect" );
        box.setAttribute( "fill", "none" );
        Integer x = Venue.Width() + 5;
        box.setAttribute( "x", x.toString() );
        box.setAttribute( "y", "0" );
        box.setAttribute( "width", WIDEST.toString() );
        box.setAttribute( "height", HEIGHT.toString() );

        draw.appendRootChild( box );
    }

    public static void Callback() {
        PagePoint start = INITIAL;
        PagePoint finish;
        for (Legendable item : LEGENDLIST) {
            finish = item.domLegendItem( DRAW, start );
            start = new PagePoint( INITIAL.x(), finish.y() + 7 );
        }
    }
}
