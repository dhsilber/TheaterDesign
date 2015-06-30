package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Describes a device (such as a {@code Pipe}, {@code Truss}, or {@code LightingStand})
 * that holds hanging things. (Currently just {@code Luminaire}s, but eventually other
 * hangable things as well.)
 *
 * Manage the list of things that can have {@code Luminaire}s (and eventually other
 * hangable things) attached to them.
 * <p/>
 * Created by dhs on 12/16/13.
 *
 * @author dhs
 * @since 0.0.23
 */
public abstract class Mountable extends MinderDom implements Schematicable {

    private static ArrayList<Mountable> MOUNTABLELIST = new ArrayList<>();

    private ArrayList<Luminaire> LUMINAIRELIST = new ArrayList<>();

    /**
     * Find a specific {@code Mountable} from all that have been constructed.
     *
     * @param id of {@code Mountable} to find
     * @return {@code Mountable} instance, or {@code null} if not found
     */
    public static Mountable Select(String id) throws MountingException {
        for (Mountable selection : MOUNTABLELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        throw new MountingException( "'" + id + "' is not a mountable object." );
    }

    public static ArrayList<Mountable> MountableList() { return MOUNTABLELIST; }

//    public static void Remove( String id ) {
//        Mountable candidate = null;
//        for (Mountable selection : MOUNTABLELIST) {
//            if (selection.id.equals( id )) {
//                candidate = selection;
//            }
//        }
//        MOUNTABLELIST.remove( candidate );
//    }

    public static void Remove( Mountable reference ) {
        MOUNTABLELIST.remove( reference );
    }

    /**
     * Construct the {@code Mountable} parts of a from an XML Element.
     * <p/>
     * Keep a list of defined {@code Mountable} objects.
     *
     * @param element DOM Element defining a {@code Mountable} object
     *                // * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException if null element is somehow presented to constructor
     *                             // * @throws SizeException             if the length is too short
     */
    public Mountable(Element element)
            throws AttributeMissingException, DataException,
            InvalidXMLException, MountingException {
        super(element);

        id = getStringAttribute(element, "id");

        try {
            Select( id );
            throw new InvalidXMLException(
                    this.getClass().getSimpleName()+" id '"+id+"' is not unique.");
        }
        catch (MountingException e) {
        }

        MOUNTABLELIST.add(this);
    }

    public abstract Point mountableLocation(String location) throws InvalidXMLException, MountingException, ReferenceException;

    /**
     * Provide the page location for the object at the location specified.
     *
     * @param location
     * @return
     * @throws InvalidXMLException
     * @throws MountingException
     */
    public abstract PagePoint schematicLocation( String location ) throws InvalidXMLException, MountingException; //, MountingException, ReferenceException;

    public abstract Place rotatedLocation( String location ) throws InvalidXMLException, MountingException, ReferenceException;

    public void hang( Luminaire luminaire ) {
        LUMINAIRELIST.add( luminaire );
    }

    public ArrayList<Luminaire> loads() {
        return LUMINAIRELIST;
    }

    // Totally untested. Yar!
    Double slope(Point point1, Point point2) {
        Double x1 = point1.x();
        Double y1 = point1.y();
        Double x2 = point2.x();
        Double y2 = point2.y();

        Double changeInX = x1 - x2;
        Double changeInY = y1 - y2;

        return changeInY / changeInX;
    }
}
