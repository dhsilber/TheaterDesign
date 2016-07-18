//package com.mobiletheatertech.plot;
//
//import java.awt.geom.Rectangle2D;
//
///**
// * Created by dhs on 12/17/14.
// */
//public interface Schematicable {
//
////    public static Schematicable Select( String identifier );
//
//    public PagePoint schematicPosition();
//
//    /**
//     * This is schematicPosition() adjusted to take into account the number of
//     * cables attempting to intersect a thing from that direction.
//     *
//     * @return a position along the edge of a thing
//     */
//    public PagePoint schematicCableIntersectPosition( CableRun run )
//            throws CorruptedInternalInformationException, ReferenceException;
//
//    public Rectangle2D.Double schematicBox();
//
//    public void schematicReset();
//
//    public void useCount( Direction direction, CableRun run );
//
//    public void preview( View view )
//            throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException;
//
//    public Place drawingLocation() throws AttributeMissingException, DataException,
//            InvalidXMLException, MountingException, ReferenceException;
//}
