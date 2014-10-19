package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Allow a user to define a drawing layer in the description file.
 *
 * @author dhs
 * @since 2014-08-15
 */
public class UserLayer extends ElementalLister {

    private String name = null;
    private String color = null;

//    /**
//     * Extract the Layer elements from a list of XML nodes and create {@code
//     * UserLayer} objects from them.
//     *
//     * @param list List of XML nodes
//     * @throws AttributeMissingException If a required attribute is/ missing
//     * @throws InvalidXMLException       if null element is somehow presented to constructor
//     */
//
//    // This seems to be generic - refactor it into Minder
//    public static void ParseXML( NodeList list )
//            throws AttributeMissingException, DataException, InvalidXMLException/*, LocationException, SizeException*/
//    {
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item( index );
//
//            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element) node;
//                new UserLayer( element );
//            }
//        }
//    }

    public UserLayer( Element element )
            throws AttributeMissingException, DataException,
            InvalidXMLException
    {
        super( element );

        id = getStringAttribute( element, "id" );
        name = getStringAttribute( element, "name" );
        color = getStringAttribute( element, "color" );

        new Layer( id, name, color );
    }

//    @Override
//    public void verify() {
//    }
//
//    @Override
//    public void dom(Draw draw, View mode) {
//    }

}
