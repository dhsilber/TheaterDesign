package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Stack;

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
    private Stackable surface = null;

    Solid shape = null;
    Point place = null;
    String layer = null;

    private static final String COLOR = "black";
    private static final String FILLCOLOR = "grey";


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

    public static Device Select( String type ) {
        for (Device selection : DEVICELIST) {
            if (selection.id.equals( type )) {
                return selection;
            }
        }
        return null;
    }

    public String layer() {
        return layer;
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

        surface = Stackable.Select(on);
        if( null == surface ){
            throw new InvalidXMLException( "Device", id,
                    "'on' reference ("+on+") does not exist" );
        }

        // Get what needs to be drawn from 'is'.
        shape = template.getSolid();

        // Give the what to 'on' to find out where
        place = surface.location( shape );

        layer = template.layer();
    }

    @Override
    public void dom(Draw draw, View mode)
            throws MountingException, ReferenceException
    {
        SvgElement group = svgClassGroup( draw, layer );
//        draw.element("g");
//        group.setAttribute("class", layer);
        draw.appendRootChild(group);

        Integer width = shape.getWidth().intValue();
        Integer height = shape.getDepth().intValue();
        SvgElement tableElement = group.rectangle( draw, place.x(), place.y(), width, height, COLOR );
//        draw.element("rect");
//        tableElement.setAttribute("x", place.x().toString());
//        tableElement.setAttribute("y", place.y().toString());
//        tableElement.setAttribute("width", shape.getWidth().toString());
//        // Plot attribute is 'depth'. SVG attribute is 'height'.
//        tableElement.setAttribute("height", shape.getDepth().toString());
        tableElement.attribute("fill", FILLCOLOR );
//        tableElement.setAttribute("stroke", "black");

//        group.appendChild(tableElement);


        Integer y = place.y() + shape.getDepth().intValue() + 9;
        SvgElement idText = group.text( draw, id, place.x(), y, COLOR );
//        draw.element("text");
//        idText.setAttribute( "x", place.x().toString() );
//        idText.setAttribute( "y", y.toString() );
//        idText.setAttribute( "fill", "black" );
//        idText.setAttribute( "stroke", "none" );
//        idText.setAttribute( "font-family", "sans-serif" );
//        idText.setAttribute( "font-weight", "100" );
//        idText.setAttribute( "font-size", "7pt" );
        idText.attribute( "text-anchor", "left" );

//        group.appendChild( idText );


//        Text textId = draw.document().createTextNode( id );
//        idText.appendChild( textId );

    }

    public String toString(){
        return "Device: "+id+", is: "+is+", on: "+on+".";
    }
}
