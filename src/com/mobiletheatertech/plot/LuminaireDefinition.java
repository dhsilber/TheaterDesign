package com.mobiletheatertech.plot;

import org.w3c.dom.*;

import java.util.ArrayList;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 7/18/13 Time: 1:53 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Definition of type of lighting instrument or part of one that is used as a building block for the
 * drawing.
 * <p/>
 * XML tag is 'luminaire-definition'. Required attribute is 'name', which is used later when an
 * instance of a given type of luminaire is created.
 * <p/>
 * Optional attributes are: <dl> <dt>svg</dt><dd>provides the SVG code to draw an instance of this
 * type of luminaire</dd> <dt>complete</dt><dd>which differentiates a definition of a building block
 * from a definition of a complete luminaire</dd> <dt>width</dt><dd>the space the luminaire drawing
 * needs perpendicular to its beam of light</dd> <dt>length</dt><dd>space the luminaire drawing
 * needs in the direction of beam of light</dd> </dl>
 * <p/>
 * Complete definitions are added to the legend.
 *
 * @author dhs
 * @since 0.0.7
 */
public class LuminaireDefinition extends MinderDom implements Legendable {

    private static ArrayList<LuminaireDefinition> LUMINAIRELIST = new ArrayList<>();

    //    private String id;
    private Boolean complete;
    private Element svg;
    private Integer width;
    private Integer length;
    private int legendHeight;

    private static final String COLOR = "black";

    /**
     * Find a specific {@code LuminaireDefinition} from all that have been constructed.
     *
     * @param id of {@code LuminaireDefinition} to find
     * @return {@code LuminaireDefinition}, or {@code null} if not found
     */
    public static LuminaireDefinition Select( String id ) {
        for (LuminaireDefinition selection : LUMINAIRELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        return null;
    }

    /**
     * Construct a {@code LuminaireDefinition} from an XML element.
     * <p/>
     * Keep a list of types of luminaires.
     * <p/>
     * Complete definitions are added to the legend.
     *
     * @param element DOM Element defining a pipe
     * @throws AttributeMissingException if any attribute is missing
     */
    public LuminaireDefinition( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = element.getAttribute( "name" );
        if (id.isEmpty()) {
            throw new AttributeMissingException( "Luminaire definition", null, "name" );
        }
        complete = (element.getAttribute( "complete" ).equals( "1" ));
        width = getOptionalIntegerAttribute( element, "width" );
        length = getOptionalIntegerAttribute( element, "length" );

        NodeList svgList = element.getElementsByTagName( "svg" );
        if (null != svgList && svgList.getLength() > 0) {
            Node svgNode = svgList.item( 0 );
            svg = (Element) svgNode;
        }

        LUMINAIRELIST.add( this );

        legendHeight = 2 + ((width > length)
                            ? length
                            : width);
        if (complete) {
            Legend.Register( this, 130, legendHeight, LegendOrder.Luminaire );
        }
    }

    public Integer height() {
        return legendHeight;
    }

    public Integer width() {
        return   ((width < length)
                  ? length
                  : width);
    }

    @Override
    public void verify() {
    }

    /**
     * Generate SVG DOM for the symbol definition used for each individual luminaire.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode - ignored
     */
    @Override
    public void dom( Draw draw, View mode ) {
        Document document = draw.document();

        SvgElement defs = draw.element("defs");

        SvgElement symbol = defs.symbol( draw, id );
//        draw.element("symbol");
//        symbol.setAttribute( "id", id );
//        symbol.setAttribute( "overflow", "visible" );
        defs.appendChild( symbol );

        Node svgNode = document.importNode( svg, true );
        symbol.appendChild( svgNode );

        draw.insertRootChild( defs );
    }

    /**
     * Callback used by {@code Legend} to allow this object to generate the information it needs to
     * put into the legend area.
     * <p/>
     * {@code LuminaireDefinition} puts out a 'use' element to draw its icon and the name of the
     * type of luminaire.
     *
     * @param draw  Canvas/DOM manager
     * @param start position on the canvas for this legend entry
     * @return start point for next {@code Legend} item
     */
    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        SvgElement use = draw.use( draw, id, start.x(), start.y() );
//        draw.element("use");
//        use.setAttribute( "xlink:href", "#" + id );
//        use.setAttribute( "x", start.x().toString() );
//        use.setAttribute( "y", start.y().toString() );
        if (length > width) {
            Double transformX = start.x() + SvgElement.OffsetX();
            Double transformY = start.y() + SvgElement.OffsetY();
            use.attribute( "transform", "rotate(-90," + transformX + "," + transformY + ")" );
        }

        Double x = start.x() + Legend.TEXTOFFSET;
        Double y = start.y() + 3;
        SvgElement text = draw.text( draw, id, x, y, COLOR );
//        draw.element("text");
//        text.setAttribute( "x", x.toString() );
//        text.setAttribute( "y", y.toString() );
//        text.setAttribute( "fill", "black" );
//        text.setAttribute( "stroke", "none" );
//        text.setAttribute( "font-family", "serif" );
//        text.setAttribute( "font-size", "10" );

//        draw.appendRootChild( use );
//        draw.appendRootChild( text );

//        Text foo = draw.document().createTextNode( id );
//        text.appendChild( foo );

        return new PagePoint( start.x(), start.y() + legendHeight );
    }
}
