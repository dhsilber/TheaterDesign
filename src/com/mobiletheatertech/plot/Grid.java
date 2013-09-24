package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/23/13 Time: 1:10 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Draw the grid that underlays the drawing.
 * <p/>
 * I'm drawing faint grid lines every 12 units, with every tenth one being slightly less faint. This
 * makes sense if one is working with inches. Should anyone want to use this software with other
 * metrics, an attribute to specify grid spacing will need to be implemented.
 *
 * @author dhs
 * @since 0.0.10
 */
public class Grid {

    /**
     * Draw the grid.
     *
     * @param draw canvas / DOM manager
     */
    public static void DOM( Draw draw ) {
        Integer depth = Venue.Depth();
        Integer width = Venue.Width();

        for (Integer x = 1; x <= Venue.Width(); x += 12) {
            String opacity = ((x % 120) == 1)
                             ? "0.2"
                             : "0.1";
            verticalLine( draw, depth, x, opacity );
//            System.out.println( "V Line: "+ x+ ", Opacity: "+ opacity);
        }
        for (Integer y = 1; y <= Venue.Depth(); y += 12) {
            String opacity = ((y % 120) == 1)
                             ? "0.2"
                             : "0.1";
            horizontalLine( draw, width, y, opacity );
//            System.out.println( "H Line: "+ y+ ", Opacity: "+ opacity );
        }
    }

    private static void verticalLine( Draw draw, Integer end, Integer x, String opacity ) {
        line( draw, x, 0, x, end, opacity );
    }

    /*  */
    private static void horizontalLine( Draw draw, Integer end, Integer y, String opacity ) {
        line( draw, 0, y, end, y, opacity );
    }

    /* Generate the SVG XML for a line. */
    private static void line( Draw draw, Integer x1, Integer y1, Integer x2, Integer y2,
                              String opacity )
    {
        Element line;
        line = draw.element( "line" );
        line.setAttribute( "x1", x1.toString() );
        line.setAttribute( "y1", y1.toString() );
        line.setAttribute( "x2", x2.toString() );
        line.setAttribute( "y2", y2.toString() );

        line.setAttribute( "stroke", "blue" );
        line.setAttribute( "stroke-opacity", opacity );

        draw.appendRootChild( line );
    }

}
