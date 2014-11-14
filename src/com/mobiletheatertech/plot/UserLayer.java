package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Allow a user to define a drawing layer in the description file.
 *
 * @author dhs
 * @since 2014-08-15
 */
public class UserLayer extends ElementalLister {

    private String name = null;
    private String color = null;

    public UserLayer( Element element )
            throws AttributeMissingException, DataException,
            InvalidXMLException
    {
        super( element );

        id = getStringAttribute( element, "id" );
        name = getStringAttribute( element, "name" );
        color = getStringAttribute( element, "color" );

        new Layer( id, name, color );
    }

}
