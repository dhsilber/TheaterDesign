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

    private static HashMap<String, String> LIST = new HashMap<>();

    public Layer( String name, String tag ) {
        LIST.put( name, tag );
    }

    public static HashMap<String, String> List() {
        return LIST;
    }
}

