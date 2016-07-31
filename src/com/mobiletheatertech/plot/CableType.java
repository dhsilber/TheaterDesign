package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by dhs on 1/6/15.
 */
public class CableType extends Elemental implements Legendable {

    private static ArrayList<CableType> CABLETYPELIST = new ArrayList<>();

    String schematicColor = null;

    public CableType( Element element ) throws AttributeMissingException, InvalidXMLException {
        super( element );

        id = getStringAttribute( "id" );
        schematicColor = getStringAttribute( "schematic-color" );

        CABLETYPELIST.add( this );

        Legend.Register( this, 130.0, 7.0, LegendOrder.Cable );
    }

    public static CableType Select( String identifier ) {
        for (CableType selection : CABLETYPELIST) {
            if (selection.id.equals( identifier )) {
                return selection;
            }
        }
        return null;
    }

    public String color() {
        return schematicColor;
    }

    @Override
    public void countReset() {
//        Count = 0;
    }

    /**
     * Callback used by {@code Legend} to allow this object to generate the information it needs to
     * put into the legend area.
     * <p/>
     *
     * @param draw  Canvas/DOM manager
     * @param start position on the canvas for this legend entry
     * @return start point for next {@code Legend} item
     */
    @Override
    public PagePoint domLegendItem( Draw draw, PagePoint start ) {
        if ( 0 >= CABLETYPELIST.size() ) { return start; }

        Double endLine = start.x() + 12;

        draw.lineAbsolute(draw, start.x(), start.y(), endLine, start.y(), schematicColor );

        String words = id + " cable run";
        Double x = start.x() + Legend.TEXTOFFSET;
        Double y = start.y() + 3;
        draw.textAbsolute(draw, words, x, y, Legend.TEXTCOLOR);

        return new PagePoint( start.x(), start.y() + 7 );
    }
}
