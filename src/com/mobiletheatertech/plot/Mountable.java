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
public abstract class Mountable extends MinderDom /*implements Schematicable*/ {

    private static ArrayList<Mountable> MOUNTABLELIST = new ArrayList<>();

    protected ArrayList<Luminaire> LUMINAIRELIST = new ArrayList<>();

//    protected ArrayList<Suspend> suspensions = new ArrayList<>();
    protected Double span = 0.0;

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

    static ArrayList<Mountable> MountableList() { return MOUNTABLELIST; }

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
     *                             // * @throws Sizthrows InvalidXMLException, MountingExceptioneException             if the length is too short
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

    public abstract Point mountableLocation(String location)
            throws InvalidXMLException, MountingException, ReferenceException;

    /**
     * Provide the page location for the object at the location specified.
     *
     * @param location
     * @return
     * @throws InvalidXMLException
     * @throws MountingException
     */
    public abstract PagePoint schematicLocation( String location ) throws InvalidXMLException, MountingException; //, MountingException, ReferenceException;

    public abstract Place rotatedLocation( String location )
            throws AttributeMissingException, DataException, InvalidXMLException,
            MountingException, ReferenceException;

    public void hang( Luminaire luminaire ) {
        LUMINAIRELIST.add( luminaire );
    }

    public abstract Integer locationDistance( String location ) throws InvalidXMLException, MountingException;

    public ArrayList<Luminaire> loads() {
        return LUMINAIRELIST;
    }

    public String id() { return id; }

    public abstract String suspensionPoints( /*ArrayList<Anchor> suspensions*/ );

    public abstract String calculateIndividualLoad( Luminaire luminaire ) throws InvalidXMLException, MountingException;

    public abstract String totalSuspendLoads();

    public String weights() throws InvalidXMLException, MountingException {
        StringBuilder text = new StringBuilder();

        text.append( "Weights for " );
        text.append( id );
        text.append( "\n\n" );
        text.append( suspensionPoints() );
        text.append( "\n\n" );
        Double totalWeight = 0.0;
        for( Luminaire lumi : loads() ) {
            text.append( lumi.unit() );
            text.append( ": " );
            text.append( lumi.type() );
            text.append( " at " );
            text.append( lumi.locationValue() );
            text.append( " weighs " );
            Double weight = lumi.weight();
            totalWeight += weight;
            text.append( weight.toString() );
            text.append( " pounds." );
//            text.append( calculateIndividualLoad( lumi ) );
            text.append( "\n" );
        }
        text.append( "\nTotal: " );
        text.append( totalWeight.toString() );
        text.append( " pounds" );
//        text.append( totalSuspendLoads() );
        text.append( "\n" );

        return text.toString();
    }
}
