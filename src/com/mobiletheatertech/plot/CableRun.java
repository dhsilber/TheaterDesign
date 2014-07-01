package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Created by dhs on 1/12/14.
 */
/**
 * Represents a theatrical lighting instrument and includes various information about where it is
 * located and how it is connected.
 * <p/>
 * XML tag is 'CableRun'.
 * <p/>
 * Required attributes are:<dl> <dt>type</dt><dd>references the name of a
 * 'CableRun-definition'</dd> <dt>on</dt><dd>references the name of a pipe, truss, or other object
 * on which can support a CableRun</dd> <dt>location</dt><dd>specifies where on the 'on' item, this
 * is placed</dd> </dl>
 * <p/>
 * Optional attributes are:<dl> <dt>circuit</dt><dd>wiring circuit which powers this</dd>
 * <dt>dimmer</dt><dd>dimmer which powers this circuit</dd> <dt>channel</dt><dd>board channel which
 * controls this dimmer</dd> <dt>color</dt><dd>gel number</dd> <dt>unit</dt><dd>marker to identify
 * this within the set of CableRuns mounted on a given support item</dd> </dl>
 *
 * @author dhs
 * @since 0.0.7
 */
public class CableRun extends MinderDom {

    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     */
    public static final String LAYERNAME = "CableRuns";

    /**
     * Name of {@code Layer} of {@code Luminare}s.
     */
    public static final String LAYERTAG = "CableRun";


    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     *
    public static final String INFOLAYERNAME = "CableRun Information";

    **
     * Name of {@code Layer} of {@code Luminare}s.
     * /
    public static final String INFOLAYERTAG = "CableRuninformation";
*/

    private String type;
    private String on;
    private String location;
    private String circuit;
    private String dimmer;
    private String channel;
    private String color;
    private String unit;
    private String target;
    private Double rotation = 0.0;
    private Point point;
    private Place place;
    private Point origin;
    private Double pipeRotation;
    private String transform;
    private Mountable mount = null;

