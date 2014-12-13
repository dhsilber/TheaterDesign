package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 12/12/14.
 */
public class LightingStand extends Mountable implements Legendable {

    private static Integer Count = 0;
    static final String NAME = "LightingStand";
    static final String TAG = "lighting-stand";
    static boolean SYMBOLGENERATED = false;

    Double x;
    Double y;
    Double orientation;

    public LightingStand( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        x = getDoubleAttribute(element, "x");
        y = getDoubleAttribute(element, "y");
        orientation = getOptionalDoubleAttributeOrZero( element, "orientation" );
    }

    @Override
    public void verify() {

    }

    @Override
    public Point location( String location ) throws InvalidXMLException {
        int offset = 0;

        switch ( location ) {
            case "a":
                offset = -18;
                break;
            case "b":
                offset = -6;
                break;
            case "c":
                offset = 6;
                break;
            case "d":
                offset = 18;
                break;
            default:
                throw new InvalidXMLException("LightingStand (" + id + ") location must specify a letter in the range of 'a' to 'd'.");
        }
        return new Point( x + offset, y, 144.0 );
    }

    @Override
    public Place rotatedLocation( String location ) throws InvalidXMLException {
        return new Place( location(location),
                new Point( x + SvgElement.OffsetX(), y + SvgElement.OffsetY(), 144.0 ),
                orientation );
    }

    @Override
    public void dom( Draw draw, View mode ) {
        generateSymbol(draw);

        SvgElement group = svgClassGroup( draw, TAG );
        draw.appendRootChild(group);

        SvgElement use = group.use( draw, TAG, x, y );
        use.attribute( "transform",
                "rotate("+orientation+","+(x+SvgElement.OffsetX())+","+(y+SvgElement.OffsetY())+")" );

        Count++;
    }

    private void generateSymbol(Draw draw) {
        if (!SYMBOLGENERATED) {
            SvgElement defs = draw.element("defs");
            draw.appendRootChild(defs);

            SvgElement symbol = defs.symbol( draw, TAG );
            defs.appendChild(symbol);

            String color="black";

            symbol.rectangle( draw, -1.0, -1.0, 2.0, 12.0, color );

            SvgElement sw = symbol.rectangle( draw, -11.0, -1.0, 12.0, 2.0, color );
            sw.attribute("transform", "rotate(30.0,0.0,0.0)" );

            SvgElement se = symbol.rectangle( draw, -1.0, -1.0, 12.0, 2.0, color );
            se.attribute("transform", "rotate(-30.0,0.0,0.0)" );

            SvgElement bar = symbol.rectangle( draw, -24.0, -1.0, 48.0, 2.0, color );
            bar.attribute( "fill", "white" );

            SYMBOLGENERATED = true;
        }
    }

    @Override
    public void countReset() {

    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        return null;
    }

}
