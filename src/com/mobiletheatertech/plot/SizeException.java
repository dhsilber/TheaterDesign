package com.mobiletheatertech.plot;

/**
 * {@code SizeException} is thrown when an item is constructed with an invalid value. E.g. a {@link
 * Pipe} constructed with a length of zero.
 *
 * @author dhs
 * @since 0.0.6
 */
public class SizeException extends Exception {

    /**
     * Constructs an instance of <code>SizeException</code> with the specified detail message.
     *
     * @param type Kind of Plot thingy
     * @param part Size
     */
    public SizeException( String type, String part ) {
        super( type + " should have a positive " + part + "." );
    }
}
