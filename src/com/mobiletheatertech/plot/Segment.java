package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 4/15/14.
 *
 * @author dhs
 * @since 0.0.24 
 */
public class Segment extends Elemental {

    Integer x1=null;
    Integer y1=null;
    Integer x2=null;
    Integer y2=null;

    public Segment( Element element ) throws AttributeMissingException, InvalidXMLException {
        super (element);

        x1 = getPositiveIntegerAttribute( "x1");
        y1 = getPositiveIntegerAttribute( "y1");
        x2 = getPositiveIntegerAttribute( "x2");
        y2 = getPositiveIntegerAttribute( "y2");

    }
}
