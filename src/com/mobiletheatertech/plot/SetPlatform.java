package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

/**
 * Created by dhs on 1/21/15.
 */
public class SetPlatform extends MinderDom implements Legendable {

    ArrayList<Shape> Polygons = new ArrayList<>();

    private Double x = null;
    private Double y = null;
    private Double orientation = null;

    public SetPlatform( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException
    {
        super( element );

        x = getDoubleAttribute( "x" );
        y = getDoubleAttribute( "y" );
        orientation = getOptionalDoubleAttributeOrZero( "orientation" );

        ArrayList<Element> polygonList = subElements( element, "shape" );
        for( Element polygonElement : polygonList ) {
            String polygonString = polygonElement.getAttribute("polygon");
            if (null != polygonString && ! "".equals( polygonString )) {
                Shape polygon = new Shape( polygonString );
                Polygons.add( polygon );
            }
        }
    }

    ArrayList<Element> subElements( Element element, String tag ) {
        ArrayList<Element> resultList = new ArrayList<>();

        NodeList displays = element.getElementsByTagName( tag );
        int length = displays.getLength();
        for (int index = 0; index < length; index++) {
            Node node = displays.item( index );

            if (null != node) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element subElement = (Element) node;
                    resultList.add(subElement);
                }
            }
        }

        return resultList;
    }

    @Override
    public void verify() throws DataException, FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException {

    }

    @Override
    public void dom(Draw draw, View mode)
            throws InvalidXMLException, MountingException, ReferenceException
    {
        if( ! Proscenium.Active() ) { return; }


        SvgElement group = draw.group( draw, "" );
        for ( Shape thing : Polygons ) {

            group.path( draw, thing.toSvgPath( x, y ), "black" );
        }
    }

    @Override
    public void countReset() {

    }

    @Override
    public PagePoint domLegendItem(Draw draw, PagePoint start) {
        return null;
    }
}
