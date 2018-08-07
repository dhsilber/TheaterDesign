package com.mobiletheatertech.plot;

import java.util.ArrayList;

/**
 * Collect lists of gear.
 *
 * Created by dhs on 4/12/14.
 *
 * @author dhs
 * @since 0.0.24
 */
public class GearList {

    private static ArrayList<Gear> GEARS = new ArrayList<>();
    private static ArrayList<Integer> COUNT = new ArrayList<>();
    private static ArrayList<String> ITEMS = new ArrayList<>();

    public static final int ITEM = 0;
    public static final int QUANTITY = 1;
    public static final int EXTENT = 2;


    public static void Add (Gear thing) {
//        System.out.println( "In GearList.Add: Adding " + thing.item() );
        if( ITEMS.contains( thing.item() ) ) {
            int index = ITEMS.indexOf( thing.item() );
            COUNT.set(index, 1 + COUNT.get( index ) );
        }
        else {
            ITEMS.add( thing.item() );
            GEARS.add( thing );
            COUNT.add( 1 );
        }
    }

    public static Integer Check( String item ) {
        int index;
        Integer count = 0;
        if( ITEMS.contains( item ) ) {
            index = ITEMS.indexOf( item );
            count = COUNT.get( index );
        }

        return count;
    }

    public static Object[][] Report () {
        final Object[][] data = new Object[ GEARS.size()][EXTENT];

        int dataIndex = 0;
        for ( Gear thing : GEARS ) {
            int index = GEARS.indexOf( thing );
            int count = COUNT.get( index);
            data[dataIndex][ITEM] = thing.item();
            data[dataIndex][QUANTITY] = count;
            dataIndex++;
        }

        return data;
    }

    public static int Size()
    {
        return GEARS.size();
    }

    public static void Reset()
    {
        ITEMS.clear();
        GEARS.clear();
        COUNT.clear();
    }

}