    /**
     * Construct a {@code CableRun} for each element in a list of XML nodes.
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
                new CableRun(element);
            }

        }
    }

    /**
     * Construct a {@code CableRun} from an XML element.
     *
     * @param element DOM Element defining a CableRun
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public CableRun (Element element) throws AttributeMissingException, InvalidXMLException {
        super(element);

        type = getStringAttribute(element, "type");
        on = getStringAttribute(element, "on");
        location = getStringAttribute(element, "location");
        circuit = getOptionalStringAttribute(element, "circuit");
        dimmer = getOptionalStringAttribute(element, "dimmer");
        channel = getOptionalStringAttribute(element, "channel");
        color = getOptionalStringAttribute(element, "color");
        unit = getOptionalStringAttribute(element, "unit");
        target = getOptionalStringAttribute(element, "target");
        String rotate = getOptionalStringAttribute(element, "rotation");
        if (null != rotate && ! rotate.isEmpty() ) {
            rotation = new Double(rotate);
        }

        new Layer(LAYERNAME, LAYERTAG);
    }

    /**
     * Provide the drawing location of this {@code CableRun}.
     *
     * @return drawing location
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    protected Place location() throws InvalidXMLException, MountingException, ReferenceException {
        mount = Mountable.Select(on);
        if (null == mount) {
            throw new MountingException(
                    "CableRun of type '" + type + "' has unknown mounting: '" + on + "'.");
        }
        Place result;
        try {
            result = mount.rotatedLocation(location);
        } catch (MountingException e) {
            throw new MountingException(
                    "CableRun of type '" + type + "' has location " + location + " which is " +
                            e.getMessage() + " '" + on + "'.");
        }
        return result;
    }

    @Override
    public void verify() throws InvalidXMLException, MountingException, ReferenceException {
        place = location();
        point=place.location();
        origin=place.origin();
        pipeRotation=place.rotation();
        transform = "rotate(" + pipeRotation + "," + origin.x() + "," + origin.y() + ")";
    }

    /**
     * Provide the rotation required to orient this CableRun's icon to the specified point.
     *
     * @param point towards which CableRun icon should be oriented
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
     * Generate SVG DOM for a {@code CableRun}, along with its circuit, dimmer, channel, color, and
     * unit information.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void dom(Draw draw, View mode) throws MountingException, ReferenceException {
        if( View.TRUSS == mode && !Truss.class.isInstance(mount)) {
            return;
        }
        Truss truss = null;

//        System.out.println( "CableRun.dom: About to draw id: "+id+" type: "+type+" on: "+ on+" location: "+location+".");
        Integer x;
        Integer y;

        Integer z = Venue.Height() - point.z();

        Element group = draw.element("g");
        group.setAttribute("class", LAYERTAG);
        if( View.TRUSS != mode ) {
            group.setAttribute("transform", transform);
        }
        draw.appendRootChild(group);
//        System.out.println("CableRun.dom: added group.");

        // use element for CableRun icon
        Element use = draw.element("use");
        use.setAttribute("xlink:href", "#" + type);

        switch (mode) {
            case PLAN:
                use.setAttribute("x", point.x().toString());
                use.setAttribute("y", point.y().toString());
                if (!target.equals("")) {
                    // With this I lose the alignment with zones. :-(
//                    Integer rotation = alignWithZone(point);
//                    String transform =
//                            "rotate(" + rotation + "," + point.x() + "," + point.y() + ")";
                }
                rotation += 180;
                use.setAttribute("transform", "rotate(" + rotation + "," + point.x() + "," + point.y() + ")" );
                break;
            case SECTION:
                use.setAttribute("x", point.y().toString());
                use.setAttribute("y", z.toString());
                break;
            case FRONT:
                use.setAttribute("x", point.x().toString());
                use.setAttribute("y", z.toString());
                break;
            case TRUSS:
                truss = (Truss) mount;
                point = truss.relocate( location );
//                System.out.println("Truss Point: "+point.toString() );
                use.setAttribute("x", point.x().toString());
                use.setAttribute("y", point.y().toString());
//                 rotation += 180;
                use.setAttribute("transform", "rotate(" + rotation + "," + point.x() + "," + point.y() + ")" );
                break;
        }
        group.appendChild(use);
//        System.out.println("CableRun.dom: added use.");

        // There is no good place to display extra information in section and front views.
        switch (mode) {
            case PLAN:
                break;

            case TRUSS:
            case SECTION:
            case FRONT:
                return;
        }
/*
        Element infogroup = draw.element("g");

        infogroup.setAttribute("class", INFOLAYERTAG);
        group.appendChild(infogroup);
        System.out.println("CableRun.dom: added infogroup.");

        Element circuitHexagon = draw.element("path");
//        circuitHexagon.setAttribute("transform", transform);
        circuitHexagon.setAttribute("fill", "none");
        circuitHexagon.setAttribute("stroke", "black");
        circuitHexagon.setAttribute("stroke-width", "1");
        circuitHexagon.setAttribute("width", "18");
        circuitHexagon.setAttribute("height", "12");

        Element dimmerRectangle = draw.element("rect");
//        dimmerRectangle.setAttribute("transform", transform);
        dimmerRectangle.setAttribute("fill", "none");
        dimmerRectangle.setAttribute("height", "11");

        Element channelCircle = draw.element("circle");
//        channelCircle.setAttribute("transform", transform);
        channelCircle.setAttribute("fill", "none");
        channelCircle.setAttribute("r", "8");

        Element circuitText = draw.element("text");
//        circuitText.setAttribute("transform", transform);
        circuitText.setAttribute("fill", "black");
        circuitText.setAttribute("stroke", "none");
        circuitText.setAttribute("font-family", "serif");
        circuitText.setAttribute("font-size", "9");
        circuitText.setAttribute("text-anchor", "middle");

        Element dimmerText = draw.element("text");
//        dimmerText.setAttribute("transform", transform);
        dimmerText.setAttribute("fill", "black");
        dimmerText.setAttribute("stroke", "none");
        dimmerText.setAttribute("font-family", "serif");
        dimmerText.setAttribute("font-size", "9");
        dimmerText.setAttribute("text-anchor", "middle");

        Element channelText = draw.element("text");
//        channelText.setAttribute("transform", transform);
        channelText.setAttribute("fill", "black");
        channelText.setAttribute("fill", "black");
        channelText.setAttribute("font-family", "serif");
        channelText.setAttribute("font-size", "9");
        channelText.setAttribute("text-anchor", "middle");

        Text textCircuit = draw.document().createTextNode(circuit);
        Text textDimmer = draw.document().createTextNode(dimmer);
        Text textChannel = draw.document().createTextNode(channel);

        boolean farSide = (null != truss && truss.farSide( location ) );

        int direction = 1;
        int hexagonYOffset = 0;
        if (View.TRUSS == mode && farSide ) {
            direction = -1;
            hexagonYOffset = 7;
        }

        Integer dimmerTextY;
        Integer dimmerRectangleY;
        Integer channelCircleY;
        Integer channelTextY;
        switch (Venue.Circuiting()) {
            case Venue.ONETOONE:
            case Venue.ONETOMANY:
                dimmerTextY = point.y() - 20 * direction - hexagonYOffset;
                dimmerRectangleY = point.y() - 28 * direction - hexagonYOffset;
                channelCircleY = point.y() - 39 * direction;
                channelTextY = point.y() - 25 * direction;
                break;
            default:
                // Hexagon for circuit
                x = point.x() - 9;
                y = point.y() - 25 * direction - hexagonYOffset;
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
                infogroup.appendChild(circuitHexagon);

                Integer circuitTextX = point.x();
                Integer circuitTextY = point.y() - 17 * direction;
                circuitText.setAttribute("x", circuitTextX.toString());
                circuitText.setAttribute("y", circuitTextY.toString());
                infogroup.appendChild(circuitText);
                circuitText.appendChild(textCircuit);

                dimmerTextY = point.y() - 31 * direction;
                dimmerRectangleY = point.y() - 39 * direction;
                channelCircleY = point.y() - 50 * direction;
                channelTextY = point.y() - 46 * direction;
                break;
        }
        System.out.println( "CableRun.dom: after circuiting switch.");

        // Text for dimmer
        Integer dimmerTextX = point.x();
//        if (3 < textDimmer.getLength()) {
//            dimmerTextX -= 5;
//        }
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
        infogroup.appendChild(dimmerRectangle);

        infogroup.appendChild(dimmerText);
        switch (Venue.Circuiting()) {
            case Venue.ONETOMANY:
                dimmerText.appendChild(textCircuit);
                break;
            case Venue.ONETOONE:
            default:
                dimmerText.appendChild(textDimmer);
                break;
        }
        System.out.println( "CableRun.dom: added dimmer stuff.");

        // Circle and text for channel number
        x = point.x();
        channelCircle.setAttribute("cx", x.toString());
        channelCircle.setAttribute("cy", channelCircleY.toString());
        infogroup.appendChild(channelCircle);

        Integer channelTextX = point.x();
        channelText.setAttribute("x", channelTextX.toString());
        channelText.setAttribute("y", channelTextY.toString());
        infogroup.appendChild(channelText);

        channelText.appendChild(textChannel);
        System.out.println("CableRun.dom: added channel stuff.");


        // Unit number to overlay on icon
        Element unitText = draw.element("text");
//        unitText.setAttribute("transform", transform);
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
        infogroup.appendChild(unitText);

        Text textUnit = draw.document().createTextNode(unit);
        unitText.appendChild(textUnit);
        System.out.println("CableRun.dom: added unit stuff.");


        // Color designation to display
        Element colorText = draw.element("text");
//        colorText.setAttribute("transform", transform);
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
        infogroup.appendChild(colorText);

        Text textColor = draw.document().createTextNode(color);
        colorText.appendChild(textColor);
        System.out.println("CableRun.dom: added color stuff.");
  */
    }

    /**
     * Describe this {@code CableRun}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "CableRun: " + type + ", " + circuit;
    }
}
