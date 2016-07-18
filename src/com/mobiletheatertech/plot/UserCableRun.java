//package com.mobiletheatertech.plot;
//
//import org.w3c.dom.Element;
//
///**
// * Created by dhs on 1/9/15.
// */
//public class UserCableRun extends Elemental {
//
//    private CableRun cableRun = null;
//
//    private String signal;
//    private String source;
//    private String sink;
//    private String channel;
//    private String routing;
//
//
//    public UserCableRun ( Element element ) throws AttributeMissingException, InvalidXMLException {
//        super(element);
//
//        signal = getOptionalStringAttribute( element, "signal" );
//        source = getOptionalStringAttribute( element, "source" );
//        sink = getOptionalStringAttribute( element, "sink" );
//        channel = getOptionalStringAttribute( element, "channel" );
//        routing = getOptionalStringAttribute( element, "routing");
//
//        if (signal.isEmpty() || source.isEmpty() || sink.isEmpty() ) {
//            StringBuilder message = new StringBuilder ( "CableRun " );
//            if ( ! signal.isEmpty() ) {
//                message.append( "of " ).append( signal ).append( " " );
//            }
//            if ( ! source.isEmpty() ) {
//                message.append( "from " ).append( source ).append( " " );
//            }
//            if ( ! sink.isEmpty() ) {
//                message.append( "to " ).append( sink ).append( " " );
//            }
//            message.append( "is missing required" );
//
//            int signalCommaPosition = 0;
//            int beforeLastErrorPosition = 0;
//            int errorCount = 0;
//
//            if ( signal.isEmpty() ) {
//                message.append( " 'signal'" );
//                signalCommaPosition = message.length();
//                errorCount++;
//            }
//            if ( source.isEmpty() ) {
//                beforeLastErrorPosition = message.length();
//                message.append( " 'source'" );
//                errorCount++;
//            }
//            if ( sink.isEmpty() ) {
//                beforeLastErrorPosition = message.length();
//                message.append( " 'sink'" );
//                errorCount++;
//            }
//
//            if ( 2 <= errorCount) {
//                message.insert( beforeLastErrorPosition, " and" );
//            }
//
//            if ( 3 == errorCount ) {
//                message.insert( beforeLastErrorPosition, "," );
//                message.insert( signalCommaPosition, "," );
//            }
//
//            message.append( " attribute." );
//            if (1 < errorCount) {
//                message.insert( message.length() - 1, 's' );
//            }
//            throw new AttributeMissingException( message );
//        }
//
//        if( ! "".equals( routing ) && ! CableRun.DIRECT.equals( routing )) {
//            throw new InvalidXMLException( CableRun.class.getSimpleName() +
//                    " from " + source + " to " + sink + " has invalid routing attribute '" + routing + "'.");
//        }
//
//        cableRun = new CableRun( signal, source, sink, channel, routing );
//    }
//
//    public CableRun cableRun() {
//        return cableRun;
//    }
//}
