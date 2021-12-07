package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
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
public class Luminaire extends MinderDom
        implements IsClamp, Gear/*implements Schematicable*/ {

    static ArrayList<Luminaire> LUMINAIRELIST = new ArrayList<>();
    /**
     * Name of {@code Layer} of {@code Luminaoire}s.
     */
    public static final String LAYERNAME = "Luminaires";

    /**
     * Name of {@code Layer} of {@code Luminare}s.
     */
    public static final String LAYERTAG = "luminaire";

    public static final String Tag = LAYERTAG;

    LuminaireDefinition definition;

    private PagePoint schematicPosition = null;
    private Rectangle2D.Double schematicBox = null;

    private String owner;
    private Integer unit;
    private String type;
    private String on;
    private Location location;
    private String circuit;
    private String dimmer;
    private String channel;
    private String color;
    private String target;
    private String address;
    private String info;
    private String label;
    private double rotation = 0.0;
    private Point point;
    private Place place;
    private Point origin;
    private Double pipeRotation;
    private String transform;
    private LinearSupportsClamp mount = null;

    private Point standAlonePipeOffset = new Point(0, 0, 0);

    static final String COLOR = "black";

//    CableCounter cableCounter = new CableCounter();

    /**
     * Construct a {@code Luminaire} from an XML element.
     *
     * @param element DOM Element defining a luminaire
     * @throws AttributeMissingException if any attribute is missing
     * @throws InvalidXMLException       if element is null
     */
    public Luminaire(Element element)
            throws AttributeMissingException, DataException,
            InvalidXMLException {
        super(element);

        on = getStringAttribute("on");
        type = getStringAttribute("type");
        location = new Location(getStringAttribute("location"));
//        unit     = getStringAttribute(  "unit" );
        owner = getStringAttribute("owner");
        circuit = getOptionalStringAttribute("circuit");
        dimmer = getOptionalStringAttribute("dimmer");
        channel = getOptionalStringAttribute("channel");
        color = getOptionalStringAttribute("color");
        target = getOptionalStringAttribute("target");
        address = getOptionalStringAttribute("address");
        info = getOptionalStringAttribute("info");
        label = getOptionalStringAttribute("label");
        String rotate = getOptionalStringAttribute("rotation");
        if (null != rotate && !rotate.isEmpty()) {
            rotation = parseDouble(rotate);
        }

//        id = on + ":" + unit;
//
//        Luminaire prior = Select( id );
//        if( null != prior ) {
////            if ( element.getParentElement() )
////            throw new InvalidXMLException(
//            System.out.println(
//                    this.getClass().getSimpleName()+" id '"+id+"' is not unique.");
//        }


//        System.out.println("Luminaire selected");
        new Layer(LAYERTAG, LAYERNAME, COLOR);
//        System.out.println("Made Layer for Luminaire");
        new LuminaireInformation(element, this);
//        System.out.println("Made LuminaireInformation");

        GearList.Add(this);
//        System.out.println("Added Luminaire to gear list");

        LUMINAIRELIST.add(this);
//        System.out.println("added to Luminaire list");
    }

    public static Luminaire Select(String identifier) {
        for (Luminaire selection : LUMINAIRELIST) {
            if (selection.id.equals(identifier)) {
                return selection;
            }
        }
        return null;
    }

    public static Object[][] Report() {
        final Object[][] data = new Object[LUMINAIRELIST.size()][8];

        int dataIndex = 0;
        for (Luminaire selection : LUMINAIRELIST) {
            data[dataIndex][0] = selection.on + ":" + selection.unit;
            data[dataIndex][1] = selection.type;
            data[dataIndex][2] = selection.location;
//            data[dataIndex][3]= selection.circuit();
            data[dataIndex][3] = selection.dimmer();
            data[dataIndex][4] = selection.channel();
//            data[dataIndex][6]= selection.target;
            data[dataIndex][5] = selection.address;
            data[dataIndex][6] = selection.color;
            data[dataIndex][7] = selection.info;
            dataIndex++;
        }

//        data[0] = new Object[] { GEARS.get(0), CHAIRCOUNT.get(0) };
//        data[1] = new Object[] { GEARS.get(1), CHAIRCOUNT.get(1) };

        return data;
    }

    /**
     * Provide the drawing location of this {@code Luminaire}.
     *
     * @return drawing location
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
//    @Override
    public Place drawingLocation()
            throws AttributeMissingException, DataException,
            InvalidXMLException, MountingException, ReferenceException {
        Place result;
//        try {
        result = mount.rotatedLocation(location);
//        } catch (MountingException e) {
//            System.err.println( e.toString() );
//            throw new MountingException(
//                    "Luminaire of type '" + type + "' has location " + location + " which is " +
//                            e.getMessage() + " '" + on + "'.");
//        }
        return result;
    }

    @Override
    public void verify() throws AttributeMissingException, DataException,
            InvalidXMLException, MountingException, ReferenceException {

        mount = LinearSupportsClamp$.MODULE$.Select(on);

        if (null == mount) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has unknown mounting: '" + on + "'.");
        }
        try {
            mount.hang(this, location);
        } catch (NumberFormatException exception) {
//            System.out.println ( mount.toString() );

            throw new MountingException(
                    "Luminaire on pipe (" + on + ") has invalid location '"
                            + location + "'.");
        }


        place = drawingLocation();
        point = place.location();
        origin = place.origin();
        pipeRotation = place.rotation();
        rotation += 180;
//        Integer transformY = origin.y() + SvgElement.OffsetY();
//        transform = "rotate(" + pipeRotation + "," + origin.x() + "," + transformY + ")";
        transform = "rotate(" + pipeRotation + "," + origin.x() + "," + origin.y() + ")";

        // TODO Keep this until I resolve how Luminaire knows what type it is.
        definition = LuminaireDefinition.Select(type);
        if (null == definition) {
            throw new ReferenceException("Unable to find definition for " + type);
        }
    }

//    @Override
//    public void useCount( Direction direction, CableRun run ) {
//        cableCounter.add( direction, run );
////        schematicPosition = null;
//    }

    Point point() {
        return point;
    }

    LinearSupportsClamp mount() {
        return mount;
    }

    Integer unit() {
        return unit;
    }

    void unit(int newUnit) {
        unit = newUnit;
        id = on + ":" + unit;
    }

    String type() {
        return type;
    }

    String on() {
        return on;
    }

//    String locationValue() {
//        return location.toString();
//    }

    Location location() {
        return location;
    }

    String circuit() {
        return circuit;
    }

    String dimmer() {
        return dimmer;
    }

    String channel() {
        return channel;
    }

    String color() {
        return color;
    }

    String target() {
        return target;
    }

    String info() {
        return info;
    }

    String label() {
        return label;
    }

    String address() {
        return address;
    }

    void setStandAloneOffset(Point point) {
        standAlonePipeOffset = point;
    }

    public double weight() {
        Double result = 0.0;

        if (null != definition) {
            result = definition.weight();
        }

        return result;
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
        Double oppositeLength = point.y() - zone.drawY();
        Double adjacentLength = point.x() - zone.drawX();
        Double angle = Math.atan2(oppositeLength, adjacentLength);
        angle = Math.toDegrees(angle);

        return angle.intValue() + 90;
    }

//    @Override
//    public void useCount( Direction direction, CableRun run ) {
//        cablesIn[ direction.ordinal() ]++;
//    }

//    @Override
//    public void preview( View view ) throws InvalidXMLException, MountingException {
//        switch ( view ) {
//            case SCHEMATIC:
//                schematicPosition = mount.schematicLocation( location );
//                if( null == schematicPosition ) {
//                    return;
//                }
//
//                Double width = definition.width();
//                Double height = definition.length();
//                schematicBox = new Rectangle2D.Double(
//                        schematicPosition.x() - width / 2,
//                        schematicPosition.y() - height / 2,
//                        width, height );
//                Schematic.Obstruction( this );
//        }
//    }
//
//    @Override
//    public PagePoint schematicPosition() {
//        return schematicPosition;
//    }
//
//    @Override
//    public PagePoint schematicCableIntersectPosition( CableRun run )
//            throws CorruptedInternalInformationException, ReferenceException
//    {
//        Solid shape = new Solid( definition.width(), definition.width(), definition.length() );
//
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
    public Place position() {
        return null;
    }

    @Override
    public void position(Point point) {
    }

    public String item() {
        return type;
    }

    public String owner() {
        return owner;
    }

    public String room() {
        return "Room info needs to be gotten";
    }

    /**
     * Generate SVG DOM for a {@code Luminaire}, along with its circuit, dimmer, channel, color, and
     * unit information.
     *
     * @param draw Canvas/DOM manager
     * @param view drawing mode
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void dom(Draw draw, View view)
            throws InvalidXMLException, MountingException, ReferenceException {
        if (View.TRUSS == view && !Truss.class.isInstance(mount)) {
            return;
        }
        System.out.println("In Luminaire: " + this + "  draw = " + draw + ", view = " + view);

        Double z = Venue.Height() - point.z();

        SvgElement group = svgClassGroup(draw, LAYERTAG);
        draw.appendRootChild(group);

        // use element for luminaire icon
        SvgElement use = null;

        switch (view) {
            case PLAN:
                domPlan(draw, group);
//                Double x;
//                Double y;

                break;
            case SECTION:
                group.use(draw, type, point.y(), z);
                break;
            case FRONT:
                group.use(draw, type, point.x(), z);
                break;
            case TRUSS:
                Truss truss = (Truss) mount;
                Point newPoint = truss.relocate(location);
                use = group.use(draw, type, newPoint.x(), newPoint.y());
                use.attribute("transform", "rotate(" + rotation + "," + newPoint.x() + "," + newPoint.y() + ")");
                break;
//            case SCHEMATIC:
//                if( null == schematicPosition ) {
//                    return;
//                }
//                use = group.useAbsolute(draw, type, schematicPosition.x(), schematicPosition.y() );
//                if( ! LightingStand.class.isInstance( mount ) ) {
//                    use.attribute("transform",
//                            "rotate(" + rotation + "," + schematicPosition.x() + "," + schematicPosition.y() + ")");
//                }
//
//                // Unit number to draw under icon
//                SvgElement unitText = group.textAbsolute(draw, unit,
//                        schematicPosition.x() - 1, schematicPosition.y() + 2 + definition.length(), COLOR);
//                unitText.attribute("fill", "green");
//                unitText.attribute("stroke", "nonef");
//                unitText.attribute("font-family", "sans-serif");
//                unitText.attribute("font-weight", "100");
//                unitText.attribute("font-size", "6");
//                unitText.attribute("text-anchor", "middle");
//
//                // Location to draw over icon
//                Double locationX = schematicPosition.x() + 2;
//                Double locationY = schematicPosition.y() - 4 - definition.length();
//                Distance place = new Distance( mount.locationDistance( location ) );
//                SvgElement locationText = group.textAbsolute(draw, place.toString(), locationX, locationY, COLOR);
//                locationText.attribute("fill", "black");
//                locationText.attribute("stroke", "none");
//                locationText.attribute("font-family", "sans-serif");
//                locationText.attribute("font-weight", "100");
//                locationText.attribute("font-size", "6");
////                locationText.attribute("text-anchor", "middle");
//                locationText.attribute("transform",
//                        "rotate(" + -90 + "," + locationX + "," + locationY + ")");
//
//                StringBuilder information = new StringBuilder (unit + ": " + type + " :: " );
//                if( (null != target) && (target.length() > 0) ) {
//                    information.append(", target: " + target);
//                }
//                if ( null != circuit && circuit.length() > 0) {
//                    information.append(", circuit: " + circuit);
//                }
//                if ( null != dimmer && dimmer.length() > 0) {
//                    information.append(", dimmer: " + dimmer);
//                }
//                if( null != channel && channel.length() > 0) {
//                    information.append(", channel: " + channel);
//                }
//                if ( null != address && address.length() > 0) {
//                    information.append(", address: " + address);
//                }
//                if( null != color && color.length() > 0) {
//                    information.append(", color: " + color);
//                }
//                if( null != info && info.length() > 0) {
//                    information.append(", info: " + info);
//                }
//                LuminaireTable.add(information.toString());
//                break;
            default:
                return;
        }

        definition.count();
    }

    void domPlan(Draw draw, SvgElement group) {
        SvgElement use;
        Double x = point.x() - standAlonePipeOffset.x();
        Double y = point.y() - standAlonePipeOffset.y();

        group.attribute("transform", transform);
//        group.mouseover( "showData(evt)", "hideData(evt)" );

        use = group.use(draw, type, x, y, id);
        use.mouseover("showData(evt)", "hideData(evt)");

        // This transform is to orient the luminaire.
        // See verify() for the transform that rotates the position of the luminaire to
        // keep it with a truss that has been rotated.
        String transform;
//        Double transformX = point.x() + SvgElement.OffsetX();
//        Double transformY = point.y() + SvgElement.OffsetY();
        Double transformX = x + SvgElement.OffsetX();
        Double transformY = y + SvgElement.OffsetY();
        if (!target.equals("")) {
            // With this I lose the alignment with zones. :-(
            Integer rotationToFaceZone = alignWithZone(point);
            transform = "rotate(" + rotationToFaceZone + "," + transformX + "," + transformY + ")";
        } else {
            transform = "rotate(" + rotation + "," + transformX + "," + transformY + ")";
        }
        use.attribute("transform", transform);

        // Unit number to overlay on icon
        if (null == unit) {
            System.out.println("Unit numbers have not been set.");
        } else {
            SvgElement unitText = group.text(draw, unit.toString(), x, y + 3, "green");
            unitText.attribute("font-size", "7");
            unitText.attribute("text-anchor", "middle");
//        unitText.mouseover( "showData(evt)", "hideData(evt)" );
        }

        this.data(draw, group);
    }

    SvgElement data(Draw draw, SvgElement parent) {
        SvgElement data = parent.data(draw, this);

        data.attribute("type", this.type());
        data.attribute("on", this.on());
        data.attribute("location", this.location().toString());
        data.attribute("circuit", this.circuit());
        data.attribute("dimmer", this.dimmer());
        data.attribute("channel", this.channel());
        data.attribute("color", this.color());
        data.attribute("target", this.target());
        data.attribute("address", this.address());
        data.attribute("info", this.info());

        return data;
    }

    /**
     * Describe this {@code Luminaire}.
     *
     * @return textual description
     */
    @Override
    public String toString() {
        return "Luminaire: " + id + ": " + type + ", " + circuit + ", " + info + ", " + location;
    }
}
