package com.mobiletheatertech.plot;

import java.util.ArrayList;

/**
 * Collect lists of gear.
 *
 * Constructor accepts a string and keeps a count of how many times
 * that string has been seen.
 *
 * Created by dhs on 4/12/14.
 *
 * @author dhs
 * @since 0.0.24
 */
public class GearList {

    private static ArrayList<String> GEARS = new ArrayList<>();
    private static ArrayList<Integer> COUNT = new ArrayList<>();

    public GearList( String item ) {
    }

    public static void Add (String item) {
        if( GEARS.contains( item ) ) {
            int index = GEARS.indexOf( item );
            int count = COUNT.get( index );
            COUNT.set(index, ++count);
        }
        else {
        GEARS.add( item );
        COUNT.add( 1 );
        }
    }

    public static Integer Check( String item ) {
        int index;
        Integer count = 0;
        if( GEARS.contains( item ) ) {
            index = GEARS.indexOf( item );
            count = COUNT.get( index );
        }

        return count;
    }

    public static Object[][] Report () {
        final Object[][] data = new Object[ GEARS.size()][2];

        int dataIndex = 0;
        for ( String item : GEARS) {
            int index = GEARS.indexOf( item );
            int count = COUNT.get( index);
            data[dataIndex][0]=item;
            data[dataIndex][1]=count;
            dataIndex++;
        }

//        data[0] = new Object[] { GEARS.get(0), COUNT.get(0) };
//        data[1] = new Object[] { GEARS.get(1), COUNT.get(1) };

        return data;
    }

    public static int Size()
    {
        return GEARS.size();
    }

    public static void Reset()
    {
        GEARS.clear();
        COUNT.clear();
    }

}
