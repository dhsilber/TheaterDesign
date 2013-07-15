package com.mobiletheatertech.plot;

/**
 * Complain about invalid references to named Plot objects.
 *
 * @author dhs
 * @since 0.0.5
 */
public class ReferenceException extends Exception {
    public ReferenceException( String message ) {
        super( message );
    }
}
