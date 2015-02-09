package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Manage the list of things that can have {@code Luminaire}s (and eventually other hangable things) attached to them.
 * <p/>
 * Initially this will be {@code Pipe} and {@code Truss} objects.
 * <p/>
 * Created by dhs on 12/16/13.
 *
 * @author dhs
 * @since 0.0.23
 */
public abstract class Mountable extends MinderDom implements Schematicable {

    private static ArrayList<Mountable> MOUNTABLELIST = new ArrayList<>();

    /**
     * Find a specific {@code Mountable} from all that have been constructed.
     *
     * @param id of {@code Mountable} to find
     * @return {@code Mountable} instance, or {@code null} if not found
     */
    public static Mountable Select(String id) {
        for (Mountable selection : MOUNTABLELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        return null;
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
    public Mountable(Element element) throws AttributeMissingException, DataException, InvalidXMLException {
        super(element);

        id = getStringAttribute(element, "id");

        if( null != Select( id )){
            throw new InvalidXMLException(
                    this.getClass().getSimpleName()+" id '"+id+"' is not unique.");
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
}
