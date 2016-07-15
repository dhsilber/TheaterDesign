package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Represents a group of chairs.
 * <p/>
 * XML tag is 'chair'. Required attributes are: 'x' and 'y', which specify the location of a single chair as real numbers of inches from the origin.
 * An optional 'orientation' attribute may be specified, which is an angle in degrees by which the chair should be rotated from its default of facing up on the page.
 * In the case of a circle with an opening, 'orientation' indicates the direction that opening faces. <- - Not yet implemented
 * An optional 'line' attribute may be specified to set an integer count of chairs that will be set out instead of just a single one. In this case, the coordinates will be for the leftmost chair in the line.
 * An optional 'r' attribute may be specified to set the radius as a real number of inches of a circle of chairs. The coordinates are for the center of the circle. The number of chairs put out is calculated by the software.
 * An optional 'space' attribute may be specified to allow extra space as a real number of inches between the chairs in a circle.
 * An optional 'layer' attribute may be specified as the name of an existing {@code Layer} to orverride the default layer.
 * An optional 'count' attribute sets the number of chairs to be drawn in a circle. In this case a radius is ignored.
 * An optional 'opening' attribute sets the percentage of a circle of chairs that is left unfilled. <- - Not yet implemented
 */
// Note that 'space' only applies to circles. It ought to be trivial to make it also apply to lines.
public class Chair extends MinderDom implements Legendable {

    static final String CATEGORY = "chair";

    static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final String COLOR = "black";
    static final String CHAIR = "chair";
    public static final String LAYERTAG = "Chair";
    public static final String LAYERNAME = "Chair";

    static boolean SYMBOLGENERATED = false;
    static boolean LEGENDREGISTERED = false;
    static Integer CHAIRCOUNT = 0;

    Double x = null;
    Double y = null;
    Double radius = null;
    Double orientation = null;
    Integer line = null;
    String layerName = null;
    Double space = null;
    Double opening = null;
    Integer count = null;

    Double circumference = null;
    Integer chairFit = null;
    Double chairWidth = CHAIRWIDTH.doubleValue();
    Double angle = null;
    Double section = 0.0;

    public Chair(Element element) throws AttributeMissingException, DataException, InvalidXMLException {
        super(element);

        x = getDoubleAttribute(element, "x");
        y = getDoubleAttribute(element, "y");
        radius = getOptionalDoubleAttributeOrNull( element, "r" );
        space = getOptionalDoubleAttributeOrZero( element, "space" );
        orientation = getOptionalDoubleAttributeOrZero( element, "orientation" );
        line = getOptionalIntegerAttributeOrZero( element, "line" );
        layerName = getOptionalStringAttribute( element, "layer" );
        count = getOptionalIntegerAttributeOrNull(element, "count");
        opening = getOptionalDoubleAttributeOrNull(element, "opening");

        layerName = ("".equals( layerName )) ? LAYERTAG : layerName;
        if ( ! "".equals( layerName )) {
            Layer layerActual = Layer.Retrieve( layerName );
            layerActual.register( this );
        }

        chairWidth += space;

//        new Layer( LAYERTAG, LAYERNAME, COLOR );
//
//        new Category( CATEGORY, this.getClass() );
//        new Category( CATEGORY, this.getClass(), LAYERTAG );

        if ( ! LEGENDREGISTERED ) {
            Legend.Register(this, 2.0, 7.0, LegendOrder.Furniture);
            LEGENDREGISTERED = true;
        }
    }

    static int Count() {
        return CHAIRCOUNT;
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException,
            MountingException, ReferenceException {
        circumference = null;

        if( null != radius ) {
            circumference = 2 * Math.PI * radius;
            chairFit = (int) (circumference / chairWidth);
            angle = 360.0 / chairFit;
        }

        if (null != count ) {
            circumference = chairWidth * count;
            chairFit = count;
            angle = 360.0 / chairFit;
            if( null != opening) {
                section = 100 - opening;
                circumference += circumference * opening / section;
                angle = 360.0 / (circumference / chairWidth);
            }
            radius =  circumference / (2 * Math.PI);
        }
    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        generateSymbol(draw);

        SvgElement group = svgClassGroup( draw, layerName );
        draw.appendRootChild(group);

        Double interimX = x;
        Double interimY = y;
        if ( ( null != radius ) || ( null != count ) ) {

            for ( int chairCount = 0; chairCount < chairFit; chairCount++ ) {
                Double thisAngle = angle * chairCount - section / 2;
                interimX = x - radius * Math.sin(Math.toRadians(thisAngle));
                interimY = y + radius * Math.cos(Math.toRadians(thisAngle));
                useChair(draw, group, interimX, interimY, thisAngle );
            }
//            CHAIRCOUNT += chairFit;
        }
        else {
            useChair(draw, group, x, y, orientation );
            for (int chairCount = 1; chairCount < line; chairCount++) {
                interimX += Math.cos(Math.toRadians(orientation)) * CHAIRWIDTH;
                interimY += Math.sin(Math.toRadians(orientation)) * CHAIRWIDTH;
                useChair(draw, group, interimX, interimY, orientation );
            }
//            CHAIRCOUNT += (0 == line) ? 1: line;
        }
    }

    private void generateSymbol(Draw draw) {
        if (!SYMBOLGENERATED) {
            SvgElement defs = draw.element("defs");
            draw.appendRootChild(defs);

            SvgElement symbol = defs.symbol( draw, CHAIR );
            defs.appendChild(symbol);

            int sideways = CHAIRWIDTH / 3;
            int forward = CHAIRDEPTH / 2;
            String path =                     "M -" + sideways + " -" + forward +
                    " L -" + sideways + " " + forward +
                    " L " + sideways + " " + forward +
                    " L " + sideways + " -" + forward;

        /*SvgElement perimeter =*/ symbol.path( draw, path, COLOR );

            SYMBOLGENERATED = true;
        }
    }

    void useChair(Draw draw, SvgElement parent, Double interimX, Double interimY, Double rotation ) {
        SvgElement use = parent.use( draw, CHAIR, interimX, interimY );
        use.attribute( "transform", "rotate("+rotation+","+(interimX+SvgElement.OffsetX())+","+(interimY+SvgElement.OffsetY())+")" );

        CHAIRCOUNT++;
    }

    @Override
    public void countReset() {
        CHAIRCOUNT = 0;
    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        if ( 0 >= CHAIRCOUNT) { return start; }

        generateSymbol(draw);

        SvgElement group = svgClassGroup( draw, layerName );
        group.attribute( "transform", "translate(" + start.x() + "," + start.y() + ")" );
        draw.appendRootChild(group);

        SvgElement use = group.useAbsolute(draw, CHAIR, CHAIRWIDTH / 2.0, CHAIRDEPTH / 2.0  );
        use.attribute( "transform", "scale(0.5)" );

        group.textAbsolute(draw, CHAIR, Legend.TEXTOFFSET, 7.0, Legend.TEXTCOLOR);

        group.textAbsolute(draw, CHAIRCOUNT.toString(), Legend.QUANTITYOFFSET, 7.0, Legend.TEXTCOLOR);

        return new PagePoint( start.x(), start.y() + 7.0 );
    }

}
