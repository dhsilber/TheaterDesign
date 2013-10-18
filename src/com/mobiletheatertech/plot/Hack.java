package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.*;

/**
 * <p> This is a {@code Hack}. </p><p> Really. </p><p> Sometimes I need to be able to draw up items
 * for a project without having time to properly develop code to allow me to specify what I want in
 * the usual manner. Those drawing & DOM modifications then go here. </p>
 *
 * @author dhs
 * @since 0.0.5
 */
public class Hack {

    public static void Draw( Graphics2D canvas ) {

    }

    public static void Dom( Draw draw ) throws ReferenceException {


//        These lines are for the Reagle Repertory plot, to show the house booms
//
//        Element line1 = draw.element( "line" );
//        line1.setAttribute( "fill", "none" );
//        line1.setAttribute( "x1", "60" );
//        line1.setAttribute( "y1", "630" );
//        line1.setAttribute( "x2", "90" );
//        line1.setAttribute( "y2", "250" );
//        draw.appendRootChild( line1 );
//
//        Element line2 = draw.element( "line" );
//        line2.setAttribute( "fill", "none" );
//        line2.setAttribute( "x1", "62" );
//        line2.setAttribute( "y1", "630" );
//        line2.setAttribute( "x2", "92" );
//        line2.setAttribute( "y2", "250" );
//        draw.appendRootChild( line2 );
//
//        Element line3 = draw.element( "line" );
//        line3.setAttribute( "fill", "none" );
//        line3.setAttribute( "x1", "780" );
//        line3.setAttribute( "y1", "630" );
//        line3.setAttribute( "x2", "750" );
//        line3.setAttribute( "y2", "250" );
//        draw.appendRootChild( line3 );
//
//        Element line4 = draw.element( "line" );
//        line4.setAttribute( "fill", "none" );
//        line4.setAttribute( "x1", "782" );
//        line4.setAttribute( "y1", "630" );
//        line4.setAttribute( "x2", "752" );
//        line4.setAttribute( "y2", "250" );
//        draw.appendRootChild( line4 );

        if (Venue.Name().equals( "Lawrence Academy" )) {

//        For the Lawrence Academy apron:
            Element line = draw.element( "line" );
            line.setAttribute( "fill", "none" );
            line.setAttribute( "x1", "105" );
            line.setAttribute( "y1", "519" );
            line.setAttribute( "x2", "679" );
            line.setAttribute( "y2", "519" );
            draw.appendRootChild( line );

            // For the raked platform for "The Sparrow":
            Element raked = draw.element( "path" );
            raked.setAttribute( "fill", "none" );
            raked.setAttribute( "stroke", "orange" );
            raked.setAttribute( "stroke-width", "1" );
            raked.setAttribute( "d",
//                               "M -185 -168, -120 -172, 120 -172, 185 -168, 72 190, -72 190 Z" );
                                "M 207 575, 272 579, 512 579, 577 575, 464 269, 320 269 Z" );
            draw.appendRootChild( raked );

            // For the flat platform for "The Sparrow":
            Element flat = draw.element( "path" );
            flat.setAttribute( "fill", "none" );
            flat.setAttribute( "stroke", "orange" );
            flat.setAttribute( "stroke-width", "1" );
            flat.setAttribute( "d",
//                               "M  72 190, -72 190, -72 262, 72 262 Z" );
                               "M  464 269, 320 269, 320 197, 464 197 Z" );

            draw.appendRootChild( flat );
        }

//        line = draw.element( "line" );
//        line.setAttribute( "fill", "none" );
//        line.setAttribute( "x1", "105" );
//        line.setAttribute( "y1", "519" );
//        line.setAttribute( "x2", "679" );
//        line.setAttribute( "y2", "519" );
//        draw.appendRootChild( line );


    }
}
