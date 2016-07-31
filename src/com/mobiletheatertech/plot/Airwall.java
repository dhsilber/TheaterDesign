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
public class Airwall extends MinderDom {

    Double depth = null;

    static final String COLOR = "dark gray";

    /**
     * Construct an {@code Airwall} from an XML element.
     *
     * @param element data to create {@code Airwall} from
     * @throws AttributeMissingException
     */
    public Airwall( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException, LocationException,
            ReferenceException
    {
        super( element );

        depth = getDoubleAttribute( "depth" );

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

//    @Override
//    public void drawPlan( Graphics2D canvas ) throws MountingException, ReferenceException {
//        int width = Venue.Width();
//
//        canvas.setPaint( Color.ORANGE );
//        canvas.draw( new Line2D.Float( 0, depth, width, depth ) );
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) throws MountingException {
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) throws MountingException {
//    }

    @Override
    public void dom( Draw draw, View mode ) throws ReferenceException {
        SvgElement element = null;
        Double width = Venue.Width();

        switch (mode) {
            case PLAN:
                element = draw.line( draw, 0.0, depth, width * 1.0, depth, COLOR )
// TODO Add stroke-width
//                        .lineWidth( 2 )
                ;
//                draw.appendRootChild( element );
                break;
            case SECTION:
                break;
            case FRONT:
                break;
            case TRUSS:
                break;
        }
    }
}
