package com.mobiletheatertech.plot;

/**
 * Created by dhs on 12/16/14.
 */
public class Schematic {

    static Double FirstX = 100.0;
    static Double FirstY = 100.0;
    static Double TextSpace = 12.0;

    static int Count = 0;

    static PagePoint Position( Double width, Double height ) {
        Count++;
        return new PagePoint( FirstX * Count * 2 - FirstX, FirstY );
    }
}
