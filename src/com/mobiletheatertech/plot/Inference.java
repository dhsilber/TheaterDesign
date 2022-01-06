package com.mobiletheatertech.plot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by dhs on 5/28/14.
 */
public class Inference {

    private static HashMap< String, ArrayList<String> > Dictionary =
            new HashMap<String, ArrayList<String>>();

    public static void Add( String key, String value ) {
        ArrayList<String> values;
        if( Dictionary.containsKey(key)) {
            values = Dictionary.get(key);
        }
        else
        {
            values = new ArrayList<String>();
            Dictionary.put( key, values );
        }
        values.add(value);
    }

    public static ArrayList<String> Get( String key ) {
        return Dictionary.get( key );
    }

    public static void Reset() {
        Dictionary.clear();
    }

    public static int Count() {
        return Dictionary.size();
    }

    public static void Dump() {

        for( String key: Dictionary.keySet()) {
            System.out.print( "Key: "+ key+ " --> ");
            for ( String value: Dictionary.get( key ) ) {
                System.out.print( value + ", ");
            }
        }
    }
}
