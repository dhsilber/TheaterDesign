package com.mobiletheatertech.plot;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * Created by dhs on 12/16/14.
 */
public class Schematic {

    static final Double Increment = 100.0;
    static final Double Spacer = 50.0;
    static final Double MaxX = 700.0;
//    static final Double MaxY = 600.0;
    static Double LastX = 0.0;
    static Double LastY = Increment;
    static Double LastWidth = Spacer;

    static Double TotalWidth = 0.0;

//    static Double FirstX = 100.0;
//    static Double FirstY = 100.0;
    static final Double TextSpace = 12.0;
    static ArrayList<Schematicable> ObstructionList = new ArrayList<>();

//    static int CountX = 0;
//    static int CountY = 1;

    static PagePoint Position( Double width, Double height ) {
        Double newHeight = Math.max( height, Increment );
        Double newWidth  = Math.max( width / 2 + Spacer,  Increment );

        LastX += LastWidth / 2 + newWidth + Spacer;
        if ( LastX >= MaxX ) {
            LastX = newWidth + Spacer;
            LastY += newHeight;
        }
        LastWidth = width;

        TotalWidth = Math.max( TotalWidth, LastX + width / 2 );

        return new PagePoint( LastX, LastY );
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

    public static void Reset() {
        ObstructionList = new ArrayList<>();
        LastX = 0.0;
        LastY = Increment;
        LastWidth = Spacer;
//        CableCounter.cablesIn = new ArrayList<>( Direction.Left.ordinal() + 1 );
//        CableRun.RunList = new ArrayList<>();
    }
}
