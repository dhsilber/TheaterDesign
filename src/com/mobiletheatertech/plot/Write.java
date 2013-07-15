package com.mobiletheatertech.plot;

/**
 * <code>Write</code> deals with output file issues and relies on {@link Draw Draw} to generate SVG
 * content.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Write {

    private String home = null;

    /**
     * Draw each of the Plot items that have been defined to a SVG file.
     * <p/>
     * The complete pathname is built as {@literal <user's home directory>}{@code
     * /Plot/out/}{@literal <basename>}{@code .xml}
     * <p/>
     * The non-drawing updates to the generated SVG document have to be done after all of the
     * drawing is completed. Because of this, {@code Write} needs to interact with {@link Draw} and
     * {@link Minder} methods in a very specific order.
     *
     * @param basename basename of the file to be written.
     */
    public Write( String basename ) {
        home = System.getProperty( "user.home" );
        if (null == home) {
            // throw exception
        }

        writePlan( basename );
        writeSection( basename );
        writeFront( basename );
    }

    private void writePlan( String basename ) {
        Draw draw = new Draw();

        Minder.DrawAllPlan( draw.canvas() );

//        Hack.Draw( drawPlan.canvas() );

        draw.getRoot();

        Minder.DomAllPlan( draw );

        Hack.Dom( draw );


        String pathname = home + "/Plot/out/" + basename + "_plan.svg";

        draw.create( pathname );
    }

    private void writeSection( String basename ) {
        Draw draw = new Draw();

        Minder.DrawAllSection( draw.canvas() );

//        Hack.Draw( drawPlan.canvas() );

        draw.getRoot();

        Minder.DomAllSection( draw );

        Hack.Dom( draw );

        String pathname = home + "/Plot/out/" + basename + "_section.svg";

        draw.create( pathname );
    }

    private void writeFront( String basename ) {
        Draw draw = new Draw();

        Minder.DrawAllFront( draw.canvas() );

//        Hack.Draw( drawPlan.canvas() );

        draw.getRoot();

        Minder.DomAllFront( draw );

        Hack.Dom( draw );

        String pathname = home + "/Plot/out/" + basename + "_front.svg";

        draw.create( pathname );
    }
}

