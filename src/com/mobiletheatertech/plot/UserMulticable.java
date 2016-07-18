//package com.mobiletheatertech.plot;
//
//import org.w3c.dom.Element;
//
///**
// * Created by dhs on 1/9/15.
// */
//public class UserMulticable extends Elemental {
//
//    Double startX = null;
//    Double startY = null;
//    Double startZ = null;
//    Double endX = null;
//    Double endY = null;
//    Double endZ = null;
//
//    Multicable start = null;
//    Multicable end = null;
//
//    public UserMulticable( Element element ) throws AttributeMissingException, InvalidXMLException {
//        super( element );
//
//        id = getStringAttribute( element, "id" );
//        startX = getDoubleAttribute(element, "start-x");
//        startY = getDoubleAttribute( element, "start-y" );
//        startZ = getDoubleAttribute(element, "start-z");
//        endX = getDoubleAttribute(element, "end-x");
//        endY = getDoubleAttribute( element, "end-y" );
//        endZ = getDoubleAttribute( element, "end-z" );
//
//        start = new Multicable( id + ":start", startX, startY, startZ );
//        end = new Multicable( id + ":end", endX, endY, endZ );
//
//        new CableRun( "multicable", id + ":start", id + ":end", "", "" );
//    }
//
//    public Multicable start() {
//        return start;
//    }
//
//    public Multicable end() {
//        return end;
//    }
//
//}
