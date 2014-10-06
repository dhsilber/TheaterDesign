package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.awt.*;

/**
 * <p> Hard hang point built into the ceiling of the venue. </p><p> XML tag is 'hangpoint'. There
 * are no children. Required attributes are 'x' and 'y', which define its location relative to the
 * venue. The z-coordinate is derived from the height of the venue. Optional attribute is 'id',
 * which becomes necessary if a hangpoint is to be referenced. </p>
 *
 * @author dhs
 * @since 0.0.4
 */
public class HangPoint extends MinderDom implements Legendable {

    static final String SYMBOL = "hangpoint";

    /**
     * Name of {@code Layer} of {@code HangPoint}s.
     */
    public static final String LAYERNAME = "Hangpoints";

    /**
     * Name of {@code Layer} of {@code HangPoint}s.
     */
    public static final String LAYERTAG = SYMBOL;

    // This is public to allow for a terrible, terrible hack in MinderDom.DomAllTruss().
    public static Boolean SYMBOLGENERATED = false;

    private Integer x = null;
    private Integer y = null;

    static final String CATEGORY = SYMBOL;

    private static final String COLOR = "light blue";

    static final Integer RADIUS = 6;

    private static Boolean Legended = false;

    /**
     * Create a {@code HangPoint}
     * <p/>
     * It must be within the bounds of the {@link Venue}.
     *
     * @param element describes this {@code HangPoint}.
     * @throws AttributeMissingException if some required attribute is missing.
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     * @throws LocationException         if some coordinate is outside the area of the {@link
     *                                   Venue}.
     */
    HangPoint( Element element )
            throws AttributeMissingException, DataException,
            InvalidXMLException, LocationException, ReferenceException
    {
        super( element );

        id = getOptionalStringAttribute( element, "id" );
        x = getIntegerAttribute( element, "x" );
        y = getIntegerAttribute( element, "y" );

        int outcode = Venue.Contains2D( x, y );
        switch (outcode) {
            case Rectangle.OUT_LEFT:
            case Rectangle.OUT_RIGHT:
                throw new LocationException(
                        "HangPoint x value outside boundary of the venue" );
            case Rectangle.OUT_TOP:
            case Rectangle.OUT_BOTTOM:
                throw new LocationException(
                        "HangPoint y value outside boundary of the venue" );
        }

        new Layer( LAYERTAG, LAYERNAME );

        new Category( CATEGORY, this.getClass() );

        if( ! Legended ) {
            Legend.Register( this, 130, 7, LegendOrder.Room );
            Legended = true;
        }
    }

    /**
     * Find a specific {@code HangPoint} in the set of plot objects.
     *
     * @param id Name of {@code HangPoint} to find
     * @return {@code HangPoint} which matches specified {@code id}.
     */
    public static HangPoint Find( String id ) {

        for (ElementalLister thingy : ElementalLister.List()) {
            if (HangPoint.class.isInstance( thingy )) {
                if (((HangPoint) thingy).id.equals( id )) {
                    return (HangPoint) thingy;
                }
            }
        }
        return null;
    }

    public static void Draw( Draw draw, Integer x, Integer y, Integer textX, Integer textY, String id ) {
//System.out.println( "HangPoint.Draw( "+x.toString()+", "+ y.toString()+", "+ textX.toString()+", "+ textY.toString()+", "+ id+" )" );

        SvgElement group = svgClassGroup(draw, LAYERTAG);
//        draw.element("g");
//        group.setAttribute( "class", LAYERTAG );
        draw.appendRootChild( group );

        String reference = SYMBOL;
        SvgElement use = group.use( draw, reference, x, y );
//        draw.element("use");
//        use.setAttribute( "xlink:href", "#hangpoint" );
//        use.setAttribute( "x", x.toString() );
//        use.setAttribute( "y", y.toString() );
//        group.appendChild( use );

        SvgElement idText = group.text( draw, id, textX, textY, COLOR );
//        draw.element("text");
//        idText.setAttribute( "x", textX.toString() );
//        idText.setAttribute( "y", textY.toString() );
//        idText.setAttribute( "fill", color );
        idText.attribute( "stroke", "none" );
        idText.attribute("font-family", "sans-serif");
        idText.attribute("font-weight", "100");
        idText.attribute("font-size", "12pt");
        idText.attribute("text-anchor", "left");
//        group.appendChild( idText );

//        Text textId = draw.document().createTextNode( id );
//        idText.appendChild( textId );
    }

