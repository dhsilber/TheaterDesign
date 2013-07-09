package com.mobiletheatertech.plot;

/**
 * Complain about invalid XML.
 *
 * @author dhs
 * @since 0.0.2
 */
public class InvalidXMLException extends Exception {

    /**
     * Constructs an instance of
     * <code>InvalidXMLException</code> with the message specified.
     *
     * @param message Message to display for this exception
     */
    public InvalidXMLException( String message ) {
        super( message );
//        super( "Top level element must be '" + tag+"'" );
    }
}
