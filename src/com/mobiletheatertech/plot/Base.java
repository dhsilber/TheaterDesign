//package com.mobiletheatertech.plot;
//
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import java.awt.*;
//
///**
//* Created with IntelliJ IDEA. User: dhs Date: 6/29/13 Time: 5:05 PM To change this template use
//* File | Settings | File Templates.
//*
//* @author dhs
//* @since 0.0.5
//*/
//public class Base extends Minder {
//
//    private int x;
//    private int y;
//
//
//    // This seems to be generic - refactor it into Minder
//    public static void ParseXML( NodeList list ) throws AttributeMissingException {
//        int length = list.getLength();
//        for (int index = 0; index < length; index++) {
//            Node node = list.item( index );
//
//            if (null != node && node.getNodeType() == Node.ELEMENT_NODE) {
//                Element element = (Element) node;
//                new Base( element );
//            }
//        }
//    }
//
//    public Base( Element element ) throws AttributeMissingException {
//        x = getIntegerAttribute( element, "x" );
//        y = getIntegerAttribute( element, "y" );
//    }
//
//    public Point locate() {
//        return new Point( x, y, 0 );
//    }
//
//    @Override
//    public void verify() throws InvalidXMLException {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void drawPlan( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLUE );
//        canvas.draw( new Rectangle( x - 18, y - 18, 36, 36 ) );
//    }
//
//    @Override
//    public void drawSection( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLUE );
//        canvas.draw( new Rectangle( x - 18, y - 18, 36, 36 ) );
//    }
//
//    @Override
//    public void drawFront( Graphics2D canvas ) {
//        canvas.setPaint( Color.BLUE );
//        canvas.draw( new Rectangle( x - 18, y - 18, 36, 36 ) );
//    }
//
//    @Override
//    public void dom( Draw draw, View mode ) {
//    }
//}
