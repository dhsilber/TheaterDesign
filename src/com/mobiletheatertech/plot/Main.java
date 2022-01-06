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
     * With only one argument, it names the description file without any extension. This same
     * name is used for the output directory. Input and output root directories are built in.
     * <p/>
     * If that single argument has a path component, the default input directory is ignored.
     * <p/>
     * If a second argument is present, it specifies that output root directory.
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
        try {
            Configuration.Initialize( args );

            new Read();

            new Write().init();
        }
        catch ( Exception e ) {
//            System.err.println( e.getMessage() );
            e.printStackTrace( System.err );
//            System.err.println(e.getStackTrace().toString());
            System.exit( 1 );
        }
    }
}
