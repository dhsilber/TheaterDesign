package com.mobiletheatertech.plot;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: dhs
 * Date: 5/26/13
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 *
 * @since 0.0.5
 */
public class Drawable extends Minder {

    /**
     * Provides the list of all plot items defined.
     *
     * @return list of plot items
     */
    public static ArrayList<Minder> List() {
        System.out.println( "Drawable.List: " + LIST.size() );
        return LIST;
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
    public void dom(Draw draw) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
