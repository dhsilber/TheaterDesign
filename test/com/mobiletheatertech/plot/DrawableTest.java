package com.mobiletheatertech.plot;

import org.testng.annotations.Test;

import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 8/30/13 Time: 6:30 PM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.7
 */
public class DrawableTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void verify() throws Exception {
        Drawable drawable = new Drawable();
        drawable.verify();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void drawPlan() throws Exception {
        Drawable drawable = new Drawable();
        Graphics2D canvas = new Draw().canvas();
        drawable.drawPlan( canvas );
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void drawSection() throws Exception {
        Drawable drawable = new Drawable();
        Graphics2D canvas = new Draw().canvas();
        drawable.drawSection( canvas );
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void drawFront() throws Exception {
        Drawable drawable = new Drawable();
        Graphics2D canvas = new Draw().canvas();
        drawable.drawFront( canvas );
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void dom() throws Exception {
        Drawable drawable = new Drawable();
        Draw draw = new Draw();
        drawable.dom( draw, View.PLAN );
    }
}
