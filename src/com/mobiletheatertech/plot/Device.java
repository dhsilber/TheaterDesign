package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

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
public class Device extends Stackable
{
    private static ArrayList<Device> DEVICELIST = new ArrayList<>();

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
    Double width = null;
    Double height = null;

//    private static final String COLOR = "black";
//    private static final String FILLCOLOR = "grey";


    /**
     * Construct a {@code Box} from an XML element.
     *
     * @param element DOM Element defining a device
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Device( Element element ) throws AttributeMissingException, InvalidXMLException {
        super(element);

        id = getStringAttribute(element, "id");
        is = getStringAttribute(element, "is");
        on = getOptionalStringAttribute(element, "on");
        x = getOptionalDoubleAttribute(element, "x");
        y = getOptionalDoubleAttribute(element, "y");
        z = getOptionalDoubleAttribute(element, "z");
        orientation = getOptionalDoubleAttribute(element, "orientation");

        if( "".equals( on ) && ( x.equals( 0.0 ) || y.equals( 0.0 ) ) ) {
            throw new AttributeMissingException( "Device (" + id +
                    ") needs either the 'on' attribute or the set of x, y, and z coordinates." );
        }

        GearList.Add( is );

        DEVICELIST.add( this );
    }

    public static Device Select( String type ) {
        for (Device selection : DEVICELIST) {
            if (selection.id.equals( type )) {
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

    public Point location() {
        return place;
    }

    @Override
    public void verify()
            throws FeatureException, InvalidXMLException, LocationException,
            MountingException, ReferenceException
    {
        template = DeviceTemplate.Select( is );
        if( null == template ){
            throw new InvalidXMLException( "Device", id,
                    "'is' reference ("+is+") does not exist" );
        }

        // Get what needs to be drawn from 'is'.
        shape = template.getSolid();

        if( 90.0 == orientation ){
            width  = shape.depth();
            height = shape.width();
        }
        else {
            width  = shape.width();
            height = shape.depth();
        }

        if( "".equals( on ) ) {
            place = new Point( x - width / 2, y - height / 2, z );
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

        layerName = template.layer();

        Layer layer = Layer.List().get( layerName );
        if( null != layer ) {
            color = layer.color();
        }
    }

    @Override
    public void dom(Draw draw, View mode)
            throws MountingException, ReferenceException
    {
        SvgElement group = svgClassGroup( draw, layerName);
        draw.appendRootChild(group);

        SvgElement tableElement = group.rectangle( draw, place.x(), place.y(), width, height, color );
        tableElement.attribute( "fill", color );
        tableElement.attribute( "fill-opacity", "0.1" );

        Double x = place.x() + 3;
        Double y = place.y() + height - 4;
        SvgElement idText = group.text( draw, id, place.x(), y, color );
        idText.attribute( "text-anchor", "left" );
    }

    @Override
    public Point location( Solid shape ) {
        Double ex = x;
        Double wy = y;
        Double ze = z;

        Double lastWidth = 0.0;

        for ( Thing item : thingsOnThis) {
            ex = Math.max( ex, item.point.x().intValue() );
            wy = Math.max( wy, item.point.y() );
            ze = Math.max( ze, item.point.z() );

            lastWidth = item.solid.width();
        }
        Thing thing = new Thing();
        thing.point = new Point( x, wy + lastWidth, z + height );
        thing.solid = shape;

        thingsOnThis.add( thing );

        return thing.point;
    }

    public String toString(){
        return "Device: "+id+", is: "+is+", on: "+on+".";
    }
}
