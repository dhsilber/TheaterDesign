package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 9/26/13 Time: 1:34 PM To change this template use
 * File | Settings | File Templates.
 */

import org.w3c.dom.Element;

/**
 * An opening in a wall.  E.g. a doorway without the doors.
 * <p/>
 * XML tag is 'opening'. Must be a child of a 'wall' element. Required attributes are 'height',
 * 'width', and 'start', which is the distance from the wall's first pair of coordinates to start
 * the opening.
 *
 * @author dhs
 * @since 0.0.12
 */
public class Opening extends Elemental {

    private Integer height = null;
    private Integer width = null;
    private Integer start = null;

    public Opening( Element element ) throws AttributeMissingException, InvalidXMLException {
        if (null == element) {
            throw new InvalidXMLException( "Element unexpectedly null!" );
        }

        height = getIntegerAttribute( element, "height" );
        width = getIntegerAttribute( element, "width" );
        start = getIntegerAttribute( element, "start" );
    }

    /**
     * Provide the start of this opening.
     *
     * @return start
     */
    public Integer start() {
        return start;
    }

    /**
     * Provide the width of this opening.
     *
     * @return width
     */
    public Integer width() {
        return width;
    }
//
//    public int compare( Opening a, Opening b ) {
//        if( a.start() < b.start() ) {
//            return -1;
//        }
//        if( a.start() > b.start() ) {
//            return 1;
//        }
//        else {
//            return 0;
//        }
//    }
}
