package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 9/9/14.
 */
public class Display extends Elemental {

    String category = null;

    public Display( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        category = getStringAttribute( element, "category" );
        new Category( category, Device.class, category );
    }

    public String category() {
        return category;
    }
}
