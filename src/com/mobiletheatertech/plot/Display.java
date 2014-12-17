package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 9/9/14.
 */
public class Display extends Elemental {

    String layerName = null;
    String deviceName = null;

    public Display( Element element ) throws InvalidXMLException {
        super( element );

        layerName = getOptionalStringAttribute( element, "layer" );
        deviceName = getOptionalStringAttribute( element, "device" );

        if( (layerName.equals( "" ) && deviceName.equals( "" )) ||
                (!layerName.equals( "" ) && !deviceName.equals( "" )) ) {
            throw new InvalidXMLException(
                    "Display requires one of 'layer' or 'device' attributes." );
        }
    }

    public String layer() {
        return layerName;
    }

    public String device() {
        return deviceName;
    }
}
