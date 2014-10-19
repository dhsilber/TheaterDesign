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

    static final Double SCALETHICKNESS = 19.0;

    Double startx = null;
    Double starty = null;

    public Grid( Element element ) throws AttributeMissingException, InvalidXMLException{
        super( element );

        startx = getOptionalDoubleAttribute( element, "startx" );
        starty = getOptionalDoubleAttribute( element, "starty" );

        SvgElement.Offset( SCALETHICKNESS + startx, SCALETHICKNESS + starty );

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

        Point start = new Point( startx, starty, 0.0 );
        Double width = Venue.Width() + SCALETHICKNESS + startx.intValue();
        Double depth = Venue.Depth() + SCALETHICKNESS + starty;
        draw.scaleLine( draw, start, width, depth );

        Double startX = SCALETHICKNESS + startx % 48;
        for (Double x = startX; x <= width; x += 48) {
            String opacity = ((x % 120) == 1)
                    ? "0.2"
                    : "0.1";
            verticalLine( draw, depth, x, opacity );
        }

        Double startY = SCALETHICKNESS + starty % 48;
        for (Double y = startY; y <= depth; y += 48) {
            String opacity = ((y % 120) == 1)
                    ? "0.2"
                    : "0.1";
            horizontalLine( draw, width, y, opacity );
        }
    }

    private void verticalLine( Draw draw, Double end, Double x, String opacity ) {
        line( draw, x, SCALETHICKNESS, x, SCALETHICKNESS + end, opacity );
    }

    /*  */
    private void horizontalLine( Draw draw, Double end, Double y, String opacity ) {
        line( draw, SCALETHICKNESS, y, SCALETHICKNESS + end, y, opacity );
    }

    /* Generate the SVG XML for a line. */
    private void line( Draw draw, Double x1, Double y1, Double x2, Double y2,
                              String opacity )
    {
        SvgElement line = draw.lineAbsolute( draw, x1, y1, x2, y2, "blue" );

        line.attribute("stroke-opacity", opacity);
    }

}
