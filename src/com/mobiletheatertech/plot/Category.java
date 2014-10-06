package com.mobiletheatertech.plot;

import java.util.HashMap;

/**
 * Display category, so that certain types of things can be requested for a
 * drawing.
 *
 * Created by dhs on 9/9/14.
 */
public class Category {
    private static HashMap<String, Class> CATEGORYLIST = new HashMap<>();

    private String name = null;

    public Category( String name, Class clazz  ) {
        this.name = name;

        CATEGORYLIST.put( name, clazz );
    }

    public static Class Select( String name ) {
//        for( String key : CATEGORYLIST.keySet() ) {
//            if (key.equals( name )) {
                return CATEGORYLIST.get( name );
//            }
//        }
//        return null;
    }

    static int size() {
        return CATEGORYLIST.size();
    }

    static Boolean Contains (Class clazz) {
        return CATEGORYLIST.containsValue( clazz );
    }

    public String name() {
        return name;
    }
}
