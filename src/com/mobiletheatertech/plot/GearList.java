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

//    public GearList( String item ) {
//    }

    public static void Add (Gear thing) {
//        System.out.println( "In GearList.Add: Adding " + thing.item() );
        if( ITEMS.contains( thing.item() ) ) {
            int index = ITEMS.indexOf( thing.item() );
            int count = COUNT.get( index );
            COUNT.set(index, ++count);
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
        final Object[][] data = new Object[ GEARS.size()][2];

        int dataIndex = 0;
        for ( Gear thing : GEARS ) {
            int index = GEARS.indexOf( thing );
            int count = COUNT.get( index);
            data[dataIndex][0] = thing.item();
            data[dataIndex][1] = count;
            dataIndex++;
        }

//        data[0] = new Object[] { GEARS.get(0), CHAIRCOUNT.get(0) };
//        data[1] = new Object[] { GEARS.get(1), CHAIRCOUNT.get(1) };

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
