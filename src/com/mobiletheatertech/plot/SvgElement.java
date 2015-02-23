package com.mobiletheatertech.plot;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

/**
 * Created by dhs on 9/21/14.
 */
class SvgElement {

    Element element = null;

    private static Double xOffset = 0.0;
    private static Double yOffset = 0.0;


    public SvgElement( Element element ) {
        this.element = element;
    }

    public static void Offset( Double x, Double y ) {
        xOffset = x;
        yOffset = y;
    }

    public static Double OffsetX() {
        return xOffset;
    }

    public static Double OffsetY() {
        return yOffset;
    }

    public Element element() {
        return element;
    }

    public String attribute(String tag) {
        return element.getAttribute( tag );
    }

    public void attribute(String tag, String value ) {
        element.setAttribute(tag, value);
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
                              Double x, Double y, Double r,
                              String color ) {

        Double xSet = x;
        Double ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        return circleAbsolute( draw, xSet, ySet, r, color );
    }

    public SvgElement circleAbsolute( Draw draw,
                              Double x, Double y, Double r,
                              String color ) {

        SvgElement element = draw.element("circle");
        element.attribute("cx", x.toString());
        element.attribute("cy", y.toString());
        element.attribute("r", r.toString());
        element.attribute("stroke", color);
        element.attribute("fill", "none" );

        this.appendChild(element);

        return element;
    }

    public SvgElement group( Draw draw, String className ) {
        SvgElement element = groupExceptAppend(draw, className);

        this.appendChild(element);

        return element;
    }

    // TODO Clean source for this out of MinderDom
    SvgElement groupExceptAppend(Draw draw, String className) {
        SvgElement element = draw.element("g");
        element.attribute("class", className);

        return element;
    }

    public SvgElement line( Draw draw,
                                      Double x1, Double y1, Double x2, Double y2,
                                      String color ) {
        Double x1Set = x1;
        Double y1Set = y1;
        Double x2Set = x2;
        Double y2Set = y2;
        if( ! "symbol".equals( element.getTagName() )) {
            x1Set += xOffset;
            y1Set += yOffset;
            x2Set += xOffset;
            y2Set += yOffset;
        }

        return lineAbsolute(draw, x1Set, y1Set, x2Set, y2Set, color );
    }

