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
public class Grid extends MinderDom {

    static final String CATEGORY = "grid";

    public Grid( Element element ) throws InvalidXMLException{
        super( element );

        SvgElement.Offset( 19, 19 );

        new Category( CATEGORY, this.getClass() );
    }

    @Override
    public void verify() {
    }

    /**
     * Draw the grid.
     *
     * @param draw canvas / DOM manager
     */
    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {

        Point start = new Point( SvgElement.OffsetX(), SvgElement.OffsetY(), 0 );
        draw.scaleLine( draw, start, Venue.Width(), Venue.Depth() );

        Integer depth = Venue.Depth();
        Integer width = Venue.Width();

        for (Integer x = -119; x <= Venue.Width(); x += 48) {
            String opacity = ((x % 120) == 1)
                             ? "0.2"
                             : "0.1";
            verticalLine( draw, depth, x, opacity );
//            System.out.println( "V Line: "+ x+ ", Opacity: "+ opacity);
        }
        for (Integer y = 1; y <= Venue.Depth(); y += 48) {
            String opacity = ((y % 120) == 1)
                             ? "0.2"
                             : "0.1";
            horizontalLine( draw, width, y, opacity );
//            System.out.println( "H Line: "+ y+ ", Opacity: "+ opacity );
        }
    }

    private void verticalLine( Draw draw, Integer end, Integer x, String opacity ) {
        line( draw, x, 0, x, end, opacity );
    }

    /*  */
    private void horizontalLine( Draw draw, Integer end, Integer y, String opacity ) {
        line( draw, 0, y, end, y, opacity );
    }

    /* Generate the SVG XML for a line. */
    private void line( Draw draw, Integer x1, Integer y1, Integer x2, Integer y2,
                              String opacity )
    {
        SvgElement line = draw.line( draw, x1, y1, x2, y2, "blue" );
//        line = draw.element( "line" );
//        line.attribute("x1", x1.toString());
//        line.attribute("y1", y1.toString());
//        line.attribute("x2", x2.toString());
//        line.attribute("y2", y2.toString());
//
//        line.attribute("stroke", "blue");
        line.attribute("stroke-opacity", opacity);

//        draw.appendRootChild( line );
    }

}
