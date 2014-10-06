package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 10/6/13 Time: 8:53 PM To change this template use
 * File | Settings | File Templates.
 */

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * A lighting zone.
 * <p/>
 * XML tag is 'zone'. Required attributes are:<dl><dt>id</dt><dd>name with which to reference this
 * zone</dd><dt>x</dt><dd>x-coordinate relative to origin</dd><dt>y</dt><dd>y-coordinate relative to
 * origin</dd><dt>z</dt><dd>z-coordinate relative to origin</dd><dt>r</dt><dd>radius of circle
 * marking zone</dd></dl>. Optional attribute is <dl><dt>color</dt><dd>color in which to draw this
 * zone</dd></dl>
 *
 * @author dhs
 * @since 0.0.15
 */
public class Zone extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Zone}s.
     */
    public static final String LAYERNAME = "Zones";

    /**
     * Name of {@code Layer} of {@code Zone}s.
     */
    public static final String LAYERTAG = "zone";

    private Integer x;
    private Integer y;
    //    private Integer z;
    private Integer r;

    private Integer xDraw;
    private Integer yDraw;
    //    private Integer zDraw;
//    private Integer rDraw;
    private String color;

    /**
     * Construct a {@code Zone} for each element in a list of XML nodes.
     *
     * @param list of XML nodes
     * @throws AttributeMissingException if a required attribute is missing
     * @throws InvalidXMLException       if null element is somehow presented to constructor
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML(NodeList list)
            throws AttributeMissingException, InvalidXMLException/*, LocationException, SizeException*/ {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item(index);

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Zone(element);
            }
        }
    }

    /**
     * Find a specific {@code Zone} in the set of plot objects.
     *
     * @param id Name of {@code Zone} to find
     * @return {@code Zone} which matches specified {@code id}.
     */
    public static Zone Find(String id) {

        for (MinderDom thingy : Drawable.List()) {
            if (Zone.class.isInstance(thingy)) {
                if (((Zone) thingy).id.equals(id)) {
                    return (Zone) thingy;
                }
            }
        }
        return null;
    }

    /**
     * Construct a {@code Zone} from an XML element.
     *
     * @param element DOM Element defining a zone
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Zone(Element element) throws AttributeMissingException, InvalidXMLException {
        super(element);

        id = getStringAttribute(element, "id");
        x = getIntegerAttribute(element, "x");
        y = getIntegerAttribute(element, "y");
//        z=getIntegerAttribute( element, "z" );
        r = getIntegerAttribute(element, "r");
        color = getOptionalStringAttribute(element, "color");
        if (color.equals("")) {
            color = "teal";
        }

        new Layer(LAYERNAME, LAYERTAG);
    }

    /**
     * Provide the x coordinate corrected for the drawing area
     *
     * @return x drawing coordinate
     */
    public Integer drawX() {
        return xDraw;
    }

    /**
     * Provide the y coordinate corrected for the drawing area
     *
     * @return y drawing coordinate
     */
    public Integer drawY() {
        return yDraw;
    }

    /**
     * Compute the drawing coordinates for a zone. (They are different, depending on whether a
     * proscenium has been defined.)
     */
    @Override
    public void verify() /*throws FeatureException, InvalidXMLException, LocationException*/ {
        if (Proscenium.Active()) {
            Point point = Proscenium.Locate(new Point(x, y, 48));
            xDraw = point.x();
            yDraw = point.y();
        } else {
            xDraw = x;
            yDraw = y;
        }
    }

//    @Override
//    public void drawPlan( Graphics2D canvas ) throws MountingException {
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) throws MountingException {
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) throws MountingException {
//    }

    /**
     * Draw a circle with a textual label to represent the zone.
     *
     * @param draw canvas/DOM manager
     * @param mode drawing mode
     * @throws MountingException if mounting location cannot be established
     */
    @Override
    public void dom(Draw draw, View mode) throws MountingException {
        if (View.PLAN != mode) {
            return;
        }

        Element group = draw.element("g");
        group.setAttribute("class", LAYERTAG);
        draw.appendRootChild(group);

        Element circle = draw.element("circle");
//        channelCircle.setAttribute( "fill", "none" );
        circle.setAttribute("cx", xDraw.toString());
        circle.setAttribute("cy", yDraw.toString());
        circle.setAttribute("r", r.toString());
        circle.setAttribute("fill", "none");
        circle.setAttribute("stroke", color);
        circle.setAttribute("stroke-opacity", "0.5");
        circle.setAttribute("stroke-width", "1");

        group.appendChild(circle);


        Element text = draw.element("text");
        Integer textX = xDraw - (r / 5);
        Integer textY = yDraw + (r / 5);
        text.setAttribute("x", textX.toString());
        text.setAttribute("y", textY.toString());
        text.setAttribute("fill", color);
        text.setAttribute("stroke", "none");
        text.setAttribute("fill-opacity", "0.4");
        text.setAttribute("font-family", "serif");
        text.setAttribute("font-size", "22");
        group.appendChild(text);

        Text textNode = draw.document().createTextNode(id);
        text.appendChild(textNode);
    }
}
