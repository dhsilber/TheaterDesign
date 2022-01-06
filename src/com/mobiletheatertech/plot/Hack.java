package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGElement;

public class Hack {
    public static void Dom( Draw draw, View mode) throws ReferenceException {

        SvgElement script = draw.element("script");
        draw.appendRootChild( script );


        if (Venue.Building().equals( "Lincoln-Sudbury Regional High School")) {
            Double xLeft = 288 + SvgElement.OffsetX();
            Double xRight = 864 + SvgElement.OffsetX();
            Double apronY = 508 + SvgElement.OffsetY();
            Double orchestraY = 652 + SvgElement.OffsetY();

            String apronPath = "M " + xLeft.toString() + "," + apronY.toString() +
                    "A 400,120 0 0 0 " + xRight.toString() + "," + apronY.toString();
            String orchestraPath = "M " + xLeft.toString() + "," + orchestraY.toString() +
                    "A 400,120 0 0 0 " + xRight.toString() + "," + orchestraY.toString();

//            System.out.println( apronPath );
//            System.out.println( orchestraPath );

            SvgElement apronEdge = draw.element("path");
            apronEdge.attribute( "stroke", "black" );
            apronEdge.attribute( "fill", "none" );
            apronEdge.attribute( "stroke-width", "2" );
//            apronEdge.attribute( "d", "M 288,508 A 400,120 0 0 0 864,508 " );
            apronEdge.attribute( "d", apronPath );
            draw.appendRootChild(apronEdge);

            SvgElement orchestraEdge = draw.element("path");
            orchestraEdge.attribute("stroke", "black");
            orchestraEdge.attribute("fill", "none");
            orchestraEdge.attribute("stroke-width", "2");
//            orchestraEdge.attribute("d", "M 288,652 A 400,120 0 0 0 864,652 ");
            orchestraEdge.attribute("d", orchestraPath );
            draw.appendRootChild(orchestraEdge);
        }
    }
}