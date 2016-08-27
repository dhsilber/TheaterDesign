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

    static final String Tag = "grid";
    static final String Color= "blue";

    static final Double SCALETHICKNESS = 21.0;
    static final Double ExtraLeftSpace = 5.0;

    Double startx = null;
    Double starty = null;
    Double startz = null;

    public Grid( Element element ) throws AttributeMissingException, DataException, InvalidXMLException{
        super( element );

        startx = getOptionalDoubleAttributeOrZero( "startx" );
        starty = getOptionalDoubleAttributeOrZero( "starty" );
        startz = getOptionalDoubleAttributeOrZero( "startz" );

        SvgElement.Offset( SCALETHICKNESS + startx + ExtraLeftSpace, SCALETHICKNESS + starty );

//        new Category( Tag, this.getClass() );
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

        String opacity = "0.2";

        Point start   = null;

        // Display coordinates
        Double width  = null;
        Double height = null;

        switch (mode) {
            case PLAN:
                start = new Point( startx, starty, 0.0 );
                width = Venue.Width() + SCALETHICKNESS * 2 + startx;
                height = Venue.Depth() + SCALETHICKNESS * 2 + starty;
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

        SvgElement scale = borderLines( draw, start, width, height );
        scaleLines( draw, scale, start, width, height );
        scaleNumbers( draw, scale, start, width, height );

        Double ex = Proscenium.Origin().x() + startx;
        Double startX = (ex % 48) + SCALETHICKNESS + ExtraLeftSpace;
        for (Double x = startX; x <= width; x += 48) {
            line( draw, x, SCALETHICKNESS, x, height, opacity );
        }

        Double wy = Proscenium.Origin().y() + starty;
        Double startY = (wy % 48) + SCALETHICKNESS;
        for (Double y = startY; y <= height; y += 48) {
            line( draw, SCALETHICKNESS, y, width, y, opacity );
        }
    }

    /* Generate the SVG XML for a line. */
    private void line( Draw draw, Double x1, Double y1, Double x2, Double y2,
                              String opacity )
    {
        SvgElement line = draw.lineAbsolute( draw, x1, y1, x2, y2, "blue" );

        line.attribute("stroke-opacity", opacity);
    }

    SvgElement borderLines( Draw draw, Point start, Double width, Double height )
    {
        Integer lineSpacer = 3;
        Double borderPosition = SCALETHICKNESS - lineSpacer;

        SvgElement scale = draw.group(draw, "scale");

        SvgElement top = scale.lineAbsolute( draw,
                borderPosition + ExtraLeftSpace, borderPosition, width, borderPosition,
                Color );
        top.attribute( "stroke-width", "3" );

        SvgElement bottom = scale.lineAbsolute( draw,
                borderPosition + ExtraLeftSpace, height + lineSpacer, width, height + lineSpacer,
                Color );
        bottom.attribute( "stroke-width", "3" );

        SvgElement left = scale.lineAbsolute( draw,
                borderPosition + ExtraLeftSpace, borderPosition,
                borderPosition + ExtraLeftSpace, height,
                Color );
        left.attribute( "stroke-width", "3" );

        SvgElement right = scale.lineAbsolute( draw,
                width + lineSpacer, borderPosition, width + lineSpacer, height + lineSpacer,
                Color );
        right.attribute( "stroke-width", "3" );

       return scale;
    }


    void scaleLines( Draw draw, SvgElement scale, Point start, Double width, Double height )
    {
        Integer dashSpacer = 6;
        Double scalePosition = SCALETHICKNESS - dashSpacer;

        Double dashOffsetX = - ( Proscenium.Origin().x() + start.x() ) % 48;
        Double dashOffsetY = - ( Proscenium.Origin().y() + start.y() ) % 48;

        scale.dashedLine(draw,
                SCALETHICKNESS + ExtraLeftSpace, scalePosition, width, scalePosition,
                Color, dashOffsetX);

        scale.dashedLine(draw,
                SCALETHICKNESS + ExtraLeftSpace, height + dashSpacer, width, height + dashSpacer,
                Color, dashOffsetX);

        scale.dashedLine(draw,
                scalePosition + ExtraLeftSpace, SCALETHICKNESS,
                scalePosition + ExtraLeftSpace, height,
                Color, dashOffsetY );

        scale.dashedLine(draw,
                width + dashSpacer, SCALETHICKNESS, width + dashSpacer, height,
                Color, dashOffsetY );
    }

    void scaleNumbers( Draw draw, SvgElement scale,
                             Point start, Double width, Double height )
    {
        xAxisMeasurements(draw,
                start, width, 8.0, Color, SCALETHICKNESS, scale);

        xAxisMeasurements(draw,
                start, width, height + 16, Color, SCALETHICKNESS, scale);


        yAxisMeasurements(draw,
                start, height, 1.0, Color, SCALETHICKNESS, scale, "left");

        yAxisMeasurements(draw,
                start, height, width + 11, Color, SCALETHICKNESS, scale, "right");
    }

    void xAxisMeasurements(Draw draw,
                           Point start, Double width, Double y, String color,
                           Double thickness, SvgElement scale)
    {
        for( Double place = thickness + (Proscenium.Origin().x() + start.x()) % 48;
             place < width;
             place += 48 ) {
            Integer value = (place.intValue() - Proscenium.Origin().x().intValue()
                    - thickness.intValue() - start.x().intValue()) / 12;
            SvgElement number =
                    scale.textAbsolute( draw, value.toString(), place, y, color);
            number.attribute( "text-anchor", "middle" );
        }
    }

    void yAxisMeasurements(Draw draw,
                           Point start, Double height, Double x, String color,
                           Double thickness, SvgElement scale, String anchor)
    {
        int direction = 1;
        if ( Proscenium.Active() ) direction = -1;

        for( Double place = thickness + (Proscenium.Origin().y() + start.y()) % 48;
             place < height;
             place += 48 ) {
            Double sum = place - Proscenium.Origin().y() - thickness - start.y();
            Long value = Math.round( sum ) / 12 * direction;
            SvgElement number = scale.textAbsolute( draw, value.toString(), x, place, color);
            number.attribute( "dominant-baseline", "central" );
            number.attribute( "text-anchor", anchor );
        }
    }

}
