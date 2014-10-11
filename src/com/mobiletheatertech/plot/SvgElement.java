package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.awt.geom.Point2D;

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

    public String tag() {
        return element.getTagName();
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

        return lineAbsolute(draw, x1Set, y1Set, x2Set, y2Set, color );
    }

    SvgElement lineAbsolute(Draw draw, Integer x1, Integer y1, Integer x2, Integer y2, String color ) {
        SvgElement element = draw.element("line");
        element.attribute("x1", x1.toString());
        element.attribute("y1", y1.toString());
        element.attribute("x2", x2.toString());
        element.attribute("y2", y2.toString());
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

    public SvgElement scaleLine( Draw draw, Point start, Integer width, Integer height ){
        String color = "blue";
        Integer thickness = 19;
        Integer lineSpacer = 3;
        Integer dashSpacer = 6;
                
        SvgElement scale = draw.element( "g" );
        scale.attribute( "class", "scale" );

        SvgElement top = scale.lineAbsolute( draw,
                thickness - lineSpacer,
                thickness - lineSpacer,
                width + thickness + lineSpacer,
                thickness - lineSpacer,
                color );
        top.attribute( "stroke-width", "3" );

        SvgElement topBumps = scale.lineAbsolute( draw,
                thickness - dashSpacer,
                thickness - dashSpacer,
                width + thickness + dashSpacer,
                thickness - dashSpacer,
                color );
        topBumps.attribute( "stroke-width", "3" );
        topBumps.attribute( "stroke-dasharray", "48" );
        Integer dashOffsetX = start.x() % 48 - dashSpacer;
        topBumps.attribute( "stroke-dashoffset", dashOffsetX.toString() );

        Integer incrementX = start.x();
//    System.out.println( "IncrementX: "+ incrementX);
        for( Integer place = thickness + start.x() % 48; place < width; place += 48 ) {
            Integer value = (place - thickness - start.x()) / 12;
            SvgElement number =
                    scale.textAbsolute( draw, value.toString(), place, 8, color);
            number.attribute( "text-anchor", "middle" );
        }

        SvgElement bottom = scale.lineAbsolute( draw,
                thickness - lineSpacer,
                height + thickness + lineSpacer,
                width + thickness + lineSpacer,
                height + thickness + lineSpacer,
                color );
        bottom.attribute( "stroke-width", "3" );

        SvgElement bottomBumps = scale.lineAbsolute( draw,
                thickness - dashSpacer,
                height + thickness + dashSpacer,
                width + thickness + dashSpacer,
                height + thickness + dashSpacer,
                color );
        bottomBumps.attribute( "stroke-width", "3" );
        bottomBumps.attribute( "stroke-dasharray", "48" );
        bottomBumps.attribute( "stroke-dashoffset", dashOffsetX.toString() );

        for( Integer place = thickness + start.x() % 48; place < width; place += 48 ) {
            Integer value = (place - thickness - start.x()) / 12;
            SvgElement number =
                    scale.textAbsolute( draw, value.toString(), place, thickness + height + 16, color);
            number.attribute( "text-anchor", "middle" );
        }

        SvgElement left = scale.lineAbsolute( draw,
                thickness - lineSpacer,
                thickness - lineSpacer,
                thickness - lineSpacer,
                height + thickness + lineSpacer,
                color );
        left.attribute( "stroke-width", "3" );

        SvgElement leftBumps = scale.lineAbsolute( draw,
                thickness - dashSpacer,
                thickness - dashSpacer,
                thickness - dashSpacer,
                height + thickness + dashSpacer,
                color );
        leftBumps.attribute( "stroke-width", "3" );
        leftBumps.attribute( "stroke-dasharray", "48" );
        Integer dashOffsetY = start.y() % 48 - dashSpacer;
        leftBumps.attribute( "stroke-dashoffset", dashOffsetY.toString() );

        for( Integer place = thickness + start.y() % 48; place < height; place += 48 ) {
            Integer value = (place - thickness - start.y()) / 12;
            SvgElement number = scale.textAbsolute( draw, value.toString(), 1, place, color);
            number.attribute( "dominant-baseline", "central" );
            number.attribute( "text-anchor", "left" );
        }

        SvgElement right = scale.lineAbsolute( draw,
                width + thickness + lineSpacer,
                thickness - lineSpacer,
                width + thickness + lineSpacer,
                height + thickness + lineSpacer,
                color );
        right.attribute( "stroke-width", "3" );

        SvgElement rightBumps = scale.lineAbsolute( draw,
                width + thickness + dashSpacer,
                thickness - dashSpacer,
                width + thickness + dashSpacer,
                height + thickness + dashSpacer,
                color );
        rightBumps.attribute( "stroke-width", "3" );
        rightBumps.attribute( "stroke-dasharray", "48" );
        Integer foo = -dashSpacer;
        rightBumps.attribute( "stroke-dashoffset", foo.toString() );

        for( Integer place = thickness + start.y() % 48; place < height; place += 48 ) {
            Integer value = (place - thickness - start.y())/ 12;
            SvgElement number = scale.textAbsolute( draw, value.toString(), width + thickness + 10, place, color);
            number.attribute( "dominant-baseline", "central" );
            number.attribute( "text-anchor", "right" );
        }

        this.appendChild( scale );

        return scale;
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

        SvgElement element = textAbsolute(draw, text, xSet, ySet, color );

        return element;
    }

    SvgElement textAbsolute ( Draw draw, String text, Integer x, Integer y, String color ) {
        SvgElement element = draw.element("text");

        element.attribute( "x", x.toString() );
        element.attribute( "y", y.toString() );
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
