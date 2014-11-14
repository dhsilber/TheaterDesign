package com.mobiletheatertech.plot;

import java.util.HashMap;

/**
 * Display category, so that certain types of things can be requested for a
 * drawing.
 *
 * Created by dhs on 9/9/14.
 */
public class Category {
    private static HashMap<String, Category> CATEGORYLIST = new HashMap<>();

    private String name = null;
    private Class clazz = null;
    private String layer = null;

    public Category( String name, Class clazz ) {
        init(name, clazz);
    }

    public Category( String name, Class clazz, String layer ) {
        this.layer = layer;
        init( name, clazz );
    }

    void init(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;

        CATEGORYLIST.put(name, this);
    }

    public static Category Select( String name ) {
        return CATEGORYLIST.get( name );
    }

    static int size() {
        return CATEGORYLIST.size();
    }

    static Boolean Contains (Class clazz) {
        return CATEGORYLIST.containsValue(clazz);
    }

    public String name() {
        return name;
    }

    public Class clazz() {
        return clazz;
    }

    public String layer() {
        return layer;
    }

    @Override
    public String toString() {
        return "Category: " + name +
                ", class: " + clazz.toString() +
                ", layer: " + ((null == layer) ? "null" : layer) + "." ;
    }
}
