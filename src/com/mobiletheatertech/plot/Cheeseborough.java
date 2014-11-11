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

    String color = "orange";

    public Cheeseborough( Element element ) throws AttributeMissingException, InvalidXMLException {
        super (element);

        on = getStringAttribute( element, "on" );
        location = getStringAttribute( element, "location" );
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {
        System.err.println( "Cheeseborough verify()");
        mount = Mountable.Select(on);
        System.err.println( "location: " + location.toString() );
        System.err.println( "mount: " + mount.toString() );
        if (null == mount) {
            throw new MountingException(
                    "Cheeseborough has unknown mounting: '" + on + "'.");
        }
        System.err.println( "after null check" );

        try {
            System.err.println( "trying" );
            place = mount.rotatedLocation(location);
            System.err.println( "mount: " + mount.toString() );
        } catch (InvalidXMLException | MountingException e) {
            System.err.println( "catching" );
            throw new MountingException(
                    "Cheeseborough has location '" + location + "' which caused: " +
                            e.getMessage() );
        }
        System.err.println( "done" );
    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        switch (mode) {
            case PLAN:
                SvgElement group = svgClassGroup( draw, Truss.LAYERTAG );
                draw.appendRootChild( group );
                SvgElement base = group.circle(draw, place.location().x(), place.location().y(), 5.0, color);
        }
    }
}
