package com.mobiletheatertech.plot;

/**
 * Complain about command-line argument(s) specified to the program.
 *
 * @author dhs
 * @since 0.0.1
 */
public class ArgumentException extends Exception {

    /**
     * Constructs an instance of
     * <code>ArgumentException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public ArgumentException( String message ) {
        super( message );
    }
}
