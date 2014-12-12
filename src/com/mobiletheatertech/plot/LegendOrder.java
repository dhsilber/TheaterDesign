package com.mobiletheatertech.plot;

/**
 * Created with IntelliJ IDEA. User: dhs Date: 11/10/13 Time: 11:09 AM To change this template use
 * File | Settings | File Templates.
 *
 * @author dhs
 * @since 0.0.20
 */
public enum LegendOrder {
    Show( 1 ),
    Building( 2 ),
    Room( 3 )
            {
                @Override
                public Integer next() {
                    return last++;
                }
            },
    Furniture( 100 )
            {
                @Override
                public Integer next() {
                    return last++;
                }
            },
    Structure( 200 )
            {
                @Override
                public Integer next() {
                    return last++;
                }
            },
    Luminaire( 300 )
            {
                @Override
                public Integer next() {
                    return last++;
                }
            },
    Device( 400 )
            {
                @Override
                public Integer next() {
                    return last++;
                }
            };

    private int value;
    private static int last = -1;

    public Integer next() {
        return value;
    }

    private LegendOrder( int value ) {
        this.value = value;
    }
}
