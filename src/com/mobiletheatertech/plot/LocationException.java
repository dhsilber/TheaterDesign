package com.mobiletheatertech.plot;

/**
 * {@code LocationException} is thrown when an item would be placed in an
 * illegal place. E.g. a {@link Stage} extending outside of the {@link Venue}.
 *
 * @author dhs
 * @since 0.0.3
 */
public class LocationException extends Exception {

    /**
     * Creates a new instance of
     * <code>LocationException</code> with a fixed message.
     */
    public LocationException( String message ) {
        super( message );
//        super(  );
    }

}
