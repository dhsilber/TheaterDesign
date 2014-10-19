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
     * @param id        Identification of the specific XML element which is missing the attribute -
     *                  may be <code>null</code>
     * @param attribute Name of missing attribute
     */
    public AttributeMissingException( String tag, String id, String attribute ) {
        super(
                tag + " "
                        + (null == id || id.isEmpty()
                        ? "instance"
                        : "(" + id + ")")
                        + " is missing required '" + attribute + "' attribute." );
    }

    /**
     * Constructs an instance of <code>AttributeMissingException</code> with a specified message.
     *
     * @param message   pre-constructed message
     */
    public AttributeMissingException( StringBuilder message ) {
        super( message.toString() );
    }

    /**
     * Constructs an instance of <code>AttributeMissingException</code> with a specified message.
     *
     * @param message   pre-constructed message
     */
    public AttributeMissingException( String message ) {
        super( message.toString() );
    }
}