    /**
     * Provide the location of this {@code HangPoint}.
     *
     * @return coordinate of this {@code HangPoint}'s location
     */
    public Point locate() throws ReferenceException {
//        System.out.println( "Hangpoint (" + id + ") locate(): " + Venue.Height() );
        return new Point( x, y, Venue.Height() );

    }

    @Override
    public void verify() throws InvalidXMLException {
    }

    /**
     * Draw a {@code HangPoint} onto the provided plan canvas.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        if (( View.PLAN == mode || View.TRUSS == mode ) && !SYMBOLGENERATED) {

            SvgElement defs = draw.element("defs");
            draw.appendRootChild( defs );

            SvgElement symbol = defs.symbol( draw, SYMBOL );
////            draw.element("symbol");
////            symbol.setAttribute( "id", "hangpoint" );
////            symbol.setAttribute( "overflow", "visible" );
//            defs.appendChild( symbol );

            SvgElement circle = symbol.circle( draw, 0, 0, 4, COLOR );
////            draw.element("circle");
////            circle.setAttribute( "fill", "none" );
////            circle.setAttribute( "stroke", color );
////            circle.setAttribute( "stroke-width", "1" );
////            circle.setAttribute( "cx", "0" );
////            circle.setAttribute( "cy", "0" );
////            circle.setAttribute( "r", "4" );
//            symbol.appendChild( circle );

            SvgElement line = symbol.line( draw, 0, -RADIUS, 0, RADIUS, COLOR );
//                    draw.element("line");
//            line.setAttribute( "x1", "0" );
//            line.setAttribute( "y1", "-6" );
//            line.setAttribute( "x2", "0" );
//            line.setAttribute( "y2", "6" );
//            line.setAttribute( "stroke", color );
//            line.setAttribute( "stroke-width", "1" );
//            symbol.appendChild( line );

            SvgElement line2 = symbol.line(draw, -RADIUS, 0, RADIUS, 0, COLOR);
//            draw.element("line");
//            line2.setAttribute( "x1", "-6" );
//            line2.setAttribute( "y1", "0" );
//            line2.setAttribute( "x2", "6" );
//            line2.setAttribute( "y2", "0" );
//            line2.setAttribute( "stroke", color );
//            line2.setAttribute( "stroke-width", "1" );
//            symbol.appendChild( line2 );

            SYMBOLGENERATED = true;
        }

        if (View.PLAN != mode) {
            return;
        }

        Integer unitTextX = x + RADIUS * 2;
        Integer unitTextY = y + RADIUS;

        Draw( draw, x, y, unitTextX, unitTextY, id );
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        Integer unitTextX = x + RADIUS * 2;
        Integer unitTextY = y + RADIUS;

//        Draw( draw, x, y, unitTextX, unitTextY, id );

        SvgElement use = draw.use( draw, SYMBOL, start.x()+RADIUS, start.y()+RADIUS );
//        draw.appendRootChild( use );
//        SvgElement circle = svgCircle( draw, start.x()+RADIUS, start.y()+RADIUS, 4, COLOR );
//        draw.appendRootChild( circle );
//
//        SvgElement line =svgLine( draw, start.x()+RADIUS, start.y(), start.x()+RADIUS, start.y()+RADIUS*2, COLOR );
//        draw.appendRootChild( line );
//
//        SvgElement line2 = svgLine(draw, start.x(), start.y()+RADIUS, start.x()+RADIUS*2, start.y()+RADIUS, COLOR);
//        draw.appendRootChild( line2 );

        Integer x = start.x() + Legend.TEXTOFFSET;
        Integer y = start.y() + RADIUS * 3 / 2;

        SvgElement text = draw.text( draw, "Hangpoint", x, y, COLOR );
//        draw.appendRootChild( text );

        return new PagePoint( start.x(), start.y() + RADIUS * 2 );
    }
}
