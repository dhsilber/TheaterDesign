package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/23/13 Time: 1:10 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Draw the grid that underlays the drawing.
 * <p/>
 * I'm drawing faint grid lines every 48 units. This
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
    Double startz = null;

    public Grid( Element element ) throws AttributeMissingException, DataException, InvalidXMLException{
        super( element );

        startx = getOptionalDoubleAttributeOrZero( element, "startx" );
        starty = getOptionalDoubleAttributeOrZero( element, "starty" );
        startz = getOptionalDoubleAttributeOrZero( element, "startz" );

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

        Point start   = null;

        // Display coordinates
        Double width  = null;
        Double height = null;

        switch (mode) {
            case PLAN:
                start = new Point( startx, starty, 0.0 );
                width = Venue.Width() + SCALETHICKNESS + startx;
                height = Venue.Depth() + SCALETHICKNESS + starty;
                break;
            case SECTION:
                start  = new Point( starty, startz, 0.0 );
                width  = Venue.Depth()  + SCALETHICKNESS + starty * 2;
                height = Venue.Height() + SCALETHICKNESS + startz * 2;
                break;
            case FRONT:
            case TRUSS:
                return;
        }

        draw.scaleLine( draw, start, width, height );

        Double startX = SCALETHICKNESS + startx % 48;
        for (Double x = startX; x <= width; x += 48) {
            String opacity = /*((x % 120) == 1)
                    ?*/ "0.2"
                    /*: "0.1"*/;
            verticalLine( draw, height, x, opacity );
        }

        Double startY = SCALETHICKNESS + starty % 48;
        for (Double y = startY; y <= height; y += 48) {
            String opacity = /*((y % 120) == 1)
                    ?*/ "0.2"
                    /*: "0.1"*/;
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
