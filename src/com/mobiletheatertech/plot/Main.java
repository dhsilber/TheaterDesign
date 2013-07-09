package com.mobiletheatertech.plot;

import java.io.FileNotFoundException;

/**
 * Main for Plot.
 * 
 * Intentionally minimal. All the real work is done elsewhere.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Main {

    /**
     * Kick off a run of Plot.
     * 
     * Expects only one argument which names the description file without any
     * extension. This same name is used for the output file.
     * 
     * {@link Read Reads} the Plot description.
     * 
     * {@link Write Writes} the generated output.
     * 
     * @param args the command line arguments
     */
    public static void main( String[] args )
        throws ArgumentException, FileNotFoundException {

        if ( args.length < 1 ) {
            throw new ArgumentException( "Not enough arguments" );
        }
        if ( args.length > 1 ) {
            throw new ArgumentException( "Too many arguments" );
        }

        try {
            new Read( args[0] );

            new Write( args[0] );
        }
        catch ( Exception e ) {
            System.err.println( e.getMessage() );
            System.exit( 1 );
        }
    }
}
