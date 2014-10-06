package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.metadata.IIOMetadataNode;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/13/13 Time: 8:00 PM To change this template use
 * File | Settings | File Templates.
 *
 *
 *
 * @author dhs
 * @since 0.0.20
 */
public class Event extends MinderDom {

    private static Event Only = null;

//    public static void ParseXML( NodeList list )
//            throws AttributeMissingException, InvalidXMLException, ReferenceException
//    {
//
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item( index );
//
//            // Much of this copied to Suspend.Suspend - refactor
//            if (null != node) {
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element element = (Element) node;
//                    new Event( element );
//                }
//            }
//        }
//    }

    public static String Name() throws ReferenceException {
        if (null != Only) {
            return Only.id;
        }

        throw new ReferenceException( "Event is not defined." );
    }

    public Event( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = getStringAttribute( element, "name" );

        Only = this;
    }

    @Override
    public void verify() throws FeatureException, InvalidXMLException, LocationException, ReferenceException {
    }

    @Override
    public void dom( Draw draw, View mode ) throws MountingException, ReferenceException {

        StringBuilder title = new StringBuilder( id + " - " );

        switch (mode) {
            case PLAN:
                title.append( "Plan view" );
                break;
            case SECTION:
                title.append( "Section view" );
                break;
            case FRONT:
                title.append( "Front view" );
                break;

        }
        draw.setDocumentTitle( title.toString() );
    }
}
