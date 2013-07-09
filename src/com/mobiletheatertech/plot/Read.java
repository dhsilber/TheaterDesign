package com.mobiletheatertech.plot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Read deals with input file issues and relies on {@link Parse Parse}
 * to deal with XML.
 *
 * @author dhs
 * @since 0.0.1
 */
public class Read {

    /**
     * Look for a file named according to the specified basename and open a
     * stream from it which is {@link Parse parsed}.
     * 
     * The complete pathname is built as
     * {@literal <user's home directory>}{@code /Plot/plotfiles/}{@literal <basename>}{@code .xml}
     * 
     * @param basename basename of the file to be read.
     * @throws FileNotFoundException if the file cannot be found 
     */
    public Read( String basename ) throws Exception {
        
        String home = System.getProperty("user.home");
        if (null == home) {
            // throw exception
        }
        
        String pathname = home + "/Plot/plotfiles/" + basename + ".xml";
        
        InputStream stream = new FileInputStream(pathname);
        
        new Parse( stream );
    }
}
