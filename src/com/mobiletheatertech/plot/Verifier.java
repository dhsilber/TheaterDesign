package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 8/18/14.
 */
public abstract class Verifier extends ElementalLister {

    public Verifier( Element element ) throws InvalidXMLException{
        super( element );
//        System.err.println( "F" );
    }

    /**
     * @throws InvalidXMLException
     * @throws LocationException
     */
    public static void VerifyAll()
            throws AttributeMissingException, DataException, FeatureException,
            InvalidXMLException, LocationException,
            MountingException, ReferenceException
    {
        for ( ElementalLister item : LIST ) {
            if ( Verifier.class.isInstance( item )) {
                Verifier thing = (Verifier) item;
                thing.verify();
            }
        }
//        CableRun.Verify();
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
            throws AttributeMissingException, DataException, FeatureException,
            InvalidXMLException, LocationException,
            MountingException, ReferenceException;

}
