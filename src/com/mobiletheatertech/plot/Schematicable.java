package com.mobiletheatertech.plot;

import java.awt.geom.Rectangle2D;

/**
 * Created by dhs on 12/17/14.
 */
public interface Schematicable {

    public PagePoint schematicPosition();

    public Rectangle2D.Double schematicBox();
}
