package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/24/13 Time: 6:57 PM To change this template use
 * File | Settings | File Templates.
 */

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;

/**
 * A wall.
 * <p/>
 * Flat. Extending the full height of the venue. Don't worry about doorways and archways, as those
 * will be subsidary elements.
 *
 * @author dhs
 * @since 0.0.11
 */
public class Wall extends Minder {

    private Integer x1 = null;
    private Integer y1 = null;
    private Integer x2 = null;
    private Integer y2 = null;

    /**
     * Construct a {@code Pipe} for each element in a list of XML nodes.
     *
     * @param list of 'pipe' nodes
     * @throws AttributeMissingException if any attribute is missing from any {@code Pipe}
     */

    /*
    This ends up copied to each thing that inherits from
     *                                   Minder. There needs to be a factory somewhere.
     */
    public static void ParseXML( NodeList list )
            throws AttributeMissingException, LocationException, SizeException
    {
        int length = list.getLength();
        for (int index = 0; index < length; index++) {
            Node node = list.item( index );

            // Much of this copied to Suspend.Suspend - refactor
            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    new Wall( element );
                }
            }
        }
    }

    /**
     * Construct a {@code Wall} from an XML Element.
     *
     * @param element DOM Element defining a wall
     * @throws AttributeMissingException if any attribute is missing
     */
    public Wall( Element element ) throws AttributeMissingException {

        x1 = getIntegerAttribute( element, "x1" );
        y1 = getIntegerAttribute( element, "y1" );
        x2 = getIntegerAttribute( element, "x2" );
        y2 = getIntegerAttribute( element, "y2" );
    }

    @Override
    public void verify() throws InvalidXMLException, LocationException {
    }

    @Override
    public void drawPlan( Graphics2D canvas ) throws MountingException {
    }

    @Override
    public void drawSection( Graphics2D canvas ) throws MountingException {
    }

    @Override
    public void drawFront( Graphics2D canvas ) throws MountingException {
    }

    /**
     * Draw the wall.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        Element line = draw.element( "line" );

        line.setAttribute( "x1", x1.toString() );
        line.setAttribute( "y1", y1.toString() );
        line.setAttribute( "x2", x2.toString() );
        line.setAttribute( "y2", y2.toString() );
        line.setAttribute( "stroke", "black" );
        line.setAttribute( "stroke-width", "2" );

        draw.appendRootChild( line );
    }
}
