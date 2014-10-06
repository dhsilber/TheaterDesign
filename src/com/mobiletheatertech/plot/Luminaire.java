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


    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     */
    public static final String INFOLAYERNAME = "Luminaire Information";

    /**
     * Name of {@code Layer} of {@code Luminare}s.
     */
    public static final String INFOLAYERTAG = "luminaireinformation";

    private String type;
    private String on;
    private String location;
    private String circuit;
    private String dimmer;
    private String channel;
    private String color;
    private String unit;
    private String target;
    private String info;
    private String address;
    private Double rotation = 0.0;
    private Point point;
    private Place place;
    private Point origin;
    private Double pipeRotation;
    private String transform;
    private Mountable mount = null;

    private static final String COLOR = "black";

//    /**
//     * Construct a {@code Luminaire} for each element in a list of XML nodes.
//     *
//     * @param list of XML nodes
//     * @throws AttributeMissingException if a required attribute is missing
//     * @throws LocationException         if the stage is outside the {@code Venue}
//     * @throws SizeException             if a length attribute is too short
//     */
//
//    // This seems to be generic - refactor it into Minder
//    public static void ParseXML(NodeList list)
//            throws AttributeMissingException, DataException,
//            InvalidXMLException, LocationException, SizeException
//    {
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item(index);
//
//            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element) node;
//                new Luminaire(element);
//            }
//
//        }
//    }

    /**
     * Construct a {@code Luminaire} from an XML element.
     *
     * @param element DOM Element defining a luminaire
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Luminaire(Element element)
            throws AttributeMissingException, DataException,
            InvalidXMLException
    {
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
        info = getOptionalStringAttribute( element, "info" );
        address = getOptionalStringAttribute( element, "address" );
        String rotate = getOptionalStringAttribute(element, "rotation");
        if (null != rotate && ! rotate.isEmpty() ) {
            rotation = new Double(rotate);
        }

        new Layer(LAYERTAG, LAYERNAME);
        new Layer(INFOLAYERTAG, INFOLAYERNAME);

        GearList.Add(type);
    }

    /**
     * Provide the drawing location of this {@code Luminaire}.
     *
     * @return drawing location
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    protected Place location() throws InvalidXMLException, MountingException, ReferenceException {
        mount = Mountable.Select(on);
        if (null == mount) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has unknown mounting: '" + on + "'.");
        }
        Place result;
        try {
            result = mount.rotatedLocation(location);
        } catch (MountingException e) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has location " + location + " which is " +
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
//        Integer transformY = origin.y() + SvgElement.OffsetY();
//        transform = "rotate(" + pipeRotation + "," + origin.x() + "," + transformY + ")";
        transform = "rotate(" + pipeRotation + "," + origin.x() + "," + origin.y() + ")";
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
        if( View.TRUSS == mode && !Truss.class.isInstance(mount)) {
            return;
        }
        Truss truss = null;

//        System.out.println( "Luminaire.dom: About to draw id: "+id+" type: "+type+" on: "+ on+" location: "+location+".");
        Integer x;
        Integer y;

        Integer z = Venue.Height() - point.z();

        SvgElement group = svgClassGroup( draw, LAYERTAG );
//        draw.element("g");
//        group.setAttribute("class", LAYERTAG);
        if( View.TRUSS != mode ) {
            group.attribute( "transform", transform );
        }
        draw.appendRootChild(group);
//        System.out.println("Luminaire.dom: added group.");

        // use element for luminaire icon
        SvgElement use = null;
//        draw.element("use");
//        use.setAttribute("xlink:href", "#" + type);

        switch (mode) {
            case PLAN:
                use = group.use( draw, type, point.x(), point.y() );
//                use.setAttribute("x", point.x().toString());
//                use.setAttribute("y", point.y().toString());

                // This transform is to orient the luminaire.
                // See verify() for the transform that rotates the position of the luminaire to
                // keep it with a truss that has been rotated.
                String transform;
                Integer transformX = point.x() + SvgElement.OffsetX();
                Integer transformY = point.y() + SvgElement.OffsetY();
                if ( !target.equals("") ) {
                    // With this I lose the alignment with zones. :-(
                    Integer rotation = alignWithZone(point);
                    transform = "rotate(" + rotation + "," + transformX + "," + transformY + ")";
//                    transform = "rotate(" + rotation + "," + point.x() + "," + point.y() + ")";
                }
                else {
                    rotation += 180;
                    transform = "rotate(" + rotation + "," + transformX + "," + transformY + ")";
//                    transform = "rotate(" + rotation + "," + point.x() + "," + point.y() + ")";
                }
//                use.setAttribute("transform", "rotate(" + rotation + "," + point.x() + "," + point.y() + ")" );
System.out.println( "In Luminaire.dom(), for " + type + " transform: " + transform );
                use.attribute("transform", transform );

                break;
            case SECTION:
                use = group.use( draw, type, point.y(), z );
//                use.setAttribute("x", point.y().toString());
//                use.setAttribute("y", z.toString());
                break;
            case FRONT:
                use = group.use( draw, type, point.x(), z );
//                use.setAttribute("x", point.x().toString());
//                use.setAttribute("y", z.toString());
                break;
            case TRUSS:
                truss = (Truss) mount;
                point = truss.relocate( location );
                use = group.use( draw, type, point.x(), point.y() );
//                System.out.println("Truss Point: "+point.toString() );
//                use.setAttribute("x", point.x().toString());
//                use.setAttribute("y", point.y().toString());
////                 rotation += 180;
                use.attribute("transform", "rotate(" + rotation + "," + point.x() + "," + point.y() + ")" );
                break;
        }
//        group.appendChild(use);
//        System.out.println("Luminaire.dom: added use.");

        // There is no good place to display extra information in section and front views.
        switch (mode) {
            case PLAN:
            case TRUSS:
                break;

            case SECTION:
            case FRONT:
                return;
        }

//        SvgElement infogroup = svgClassGroup( draw, INFOLAYERTAG );
////        draw.element("g");
//
////        infogroup.setAttribute("class", INFOLAYERTAG);
//        group.appendChild(infogroup);
//        System.out.println("Luminaire.dom: added infogroup.");

//        SvgElement circuitHexagon = draw.element("path");
////        circuitHexagon.setAttribute("transform", transform);
//        circuitHexagon.setAttribute("fill", "none");
//        circuitHexagon.setAttribute("stroke", "black");
//        circuitHexagon.setAttribute("stroke-width", "1");
//        circuitHexagon.setAttribute("width", "18");
//        circuitHexagon.setAttribute("height", "12");

//        SvgElement dimmerRectangle = draw.element("rect");
////        dimmerRectangle.setAttribute("transform", transform);
//        dimmerRectangle.setAttribute("fill", "none");
//        dimmerRectangle.setAttribute("height", "11");

//        SvgElement channelCircle = draw.element("circle");
////        channelCircle.setAttribute("transform", transform);
//        channelCircle.setAttribute("fill", "none");
//        channelCircle.setAttribute("r", "8");

//        SvgElement circuitText = draw.element("text");
////        circuitText.setAttribute("transform", transform);
//        circuitText.setAttribute("fill", "black");
//        circuitText.setAttribute("stroke", "none");
//        circuitText.setAttribute("font-family", "serif");
//        circuitText.setAttribute("font-size", "9");
//        circuitText.setAttribute("text-anchor", "middle");

//        SvgElement dimmerText = draw.element("text");
////        dimmerText.setAttribute("transform", transform);
//        dimmerText.setAttribute("fill", "black");
//        dimmerText.setAttribute("stroke", "none");
//        dimmerText.setAttribute("font-family", "serif");
//        dimmerText.setAttribute("font-size", "9");
//        dimmerText.setAttribute("text-anchor", "middle");

//        SvgElement channelText = draw.element("text");
////        channelText.setAttribute("transform", transform);
//        channelText.setAttribute("fill", "black");
//        channelText.setAttribute("fill", "black");
//        channelText.setAttribute("font-family", "serif");
//        channelText.setAttribute("font-size", "9");
//        channelText.setAttribute("text-anchor", "middle");

//        Text textCircuit = draw.document().createTextNode(circuit);
//        Text textDimmer = draw.document().createTextNode(dimmer);
//        Text textChannel = draw.document().createTextNode(channel);

        boolean farSide = (null != truss && truss.farSide( location ) );

        int direction = 1;
        int hexagonYOffset = 0;
        int rectangleYOffset = 0;
        int circleYOffset = 0;
        int dimmerTextYOffset = 0;
        int channelTextYOffset = 0;
        if (View.TRUSS == mode && farSide ) {
            direction = -1;
            hexagonYOffset = 7;
            rectangleYOffset = 12;
            dimmerTextYOffset = -4;
            circleYOffset = 6;
        }

        Integer dimmerTextY;
        Integer dimmerRectangleY;
        Integer channelCircleY;
        Integer channelTextY;
        Integer dimmerTextX;
        Integer circuitTextX=0;
        Integer circuitTextY=0;
        Integer dimmerRectangleWidth;
        Integer unitTextX;
        Integer unitTextY;
        Integer colorTextX;
        Integer colorTextY;
        Integer xColorText = 0;
        Integer yColor = 0;
        Integer yDimmerRectangle = 0;
        Integer xDimmerRectangle = 0;
        Integer yDimmerText = 0;
        Integer addressOvalRadiusY;
        Integer xAddressOval;
        Integer yAddressOval;
        Integer addressTextY;
        String circuitPath="";

        switch (Venue.Circuiting()) {
            case Venue.ONETOONE:
            case Venue.ONETOMANY:
                dimmerTextY = point.y() - 20 * direction - hexagonYOffset - dimmerTextYOffset;
                dimmerRectangleY = point.y() - 28 * direction - hexagonYOffset - rectangleYOffset;
                channelCircleY = point.y() - 39 * direction - circleYOffset;
                channelTextY = point.y() - 36 * direction - channelTextYOffset;
                break;
            default:
                // Hexagon for circuit
                x = point.x() - 9;
                y = point.y() - 25 * direction - hexagonYOffset;
//                circuitHexagon.setAttribute("d",
//                        "M " + (x + 1) + " " + (y + 5) +
//                                " L " + (x + 4) + " " + y +
//                                " L " + (x + 14) + " " + y +
//                                " L " + (x + 17) + " " + (y + 5) +
//                                " L " + (x + 14) + " " + (y + 10) +
//                                " L " + (x + 4) + " " + (y + 10) +
//                                " Z");
                circuitPath = "M " + (x + 1) + " " + (y + 5) +
                            " L " + (x + 4) + " " + y +
                            " L " + (x + 14) + " " + y +
                            " L " + (x + 17) + " " + (y + 5) +
                            " L " + (x + 14) + " " + (y + 10) +
                            " L " + (x + 4) + " " + (y + 10) +
                            " Z";
//                circuitHexagon.setAttribute("x", x.toString());
//                circuitHexagon.setAttribute("y", y.toString());
//                infogroup.appendChild(circuitHexagon);

                circuitTextX = point.x();
                circuitTextY = point.y() - 17 * direction;
//                circuitText.setAttribute("x", circuitTextX.toString());
//                circuitText.setAttribute("y", circuitTextY.toString());
//                infogroup.appendChild(circuitText);
//                circuitText.appendChild(textCircuit);

                dimmerTextY = point.y() - 31 * direction;
                dimmerRectangleY = point.y() - 39 * direction;
                channelCircleY = point.y() - 50 * direction;
                channelTextY = point.y() - 46 * direction;
                break;
        }
//        System.out.println( "Luminaire.dom: after circuiting switch.");

       // Text for dimmer
        dimmerTextX = point.x();
//        if (3 < textDimmer.getLength()) {
//            dimmerTextX -= 5;
//        }
//        dimmerText.setAttribute("x", dimmerTextX.toString());
//        dimmerText.setAttribute("y", dimmerTextY.toString());

        // Rectangle for dimmer
        x = point.x() - 9;
        dimmerRectangleWidth = 18;
//        if (3 < textDimmer.getLength()) {
//            dimmerRectangleWidth = 30;
//            x -= 4;
//        }

//        dimmerRectangle.setAttribute("x", x.toString());
//        dimmerRectangle.setAttribute("y", dimmerRectangleY.toString());
//        dimmerRectangle.setAttribute( "width", dimmerRectangleWidth.toString() );

//        if ( "" != dimmer ) {
//        infogroup.appendChild(dimmerRectangle);

//        infogroup.appendChild(dimmerText);
//        switch (Venue.Circuiting()) {
//            case Venue.ONETOMANY:
//                dimmerText.appendChild(textCircuit);
//                break;
//            case Venue.ONETOONE:
//            default:
//                dimmerText.appendChild(textDimmer);
//                break;
//        }
//        System.out.println( "Luminaire.dom: added dimmer stuff.");
//        }

        // Circle and text for channel number
        x = point.x();
//        channelCircle.setAttribute("cx", x.toString());
//        channelCircle.setAttribute("cy", channelCircleY.toString());
//        infogroup.appendChild(channelCircle);

        Integer channelTextX = point.x();
//        channelText.setAttribute("x", channelTextX.toString());
//        channelText.setAttribute("y", channelTextY.toString());
//        infogroup.appendChild(channelText);

//        channelText.appendChild(textChannel);
//        System.out.println("Luminaire.dom: added channel stuff.");


//        // Unit number to overlay on icon
//        SvgElement unitText = draw.element("text");
////        unitText.setAttribute("transform", transform);
//        unitTextX = point.x();
//        unitTextY = point.y() + 0;
//        unitText.setAttribute("x", unitTextX.toString());
//        unitText.setAttribute("y", unitTextY.toString());
//        unitText.setAttribute("fill", "green");
//        unitText.setAttribute("stroke", "green");
//        unitText.setAttribute("font-family", "sans-serif");
//        unitText.setAttribute("font-weight", "100");
//        unitText.setAttribute("font-size", "6");
//        unitText.setAttribute("text-anchor", "middle");
//        infogroup.appendChild(unitText);
//
//        Text textUnit = draw.document().createTextNode(unit);
//        unitText.appendChild(textUnit);
////        System.out.println("Luminaire.dom: added unit stuff.");
//
//
//        // Color designation to display
//        SvgElement colorText = draw.element("text");
////        colorText.setAttribute("transform", transform);
//        colorTextX = point.x();
//        colorTextY = point.y() + 18;
//        colorText.setAttribute("x", colorTextX.toString());
//        colorText.setAttribute("y", colorTextY.toString());
//        colorText.setAttribute("fill", "black");
//        colorText.setAttribute("stroke", "none");
//        colorText.setAttribute("font-family", "sans-serif");
//        colorText.setAttribute("font-weight", "100");
//        colorText.setAttribute("font-size", "5");
//        colorText.setAttribute("stroke-width", "1px");
//        colorText.setAttribute("text-anchor", "middle");
//        infogroup.appendChild(colorText);
//
//        Text textColor = draw.document().createTextNode(color);
//        colorText.appendChild(textColor);
////        System.out.println("Luminaire.dom: added color stuff.");
//
//        if (View.PLAN == mode ) {
//
//            LuminaireDefinition definition = LuminaireDefinition.Select( type );
//            if( null == definition ) {
//                throw new    ReferenceException( "Unable to find definition for "+ type );
//            }
//
//
//            SvgElement addressOval= draw.element("ellipse");
//            SvgElement addressText = draw.element("text");
//
//            if( "" != address){
//                addressOval.setAttribute( "ry", "7" );
//                addressOvalRadiusY = dimmerRectangleWidth / 2 + 1;
//                addressOval.setAttribute( "rx", addressOvalRadiusY.toString() );
//                addressOval.setAttribute( "fill", "none" );
//                infogroup.appendChild( addressOval );
//
//                addressText.setAttribute("fill", "black");
//                addressText.setAttribute("stroke", "none");
//                addressText.setAttribute("font-family", "sans-serif");
//                addressText.setAttribute("font-weight", "100");
//                addressText.setAttribute("font-size", "9");
//                addressText.setAttribute("stroke-width", "1px");
//                addressText.setAttribute("text-anchor", "middle");
//                infogroup.appendChild(addressText);
//
//                Text textAddress=draw.document().createTextNode( address );
//                addressText.appendChild( textAddress );
//            }
//
//            switch (info) {
//                case "right":
//                    xColorText = point.x()+definition.width() + dimmerRectangleWidth;
//                    yColor = point.y() - 9;
//                    colorText.setAttribute("x", xColorText.toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 3;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2 + definition.width()  + 8;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 5;
//                    dimmerText.setAttribute("x", xColorText.toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        Integer xAddressOval = xDimmerRectangle + dimmerRectangleWidth * 2;
//                        addressOval.setAttribute( "cx", xAddressOval.toString());
//                        Integer yAddressOval = yDimmerRectangle + 5;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", xAddressOval.toString());
//                        Integer addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "left":
//                    xColorText = point.x() - definition.width() - dimmerRectangleWidth;
//                    yColor = point.y() - 9;
//                    colorText.setAttribute("x", xColorText.toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 4;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2 - definition.width() - 8;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 5;
//                    dimmerText.setAttribute("x", xColorText.toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        xAddressOval = xDimmerRectangle - dimmerRectangleWidth;
//                        addressOval.setAttribute( "cx", xAddressOval.toString());
//                        yAddressOval = yDimmerRectangle + 5;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", xAddressOval.toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "up":
//                     yColor = point.y() - definition.height();
//                    colorText.setAttribute("x", point.x().toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor - 17;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 9;
//                    dimmerText.setAttribute("x", point.x().toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        addressOval.setAttribute( "cx", point.x().toString());
//                        yAddressOval = yDimmerRectangle - 10;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", point.x().toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//                case "down":
//                    yColor = point.y() + definition.height();
//                    colorText.setAttribute("x", point.x().toString());
//                    colorText.setAttribute("y", yColor.toString());
//
//                    yDimmerRectangle = yColor + 2;
//                    xDimmerRectangle = point.x() - dimmerRectangleWidth / 2;
//                    dimmerRectangle.setAttribute("x", xDimmerRectangle.toString());
//                    dimmerRectangle.setAttribute("y", yDimmerRectangle.toString());
//
//                    yDimmerText = yDimmerRectangle + 9;
//                    dimmerText.setAttribute("x", point.x().toString());
//                    dimmerText.setAttribute("y", yDimmerText.toString());
//
//                    if( "" != address){
//                        addressOval.setAttribute( "cx", point.x().toString());
//                        yAddressOval = yDimmerRectangle + 20;
//                        addressOval.setAttribute( "cy", yAddressOval.toString() );
//
//                        addressText.setAttribute("x", point.x().toString());
//                        addressTextY = yAddressOval + 4;
//                        addressText.setAttribute("y", addressTextY.toString());
//                    }
//
//                    break;
//            }
//        }

        SvgElement infogroup = svgClassGroup( draw, INFOLAYERTAG );
        group.appendChild(infogroup);

        switch (Venue.Circuiting() ) {
            case Venue.ONETOMANY:
            case Venue.ONETOONE:
                break;
            default:
                SvgElement circuitHexagon = infogroup.path( draw, circuitPath, COLOR );
//                infogroup.appendChild(circuitHexagon);
                SvgElement circuitText =
                        infogroup.text( draw, circuit, circuitTextX, circuitTextY, COLOR );
//                infogroup.appendChild(circuitText);

                break;
        }

        SvgElement dimmerRectangle = infogroup.rectangle(
                draw, x, dimmerRectangleY, dimmerRectangleWidth, 11, COLOR );
//        infogroup.appendChild(dimmerRectangle);
        SvgElement dimmerText;
        switch (Venue.Circuiting()) {
            case Venue.ONETOMANY:
                dimmerText = infogroup.text(
                        draw, circuit, dimmerTextX, dimmerTextY, COLOR );
                break;
            case Venue.ONETOONE:
            default:
                dimmerText = infogroup.text(
                        draw, dimmer, dimmerTextX, dimmerTextY, COLOR );
                break;
        }
//        infogroup.appendChild(dimmerText);

        SvgElement channelCircle = infogroup.circle( draw, x, channelCircleY, 8, COLOR );
//        infogroup.appendChild(channelCircle);
        SvgElement channelText =
                infogroup.text( draw, channel, channelTextX, channelTextY, COLOR );
//        infogroup.appendChild(channelText);

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
