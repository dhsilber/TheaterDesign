package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 12/8/14.
 */
public class DanceTile extends MinderDom implements Legendable {

    static boolean LEGENDREGISTERED = false;

    final Double Size = 36.0;
    final Double BorderWidth = 12.0;

    Double startX;
    Double endX;
    Double startY;
    Double endY;

    static Integer Count = 0;

    String color = "brown";

    public DanceTile( Element element )
            throws AttributeMissingException, DataException, InvalidXMLException {
        super( element );

        startX = getDoubleAttribute( "startX" );
        endX   = getDoubleAttribute( "endX" );
        startY = getDoubleAttribute( "startY" );
        endY   = getDoubleAttribute( "endY" );

        if ( ! LEGENDREGISTERED ) {
            Legend.Register(this, 2.0, 7.0, LegendOrder.Furniture);
            LEGENDREGISTERED = true;
        }
    }

    public void verify() { }

    public void dom( Draw draw, View mode ) {

        SvgElement group = svgClassGroup(draw, this.getClass().getSimpleName());
        draw.appendRootChild(group);

        Double x;
        Double y = startY;
        for ( x = startX; x + Size <= endX; x += Size ) {
            for ( y = startY; y + Size <= endY; y += Size ) {
                group.rectangle( draw, x, y, Size, Size, color );
                Count++;
            }
        }
        group.rectangle( draw, startX - BorderWidth, startY - BorderWidth,
                x -startX + 2 * BorderWidth, y -startY + 2 * BorderWidth, color );

    }

    public static void CountReset() {
        Count = 0;
    }

    @Override
    public void legendCountReset() {
    }

    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        if (0 >= Count) { return start; }

//        Layer layerInstance = Layer.List().get( layer );
//        if( null != layerInstance ) {
//            color = layerInstance.color();
//        }

        SvgElement box = draw.rectangleAbsolute(draw, start.x(), start.y(), 12.0, 12.0, color);
//        box.attribute( "fill", color );
//        box.attribute( "fill-opacity", "0.1" );

        Double x = start.x() + Legend.TEXTOFFSET;
        Double y = start.y() + 7;
        draw.textAbsolute(draw, "Dance floor tile", x, y, Legend.TEXTCOLOR);

        x = start.x() + Legend.QUANTITYOFFSET;
        draw.textAbsolute(draw, Count.toString(), x, y, Legend.TEXTCOLOR);

        PagePoint finish = new PagePoint( start.x(), start.y() + 12 );
        return finish;
    }

}
