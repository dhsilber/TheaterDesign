package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents a specific instance of a device described by a {@code DeviceTemplate}.
 *
 * Positioning is flexible:
 *
 * An arbitrary position can be set with x, y, and z attributes, if no
 * 'on' attribute is specified.
 *
 * The 'on' attribute stacks a {@code Device} on top of some other
 * appropriate item. If x and y attributes are set, they position the
 * device relative to what it is stacked on. No z attribute may be set.
 *
 * An orientation may be specified to rotate the device horizontally.
 *
 * A layer attribute may optionally be specified.
 *
 *
 * Created by dhs on 7/2/14.
 */
/*
If I need it, implement 'inclination' for vertical rotation.
 */
public class Device extends Stackable // implements Schematicable
{
    private static ArrayList<Device> DEVICELIST = new ArrayList<>();

    private PagePoint schematicPosition = null;
    private Rectangle2D.Double schematicBox = null;

    private String is;
    private String on;

    private DeviceTemplate template = null;
    private Stackable surface = null;

    Solid shape = null;
    Point place = null;
    String layerName = null;
    String color = "black";
    Double x = null;
    Double y = null;
    Double z = null;
    Double orientation = null;
    String layerSpecified = null;

    // device dimensions
    Double width = null;
    Double depth = 0.0;
    Double height = null;

    boolean verified = false;

//    CableCounter cableCounter = new CableCounter();


    /**
     * Construct a {@code Box} from an XML element.
     *
     * @param element DOM Element defining a device
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Device( Element element ) throws AttributeMissingException, DataException, InvalidXMLException {
        super(element);
        id = getStringAttribute( "id" );
        is = getStringAttribute( "is" );
        on = getOptionalStringAttribute( "on" );
        x = getOptionalDoubleAttributeOrZero( "x" );
        y = getOptionalDoubleAttributeOrZero( "y" );
        z = getOptionalDoubleAttributeOrZero( "z" );
        orientation = getOptionalDoubleAttributeOrZero( "orientation" );
        layerSpecified = getOptionalStringAttribute( "layer" );

        if( "".equals( on ) && ( x.equals( 0.0 ) || y.equals( 0.0 ) ) ) {
            throw new AttributeMissingException( "Device (" + id +
                    ") needs either the 'on' attribute or the set of x, y, and z coordinates." );
        }

        GearList.Add(is);

        DEVICELIST.add( this );
    }

    public static Device Select( String identifier ) {
        for (Device selection : DEVICELIST) {
            if (selection.id.equals( identifier )) {
                return selection;
            }
        }
        return null;
    }

    public String layer() {
        return layerName;
    }

    public String is() {
        return is;
    }

    public Place drawingLocation() {
        return new Place( place, place, 0.0 );
    }

    /**
     * Verifies that the specifications for this Device make sense within the context of all of the various
     * XML that needed to be parsed before it could be discovered that it affected this Device.
     *
     * @throws DataException
     * @throws FeatureException
     * @throws InvalidXMLException
     * @throws LocationException
     * @throws MountingException
     * @throws ReferenceException
     */
    @Override
    public void verify()
            throws DataException, FeatureException, InvalidXMLException, LocationException,
            MountingException, ReferenceException
    {
        verified = true;

        template = DeviceTemplate.Select( is );
        if( null == template ){
            throw new InvalidXMLException( "Device", id,
                    "'is' reference ("+is+") does not exist" );
        }

        // Get what needs to be drawn from 'is'.
        shape = template.getSolid();

//        if( 90.0 == orientation ){
//            width = shape.depth();
//            depth = shape.width();
//        }
//        else {
            width = shape.width();
            depth = shape.depth();
        height = shape.height();
//        }

        if( "".equals( on ) ) {
            place = new Point( x - width / 2, y - depth / 2, z );
        }
        else {
            surface = Stackable.Select(on);
            if( null == surface ) {
                throw new InvalidXMLException( "Device", id,
                        "'on' reference ("+on+") does not exist" );
            }

            // Give the what to 'on' to find out where
            place = surface.location(shape);
        }

        layerName = ( ! "".equals( layerSpecified ) ) ? layerSpecified : template.layer();

        if ( null != layerName ) {
            Layer layerActual = Layer.Retrieve(layerName);
            if (null != layerActual) {
                color = layerActual.color();
            }

            layerActual.register(this);
        }
    }

//    @Override
//    public void useCount( Direction direction, CableRun run ) {
//        cableCounter.add( direction, run );
//    }
//
//    @Override
//    public void preview( View view ) {
//        switch ( view ) {
//            case SCHEMATIC:
//                Double width = shape.width();
//                Double height = shape.height();
//                schematicPosition = Schematic.Position( width, height );
//
//                schematicBox = new Rectangle2D.Double(
//                        schematicPosition.x() - width / 2,
//                        schematicPosition.y() - height / 2,
//                        width, height );
//                Schematic.Obstruction( this );
//        }
//    }

