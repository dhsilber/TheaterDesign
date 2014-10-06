package com.mobiletheatertech.plot;

import java.util.HashMap;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 8:15 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Keep track of the various layers that are in use for a plot.
 *
 * @author dhs
 * @since 0.0.19
 */
public class Layer {

    private static HashMap<String, Layer> LIST = new HashMap<>();

    public static HashMap<String, Layer> List() {
        return LIST;
    }

    private String name = null;
    private Boolean active = false;

    public Layer( String tag, String name ) throws DataException {
        if ( LIST.containsKey( tag ) ) {
            if ( ! LIST.get( tag ).name().equals( name ) ) {
                throw new DataException("Layer " + tag + " is already defined.");
            }
        }
        this.name = name;
        LIST.put( tag, this );
    }

    public String name() {
        return name;
    }

    public Boolean active() {
        return active;
    }

    public void activate() {
        active = true;
    }
}

