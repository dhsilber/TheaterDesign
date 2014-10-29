package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;


/**
 * Manage the list of things that can be stacked on.
 * <p/>
 * Initially this will be {@code Table} objects.
 * <p/>
 * The id attribute is not required, but if one is not provided, the
 * resulting Stackable cannot be searched for or stacked on.
 *
 * Created by dhs on 7/3/14.
 */
public abstract class Stackable extends MinderDom {

    // List of Stackable objects.
    static ArrayList<Stackable> STACKABLELIST = new ArrayList<>();

    // List of things on a Stackable instance.
    ArrayList<Thing> thingsOnThis = new ArrayList<Thing>();

    /**
     * Find a specific {@code Stackable} from all that have been constructed.
     *
     * @param id of {@code Mountable} to find
     * @return {@code Mountable} instance, or {@code null} if not found
     */
    public static Stackable Select(String id) {
        for (Stackable selection : STACKABLELIST) {
            if (selection.id.equals( id )) {
                return selection;
            }
        }
        return null;
    }

    public Stackable(Element element) throws AttributeMissingException, InvalidXMLException {
        super(element);

        id = getOptionalStringAttribute( element, "id");

        if( ! "".equals( id )) {

            if( null != Select( id )){
                throw new InvalidXMLException(this.getClass().getSimpleName()+" id '"+id+"' is not unique.");
            }

            STACKABLELIST.add(this);
        }
    }

//    public abstract Point location( String location )
//            throws InvalidXMLException, MountingException, ReferenceException;

    public abstract Point location( Solid shape ) throws FeatureException, InvalidXMLException,
            LocationException, MountingException, ReferenceException
            ;

}