    /*
     */
    public PagePoint schematicLocation() throws InvalidXMLException {
        return schematicPosition;
    }

//    /*
//    Position of this Device
//     */
//    @Override
//    public PagePoint schematicPosition() {
//        if (null == schematicPosition) {
////            System.err.println( this.toString() + " has no schematic position.");
//        }
//        return schematicPosition;
//    }
//
//    @Override
//    public PagePoint schematicCableIntersectPosition( CableRun run )
//            throws CorruptedInternalInformationException
//    {
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

    @Override
    public void dom(Draw draw, View view)
            throws MountingException, ReferenceException
    {
        SvgElement group = null;
        SvgElement element = null;

        switch (view) {
            case PLAN:
                group = svgClassGroup( draw, layerName);
                Double shiftedX = x + SvgElement.OffsetX();
                Double shiftedY = y + SvgElement.OffsetY();
                group.attribute("transform", "rotate(" + orientation + "," + shiftedX + "," + shiftedY + ")");
                draw.appendRootChild(group);

                element = group.rectangle( draw, place.x(), place.y(), width, depth, color );
                element.attribute("fill", color);
                element.attribute("fill-opacity", "0.1");

                Double x = place.x() + 3;
                Double y = place.y() + depth - 4;
                SvgElement idText = group.text( draw, id, x, y, color );
                idText.attribute( "text-anchor", "left" );

                template.count();

                break;
            case SECTION:
                Double bottom = Venue.Height();

                group = svgClassGroup( draw, layerName);
                draw.appendRootChild(group);

                element = group.rectangle( draw, place.y(), bottom - height, depth, height, color );
                element.attribute("fill", color);
                element.attribute("fill-opacity", "0.1");

                break;
            case FRONT:
                break;
            case TRUSS:
                break;
//            case SCHEMATIC:
//                group = svgClassGroup( draw, layerName);
//                draw.appendRootChild(group);
//
//                Double leftEdge = schematicPosition.x() - width / 2;
//                Double topEdge = schematicPosition.y() - height / 2;
//                element = group.rectangleAbsolute( draw,
//                        leftEdge, topEdge, width, height, color);
//                element.attribute("fill", color);
//                element.attribute("fill-opacity", "0.1");
//
//                /*SvgElement idText =*/ group.textAbsolute(draw, id,
//                    leftEdge, topEdge + height + Schematic.TextSpace, color);
////                idText.attribute( "text-anchor", "left" );
//
//                template.count();
//
//                break;
        }
    }

    /**
     * Provide the location to stack a specified Solid on this Device.
     *
     * @param shape
     * @return
     */
    /*
    This needs verify() to have been run. If it hasn't been, run verify() when invoked.
     */
    @Override
    public Point location( Solid shape ) throws DataException, FeatureException, InvalidXMLException,
            LocationException, MountingException, ReferenceException {

        if ( ! verified ) { this.verify(); }

        if ( null == x ) {
            throw new Error ("Found null x for "+ this.toString() );
        }
        if ( null == y ) {
            throw new Error ("Found null y for "+ this.toString() );
        }
        if ( null == z ) {
            throw new Error ("Found null z for "+ this.toString() );
        }
        Double ex = x;
        Double wy = y;
        Double ze = z;

        Double lastWidth = 0.0;

        if ( null == thingsOnThis ) {
            throw new Error ("Found null thingsOnThis." );
        }

        for ( Thing item : thingsOnThis) {
            if( null == item ) {
                throw new Error ("Found null item for "+ this.toString() );
            }

            if( null == item.point ) {
                throw new Error ("Found null item.point for "+ this.toString() );
            }
            if( null == item.solid ) {
                throw new Error ("Found null item.solid for "+ this.toString() );
            }
            ex = Math.max( ex, item.point.x() );
            wy = Math.max( wy, item.point.y() );
            ze = Math.max( ze, item.point.z() );

            lastWidth = item.solid.width();
        }
        Thing thing = new Thing();
        if ( null == depth) {
            throw new Error ( "height is null for "+ this.toString() );
        }
        thing.point = new Point( x - width / 2, wy + lastWidth - depth / 2, z );

        if( null == shape ) {
            throw new Error ("Solid is null for "+ this.toString() );
        }
        thing.solid = shape;

        thingsOnThis.add(thing);

        return thing.point;
    }

    public String toString(){
        StringBuilder result = new StringBuilder( "Device: " );
        if ( null == id ) {
            result.append( "id is null" );
        } else {
            result.append( id );
        }
        result.append( ", is: " );
        if ( null == is ) {
            result.append( "is is null" );
        } else {
            result.append( is );
        }
        result.append( ", on: " );
        if ( null == on ) {
            result.append( "on is null" );
        } else {
            result.append( on );
        }
        result.append( ", at: " );
        if ( null == x ) {
            result.append( "x is null" );
        } else {
            result.append( x );
        }
        result.append( ", " );
        if ( null == y ) {
            result.append( "y is null" );
        } else {
            result.append( y );
        }
        result.append( ", " );
        if ( null == z ) {
            result.append( "z is null" );
        } else {
            result.append( z );
        }
        result.append( "." );
        return result.toString();
    }
}
