package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by dhs on 9/9/14.
 */
public class Drawing extends ElementalLister {

    String filename = null;
    public ArrayList<String> displayList = new ArrayList<>();

    public Drawing( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = getStringAttribute( element, "id" );
        filename = getStringAttribute( element, "filename" );

        NodeList displays = element.getElementsByTagName( "display" );
        int length = displays.getLength();
        for (int index = 0; index < length; index++) {
            Node node = displays.item( index );

            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subElement = (Element) node;
                    Display display = new Display( subElement );
                    displayList.add( display.category() );
//                    displayList.add( new Opening( subElement ) );
                }
            }
        }

    }

//    public static Drawing Select( String name ) {
////        for( Drawing selection : DRAWINGLIST ) {
////            if (selection.name.equals( name )) {
////                return selection;
////            }
////        }
//        return null;
//    }

    public String filename() {
        return filename;
    }
}
