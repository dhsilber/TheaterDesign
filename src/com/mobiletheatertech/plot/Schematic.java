package com.mobiletheatertech.plot;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by dhs on 12/16/14.
 */
public class Schematic {

    static Double FirstX = 100.0;
    static Double FirstY = 100.0;
    static Double TextSpace = 12.0;
    static ArrayList<Schematicable> ObstructionList = new ArrayList<>();

    static int Count = 0;

    static PagePoint Position( Double width, Double height ) {
        Count++;
        return new PagePoint( FirstX * Count * 2 - FirstX, FirstY );
    }

    static void Obstruction( Schematicable schemer ) {
        ObstructionList.add( schemer );
    }

//    static void Obstruction( Double x, Double y, Double width, Double height ) {
//        ObstructionList.add( new Rectangle2D.Double( x, y, width, height ) );
//    }

    static ArrayList<Schematicable> FindObstruction( Line2D.Double line ) {
        ArrayList<Schematicable> matches = new ArrayList<>();

        for( Schematicable candidate : ObstructionList ) {
            if (candidate.schematicBox().intersectsLine(line) ) {
                matches.add( candidate );
            }
        }
        return matches;
    }
}
