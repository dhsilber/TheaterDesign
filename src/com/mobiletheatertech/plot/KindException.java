package com.mobiletheatertech.plot;

/**
 * {@code KindException} is thrown when an item is constructed with an invalid type. E.g. a {@link
 * Truss} constructed with an unsupported {@code size} attribute.
 *
 * @author dhs
 * @since 0.0.5
 */
public class KindException extends Exception {

    /**
     * Constructs an instance of <code>KindException</code> with the specified detail message.
     *
     * @param type  Kind of Plot thingy
     * @param value Size
     */
    public KindException( String type, int value ) {
        super( type + " of size " + value + " not supported. Try 12 or 20." );
    }
}
