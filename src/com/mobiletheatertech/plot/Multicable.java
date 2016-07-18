//package com.mobiletheatertech.plot;
//
//import org.w3c.dom.Element;
//
//import java.awt.geom.Rectangle2D;
//import java.util.ArrayList;
//
///**
// * Created by dhs on 1/8/15.
// */
//public class Multicable implements Schematicable {
//
//    private static ArrayList<Multicable> MULTICABLELIST = new ArrayList<>();
//
//    private PagePoint schematicPosition = null;
//    private Rectangle2D.Double schematicBox = null;
//
//    String id = null;
//    Double x = null;
//    Double y = null;
//    Double z = null;
//
//    final Double RADIUS = 5.0;
//    final String COLOR ="black";
//    final Double BOXSIZE = 10.0;
//
//    CableCounter cableCounter = new CableCounter();
//
//
//    public Multicable( String id, Double x, Double y, Double z )
//    {
//        this.id = id;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//
//        MULTICABLELIST.add( this );
//    }
//
//    public static Multicable Select( String identifier ) {
////        int index = identifier.lastIndexOf( ":" );
////        String name = identifier.substring( 0, index );
//
//        for ( Multicable instance : MULTICABLELIST ) {
//            if ( instance.id.equals( identifier ) ) {
//                return instance;
//            }
//        }
//        return null;
//    }
//
//    public static void PreviewAll( View view )
//            throws InvalidXMLException, MountingException, ReferenceException
//    {
//        for ( Multicable instance : MULTICABLELIST ) {
//            instance.preview(view);
//        }
//    }
//
//    public static void DomAll( Draw draw, View view )
//            throws InvalidXMLException, MountingException, ReferenceException
//    {
//        for ( Multicable instance : MULTICABLELIST ) {
//            instance.dom(draw, view);
//        }
//    }
//
//    @Override
//    public PagePoint schematicPosition() {
//        if (null == schematicPosition) {
////            System.err.println( this.toString() + " has no schematic position.");
//        }
//        return schematicPosition;
//    }
//
//    /**
//     * This is schematicPosition() adjusted to take into account the number of
//     * cables attempting to intersect a thing from that direction.
//     *
//     * @return a position along the edge of a thing
//     */
//    @Override
//    public PagePoint schematicCableIntersectPosition( CableRun run )
//            throws CorruptedInternalInformationException
//    {
//        Solid shape = new Solid( RADIUS * 2, RADIUS * 2, 2.0 );
//        return cableCounter.cableIntersectPosition( shape, schematicPosition, run );
//    }
//
//    @Override
//    public Rectangle2D.Double schematicBox() {
//        return schematicBox;
//    }
//
//    @Override
//    public void schematicReset() {
//        cableCounter.clear();
//        schematicPosition = null;
//    }
//
//    @Override
//    public void useCount( Direction direction, CableRun run ) {
//        cableCounter.add( direction, run );
//    }
//
//    @Override
//    public void preview( View view )
////            throws CorruptedInternalInformationException, InvalidXMLException, MountingException, ReferenceException
//    {
//        switch ( view ) {
//            case SCHEMATIC:
//                Double width = RADIUS * 2;
//                Double height = RADIUS * 2;
//                schematicPosition = Schematic.Position( width, height );
//
//                schematicBox = new Rectangle2D.Double(
//                        schematicPosition.x() - RADIUS,
//                        schematicPosition.y() - RADIUS,
//                        width, height );
//                Schematic.Obstruction( this );
//        }
//    }
//
//    @Override
//    public Place drawingLocation() {
//        Point point = new Point( x, y, z );
//        return new Place( point, point, 0.0 );
//    }
//
//    public void dom( Draw draw, View view ) {
//        switch (view) {
//            case PLAN:
//            case SCHEMATIC:
//                break;
//            default:
//                return;
//        }
//
//        SvgElement group = draw.group(draw, "");
////        draw.appendRootChild(group);
//        SvgElement circle = null;
//        switch (view) {
//            case PLAN:
//                circle = group.circle(draw, x, y, RADIUS, COLOR);
//                circle.attribute("stroke-width", "3");
//
//                break;
//
//            case SCHEMATIC:
//                circle = group.circleAbsolute(draw,
//                        schematicPosition.x(), schematicPosition.y(), RADIUS, COLOR);
//                circle.attribute("stroke-width", "3");
//
//                group.textAbsolute(draw, id, schematicPosition.x() - RADIUS,
//                        schematicPosition.y() + RADIUS + Schematic.TextSpace, COLOR );
//                break;
//        }
//    }
//}
