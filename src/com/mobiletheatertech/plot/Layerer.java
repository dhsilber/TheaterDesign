package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

/**
 * Created by dhs on 12/2/14.
 */
public abstract class Layerer extends Verifier {

    Layer layer = null;

    public Layerer( Element element ) throws DataException, InvalidXMLException {
        super( element );
//        System.err.println("E");

        String name = this.getClass().getSimpleName();
        layer = Layer.Register( name, name );
//        System.out.println( "Layerer adding " + name );
        layer.register(this);
    }
}
