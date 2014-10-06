package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 9/16/14.
 */
public class CableDiversionPoint extends Elemental {

    Point point = null;

    public CableDiversionPoint( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        int x = getIntegerAttribute( element, "x" );
        int y = getIntegerAttribute( element, "y" );
        int z = getIntegerAttribute( element, "z" );

        point = new Point( x, y, z );
    }

    public Point point() {
        return point;
    }
}
