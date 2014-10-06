package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Created by dhs on 9/21/14.
 */
class SvgElement {

    Element element = null;

    private static Integer xOffset = 0;
    private static Integer yOffset = 0;


    public SvgElement( Element element ) {
        this.element = element;
    }

    public static void Offset( Integer x, Integer y ) {
        xOffset = x;
        yOffset = y;
    }

    public static Integer OffsetX() {
        return xOffset;
    }

    public static Integer OffsetY() {
        return yOffset;
    }

    public Element element() {
        return element;
    }

    public String attribute(String tag) {
        return element.getAttribute( tag );
    }

    public void attribute(String tag, String value ) {
        element.setAttribute( tag, value );
    }

    public void appendChild( SvgElement element ) {
        this.element().appendChild(element.element());
    }

    public void appendChild( Text element ) {
        this.element().appendChild( element );
    }

    public void appendChild( Node element ) {
        this.element().appendChild( element );
    }

    public SvgElement svgClass( String name ) {
        this.attribute("class", name);

        return this;
    }

    public SvgElement circle( Draw draw,
                            Integer x, Integer y, Integer r,
                            String color ) {

        Integer xSet = x;
        Integer ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = draw.element("circle");
        element.attribute("cx", xSet.toString());
        element.attribute("cy", ySet.toString());
        element.attribute("r", r.toString());
        element.attribute("stroke", color);
        element.attribute("fill", "none" );

        this.appendChild(element);

        return element;
    }

    public SvgElement line( Draw draw,
                                      Integer x1, Integer y1, Integer x2, Integer y2,
                                      String color ) {
        Integer x1Set = x1;
        Integer y1Set = y1;
        Integer x2Set = x2;
        Integer y2Set = y2;
        if( ! "symbol".equals( element.getTagName() )) {
            x1Set += xOffset;
            y1Set += yOffset;
            x2Set += xOffset;
            y2Set += yOffset;
        }

        SvgElement element = draw.element("line");
        element.attribute("x1", x1Set.toString());
        element.attribute("y1", y1Set.toString());
        element.attribute("x2", x2Set.toString());
        element.attribute("y2", y2Set.toString());
        element.attribute("stroke", color);
//        element.attribute( "stroke-width", "2" );

        this.appendChild( element );

        return element;
    }

    public SvgElement path( Draw draw,
                                      String path,
                                      String color) {
        SvgElement element = draw.element( "path" );
        element.attribute("fill", "none");
        element.attribute("stroke", color );
        element.attribute("stroke-width", "2");
        element.attribute("d", path );

        this.appendChild( element );

        return element;
    }

    public SvgElement rectangle( Draw draw,
                                           Integer x, Integer y, Integer width, Integer height,
                                           String color ) {
        Integer xSet = x;
        Integer ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = draw.element("rect");
        element.attribute("x", xSet.toString());
        element.attribute("y", ySet.toString());
        element.attribute("width", width.toString());
        element.attribute("height", height.toString());
        element.attribute("stroke", color);
        element.attribute("fill", "none");

        this.appendChild( element );

        return element;
    }

    public SvgElement symbol( Draw draw, String id ) {
        SvgElement element = draw.element("symbol");
        element.attribute("id", id);
        element.attribute("overflow", "visible");

        this.appendChild( element );

        return element;
    }

    public SvgElement text( Draw draw,
                                      String text,
                                      Integer x, Integer y,
                                      String color ) {
        Integer xSet = x;
        Integer ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = draw.element("text");

        element.attribute( "x", xSet.toString() );
        element.attribute( "y", ySet.toString() );
        element.attribute( "fill", color );
        element.attribute( "stroke", "none" );
        element.attribute( "font-family", "serif" );
        element.attribute( "font-size", "10" );

        Text foo = draw.document().createTextNode( text );
        element.appendChild( foo );

        this.appendChild( element );

        return element;
    }

    public SvgElement use( Draw draw,
                                     String id,
                                     Integer x, Integer y ) {
        Integer xSet = x;
        Integer ySet = y;
        if( ! descendantOf( "symbol" )) {
//        if( ! "symbol".equals( element.getTagName() )) {
            System.out.println( "In use, "+ id + " is a descendant of symbol.");
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = draw.element("use");
        element.attribute("xlink:href", "#" + id);
        element.attribute("x", xSet.toString());
        element.attribute("y", ySet.toString());

        this.appendChild( element );

        return element;
    }


    /*
    TODO I'm not sure that all this is actually needed.
     */
    private boolean descendantOf( String tag ) {
        Element root = element.getOwnerDocument().getDocumentElement();

        System.out.println( "Looking for "+ tag );
        Element checkElement = element;
        do {
            System.out.println( checkElement.toString() );
            if( tag.equals( checkElement.getTagName() ) ) {
                return true;
            }
            Node checkNode = checkElement.getParentNode();
            if( null == checkNode ) {
                return false;
            }
            if( Node.ELEMENT_NODE == checkNode.getNodeType() ) {
                checkElement = (Element) checkNode;
            }
        } while( ! checkElement.isSameNode( root )  );

        return false;
    }
}
