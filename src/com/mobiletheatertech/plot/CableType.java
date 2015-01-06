package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by dhs on 1/6/15.
 */
public class CableType extends Elemental {

    private static ArrayList<CableType> CABLETYPELIST = new ArrayList<>();

    String schematicColor = null;

    public CableType( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = getStringAttribute( element, "id" );
        schematicColor = getStringAttribute( element, "schematic-color" );

        CABLETYPELIST.add( this );

    }

    public static CableType Select( String identifier ) {
        for (CableType selection : CABLETYPELIST) {
            if (selection.id.equals( identifier )) {
                return selection;
            }
        }
        return null;
    }

    public String color() {
        return schematicColor;
    }
}
