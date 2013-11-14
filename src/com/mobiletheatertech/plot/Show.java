package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 12:30 AM To change this template use
 * File | Settings | File Templates.
 */
public class Show extends Elemental {

    //    private String name;
    private String setup;

    public Show( Element element ) throws AttributeMissingException {
        id = getStringAttribute( element, "name" );
        setup = getOptionalStringAttribute( element, "setup" );
    }

    public String name() {
        return id;
    }

    public String setup() {
        return setup;
    }
}
