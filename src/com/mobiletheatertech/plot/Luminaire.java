package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.awt.*;
import java.awt.geom.Line2D;

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
public class Luminaire extends Minder {
    private String type;
    private String on;
    private Integer location;
    private String circuit;
    private String dimmer;
    private String channel;
    private String color;
    private String unit = "5";

    /**
     * Construct a {@code Luminaire} for each element in a list of XML nodes.
     *
     * @param list of XML nodes
     * @throws AttributeMissingException if a required attribute is missing
     * @throws LocationException         if the stage is outside the {@code Venue}
     * @throws SizeException             if a length attribute is too short
     */

    // This seems to be generic - refactor it into Minder
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, InvalidXMLException, LocationException, SizeException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                new Luminaire( element );
            }

        }
    }

    /**
     * Construct a {@code Luminaire} from an XML element.
     *
     * @param element DOM Element defining a luminaire
     * @throws AttributeMissingException if any attribute is missing
     */
    public Luminaire( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        type = getStringAttribute( element, "type" );
        on = getStringAttribute( element, "on" );
        location = getIntegerAttribute( element, "location" );
        circuit = getOptionalStringAttribute( element, "circuit" );
        dimmer = getOptionalStringAttribute( element, "dimmer" );
        channel = getOptionalStringAttribute( element, "channel" );
        color = getOptionalStringAttribute( element, "color" );
        unit = getOptionalStringAttribute( element, "unit" );
    }

    /**
     * Provide the drawing location of this {@code Luminaire}.
     *
     * @return drawing location
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    protected Point location() throws MountingException {
        Pipe mount = Pipe.Select( on );
        if (null == mount) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has unknown mounting: '" + on + "'." );
        }
        Point result;
        try {
            result = mount.location( location );
        }
        catch ( MountingException e ) {
            throw new MountingException(
                    "Luminaire of type '" + type + "' has location " + location + " which is " +
                            e.getMessage() + " '" + on + "'." );
        }
        return result;
    }

    @Override
    public void verify() {
    }

    /**
     * If the specified type is not defined by a {@code LuminaireDefinition}, draw a big red X where
     * the luminaire should be in the plan view.
     *
     * @param canvas drawing media
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void drawPlan( Graphics2D canvas ) throws MountingException {
        if (null == LuminaireDefinition.Select( type )) {
            Point point = location();
            canvas.setPaint( Color.RED );
            canvas.draw(
                    new Line2D.Float( point.x() - 2, point.y() - 2, point.x() + 2,
                                      point.y() + 2 ) );
            canvas.draw(
                    new Line2D.Float( point.x() + 2, point.y() - 2, point.x() - 2,
                                      point.y() + 2 ) );
        }
    }

    /**
     * If the specified type is not defined by a {@code LuminaireDefinition}, draw a big red X where
     * the luminaire should be in the section view.
     *
     * @param canvas drawing media
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void drawSection( Graphics2D canvas ) throws MountingException {
        if (null == LuminaireDefinition.Select( type )) {
            int bottom = Venue.Height();

            Point point = location();
            canvas.setPaint( Color.RED );
            canvas.draw( new Line2D.Float( point.y() - 2, bottom - point.z() - 2,
                                           point.y() + 2, bottom - point.z() + 2 ) );
            canvas.draw( new Line2D.Float( point.y() + 2, bottom - point.z() - 2,
                                           point.y() - 2, bottom - point.z() + 2 ) );
        }
    }

    /**
     * If the specified type is not defined by a {@code LuminaireDefinition}, draw a big red X where
     * the luminaire should be in the front view.
     *
     * @param canvas drawing media
     * @throws MountingException if the {@code Pipe} that this is supposed to be on does not exist
     */
    @Override
    public void drawFront( Graphics2D canvas ) throws MountingException {
        if (null == LuminaireDefinition.Select( type )) {
            int bottom = Venue.Height();

            Point point = location();
            canvas.setPaint( Color.RED );
            canvas.draw( new Line2D.Float( point.x() - 2, bottom - point.z() - 2,
                                           point.x() + 2, bottom - point.z() + 2 ) );
            canvas.draw( new Line2D.Float( point.x() + 2, bottom - point.z() - 2,
                                           point.x() - 2, bottom - point.z() + 2 ) );
        }
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
    public void dom( Draw draw, View mode ) throws MountingException {
        Point point = location();

        Integer z = Venue.Height() - point.z();

        Element use = draw.element( "use" );
        use.setAttribute( "xlink:href", "#" + type );

        switch (mode) {
            case PLAN:
                use.setAttribute( "x", point.x().toString() );
                use.setAttribute( "y", point.y().toString() );
                break;
            case SECTION:
                use.setAttribute( "x", point.y().toString() );
                use.setAttribute( "y", z.toString() );
                break;
            case FRONT:
                use.setAttribute( "x", point.x().toString() );
                use.setAttribute( "y", z.toString() );
                break;

        }

        draw.appendRootChild( use );

//        draw.appendRootChild( domCircuit( circuit ) );
        Element circuitHexagon = draw.element( "path" );
        Integer x = point.x() - 9;
        Integer y = point.y() - 25;
        circuitHexagon.setAttribute( "fill", "none" );
        circuitHexagon.setAttribute( "stroke", "black" );
        circuitHexagon.setAttribute( "stroke-width", "1" );
        circuitHexagon.setAttribute( "d",
                                     "M " + (x + 1) + " " + (y + 5) +
                                             " L " + (x + 4) + " " + y +
                                             " L " + (x + 14) + " " + y +
                                             " L " + (x + 17) + " " + (y + 5) +
                                             " L " + (x + 14) + " " + (y + 10) +
                                             " L " + (x + 4) + " " + (y + 10) +
                                             " Z" );
        circuitHexagon.setAttribute( "x", x.toString() );
        circuitHexagon.setAttribute( "y", y.toString() );
        circuitHexagon.setAttribute( "width", "18" );
        circuitHexagon.setAttribute( "height", "12" );
        draw.appendRootChild( circuitHexagon );

        Element circuitText = draw.element( "text" );
        Integer circuitTextX = point.x() - 4;
        Integer circuitTextY = point.y() - 17;
        circuitText.setAttribute( "x", circuitTextX.toString() );
        circuitText.setAttribute( "y", circuitTextY.toString() );
        circuitText.setAttribute( "fill", "black" );
        circuitText.setAttribute( "font-family", "serif" );
        circuitText.setAttribute( "font-size", "9" );
        draw.appendRootChild( circuitText );

        Text textCircuit = draw.document().createTextNode( circuit );
        circuitText.appendChild( textCircuit );


        Text textDimmer = draw.document().createTextNode( dimmer );

        Element dimmerRectangle = draw.element( "rect" );
        x = point.x() - 9;
        y = point.y() - 39;
        Integer width = 18;
        if (3 < textDimmer.getLength()) {
            width = 30;
            x -= 4;
        }
        dimmerRectangle.setAttribute( "fill", "none" );
        dimmerRectangle.setAttribute( "x", x.toString() );
        dimmerRectangle.setAttribute( "y", y.toString() );
        dimmerRectangle.setAttribute( "width", width.toString() );
        dimmerRectangle.setAttribute( "height", "11" );
        draw.appendRootChild( dimmerRectangle );

        Element dimmerText = draw.element( "text" );
        Integer dimmerTextX = point.x() - 4;
        if (3 < textDimmer.getLength()) {
            dimmerTextX -= 5;
        }
        Integer dimmerTextY = point.y() - 31;
        dimmerText.setAttribute( "x", dimmerTextX.toString() );
        dimmerText.setAttribute( "y", dimmerTextY.toString() );
        dimmerText.setAttribute( "fill", "black" );
        dimmerText.setAttribute( "font-family", "serif" );
        dimmerText.setAttribute( "font-size", "9" );
        draw.appendRootChild( dimmerText );

        dimmerText.appendChild( textDimmer );


        Element channelCircle = draw.element( "circle" );
        x = point.x();
        y = point.y() - 50;
        channelCircle.setAttribute( "fill", "none" );
        channelCircle.setAttribute( "cx", x.toString() );
        channelCircle.setAttribute( "cy", y.toString() );
        channelCircle.setAttribute( "r", "8" );
        draw.appendRootChild( channelCircle );

        Element channelText = draw.element( "text" );
        Integer channelTextX = point.x() - 5;
        Integer channelTextY = point.y() - 46;
        channelText.setAttribute( "x", channelTextX.toString() );
        channelText.setAttribute( "y", channelTextY.toString() );
        channelText.setAttribute( "fill", "black" );
        channelText.setAttribute( "font-family", "serif" );
        channelText.setAttribute( "font-size", "9" );
        draw.appendRootChild( channelText );

        Text textChannel = draw.document().createTextNode( channel );
        channelText.appendChild( textChannel );


        Element unitText = draw.element( "text" );
        Integer unitTextX = point.x() - 2;
        Integer unitTextY = point.y() + 0;
        unitText.setAttribute( "x", unitTextX.toString() );
        unitText.setAttribute( "y", unitTextY.toString() );
        unitText.setAttribute( "fill", "black" );
        unitText.setAttribute( "font-family", "sans-serif" );
        unitText.setAttribute( "font-weight", "100" );
        unitText.setAttribute( "font-size", "6" );
        unitText.setAttribute( "stroke-width", "1px" );
        draw.appendRootChild( unitText );

        Text textUnit = draw.document().createTextNode( unit );
        unitText.appendChild( textUnit );


        Element colorText = draw.element( "text" );
        Integer colorTextX = point.x() - 6;
        Integer colorTextY = point.y() + 18;
        colorText.setAttribute( "x", colorTextX.toString() );
        colorText.setAttribute( "y", colorTextY.toString() );
        colorText.setAttribute( "fill", "black" );
        colorText.setAttribute( "font-family", "sans-serif" );
        colorText.setAttribute( "font-weight", "100" );
        colorText.setAttribute( "font-size", "5" );
        colorText.setAttribute( "stroke-width", "1px" );
        draw.appendRootChild( colorText );

        Text textColor = draw.document().createTextNode( color );
        colorText.appendChild( textColor );
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
