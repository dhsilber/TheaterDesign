package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
* Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:05 PM To change this template use
* File | Settings | File Templates.
 *
 * This is a square base for truss.
*
* @author dhs
* @since 0.0.5
*/
public class TrussBase extends MinderDom {

    private Double x;
    private Double y;
    private Double size;
    private Double rotation;

    private String color = "taupe";

    private String processedMark = null;


    public TrussBase(Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        size = getDoubleAttribute( "size" );
        y = getDoubleAttribute( "y" );
        x = getDoubleAttribute( "x" );
        rotation = getOptionalDoubleAttributeOrZero( "rotation" );

        // Give the base's element a unique id so that its Plot object can be recognized
        // and used.
        processedMark = Mark.Generate();
        element.setAttribute( "processedMark", processedMark );
    }

    /**
     * Find a {@code TrussBase}
     *
     * @param mark string to match while searching for a {@code TrussBase}
     * @return {@code TrussBase} whose mark matches specified string
     */
    // Copied from Truss - refactor to Minder?
    public static TrussBase Find(String mark ) {
        for (ElementalLister thingy : ElementalLister.List()) {
            if (TrussBase.class.isInstance( thingy )) {
                if (((TrussBase) thingy).processedMark.equals( mark )) {
                    return (TrussBase) thingy;
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
                SvgElement group = svgClassGroup( draw, Truss$.MODULE$.LayerTag() );
                draw.appendRootChild( group );
                SvgElement base = group.rectangle( draw, x - size / 2, y - size / 2, size, size, color );

                Double transformX = x + SvgElement.OffsetX();
                Double transformY = y + SvgElement.OffsetY();
                String transform = "rotate(" + rotation + "," + transformX + "," + transformY + ")";
                base.attribute( "transform", transform );
        }
    }
}
