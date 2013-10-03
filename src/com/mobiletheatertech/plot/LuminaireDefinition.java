package com.mobiletheatertech.plot;

import org.w3c.dom.*;

import java.awt.*;
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
public class LuminaireDefinition extends Minder implements Legendable {

    private static ArrayList<LuminaireDefinition> LUMINAIRELIST = new ArrayList<>();

    //    private String id;
    private Boolean complete;
    private Element svg;
    private Integer width;
    private Integer length;
    private int legendHeight;

    /**
     * Extract the stage description element from a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing
     * @throws LocationException         If the stage is outside the {@code Venue}
     * @throws SizeException             If a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException, LocationException, SizeException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new LuminaireDefinition( element );
            }

        }
    }

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
            Legend.Register( this, 130, legendHeight );
        }
    }

    @Override
    public void verify() {
    }

    @Override
    public void drawPlan( Graphics2D canvas ) {
    }

    @Override
    public void drawSection( Graphics2D canvas ) {
    }

    @Override
    public void drawFront( Graphics2D canvas ) {
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

        Element defs = draw.element( "defs" );

        Element symbol = draw.element( "symbol" );
        symbol.setAttribute( "id", id );
        symbol.setAttribute( "overflow", "visible" );
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
        Element use = draw.element( "use" );
        use.setAttribute( "xlink:href", "#" + id );
        use.setAttribute( "x", start.x().toString() );
        use.setAttribute( "y", start.y().toString() );
        if (length > width) {
            use.setAttribute( "transform", "rotate(-90," + start.x() + "," + start.y() + ")" );
        }

        Element text = draw.element( "text" );
        Integer x = start.x() + 20;
        Integer y = start.y() + 3;
        text.setAttribute( "x", x.toString() );
        text.setAttribute( "y", y.toString() );
        text.setAttribute( "fill", "black" );
        text.setAttribute( "font-family", "serif" );
        text.setAttribute( "font-size", "10" );

        draw.appendRootChild( use );
        draw.appendRootChild( text );

        Text foo = draw.document().createTextNode( id );
        text.appendChild( foo );

        return new PagePoint( start.x(), start.y() + legendHeight );
    }
}
