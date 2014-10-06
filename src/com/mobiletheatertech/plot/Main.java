package com.mobiletheatertech.plot;

/**
 * Main for Plot.
 * <p/>
 * Intentionally minimal. All the real work is done elsewhere.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Main {

    /**
     * Kick off a run of Plot.
     * <p/>
     * Expects only one argument which names the description file without any extension. This same
     * name is used for the output file.
     * <p/>
     * {@link Read Reads} the Plot description.
     * <p/>
     * {@link Write Writes} the generated output.
     *
     * @param args the command line arguments
     * @throws ArgumentException If there is not exactly one argument
     */
    public static void main( String[] args )
            throws ArgumentException
    {

        if (args.length < 1) {
            throw new ArgumentException( "Not enough arguments" );
        }
        if (args.length > 1) {
            throw new ArgumentException( "Too many arguments" );
        }

        try {
            new Read( args[0] );

            new Write().init( args[0] );
        }
        catch ( Exception e ) {
            System.err.println( e.getMessage() );
            System.exit( 1 );
        }
    }
}
