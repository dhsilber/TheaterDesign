package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.awt.geom.Line2D;

/**
 * A wall that can be deployed and stowed at will.
 * <p/>
 * Currently only drawn in the deployed position.
 *
 * @author dhs
 * @since 0.0.8
 */
public class Airwall extends Minder {

    Integer depth = null;

    /**
     * Create an {@code Airwall} for each element in a list of XML nodes.
     *
     * @param list List of XML nodes
     * @throws AttributeMissingException If a required attribute is missing.
     * @throws LocationException         If the airwall is outside the {@code Venue}.
     * @throws SizeException             If a length attribute is too short.
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
                new Airwall( element );
            }

        }
    }

    /**
     * Construct an {@code Airwall} from an XML element.
     *
     * @param element data to create {@code Airwall} from
     * @throws AttributeMissingException
     */
    public Airwall( Element element ) throws AttributeMissingException, InvalidXMLException, LocationException {
        super( element );

        depth = getIntegerAttribute( element, "depth" );

        if (depth <= 0) {
            throw new LocationException( "Airwall instance is at too small a depth" );
        }

        if (depth >= Venue.Width()) {
            throw new LocationException( "Airwall instance is at too large a depth" );
        }

    }

    @Override
    public void verify() throws InvalidXMLException, LocationException {
    }

    @Override
    public void drawPlan( Graphics2D canvas ) throws MountingException {
        int width = Venue.Width();

        canvas.setPaint( Color.ORANGE );
        canvas.draw( new Line2D.Float( 0, depth, width, depth ) );
    }

    @Override
    public void drawSection( Graphics2D canvas ) throws MountingException {
    }

    @Override
    public void drawFront( Graphics2D canvas ) throws MountingException {
    }

    @Override
    public void dom( Draw draw, View mode ) throws MountingException {
    }
}
