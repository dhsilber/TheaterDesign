package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 8/18/14.
 */
public abstract class Verifier extends ElementalLister {

    public Verifier( Element element ) throws InvalidXMLException{
        super( element );
    }

    /**
     * @throws InvalidXMLException
     * @throws LocationException
     */
    public static void VerifyAll()
            throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException
    {
        for ( ElementalLister item : LIST ) {
            if ( Verifier.class.isInstance( item )) {
//System.err.print( "Verifying: " );
//System.err.println( item.toString() );
                Verifier thing = (Verifier) item;
                thing.verify();
            }
        }
//System.err.println( "Verifying done." );
    }

    /**
     * Hook to allow each {@code Verifier}-derived instance to perform sanity checks after all XML has
     * been parsed.
     * <p/>
     * Items that make use of this functionality will replace this comment with specifics.
     *
     * @throws InvalidXMLException if an invalid combination of XML specifications is found
     * @throws LocationException   if certain plot items don't fit in available physical space
     */
    public abstract void verify()
            throws FeatureException, InvalidXMLException, LocationException, MountingException, ReferenceException;

}
