package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.net.www.content.audio.x_aiff;

import java.awt.*;

/**
* Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:05 PM To change this template use
* File | Settings | File Templates.
 *
 * This is a square base for truss.
*
* @author dhs
* @since 0.0.5
*/
public class Base extends MinderDom {

    private Double x;
    private Double y;
    private Double size;
    private Double rotation;

    private String color = "taupe";

    private String processedMark = null;


    public Base( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        size = getDoubleAttribute( element, "size" );
        x = getDoubleAttribute( element, "x" );
        y = getDoubleAttribute( element, "y" );
        rotation = getOptionalDoubleAttributeOrZero( element, "rotation" );

        // Give the base's element a unique id so that its Plot object can be recognized
        // and used.
        processedMark = Mark.Generate();
        element.setAttribute( "processedMark", processedMark );
    }

    /**
     * Find a {@code Base}
     *
     * @param mark string to match while searching for a {@code Base}
     * @return {@code Base} whose mark matches specified string
     */
    // Copied from Truss - refactor to Minder?
    public static Base Find( String mark ) {
        for (ElementalLister thingy : ElementalLister.List()) {
            if (Base.class.isInstance( thingy )) {
                if (((Base) thingy).processedMark.equals( mark )) {
                    return (Base) thingy;
                }
            }
        }
        return null;
    }

//    public Point locate() {
//        return new Point( x, y, 0 );
//    }

    Double x() {
        return x;
    }

    Double y() {
        return y;
    }

    Double size() {
        return size;
    }

    Double rotation() {
        return rotation;
    }

    @Override
    public void verify() throws InvalidXMLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void dom( Draw draw, View mode ) {
        switch (mode) {
            case PLAN:
SvgElement group = svgClassGroup( draw, Truss.LAYERTAG );
                draw.appendRootChild( group );
                SvgElement base = group.rectangle( draw, x - size / 2, y - size / 2, size, size, color );

                Double transformX = x + SvgElement.OffsetX();
                Double transformY = y + SvgElement.OffsetY();
                String transform = "rotate(" + rotation + "," + transformX + "," + transformY + ")";
                base.attribute( "transform", transform );
        }
    }
}
