package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

public class Hack {
    public static void Dom( Draw draw, View mode) throws ReferenceException {
        if (Venue.Building().equals( "Lincoln-Sudbury Regional High School")) {
        Element apronEdge = draw.element("path");
        apronEdge.setAttribute( "stroke", "black" );
        apronEdge.setAttribute( "fill", "none" );
        apronEdge.setAttribute( "stroke-width", "2" );
        apronEdge.setAttribute( "d", "M 288,508 A 400,120 0 0 0 864,508 " );
        draw.appendRootChild(apronEdge);

        Element orchestraEdge = draw.element("path");
        orchestraEdge.setAttribute("stroke", "black");
        orchestraEdge.setAttribute("fill", "none");
        orchestraEdge.setAttribute("stroke-width", "2");
        orchestraEdge.setAttribute("d", "M 288,652 A 400,120 0 0 0 864,652 ");
        draw.appendRootChild(orchestraEdge);
        }
    }
}