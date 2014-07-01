package com.mobiletheatertech.plot;

import org.testng.annotations.*;
import org.w3c.dom.Element;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/30/13 Time: 6:30 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.7
 */
public class DrawableTest {
    Element element = null;

    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void verify() throws Exception {
        Drawable drawable = new Drawable( element );
        drawable.verify();
    }

    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void drawPlan() throws Exception {
        Drawable drawable = new Drawable( element );
        Graphics2D canvas = new Draw().canvas();
        drawable.drawPlan( canvas );
    }

    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void drawSection() throws Exception {
        Drawable drawable = new Drawable( element );
        Graphics2D canvas = new Draw().canvas();
        drawable.drawSection( canvas );
    }

    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void drawFront() throws Exception {
        Drawable drawable = new Drawable( element );
        Graphics2D canvas = new Draw().canvas();
        drawable.drawFront( canvas );
    }

    @Test( expectedExceptions = UnsupportedOperationException.class )
    public void dom() throws Exception {
        Drawable drawable = new Drawable( element );
        Draw draw = new Draw();
        drawable.dom( draw, View.PLAN );
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
//        Element venueElement = new IIOMetadataNode( "venue" );
//        venueElement.setAttribute( "name", "Test Name" );
//        venueElement.setAttribute( "width", "350" );
//        venueElement.setAttribute( "depth", "400" );
//        venueElement.setAttribute( "height", "240" );
//        new Venue( venueElement );
//
//        Element pipeElement = new IIOMetadataNode( "wall" );
//        pipeElement.setAttribute( "x1", "10" );
//        pipeElement.setAttribute( "y1", "20" );
//        pipeElement.setAttribute( "x2", "30" );
//        pipeElement.setAttribute( "y2", "40" );
//        new Pipe( pipeElement );
//
//        elementOnPipe = new IIOMetadataNode( "wall" );
//        elementOnPipe.setAttribute( "height", height.toString() );
//        elementOnPipe.setAttribute( "width", width.toString() );
//        elementOnPipe.setAttribute( "x2", x2.toString() );
//        elementOnPipe.setAttribute( "y2", y2.toString() );
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

}
