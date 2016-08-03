package com.mobiletheatertech.plot;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by dhs on 7/15/14.
 */
public class ElementalLister extends Elemental {

    /**
     * Every <@code ElementalLister}-derived object is referenced from this list.
     */
    public static ArrayList<ElementalLister> LIST = new ArrayList<>();

    public ElementalLister( Element element ) throws InvalidXMLException {
        super (element);
//        System.err.println("G");

        LIST.add( this );
    }

    /**
     * Provides the list of all plot items defined.
     *
     * @return list of plot items
     */
    public static ArrayList<ElementalLister> List() {
        return LIST;
    }

    public static void Remove( ElementalLister thingy ) { LIST.remove( thingy ); }
}
