package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A balcony.
 * <p/>
 * XML tag is 'balcony'
 * <p>
 *     Required attributes are:<dl> <dt>floor-height</dt><dd>height of the balcony floor over the zero level
 *     (stage or floor) of the space</dd> <dt>under-height</dt><dd>height of the bottom of the balcony over
 *     the zero level</dd> <dt>wall-height</dt><dd>height of the perimeter wall over the balcony floor</dd>
 *     <dt>rail-height</dt><dd>height of the railing over the top of the wall</dd> </dl>. These define the
 *     profile of the balcony.
 * </p>
 * <p>
 *     Each balcony element should contain some number of segment elements, which will define the length and
 *     shape of the balcony.
 * </p>
 *
 * @author dhs
 * @since 0.0.24
 * @since 2014-04-14
 */
public class Balcony extends MinderDom{

    public static final String LAYERNAME = "Balconies";
    public static final String LAYERTAG = "balcony";

    private Integer floorHeight = null;
    private Integer underHeight = null;
    private Integer wallHeight = null;
    private Integer railHeight = null;

//    /*
//    This ends up copied to each thing that inherits from
//     *                                   Minder. There needs to be a factory somewhere.
//     */
//    public static void ParseXML( NodeList list )
//            throws AttributeMissingException, DataException,
//            InvalidXMLException, LocationException, SizeException
//    {
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item( index );
//
//            // Much of this copied to Suspend.Suspend - refactor
//            if (null != node) {
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    new Balcony( element );
//                }
//            }
//        }
//    }

    /**
     * Construct a {@code Balcony} from an XML Element.
     *
     * @param element DOM Element defining a balcony
     * @throws AttributeMissingException if any attribute is missing
     */
    public Balcony( Element element )
            throws AttributeMissingException, DataException,
            InvalidXMLException {
        super( element );

        floorHeight = getPositiveIntegerAttribute( element, "floor-height" );
        underHeight = getPositiveIntegerAttribute( element, "under-height" );
        wallHeight = getPositiveIntegerAttribute( element, "wall-height" );
        railHeight = getPositiveIntegerAttribute( element, "rail-height" );

//        NodeList openings = element.getElementsByTagName( "opening" );
//        int length = openings.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = openings.item( index );
//
//            if (null != node) {
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element subElement = (Element) node;
//                    openingList.add( new Opening( subElement ) );
//                }
//            }
//        }

        new Layer(LAYERTAG, LAYERNAME);
    }

    @Override
    public void verify() throws LocationException, ReferenceException {
        if ( Venue.Height() < floorHeight + wallHeight + railHeight ) {
            throw new LocationException(
                    "Balcony instance should not extend beyond the boundaries of the venue.");
        }
        if ( underHeight >= floorHeight ) {
            throw new LocationException(
                    "Balcony instance under-height should be lower then the floor-height.");
        }
    }

    /**
     * Draw the balcony.
     *
     * @param draw Canvas/DOM manager
     * @param mode drawing mode
     */
    @Override
    public void dom( Draw draw, View mode ) {
    }

}
