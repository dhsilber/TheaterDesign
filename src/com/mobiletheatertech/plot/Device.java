package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Provides a generic item that can describe a real-world device.
 *
 * Created by dhs on 7/2/14.
 */
public class Device extends MinderDom
{
    private static ArrayList<Device> DEVICELIST = new ArrayList<>();

    private String is;
    private String on;

    private DeviceTemplate template = null;
    private Table surface = null;

    /**
     * Construct a {@code Box} for each element in a list of XML nodes.
     *
     * @param list of XML nodes
     * @throws AttributeMissingException if a required attribute is missing
     * @throws LocationException         if the stage is outside the {@code Venue}
     * @throws SizeException             if a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML(NodeList list)
            throws AttributeMissingException, InvalidXMLException/*, LocationException, SizeException*/ {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item(index);

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Device(element);
            }
        }
    }

    public static Device Select( String type ) {
        for (Device selection : DEVICELIST) {
            if (selection.id.equals( type )) {
                return selection;
            }
        }
        return null;
    }

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
        on = getStringAttribute(element, "on");

        GearList.Add( is );

        DEVICELIST.add( this );
    }

    @Override
    public void verify()
            throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {
        template = DeviceTemplate.Select( is );
        if( null == template ){
            throw new InvalidXMLException( "Box", id,
                    "'is' reference ("+is+") does not exist" );
        }

        surface = Table.Select( on );
        if( null == surface ){
            throw new InvalidXMLException( "Box", id,
                    "'on' reference ("+on+") does not exist" );
        }
    }

    @Override
    public void dom(Draw draw, View mode)
            throws MountingException, ReferenceException {

        // Get what needs to be drawn from 'is'.
        Solid shape = template.getSolid();

        // Give the what to 'on' to find out where
//        Table surface =
        Point place = surface.location( shape );

        Element group = draw.element("g");
        group.setAttribute("class", template.layer() );
        draw.appendRootChild(group);

        Element tableElement = draw.element("rect");
//        tableElement.setAttribute("class", LAYERTAG);
        tableElement.setAttribute("x", place.x().toString());
        tableElement.setAttribute("y", place.y().toString());
        tableElement.setAttribute("width", shape.getWidth().toString());
//        // Plot attribute is 'depth'. SVG attribute is 'height'.
        tableElement.setAttribute("height", shape.getDepth().toString());
        tableElement.setAttribute("fill", "grey");
        tableElement.setAttribute("stroke", "black");

        group.appendChild(tableElement);


        Integer y = place.y() + shape.getDepth().intValue() + 9;
        Element idText = draw.element( "text" );
        idText.setAttribute( "x", place.x().toString() );
        idText.setAttribute( "y", y.toString() );
        idText.setAttribute( "fill", "black" );
        idText.setAttribute( "stroke", "none" );
        idText.setAttribute( "font-family", "sans-serif" );
        idText.setAttribute( "font-weight", "100" );
        idText.setAttribute( "font-size", "7pt" );
        idText.setAttribute( "text-anchor", "left" );

        group.appendChild( idText );


        Text textId = draw.document().createTextNode( id );
        idText.appendChild( textId );

    }

    public String toString(){
        return "Device: "+id+", is: "+is+", on: "+on+".";
    }
}
