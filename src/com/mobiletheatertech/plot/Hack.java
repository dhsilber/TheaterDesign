package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

public class Hack {
    public static void Dom( Draw draw, View mode) throws ReferenceException {
        if (Venue.Building().equals( "Lincoln-Sudbury Regional High School")) {
        SvgElement apronEdge = draw.element("path");
        apronEdge.attribute( "stroke", "black" );
        apronEdge.attribute( "fill", "none" );
        apronEdge.attribute( "stroke-width", "2" );
        apronEdge.attribute( "d", "M 288,508 A 400,120 0 0 0 864,508 " );
        draw.appendRootChild(apronEdge);

        SvgElement orchestraEdge = draw.element("path");
        orchestraEdge.attribute("stroke", "black");
        orchestraEdge.attribute("fill", "none");
        orchestraEdge.attribute("stroke-width", "2");
        orchestraEdge.attribute("d", "M 288,652 A 400,120 0 0 0 864,652 ");
        draw.appendRootChild(orchestraEdge);
        }
    }
}