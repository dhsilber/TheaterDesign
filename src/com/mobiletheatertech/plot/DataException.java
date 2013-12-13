package com.mobiletheatertech.plot;

/**
 * Report bad data within the program.
 *
 * @author dhs
 * @since 0.0.21
 */
public class DataException extends Exception {

    /**
     * Constructs an instance of <code>DataException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public DataException(String message) {
        super(message);
    }

}
