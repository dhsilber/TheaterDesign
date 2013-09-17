package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.*;

/**
 * <p> This is a {@code Hack}. </p><p> Really. </p><p> Sometimes I need to be able to draw up items
 * for a project without having time to properly develop code to allow me to specify what I want in
 * usual manner. Those drawing & DOM modifications then go here. </p>
 *
 * @author dhs
 * @since 0.0.5
 */
public class Hack {

    public static void Draw( Graphics2D canvas ) {

    }

    public static void Dom( Draw draw ) {
        Element line1 = draw.element( "line" );
        line1.setAttribute( "fill", "none" );
        line1.setAttribute( "x1", "60" );
        line1.setAttribute( "y1", "630" );
        line1.setAttribute( "x2", "90" );
        line1.setAttribute( "y2", "250" );
        draw.appendRootChild( line1 );

        Element line2 = draw.element( "line" );
        line2.setAttribute( "fill", "none" );
        line2.setAttribute( "x1", "62" );
        line2.setAttribute( "y1", "630" );
        line2.setAttribute( "x2", "92" );
        line2.setAttribute( "y2", "250" );
        draw.appendRootChild( line2 );

        Element line3 = draw.element( "line" );
        line3.setAttribute( "fill", "none" );
        line3.setAttribute( "x1", "780" );
        line3.setAttribute( "y1", "630" );
        line3.setAttribute( "x2", "750" );
        line3.setAttribute( "y2", "250" );
        draw.appendRootChild( line3 );

        Element line4 = draw.element( "line" );
        line4.setAttribute( "fill", "none" );
        line4.setAttribute( "x1", "782" );
        line4.setAttribute( "y1", "630" );
        line4.setAttribute( "x2", "752" );
        line4.setAttribute( "y2", "250" );
        draw.appendRootChild( line4 );

    }
}
