package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
//import java.awt.geom.Line2D;

/**
 * Represents a theatrical lighting instrument and includes various information about where it is
 * located and how it is connected.
 * <p/>
 * XML tag is 'luminaire'.
 * <p/>
 * Required attributes are:<dl> <dt>type</dt><dd>references the name of a
 * 'luminaire-definition'</dd> <dt>on</dt><dd>references the name of a pipe, truss, or other object
 * on which can support a luminaire</dd> <dt>location</dt><dd>specifies where on the 'on' item, this
 * is placed</dd> </dl>
 * <p/>
 * Optional attributes are:<dl> <dt>circuit</dt><dd>wiring circuit which powers this</dd>
 * <dt>dimmer</dt><dd>dimmer which powers this circuit</dd> <dt>channel</dt><dd>board channel which
 * controls this dimmer</dd> <dt>color</dt><dd>gel number</dd> <dt>unit</dt><dd>marker to identify
 * this within the set of luminaires mounted on a given support item</dd> </dl>
 *
 * @author dhs
 * @since 0.0.7
 */
public class Luminaire extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     */
    public static final String LAYERNAME = "Luminaires";

    /**
     * Name of {@code Layer} of {@code Luminare}s.
     */
    public static final String LAYERTAG = "luminaire";

    private String type;
    private String on;
    private Integer location;
    private String circuit;
    private String dimmer;
    private String channel;
    private String color;
    private String unit;
    private String target;

    /**
     * Construct a {@code Luminaire} for each element in a list of XML nodes.
     *
     * @param list of XML nodes
     * @throws AttributeMissingException if a required attribute is missing
     * @throws LocationException         if the stage is outside the {@code Venue}
     * @throws SizeException             if a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML(NodeList list)
            throws AttributeMissingException, InvalidXMLException, LocationException, SizeException {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item(index);

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Luminaire(element);
            }

        }
    }

    /**
     * Construct a {@code Luminaire} from an XML element.
     *
     * @param element DOM Element defining a luminaire
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Luminaire(Element element) throws AttributeMissingException, InvalidXMLException {
        super(element);

        type = getStringAttribute(element, "type");
        on = getStringAttribute(element, "on");
        location = getIntegerAttribute(element, "location");
        circuit = getOptionalStringAttribute(element, "circuit");
        dimmer = getOptionalStringAttribute(element, "dimmer");
        channel = getOptionalStringAttribute(element, "channel");
        color = getOptionalStringAttribute(element, "color");
        unit = getOptionalStringAttribute(element, "unit");
        target = getOptionalStringAttribute(element, "target");

        new Layer(LAYERNAME, LAYERTAG);
    }

    /**
     * Provide the drawing location of this {@code Luminaire}.
     *
     * @return drawing location
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    protected Point location() throws MountingException {
        Pipe mount = Pipe.Select(on);
        if (null == mount) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has unknown mounting: '" + on + "'.");
        }
        Point result;
        try {
            result = mount.location(location);
        } catch (MountingException e) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has location " + location + " which is " +
                            e.getMessage() + " '" + on + "'.");
        }
        return result;
    }

    @Override
    public void verify() {
    }

    /**
     * Provide the rotation required to orient this luminaire's icon to the specified point.
     *
     * @param point towards which luminaire icon should be oriented
     * @return angle
     */
    private Integer alignWithZone(Point point) {
        Zone zone = Zone.Find(target);
        if (null == zone) {
            return 0;
        }
        int oppositeLength = point.y() - zone.drawY();
        int adjacentLength = point.x() - zone.drawX();
        Double angle = Math.atan2(oppositeLength, adjacentLength);
        angle = Math.toDegrees(angle);

//        System.err.println(
//                "Angle: " + angle + "  al: " + adjacentLength + "  oL: " + oppositeLength );
        return angle.intValue() + 90;
    }


    /**
     * Generate SVG DOM for a {@code Luminaire}, along with its circuit, dimmer, channel, color, and
     * unit information.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        Point point = location();

        Integer x;
        Integer y;
        Integer z = Venue.Height() - point.z();

        Element group = draw.element("g");
        group.setAttribute("class", LAYERTAG);
        draw.appendRootChild(group);

        // use element for luminaire icon
        Element use = draw.element("use");
        use.setAttribute("xlink:href", "#" + type);

        switch (mode) {
            case PLAN:
                use.setAttribute("x", point.x().toString());
                use.setAttribute("y", point.y().toString());
                if (!target.equals("")) {
                    Integer rotation = alignWithZone(point);
                    String transform =
                            "rotate(" + rotation + "," + point.x() + "," + point.y() + ")";
                    use.setAttribute("transform", transform);
                }
                break;
            case SECTION:
                use.setAttribute("x", point.y().toString());
                use.setAttribute("y", z.toString());
                break;
            case FRONT:
                use.setAttribute("x", point.x().toString());
                use.setAttribute("y", z.toString());
                break;
        }
        group.appendChild(use);

        // There is no good place to display extra information in section and front views.
        switch (mode) {
            case PLAN:
                break;
            case SECTION:
            case FRONT:
                return;
        }

        Element circuitHexagon = draw.element("path");
        circuitHexagon.setAttribute("fill", "none");
        circuitHexagon.setAttribute("stroke", "black");
        circuitHexagon.setAttribute("stroke-width", "1");
        circuitHexagon.setAttribute("width", "18");
        circuitHexagon.setAttribute("height", "12");

        Element dimmerRectangle = draw.element("rect");
        dimmerRectangle.setAttribute("fill", "none");
        dimmerRectangle.setAttribute("height", "11");

        Element channelCircle = draw.element("circle");
        channelCircle.setAttribute("fill", "none");
        channelCircle.setAttribute("r", "8");

        Element circuitText = draw.element("text");
        circuitText.setAttribute("fill", "black");
        circuitText.setAttribute("stroke", "none");
        circuitText.setAttribute("font-family", "serif");
        circuitText.setAttribute("font-size", "9");
        circuitText.setAttribute("text-anchor", "middle");

        Element dimmerText = draw.element("text");
        dimmerText.setAttribute("fill", "black");
        dimmerText.setAttribute("stroke", "none");
        dimmerText.setAttribute("font-family", "serif");
        dimmerText.setAttribute("font-size", "9");
        dimmerText.setAttribute("text-anchor", "middle");

        Element channelText = draw.element("text");
        channelText.setAttribute("fill", "black");
        channelText.setAttribute("fill", "black");
        channelText.setAttribute("font-family", "serif");
        channelText.setAttribute("font-size", "9");
        channelText.setAttribute("text-anchor", "middle");

        Text textCircuit = draw.document().createTextNode(circuit);
        Text textDimmer = draw.document().createTextNode(dimmer);
        Text textChannel = draw.document().createTextNode(channel);

        Integer dimmerTextY;
        Integer dimmerRectangleY;
        Integer channelCircleY;
        Integer channelTextY;
        switch (Venue.Circuiting()) {
            case Venue.ONETOONE:
            case Venue.ONETOMANY:
                dimmerTextY = point.y() - 20;
                dimmerRectangleY = point.y() - 28;
                channelCircleY = point.y() - 39;
                channelTextY = point.y() - 25;
                break;
            default:
                // Hexagon for circuit
                x = point.x() - 9;
                y = point.y() - 25;
                circuitHexagon.setAttribute("d",
                        "M " + (x + 1) + " " + (y + 5) +
                                " L " + (x + 4) + " " + y +
                                " L " + (x + 14) + " " + y +
                                " L " + (x + 17) + " " + (y + 5) +
                                " L " + (x + 14) + " " + (y + 10) +
                                " L " + (x + 4) + " " + (y + 10) +
                                " Z");
                circuitHexagon.setAttribute("x", x.toString());
                circuitHexagon.setAttribute("y", y.toString());
                group.appendChild(circuitHexagon);

                Integer circuitTextX = point.x();
                Integer circuitTextY = point.y() - 17;
                circuitText.setAttribute("x", circuitTextX.toString());
                circuitText.setAttribute("y", circuitTextY.toString());
                draw.appendRootChild(circuitText);
                circuitText.appendChild(textCircuit);

                dimmerTextY = point.y() - 31;
                dimmerRectangleY = point.y() - 39;
                channelCircleY = point.y() - 50;
                channelTextY = point.y() - 46;
                break;
        }

        // Text for dimmer
        Integer dimmerTextX = point.x();
        if (3 < textDimmer.getLength()) {
            dimmerTextX -= 5;
        }
        dimmerText.setAttribute("x", dimmerTextX.toString());
        dimmerText.setAttribute("y", dimmerTextY.toString());

        // Rectangle for dimmer
        x = point.x() - 9;
        Integer width = 18;
        if (3 < textDimmer.getLength()) {
            width = 30;
            x -= 4;
        }

        dimmerRectangle.setAttribute("x", x.toString());
        dimmerRectangle.setAttribute("y", dimmerRectangleY.toString());
        dimmerRectangle.setAttribute("width", width.toString());
        group.appendChild(dimmerRectangle);

        group.appendChild(dimmerText);
        switch (Venue.Circuiting()) {
            case Venue.ONETOMANY:
                dimmerText.appendChild(textCircuit);
                break;
            case Venue.ONETOONE:
            default:
                dimmerText.appendChild(textDimmer);
                break;
        }

        // Circle and text for channel number
        x = point.x();
        channelCircle.setAttribute("cx", x.toString());
        channelCircle.setAttribute("cy", channelCircleY.toString());
        group.appendChild(channelCircle);

        Integer channelTextX = point.x();
        channelText.setAttribute("x", channelTextX.toString());
        channelText.setAttribute("y", channelTextY.toString());
        group.appendChild(channelText);

        channelText.appendChild(textChannel);


        // Unit number to overlay on icon
        Element unitText = draw.element("text");
        Integer unitTextX = point.x();
        Integer unitTextY = point.y() + 0;
        unitText.setAttribute("x", unitTextX.toString());
        unitText.setAttribute("y", unitTextY.toString());
        unitText.setAttribute("fill", "black");
        unitText.setAttribute("stroke", "none");
        unitText.setAttribute("font-family", "sans-serif");
        unitText.setAttribute("font-weight", "100");
        unitText.setAttribute("font-size", "6");
        unitText.setAttribute("text-anchor", "middle");
        group.appendChild(unitText);

        Text textUnit = draw.document().createTextNode(unit);
        unitText.appendChild(textUnit);


        // Color designation to display
        Element colorText = draw.element("text");
        Integer colorTextX = point.x();
        Integer colorTextY = point.y() + 18;
        colorText.setAttribute("x", colorTextX.toString());
        colorText.setAttribute("y", colorTextY.toString());
        colorText.setAttribute("fill", "black");
        colorText.setAttribute("stroke", "none");
        colorText.setAttribute("font-family", "sans-serif");
        colorText.setAttribute("font-weight", "100");
        colorText.setAttribute("font-size", "5");
        colorText.setAttribute("stroke-width", "1px");
        colorText.setAttribute("text-anchor", "middle");
        group.appendChild(colorText);

        Text textColor = draw.document().createTextNode(color);
        colorText.appendChild(textColor);
    }

    /**
     * Describe this {@code Luminaire}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "Luminaire: " + type + ", " + circuit;
    }
}
