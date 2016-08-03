package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Provides a generic item that can describe a real-world device.
 *
 * Abstract superclass for all of the represented types of components.
 * <p>
 * The <code>*Template</code> classes each describe a prototype for devices
 * of the associated type.  The various subclasses keep track of details
 * unique to types of modeled devices.
 * <p>
 * <code>DeviceTemplate</code> has an ArrayList of Ports.
 * It is the responsibility of the extending constructor to populate that array.
 *
 * @author David H. Silber

 */
public class DeviceTemplate extends Layerer implements Legendable
{
    private static ArrayList<DeviceTemplate> DEVICELIST = new ArrayList<>();

    /*  TODO We will (later) want some means of telling which connectors are grouped together at each end of a cable.
     *
     *  Rather than separately specifying which places a device
     *  should have Connectors with different classes of Templates,
     *  keep an array of Connectors.  Or more likely, some
     *  structure which includes Connectors, but also
     *  describes how a connection is to be used.
     *
     *  Each potential connection would need
     *   - Type of connection (e.g. power, line-level audio, DMX output, etc)
     *   - Is the connection required?
     *     - More difficult would be the case where one of a set of connections
     *       must be used. (E.g. for a mixer, some input and some output must
     *       be wired up, or the device makes no sense.  But it need not be any
     *       specific input and output.)
     *
     * I think I will still need different types of DeviceTemplates:
     *  - Adaptor: a collection of connections.
     *  - Cable: a pair of collections of connections, with some amount of length
     *    between them.
     *  - N-way cable: N sets of collections of connectors, each on some length of
     *    wire.  Probably want to specify one point from which the cables originate
     *    in order to be able to figure lengths for various Y & breakout cables.
     *
     * ... and those are just the things that move current or signal from one
     * point to another.  Things that modify the current or signal are not yet
     * even discussed.
     *
     */
//     String type;
//    ArrayList<Port> ports;
    private Solid solid;

    private Double width = null;
    private Double depth = null;
    private Double height = null;
    private String layer = null;

    String color = "black";

    Integer count = 0;

    /**
     * Handle the common parts of constructing the various types of
     * <code>*Template</code>.
     *
     * Implementations must create a <code>Solid</code>.
     *
     * @param type String identifying this type of device.
     */
//    protected DeviceTemplate( String type, Solid solid )
//    {
//        this.type = type;
////        ports = new ArrayList<Port>();
//
//        this.solid = solid;
//    }

    public static DeviceTemplate Select( String type ) {
        for ( DeviceTemplate selection : DEVICELIST ) {
            if (selection.id.equals( type )) {
                return selection;
            }
        }
        return null;
    }

    public DeviceTemplate( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException
    {
        super( element );
        id = getStringAttribute( "type" );
        width = getPositiveDoubleAttribute( "width");
        depth = getPositiveDoubleAttribute( "depth");
        height = getPositiveDoubleAttribute( "height");
        layer = getOptionalStringAttributeOrNull( "layer" );
//        ports = new ArrayList<Port>();

        this.solid = new Solid( width, depth, height );

        DEVICELIST.add( this );

        Legend.Register( this, 2.0, 7.0, LegendOrder.Device );

        if ( null != layer ) {
            Layer layerActual = Layer.Register( layer, layer );
            layerActual.register( this );
        }
    }

    public static void CountReset()  {
        for (DeviceTemplate selection : DEVICELIST) {
            selection.legendCountReset();
        }
    }

    @Override
    public void verify() { }

    public void count() {
        count++;
    }

    public Solid getSolid()
    {
        return solid;
    }

    public String layer() { return layer; }

//    public String getName()
//    {
//        return name;
//    }

    @Override
    public void legendCountReset() {
        count = 0;
    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        System.err.println( "Count from withing DeviceTemplate: " + count );
        if (0 >= count) { return start; }

        Layer layerInstance = Layer.List().get( layer );
        if( null != layerInstance ) {
            color = layerInstance.color();
        }

        SvgElement box = draw.rectangleAbsolute(draw, start.x(), start.y(), 30.0, 7.0, color);
        box.attribute( "fill", color );
        box.attribute( "fill-opacity", "0.1" );

        Double x = start.x() + Legend.TEXTOFFSET;
        Double y = start.y() + 7;
        draw.textAbsolute(draw, id, x, y, Legend.TEXTCOLOR);

        x = start.x() + Legend.QUANTITYOFFSET;
        draw.textAbsolute(draw, count.toString(), x, y, Legend.TEXTCOLOR);

        PagePoint finish = new PagePoint( start.x(), start.y() + 7 );
        return finish;
    }

    public String toString() {
        return "DeviceTemplate: "+id+".";
    }
}
