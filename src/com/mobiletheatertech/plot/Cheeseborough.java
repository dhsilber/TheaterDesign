package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by dhs on 10/26/14.
 *
 * Note that as of 2015-06-22, the way that attribute checking is handled here seems like the way to go
 * to deal with sets of attributes which are required in some cases and optional in others.
 */
public class Cheeseborough extends MinderDom {

    private static ArrayList<Cheeseborough> CHEESEBOROUGHLIST = new ArrayList<>();

    String on = null;
    String location = null;
    String type = null;
    Place place = null;
    Mountable mount = null;
    Cheeseborough reference = null;

    String color = "orange";

    public Cheeseborough( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException, MountingException {
        super (element);

        id = getOptionalStringAttribute(element, "id");
        on = getOptionalStringAttribute(element, "on");
        location = getOptionalStringAttribute( element, "location" );
        String ref = getOptionalStringAttributeOrNull( element, "ref" );

        if( null == ref ) {
            checkAttribute( "id", id );
            checkAttribute( "on", on );
            checkAttribute( "location", location );

            CHEESEBOROUGHLIST.add( this );
        }
        else {
            checkNotAttribute( "id", id, "reference (" + ref + ") should not also have 'id' attribute" );
            checkNotAttribute( "on", on, "reference (" + ref + ") should not also have 'on' attribute" );
            checkNotAttribute( "location", location, "reference (" + ref + ") should not also have 'location' attribute" );

            for( Cheeseborough item : CHEESEBOROUGHLIST ) {
                if ( ref.equals( item.id ) ) {
                    reference = item;
                    return;
                }
            }

            throw new InvalidXMLException( "Cheeseborough reference (" + ref + ") does not exist" );
        }
    }

    /**
     * Find a {@code Suspend}
     *
     * @param id string to match while searching for a {@code Suspend}
     * @return {@code Suspend} whose mark matches specified string
     */
    // Copied from Truss - refactor to Minder?
    public static Cheeseborough Find( String id ) {
        for (Cheeseborough thingy : CHEESEBOROUGHLIST) {
                if ( thingy.id.equals( id )) {
                    return thingy;
                }
        }
        return null;
    }

    public Point locate() {
        return place.location();
    }

    public String on() {
        return on;
    }

    public String location() {
        return location;
    }

    public Mountable mount() {
        return mount;
    }

    @Override
    public void verify()
            throws AttributeMissingException, DataException, FeatureException,
            InvalidXMLException,
            LocationException, MountingException, ReferenceException {

        if( null == reference ) {
            System.err.println( "Cheeseborough verify()");
            try {
                mount = Mountable.Select(on);
                System.err.println( "location: " + location.toString() );
                System.err.println( "mount: " + mount.toString() );
            }
            catch ( MountingException e ) {
                throw new MountingException(
                        "Cheeseborough has unknown mounting: '" + on + "'.");
            }

            try {
                System.err.println( "trying" );
                place = mount.rotatedLocation(location);
                System.err.println( "mount: " + mount.toString() + "place: " + place.toString() );
            } catch ( InvalidXMLException | MountingException e) {
                System.err.println( "catching" );
                throw new MountingException(
                        "Cheeseborough has location '" + location + "' which caused: " +
                                e.getMessage() );
            }
            System.err.println( "done" );
        }
        else {
            mount = reference.mount();
//            on = reference.on();
            location = reference.location();
        }

    }

    // Get the location that this Cheeseborough holds something at.
    public Point mountableLocation()
            throws AttributeMissingException, DataException, InvalidXMLException,
            MountingException, ReferenceException {
        if( null != reference ) {
            if (null == place) {
                mount = reference.mount();
                location = reference.location();
                place = mount.rotatedLocation( location );
            }
        }
        return place.location();
    }

//    @Override
//    public PagePoint schematicLocation(String location) throws InvalidXMLException, MountingException {
//        return null;
//    }
//
//    @Override
//    public Place rotatedLocation(String location) throws InvalidXMLException, MountingException, ReferenceException {
//        return null;
//    }

    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        switch (mode) {
            case PLAN:
                SvgElement group = svgClassGroup( draw, Truss.LAYERTAG );
                draw.appendRootChild( group );
                System.err.println( "Cheeseborough (not drawn) place: " + place.toString() );
//                SvgElement base = group.circle(draw, place.location().x(), place.location().y(), 50.0, color);
        }
    }

//    @Override
//    public PagePoint schematicPosition() {
//        return null;
//    }
//
//    @Override
//    public PagePoint schematicCableIntersectPosition(CableRun run) throws CorruptedInternalInformationException, ReferenceException {
//        return null;
//    }
//
//    @Override
//    public Rectangle2D.Double schematicBox() {
//        return null;
//    }
//
//    @Override
//    public void schematicReset() {
//
//    }
//
//    @Override
//    public void useCount(Direction direction, CableRun run) {
//
//    }
//
//    @Override
//    public void preview(View view) throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException {
//
//    }
//
//    @Override
//    public Place drawingLocation() throws InvalidXMLException, MountingException, ReferenceException {
//        return null;
//    }
}
