package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */
public class Chair extends MinderDom {

    static final String CATEGORY = "chair";

    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final String COLOR = "black";
    private static final String CHAIR = "chair";
    public static final String LAYERTAG = "chair";
    public static final String LAYERNAME = "Chairs";

    static boolean SYMBOLGENERATED = false;

    Double x = null;
    Double y = null;
    Double orientation = null;
    Integer line = null;
    String layerName = null;

    public Chair(Element element) throws AttributeMissingException, DataException, InvalidXMLException {
        super(element);

        x = getDoubleAttribute(element, "x");
        y = getDoubleAttribute(element, "y");
        orientation = getOptionalDoubleAttributeOrZero( element, "orientation" );
        line = getOptionalIntegerAttributeOrZero( element, "line" );
        layerName = getOptionalStringAttribute( element, "layer" );

        new Layer( LAYERTAG, LAYERNAME, COLOR );

        new Category( CATEGORY, this.getClass() );
//        new Category( CATEGORY, this.getClass(), LAYERTAG );
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {

    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        if (!SYMBOLGENERATED) {
            SvgElement defs = draw.element("defs");
            draw.appendRootChild(defs);

            layerName = ("".equals( layerName )) ? LAYERTAG : layerName;

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

        SvgElement group = svgClassGroup( draw, layerName );
        draw.appendRootChild(group);
        useChair(draw, group, x, y);

        Double interimX = x;
        Double interimY = y;
        for ( int count = 1; count < line; count++ ) {
            interimX += Math.cos(Math.toRadians(orientation)) * CHAIRWIDTH;
            interimY += Math.sin( Math.toRadians( orientation ) ) * CHAIRWIDTH;
            useChair(draw, group, interimX, interimY);
        }
    }

    void useChair(Draw draw, SvgElement parent, Double interimX, Double interimY) {
        SvgElement use;
        use = parent.use( draw, CHAIR, interimX, interimY );
        use.attribute( "transform", "rotate("+orientation+","+(interimX+SvgElement.OffsetX())+","+(interimY+SvgElement.OffsetY())+")" );
    }
}
