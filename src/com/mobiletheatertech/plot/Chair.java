package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */
public class Chair extends MinderDom {

    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final String COLOR = "black";
    private static final String CHAIR = "chair";
    public static final String LAYERTAG = "chair";

    private static boolean SYMBOLGENERATED = false;

    Double x = null;
    Double y = null;
    Double orientation = null;

    public Chair(Element element) throws AttributeMissingException, InvalidXMLException {
        super(element);

//        String exceptionMessage =
//                "Chairblock can be defined with either a perimeter or a complete set of x/y/width/depth parameters, but not both.";

        x = getDoubleAttribute(element, "x");
        y = getDoubleAttribute(element, "y");
        orientation = getOptionalDoubleAttributeOrZero( element, "orientation" );
//        width = getOptionalDoubleAttribute(element, "width");
//        depth = getOptionalIntegerAttribute(element, "depth");

    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {

    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
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

        SvgElement group = svgClassGroup( draw, LAYERTAG );
        draw.appendRootChild(group);
        SvgElement use = group.use( draw, CHAIR, x, y );
        use.attribute( "transform", "rotate("+orientation+","+(x+SvgElement.OffsetX())+","+(y+SvgElement.OffsetY())+")" );

    }
}
