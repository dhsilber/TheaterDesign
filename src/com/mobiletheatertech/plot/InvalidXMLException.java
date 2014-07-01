package com.mobiletheatertech.plot;

/**
 * Complain about invalid XML.
 *
 * @author dhs
 * @since 0.0.2
 */
public class InvalidXMLException extends Exception {

    /**
     * Constructs an instance of <code>InvalidXMLException</code> with the message specified.
     *
     * @param message Message to display for this exception
     */
    public InvalidXMLException(String message) {
        super(message);
//        super( "Top level element must be '" + tag+"'" );
    }

    /**
     * Constructs an instance of <code>InvalidXMLException</code> with a message built with
     * the details provided.
     *
     * @param tag       XML element tag
     * @param id        Identification of a specific XML element - may be <code>null</code>
     * @param message   Message to present
     * @since 0.0.24
     */
    public InvalidXMLException(String tag, String id, String message) {
        super(
                tag + " "
                        + (null == id || id.isEmpty()
                        ? "instance"
                        : "(" + id + ")")
                        + " " + message + "."
        );
    }
}