    SvgElement lineAbsolute(Draw draw, Double x1, Double y1, Double x2, Double y2, String color ) {
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

    SvgElement lineAbsolute(Draw draw, Line2D.Double line, String color ) {
        SvgElement element = draw.element("line");
        element.attribute("x1", new Double(line.getX1()).toString());
        element.attribute("y1", new Double(line.getY1()).toString());
        element.attribute("x2", new Double(line.getX2()).toString());
        element.attribute("y2", new Double(line.getY2()).toString());
        element.attribute("stroke", color);
//        element.attribute( "stroke-width", "2" );

        this.appendChild( element );
        return element;
    }

    public SvgElement path( Draw draw,
                                      String path,
                                      String color) {
        SvgElement element = draw.element( "path" );
        element.attribute("fill", color );
        element.attribute( "fill-opacity", "0.1" );
        element.attribute("stroke", color );
        element.attribute("stroke-width", "2");
        element.attribute("d", path );

        this.appendChild( element );

        return element;
    }

    public SvgElement rectangle( Draw draw,
                                           Double x, Double y, Double width, Double height,
                                           String color ) {
        Double xSet = x;
        Double ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        return rectangleAbsolute(draw, xSet, ySet, width, height, color);

    }

    SvgElement rectangleAbsolute(Draw draw, Double x, Double y, Double width, Double height, String color) {
        SvgElement element = draw.element("rect");
        element.attribute("x", x.toString());
        element.attribute("y", y.toString());
        element.attribute("width", width.toString());
        element.attribute("height", height.toString());
        element.attribute("stroke", color);
        element.attribute("fill", "none");

        this.appendChild( element );
        return element;
    }

    public SvgElement scaleLine( Draw draw, Point start, Double width, Double height ){
        String color = "blue";
        Double thickness = 19.0;  // width of two lines and numbers
        Integer lineSpacer = 3;
        Integer dashSpacer = 6;

        SvgElement scale = groupExceptAppend(draw, "scale");

        SvgElement top = scale.lineAbsolute( draw,
                thickness - lineSpacer,
                thickness - lineSpacer,
                width + thickness + lineSpacer,
                thickness - lineSpacer,
                color );
        top.attribute( "stroke-width", "3" );

//        Double startX =  thickness - dashSpacer;
//        Double startY = thickness - dashSpacer;
//        Double endX = width + thickness + dashSpacer;
//        Double endY = thickness - dashSpacer;

        Integer dashOffsetX = - start.x().intValue() % 48;// + dashSpacer;
        Integer dashOffsetY = start.y().intValue() % 48 - dashSpacer;

        bumpyLine(draw, scale,
                thickness,
                thickness - dashSpacer,
                width + thickness,
                thickness - dashSpacer,
                color, dashOffsetX);

//        Integer incrementX = start.x().intValue();
        xAxisMeasurements(draw, start, width, 8.0, color, thickness, scale);

        SvgElement bottom = scale.lineAbsolute( draw,
                thickness - lineSpacer * 1.0,
                height + thickness + lineSpacer,
                width + thickness + lineSpacer * 1.0,
                height + thickness + lineSpacer,
                color );
        bottom.attribute( "stroke-width", "3" );

        bumpyLine(draw, scale,
                thickness,
                height + thickness + dashSpacer,
                width + thickness,
                height + thickness + dashSpacer,
                color, dashOffsetX);

        xAxisMeasurements(draw, start, width, thickness + height + 16, color, thickness, scale);

        SvgElement left = scale.lineAbsolute( draw,
                thickness - lineSpacer * 1.0,
                thickness - lineSpacer,
                thickness - lineSpacer * 1.0,
                height + thickness + lineSpacer,
                color );
        left.attribute( "stroke-width", "3" );

        bumpyLine( draw, scale,
                thickness - dashSpacer * 1.0,
                thickness - dashSpacer,
                thickness - dashSpacer * 1.0,
                height + thickness + dashSpacer,
                color, dashOffsetY );

        yAxisMeasurements(draw, start, height, 1.0, color, thickness, scale, "left");

        SvgElement right = scale.lineAbsolute( draw,
                width + thickness + lineSpacer * 1.0,
                thickness - lineSpacer,
                width + thickness + lineSpacer * 1.0,
                height + thickness + lineSpacer,
                color );
        right.attribute( "stroke-width", "3" );

        bumpyLine( draw, scale,
                width + thickness + dashSpacer * 1.0,
                thickness - dashSpacer,
                width + thickness + dashSpacer * 1.0,
                height + thickness + dashSpacer,
                color, dashOffsetY );

        yAxisMeasurements(draw, start, height, width + thickness + 10, color, thickness, scale, "right");

        this.appendChild( scale );

        return scale;
    }

    void bumpyLine(Draw draw, SvgElement parent, Double startX, Double startY, Double endX, Double endY, String color, Integer dashOffset) {
        SvgElement topBumps = parent.lineAbsolute( draw,
                startX,
                startY,
                endX,
                endY,
                color );
        topBumps.attribute( "stroke-width", "3" );
        topBumps.attribute( "stroke-dasharray", "48" );
        topBumps.attribute( "stroke-dashoffset", dashOffset.toString() );
    }

    void xAxisMeasurements(Draw draw, Point start, Double width, Double y, String color, Double thickness, SvgElement scale) {
        for( Double place = thickness + start.x() % 48; place < width; place += 48 ) {
            Integer value = (place.intValue() - thickness.intValue() - start.x().intValue()) / 12;
            SvgElement number =
                    scale.textAbsolute( draw, value.toString(), place, y, color);
            number.attribute( "text-anchor", "middle" );
        }
    }

    void yAxisMeasurements(Draw draw, Point start, Double height, Double x, String color,
                          Double thickness, SvgElement scale, String anchor) {
        for( Double place = thickness + start.y() % 48; place < height; place += 48 ) {
            Integer value = (int)(place - thickness - start.y()) / 12;
            SvgElement number = scale.textAbsolute( draw, value.toString(), x, place, color);
            number.attribute( "dominant-baseline", "central" );
            number.attribute( "text-anchor", anchor );
        }
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
                                      Double x, Double y,
                                      String color ) {
        Double xSet = x;
        Double ySet = y;
        if( ! "symbol".equals( element.getTagName() )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = textAbsolute(draw, text, xSet, ySet, color );

        return element;
    }

    SvgElement textAbsolute ( Draw draw, String text, Double x, Double y, String color ) {
        SvgElement element = draw.element("text");

        element.attribute( "x", x.toString() );
        element.attribute( "y", y.toString() );
        element.attribute( "fill", color );
        element.attribute( "stroke", "none" );
        element.attribute( "font-weight", "100" );
        element.attribute( "font-family", "sans-serif" );
        element.attribute( "font-size", "10" );

        Text foo = draw.document().createTextNode( text );
        element.appendChild( foo );

        this.appendChild( element );
        return element;
    }

    public SvgElement use( Draw draw,
                                     String id,
                                     Double x, Double y ) {
        Double xSet = x;
        Double ySet = y;
        if( ! descendantOf( "symbol" )) {
            xSet += xOffset;
            ySet += yOffset;
        }

        SvgElement element = useAbsolute(draw, id, xSet, ySet);

        return element;
    }

    SvgElement useAbsolute(Draw draw, String id, Double x, Double y) {
        SvgElement element = draw.element("use");
        element.attribute("xlink:href", "#" + id);
        element.attribute("x", x.toString());
        element.attribute("y", y.toString());

        this.appendChild( element );
        return element;
    }


    /*
    TODO I'm not sure that all this is actually needed.
     */
    private boolean descendantOf( String tag ) {
        Element root = element.getOwnerDocument().getDocumentElement();

        Element checkElement = element;
        do {
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
