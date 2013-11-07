package com.mobiletheatertech.plot;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 9/24/13 Time: 6:57 PM To change this template use
 * File | Settings | File Templates.
 */

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A wall.
 * <p/>
 * XML tag is 'wall'. Required attributes are 'x1', 'y1', 'x2', 'y2', which specify beginning and
 * end coordinates for the wall segment. Height & z-coordinates are not specified, as the wall is
 * presumed to extend from floor to ceiling. Children may be 'opening' elements.
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

    private TreeSet<Opening> openingList = new TreeSet<>( new OpeningComparator() );

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
            throws AttributeMissingException, InvalidXMLException, LocationException, SizeException
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
    public Wall( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        x1 = getIntegerAttribute( element, "x1" );
        y1 = getIntegerAttribute( element, "y1" );
        x2 = getIntegerAttribute( element, "x2" );
        y2 = getIntegerAttribute( element, "y2" );

        NodeList openings = element.getElementsByTagName( "opening" );
        int length = openings.getLength();
        for (int index = 0; index < length; index++) {
            Node node = openings.item( index );

            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subElement = (Element) node;
                    openingList.add( new Opening( subElement ) );
                }
            }
        }
    }

    @Override
    public void verify() throws FeatureException {
        if (openingList.size() > 0) {
            if (x1.equals( x2 )) {
//                    Line2D line = new Line2D.Float( x1, y1, x2, y1 + opening.start() );
//                }
            }
            else if (y1.equals( y2 )) {

            }
            else {
                throw new FeatureException( "Wall at angle does not yet support openings." );
            }
        }
    }

    @Override
    public void drawPlan( Graphics2D canvas ) {
    }

    @Override
    public void drawSection( Graphics2D canvas ) {
    }

    @Override
    public void drawFront( Graphics2D canvas ) {
    }

    /**
     * Draw the wall.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
        if (mode != View.PLAN) {
            return;
        }

        Iterator<Opening> iterator = openingList.iterator();
        if (openingList.size() > 0)

        {
            if (x1.equals( x2 )) {
                Integer yOne = y1;
                Integer yTwo;
                while (iterator.hasNext()) {
                    Opening opening = iterator.next();
                    yTwo = y1 + opening.start();

                    Element line = draw.element( "line" );

                    line.setAttribute( "x1", x1.toString() );
                    line.setAttribute( "y1", yOne.toString() );
                    line.setAttribute( "x2", x2.toString() );
                    line.setAttribute( "y2", yTwo.toString() );
                    line.setAttribute( "stroke", "black" );
                    line.setAttribute( "stroke-width", "2" );
                    draw.appendRootChild( line );

                    yOne = yTwo + opening.width();
                }
                Element line = draw.element( "line" );

                line.setAttribute( "x1", x1.toString() );
                line.setAttribute( "y1", yOne.toString() );
                line.setAttribute( "x2", x2.toString() );
                line.setAttribute( "y2", y2.toString() );
                line.setAttribute( "stroke", "black" );
                line.setAttribute( "stroke-width", "2" );
                draw.appendRootChild( line );
            }
            else {
                Integer xOne = x1;
                Integer xTwo;
                while (iterator.hasNext()) {
                    Opening opening = iterator.next();
                    xTwo = x1 + opening.start();

                    Element line = draw.element( "line" );

                    line.setAttribute( "x1", xOne.toString() );
                    line.setAttribute( "y1", y1.toString() );
                    line.setAttribute( "x2", xTwo.toString() );
                    line.setAttribute( "y2", y2.toString() );
                    line.setAttribute( "stroke", "black" );
                    line.setAttribute( "stroke-width", "2" );
                    draw.appendRootChild( line );

                    xOne = xTwo + opening.width();
                }
                Element line = draw.element( "line" );

                line.setAttribute( "x1", xOne.toString() );
                line.setAttribute( "y1", y1.toString() );
                line.setAttribute( "x2", x2.toString() );
                line.setAttribute( "y2", y2.toString() );
                line.setAttribute( "stroke", "black" );
                line.setAttribute( "stroke-width", "2" );
                draw.appendRootChild( line );
            }
        }

        else

        {
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
}
