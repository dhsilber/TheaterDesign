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

    // TODO Keep this until I resolve how Luminaire knows what type it is.
    private static ArrayList<LuminaireDefinition> LUMINAIRELIST = new ArrayList<>();

    Integer count = 0;

    //    private String id;
    private Boolean complete;
    private Element svg;
    private Double width;
    private Double length;
    private Double legendHeight;

    private static final String COLOR = "black";

    /**
     * Find a specific {@code LuminaireDefinition} from all that have been constructed.
     *
     * @param id of {@code LuminaireDefinition} to find
     * @return {@code LuminaireDefinition}, or {@code null} if not found
     */
    // TODO Keep this until I resolve how Luminaire knows what type it is.
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
    public LuminaireDefinition( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        id = element.getAttribute( "name" );
        if (id.isEmpty()) {
            throw new AttributeMissingException( "Luminaire definition", null, "name" );
        }
        complete = (element.getAttribute( "complete" ).equals( "1" ));
        width = getOptionalDoubleAttributeOrZero(element, "width");
        length = getOptionalDoubleAttributeOrZero(element, "length");

        NodeList svgList = element.getElementsByTagName( "svg" );
        if (null != svgList && svgList.getLength() > 0) {
            Node svgNode = svgList.item( 0 );
            svg = (Element) svgNode;
        }
        else {
            throw new InvalidXMLException( this.getClass().getSimpleName(), id,
                    "svg element is required");
        }

        LUMINAIRELIST.add( this );

        legendHeight = 2 + ((width > length)
                            ? length
                            : width);
        if (complete) {
            Legend.Register( this, 130.0, legendHeight, LegendOrder.Luminaire );
        }
    }

    public Double length() {
        return length;
    }

    public Double width() {
        return width;
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
        defs.appendChild( symbol );

        Node svgNode = document.importNode( svg, true );
        symbol.appendChild( svgNode );

        draw.insertRootChild( defs );
    }

    public void count() {
        count++;
    }

    public static void CountReset()  {
        for (LuminaireDefinition selection : LUMINAIRELIST) {
            selection.countReset();
        }
    }

    @Override
    public void countReset() {
        count = 0;
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
        if ( 0 >= count ) { return start; }

        SvgElement group = svgClassGroup( draw , "" );
        group.attribute( "transform", "translate(" + start.x() + "," + start.y() + ")" );
        draw.appendRootChild(group);

        SvgElement use = group.useAbsolute( draw, id, 0.0, 0.0 );

        if (length > width) {
            use.attribute( "transform", "rotate(-90,0,0)" );
        }

        group.textAbsolute( draw, id, Legend.TEXTOFFSET, 3.0, COLOR );

        group.textAbsolute( draw, count.toString(), Legend.QUANTITYOFFSET, 7.0, Legend.TEXTCOLOR );

        return new PagePoint( start.x(), start.y() + legendHeight );
    }
}
