package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 12/12/14.
 */
public class LightingStand extends Mountable implements Legendable {

    static Integer Count = 0;
    static final String NAME = "LightingStand";
    static final String TAG = "lighting-stand";
    static boolean SYMBOLGENERATED = false;

    Double x;
    Double y;
    Double orientation;
    static Double SchematicX = 100.0;
    static Double SchematicY = 100.0;
    static Double SchematicWidth = 48.0;
    static Double SchematicHeight = 24.0;
    static Double TextHeight = 12.0;

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
    public void dom( Draw draw, View view ) {
        SvgElement group;
        SvgElement use;
        switch( view ) {
            case PLAN:
                generatePlanSymbol( draw );

                group = svgClassGroup( draw, TAG );
                draw.appendRootChild(group);

                use = group.use( draw, TAG, x, y );
                use.attribute("transform",
                        "rotate(" + orientation + "," + (x + SvgElement.OffsetX()) + "," + (y + SvgElement.OffsetY()) + ")");

                break;
            case SCHEMATIC:
                generateSchematicSymbol( draw );

                group = svgClassGroup( draw, TAG );
                draw.appendRootChild(group);

                Double x = SchematicX * ((Count + 1) * 2) - SchematicX;
                group.useAbsolute( draw, TAG, x, SchematicY );
                group.textAbsolute( draw, id, x - SchematicWidth / 2, SchematicY + SchematicHeight + TextHeight, "black" );

                break;
            default:
                return;
        }

        Count++;
    }

    private void generatePlanSymbol(Draw draw) {
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

    private void generateSchematicSymbol(Draw draw) {
        if (!SYMBOLGENERATED) {
            SvgElement defs = draw.element("defs");
            draw.appendRootChild(defs);

            SvgElement symbol = defs.symbol( draw, TAG );
            defs.appendChild(symbol);

            String color="black";

            symbol.rectangle( draw, -1.0, -1.0, 2.0, 24.0, color );

            SvgElement bar = symbol.rectangle( draw, -24.0, -1.0, 48.0, 2.0, color );
            bar.attribute( "fill", "white" );

            SYMBOLGENERATED = true;
        }
    }

//    @Override
    public void countReset() {

    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        return null;
    }

}
