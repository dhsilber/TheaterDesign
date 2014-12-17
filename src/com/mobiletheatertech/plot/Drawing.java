package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by dhs on 9/9/14.
 */
public class Drawing extends ElementalLister {

    String filename = null;
    View view = null;
    public ArrayList<String> layers = new ArrayList<>();
    public ArrayList<String> devices = new ArrayList<>();

    public Drawing( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = getStringAttribute( element, "id" );
        filename = getStringAttribute( element, "filename" );
        String viewString = getOptionalStringAttribute( element, "view" );
        switch ( viewString ) {
            case "schematic":
                view = View.SCHEMATIC;
                break;
            case "":
                view = View.PLAN;
                break;
            default:
                throw new InvalidXMLException( this.getClass().getSimpleName(), id,
                        "has invalid 'view' attribute. Valid is 'schematic'" );
        }

        NodeList displays = element.getElementsByTagName( "display" );
        int length = displays.getLength();
        for (int index = 0; index < length; index++) {
            Node node = displays.item( index );

            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subElement = (Element) node;
                    Display display = new Display( subElement );
                    store( display.layer(), layers );
                    store( display.device(), devices );
                }
            }
        }

    }

    void store(String text, ArrayList<String> destination ) {
        if( !text.equals( "" ) ) {
            destination.add( text );
        }
    }

    public String filename() {
        return filename;
    }

    public View view() {
        return view;
    }
}
