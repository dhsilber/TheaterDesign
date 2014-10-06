package com.mobiletheatertech.plot;

import java.util.HashMap;

/*
 * Created with IntelliJ IDEA. User: dhs Date: 10/19/13 Time: 9:35 PM To change this template use
 * File | Settings | File Templates.
 */

/**
 * Generate HTML for various purposes.
 *
 * @author dhs
 * @since 0.0.19
 */
public class HTML {

    /**
     * Generate an HTML checkbox for each defined {@code Layer} which invokes an appropriate
     * JavaScript function when its state changes.
     *
     * @param data set of {@code Layer}s.
     * @return
     */
    public static String Checkboxes( HashMap<String, Layer> data ) {
        StringBuilder output = new StringBuilder();
        for (String tag : data.keySet()) {
            String name = data.get( tag ).name();
            output.append(
                    "<input type=\"checkbox\" onclick=\"parent.selectLayer" + tag +
                            "();\" name=\"layer\"" +
                            " id=\"" + tag + "layer\" checked=\"checked\" value=\"" + tag +
                            "\" />" + name +
                            "<br />\n" );
        }

        return output.toString();
    }

    /**
     * Generate an HTML checkbox for each defined {@code Layer} which invokes an appropriate
     * JavaScript function when its state changes.
     *
     * @param data set of {@code Layer}s.
     * @return
     */
    public static String SelectFunctions( HashMap<String, Layer> data ) {
        StringBuilder output = new StringBuilder();
        for (String tag : data.keySet()) {
//            String name = data.get( tag ).name();
            output.append( SelectFunction( tag ) );
        }

        return output.toString();
    }

    /**
     * Generate a JavaScript function to show or hide a particular layer.
     *
     * @param tag which specifies the layer to be hidden or shown
     * @return
     */
    private static String SelectFunction( String tag ) {
        return "function selectLayer" + tag + "()\n" +
                "{\n" +
                "  if( document.getElementById('" + tag + "layer').checked)\n" +
                "    show( \"" + tag + "\" );\n" +
                "  else\n" +
                "    hide( \"" + tag + "\" );\n" +
                "}\n";
    }
}

//Mountainside Hospital Montclaire