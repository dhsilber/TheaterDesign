package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/14/13 Time: 12:42 PM To change this template use
 * File | Settings | File Templates.
 */
public class Chair extends MinderDom implements Legendable {

    static final String CATEGORY = "chair";

    private static final Integer CHAIRWIDTH = 18;
    private static final Integer CHAIRDEPTH = 19;
    private static final String COLOR = "black";
    private static final String CHAIR = "chair";
    public static final String LAYERTAG = "chair";
    public static final String LAYERNAME = "Chairs";

    static boolean SYMBOLGENERATED = false;
    static boolean LEGENDREGISTERED = false;
    static Integer COUNT = 0;

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

//        layerName = ("".equals( layerName )) ? LAYERTAG : layerName;
        if ( ! "".equals( layerName )) {
            Layer layerActual = Layer.Retrieve( layerName );
            layerActual.register( this );
        }

//        new Layer( LAYERTAG, LAYERNAME, COLOR );
//
//        new Category( CATEGORY, this.getClass() );
//        new Category( CATEGORY, this.getClass(), LAYERTAG );

        if ( ! LEGENDREGISTERED ) {
            Legend.Register(this, 2, 7, LegendOrder.Furniture);
            LEGENDREGISTERED = true;
        }
    }

    static int Count() {
        return COUNT;
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {
    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        generateSymbol(draw);

        SvgElement group = svgClassGroup( draw, layerName );
        draw.appendRootChild(group);
        useChair(draw, group, x, y);

        Double interimX = x;
        Double interimY = y;
        for ( int chairCount = 1; chairCount < line; chairCount++ ) {
            interimX += Math.cos(Math.toRadians(orientation)) * CHAIRWIDTH;
            interimY += Math.sin( Math.toRadians( orientation ) ) * CHAIRWIDTH;
            useChair(draw, group, interimX, interimY);
        }
        COUNT += (0 == line) ? 1: line;
    }

    void generateSymbol(Draw draw) {
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

    void useChair(Draw draw, SvgElement parent, Double interimX, Double interimY) {
        SvgElement use = parent.use( draw, CHAIR, interimX, interimY );
        use.attribute( "transform", "rotate("+orientation+","+(interimX+SvgElement.OffsetX())+","+(interimY+SvgElement.OffsetY())+")" );
    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        generateSymbol(draw);

        SvgElement group = svgClassGroup( draw, layerName );
        group.attribute( "transform", "translate(" + start.x() + "," + start.y() + ")" );
        draw.appendRootChild(group);

//        group.rectangle( draw, 0.0, 0.0, 300.0, 14.0, "blue" );

        // The offsets are added in to compensate for the shift that happens when the chair
        // icon is scaled to half size.
        SvgElement use = group.use(draw, CHAIR,
                CHAIRWIDTH / 2.0 + SvgElement.OffsetX(), CHAIRDEPTH / 2.0 + SvgElement.OffsetY());
        use.attribute( "transform", "scale(0.5)" );

        group.text(draw, CHAIR, Legend.TEXTOFFSET, 7.0, Legend.TEXTCOLOR);

        group.text( draw, COUNT.toString(), Legend.QUANTITYOFFSET, 7.0, Legend.TEXTCOLOR );

        return new PagePoint( start.x(), start.y() + 7.0 );
    }
}
