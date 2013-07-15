package com.mobiletheatertech.plot;

/**
 * Complain about missing attributes.
 *
 * @author dhs
 * @since 0.0.2
 */
public class AttributeMissingException extends Exception {

    /**
     * Constructs an instance of <code>AttributeMissingException</code> with a message built with
     * the details provided.
     *
     * @param tag       Element type which is missing the attribute
     * @param attribute Name of missing attribute
     */
    public AttributeMissingException( String tag, String attribute ) {
        super( tag + " is missing required '" + attribute + "' attribute." );
    }
}
