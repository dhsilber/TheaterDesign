package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 9/9/14.
 */
public class Display extends Elemental {

    String layerName = null;
    String deviceName = null;
    String mountableName = null;

    public Display( Element element ) throws InvalidXMLException {
        super( element );

        int attributeCount = 0;

        layerName = getOptionalStringAttribute( element, "layer" );
        deviceName = getOptionalStringAttribute( element, "device" );
        mountableName = getOptionalStringAttribute( element, "mountable" );

        if( ! layerName.equals( "" ) ) {
            attributeCount++;
        }

        if( ! deviceName.equals( "" ) ) {
            attributeCount++;
        }

        if( ! mountableName.equals( "" ) ) {
            attributeCount++;
        }

        if( 1 != attributeCount ) {
            throw new InvalidXMLException(
                    "Display requires one of 'layer', 'device', or 'mountable' attributes." );
        }
    }

    public String layer() {
        return layerName;
    }

    public String device() {
        return deviceName;
    }

    public String mountable() {
        return mountableName;
    }

}
