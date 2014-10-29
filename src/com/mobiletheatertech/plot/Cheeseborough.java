package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 10/26/14.
 */
public class Cheeseborough extends MinderDom {

    String on = null;
    String location = null;
    String type = null;
    Place place = null;
    Mountable mount = null;

    public Cheeseborough( Element element ) throws AttributeMissingException, InvalidXMLException {
        super (element);

        on = getStringAttribute( element, "on" );
        location = getStringAttribute( element, "location" );
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {
        mount = Mountable.Select(on);
        if (null == mount) {
            throw new MountingException(
                    "Cheeseborough has unknown mounting: '" + on + "'.");
        }

        try {
            place = mount.rotatedLocation(location);
        } catch (InvalidXMLException | MountingException e) {
            throw new MountingException(
                    "Cheeseborough has location '" + location + "' which caused: " +
                            e.getMessage() );
        }
    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {

    }
}
