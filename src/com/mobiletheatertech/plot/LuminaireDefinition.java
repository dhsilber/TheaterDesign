package com.mobiletheatertech.plot;

import org.w3c.dom.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 7/18/13 Time: 1:53 PM To change this template use
 * File | Settings | File Templates.
 */

/**
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
     * @throws AttributeMissingException If a required attribute is missing.
     * @throws LocationException         If the stage is outside the {@code Venue}.
     * @throws SizeException             If a length attribute is too short.
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, LocationException, SizeException
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

    public static LuminaireDefinition Select( String id ) {
        for (LuminaireDefinition selection : LUMINAIRELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        return null;
    }

    public LuminaireDefinition( Element element ) throws AttributeMissingException {
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
            System.out.println( "Registering for " + id );
        }
    }

    @Override
    public void verify() throws InvalidXMLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void drawPlan( Graphics2D canvas ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void drawSection( Graphics2D canvas ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void drawFront( Graphics2D canvas ) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dom( Draw draw, View mode ) {
        Document document = draw.document();

        Element defs = draw.element( "defs" );

        Element symbol = draw.element( "symbol" );
        symbol.setAttribute( "id", id );
        symbol.setAttribute( "overflow", "visible" );
        defs.appendChild( symbol );

//        symbol.appendChild( svg );
        Node newNode = document.importNode( svg, true );
        symbol.appendChild( newNode );

        draw.insertRootChild( defs );
    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        System.out.println( "DOM for " + id );

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
